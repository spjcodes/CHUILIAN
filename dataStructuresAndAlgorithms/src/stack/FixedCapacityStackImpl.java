package stack;

/**
 * stack数据类型实现
 * @param <item> 数据类型
 * 以下代码功能实现一个固定长度的stack
 * 存在的问题：
 *              其长度固定，既内存空间的占用是固定的，
 *              如果存储的内容过小而数组长度较大时其就会浪费很多的内存空间
 *              可用自动变长的stack来实现性能优化
 */
public class FixedCapacityStackImpl<item> {

    private item[] a;
    private int n;


    public static void main(String[] args) {
        FixedCapacityStackImpl<String> test = new FixedCapacityStackImpl();
        test.intiStack(10);
        test.push("today");
        test.push("is");
        test.push("a");
        test.push("luck");
        test.push("day");

        System.out.println(test.isEmpty());
        System.out.println(test.peek());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.peek());

    }

    public void intiStack(int size) {
        a =  (item[]) new Object[size];
    }



     Object pop() {
        if (!isEmpty()) {
            item temp = a[n];
            a[n--] = null;
            return temp;
        }
        return null;
    }

     boolean push(item i) {
        if (!isFull()){
            a[++n] = i;
            return true;
        }
        return false;
    }

     boolean isFull() {
        boolean flag = a.length == n ? true : false;
        return flag;
    }

     boolean isEmpty() {

        return a.length == 0;
    }

     Object peek() {
        return a[n];
    }
}
