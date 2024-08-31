package cn.jiayeli.aop;

import cn.jiayeli.aop.model.ExceptionInfoModel;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;


public class ExceptionHandAndPersistence implements MethodInterceptor {
    String jobName;

    ExceptionHandAndPersistence(String jobName) {
        this.jobName = jobName;
    }

    private Logger log = LoggerFactory.getLogger(ExceptionHandAndPersistence.class);

    private Class<?> targetClass;
    public targetClass createProxyObj(Class<?> targetClas) {
        this.targetClass = targetClas;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClas);
        enhancer.setCallback(this);
        targetClass t =  (targetClass) enhancer.create();
        return t;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)  {
        Object result = null;
        try {
            result =  methodProxy.invokeSuper(o, objects);
        } catch (Throwable e) {
            persistenceExceptionInfo(method, objects, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e)  {

        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(jobName);
        exceptionInfoModel.setClassName(this.targetClass.getName());
        exceptionInfoModel.setMethodName(method.getName());
        exceptionInfoModel.setParameters(Arrays.toString(objects));
        exceptionInfoModel.setExceptionType(e.getClass().getName());
        exceptionInfoModel.setExceptionInfo(e.toString());
        exceptionInfoModel.setExceptionStackTrace(Arrays.toString(e.getStackTrace()));
        exceptionInfoModel.setExceptionLocalizedMessage(e.getLocalizedMessage());
        exceptionInfoModel.setTime(System.currentTimeMillis());


        String userName = "";
        String password = "";
        String url = null;
        // load dbConfig.properties file
        Properties properties = new Properties();
        try {
            properties.load(ExceptionHandAndPersistence.class.getClassLoader().getResourceAsStream("dbConfig.properties"));
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
}
