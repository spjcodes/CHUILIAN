package cn.jiayeli.aop.JDKAOPCase.TestCase;

import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ApplicationStorage;
import cn.jiayeli.aop.JDKAOPCase.invocationHandles.ExceptionHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClass001 {
   Logger log1 =  LoggerFactory.getLogger(TestClass001.class);
    //3.
    Logger log = new ExceptionHandle<>(log1, ApplicationStorage.getApplicationId()).createProxyObj();

   public void testMethod() {

       log1.error("testMethod");
   }
}
