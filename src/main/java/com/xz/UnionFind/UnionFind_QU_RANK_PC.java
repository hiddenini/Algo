package com.xz.UnionFind;

/**
 * QuickUnion 根据rank做优化 并且加上路径压缩PathCompression
 * <p>
 * 路径压缩的意思是在find时路径上的所有节点都指向根节点,从而降低树的高度
 */
public class UnionFind_QU_RANK_PC extends UnionFind_QU_RANK {

    public UnionFind_QU_RANK_PC(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangCheck(v);
        if (v != parents[v]) {
            parents[v] = find(parents[v]);
        }
        return parents[v];
    }
}
