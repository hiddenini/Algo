package com.xz.Sorting;

public class InsertionSort1<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 0; begin < array.length; begin++) {
            int current = begin;
            E v = array[current];
            /**
             * 优化，将交换变为挪动.保存当前元素，和左边元素进行比较，如果比左边元素小则将左边元素往右挪动一位
             *
             * 直到while循环结束，current的值就是v的索引
             */
            while (current > 0 && cmp(v, array[current - 1]) < 0) {
                array[current] = array[current - 1];
                current--;
            }
            array[current] = v;
        }
    }
}
