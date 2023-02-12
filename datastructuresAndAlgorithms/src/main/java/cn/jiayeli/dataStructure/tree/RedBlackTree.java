package cn.jiayeli.dataStructure.tree;

/**
 * @author: jiayeli.cn
 * @description
 * @date: 2023/2/12 下午9:09
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    RBTNode<K, V> root;
    private final static boolean RED = true;
    private final static boolean BLACK = false;

    public void put( K key, V val)
    { //
        root = put(root, key, val);
        root.color = BLACK;
    }
//    查找 ，找到则更新其值，否则为它新建一个结点
    private RBTNode<K, V> put(RBTNode<K, V> h, K  key, V val)
    {
        if (h == null) //
            return new RBTNode<K, V>(key, val, 1, RED);
//        标准的插入操作，和父结点用红链接相连
        int cmp = -h.key.compareTo(key);
        if
        (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.value = val;
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))  flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;return h;
    }

    private RBTNode<K, V> rotateLeft(RBTNode<K, V> h) {

        return null;
    }

    private RBTNode<K, V> rotateRight(RBTNode<K, V> h) {
        return null;
    }

    private int size(RBTNode<K, V> h) {
        return 0;
    }

    private void flipColors(RBTNode<K, V> h) {

    }

    private boolean isRed(RBTNode<K, V> x) {
//        return x == null ? false : x.color == RED;
        return x != null && x.color == RED;
    }

}

class RBTNode<K extends Comparable<K>, V> {

    K key;
    V value;
    //color == null ? red : black
    boolean color;
    //nodes number
    int N;
    RBTNode<K, V> left;
    RBTNode<K, V> right;

    RBTNode(K key, V value, int N, boolean color) {
        this.key = key;
        this.value = value;
        this.N = N;
        this.color = color;
    }

}