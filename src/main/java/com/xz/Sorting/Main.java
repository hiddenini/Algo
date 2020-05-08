package com.xz.Sorting;

import com.xz.Sorting.tools.Integers;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array1 = Integers.random(10000, 1, 20000);
        testSorts(array1, new BubbleSort3(),
                new SelectionSort(),
                new HeapSort(),
                new InsertionSort(),
                new InsertionSort1(),
                new InsertionSort2());
    }

    private static void testSorts(Integer[] array1, Sort... sorts) {
        for (Sort sort : sorts) {
            sort.sort(Integers.copy(array1));
        }
        Arrays.sort(sorts);

        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }
}
