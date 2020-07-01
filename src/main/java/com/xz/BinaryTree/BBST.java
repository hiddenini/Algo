package com.xz.BinaryTree;

import java.util.Comparator;

public class BBST<E> extends BST<E> {

    public BBST() {
        this(null);
    }

    public BBST(Comparator<E> comparator) {
        super(comparator);
    }
    /**
     *    ┌──g──┐
     *    │     │
     *    k  ┌──p──┐
     *       │     │
     *       m     n
     *
     *
             ┌──P──┐
             │     │
          ┌──g──┐  n
          │     │
          k     m

     */
    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     *     ┌──g──┐
     *     │     │
     *  ┌──p──┐  k
     *  │     │
     *  n     m


     *     ┌──p──┐
     *     │     │
     *     n  ┌──g──┐
              │     │
              m     k

     *
     * @param grand
     */
    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 统一旋转操作
     */
    protected void rotate(
            Node<E> r,
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e,Node<E> f, Node<E> g )    {
        //让d成为新的根节点
        d.parent=r.parent;
        if(r.isLeftChild()){
            r.parent.left=d;
        }else if(r.isRightChild()){
            r.parent.right=d;
        }else{
            root=d;
        }

        //处理a b c节点
        b.left=a;
        b.right=c;
        if (a!=null){
            a.parent=b;
        }
        if (c!=null){
            c.parent=b;
        }

        //处理e f g三个节点
        f.left=e;
        f.right=g;
        if (e!=null){
            e.parent=f;
        }
        if (g!=null){
            g.parent=f;
        }


        //处理 b d f三个节点
        d.left=b;
        d.right=f;
        b.parent=d;
        f.parent=d;

    }

    /**
     * 旋转之后需要修改parent,更新高度
     * @param grand
     * @param parent
     * @param child
     */
    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
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
}
