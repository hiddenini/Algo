package com.xz.Sorting;

/**
 * 计数排序优化
 */
public class CountingSort1 extends Sort<Integer> {
    @Override
    protected void sort() {
        //找出最值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
            if (array[i] < min) {
                min = array[i];
            }
        }
        //新建数组存储次数
        int[] counts = new int[max - min + 1];
        //统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }
        //累加次数,每一个元素的次数等于前面的所有次数加上他本身的次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        //从后往前遍历counts数组,将它挡在有序数组中的合适位置
        int[] newArray = new int[array.length];

        for (int i = array.length - 1; i >= 0; i--) {
            /** --a;  a=a-1;
             *  --counts[array[i]-min];
             *  means that
             *  counts[array[i]-min]=count[array[i]-min]-1;
             */
            newArray[--counts[array[i] - min]] = array[i];
        }
        //将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
