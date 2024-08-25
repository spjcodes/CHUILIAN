package cn.jiayeli.aop.exceptionHandle;

import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;


public class ExceptionHandAndPersistenceByLogger implements MethodInterceptor {
    private final List<String> logLevers = Arrays.asList("error", "warn");

    String jobName;

    ExceptionHandAndPersistenceByLogger(String jobName) {
        this.jobName = jobName;
    }

    private Logger log = LoggerFactory.getLogger(ExceptionHandAndPersistenceByLogger.class);

    private Class<?> targetClass;
    public Logger createProxyObj(Class<?> logclass, Class<?> targetClas) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        this.targetClass = targetClas;
        Enhancer eh = new Enhancer();
        eh.setSuperclass(logclass);
        eh.setCallbackType(ExceptionHandAndPersistenceByLogger.class);
        Class c = eh.createClass();
        Enhancer.registerCallbacks(c, new ExceptionHandAndPersistenceByLogger[]{this});
        Constructor<Logger> constructor = c.getConstructor(String.class);
        Logger loggerProxy= constructor.newInstance(logclass);
        return loggerProxy;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy)  {
        Object result = null;
        try {
            String on = method.getName();
            System.out.printf("on: %s%n", on);
            List<Object> as = Arrays.asList(objects);
            System.out.printf("on args: %s%n", as);
            if (logLevers.contains(Optional.ofNullable(on).orElse("").toLowerCase().trim())) {
                List<String> errorInfo = new ArrayList<>();
                as.forEach(
                        me -> {
                            if (me instanceof Exception) {
                                persistenceExceptionAndCauseInfo(method, objects, errorInfo.toString(), (Exception) me);
                            } else {
                                errorInfo.add(me.toString());
                            }
                        }
                );
            }

            System.out.printf("mps: %s%n", methodProxy.getSignature());
            result =  methodProxy.invokeSuper(o, objects);
        } catch (Throwable e) {
            persistenceExceptionInfo(method, objects, e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 提取异常对象中的信息到model，并用jdbc写入数据库
     * @param method 调用的方法
     * @param objects 方法参数
     * @param e 异常对象
     */
    private void persistenceExceptionInfo(Method method, Object[] objects, Throwable e)  {

//        处理异常对象中的信息，赋值给model
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

    private void persistenceExceptionAndCauseInfo(Method method, Object[] objects,String causeInfo, Throwable e)  {

//        处理异常对象中的信息，赋值给model
        ExceptionInfoModel exceptionInfoModel = new ExceptionInfoModel();
        exceptionInfoModel.setJobName(jobName);
        exceptionInfoModel.setClassName(this.targetClass.getName());
        exceptionInfoModel.setMethodName(method.getName());
        exceptionInfoModel.setParameters(Arrays.toString(objects));
        exceptionInfoModel.setExceptionType(e.getClass().getName());
        exceptionInfoModel.setExceptionInfo(String.format("Cause by: %s\n, Exception: %s", causeInfo, e));
        exceptionInfoModel.setExceptionStackTrace(Arrays.toString(e.getStackTrace()));
        exceptionInfoModel.setExceptionLocalizedMessage(e.getLocalizedMessage());
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
