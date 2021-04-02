package com.xz.Strategy.dynamicPro;

/***
 * https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/submissions/
 *
 *最长公共子串
 *
 *子串是连续的子序列
 * ◼ 求两个字符串的最长公共子串长度
 * ABCBA 和 BABCA 的最长公共子串是 ABC，长度为 3
 *
 * 最长公共子串 – 思路
 * ◼ 假设 2 个字符串分别是 str1、str2
 * i ∈ [1, str1.length]
 * j ∈ [1, str2.length]
 * ◼ 假设 dp(i, j) 是以 str1[i – 1]、str2[j – 1] 结尾的最长公共子串长度
 * dp(i, 0)、dp(0, j) 初始值均为 0 如果 str1[i – 1] = str2[j – 1]，那么 dp(i, j) = dp(i – 1, j – 1) + 1
 * 如果 str1[i – 1] ≠ str2[j – 1]，那么 dp(i, j) = 0
 * ◼ 最长公共子串的长度是所有 dp(i, j) 中的最大值 max { dp(i, j) }
 *
 */
public class Lcss {
    public static void main(String[] args) {
        System.out.println(lcss("ABCB", "BABC"));
        System.out.println(lcss1("ABCB", "BABC"));
        System.out.println(lcss2("ABCB", "BABC"));

    }

    /**
     * 空间复杂度：O(n*m)
     * 时间复杂度：O(n*m)
     *
     * @param s1
     * @param s2
     * @return
     */
    static int lcss(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        char[] chars1 = s1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = s2.toCharArray();
        if (chars2.length == 0) return 0;
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];
        int max = 0;
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max, dp[i][j]);
                } else {
                    //=0 数组初始化时默认就等于0
                }
            }
        }
        return max;
    }


    public int findLength(int[] A, int[] B) {
        if (A == null || B == null) return 0;
        if (A.length == 0 || B.length == 0) return 0;
        int[][] dp = new int[A.length + 1][B.length + 1];
        int max = 0;
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i - 1] == B[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max, dp[i][j]);
                } else {
                    //=0 数组初始化时默认就等于0
                }
            }
        }
        return max;
    }


    /**
     * 使用一维数组
     * 空间复杂度：O(k) k=min(n,m)
     * 时间复杂度：O(n*m)
     *
     * @param s1
     * @param s2
     * @return
     */
    static int lcss1(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        char[] chars1 = s1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = s2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] rowhars = chars1, colsChas = chars2;
        //让较短的数组长度作为列
        if (chars1.length < chars2.length) {
            colsChas = chars1;
            rowhars = chars2;
        }

        int[] dp = new int[colsChas.length + 1];
        int max = 0;
        for (int row = 1; row <= rowhars.length; row++) {
            int cur = 0;
            for (int col = 1; col <= colsChas.length; col++) {
                int leftTop = cur;
                cur = dp[col];
                if (chars1[row - 1] != chars2[col - 1]) {
                    dp[col] = 0;
                } else {
                    dp[col] = leftTop + 1;
                    max = Math.max(dp[col], max);
                }
            }
        }
        return max;
    }

    /**
     * 和0-1背包类似，在使用一维数组时,从后往前算，去掉leftTop
     *
     * @param s1
     * @param s2
     * @return
     */
    static int lcss2(String s1, String s2) {
        if (s1 == null || s2 == null) return 0;
        char[] chars1 = s1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = s2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] rowhars = chars1, colsChas = chars2;
        //让较短的数组长度作为列
        if (chars1.length < chars2.length) {
            colsChas = chars1;
            rowhars = chars2;
        }

        int[] dp = new int[colsChas.length + 1];
        int max = 0;
        for (int row = 1; row <= rowhars.length; row++) {
            for (int col = colsChas.length; col >= 1; col--) {
                if (chars1[row - 1] != chars2[col - 1]) {
                    dp[col] = 0;
                } else {
                    dp[col] = dp[col - 1] + 1;
                    max = Math.max(dp[col], max);
                }
            }
        }
        return max;
    }
}
