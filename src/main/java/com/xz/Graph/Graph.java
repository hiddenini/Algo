package com.xz.Graph;

import java.util.List;

public interface Graph<V, E> {
    int edgesSize();

    int verticesSize();

    void addVertex(V v);

    void addEdge(V from, V to);

    void addEdge(V from, V to, E wight);

    void removeVertex(V v);

    void removeEdge(V from, V to);

    void bfs(V begin, VertexVisitor<V> visitor);

    void dfs(V begin, VertexVisitor<V> visitor);

    List<V> topologicalSort();

    interface VertexVisitor<V> {
        boolean visit(V v);
    }
}
