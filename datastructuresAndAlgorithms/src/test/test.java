import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {

    public static void main(String[] args) {
      StuScore stu = new test().new StuScore(10);
      stu.disp();
        System.out.println(stu.avg());




    }


    //1. 定义一个方法，可以接收一个类型为整数数组的参数，并能打印出数组中最大的两个元素
    public static void maxNum(int[] arr) {
        int maxa = arr[0]>arr[1] ? arr[0] : arr[1];
        int maxb = arr[0]<arr[1] ? arr[0] : arr[1];
        for(int i=2; i<arr.length; i++) {
          if (arr[i]>maxb){
              maxb = arr[i];
              if(maxb>maxa){
                  int temp = maxa;
                  maxa = maxb;
                  maxb = temp;
              }
          }
        }
        System.out.println(maxa + "\t" + maxb);
    }


    /**
     * 2. 用java写一个从整数数组中查找特定值所在位置的二分查找方法
     * （输入参数：整数数组，目标值）
     * （返回结果：目标值在数组中的脚标，如果不存在则返回-1）
     */
    public static int binsearch(int[] arr, int key) {

        int left = 0;
        int right = arr.length - 1;

        while(left <= right) {
            int mid = (right + left) / 2;
            if(arr[mid] == key)
                return mid;
            else if (arr[mid] < key)
                left = mid + 1;
            else if (arr[mid] > key)
                right = mid - 1;
        }
        return -1;
    }



    /**
     * 3. 编写一个程序，输入N个学生数据，包括学号、姓名、成绩，要求输出这些学生数据并计算平均分。 
     * 思路：设计一个学生类Stud，除了包括no(学号)、name(姓名)和deg(成绩)数据成员外，有两个静态变量sum和num，
     * 分别存放总分和人数，另有成员函数disp()，用于输出数据成员的值，另有一个静态成员函数avg()，
     * 它用于计算平均分。在main()函数中定义了一个对象数组用于存储输入的学生数据。
     */
    class Stu{
        int no;
        String name;
        double score;

        Stu() {

        }

        public Stu(int no, String name, double score) {
            this.no = no;
            this.name = name;
            this.score = score;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Stu{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    class StuScore{
        Scanner sc = new Scanner(System.in);
        List<Stu> stuList = new ArrayList<Stu>();

        StuScore(int n) {
            while(n-- > 0) {
                Stu stu = new Stu();
                System.out.println("请输入学生学号");
                stu.setNo(sc.nextInt());
                System.out.println("请输入学生姓名");
                stu.setName(sc.next());
                System.out.println("请输入学生成绩");
                stu.setScore(sc.nextDouble());
                stuList.add(stu);
            }
        }

        double avg() {
            double sum = 0;
            for (Stu stu : stuList) {
                sum += stu.getScore();
            }
            return sum/stuList.size();
        }

        void disp() {
            stuList.forEach(stu -> {
                System.out.println(stu.toString());
            });
        }
    }


    /**
     * 4. 设计一个BankAccount类，实现银行某账号的资金往来账目管理，包括建账号、存入、取出等。
     * BankAccount类包括，账号（BankAccountId）、开户日期Date(日期)，Money(金额)，Rest(余额)。
     * 另有一个构造函数和三个成员函数Bankin()(处理存入账)，
     * Bankout()处理取出账)和和一个负责生成账号的自动增长的函数。
     */




    /**
     * 5. 编写一个控制台应用程序，完成下列功能，并写出运行程序后输出的结果
     *    1) 创建一个类A，在A中编写一个可以被重写的带int类型参数的方法MyMethod，并在该方法中输出传递的整型值加10后的结果。 
     *    2) 再创建一个类B，使其继承自类A，然后重写A中的MyMethod方法，将A中接收的整型值加50，并输出结果。 
     *    3) 在Main方法中分别创建类A和类B的对象，并分别调用MyMethod方法。
     */
}



