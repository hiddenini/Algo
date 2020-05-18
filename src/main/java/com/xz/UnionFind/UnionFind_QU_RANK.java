package com.xz.UnionFind;

/**
 * QuickUnion 根据rank做优化
 */
public class UnionFind_QU_RANK extends UnionFind {
    //记录以当前节点为根节点的树高度
    private int[] ranks;

    public UnionFind_QU_RANK(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        /**
         * 将比较矮的树嫁接到比较高的树上
         *
         * 只要两棵树的高度不一致,嫁接之后树的高度不会发生变化
         *
         * 高度一致时高度会发生变化
         */
        if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else {
            /**
             * 高度一致时谁嫁接谁都ok,但是被嫁接的那棵树的高度要加1
             */
            parents[p1] = p2;
            ranks[p2] += 1;
        }
    }
}
