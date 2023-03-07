package cn.jiayeli.algorithms.huaweiOD;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午2:31
 */
public class KVAgg {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int cap = scanner.nextInt();
        HashMap<Long, Long> collect = new HashMap<>(cap);
       for (; cap >= 0; cap--){
            String[] split = scanner.nextLine().split("\\s+");
            if (split.length == 2) {
                long key = Long.parseLong(split[0]);
                long value = Long.parseLong(split[1]);
                collect.merge(key, value, Long::sum);

            }
        }
        collect.entrySet().forEach(e -> {
            System.out.printf("%d %d\n", e.getKey(), e.getValue());

        });
    }
}
