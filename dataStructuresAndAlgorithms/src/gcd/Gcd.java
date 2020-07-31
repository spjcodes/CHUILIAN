package gcd;


import java.util.HashMap;
import java.util.Map;

/**
 * 求两个数的最大公约数
 *
 * eg: 求 120  34 的最大公约数
 *设其中一个数为a，另一个为b有如下表示：
 *
 *    1.更相减损术
 *        第一步：任意给定两个正整数；判断它们是否都是偶数。若是，则用2约简；若不是则执行第二步。
 *
 *        第二步：以较大的数减较小的数，接着把所得的差与较小的数比较，并以大数减小数。继续这个操作，
 *        直到所得的减数和差相等为止。
 *
 *        第一步中约掉的若干个2的积与第二步中等数的乘积就是所求的最大公约数。
 *
 *        gcd(120,34) --> gcd(86, 34) -->gcd(52,34) -->dcd(34,18) -->dcd(18,16) --> gcd(16,2)
 *        --> gcd(14,2) -->gcd(12,2) -->gcd(10,2) -->gcd(8,2) -->gcd(6,2) -->gcd(4,2) -->
 *        gcd(2,0)
 *
 *    2.碾转相除法
 *    把a和b交换位置，b的值为a除以b的余数，
 *    如果余数为零则a的值就是最大公约数，
 *    否则重复上述步骤
 *
 *    gcd(a, b)
 *    temp = a%b
 *    a = b
 *    b = temp
 *    until a%b = 0 break
 *
 *        gcd(120, 34) --> gcd(34, 18) --> gcd(18,16) -->
 *        gvd(16, 2) -->  gcd(2, 8) --> gcd(8,12) --> gcd(12, 8)
 *        --> gcd(8,4) --> gcd(4,2) --gcd(2, 0)
 *
 *
 */
public class Gcd {


    public static void main(String[] args) {
        System.out.println(gcd1(120,34));
    }



    public static int gcd(int a, int b) {
        //被2整除的次数
        int counta = 0;
        int countb = 0;

        if (a%2 == 0 && b%2==0) {
            counta = count(a).get("count");
            countb = count(b).get("count");
            counta = counta>countb?countb:counta;
            a = count(a).get("remainder");
            b = count(b).get("remainder");
        } else if(a!=0&&b!=0) {
            int temp = 0;
            b = a<b?a:b;
            a = a>b?a:b;

            while((temp=Math.abs(a-b))!=0 || (temp=Math.abs(a-b))!=b) {
                a = a<b?a:b;
                b = temp;
                temp = Math.abs(a-temp);
            }
            a = a!=0?a:b;
            //证明其能被2整除，将a值赋为1，否则返回时的结果乘积为零
            a= a==0&&b==0?1:a;
        }

        return a * (int)Math.pow(2,counta);
    }


    public static Map<String,Integer> count(int i) {
        int temp;

        int count = 0;
        int remainder = 1;
        Map m = new HashMap();

        while ((temp = i%2)==0){
            count ++;
            if (temp==0) {
                remainder = i%((int) Math.pow(2,count));
                m.put("remainder", remainder);
                m.put("count", count);
                return m;
            }

        }
        return m;
    }


    public static int gcd1(int num1, int num2) {
        //设gcd(a,b) 其中a为大数，b为小数
        int a = num1;
        int b = num2;
        if (num1<num2) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int temp;
        while((temp=num1%num2) != 0) {
            num1 = num2;
            num2 = temp;
        }

        if ((num1 = num2!=0?num2:num1) ==1 )
            return a*b;
        return num1;
    }
}
