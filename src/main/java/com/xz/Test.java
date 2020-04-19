package com.xz;

import com.xz.AbstractComponent.List;
import com.xz.MyLinkedList.circle.MyCircleLinkedListJosephus;
import com.xz.MyLinkedList.circle.MySingleCircleLinkedListJosephus;
import com.xz.MyQueue.MyQueue;
import com.xz.Stack.MyStack;
import com.xz.Utils.Asserts;

/**
 * @author xz
 * @date 2020/2/15 12:02
 **/

public class Test {
    public static void main(String[] args) {
        //测试双向链表
        //testList(new com.xz.MyLinkedList<Integer>());

        //查看jdk 的linkedList的源码
        java.util.LinkedList list;

        //测试单向循环链表Q
        //testList(new MySingleCircleLinkedList<Integer>());

        //测试双向循环链表
        //testList(new MyCircleLinkedList<Integer>());

        //使用双向循环链表测试约瑟夫问题
        //testJosephusWithLinkedList();


        //使用单向循环链表测试约瑟夫问题
        //testJosephusWithSingleLinkedList();

        //测试stack
        //testStack();

        //测试queue
        //testQueue();


    }


   public  static void testList(List<Integer> list) {
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);

        list.add(0, 55); // [55, 11, 22, 33, 44]
        list.add(2, 66); // [55, 11, 66, 22, 33, 44]
        list.add(list.size(), 77); // [55, 11, 66, 22, 33, 44, 77]
        list.remove(0); // [11, 66, 22, 33, 44, 77]
        list.remove(2); // [11, 66, 33, 44, 77]

        list.remove(list.size() - 1); // [11, 66, 33, 44]

        Asserts.test(list.indexOf(44) == 3);
        Asserts.test(list.indexOf(22) == List.ELEMENT_NOT_FOUND);
        Asserts.test(list.contains(33));
        Asserts.test(list.get(0) == 11);
        Asserts.test(list.get(1) == 66);
        Asserts.test(list.get(list.size() - 1) == 44);
        System.out.println(list);
    }

    public  static void testQueue() {
        MyQueue<Integer> queue = new MyQueue<Integer>();
        queue.enQueue(11);
        queue.enQueue(22);
        queue.enQueue(33);
        queue.enQueue(44);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }


    public  static void testStack() {
        MyStack<Integer> stack = new MyStack<Integer>();

        stack.push(11);
        stack.push(22);
        stack.push(33);
        stack.push(44);

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }


    public  static void testJosephusWithSingleLinkedList(){
        MySingleCircleLinkedListJosephus<Integer> linkedListJosephus = new MySingleCircleLinkedListJosephus();

        for (int i = 1; i <= 8; i++) {
            linkedListJosephus.add(i);
        }

        linkedListJosephus.reset();
        while (!linkedListJosephus.isEmpty()) {
            linkedListJosephus.next();
            linkedListJosephus.next();
            System.out.println(linkedListJosephus.remove());

        }
    }


    public  static void testJosephusWithLinkedList(){
        MyCircleLinkedListJosephus<Integer> linkedListJosephus=new MyCircleLinkedListJosephus();
        for(int i=1;i<=8;i++){
            linkedListJosephus.add(i);
        }
        linkedListJosephus.reset();
        while (!linkedListJosephus.isEmpty()){
            linkedListJosephus.next();
            linkedListJosephus.next();
            System.out.println(linkedListJosephus.remove());
        }
        }
    }


