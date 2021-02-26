package com.xz.Strategy.backTracking;

/**
 * 回溯:通过选择不同的岔路口来通往目的地
 * 每一步都选择一条路出发，能进则进,不能则退回上一步(回溯),换一条路再试
 * 树，图的深度优先搜索就是典型的回溯应用
 */
public class EightQueens {
    public static void main(String[] args) {
        new EightQueens().placeQueens(4);
    }

    /**
     * 数组索引是行号,数组元素是列号
     */
    int[] cols;

    /**
     * 一共有多少总摆法
     */
    int ways;

    void placeQueens(int n) {
        if (n < 1) return;
        cols = new int[n];
        place(0);
        System.out.println("共有" + ways + "种摆法");
    }

    /**
     * 从第row行开始拜访皇后
     *
     * @param row
     */
    void place(int row) {
        if (row == cols.length) {
            //如果摆满了n行，那么摆法加1
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (isValid(row, col)) {
                //如果可以摆放,则在第row行的col列摆放一个皇后
                cols[row] = col;
                //开始摆放下一行,这个地方开始递归，同时也是回溯
                //如果第一行摆放好了一个皇后,那么给第二行找执行place(1),在进行for循环时
                // 通过剪枝判断后还有一些点可以放,一个点一个点试,如果成功摆放则放第三行
                //如果每一个点都不能摆放,那么函数返回到place(0),即回溯到第一行,第一行又开始选择第二列开始摆放
                //如此循环,直到n==
                place(row + 1);
            }
        }
    }

    /**
     * 判断第row行,第col列是否可以摆放皇后
     * <p>
     * 剪枝
     *
     * @param row
     * @param col
     * @return
     */
    boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            //1--判断同一列 如果之前行的皇后摆放的列号等于第row行的col号,那么这个点不能摆放皇后
            if (cols[i] == col) {
                //加打印可以看到是如何回溯的
                System.out.println("[" + row + "][" + col + "]=false");
                return false;
            }
            //2--判断同一行.无需判断，因为这是新的一行
            //3--判断是是否对角线有皇后,利用斜率的知识,如果在统一斜线，那么y2-y1/x2-x1=1 或者-1
            if (row - i == Math.abs(col - cols[i])) {
                System.out.println("[" + row + "][" + col + "]=false");
                return false;
            }
        }
        System.out.println("[" + row + "][" + col + "]=true");
        return true;
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                //如果该列是该行中摆放皇后的那一列，那么打印1
                if (col == cols[row]) {
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            //换行
            System.out.println();
        }
        System.out.println("-----------------");
    }
}
