package com.xz.Strategy.dynamicPro;

/**
 * 动态规划，简称DP
 * 是求解最优化问题的一种常用策略
 * ◼ 通常的使用套路（一步一步优化）
 * ① 暴力递归（自顶向下，出现了重叠子问题）
 * ② 记忆化搜索（自顶向下）
 * ③ 递推（自底向上）
 * <p>
 * 假设有25分、20分、5分、1分的硬币，现要找给客户41分的零钱，如何办到硬币个数最少？
 * 此前用贪心策略得到的并非是最优解（贪心得到的解是 5 枚硬币）
 * <p>
 * 动态规划中的“动态”可以理解为是“会变化的状态”
 * ① 定义状态（状态是原问题、子问题的解）
 * ✓ 比如定义 dp(i) 的含义 例如找零钱中的dp(41) dp(5) 就是找零41或者5需要的硬币个数
 * ② 设置初始状态（边界）
 * ✓ 比如设置 dp(0) 的值  初始化dp数组 例如找零钱中的dp[1]=1 dp[5]=1
 * ③ 确定状态转移方程
 * ✓ 比如确定 dp(i) 和 dp(i – 1) 的关系 通过dp[1] -->dp[2] -->... 通过小的递推出大的
 * <p>
 * 可以用动态规划来解决的问题，通常具备2个特点
 * 最优子结构（最优化原理）：通过求解子问题的最优解，可以获得原问题的最优解
 * 无后效性
 * ✓ 某阶段的状态一旦确定，则此后过程的演变不再受此前各状态及决策的影响（未来与过去无关）
 * ✓ 在推导后面阶段的状态时，只关心前面阶段的具体状态值，不关心这个状态是怎么一步步推导出来的
 */

public class CoinChange {
    public static void main(String[] args) {
        //System.out.println(coins(41));
        //System.out.println(coins1(41));
        //System.out.println(coins2(7));

        System.out.println(coins4(41, new int[]{1, 5, 20, 25}));
        System.out.println(coins4(6, new int[]{5, 20, 25}));

    }

    /**
     * 暴力递归 （自顶向下的调用，出现了重叠子问题）
     * ◼ 假设 dp(n) 是凑到 n 分需要的最少硬币个数
     * 如果第 1 次选择了 25 分的硬币，那么 dp(n) = dp(n – 25) + 1
     * 如果第 1 次选择了 20 分的硬币，那么 dp(n) = dp(n – 20) + 1
     * 如果第 1 次选择了 5 分的硬币，那么 dp(n) = dp(n – 5) + 1
     * 如果第 1 次选择了 1 分的硬币，那么 dp(n) = dp(n – 1) + 1
     * 所以 dp(n) = min { dp(n – 25), dp(n – 20), dp(n – 5), dp(n – 1) } + 1
     *
     * @param n
     * @return
     */
    static int coins(int n) {
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n == 1) return 1;
        int min1 = Math.min(coins(n - 1), coins(n - 5));
        int min2 = Math.min(coins(n - 20), coins(n - 25));
        return Math.min(min1, min2) + 1;
    }

    /**
     * 记忆化搜索（自顶向下的调用）
     */
    static int coins1(int n) {
        if (n < 1) return -1;
        //存放已经计算过的dp
        int dp[] = new int[n + 1];
        int[] faces = {1, 5, 10, 20};
        //初始化dp数组
        for (int face : faces) {
            if (n < face) break;
            dp[face] = 1;
        }
        return coins1(n, dp);
    }

    private static int coins1(int n, int[] dp) {
        if (n < 1) return Integer.MAX_VALUE;
        //如果dp[n]没有求过
        if (dp[n] == 0) {
            int min1 = Math.min(coins1(n - 25, dp), coins1(n - 20, dp));
            int min2 = Math.min(coins1(n - 5, dp), coins1(n - 1, dp));
            dp[n] = Math.min(min1, min2) + 1;
        }
        return dp[n];
    }

    /**
     * 记录零钱的具体方案
     */
    static int coins3(int n) {
        if (n < 1) return -1;
        int[] dp = new int[n + 1];
        //记录具体的零钱
        // faces[i]是凑够i分时最后那枚硬币的面值
        int faces[] = new int[dp.length];
        //从小到大遍历,因为就算递归调用也是最后先求出了小的，函数一步一步返回才求出大的
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            //这里也初始化了dp[] 从1开始 i=1时  dp[1]=1 i=5时  dp[5]=1 ...
            //if (i >= 1) min = Math.min(dp[i - 1], min);
            if (i >= 1 && dp[i - 1] < min) {
                min = dp[i - 1];
                faces[i] = 1;
            }

            if (i >= 5 && dp[i - 5] < min) {
                min = dp[i - 5];
                faces[i] = 5;
            }
            if (i >= 20 && dp[i - 20] < min) {
                min = dp[i - 20];
                faces[i] = 20;
            }
            if (i >= 25 && dp[i - 25] < min) {
                min = dp[i - 25];
                faces[i] = 25;
            }
            dp[i] = min + 1;
            //打印 1--n 每一种的具体需要找零方案
            print(faces, i);
        }
        //print(faces, n);
        return dp[n];
    }

    static void print(int[] faces, int n) {
        //faces[i]是凑够i分时最后那枚硬币的面值
        //那么n-faces[n]就是减去最后那枚硬币剩下需要找零的钱 那么faces[n-faces[n]] 就是倒数第二枚找零的钱
        //n=7时 face[7]=5 face[7-5]=1 face[2-1]=1
        System.out.print("[" + n + "] = ");
        while (n > 0) {
            System.out.print(faces[n] + " ");
            n -= faces[n];
        }
        System.out.println();
    }

    /**
     * 通用版本
     *
     * @param n
     * @param faces
     * @return
     */
    static int coins4(int n, int faces[]) {
        if (n <= 1 || faces == null || faces.length == 0) return -1;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            //遍历faces数组,生成
            /**
             *  if (i >= 1) min = Math.min(dp[i - 1], min);
             *  if (i >= 5) min = Math.min(dp[i - 5], min);
             */
            //考虑到无法凑齐,例如 面额为 5 10 25 凑6分钱
            //或者5 10 25 凑41分钱
            for (int face : faces) {
                if (i < face || dp[i - face] < 0) continue;
                min = Math.min(dp[i - face], min);
            }
            if (min == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }
        return dp[n];
    }

    /**
     * 递推（自底向上） 先求小的,推出大的 时间和空间复杂度都为O(n)
     */
    static int coins2(int n) {
        if (n < 1) return -1;
        //这里的dp数组是每种零钱 1--n 需要找的零钱的个数
        int[] dp = new int[n + 1];
        //从小到大遍历,因为就算递归调用也是最后先求出了小的，函数一步一步返回才求出大的
        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            //这里也初始化了dp[] 从1开始 i=1时  dp[1]=1 i=5时  dp[5]=1 ...
            if (i >= 1) min = Math.min(dp[i - 1], min);
            if (i >= 5) min = Math.min(dp[i - 5], min);
            if (i >= 20) min = Math.min(dp[i - 20], min);
            if (i >= 25) min = Math.min(dp[i - 25], min);
            dp[i] = min + 1;
        }
        return dp[n];
    }

    public int coinChange(int[] coins, int amount) {
        if (amount <= 0) return 0;
        if (coins == null || coins.length == 0) return -1;
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            //遍历faces数组,生成
            /**
             *  if (i >= 1) min = Math.min(dp[i - 1], min);
             *  if (i >= 5) min = Math.min(dp[i - 5], min);
             */
            //考虑到无法凑齐,例如 面额为 5 10 25 凑6分钱
            //或者5 10 25 凑41分钱
            for (int face : coins) {
                if (i < face || dp[i - face] < 0) continue;
                min = Math.min(dp[i - face], min);
            }
            if (min == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }
        return dp[amount];
    }

}
