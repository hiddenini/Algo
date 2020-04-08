package com.xz.BinaryTree;

import java.util.Comparator;

/**
 * 二叉搜索树 ,实现特定的add remove逻辑
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class BinarySearchTree<E>  extends BinaryTree<E> {
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
        root=null;
        size=0;
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
        remove(node(element));
    }

    public void remove(Node<E> node) {
        if (node==null) return;;
        size--;
        //度为2的节点
        if (node.hasTwoChildren()){
            //找到后继节点
            Node<E> s=successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.element=s.element;
            //删除后继节点
            node=s;
        }
        //删除node节点(node的度必然是1或者0)
        Node<E> replace=node.left!=null?node.left:node.right;

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
            }else if(node==node.parent.right){
                node.parent.right=replace;
            }

        }else if(node.parent==null){
            //node为度为0的节点,且为根节点
            root=null;
        }else{
            //node为度为0的节点,但不是根节点
            if (node==node.parent.left){
                node.parent.left=null;
            }else{
                //即node==node.parent.left
                node.parent.right=null;
            }
        }
    }

    /**
     * 根据元素找到对应的节点
     * @param element
     * @return
     */
    private Node<E> node(E element){
        Node<E> node=root;
        while (node!=null){
            int cmp=compare(element,node.element);
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

    public boolean contains(E element) {
        return node(element)!=null;
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


}

