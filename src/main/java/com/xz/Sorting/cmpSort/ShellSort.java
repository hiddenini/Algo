package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 希尔排序 ,取决于步长序列
 *
 * @param <E>
 */
public class ShellSort<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        //List<Integer> stepSequence = shellStepSequence();
        List<Integer> stepSequence = sedgewickStepSequence();
        for (Integer step : stepSequence) {
            sort(step);
        }
    }

    /**
     * 分成step列进行排序，每一个step的排序其实是使用的插入排序
     *
     * @param step
     */
    private void sort(int step) {
        for (int col = 0; col < step; col++) {
            //和之前的插入排序不一样的地方在于,这里的的每一个要比较的数据的索引是col,col+step,col+2*step...
            for (int begin = col + step; begin < array.length; begin += step) {
                int current = begin;
                while (current > col && cmp(current, current - step) < 0) {
                    swap(current, current - step);
                    current -= step;
                }
            }
        }
    }

    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }

    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}
