package cn.jiayeli.dataStructure.tree;

import org.junit.jupiter.api.Test;

/**
 * @author: jiayeli.cn
 * @description
 *                                  *
 *              a                   *
 *            /  \                  *
 *           b    c                 *
 *          / \    \                *
 *         d   e    f               *
 *                                  *
 *   preorder:  a b d e c f
 *   inorder:   d b e a c f
 *   postorder: f c e d b a
 * @date: 2023/2/7 下午3:24
 */
public class BinaryTree<T extends Comparable<T>> {

    private Node<T> root;

    BinaryTree() {}

    BinaryTree(Node<T> root) {
        this.root = root;
    }

    public void display() {
        if (this.root == null) {
            System.out.println(this.root);
        } else {
            preorderTraversal(this.root);
        }
    }

    public void preorder() {
        if (this.root == null) {
            System.out.println(this.root);
        } else {
            preorderTraversal(this.root);
        }
    }

    public void inorder() {
        if (this.root == null) {
            System.out.println(this.root);
        } else {
            inorderTraversal(this.root);
        }
    }

    private void inorderTraversal(Node<T> currentNode) {
        if (currentNode != null) {
            inorderTraversal(currentNode.getPreNode());
            System.out.printf("%s\t", currentNode.getValue());
            inorderTraversal(currentNode.getNextNode());
        }
    }

    public void postorder() {
        if (this.root == null) {
            System.out.println(this.root);
        } else {
            postorderTraversal(this.root);
        }
    }

    private void postorderTraversal(Node<T> currentNode) {
        if (currentNode != null) {
            postorderTraversal(currentNode.getNextNode());
            postorderTraversal(currentNode.getPreNode());
            System.out.printf("%s\t", currentNode.getValue());
        }

    }

    private void preorderTraversal(Node<T> currentNode) {
        if (currentNode != null) {
            System.out.printf("%s\t", currentNode.getValue());
            preorderTraversal(currentNode.getPreNode());
            preorderTraversal(currentNode.getNextNode());
        }
    }

}

class Node<T> {
    private Node<T> nextNode;
    private Node<T> preNode;
    private T value;

    Node(T value) {
        this.node(value, null, null);
    }

    public Node(T value, Node<T> preNode) {
        this.node(value, preNode, null);
    }

    Node(T value, Node<T> preNode, Node<T> nextNode) {
        this.node(value, preNode, nextNode);
    }

    private void node(T value, Node<T> preNode, Node<T> nextNode) {
        this.value = value;
        this.preNode = preNode;
        this.nextNode = nextNode;
    }


    public Node<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

    public Node<T> getPreNode() {
        return preNode;
    }

    public void setPreNode(Node<T> preNode) {
        this.preNode = preNode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nextNode=" + nextNode +
                ", preNode=" + preNode +
                ", value=" + value +
                '}';
    }
}

class TestCase{
    /**
     *              a                   *
     *            /  \                  *
     *           b    c                 *
     *          / \    \                *
     *         d   e    f               *
     * preorder: a b d e c f
     * inorder:  d b e a c f*
     * postorder:f c e d b a*
     */
    @Test
    public void testCase() {
        //root
        Node<String> a = new Node<>("a");
        Node<String> b = new Node<>("b");
        Node<String> c = new Node<>("c");
        Node<String> d = new Node<>("d");
        Node<String> e = new Node<>("e");
        Node<String> f = new Node<>("f");
        a.setPreNode(b);
        a.setNextNode(c);
        b.setPreNode(d);
        b.setNextNode(e);
        c.setNextNode(f);
        BinaryTree<String> binaryTree = new BinaryTree<>(a);
        System.out.println("preorder:");
        binaryTree.preorder();
        System.out.println("\ninorder:");
        binaryTree.inorder();
        System.out.println("\npostorder:");
        binaryTree.postorder();

    }
}
