package com.xz.Strategy.dynamicPro;

public class MaxSubArray {
    public static void main(String[] args) {
        int nums[] = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));
        System.out.println(maxSubArray1(nums));

    }

    /**
     * 时间复杂度 空间复杂度:O(n)
     * 状态定义
     * 假设 dp(i) 是以 nums[i] 结尾的最大连续子序列和（nums是整个序列）
     * <p>
     * ✓ 以 nums[0] –2 结尾的最大连续子序列是 –2，所以dp(0) = –2 ✓
     * 以 nums[1] 1 结尾的最大连续子序列是 1，所以 dp(1) = 1 ✓
     * 以 nums[2] –3 结尾的最大连续子序列是 1、–3，所以 dp(2) = dp(1) + (–3) = –2 ✓
     * 以 nums[3] 4 结尾的最大连续子序列是 4，所以 dp(3) = 4 ✓
     * 以 nums[4] –1 结尾的最大连续子序列是 4、–1，所以 dp(4) = dp(3) + (–1) = 3 ✓
     * 以 nums[5] 2 结尾的最大连续子序列是 4、–1、2，所以 dp(5) = dp(4) + 2 = 5 ✓
     * 以 nums[6] 1 结尾的最大连续子序列是 4、–1、2、1，所以 dp(6) = dp(5) + 1 = 6 ✓
     * 以 nums[7] –5 结尾的最大连续子序列是 4、–1、2、1、–5，所以 dp(7) = dp(6) + (–5) = 1 ✓
     * 以 nums[8] 4 结尾的最大连续子序列是 4、–1、2、1、–5、4，所以 dp(8) = dp(7)
     *
     * <p>
     * 初始状态
     * dp(0) 的值是 nums[0]
     * <p>
     * 状态转移方程
     * 如果 dp(i – 1) ≤ 0，那么 dp(i) = nums[i]
     * 如果 dp(i – 1) > 0，那么 dp(i) = dp(i-1)+nums[i]
     *
     * @param nums
     * @return
     */
    static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        System.out.println("dp[0]=" + dp[0]);
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] < 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] + nums[i];
            }
            max = Math.max(dp[i], max);
            //打印num[i] 的dp
            System.out.println("dp[" + i + "]=" + dp[i]);

        }
        return max;
    }

    /**
     * 优化空间复杂度     *时间复杂度 :O(n) 空间复杂度:O(1)
     *
     * @param nums
     * @return
     */
    static int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        //只需要保存前一个dp,不需要知道之前的dp的值,节省空间
        int dp = nums[0];
        int max = dp;
        for (int i = 1; i < nums.length; i++) {
            if (dp < 0) {
                dp = nums[i];
            } else {
                dp = dp + nums[i];
            }
            max = Math.max(dp, max);
        }
        return max;
    }
}
