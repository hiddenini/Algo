package com.xz.MyQueue;

import com.xz.MyLinkedList.MyLinkedList;

/**
 * @author xz
 * @date 2020/2/18 13:14
 **/

public class MyQueue<E> {
    private MyLinkedList<E> list=new MyLinkedList<E>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueue(E element) {
        list.add(element);
    }

    public E deQueue() {
        return list.remove(0);
    }

    public E front() {
        return list.get(0);
    }
}
