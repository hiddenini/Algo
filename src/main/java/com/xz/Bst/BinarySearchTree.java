package com.xz.Bst;

import com.xz.Bst.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class BinarySearchTree<E>  implements BinaryTreeInfo {
    private int size;
    private Node<E> root;
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {

    }

    public void add(E element) {
        elementNotNullCheck(element);

        // 添加第一个节点
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等,如果是基础数据类型可以什么都不做,如果是自定义数据(Person)是否需要覆盖，看你自己吧
                node.element=element;
                return;
            }
        }

        // 看看插入到父节点的哪个位置
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    public void remove(E element) {

    }

    public boolean contains(E element) {
        return false;
    }

    /**
     * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        @SuppressWarnings("unused")
        Node<E> parent;
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>)node).element;
    }

    /**
     * 前序遍历
     */
    public  void preOrderTraversal(){
        preOrderTraversal(root);
    }

    public  void preOrderTraversal(Node<E> node){
        if(node==null) return;
        System.out.println(node.element);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    /**
     * 中序遍历
     */
    public  void inOrderTraversal(){
        inOrderTraversal(root);
    }

    public  void inOrderTraversal(Node<E> node){
        if(node==null) return;
        inOrderTraversal(node.left);
        System.out.println(node.element);
        inOrderTraversal(node.right);
    }

    /**
     * 后序遍历
     */
    public  void postOrderTraversal(){
        postOrderTraversal(root);
    }

    public  void postOrderTraversal(Node<E> node){
        if(node==null) return;
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.println(node.element);
    }


    /**
     * 层序遍历
     *      1--将根节点入队
     *      2--循环执行以下操作
     *          --将对头节点A出队并访问
     *          --将A的左节点入队
     *          --将A的右节点入队
     *
     */
    public  void levelOrderTraversal(){
        if(root==null) return;
        Queue<Node<E>> queue=new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (! queue.isEmpty()){
            //头结点出队
            Node<E> node=queue.poll();
            System.out.println(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
        }

    }

    public static interface Vistor<E>{
        void visit(E element);
    }

    /**
     * 带有访问接口的层序遍历
     * @param vistor
     */
    public  void levelOrderTraversal(Vistor vistor){
        if(root==null && vistor==null) return;
        Queue<Node<E>> queue=new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (! queue.isEmpty()){
            //头结点出队
            Node<E> node=queue.poll();
            vistor.visit(node.element);
            //System.out.println(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
        }

    }
}

