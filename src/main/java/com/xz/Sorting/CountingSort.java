package com.xz.Sorting;

/**
 * 计数排序 只针对一定范围内的整数进行排序,非比较排序 用空间换时间
 * 这个版本的缺点是:
 * <p>
 * 缺点是无法对负整数进行排序
 * 不稳定
 * 浪费空间
 */
public class CountingSort extends Sort<Integer> {
    @Override
    protected void sort() {
        //找出最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        /**
         * 构建一个新的数组存储每个整数出现的次数，counts数组中的索引就是array中的数据
         *
         * counts数组中的值是array中的数据出现的次数
         */
        int[] counts = new int[1 + max];
        //遍历array拿到array[i] 这个array[i]就是counts中的索引 所以++即可 统计每个整数出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i]]++;
        }

        //遍历counts数组输出有序序列
        //记录当前数组输出到哪个位置
        int index = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                array[index++] = i;
            }
        }
    }
}
