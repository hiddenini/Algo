package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;

/**
 * 归并排序 时间复杂度 O(NLogN)
 *
 * @param <E>
 */
public class MergeSort<E extends Comparable<E>> extends Sort<E> {
    private E[] leftArray;

    @Override
    protected void sort() {
        leftArray = (E[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }

    /**
     * 对[begin,end)范围内的数据进行归并排序
     *
     * @param begin
     * @param end
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    /**
     * 将[begin,mid) [mid,end) 范围的序列合并成一个有序序列
     */
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        //将要覆盖的index
        int ai = begin;
        //备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }
        /**
         * 如果左边先结束则直接结束掉,只有左边还未结束才需要做比对，
         *
         * 如果右边先结束则将左边的值挪到array
         */
        //左边还未结束
        while (li < le) {
            //如果右边还未结束  cmp(array[ri], leftArray[li]) < 0保证了稳定性
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                //如果右边比左边大,则使用右边覆盖掉ai的位置并且ai++,ri++
                array[ai++] = array[ri++];
            } else {
                //如果左边比较小则将左边覆盖掉ai的位置,并且ai++,li++  或者说右边先结束了直接将左边的挪动到array
                array[ai++] = leftArray[li++];
            }
        }
    }
}
