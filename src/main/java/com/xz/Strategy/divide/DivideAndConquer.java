package com.xz.Strategy.divide;

/**
 * 分治，也就是分而治之。它的一般步骤是
 * ① 将原问题分解成若干个规模较小的子问题（子问题和原问题的结构一样，只是规模不一样）
 * ② 子问题又不断分解成规模更小的子问题，直到不能再分解（直到可以轻易计算出子问题的解）
 * ③ 利用子问题的解推导出原问题的解
 * 分治的应用
 * 快速排序
 * 归并排序
 * Karatsuba算法
 * <p>
 * <p>
 * 给定一个长度为 n 的整数序列，求它的最大连续子序列和
 * <p>
 * 比如 –2、1、–3、4、–1、2、1、–5、4 的最大连续子序列和是 4 + (–1) + 2 + 1 = 6
 */
public class DivideAndConquer {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums));
        System.out.println(maxSubArray1(nums));
        System.out.println(maxSubArray(nums, 0, nums.length));
    }

    /**
     * 暴力解法,从第一个数字开始往后分割连续子序列,列出所有情况后计算和，比较得到最大值
     * O(n^3)
     *
     * @param nums
     * @return
     */
    static int maxSubArray(int nums[]) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max, sum);

            }
        }
        return max;
    }

    /**
     * 重复利用前面计算出来的结果 O(n^2)
     *
     * @param nums
     * @return
     */
    static int maxSubArray1(int nums[]) {
        if (nums == null || nums.length == 0) return 0;
        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                sum += nums[end];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    /**
     * 分治
     * 将序列均匀地分割成 2 个子序列
     * [begin , end) = [begin , mid) + [mid , end)，mid = (begin + end) >> 1
     * 假设 [begin , end) 的最大连续子序列和是 S[i , j) ，那么它有 3 种可能
     * [i , j) 存在于 [begin , mid) 中，同时 S[i , j) 也是 [begin , mid) 的最大连续子序列和
     * [i , j) 存在于 [mid , end) 中，同时 S[i , j) 也是 [mid , end) 的最大连续子序列和
     * [i , j) 一部分存在于 [begin , mid) 中，另一部分存在于 [mid , end)
     * ✓ [i , j) = [i , mid) + [mid , j) 左闭右开 右闭左开
     * ✓ S[i , mid) = max { S[k , mid) }，begin ≤ k ＜ mid
     * ✓ S[mid , j) = max { S[mid , k) }，mid ＜ k ≤ en
     * <p>
     * T(n) = T(n/2) + T(n/2) + O(n)
     * T(n) = 2T(n/2) + O(n)
     * logba = 1  d = 1
     * ◼ 空间复杂度：O logn ◼ 时间复杂度：O nlogn
     */
    static int maxSubArray(int[] nums, int begin, int end) {
      /*  if (end - begin < 2) return nums[begin];
        int mid = (begin + end) >> 1;
        int leftMax = nums[mid - 1];
        int leftSum = leftMax;
        //从mid-2往前加 因为leftMax初始化为mid-1
        for (int i = mid - 2; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }
        int rightMax = nums[mid];
        int rightSum = rightMax;
        //从mid+1往后加 因为rightMax初始化为mid
        for (int i = mid + 1; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }*/

        if (end - begin < 2) return nums[begin];
        int mid = (begin + end) >> 1;
        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        for (int i = mid - 1; i >= begin; i--) {
            leftSum += nums[i];
            leftMax = Math.max(leftMax, leftSum);
        }

        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        for (int i = mid; i < end; i++) {
            rightSum += nums[i];
            rightMax = Math.max(rightMax, rightSum);
        }
        return Math.max(leftMax + rightMax,
                Math.max(
                        maxSubArray(nums, begin, mid),
                        maxSubArray(nums, mid, end))
        );
    }
}
