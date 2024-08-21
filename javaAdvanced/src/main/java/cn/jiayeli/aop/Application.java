package cn.jiayeli.aop;

import cn.jiayeli.aop.model.ExceptionInfoModel;

/**
 * desc:
 *
 * @author SHIPENGJUN
 * @date 2023/7/6
 */
public class Application {

    public static void main(String args[]) {
        targetClass proxyObj = new ExceptionHandAndPersistence("test")
                .createProxyObj(targetClass.class);
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