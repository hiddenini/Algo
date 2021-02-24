package com.xz.Strategy.recursion;

/**
 * 尾调用:一个函数的最后一个动作是调用函数
 * 如果最后一个动作是调用自身，成为尾递归，是尾调用的特殊情况
 * 一些编译器能对尾调用进行优化，以达到节省栈空间的目的
 * <p>
 * 为什么只有尾调用才可能优化，因为最后一个动作是调用函数，那么后面不会再有使用前面变量的代码了，所以直接将
 * <p>
 * 栈空间给最后这个函数使用
 * <p>
 * 尾调用优化也叫做尾调用消除
 * 如果当前栈帧上的局部变量等内容都不需要了，当前栈帧经过适当的改变后可以直接当做被尾调用的函数的栈帧使用
 * 然后程序可以jump到被尾调用的函数代码
 * <p>
 * 生成栈帧改变代码与jump的过程成为尾调用消除或尾调用优化
 * <p>
 * 消除尾递归比消除尾调用简单很多，因为同一个函数的栈帧大小一致
 * <p>
 * 因此尾递归优化相对容易很多，jvm虚拟会消除尾递归里的尾调用，但不会消除一般的尾调用
 */

public class TailCall {
    public static void main(String[] args) {
        System.out.println(factorial(3));
        System.out.println(factorial0(3));

    }

    static int factorial(int n) {
        if (n <= 1) return n;
        return n * factorial(n - 1);
    }

    static int factorial0(int n) {
        return factorial0(n, 1);
    }

    /**
     * 将阶乘递归调用优化成尾递归
     *
     * @param n
     * @param result
     * @return
     */
    static int factorial0(int n, int result) {
        if (n <= 1) return result;
        return factorial0(n - 1, result * n);
    }



}
