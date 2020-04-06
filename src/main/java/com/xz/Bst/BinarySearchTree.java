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
        public boolean isLeaf(){
            return left==null&&right==null;
        }

        public boolean hasTwoChildren(){
            return left!=null&&right!=null;
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

    public int height(){
        //return  heightRecursion(root);
        return heightNotRecursion();
    }

    /**
     * 获取节点的高度(递归形式)
     */
    private int heightRecursion(Node<E> node){
        if (node==null) return 0;
        return 1+Math.max(heightRecursion(node.left),heightRecursion(node.right));
    }

    /**
     * 获取节点的高度(非递归形式),利用层序遍历
     */
    private int heightNotRecursion(){
        if(root==null ) return 0;
        int height=0;
        //每一层的高度,默认为1(第一层只有根节点)
        int levelSize=1;
        Queue<Node<E>> queue=new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (! queue.isEmpty()){
            //头结点出队
            Node<E> node=queue.poll();
            levelSize--;
            //System.out.println(node.element);
            if(node.left!=null){
                queue.offer(node.left);
            }
            if(node.right!=null){
                queue.offer(node.right);
            }
            //每一层访问完之后下一层的节点数量等于队列的size
            if(levelSize==0){
                levelSize=queue.size();
                height++;
            }
        }
        return height;
    }

        /**
         * 判断是否是完全二叉树(利用层序遍历)
         *  这里有一个bug 比如下面这种就会认为是完全二叉树原因是
         *  if(node.left!=null&&node.right!=null) 这里是一起判断的。比图节点4 是有左节点的，但是没有右节点，不会入队
         *     ┌──7──┐
         *     │     │
         *   ┌─4     9
         *   │
         * ┌─2
         * │
         * 1
         */
    public boolean isCompleteOld(){
        if(root==null) return false;
        Queue<Node<E>> queue=new LinkedList<>();
        //根节点入队
        queue.offer(root);
        boolean leaf=false;
        while (! queue.isEmpty()){
            //头结点出队
            Node<E> node=queue.poll();
            if(leaf&& !node.isLeaf()){
                return false;
            }
            if(node.left!=null&&node.right!=null){
                queue.offer(node.left);
                queue.offer(node.right);
            }else if(node.left==null&&node.right!=null){
                return false;
            }else{
                //剩下还有2种情况,左右都为空,或者左为空，右不为空,这2种情况都需要保证后续的所有节点都是叶子节点
                leaf=true;

            }
        }
        return true;
    }

    public boolean isComplete(){
        if(root==null) return false;
        Queue<Node<E>> queue=new LinkedList<>();
        //根节点入队
        queue.offer(root);
        boolean leaf=false;
        while (! queue.isEmpty()){
            //头结点出队
            Node<E> node=queue.poll();
            if(leaf&& !node.isLeaf()){
                return false;
            }
            if(node.left!=null){
                queue.offer(node.left);
            }else if(node.right==null){
                //即node.left==null && node.right==null
                return  false;
            }
            if(node.right!=null) {
                queue.offer(node.right);
            }else{
                //即node.right==null 此时不管是左节点为空还是不为空，都代表后续的所有节点都应该是叶子节点
                leaf=true;
            }

        }
        return true;
    }
}
