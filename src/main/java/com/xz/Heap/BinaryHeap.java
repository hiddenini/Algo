package com.xz.Heap;

import com.xz.BinaryTree.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 二叉堆(这里实现大顶堆)
 *
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public BinaryHeap() {
        this(null);
    }


    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    @Override
    public void add(E element) {
        ensureCapacity(size + 1);
        elementNotNullCheck(element);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }

    private void siftDown(int index) {
        int half = size >> 1;
        E element = elements[index];
        /**
         *  必须保证index位置是非叶子节点
         *
         *  完全二叉树的      第一个叶子节点的索引==非叶子节点的个数
         */
        while (index < half) {
            //index只会有2种情况,只有左节点或者有左右节点
            //默认的比较节点左子节点
            int childrenIndex = (index << 1) + 1;
            E child = elements[childrenIndex];
            //右子节点
            int rightIndex = childrenIndex + 1;
            //选出较大的那个
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
/*                childrenIndex=rightIndex;
                child=elements[rightIndex];*/
                child = elements[childrenIndex = rightIndex];
            }
            if (compare(element, child) >= 0) break;
            //如果子节点比element要大,那么将子节点放到index的位置
            elements[index] = child;
            index = childrenIndex;
        }
        elements[index] = element;
    }


    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 新容量为旧容量的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 让index位置的元素上滤
     */
    private void siftUp(int index) {
/*        E e = elements[index];
        while (index != 0) {
            int parentIndex = (index - 1) >> 1;
            E p = elements[parentIndex];
            if (comapre(e, p) <= 0) return;
            //否则需要交换该节点和父节点
            E temp = elements[index];
            elements[index] = elements[parentIndex];
            elements[parentIndex] = temp;
            index = parentIndex;
        }*/

        E e = elements[index];
        while (index != 0) {
            int parentIndex = (index - 1) >> 1;
            E p = elements[parentIndex];
            if (compare(e, p) <= 0) break;
            //将父节点值覆盖当前的index,父节点的index赋值给index继续往上找,直到跳出循环index的值就是当前节点的index
            elements[index] = p;
            index = parentIndex;
        }
        elements[index] = e;
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}
