package algorithms.Josephus;

import java.util.Scanner;

/**
 * 约瑟夫问题:
 * 一个烈日当空的下午，小强一群人去河边尿尿被抓了，这触犯了一个不成文的法律...
 * 设有number个人，要求所有人站成一圈，从一个人开始报数，设这个人开始的位置为start，数的数为从0到distance，
 * 数到那个就让那个跳河，并从下一个开始报数，直到只有最后一个人可以获得一块钱买辣条，回家找妈妈
 *
 * 算法分析：
 *   1.将所有人赋值就去一个数组，从第start的位置开始循环，
 *   2.直到distance将distance位置的数删除
 *   3.从2.中删除数的下一个位置开始distance重复2的步骤
 *   直到数组只剩一个数据方结束循环
 *
 * eg:
 *   start=3 distance=5
 *
 *   cc 开始数到5的数据删除,并从下一个位置开始数
 *   1  2  3  4  5  6  7  8
 *   aa bb cc dd ee ff gg hh
 *   aa bb cc dd ee ff hh     --> gg
 *   aa bb cc ee ff hh        --> dd
 *   aa cc ee ff hh           --> bb
 *   cc ee ff hh              --> aa
 *   ee ff hh                 --> cc
 *   ee hh                    --> ff
 *   ee                       --> hh
 *
 *
 */

public class Josephus<type> {

    private type[] josephs;


    public static void main(String[] args) {
       Josephus<String> joseph =  new Josephus<String>();
       joseph.Josephus(8, 3, 5);
    }

    /**
     *
     * @param number  总的人数
     * @param start  开始位置
     * @param distance  报数的数
     * @return
     */
    public type Josephus(int number, int start, int distance) {
        if (start>number-1) {
            System.out.println("start位置出错，请重新输入0到" + (number-1) + "的一个数作为开始位置");
            return null;
        }

        setJosephs(number);
        start--;
        while(size()!=1) {
            start = (start + distance - 1)%size();
            System.out.println(josephs[start] + "跳河了");
            //数到的数后面的数前移一位，最后的位置置为空
            for(int i=start; i<size()-1; i++) {
                josephs[i] = josephs[i+1];
            }
            josephs[size()-1] = null;
            for (type joseph : josephs) {
                if (joseph != null)
                    System.out.print(joseph + "\t");
            }
            System.out.println();
        }

        System.out.println(josephs[0] + "得到一块钱，买了包辣条回家找妈妈了");
        return josephs[0];
    }

    public  int size() {
        int count = 0;
        for (type joseph : josephs) {
            if(joseph != null)
                count ++;
        }
        return count;
    }
    /*public type algorithms.Josephus(int number, int start, int distance) {

        if (start > number-1){
            System.out.println("start位置出错，请重新输入0到" + (number-1) + "的一个数作为开始位置");
            return null;
        }

//        josephs = (type[]) new Object[number];
        this.setJosephs(number);
        int point = 0;
      for (;number!=1; number--) {
          if(point==1&&point!=2) {
              start++;
              point = 2;
          }
          if ((start-1)+distance < number)
              start = start + distance;
          else
              start = (start + distance)%number;
          System.out.println(josephs[start] + "跳河了");
          for (int i = start; i< number-1; i++){
              josephs[i] = josephs[i+1];
          }
          point = 1;
      }
        return josephs[point];
    }*/

    public void setJosephs(int size) {
        Scanner scanner = new Scanner(System.in);
        josephs = (type[]) new Object[size];
        int count = 0;
        String inStr;
        while(!(inStr=scanner.next()).equals("end")&&josephs.length>count) {
            josephs[count++] = (type) inStr;
        }

        if (inStr.equals("end")){
            System.out.println("输入的数为：");
            for (type joseph : josephs) {
                System.out.println(joseph);
            }
            return;
        }
        System.out.println(".........................................");
    }
}
