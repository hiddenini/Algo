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
        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            //如果是root节点,则没有兄弟节点
            return null;
        }

        @Override
        public String toString() {
            String parentString = "null";
            if (parent != null) {
                parentString = parent.element.toString();
            }
            return element + "_p(" + parentString + ")";
        }
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

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 前序遍历
     */
    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    public void preOrderTraversal(Node<E> node) {
        if (node == null) return;
        System.out.println(node.element);
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }

    /**
     * 中序遍历
     */
    public void inOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrderTraversal(root,visitor);
    }

    public void inOrderTraversal(Node<E> node,Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        inOrderTraversal(node.left,visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
        inOrderTraversal(node.right,visitor);
    }

    /**
     * 后序遍历
     */
    public void postOrderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        postOrderTraversal(root,visitor);
    }

    public void postOrderTraversal(Node<E> node,Visitor<E> visitor) {
        if (node == null || visitor.stop) return;
        postOrderTraversal(node.left,visitor);
        postOrderTraversal(node.right,visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(node.element);
    }


    /**
     * 层序遍历
     * 1--将根节点入队
     * 2--循环执行以下操作
     * --将队头节点A出队并访问
     * --将A的左节点入队
     * --将A的右节点入队
     */
    public void levelOrderTraversal() {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //头结点出队
            Node<E> node = queue.poll();
            System.out.println(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }

    public static  abstract class Visitor<E> {
        boolean stop;
        public abstract boolean visit(E element);
    }

    /**
     * 带有访问接口的层序遍历
     *
     * @param visitor
     */
    public void levelOrderTraversal(Visitor visitor) {
        if (root == null && visitor == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //头结点出队
            Node<E> node = queue.poll();
            visitor.visit(node.element);
            //System.out.println(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

    }

    public int height() {
        //return  heightRecursion(root);
        return heightNotRecursion();
    }

    /**
     * 获取节点的高度(递归形式)
     */
    private int heightRecursion(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(heightRecursion(node.left), heightRecursion(node.right));
    }

    /**
     * 获取节点的高度(非递归形式),利用层序遍历
     */
    private int heightNotRecursion() {
        if (root == null) return 0;
        int height = 0;
        //每一层的高度,默认为1(第一层只有根节点)
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        //根节点入队
        queue.offer(root);
        while (!queue.isEmpty()) {
            //头结点出队
            Node<E> node = queue.poll();
            levelSize--;
            //System.out.println(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
            //每一层访问完之后下一层的节点数量等于队列的size
            if (levelSize == 0) {
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * 判断是否是完全二叉树(利用层序遍历)
     * 这里有一个bug 比如下面这种就会认为是完全二叉树原因是
     * if(node.left!=null&&node.right!=null) 这里是一起判断的。比图节点4 是有左节点的，但是没有右节点，不会入队
     * ┌──7──┐
     * │     │
     * ┌─4     9
     * │
     * ┌─2
     * │
     * 1
     */
    public boolean isCompleteOld() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        //根节点入队
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            //头结点出队
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) {
                return false;
            }
            if (node.left != null && node.right != null) {
                queue.offer(node.left);
                queue.offer(node.right);
            } else if (node.left == null && node.right != null) {
                return false;
            } else {
                //剩下还有2种情况,左右都为空,或者左为空，右不为空,这2种情况都需要保证后续的所有节点都是叶子节点
                leaf = true;

            }
        }
        return true;
    }

    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        //根节点入队
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            //头结点出队
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) {
                return false;
            }
            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right == null) {
                //即node.left==null && node.right==null
                return false;
            }
            if (node.right != null) {
                queue.offer(node.right);
            } else {
                //即node.right==null 此时不管是左节点为空还是不为空，都代表后续的所有节点都应该是叶子节点
                leaf = true;
            }

        }
        return true;
    }

    /**
     * 获取节点的前驱节点
     *
     * @param node
     * @return
     */
    public Node<E> predecessor(Node<E> node) {
        if (node == null) return null;
        /**
         * 如果节点的左子树不为空，则找到左节点，一直往右，直到为空
         */
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //node.parent==null or node=node.parent.right;两种情况都是直接返回node.parent
        return node.parent;
    }

    /**
     * 获取节点的后继节点
     *
     * @param node
     * @return
     */
    public Node<E> successor(Node<E> node) {
        if (node == null) return null;
        /**
         * 如果节点的右子树不为空，则找到右节点，一直往左，直到为空
         *
         * 如果节点有右子树，则该节点的后继节点就是往右子树出发，然后转到右子树的左子树，一直到左子树的左子树为空
         */
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }
        /**
         * 如果节点没有右子树，则向上寻找父节点，直到父节点的左子树等于当前节点，则该父节点就是后继节点
         */
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
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
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }
}
