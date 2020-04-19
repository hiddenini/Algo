package com.xz.Map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings({"unchecked","unused"})
public class TreeMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    protected int size;
    protected Node<K, V> root;
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);
        // 添加第一个节点
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;
            // 新添加节点之后的处理s
            afterPut(root);
            return null;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等,如果是基础数据类型可以什么都不做,如果是自定义数据(Person)是否需要覆盖，看你自己吧
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 新添加节点之后的处理
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node!=null?node.value:null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key)!=null;
    }

    @Override
    public boolean containsValue(V value) {
        /**
         *层序遍历
         */
        if (root== null) return false;
        Queue<Node<K,V>> queue=new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<K, V> node = queue.poll();
            if (valEquals(value,node.value)) return true;
            if (node.left!=null){
                queue.offer(node.left);
            }
            if (node.right!=null){
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor==null) return;
        traversal(root,visitor);
    }

    public void traversal(Node<K,V> node,Visitor<K, V> visitor) {
        if (node==null || visitor.stop) return;
        traversal(node.left,visitor);
        if (visitor.stop) return;
        visitor.visit(node.key,node.value);
        traversal(node.right,visitor);
    }

    private static class Node<K, V> {
        Boolean color = RED;
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 节点的兄弟节点
         *
         * @return
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            //如果是root节点,则没有兄弟节点
            return null;
        }

    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        //如果是root节点，则染黑
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色,则直接return(4种)
        if (isBlack(parent)) {
            return;
        }
        //uncle节点
        Node<K, V> uncle = parent.sibling();
        //grand
        Node<K, V> grand = parent.parent;
        //如果uncle是红色(4种)
        if (isRed(uncle)) {
            //父节点和叔父节点染黑
            black(parent);
            black(uncle);
            //把祖父节点当做新添加的节点
            afterPut(red(grand));
            return;
        }
        //叔父节点不是红色(4种)
        if (parent.isLeftChild()) {//L
            //grand染成红色
            red(grand);
            if (node.isLeftChild()) {//LL
                //parent染成黑色
                black(parent);
            } else {//LR
                //自己染成黑色
                black(node);
                rotateLeft(parent);
            }
            //grand右旋转
            rotateRight(grand);
        } else {//R
            //grand染成红色
            red(grand);
            if (node.isLeftChild()) {//RL
                //自己染成黑色
                black(node);
                rotateRight(parent);
            } else {//RR
                //parent染成黑色
                black(parent);
            }
            //grand左旋转
            rotateLeft(grand);
        }
    }

    /**
     * 对节点进行染色，并返回该节点
     *
     * @param node
     * @param color
     * @return
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    /**
     * 将节点染成红色
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 返回节点的颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 节点是否是黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点是否是红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * g──┐
     * │
     * p──┐
     * │
     * n
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * ┌──g
     * │
     * ┌─p
     * │
     * n
     *
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 旋转之后需要修改parent,更新高度
     *
     * @param grand
     * @param parent
     * @param child
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            root = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;


    }

    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<K>) e1).compareTo(e2);
    }


    /**
     * 根据元素找到对应的节点
     * @param key
     * @return
     */
    private Node<K, V> node(K key){
        Node<K, V> node=root;
        while (node!=null){
            int cmp=compare(key,node.key);
            if (cmp==0) return node;
            //如果该element比当前节点的element要大，则往右边找
            if (cmp>0){
                node=node.right;
            }else{
                //cmp<0
                node=node.left;
            }
        }
        return null;
    }

    private V remove(Node<K, V> node){
        if (node==null) return null;;
        V oldValue = node.value;
        size--;
        //度为2的节点
        if (node.hasTwoChildren()){
            //找到后继节点
            Node<K, V>  s=successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.key=s.key;
            node.value=s.value;
            //删除后继节点
            node=s;
        }
        //删除node节点(node的度必然是1或者0)
        Node<K, V> replace=node.left!=null?node.left:node.right;

        if (replace!=null) {
            //node为度为1的节点
            //更改parent
            replace.parent=node.parent;
            //更改parent的left or right的指向
            if (node.parent==null){
                //node是度为1的节点并且是根节点
                root=replace;
            }else if(node==node.parent.left){
                node.parent.left=replace;
            }else {
                node.parent.right=replace;
            }
            //删除节点之后的处理,node被删除时node的parent指针还是存在的
            afterRemove(node,replace);
        }else if(node.parent==null){
            //node为度为0的节点,且为根节点
            root=null;
            //删除节点之后的处理
            afterRemove(node,null);
        }else{
            //node为度为0的节点,但不是根节点
            if (node==node.parent.left){
                node.parent.left=null;
            }else{
                node.parent.right=null;
            }
            //删除节点之后的处理
            afterRemove(node,null);
        }
        return oldValue;
    }

    protected void afterRemove(Node<K, V> node, Node<K, V> replace) {
        //如果被删除的节点是红色
        if (isRed(node)) return;

        //用于取代node的节点是红色
        if (isRed(replace)) {
            black(replace);
            return;
        }
        Node<K, V> parent = node.parent;
        //删除根节点
        if (parent == null) return;
        ;

        //删除的黑色叶子节点
        /**
         * 这种判断是错误的,因为如果来到这里说明删除的一定是叶子节点，所以parent的left或者right被置为null了
         *
         * 可以通过判断被删除的node是左还是右
         */
        //Node<E> sibling = node.sibling();
        //可能是叶子节点，也可能是持续下溢的节点,所以2个判断
        Boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { //和else完全对称(方向)
            //被删除节点在左边,兄弟节点在右边
            if (isRed(sibling)) {
                //兄弟节点是红色,要转换成黑色的情况
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟节点
                sibling = parent.right;
            }
            //能来到这里，说明兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点,父节点要向下跟兄弟节点合并(下溢)
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    //持续下溢
                    afterRemove(parent, null);
                }
            } else {
                //至少有一个红色子节点,需要向兄弟节点借元素
                if (isBlack(sibling.right)) {
                    //先对兄弟进行左旋转(时期演变成后面两种情况)
                    rotateRight(sibling);
                    //重要,需要切换兄弟节点
                    sibling=parent.right;
                }
                //继承parent的颜色
                color(sibling,colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }
        } else {
            //被删除节点在右边,兄弟节点在左边
            if (isRed(sibling)) {
                //兄弟节点是红色,要转换成黑色的情况
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟节点
                sibling = parent.left;
            }
            //能来到这里，说明兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点,父节点要向下跟兄弟节点合并(下溢)
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    //持续下溢
                    afterRemove(parent, null);
                }
            } else {
                //至少有一个红色子节点,需要向兄弟节点借元素
                if (isBlack(sibling.left)) {
                    //先对兄弟进行左旋转(时期演变成后面两种情况)
                    rotateLeft(sibling);
                    //重要,需要切换兄弟节点
                    sibling=parent.left;
                }
                rotateRight(parent);
                //继承parent的颜色
                color(sibling,colorOf(parent));
                black(sibling.left);
                black(parent);

            }

        }

    }

    /**
     * 获取节点的后继节点
     *
     * @param node
     * @return
     */
    private Node<K, V>  successor(Node<K, V>  node) {
        if (node == null) return null;
        /**
         * 如果节点的右子树不为空，则找到右节点，一直往左，直到为空
         */
        Node<K, V>  p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        //node.parent==null or node=node.parent.left;两种情况都是直接返回node.parent
        return node.parent;
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

}
