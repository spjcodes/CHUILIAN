package com.jiayeli.lambdaExepression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
interface TestMethod {

    String apply(Object... args);
}

class Test1 implements TestMethod {

    @Override
    public String apply(Object... args) {
        Object[] args1 = args;
        List<String> argsList = Arrays.stream(args1).map(e -> (String) e).collect(Collectors.toList());
        String tmpStr = "__";
        for (int i = 0; i < argsList.size(); i++) {
            tmpStr += argsList.get(i);
        }
        System.out.println("........................in implements functions...");
        return tmpStr;
    }

    public static void main(String[] args) {
        System.out.println(new Test1().apply("a", "b", "c", "d"));;
    }
}
