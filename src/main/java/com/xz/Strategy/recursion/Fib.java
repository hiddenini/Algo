package com.xz.Strategy.recursion;


/**
 * 斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）以兔子繁殖为例子而引入
 * <p>
 * 故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……
 */

public class Fib {
    public static void main(String[] args) {
        System.out.println(fib0(6));
    }

    /**
     * 求第n个斐波那契数
     */
    public static int fib0(int n) {
        if (n <= 2) return 1;

        return fib0(n - 1) + fib0(n - 2);
    }


}
