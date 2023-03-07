package cn.jiayeli.algorithms.huaweiOD;

import java.util.TreeSet;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午10:42
 */
public class HJ3 {
    public static void main(String[] args) {
        TreeSet<Integer> ts = new TreeSet<>();
        ts.add(1);
        ts.add(100);
        ts.add(10);
        ts.add(2);
        ts.forEach(System.out::println);

    }
}
