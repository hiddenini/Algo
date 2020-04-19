package com.xz.Set;

import com.xz.BinaryTree.BinaryTree;
import com.xz.BinaryTree.RBTree;

import java.util.Comparator;

/**
 * 使用红黑树实现Set
 * @param <E>
 */
public class TreeSet<E> implements Set<E> {
    private RBTree<E> rbTree;

    TreeSet(){
        this(null);
    }

    TreeSet(Comparator comparator){
        rbTree=new RBTree<>(comparator);
    }

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void add(E element) {
        rbTree.add(element);
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        rbTree.inOrderTraversal(new BinaryTree.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }
}
