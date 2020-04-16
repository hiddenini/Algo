package com.xz.BinaryTree;

import com.sun.org.apache.regexp.internal.RE;

import java.sql.Blob;
import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
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
        Node<E> uncle = parent.sibling();
        //grand
        Node<E> grand = parent.parent;
        //如果uncle是红色(4种)
        if (isRed(uncle)) {
            //父节点和叔父节点染黑
            black(parent);
            black(uncle);
            //把祖父节点当做新添加的节点
            afterAdd(red(grand));
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

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    /**
     * 对节点进行染色，并返回该节点
     *
     * @param node
     * @param color
     * @return
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }

    /**
     * 将节点染成红色
     *
     * @param node
     * @return
     */
    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     *
     * @param node
     * @return
     */
    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    /**
     * 返回节点的颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 节点是否是黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点是否是红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element,parent);
    }

    private static class RBNode<E> extends Node<E> {
        Boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED){
                str="R_";
            }
            return str+element.toString();
        }
    }

}
