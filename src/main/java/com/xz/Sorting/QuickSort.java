package com.xz.Sorting;

/**
 * @author xz
 * @date 2020/5/12 14:22
 **/

/**
 * 快速排序
 */
public class QuickSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(E[] array, int low, int high) {
        if (low > high) {
            return;
        }
        int i, j;
        i = low;
        j = high;
        /**
         * 将最低位设置为基准位
         */
        E temp = array[low];
        E t;
        while (i < j) {
            //从右边开始找到第一个比temp小的数据
            while (cmp(temp,array[j]) <= 0 && i < j) {
                j--;
            }
            //从左边开始找到第一个比temp大的数据
            while (cmp(temp,array[i]) >= 0 && i < j) {
                i++;
            }

            //找到之后,如果low < high，进行交换
            if (i < j) {
/*                t = array[j];
                array[j] = array[i];
                array[i] = t;*/
                swap(i,j);
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        array[low] = array[i];
        array[i] = temp;
        //递归调用左半数组
        quickSort(array, low, j - 1);
        //递归调用右半数组
        quickSort(array, j + 1, high);
    }

}
