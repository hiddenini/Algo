package com.xz.UnionFind;

/**
 * QuickUnion 根据rank做优化 并且加上路径分裂Path Splitting
 * <p>
 * 路径分裂的意思是在find时路径上的所有节点都指向其祖父节点,从而降低树的高度
 */
public class UnionFind_QU_RANK_PS extends UnionFind_QU_RANK {

    public UnionFind_QU_RANK_PS(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangCheck(v);
        while (v != parents[v]) {
            //备份下该节点的父节点,不备份的话会父节点会丢失,因为该节点直接指向了祖父节点
            int p = parents[v];
            parents[v] = parents[parents[v]];
            //修改该节点的父节点的父节点(也是使其指向祖父节点)
            v = p;
        }
        return v;
    }
}
