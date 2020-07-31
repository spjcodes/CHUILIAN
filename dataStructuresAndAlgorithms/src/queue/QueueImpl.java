package queue;

import linkTable.Node;

/**
 *链式队列的实现
 *
 * 队列 queue在java中表示一只能在其头部进行数据获取（get, delete），
 * 在其尾部进行数据添加的数据结构。
 * 其特点为：先进先出（FIFO）
 *      可要链表或者数组来进行实现
 */
public class QueueImpl {
    private Node head;
    private Node tail;

    public static void main(String[] args) {
        QueueImpl queue = new QueueImpl();

        Node headNode = queue.initAndGetQueue();
        headNode.setItem("to");

        queue.addElement(new Node("do"));
        queue.addElement(new Node("or"));
        queue.addElement(new Node("not"));
        queue.addElement(new Node("to"));
        queue.addElement(new Node("do"));
        System.out.println("队列元素个数为：" + queue.size());

        System.out.println(queue.getHead().getItem());
        System.out.println(queue.getHead().getItem());
        System.out.println(queue.getHead().getItem());
        System.out.println(queue.getHead().getItem());
        System.out.println(queue.getHead().getItem());
        System.out.println(queue.getHead().getItem());

        System.out.println("队列元素个数为：" + queue.size());
        System.out.println(queue.getHead().getItem());

    }

    public Node initAndGetQueue() {
        Node<String> q = new Node();
        this.head = q;
        return head;
    }

    public boolean addElement(Node<String> node) {
        if (head.getNextNode() == null) {
            tail = node;
            head.setNextNode(tail);
            return true;
        } else {
            tail.setNextNode(node);
            tail = tail.getNextNode();
        }

        return false;
    }

    public Node getHead() {
        if (isEmpty()) {
            System.out.println("队列为空！出队失败！");
            return new Node();
        }
        Node oldHead = head;
        head = head.getNextNode();
        if (oldHead != null){
            System.out.println("出队元素为:" + oldHead.getItem() + ":" + oldHead.getNextNode());
            return oldHead;
        }
        return null;
    }

    public int size() {
        int count  = 0;
        Node temp = head;
        try {
            while (temp.getNextNode() != null || temp == tail) {
                count ++;
                System.out.println(temp.getItem());
                temp = temp.getNextNode();
            }
        } catch (NullPointerException e) {
            /**
             *  tail的nextNode为空，
             * 会报NullPointerException
             * 捕获不做处理即可
             */
        }
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }



}
