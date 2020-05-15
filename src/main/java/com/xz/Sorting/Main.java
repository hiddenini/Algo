package com.xz.Sorting;

import com.xz.Sorting.tools.Integers;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer[] array1 = Integers.random(10000, 1, 20000);
        //Integer[] array1 = Integers.same(50000, 0);
        testSorts(array1,
                new ShellSort(),
                new InsertionSort2(),
                new HeapSort(),
                new MergeSort(),
                new QuickSort());
    }

    private static void testSorts(Integer[] array1, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] copy = Integers.copy(array1);
            sort.sort(copy);
            //小批量数据可以打印
/*            for (int i = 0; i < copy.length; i++) {
                System.out.print(copy[i] + "_");
            }
            System.out.println();*/
        }
        Arrays.sort(sorts);

        for (Sort sort : sorts) {
            System.out.println(sort);
        }

    }
}
