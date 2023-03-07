package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午11:10
 */
public class JumpStep {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int stepHeight = scanner.nextInt();
        int num = jump(stepHeight);
        System.out.println(num);
    }

    private static int jump(int stepHeight) {
        if (stepHeight == 1 || stepHeight == 2) {
            return stepHeight;
        }
        int pre = 1;
        int cur = 2;

        for (int i = 3; i <= stepHeight ; i++) {
            cur = pre + cur;
            pre = cur - pre;
        }

        return cur;
    }
}
