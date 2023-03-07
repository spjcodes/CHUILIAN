package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午11:40
 */
public class HJ17 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pos = scanner.nextLine();
        String[] p = pos.split(";");
        int x = 0;
        int y = 0;
        for (String s : p) {
            if (s.matches("[WSAD][0-9]{1,2}")) {
                int move = Integer.parseInt(s.substring(1, s.length()));
                switch (s.substring(0,1)) {
                    case "W":
                        y += move; break;
                    case "S":
                        y -= move; break;
                    case "A":
                        x += move; break;
                    case "D":
                        x -= move; break;
                }
            }
        }
        System.out.printf("%s,%s", x, y);
    }
}
