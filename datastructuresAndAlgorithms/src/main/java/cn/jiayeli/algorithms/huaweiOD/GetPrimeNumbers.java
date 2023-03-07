package cn.jiayeli.algorithms.huaweiOD;

import java.util.Scanner;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/3/7 下午1:27
 */
public class GetPrimeNumbers {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();
        int x = s;
        int y = 2;
        StringBuilder sb = new StringBuilder("");
        while(x>1){
            if(x%y==0){
                x=x/y;
                sb.append(y+" ");
            }else{
                // 不超时的关键代码
                if(y>x/y){
                    y = x;
                }
                else {
                    y++;
                }
            }
        }
        System.out.println(sb.toString());
    }
}

