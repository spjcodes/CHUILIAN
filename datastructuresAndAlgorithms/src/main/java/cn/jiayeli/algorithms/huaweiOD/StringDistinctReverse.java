package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午2:50
 */
public class StringDistinctReverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        String t = "";
        for (int i = next.length()-1 ; i >=0 ; i--) {
            String s = next.charAt(i) + "";
            if (!t.contains(s)) {
                t += s;
            }
        }
        System.out.println(t);
    }
}
