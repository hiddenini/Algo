package com.xz.Stack;

import com.xz.AbstractComponent.List;
import com.xz.MyDynamicArray.ArrayList;

/**
 * @author xz
 * @date 2020/2/18 9:55
 **/

public class MyStack<E> {

    //List<E> list=new MySingleLinkedList<E>();

    List<E> list=new ArrayList<E>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }


    public E pop() {
        return  list.remove(list.size()-1);
    }



}
