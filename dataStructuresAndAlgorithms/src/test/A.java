package test;

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
        String str  = "33254";
        System.out.println(str.charAt(3));
    }
}
