package cn.jiayeli.aop.JDKAOPCase.invocationHandles;

import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理异常信息并写入ES。
 * ES IndexName 写在配置文件中，
 * 每次生成的日志id是作业名加 . 分割的随机后缀。
 * 要查询同一次执行产生的异常信息，就需要拿id去除 . 及后面的部分去匹配去查询。
 * {
 *         "_index" : "data_sync_exception_info_2024",
 *         "_type" : "_doc",
 *         "_id" : "applicationId_23242343434-424234.1019204576",
 *         "_score" : 1.0,
 *         "_source" : {
 *           "id" : 0,
 *           "jobName" : "applicationId_23242343434-424234",
 *           "className" : "org.apache.logging.slf4j.Log4jLogger",
 *           "methodName" : "error",
 *           "parameters" : "[log.error***********, java.lang.ArithmeticException: / by zero]",
 *           "exceptionType" : "java.lang.ArithmeticException",
 *           "exceptionInfo" : "Cause by: [log.error***********]\n, Exception: java.lang.ArithmeticException: / by zero",
 *           "exceptionStackTrace" : "[cn.jiayeli.aop.JDKAOPCase.TestCase.TestCase.test(Application.java:60), cn.jiayeli.aop.JDKAOPCase.TestCase.Application.main(Application.java:43)]",
 *           "exceptionLocalizedMessage" : "/ by zero",
 *           "time" : 1724864122681
 *         }
 *       }
 *
 * @param <T>
 */
public class ExceptionHandlePersistence2ES<T> implements InvocationHandler {

    private static RestHighLevelClient client = null;


    /**
     * 配置要写道MySQL数据库中的日志级别
     */
    private final List<String> logLevers = Arrays.asList("error", "warn");
    ObjectMapper mapper = new ObjectMapper();
    AtomicInteger logCount = new AtomicInteger();
    String defaultIndexName = "data_sync_exception_info";
    String indexName;
    String jobName ;
    static Logger  log = LoggerFactory.getLogger(ExceptionHandlePersistence2ES.class);

    private T targetObj;

    public ExceptionHandlePersistence2ES(T targetObj) {
        this.targetObj = targetObj;
        this.jobName = ApplicationStorage.getApplicationId();
    }

    /**
     * 创建代理对象
     * @return
     */
    public T createProxyObj() {
        T proxyObj = (T) Proxy.newProxyInstance(
                targetObj.getClass().getClassLoader(),
                targetObj.getClass().getInterfaces(),
                this
        );
        return proxyObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            matchSlf4jInstance(method, args);
            result = method.invoke(targetObj, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            persistenceExceptionInfo(method, args, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private void matchSlf4jInstance(Method method, Object[] args) {
       if(targetObj instanceof Logger) {
           log.debug("inProgress: {}.{}",  ExceptionHandlePersistence2ES.class.getName(),  "matchSlf4jInstance" );
           String on = method.getName();
           List<Object> as = Arrays.asList(args);
           log.debug("inProgress: {}.{}, methodName: {}, args: {}",
                   ExceptionHandlePersistence2ES.class.getName(),"matchSlf4jInstance", on, as );

           if (logLevers.contains(Optional.ofNullable(on).orElse("").toLowerCase().trim())) {
               List<String> errorInfo = new ArrayList<>();
               as.forEach(
                   me -> {
                       if (me instanceof Exception) {
                           persistenceExceptionAndCauseInfo(method, args, errorInfo.toString(), (Exception) me);
                           errorInfo.clear();
                       } else {
                           errorInfo.add(me.toString());
                       }
                   }
               );
               if(!errorInfo.isEmpty()) persistenceExceptionAndCauseInfo(method, args, errorInfo.toString(), null);
           }
       }
    }

    static Properties properties = new Properties();

    /**
     * 提取异常对象中的信息到model，并用jdbc写入数据库
     * @param method 调用的方法
     * @param objects 方法参数
     * @param e 异常对象
     */
    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e) {

    // 处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(this.jobName);
        Optional.ofNullable(this.targetObj).ifPresent(obj -> exceptionInfoModel.setClassName(((Logger) obj).getName()));
        Optional.ofNullable(method).ifPresent(m -> exceptionInfoModel.setMethodName(m.getName()));
        exceptionInfoModel.setParameters(Arrays.toString(objects));
        if (Optional.ofNullable(e).isPresent()) {
            exceptionInfoModel.setExceptionType(e.getClass().getName());
            exceptionInfoModel.setExceptionInfo(e.toString());
            exceptionInfoModel.setExceptionStackTrace(Arrays.toString(e.getStackTrace()));
            exceptionInfoModel.setExceptionLocalizedMessage(e.getLocalizedMessage());
        }
        exceptionInfoModel.setTime(System.currentTimeMillis());


        persistent2ES(exceptionInfoModel);

    }


    private void persistenceExceptionAndCauseInfo(Method method, Object[] objects,String causeInfo, Throwable e)  {

        ExceptionInfoModel exceptionInfoModel = getExceptionInfoModel(method, objects, causeInfo, e);
        persistent2ES(exceptionInfoModel);

    }

    private ExceptionInfoModel getExceptionInfoModel(Method method, Object[] objects, String causeInfo, Throwable e) {
        // 处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(this.jobName);
        Optional.ofNullable(this.targetObj).ifPresent(tarObj -> {
            Logger o = (Logger) this.targetObj;
            exceptionInfoModel.setClassName(o.getName());
        });
        Optional.ofNullable(method).ifPresent(m -> exceptionInfoModel.setMethodName(m.getName()));
        exceptionInfoModel.setParameters(Arrays.toString(objects));
        if(Optional.ofNullable(e).isPresent()){
            exceptionInfoModel.setExceptionType(e.getClass().getName());
            exceptionInfoModel.setExceptionLocalizedMessage(e.getLocalizedMessage());
            exceptionInfoModel.setExceptionStackTrace(Arrays.toString(e.getStackTrace()));
        }
        exceptionInfoModel.setExceptionInfo(String.format("Cause by: %s\n, Exception: %s", causeInfo, e));
        exceptionInfoModel.setTime(System.currentTimeMillis());
        return exceptionInfoModel;
    }


    private void getEsClient() {
        String hostName = "localhost";
        int port = 9200;
        String schema = "http";
        try {
            properties.load(ExceptionHandlePersistence2ES.class.getClassLoader().getResourceAsStream("dbConfig.properties"));
            String indexNameGet = properties.getProperty("indexName");
            indexName = indexNameGet == null ? defaultIndexName : indexNameGet;
            hostName = properties.getProperty("hostName");
            port = Integer.parseInt(properties.getProperty("port"));
            schema = properties.getProperty("schema");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(hostName, port, schema)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void persistent2ES(ExceptionInfoModel exceptionInfoModel)  {

        try{
            if(client == null) {
                getEsClient();
            }
            exceptionInfoModel.setId(String.format("%s.%05d", exceptionInfoModel.getJobName(), new Random().nextInt() + logCount.incrementAndGet()));
            log.debug("insert ExceptionInfoModel to ElasticSearch: {}", exceptionInfoModel);
            log.debug("insert ExceptionInfoModel to ElasticSearch index name: {}", indexName);
            String modelJsonStr = mapper.writeValueAsString(exceptionInfoModel);
            IndexRequest request = new IndexRequest(indexName)
//   ------------------------------------------------ ID ----------------------------------------------------------------------
                    .id(exceptionInfoModel.getId());
            request.source(modelJsonStr, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            log.info("send errorInfo to es success: {}", response.getResult());
        } catch (Exception ex) {
            log.error("insert ExceptionInfoModel to ElasticSearch error.", ex);

            throw new RuntimeException(ex);
        } finally {
//            try {
//                if(client != null) client.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

}
