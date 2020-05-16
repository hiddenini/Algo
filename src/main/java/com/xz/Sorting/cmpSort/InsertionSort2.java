package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;

public class InsertionSort2<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            //找到begin索引对应元素的待插入索引,然后将[insertIndex,begin)范围内的元素往右边挪动一位 ,从较大的索引开始
            insert(begin, search(begin));
        }
    }

    /**
     * 将source位置的元素插入到dest位置
     *
     * @param source
     * @param dest
     */
    private void insert(int source, int dest) {
        E v = array[source];
        for (int i = source; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = v;
    }

    /**
     * 利用二分搜索查找到index位置元素的待插入位置
     *
     * @param index
     * @return
     */
    public int search(int index) {
        int begin = 0;
        int end = index;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (cmp(array[index], array[mid]) < 0) {
                end = mid;
            } else {
                //大于等于则往右边找
                begin = mid + 1;
            }
        }
        //循环结束时的begin==end 就是该元素的插入位置
        return begin;
    }

}
