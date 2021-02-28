package com.xz.Strategy.greedy;

import java.util.Arrays;

/**
 * 海盗船载重30,古董的重量是 3, 5, 4, 10, 7, 14, 2, 11，请问最多能装多少件古董
 */
public class Pirate {
    public static void main(String[] args) {
        int[] weights = {3, 5, 4, 10, 7, 14, 2, 11};
        Arrays.sort(weights);
        int capacity = 30, weight = 0, count = 0;

        /**
         * 如果已经装满了，则后续的古董不需要再遍历了weight < capacity
         */
        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weights[i] + weight;
            if (newWeight <= capacity) {
                weight = newWeight;
                count++;
            }
        }

        System.out.println("一共选了" + count + "件古董");
    }
}
