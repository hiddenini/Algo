package com.xz.Strategy.recursion;

/**
 * A B C 三个柱子  有N个盘子
 * <p>
 * 将A 上面的N个盘子移动到C
 * <p>
 * 规则是 每次只能移动一个盘子 ,大盘子只能放在小盘子下面
 * <p>
 * 分2种情况讨论
 * <p>
 * 当n==1时,直接将盘子从A移动到C
 * 当n>1时,可以拆分为3个步骤
 * 1--将n-1个盘子从A移动到B
 * 2--将编号为n的盘子从A移动到C
 * 3--将n-1个盘子从B移动到C
 * 步骤1 3 冥想是递归调用 只是说中间柱子不同而已
 * <p>
 * <p>
 * 递归百分百可以转成非递归
 * 1--万能公式，自己维护一个栈，来保存参数,局部变量,但是空间复杂度还是没有得到优化
 * 2--在某些时候，也可以重复使用一组相同的变量来保存每个栈帧的内同
 */
public class Hanoi {
    public static void main(String[] args) {
        hanoi(3, "A", "B", "C");
    }

    /**
     * 将n个盘子从p1移动到p3
     *
     * @param n
     * @param p1
     * @param p2
     * @param p3 时间复杂度:O(2^n)
     *           空间复杂度:O(n)
     */
    static void hanoi(int n, String p1, String p2, String p3) {
        if (n == 1) {
            move(1, p1, p3);
            return;
        }
        //将n-1个盘子从p1移动到p2 p3为中间柱子
        hanoi(n - 1, p1, p3, p2);
        //将编号为n的盘子从A移动到C
        move(n, p1, p3);
        //-将n-1个盘子从B移动到C
        hanoi(n - 1, p2, p1, p3);
    }

    /**
     * 将 no 号盘子从from 移动到  to
     *
     * @param no
     * @param from
     * @param to
     */
    static void move(int no, String from, String to) {
        System.out.println("将" + no + "号盘子从" + from + "移动到" + to);
    }
}
