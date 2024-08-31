package cn.jiayeli.aop.cglib.interceptors;

import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ApplicationStorage;
import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ExceptionHandlePersistence2ES;
import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ExceptionHandleInterceptor<T> {

    static Logger log = LoggerFactory.getLogger(ExceptionHandleInterceptor.class);

    ObjectMapper mapper = new ObjectMapper();
    AtomicInteger logCount = new AtomicInteger();
    String defaultIndexName = "data_sync_exception_info";
    String indexName;
    String jobName ;
    private static RestHighLevelClient client = null;

    private Class<T> targetClazz;

    public T createProxyObj(Class<T> targetClazz, Object... args) throws NoSuchMethodException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, p, proxy) -> {
            Object result = null;
            try {
                result = proxy.invokeSuper(obj, p);
            } catch (Throwable e) {
                log.debug("ExceptionHandleInterceptor catch Throwable: {}", e.getMessage());
                persistenceExceptionInfo(method, p, e);
                throw new RuntimeException(e);
            }
            return result;
        });
        this.jobName = ApplicationStorage.getApplicationId();
        this.targetClazz = targetClazz;
        return (T) enhancer.create(
                findConstructor(targetClazz, args).getParameterTypes(),
                args
        );
    }



    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e) {

        // 处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(this.jobName);
        Optional.ofNullable(this.targetClazz).ifPresent(obj -> exceptionInfoModel.setClassName(obj.getName()));
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
        Optional.ofNullable(this.targetClazz).ifPresent(tarObj -> exceptionInfoModel.setClassName(tarObj.getName()));
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


    static Properties properties = new Properties();
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
            exceptionInfoModel.setId(String.format("%s.%05d", exceptionInfoModel.getJobName(), Math.abs(new Random().nextInt()) + logCount.incrementAndGet()));
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

    public static   Constructor<?> findConstructor(Class<?> clazz, Object... args) {
        Constructor<?>[] constructors = clazz.getConstructors();

        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == Arrays.stream(args).count()) {
                Class<?>[] parameterTypes = c.getParameterTypes();
                boolean matches = true;
                for (int i = 0; i < args.length; i++) {
                    if (!parameterTypes[i].isInstance(args[i])){
                        if(matchTypes(parameterTypes[i], args[i]))
                            matches = false;
                        break;
                    }
                }
                if (matches)  return c;
            }
        }
        return null;
    }

    public static <T, U> boolean matchTypes(T primitive, U wrapper) {
       return  primitive.getClass().getName().equals(getWrapperClassName(wrapper));
    }

    public static <U> String getWrapperClassName(U wrapper) {
        return wrapper.getClass().getName();
    }
}

