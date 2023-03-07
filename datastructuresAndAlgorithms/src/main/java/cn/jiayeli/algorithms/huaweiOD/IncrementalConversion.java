package cn.jiayeli.algorithms.huaweiOD;

import java.util.*;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午12:22
 */
public class IncrementalConversion {

    public static void main(String[] args) {
        //oxAA
        //      A A
        //      1 6
        //1 0 0 0 0
        Scanner scanner = new Scanner(System.in);
        String oxStr = scanner.nextLine();
        HashMap<String, Integer> oxCharMaps = new HashMap<>();
        oxCharMaps.put("A", 10);
        oxCharMaps.put("B", 11);
        oxCharMaps.put("C", 12);
        oxCharMaps.put("D", 13);
        oxCharMaps.put("E", 14);
        oxCharMaps.put("F", 15);
        if (oxStr.toUpperCase().startsWith("0X")) {
            oxStr = oxStr.substring(2);
        }
        char[] chars = oxStr.toCharArray();
        double result = 0;
        for (int i = 0; i < chars.length; i++) {
            String key = String.valueOf(chars[i]).toUpperCase();
            Integer num = oxCharMaps.get(key) != null ? oxCharMaps.get(key) : Integer.valueOf(String.valueOf(chars[i]));
            result += num * Math.pow(16, chars.length - i -1);
        }
        System.out.println(Integer.valueOf((int) result));
    }
}
