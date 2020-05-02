package com.xz.Heap;

import com.xz.BinaryTree.printer.BinaryTrees;

public class Test {

    static void test1() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
/*        heap.add(10);
        heap.add(90);
        heap.add(65);*/
        BinaryTrees.println(heap);
    }

    static void test2() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
        heap.remove();
        BinaryTrees.println(heap);
    }


    static void test3() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        BinaryTrees.println(heap);
        System.out.println(heap.replace(70));
        BinaryTrees.println(heap);
    }

    public static void main(String[] args) {
        test3();
    }
}
