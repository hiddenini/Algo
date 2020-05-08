package com.xz.Sorting;

import com.xz.Utils.Asserts;

/**
 * 二分查找(前提是序列是有序)
 */
public class BinarySearch {

    /**
     * 查找有序序列中指定v的index
     */
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else if (v > array[mid]) {
                begin = mid;
            } else {
                return mid;
            }
        }
        return -1;
    }


    /**
     * 查找有序序列中v的插入位置(找到第一个大于v的值的index)
     */
    public static int search(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (v < array[mid]) {
                end = mid;
            } else {
                //大于等于则往右边找
                begin = mid + 1;
            }
        }
        //循环结束时的begin==end 就是该元素的插入位置
        return begin;
    }

    public static void main(String[] args) {
/*        int[] array = {2, 4, 6, 8, 10};
        Asserts.test(indexOf(array, 2) == 0);
        Asserts.test(indexOf(array, 4) == 1);
        Asserts.test(indexOf(array, 10) == 4);*/

        int[] array = {2, 4, 8, 8, 8, 12, 14};
        Asserts.test(search(array, 5) == 2);
        Asserts.test(search(array, 1) == 0);
        Asserts.test(search(array, 15) == 7);
        Asserts.test(search(array, 8) == 5);


    }
}
