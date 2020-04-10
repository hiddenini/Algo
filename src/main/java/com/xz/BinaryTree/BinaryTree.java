package com.xz.BinaryTree;

/**
 * @author xz
 * @date 2020/4/8 9:44
 **/


import com.xz.BinaryTree.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树基类,提供二叉树的公共方法
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
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

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root=null;
        size=0;
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
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
     *          --将队头节点A出队并访问
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

    /**
     * 获取节点的前驱节点
     * @param node
     * @return
     */
    public Node<E>  predecessor(Node<E> node){
        if (node==null) return  null;
        /**
         * 如果节点的左子树不为空，则找到左节点，一直往右，直到为空
         */
        Node<E> p = node.left;
        if (p!=null){
            while (p.right!=null){
                p=p.right;
            }
            return p;
        }

        while (node.parent!=null && node==node.parent.left){
            node=node.parent;
        }
        //node.parent==null or node=node.parent.right;两种情况都是直接返回node.parent
        return node.parent;
    }

    /**
     * 获取节点的后继节点
     * @param node
     * @return
     */
    public Node<E>  successor(Node<E> node){
        if (node==null) return  null;
        /**
         * 如果节点的右子树不为空，则找到右节点，一直往左，直到为空
         */
        Node<E> p = node.right;
        if (p!=null){
            while (p.left!=null){
                p=p.left;
            }
            return p;
        }

        while (node.parent!=null && node==node.parent.right){
            node=node.parent;
        }
        //node.parent==null or node=node.parent.left;两种情况都是直接返回node.parent
        return node.parent;
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
        Node<E> treeNode=((Node<E>)node);
        if (treeNode==root){
            return ((Node<E>)node).element+"_p(null)";
        }
        return ((Node<E>)node).element+"_p("+((Node<E>)node).parent.element+")";
    }
}
