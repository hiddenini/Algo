package com.xz.MyLinkedList;

import com.xz.AbstractComponent.AbstractList;

/**
 * @author xz
 * @date 2020/2/15 11:07
 **/


/**
 * 双链表
 * @param <E>
 */
public class MyLinkedList<E> extends AbstractList<E>{

    private Node<E> first;

    private Node<E> last;


    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;
        public Node(E element, Node<E> prev,Node<E> next) {
            this.element = element;
            this.next = next;
            this.prev=prev;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }

            sb.append("_").append(element).append("_");

            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }

            return sb.toString();
        }
    }


    public void clear() {
        size=0;
        first=null;
        last=null;
    }

    public E get(int index) {
        return node(index).element;
    }

    public Node<E> node(int index){
        Node node=first;
        for (int i=0;i<index;i++){
            node=node.next;
        }
        return node;
    }

    public E set(int index, E element) {
        Node<E> node= node(index);
        E old=node.element;
        node.element=element;
        return old;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);

        //往尾部添加数据
        if(index==size){
            Node<E> oldLast=last;
            //尾指针指向新的尾结点
            last=new Node<E>(element,oldLast,null);
            //添加的第一个元素
            if(oldLast==null){
                first=last;
            }else{
                oldLast.next=last;
            }
        }else{
            Node<E> next=node(index);
            Node<E> prev=next.prev;
            Node<E> node=new Node(element,prev,next);
            next.prev=node;
            //如果在头结点插入一个元素
            if(prev==null){
                first=node;
            }else{
                prev.next=node;
            }
        }
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        Node<E> node=node(index);
        Node<E> prev=node.prev;
        Node<E> next=node.next;
        //如果删除的是头结点 index==0
        if(prev==null){
            first=next;
        }else{
            prev.next=next;
        }
        //如果删除的是尾结点 index==size-1
        if(next==null){
            last=prev;
        }else{
            next.prev=prev;
        }
        size--;
        return node.element;
    }



    public int indexOf(E element) {
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (element.equals(node.element)) return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }
            string.append(node);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
