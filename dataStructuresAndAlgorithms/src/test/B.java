package test;

public class B extends A {

    static {
        System.out.println("b");
    }

    B() {
        System.out.println("b constructor");
    }

    public static void main(String[] args) {
        A a = new A();
        System.out.println("******************");
        B b = new B();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
        a.myMthod(10);
        System.out.println("............");
        b.myMthod(10);
    }

    @Override
    public int myMthod(int i) {
        System.out.println(super.myMthod(i) + 50); //20 70
        return super.myMthod(i) + 50;  //20
    }
}
