package com.xz.Strategy.recursion;

/**
 * 楼梯有n阶台阶，上楼可以一步上一阶，也可以一步上2阶，走完n阶台阶共有多少不同种走法
 * <p>
 * 假设n 阶台阶有f(n)种走法，第一步有2种走法,
 * 1--如果走1阶，那么还剩n-1阶，共f(n-1)种走法
 * 2--如果走2阶，那么还剩n-2阶，共f(n-2)种走法
 * <p>
 * 那么f(n)=f(n-1)+f(n-2)  这个递归式和fib一样，只是初始值不一样
 */
public class ClimbStairs {
    public static void main(String[] args) {

    }

    int climbStairs(int n) {
        if (n <= 2) return n;
        int first = 1, second = 2;
        for (int i = 3; i <= n; i++) {
            second = first + second;
            first = second - first;
        }
        return second;
    }
}
