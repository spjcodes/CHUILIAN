package cn.jiayeli.dataStructure.tree;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author: jiayeli.cn
 * @description
 *                                   *
 *               8                   *
 *             /  \                  *
 *            5    10                *
 *           / \    \                *
 *          1   7    11              *
 *                                   *
 *  inorder: 1 5 7 8 10 11           *
*  一棵二叉查找树（BST）是一棵二叉树，其中每个结点都含有一个 Comparable 的键（以及相关联的值）
 * 且每个结点的键都大于其左子树中的任意结点的键而小于右子树的任意结点的键*
 * @date: 2023/2/7 下午5:34
 */
public class BinarySearchTree<T extends Comparable<T>> {
    BSTNode<T> root;
    public void add(BSTNode<T> node) {
        if (this.root == null) {
            this.root = node;
        } else {
           add(this.root, node);
        }

    }

    private void add(BSTNode<T> root, BSTNode<T> node) {
        if (node.getValue().compareTo(root.value) > 0) {
            if (root.getNextNode() == null) {
                root.setNextNode(node);
            } else {
                add(root.getNextNode(), node);
            }
        } else {
            if (root.getPreNode() == null) {
                root.setPreNode(node);
            } else {
                add(root.getPreNode(), node);
            }
        }
    }

   /* private void add(BSTNode<T> parentNode, BSTNode<T> currentNode, BSTNode<T> node) {
        if (currentNode.value.compareTo(node.value) < 0) {
           // cur < node
            BSTNode<T> nextNode = currentNode.getNextNode();
            if (nextNode == null) {
                currentNode.setNextNode(node);
            } else if (nextNode.value.compareTo(node.value) > 0) {
                // cur < node < next
                replaceCurNextNode(currentNode, nextNode, node);
            } else {
                //  cur < next <  node
                this.add(currentNode, nextNode, node);
            }
        } else {
            //cur > node
            BSTNode<T> preNode = currentNode.getPreNode();
            if (preNode == null) {
                currentNode.setPreNode(node);
            } else if (preNode.value.compareTo(node.value) < 0) {
                //pre < node < cur
                replaceCurPreNode(currentNode, preNode, node);
            } else {
                //  node < pre < cur
                this.add(currentNode, preNode, node);
            }
        }

    }
*/
    /**
     *       10
     *   5        16
     * 1   8  13       18
     * add: 6
     * 4[5,]*
     *   *    *
     * @param currentNode
     * @param nextNode
     * @param node
     */
    private void replaceCurNextNode(BSTNode<T> currentNode, BSTNode<T> nextNode, BSTNode<T> node) {
       node.setNextNode(nextNode);
       node.setPreNode(nextNode.preNode);
       currentNode.setNextNode(node);
        if (currentNode.value.equals(node.value)) {
            this.root = node;
        }
    }

    private void replaceCurPreNode(BSTNode<T> currentNode, BSTNode<T> preNode, BSTNode<T> node) {
        node.setPreNode(preNode.preNode);
        node.setNextNode(preNode);
        currentNode.setPreNode(node);
        if (currentNode.getValue().equals(node.value)) {
            this.root = node;
        }

    }

    public void display() {
        if (this.root != null) {
            System.out.printf("root: %s\n", root.getValue());
            display(this.root);
        } else {
            System.out.println(this.root);
        }
    }

    private void display(BSTNode<T> root) {
        BSTNode<T> preNode = root.getPreNode();
        BSTNode<T> nextNode = root.getNextNode();
        System.out.printf("left: %s\t", preNode != null ? preNode.getValue() : "null");
        System.out.printf("right: %s\t\t", nextNode!= null ? nextNode.getValue() : "null");
        if (preNode != null) {
            display(preNode);
        }
        if (nextNode != null) {
            display(nextNode);
        }
    }

    T result;
    public T search(T target) {
        if (this.root.getValue() == target) {
            result = root.getValue();
        } else {
            result = search(this.root, target);
        }
        if (result == null) {
            System.out.printf("not search element:[%s] by the collect!!", target);
        }
        return result;
    }

    private T search(BSTNode<T> root, T target) {
        if (root.getValue().compareTo(target) == 0) {
            return root.getValue();
        } else if (root.getValue().compareTo(target) > 0) {
            if (root.getPreNode() == null) {
                return null;
            }
            result = search(root.preNode, target);
        } else {
            if (root.getNextNode() == null) {
                return null;
            }
            result = search(root.getNextNode(), target);
        }
        return result;
    }
}

class BSTNode<T extends Comparable<T>> {

    public T value;
    public BSTNode<T> preNode;
    public BSTNode<T> nextNode;

    BSTNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BSTNode<T> getPreNode() {
        return preNode;
    }

    public void setPreNode(BSTNode<T> preNode) {
        this.preNode = preNode;
    }

    public BSTNode<T> getNextNode() {
        return nextNode;
    }

    public void setNextNode(BSTNode<T> nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public String toString() {
        return "\nBSTNode{" +
                "value=" + value +
                ", preNode=" + preNode +
                ", nextNode=" + nextNode +
                '}';
    }
}

class BST_TestCase {

    @Test
    public void testCase() {
        List<Integer> unOrderDataSet = Arrays.asList(1, 5, 7, 6, 4, 10, 8, 11);
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        unOrderDataSet.forEach(e -> {
            bst.add(new BSTNode<>(e));
        });

        bst.display();
        System.out.println();
        System.out.println(bst.search(6));
        System.out.println(bst.search(20));
    }

}
