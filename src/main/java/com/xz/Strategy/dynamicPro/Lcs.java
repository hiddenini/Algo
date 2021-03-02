package com.xz.Strategy.dynamicPro;

/**
 * 求两个序列的最长公共子序列长度
 * [1, 3, 5, 9, 10] 和 [1, 4, 9, 10] 的最长公共子序列是 [1, 9, 10]，长度为 3
 * <p>
 * <p>
 * ◼ 假设 dp(i, j) 是【nums1 前 i 个元素】与【nums2 前 j 个元素】的最长公共子序列长度
 * dp(i, 0)、dp(0, j) 初始值均为 0 如果 nums1[i – 1] = nums2[j – 1]，那么 dp(i, j) = dp(i – 1, j – 1) + 1
 * 如果 nums1[i – 1] ≠ nums2[j – 1]，那么 dp(i, j) = max { dp(i – 1, j), dp(i, j – 1) }
 */
public class Lcs {
    public static void main(String[] args) {
        int nums1[] = new int[]{1, 4, 5, 9, 10};
        int nums2[] = new int[]{1, 4, 9, 10};
        System.out.println(lcs(nums1, nums2));
        System.out.println(lcs1(nums1, nums2));
    }

    /**
     * 暴力递归
     *
     * @param nums1
     * @param nums2
     * @return
     */
    static int lcs(int nums1[], int nums2[]) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        return lcs(nums1, nums1.length, nums2, nums2.length);
    }

    private static int lcs(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) return 0;
        if (nums1[i - 1] != nums2[j - 1]) {
            return Math.max(lcs(nums1, i - 1, nums2, j), lcs(nums1, i, nums2, j - 1));
        }
        return lcs(nums1, i - 1, nums2, j - 1) + 1;
    }

    /**
     * 非递归 动态规划
     */
    static int lcs1(int nums1[], int nums2[]) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[nums1.length][nums2.length];
    }
}
