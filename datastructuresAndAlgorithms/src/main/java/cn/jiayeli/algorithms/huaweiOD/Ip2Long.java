package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/8 上午12:48
 */
public class Ip2Long {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ip = scanner.next();
        String longIp = scanner.next();
        ip2Long(ip);
        long2Ip(longIp);
    }

    private static void long2Ip(String longIp) {
        String longIpStr = Long.toBinaryString(Long.parseLong(longIp));
        //1010 00000011 00000011 11000001
        String ipSegment4 = longIpStr.substring(longIpStr.length() - 8, longIpStr.length());
        String ipSegment3 = longIpStr.substring(longIpStr.length() - 16, longIpStr.length() - 8);
        String ipSegment2 = longIpStr.substring(longIpStr.length() - 24, longIpStr.length() - 16);
        String ipSegment1 = longIpStr.substring(0, longIpStr.length() - 24);
        System.out.printf("%s.%s.%s.%s",
                Long.parseLong(ipSegment1, 2),
                Long.parseLong(ipSegment2, 2),
                Long.parseLong(ipSegment3, 2),
                Long.parseLong(ipSegment4, 2));
    }

    private static void ip2Long(String ip) {
        String[] ipSegment = ip.split("\\.");
        StringBuilder binIp = new StringBuilder();
        for (String ipSg : ipSegment) {
            String ipBin = Integer.toBinaryString(Integer.parseInt(ipSg));
            binIp.append(String.format("%8s", ipBin).replace(" ", "0"));
        }
        System.out.println(Long.parseLong(binIp.toString(), 2));
    }
}
