package com.xz.Strategy.dynamicPro;

public class Lis {
    public static void main(String[] args) {
        System.out.println(lengthOfLis(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
        System.out.println(lengthOfLis1(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
        System.out.println(lengthOfLis2(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));

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

    /**
     * 把每个数字看做是一张扑克牌，从左到右按顺序处理每一个扑克牌
     * 将它压在（从左边数过来）第一个牌顶 ≥ 它的牌堆上面
     * 如果找不到牌顶 ≥ 它的牌堆，就在最右边新建一个牌堆，将它放入这个新牌堆中
     * ◼ 当处理完所有牌，最终牌堆的数量就是最长上升子序列的长度
     * <p>
     * ◼ 思路（假设数组是 nums，也就是最初的牌数组）
     * top[i] 是第 i 个牌堆的牌顶，len 是牌堆的数量，初始值为 0 遍历每一张牌 num
     * ✓ 利用二分搜索找出 num 最终要放入的牌堆位置 index
     * ✓ num 作为第 index 个牌堆的牌顶，top[index] = num
     * ✓ 如果 index 等于 len，相当于新建一个牌堆，牌堆数量 +1，也就是 len++
     *
     * @param nums
     * @return
     */
    static int lengthOfLis1(int nums[]) {
        if (nums == null || nums.length == 0) return 0;
        //牌堆的数量
        int len = 0;
        //排顶数组
        int[] top = new int[nums.length];
        //遍历所有的牌
        for (int num : nums) {
            //遍历所有的牌堆
            int j = 0;
            while (j < len) {
                //找到第一个大于等于num的牌堆
                if (top[j] >= num) {
                    top[j] = num;
                    break;
                }
                j++;
            }
            if (j == len) {
                len++;
                top[j] = num;
            }
        }
        return len;
    }

    /**
     * 使用二分搜索优化 牌堆数组是递增的,所以可以使用二分搜索找到插入位置
     *
     * @param nums
     * @return
     */
    static int lengthOfLis2(int nums[]) {
        if (nums == null || nums.length == 0) return 0;
        //牌堆的数量
        int len = 0;
        //排顶数组
        int[] top = new int[nums.length];
        //遍历所有的牌
        for (int num : nums) {
            int begin = 0;
            int end = len;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num <= top[mid]) {
                    end = mid;
                } else {
                    //大于等于则往右边找
                    begin = mid + 1;
                }
            }
            //循环结束时的begin==end 就是该元素的插入位置
            //覆盖牌顶
            top[begin] = num;
            //新建一个牌堆
            if (begin == len) len++;
        }
        return len;
    }
}
