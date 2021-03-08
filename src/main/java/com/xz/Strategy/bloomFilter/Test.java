package com.xz.Strategy.bloomFilter;

/**
 * 如果使用布隆过滤器爬取网页 爬取过的肯定不会再重复爬取，但是没有爬取过的可能会误判为已经爬取
 */
public class Test {
    public static void main(String[] args) {
        BloomFilter<Integer> bf = new BloomFilter<>(1_0000_0000, 0.01);
        for (int i = 1; i < 1_00_0000; i++) {
            bf.put(i);
        }

        /**
         * 看看误判的个数
         */
        int count = 0;
        for (int i = 1_00_0001; i <= 2_00_0000; i++) {
            if (bf.contains(i)) {
                count++;
            }
        }
        System.out.println(count);
    }
}
