package cn.jiayeli.aop.exceptionHandle;

import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * desc:
 *
 * @author SHIPENGJUN
 * @date 2023/7/6
 */
public class Application {

    static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 作业名，用来标识是哪个作业的日志
        String jobName = "test";
        /**
        *创建代理对象
         *  代理类的类名 proxyObj = (代理类的类名) new ExceptionHandAndPersistence(jobName)
         *                 .createProxyObj(代理类的类名.class);
         */
        /**
         * original usage:
         *  TargetClassTest targetClass = new TargetClassTest();
         *  int non = targetClass.hasException("s1");
         */

        /*cglib proxy usage:
        * */
        TargetClassTest proxyObj = (TargetClassTest) new ExceptionHandAndPersistence(jobName)
                .createProxyObj(TargetClassTest.class);

        proxyObj.hasException("s");

    }


}




class TargetClassTest {
    Logger log = LoggerFactory.getLogger(TargetClassTest.class);
  /*  Logger log1 = LoggerFactory.getLogger(targetClass.class);
           Logger log =  (Logger) new ExceptionHandAndPersistenceByLogger("jobId")
            .createProxyObj(log1.getClass(), targetClass.class);*/

    private Object a1;
    private String a2;

    TargetClassTest(Object a1, String a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    TargetClassTest() {

    }


    public int hasException(String s) {
      if(s.equals("s")) hasException("ss");
      int i = 0;
      try {
          log.info("execute 1/0");
          throw new SQLException("spj throwable");
//          i = 1 / 0;
      } catch (Exception e) {
          log.error("has exception", e);
      }
      return i;
  }

    public void non(ExceptionInfoModel model) {
        System.out.println(model.toString());
    }

    public String non(ExceptionInfoModel model, boolean flag) {
        System.out.println(model.toString());
        String className = model.getClassName();
        if (className.equals("aa")) {
            System.out.println("aa");
        }
        return className;
    }

}
