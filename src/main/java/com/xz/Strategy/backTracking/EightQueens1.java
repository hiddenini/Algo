package com.xz.Strategy.backTracking;

/**
 * 使用三个boolean数组来优化剪枝操作
 */
public class EightQueens1 {
    public static void main(String[] args) {
        new EightQueens1().placeQueens(4);
    }

    /**
     * 数组索引是行号,数组元素是列号,用于打印
     */
    int[] queens;

    //保存某列上是否已经存在皇后了
    boolean[] cols;

    //保存由左至右的斜线上是否已经存在皇后了
    boolean[] leftTop;

    //保存由右至左的斜线上是否已经存在皇后了
    boolean[] rightTop;
    /**
     * 一共有多少总摆法
     */
    int ways;

    void placeQueens(int n) {
        if (n < 1) return;
        cols = new boolean[n];
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
        queens = new int[n];
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
            if (cols[col]) continue;
            int ltIndex = row - col + cols.length - 1;
            if (leftTop[ltIndex]) continue;
            int rtIndex = row + col;
            if (rightTop[rtIndex]) continue;

            //如果该位置可以摆放皇后,那么将对应列，对应斜线的数组位置置为true
            cols[col] = true;
            leftTop[ltIndex] = true;
            rightTop[rtIndex] = true;
            queens[row] = col;
            place(row + 1);

            //如果回溯了,那么将那么将对应列，对应斜线的数组位置还原,置为false
            //为什么第一种算法不需要还原，是因为回溯的时候后面的列会覆盖掉之前的列
            cols[col] = false;
            leftTop[ltIndex] = false;
            rightTop[rtIndex] = false;

        }
    }


    void show() {
        for (int row = 0; row < queens.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                //如果该列是该行中摆放皇后的那一列，那么打印1
                if (col == queens[row]) {
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
