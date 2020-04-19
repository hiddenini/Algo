package com.xz.Set;

import com.xz.AbstractComponent.List;
import com.xz.MyLinkedList.MyLinkedList;

/**
 * 使用链表实现Set
 */
public class ListSet<E> implements Set<E>{
    private List<E> list=new MyLinkedList();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        int index = list.indexOf(element);
        if (index!=List.ELEMENT_NOT_FOUND){
            //覆盖旧的值
            list.set(index,element);
        }else{
            list.add(element);
        }
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index!=List.ELEMENT_NOT_FOUND){
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor visitor) {
        int size = list.size();
        for (int i=0;i<size;i++){
            if(visitor.visit(list.get(i))) return;
        }
    }
}
