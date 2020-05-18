package com.xz.UnionFind;

public abstract class UnionFind {
    protected int parents[];

    public UnionFind(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must larger than zero! ");
        }
        parents = new int[capacity];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /**
     * 查找v所属的集合(根节点)
     */
    public abstract int find(int v);

    /**
     * 判断是否属于一个集合
     */
    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }

    public abstract void union(int v1, int v2);

    protected void rangCheck(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("v is out of bounds");
    }

}
