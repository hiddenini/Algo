package com.xz.UnionFind;

/**
 * QuickUnion 根据rank做优化 并且加上路径减半Path Halving
 * <p>
 * 路径减半的意思是在find时路径上的每隔一个节点就指向其祖父节点,从而降低树的高度
 */
public class UnionFind_QU_RANK_PH extends UnionFind_QU_RANK {

    public UnionFind_QU_RANK_PH(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangCheck(v);
        while (v != parents[v]) {
            parents[v] = parents[parents[v]];
            //节点-->祖父节点-->祖父节点的祖父节点
            v = parents[v];
        }
        return v;
    }
}
