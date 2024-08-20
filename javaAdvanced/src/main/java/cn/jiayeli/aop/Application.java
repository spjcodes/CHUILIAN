package cn.jiayeli.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * desc:
 *
 * @author SHIPENGJUN
 * @date 2023/7/6
 */
public class Application {

    public static void main(String args[]) {
        targetClass proxyObj = new ExceptionHandAndPersistence()
                .createProxyObj(targetClass.class);
        proxyObj.hasException("s");
    }


}

class targetClass {

  public int hasException(String s) {
      if(s.equals("s")) hasException("ss");
      return 1/0;
  }

}