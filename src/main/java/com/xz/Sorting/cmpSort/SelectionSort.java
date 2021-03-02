package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;
import com.xz.Sorting.tools.Asserts;
import com.xz.Sorting.tools.Integers;
import com.xz.Sorting.tools.Times;

/**
 * 选择排序每次循环找到最大的那个值和末尾值交换(相比冒泡减少了很多交换)
 * <p>
 * 选择排序是不稳定的
 * <p>
 * 7 5 10 10 2 4 2
 */
public class SelectionSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(maxIndex, begin) <= 0) {
                    maxIndex = begin;
                }
            }
            swap(maxIndex, end);
        }
    }
}
