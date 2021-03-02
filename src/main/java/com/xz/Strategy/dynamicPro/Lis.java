package com.xz.Strategy.dynamicPro;

public class Lis {
    public static void main(String[] args) {
        System.out.println(lengthOfLis(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 假设数组是 nums， [10, 2, 2, 5, 1, 7, 101, 18]
     * dp(i) 是以 nums[i] 结尾的最长上升子序列的长度，i ∈ [0, nums.length)
     * ✓ 以 nums[0] 10 结尾的最长上升子序列是 10，所以 dp(0) = 1 ✓
     * 以 nums[1] 2 结尾的最长上升子序列是 2，所以 dp(1) = 1 ✓
     * 以 nums[2] 2 结尾的最长上升子序列是 2，所以 dp(2) = 1 ✓
     * 以 nums[3] 5 结尾的最长上升子序列是 2、5，所以 dp(3) = dp(1) + 1 = dp(2) + 1 = 2 ✓
     * 以 nums[4] 1 结尾的最长上升子序列是 1，所以 dp(4) = 1 ✓
     * 以 nums[5] 7 结尾的最长上升子序列是 2、5、7，所以 dp(5) = dp(3) + 1 = 3 ✓
     * 以 nums[6] 101 结尾的最长上升子序列是 2、5、7、101，所以 dp(6) = dp(5) + 1 = 4 ✓
     * 以 nums[7] 18 结尾的最长上升子序列是 2、5、7、18，所以 dp(7)
     * <p>
     * 状态转移方程
     * 遍历 j ∈ [0, i) 当 nums[i] > nums[j] ✓ nums[i] 可以接在 nums[j] 后面，形成一个比 dp(j) 更长的上升子序列，长度为 dp(j) + 1
     * ✓ dp(i) = max { dp(i), dp(j) + 1 }
     * 当 nums[i] ≤ nums[j] ✓ nums[i] 不能接在 nums[j] 后面，跳过此次遍历（continue)
     * <p>
     * 状态的初始值
     * dp(0) = 1
     * 所有的 dp(i) 默认都初始化为 1
     *
     * @param nums
     * @return
     */
    static int lengthOfLis(int nums[]) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) continue;
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
