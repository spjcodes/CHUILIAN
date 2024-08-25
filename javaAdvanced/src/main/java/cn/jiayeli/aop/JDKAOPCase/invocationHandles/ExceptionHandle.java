package cn.jiayeli.aop.JDKAOPCase.invocationHandles;

import cn.jiayeli.aop.exceptionHandle.ExceptionHandAndPersistenceByLogger;
import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;

public class ExceptionHandle<T> implements InvocationHandler {

    /**
     * 配置要写道MySQL数据库中的日志级别
     */
    private final List<String> logLevers = Arrays.asList("error", "warn");

    Logger  log = LoggerFactory.getLogger(ExceptionHandle.class);

    private T targetObj;
    private String applicationId;

    public ExceptionHandle(T targetObj, String applicationId) {
        this.targetObj = targetObj;
        this.applicationId = applicationId;
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
           log.debug("inProgress: {}.{}",  ExceptionHandle.class.getName(),  "matchSlf4jInstance" );
           String on = method.getName();
           List<Object> as = Arrays.asList(args);
           log.debug("inProgress: {}.{}, methodName: {}, args: {}",
                   ExceptionHandle.class.getName(),"matchSlf4jInstance", on, as );

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

    /**
     * 提取异常对象中的信息到model，并用jdbc写入数据库
     * @param method 调用的方法
     * @param objects 方法参数
     * @param e 异常对象
     */
    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e)  {

    // 处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(this.applicationId);
        Optional.ofNullable(this.targetObj).ifPresent(obj -> exceptionInfoModel.setClassName(obj.getClass().getName()));
        Optional.ofNullable(method).ifPresent(m -> exceptionInfoModel.setMethodName(m.getName()));
        exceptionInfoModel.setParameters(Arrays.toString(objects));
        if (Optional.ofNullable(e).isPresent()) {
            exceptionInfoModel.setExceptionType(e.getClass().getName());
            exceptionInfoModel.setExceptionInfo(e.toString());
            exceptionInfoModel.setExceptionStackTrace(Arrays.toString(e.getStackTrace()));
            exceptionInfoModel.setExceptionLocalizedMessage(e.getLocalizedMessage());
        }
        exceptionInfoModel.setTime(System.currentTimeMillis());


        String userName = "";
        String password = "";
        String url = null;
        // load dbConfig.properties file
        Properties properties = new Properties();
        try {
            properties.load(ExceptionHandAndPersistenceByLogger.class.getClassLoader().getResourceAsStream("dbConfig.properties"));
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
        } catch (Exception ex) {
            log.error("load dbConfig.properties file error: {}", ex.getMessage());
        }


        try{
            log.debug("insert ExceptionInfoModel to MySQL sql: {}", exceptionInfoModel);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            String insertIntoSQL = "INSERT INTO test.exception_info (" +
                    "    id," +
                    "    jobName," +
                    "    className," +
                    "    methodName," +
                    "    parameters," +
                    "    exceptionType," +
                    "    exceptionInfo," +
                    "    exceptionStackTrace," +
                    "    exceptionLocalizedMessage," +
                    "    time" +
                    ") VALUES (null,?,?,?,?,?,?,?,?,?)";
            log.debug("sql: {}", insertIntoSQL);

            PreparedStatement pstmt = connection.prepareStatement(insertIntoSQL);
            pstmt.setString(1, exceptionInfoModel.getJobName());
            pstmt.setString(2, exceptionInfoModel.getClassName());
            pstmt.setString(3, exceptionInfoModel.getMethodName());
            pstmt.setString(4, exceptionInfoModel.getParameters());
            pstmt.setString(5, exceptionInfoModel.getExceptionType());
            pstmt.setString(6, exceptionInfoModel.getExceptionInfo());
            pstmt.setString(7, exceptionInfoModel.getExceptionStackTrace());
            pstmt.setString(8, exceptionInfoModel.getExceptionLocalizedMessage());
            pstmt.setTimestamp(9, new Timestamp(exceptionInfoModel.getTime()));
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            log.error("insert ExceptionInfoModel to MySQL sql has SQLException: ", ex);
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            log.error("MySQL Driver Not Found : " + ex);
            throw new RuntimeException(ex);
        }

    }

    private void persistenceExceptionAndCauseInfo(Method method, Object[] objects,String causeInfo, Throwable e)  {

        ExceptionInfoModel exceptionInfoModel = getExceptionInfoModel(method, objects, causeInfo, e);

        String userName = "";
        String password = "";
        String url = null;
        // load dbConfig.properties file
        Properties properties = new Properties();
        try {
            properties.load(ExceptionHandAndPersistenceByLogger.class.getClassLoader().getResourceAsStream("dbConfig.properties"));
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
        } catch (Exception ex) {
            log.error("load dbConfig.properties file error: {}", ex.getMessage());
        }


        try{
            log.debug("insert ExceptionInfoModel to MySQL sql: {}", exceptionInfoModel.toString());
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, userName, password);
            String insertIntoSQL = "INSERT INTO test.exception_info (" +
                    "    id," +
                    "    jobName," +
                    "    className," +
                    "    methodName," +
                    "    parameters," +
                    "    exceptionType," +
                    "    exceptionInfo," +
                    "    exceptionStackTrace," +
                    "    exceptionLocalizedMessage," +
                    "    time" +
                    ") VALUES (null,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(insertIntoSQL);
            pstmt.setString(1, exceptionInfoModel.getJobName());
            pstmt.setString(2, exceptionInfoModel.getClassName());
            pstmt.setString(3, exceptionInfoModel.getMethodName());
            pstmt.setString(4, exceptionInfoModel.getParameters());
            pstmt.setString(5, exceptionInfoModel.getExceptionType());
            pstmt.setString(6, exceptionInfoModel.getExceptionInfo());
            pstmt.setString(7, exceptionInfoModel.getExceptionStackTrace());
            pstmt.setString(8, exceptionInfoModel.getExceptionLocalizedMessage());
            pstmt.setTimestamp(9, new Timestamp(exceptionInfoModel.getTime()));
            pstmt.executeUpdate();
            pstmt.close();
            connection.close();
        } catch (SQLException ex) {
            log.error("insert ExceptionInfoModel to MySQL sql has SQLException: ", ex);
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            log.error("MySQL Driver Not Found : " + ex);
            throw new RuntimeException(ex);
        }

    }

    private ExceptionInfoModel getExceptionInfoModel(Method method, Object[] objects, String causeInfo, Throwable e) {
        // 处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(this.applicationId);
        Optional.ofNullable(this.targetObj).ifPresent(t -> exceptionInfoModel.setClassName(t.getClass().getName()));
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
}
