package dataStructure.linkedList;

/**
 * 链表中的数据节点
 * @param <item>
 */
public class Node<item> {

    private item item;
    private Node nextNode;

    public Node() {
    }

    public Node(item item) {
        this.item = item;
    }

    public Node(item item, Node nextNode) {
        this.item = item;
        this.nextNode = nextNode;
    }

    public item getItem() {
        return item;
    }

    public void setItem(item item) {
        this.item = item;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
