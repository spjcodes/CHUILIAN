import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class A {

    A () {
        System.out.println("a constructor");
    }
    static{
        System.out.println("a");
    }

    public int myMthod(int n) {
        System.out.println(n + 10);
        return n + 10;
    }

    public static void main(String[] args) {
      /*  String str  = "133254";
        System.out.println(str.charAt(3));
        System.out.println(str.charAt(0));
        System.out.println(Integer.valueOf(10).byteValue());
        // 00001010 00000000 00000011 11000001
        System.out.println(10 << 8);*/
        System.out.println(String.format("%8s%n", Integer.toBinaryString(10)).replace(" ", "0"));
        System.out.println("ip to long:\t" + Integer.parseInt("001010", 2));

    }

    public static String decimalToBinary(long num) {
        String tempResult = Long.toBinaryString(num);
        StringBuilder result = new StringBuilder(tempResult);
        while (result.length() % 8 != 0) {
            // 头部补0
            result.insert(0, "0");
        }
        return result.toString();
    }

    private synchronized static Long ip2long(String ip) {
        Long result = 0l;
        String[] ipSegment = ip.split("\\.");
        //ip格式校验
        if (ipSegment.length != 4) {
            System.out.println("ip 格式错误！！！");
        }
        for (int i =0; i<ipSegment.length; i++) {
            result += Long.valueOf(ipSegment[i].isEmpty() ? "0" : ipSegment[i]) << (8*(3-i)) ;
        }
        return result;
    }



}
