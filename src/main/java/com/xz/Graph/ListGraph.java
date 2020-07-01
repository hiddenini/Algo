package com.xz.Graph;

import java.util.*;

public class ListGraph<V, E> implements Graph<V, E> {
    /**
     * 存放图的所有顶点
     */
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();

    /**
     * 存放图的所有边
     */
    private Set<Edge<V, E>> edges = new HashSet<>();

    public void print() {
        System.out.println("num of vertex:" + vertices.size());
        System.out.println("num of edge:" + edges.size());

        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out------------");
            System.out.println(vertex.outEdges);
            System.out.println("in------------");
            System.out.println(vertex.inEdges);

        });

        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }

    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E wight) {
        //判断顶点from和to是否存在
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }

        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }

        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = wight;
        /**
         * 判断这条边是否存在,HashSet判断是否存在的依据是Edge的equal方法,而不是内存地址
         */
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);

    }

    @Override
    public void removeVertex(V v) {
        Vertex<V, E> remove = vertices.remove(v);
        if (remove == null) return;

        Vertex<V, E> vertex = vertices.get(v);
        Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator();
        /**
         * 遍历顶点vertex的所有入边
         */
        while (iterator.hasNext()) {
            Edge<V, E> next = iterator.next();
            //从自己的入边的集合中删除掉
            iterator.remove();
            //删除vertex的所有入边的from顶点的出边的集合中的该条边
            next.from.outEdges.remove(next);
            //删除全局边集合中的该条边
            edges.remove(next);
        }
        /**
         * 遍历顶点vertex的所有出边
         */
        iterator = vertex.outEdges.iterator();
        while (iterator.hasNext()) {
            //从自己的出边的集合中删除掉
            Edge<V, E> next = iterator.next();
            iterator.remove();
            //删除vertex的所有出边的to顶点的入边的集合中的该条边
            next.to.inEdges.remove(next);
            //删除全局边集合中的该条边
            edges.remove(next);
        }

    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        /**
         * 先从fromVertex顶点的出边的集合中去删除,如果能删除成功,
         *
         * 则去toVertex顶点的入边的集合中去删除,
         *
         * 再去全局的边的集合中删除
         */
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }

    }

    /**
     * 广度优先遍历
     */
    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);
        while (!queue.isEmpty()) {
            Vertex<V, E> poll = queue.poll();
            if (visitor.visit(poll.value)) return;
            poll.outEdges.forEach((edge) -> {
                if (visitedVertices.contains(edge.to)) return;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            });
        }
    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {
        if (visitor == null) return;
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        Set<Vertex<V, E>> visitedVertices = new HashSet<>();
        //doDfsRecursive(beginVertex, visitor, visitedVertices);
        doDfs(beginVertex, visitor, visitedVertices);
    }

    /**
     * 递归形式的深度优先搜索  类似二叉树的前序遍历先访问自身，再访问左子树,再访问右子树
     * <p>
     * 图是先访问订单,再访问outEdges 中的某条边的edge.to
     */
    void doDfsRecursive(Vertex<V, E> beginVertex, VertexVisitor<V> visitor, Set<Vertex<V, E>> visitedVertices) {
        visitor.visit(beginVertex.value);
        visitedVertices.add(beginVertex);
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            if (visitedVertices.contains(outEdge.to)) continue;
            doDfsRecursive(outEdge.to, visitor, visitedVertices);
        }
    }

    /**
     * 非递归形式的深度优先搜索  使用栈实现
     * <p>
     * 先访问输入的顶点,将该顶点放入visitedVertices 然后
     * <p>
     * 1--入栈
     * <p>
     * 2--栈不为空,则出栈
     * <p>
     * 3--访问出栈的订单的outEdge中的某一条边,如果这条边的outEdge.to 没有被访问过
     * <p>
     * 4--将from入栈 将to入栈
     * <p>
     * 5--访问to 并将to放入 visitedVertices
     */
    void doDfs(Vertex<V, E> beginVertex, VertexVisitor<V> visitor, Set<Vertex<V, E>> visitedVertices) {
        Stack<Vertex<V, E>> stack = new Stack<>();
        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(beginVertex.value)) return;
        while (!stack.isEmpty()) {
            Vertex<V, E> pop = stack.pop();
            for (Edge<V, E> outEdge : pop.outEdges) {
                if (visitedVertices.contains(outEdge.to)) continue;
                stack.push(outEdge.from);
                stack.push(outEdge.to);
                visitedVertices.add(outEdge.to);
                if (visitor.visit(outEdge.to.value)) return;
                break;
            }
        }
    }

    private static class Vertex<V, E> {
        V value;
        Set<Edge<V, E>> inEdges = new HashSet<>();
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }

        /**
         * 2个顶点的value相同则表示顶点相同
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>) obj).value);
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;
        Vertex<V, E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        /**
         * 2条边的起点和终点相等则表示是同一条边
         *
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = (Edge<V, E>) obj;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
