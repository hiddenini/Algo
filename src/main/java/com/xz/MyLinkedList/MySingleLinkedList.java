package com.xz.MyLinkedList;

import com.xz.AbstractComponent.AbstractList;

/**
 * @author xz
 * @date 2020/2/15 11:07
 **/


/**
 * 单链表
 * @param <E>
 */
public class MySingleLinkedList<E> extends AbstractList<E>{

    private Node<E> first;

    private static class Node<E> {
        E element;
        Node<E> next;
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
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
        if(index==0){
            first=new Node(element,first);
        }else{
            rangeCheckForAdd(index);
            Node<E> old= node(index);
            Node<E> prev= node(index-1);
            Node<E> newNode=new Node(element,null);
            newNode.next=old;
            prev.next=newNode;
        }
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        Node<E> node=first;
        if(index==0){
            first=first.next;
        } else{
            Node<E> prev=node(index-1);
            node=prev.next;
            prev.next=prev.next.next;
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
            string.append(node.element);
            node = node.next;
        }
        string.append("]");
        return string.toString();
    }
}
