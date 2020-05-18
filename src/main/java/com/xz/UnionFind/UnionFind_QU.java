package com.xz.UnionFind;

/**
 * QuickUnion
 * 查找和union都是O(logN)
 * 后面进行优化后每个操作的均摊复杂度为O(α(n))  α(n)<5
 */
public class UnionFind_QU extends UnionFind {
    public UnionFind_QU(int capacity) {
        super(capacity);
    }

    /**
     * 通过parent链条不断向上找，直到找到根节点
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
        /**
         * 将左边节点的结根点的值设置为右边节点的根节点的值
         */
        parents[p1] = p2;
    }
}
