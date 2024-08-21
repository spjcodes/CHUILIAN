package cn.jiayeli.aop.exceptionHandle;

import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import cn.jiayeli.juc.ThreadCreate;

/**
 * desc:
 *
 * @author SHIPENGJUN
 * @date 2023/7/6
 */
public class Application {

    public static void main(String args[]) {
        // 作业名，用来标识是哪个作业的日志
        String jobName = "test";
        /**
        *创建代理对象
         *  代理类的类名 proxyObj = (代理类的类名) new ExceptionHandAndPersistence(jobName)
         *                 .createProxyObj(代理类的类名.class);
         */
        targetClass proxyObj = (targetClass) new ExceptionHandAndPersistence(jobName)
                .createProxyObj(targetClass.class);

        /*ThreadCreate proxyObj1 = (ThreadCreate) new ExceptionHandAndPersistence(jobName)
                .createProxyObj(ThreadCreate.class);

        proxyObj1.doublyStartCommonThread();*/

       /* targetClass targetClass = new targetClass();
        targetClass.hasException("s");*/
        String non = proxyObj.non(new ExceptionInfoModel(), true);

    }


}

class targetClass {

  public int hasException(String s) {
      if(s.equals("s")) hasException("ss");
      return 1/0;
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