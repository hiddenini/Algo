package com.xz.MyLinkedList.circle;

import com.xz.AbstractComponent.AbstractList;

/**
 * @author xz
 * @date 2020/2/15 11:07
 **/


/**
 * 单向循环链表
 * @param <E>
 */
public class MySingleCircleLinkedListJosephus<E> extends AbstractList<E>{

    private Node<E> first;

    private Node<E> current;

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
            sb.append(element).append("_").append(next.element);
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
            Node<E> newFirst = new Node(element, first);
            // 找到尾结点,如果现在只有一个节点的话，那么自己指向自己
            Node<E> last = (size == 0) ? newFirst : node(size - 1);
            last.next = newFirst;
            //将尾结点指向新的头结点
            first = newFirst;

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
            //如果只有一个节点时,直接让头结点指向null
            if(size==1){
                first=null;
            }else{
                //拿最后一个节点的操作需要在first节点发生改变之前,因为node()是依赖first节点的
                Node<E> last=node(size-1);
                first=first.next;
                last.next=first;
            }

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

    public void reset(){
        current=first;
    }

    public E next(){
        if(current==null) return null;
        current=current.next;
        return current.element;
    }


    public E remove(){
        if(current==null) return null;

        Node<E> next=current.next;
        int index=indexOf(current.element);
        E element=remove(index);

        if(size==0){
            current=null;
        }else {
            current=next;
        }
        return element;

    }




}
