package dataStructure.list;

import java.util.ArrayList;
import java.util.List;

public class Mtest {

    public static void main(String[] args) {
        SeqList<String> seqList = new SeqList<>();
        Integer[] inta = {10,20,30,50,90,40};
        SeqList<Integer> seqListInt = new SeqList<>(inta);
        SeqList<String> seqListStr = new SeqList<>(12);

        System.out.println( seqList.size());
        System.out.println( seqListInt.size());
        System.out.println( seqListStr.size());

       List<String> strs = new ArrayList<>();
       strs.add("a");
       strs.add("b");
       strs.add("c");
       strs.add("d");
       strs.add("e");
       strs.add("f");
       strs.add("g");
       seqList.addAll(strs);
        for (Object o : seqList.element) {
            System.out.println(o);
        }
       System.out.println(seqList.search("a的位置为：" + "a"));
    }
}
