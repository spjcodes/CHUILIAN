package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午2:23
 */
public class Round {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String next = scanner.next();
        if (next.contains(".")) {
            String[] split = next.split("\\.");
            System.out.println(Integer.parseInt(split[1].substring(0, 1)) >= 5 ? Long.parseLong(split[0]) + 1 : Long.parseLong(split[0]));
        } else {
            System.out.println(Long.valueOf(next));
        }
    }
}
