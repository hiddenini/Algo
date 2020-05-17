package com.xz.Sorting;

/**
 * 基数排序
 * <p>
 * 依次对个位 十位 百位...进行排序
 * <p>
 * 每个位上面的排序可以使用计数排序
 */
public class RadixSort extends Sort<Integer> {
    @Override
    protected void sort() {
        //找出最大值,下面比较的次数是最大数的位数
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        /**
         * eg max=593;
         * 个位数: 593 % 10 =3
         *十位数: 593 / 10 % 10 =9
         * 百位数 593 / 100 % 10 =5
         */
        for (int divider = 1; divider <= max; divider *= 10) {
            countingSort(divider);
        }
    }

    protected void countingSort(int divider) {
        //新建数组存储次数，每一位比较的范围都是0-9,不从min到max是因为这里的空间浪费很少,可以不用考虑
        int[] counts = new int[10];
        //统计每个个位数(十位数...)出现的次数
        for (int i = 0; i < array.length; i++) {
            counts[array[i] / divider % 10]++;
        }
        //累加次数,每一个元素的次数等于前面的所有次数加上他本身的次数
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        //从后往前遍历counts数组,将它放在有序数组中的合适位置
        int[] newArray = new int[array.length];

        for (int i = array.length - 1; i >= 0; i--) {
            /** --a;  a=a-1;
             *  --counts[array[i]-min];
             *  means that
             *  counts[array[i]-min]=count[array[i]-min]-1;
             */
            newArray[--counts[array[i] / divider % 10]] = array[i];
        }
        //将有序数组赋值到array
        for (int i = 0; i < newArray.length; i++) {
            array[i] = newArray[i];
        }
    }
}
