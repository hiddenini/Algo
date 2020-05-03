package com.xz.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        Person p1 = new Person("jack", 10);
        Person p2 = new Person("rose", 6);
        Person p3 = new Person("james", 15);
        Person p4 = new Person("susan", 1);
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(p1);
        queue.enQueue(p2);
        queue.enQueue(p3);
        queue.enQueue(p4);
        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
