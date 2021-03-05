package com.xz.Strategy.dynamicPro;

/**
 * 有 n 件物品和一个最大承重为 W 的背包，每件物品的重量是 𝑤i、价值是 𝑣i
 * 在保证总重量不超过 W 的前提下，选择某些物品装入背包，背包的最大总价值是多少？
 * 注意：每个物品只有 1 件，也就是每个物品只能选择 0 件或者 1 件
 */
public class Knapsack {
    public static void main(String[] args) {
        int[] values = {6, 3, 5, 4, 6};
        int[] weights = {2, 2, 6, 5, 4};
        int capacity = 10;
        System.out.println(select(values, weights, capacity));
        System.out.println(select1(values, weights, capacity));
        System.out.println(select2(values, weights, capacity));
        System.out.println(select3(values, weights, 3));


    }

    /**
     * 假设 values 是价值数组，weights 是重量数组
     * 编号为 k 的物品，价值是 values[k]，重量是 weights[k]，k ∈ [0, n)
     * ◼ 假设 dp(i, j) 是 最大承重为 j、有前 i 件物品可选 时的最大总价值，i ∈ [1, n]，j ∈ [1, W]
     * dp(i, 0)、dp(0, j) 初始值均为 0
     * 如果 j < weights[i – 1]，那么 dp(i, j) = dp(i – 1, j)
     * 如果 j ≥ weights[i – 1]
     * 即最后一件可以选，那么有两种情况
     * 1--选择最后一件 dp(i, j)=dp(i – 1, j – weights[i – 1]) + values[i – 1]
     * 2--不选择最后一件 dp(i, j)=dp(i – 1, j)
     * 所以  dp(i, j) = max { dp(i – 1, j), dp(i – 1, j – weights[i – 1]) + values[i – 1] }
     *
     * @return
     */
    static int select(int values[], int weights[], int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (capacity < 0) return 0;
        int[][] dp = new int[values.length + 1][capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j],
                            values[i - 1] + dp[i - 1][j - weights[i - 1]]);
                }
            }
        }
        return dp[values.length][capacity];
    }

    /**
     * dp(i, j) 都是由 dp(i – 1, k) 推导出来的，也就是说，第 i 行的数据是由它的上一行第 i – 1 行推导出来的
     * 因此，可以使用一维数组来优化
     * 这里和之前几个优化不太一样，是因为在这里使用到的上一行的数据是不规律的，之前基本上都是leftTop，但是这里是k
     * 另外，由于 k ≤ j ，所以 j 的遍历应该由大到小，否则导致数据错乱
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    static int select1(int values[], int weights[], int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (capacity < 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= 1; j--) {
                if (j < weights[i - 1]) {
                    //dp[j] = dp[j]; 这种赋值没有意义
                    continue;
                } else {
                    dp[j] = Math.max(dp[j],
                            values[i - 1] + dp[j - weights[i - 1]]);
                }
            }
        }
        return dp[capacity];
    }

    /**
     * 继续优化
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    static int select2(int values[], int weights[], int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (capacity < 0) return 0;
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            //j >= weights[i - 1] 只有当前的capacity大于最后一件物品的重量时,才有选择权,否则全部不能选择,等于上一行的值 即dp[j] = dp[j]
            for (int j = capacity; j >= weights[i - 1]; j--) {
                dp[j] = Math.max(dp[j],
                        values[i - 1] + dp[j - weights[i - 1]]);
            }
        }
        return dp[capacity];
    }

    /**
     * 刚好装满 只需要修改下初始值和返回值即可  不鞥凑到时设置为负无穷 不能是-1 -2之类的
     *
     * 需要结合二维数组来看
     *
     * @param values
     * @param weights
     * @param capacity
     * @return
     */
    static int select3(int values[], int weights[], int capacity) {
        if (values == null || values.length == 0) return -1;
        if (weights == null || weights.length == 0) return -1;
        if (capacity < 0) return -1;
        int[] dp = new int[capacity + 1];
        for (int j = 1; j <= capacity; j++) {
            dp[j] = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= values.length; i++) {
            //j >= weights[i - 1] 只有当前的capacity大于最后一件物品的重量时,才有选择权,否则全部不能选择,等于上一行的值 即dp[j] = dp[j]
            for (int j = capacity; j >= weights[i - 1]; j--) {
                dp[j] = Math.max(dp[j],
                        values[i - 1] + dp[j - weights[i - 1]]);
            }
        }
        return dp[capacity] < 0 ? -1 : dp[capacity];
    }
}
