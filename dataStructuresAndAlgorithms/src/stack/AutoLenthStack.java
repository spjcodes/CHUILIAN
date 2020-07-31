package stack;

public class AutoLenthStack<item> {

    //初始化并设置默认数组长度为10
    private item[] a = (item[]) new Object[10];
    private int pointer = 0;

    boolean push(item element) {
        if (size() > a.length/2)
            resize(a.length*2);
        try {
            a[pointer++] = element;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    Object pop() {
        int len = size()/4;
        if (pointer < len)
            resize(size()/2);
        item temp = a[pointer];
        a[pointer--] = null;
        return temp;
    }

    private void resize(int len) {
        item[] temp = (item[]) new Object[len];
        for (int i=0; i<size(); i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    boolean isFull() {
        return pointer == a.length;
    }


    boolean isEmpey() {
        return a[0] == null;
    }

    Object peek() {
        return a[pointer];
    }

    int size() {
        int count = 0;
        for (item i: a) {
            if (i != null)
                count ++;
        }

        return count;
    }


    public static void main(String[] args) {
        AutoLenthStack<String> test = new AutoLenthStack();
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");
        test.push("a");

        System.out.println(test.size());

        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());

        System.out.println(test.size());
    }





}
