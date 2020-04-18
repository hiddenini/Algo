package com.xz.BinaryTree;

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
    protected void afterRemove(Node<E> node, Node<E> replace) {
        //如果被删除的节点是红色
        if (isRed(node)) return;

        //用于取代node的节点是红色
        if (isRed(replace)) {
            black(replace);
            return;
        }
        Node<E> parent = node.parent;
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
        Node<E> sibling = left ? parent.right : parent.left;
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
        return new RBNode<>(element, parent);
    }

    private static class RBNode<E> extends Node<E> {
        Boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }

}
