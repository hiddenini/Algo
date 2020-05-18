package com.xz.UnionFind;

/**
 * QuickUnion 根据size做优化
 */
public class UnionFind_QU_SIZE extends UnionFind {
    //记录以当前节点为根节点的树的元素个数
    private int[] sizes;

    public UnionFind_QU_SIZE(int capacity) {
        super(capacity);
        sizes = new int[capacity];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 1;
        }
    }

    /**
     * 通过parent链表不断向上找，直到找到根节点
     */
    @Override
    public int find(int v) {
        rangCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    /**
     * 将v1的根节点嫁接到v2的根节点上
     */
    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        if (sizes[p1] < sizes[p2]) {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        } else {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        }
    }
}
