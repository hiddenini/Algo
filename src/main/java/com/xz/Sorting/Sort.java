package com.xz.Sorting;

import com.xz.Sorting.cmpSort.SelectionSort;
import com.xz.Sorting.cmpSort.ShellSort;
import com.xz.Sorting.entity.Student;

import java.text.DecimalFormat;


@SuppressWarnings("unchecked")
public abstract class Sort<E extends Comparable<E>> implements Comparable<Sort<E>> {
    protected E[] array;
    private int cmpCount;
    private int swapCount;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(E[] array) {
        if (array == null || array.length < 2) return;

        this.array = array;

        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    @Override
    public int compareTo(Sort o) {
        int result = (int) (time - o.time);
        if (result != 0) return result;

        result = cmpCount - o.cmpCount;
        if (result != 0) return result;

        return swapCount - o.swapCount;
    }

    protected abstract void sort();

    /*
     * 返回值等于0，代表 array[i1] == array[i2]
     * 返回值小于0，代表 array[i1] < array[i2]
     * 返回值大于0，代表 array[i1] > array[i2]
     */
    protected int cmp(int i1, int i2) {
        cmpCount++;
        return array[i1].compareTo(array[i2]);
    }

    protected int cmp(E v1, E v2) {
        cmpCount++;
        return v1.compareTo(v2);
    }

    protected void swap(int i1, int i2) {
        swapCount++;
        E tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        //最后执行isStable()不影响上面比较和交换次数的统计，因为isStable()这个方法也会进行比较和交换
        String stableStr = "稳定性: " + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";

    }

    private Boolean isStable() {
        //基数排序是非比较的排序,是稳定的
        if (this instanceof RadixSort) return true;
        //计数排序是非比较排序 并且只能针对整数,并且是非稳定的
        if (this instanceof CountingSort) return false;
        //改进版的计数排序是非比较排序 并且只能针对整数,并且是稳定的
        if (this instanceof CountingSort1) return true;
        //希尔排序是按照列排序的,之前的规则不太适合希尔排序,但是希尔排序是不稳定的
        if (this instanceof ShellSort) return false;
        if (this instanceof SelectionSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 10);
        }
        sort((E[]) students);
        for (int i = 1; i < students.length; i++) {
            if (students[i].getScore() != students[i - 1].getScore() + 10) return false;
        }
        return true;
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

}
