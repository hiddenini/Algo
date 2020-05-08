package com.xz.Sorting;

import com.xz.Sorting.tools.Asserts;
import com.xz.Sorting.tools.Integers;
import com.xz.Sorting.tools.Times;

/**
 * 选择排序每次循环找到最大的那个值和末尾值交换(相比冒泡减少了很多交换)
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
