package linkTable;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * 链表数据结构的实现
 */
public class LinkTable implements Iterable{

    Node<Integer> three = new Node(18, null);
    Node<String> second = new Node("be", three);
    Node<String> first = new Node("to", second);

    public void addNodeInFirst(Node node){
        Node oldNode = first;
        first = new Node<>("addTest", oldNode);
    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
