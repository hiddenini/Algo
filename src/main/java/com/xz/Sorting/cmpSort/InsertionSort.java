package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;

/**
 * 插入排序   时间复杂度和数组的逆序对的数量成正比
 * {2,3,8,6,1}逆序对有{2,1},{3,1},{8,6},{8,1},{6,1}
 * 逆序对越多，表示左边比该元素大的元素越多，那么需要进行更多的交换或者挪动
 *
 * @param <E>
 */
public class InsertionSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        /**
         * 插入排序是从第二张牌开始比较的,所以这个地方直接从1开始
         */
        for (int begin = 1; begin < array.length; begin++) {
            int current = begin;
            /**
             * 元素和左边进行比较，如果小于左边的元素则进行交换,并且将current往左移动一个位置继续循环
             *
             * cmp(current, current - 1) < 0 这里决定了是一个稳定的排序
             */
            while (current > 0 && cmp(current, current - 1) < 0) {
                swap(current, current - 1);
                current--;
            }
        }
    }
}
