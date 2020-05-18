package com.xz.UnionFind;

/**
 * QuickFind
 * 查找实现O(1) ,但是union速度较慢O(N)
 */
public class UnionFind_QF extends UnionFind {

    public UnionFind_QF(int capacity) {
        super(capacity);
    }

    /**
     * 查找v所属的集合(根节点),QF的实现 find就是返回父节点parents[v]
     */
    public int find(int v) {
        rangCheck(v);
        return parents[v];
    }

    /**
     * 判断是否属于一个集合
     */
    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /**
     * 将v1所在集合的所有元素都嫁接到v2的父节点上
     */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        /**
         * 将所有父节点为p1的节点的父节点设置为p2
         */
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                parents[i] = p2;
            }
        }
    }

}
