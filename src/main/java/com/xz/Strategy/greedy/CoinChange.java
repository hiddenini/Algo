package com.xz.Strategy.greedy;

import java.util.Arrays;

/**
 * 假设有25分，10分，5分，1分的硬币,现在要找给客户41的零钱，如何办到硬币个数最少
 */
public class CoinChange {
    public static void main(String[] args) {
//		coinChange(new Integer[] {25, 10, 5, 1}, 41);

        //coinChange0(new Integer[]{25, 10, 5, 1}, 41);
        //coinChange1(new Integer[]{25, 10, 5, 1}, 41);
        //使用下面的算法时,得到的结果是25,5,5,5,1
        //贪心算法不一定会得到全局最优解，只贪图眼前利益最大化,看不到长远未来,走一步看一步，后面使用动态规划
        coinChange1(new Integer[]{25, 20, 5, 1}, 41);
        //coinChange2(new Integer[]{25, 10, 5, 1}, 41);

    }

    static void coinChange0(Integer[] faces, int money) {
        // 1 5 20 25
        Arrays.sort(faces);
        int coins = 0;
        for (int i = faces.length - 1; i >= 0; i--) {
            if (money < faces[i]) continue;
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
            //因为硬币可以重复使用,所以最简单的办法就是再次从最后一位开始遍历
            i = faces.length; //进入循环后会立刻减一所以这里不需要减一
        }
        System.out.println(coins);
    }


    static void coinChange1(Integer[] faces, int money) {
        //重大到小排序
        Arrays.sort(faces, (Integer f1, Integer f2) -> f2 - f1);
        int coins = 0, i = 0;
        while (i < faces.length) {
            if (money < faces[i]) {
                i++;
                continue;
            }
            System.out.println(faces[i]);
            money -= faces[i];
            coins++;
            //i = 0;
        }
        System.out.println(coins);

    }

    static void coinChange2(Integer[] faces, int money) {
        //重大到小排序
        Arrays.sort(faces);
        int coins = 0, idx = faces.length - 1;
        while (idx >= 0) {
            while (money >= faces[idx]) {
                money -= faces[idx];
                coins++;
            }
            //当前剩余的money小于faces[idx]时,永久的跳过这个已经硬币
            idx--;
        }


    }
}
