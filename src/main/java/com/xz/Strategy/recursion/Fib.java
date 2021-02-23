package com.xz.Strategy.recursion;


import com.xz.Map.Times;

/**
 * 斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入
 * <p>
 * 故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……
 */

public class Fib {
    public static void main(String[] args) {
        Times.test("fib0", () -> {

            System.out.println(fib0(10));

        });

        Times.test("fib1", () -> {

            System.out.println(fib1(10));

        });

        Times.test("fib2", () -> {

            System.out.println(fib2(10));

        });

        Times.test("fib3", () -> {

            System.out.println(fib3(10));

        });

        Times.test("fib4", () -> {

            System.out.println(fib4(10));

        });

    }

    /**
     * 求第n个斐波那契数
     * <p>
     * 空间复杂度:O(n)
     * 时间复杂度:O(2^n)
     */
    public static int fib0(int n) {
        if (n <= 2) return 1;

        return fib0(n - 1) + fib0(n - 2);
    }

    /**
     * 优化一 使用数组保存以及计算过的值 减少大量的重复计算，但是还是有递归调用
     * 空间复杂度:O(n)
     * 时间复杂度:O(n)
     *
     * @param n
     * @return
     */
    public static int fib1(int n) {
        if (n <= 2) return 1;
        int arr[] = new int[n + 1];
        arr[1] = arr[2] = 1;
        return fib1(n - 1, arr) + fib1(n - 2, arr);
    }

    static int fib1(int n, int arr[]) {
        //如果n的值还没被计算过,则递归计算
        if (arr[n] == 0) {
            arr[n] = fib1(n - 1, arr) + fib1(n - 2, arr);
        }
        return arr[n];
    }

    /**
     * 优化一 使用数组保存以及计算过的值并且借用数组去除递归调用
     * 空间复杂度:O(n)
     * 时间复杂度:O(n)
     *
     * @param n
     * @return
     */
    public static int fib2(int n) {
        if (n <= 2) return 1;
        int arr[] = new int[n + 1];
        arr[1] = arr[2] = 1;
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n];
    }

    /**
     * 使用滚动数组
     * 空间复杂度:O(1)
     * 时间复杂度:O(n)
     * n %  2 =n & 1
     *
     * @param n
     * @return
     */
    public static int fib3(int n) {
        if (n <= 2) return 1;
        int arr[] = new int[2];
        arr[0] = arr[1] = 1;
        for (int i = 3; i <= n; i++) {
            //arr[i % 2] = arr[(i - 1) % 2] + arr[(i - 2) % 2];
            arr[i & 1] = arr[(i - 1) & 1] + arr[(i - 2) & 1];
        }
        return arr[n & 1];
    }

    /**
     * 直接使用2个变量
     * 空间复杂度:O(1)
     * 时间复杂度:O(n)
     *
     * @param n
     * @return
     */
    public static int fib4(int n) {
        if (n <= 2) return 1;
        int first = 1, second = 1;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
}
