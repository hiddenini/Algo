package com.xz.Heap;

import com.xz.BinaryTree.printer.BinaryTrees;

import java.util.Comparator;

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

    static void test4() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data);
        BinaryTrees.println(heap);
        data[0] = 10;
        data[1] = 20;
        BinaryTrees.println(heap);

    }

    static void test5() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37};
        BinaryHeap<Integer> heap = new BinaryHeap<>(data, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        BinaryTrees.println(heap);
    }

    /**
     * top k 问题
     */
    static void test6() {
        Integer[] data = {88, 44, 53, 41, 16, 6, 70, 18, 85, 98, 81, 23, 36, 43, 37, 1, 3, 199, 300, 37, 65,
                999, 1008, 4, 6, 8, 34, 5, 6, 89};
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int k = 5;
        for (int i = 0; i < data.length; i++) {
            if (heap.size < k) {
                //  前k个树添加到小顶堆
                heap.add(data[i]);
            } else if (data[i] > heap.get()) {
                /**如果是第k+1以及往后的走的数据,并且大于堆顶元素,那么replace
                 * 这样做的话每一次堆中最小的元素都被删除了，直到所有数据都遍历完
                 * 并且add操作和replace都是O（logK）的复杂度，相比全排序(O(logN))要快
                 */
                heap.replace(data[i]);
            }
        }
        BinaryTrees.println(heap);
    }

    public static void main(String[] args) {
        test6();
    }
}
