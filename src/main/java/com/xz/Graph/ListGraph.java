package com.xz.Graph;

import java.util.*;

public class ListGraph<V, E> extends Graph<V, E> {
    /**
     * 存放图的所有顶点
     */
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();

    /**
     * 存放图的所有边
     */
    private Set<Edge<V, E>> edges = new HashSet<>();

    /**
     * 全局的比较器
     */
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
        return weightManager.compare(e1.weight, e2.weight);
    };

    public ListGraph() {
    }

    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }


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
     * 拓扑排序 要求是无向无环连通图
     * <p>
     * 将AOV网中的所有活动排成一个序列,使得每个活动的前驱活动都排在该活动的前面
     * 卡恩算法
     * <p>
     * 假设L是存放拓扑排序结果的列表
     * 1--把所有入度为0的顶点放入L中，然后把这些顶点从图中删掉
     * 2--重复步骤1，直到找不到入度为0的顶点
     * 如果此时L中的元素个数和顶点的总数相同，说明拓扑排序完成
     * 如果此时L中的元素个数少于顶点的总数，说明原图中存在环，无法进行拓扑排序
     * <p>
     * 下面的做法和卡恩算法类似，但是没有对节点进行删除，而是用一个map保存了顶点和顶点的入度
     */
    @Override
    public List<V> topologicalSort() {
        //最终输出的结果
        List<V> list = new ArrayList<>();
        //保存入度为0的顶点
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        //保存顶点和顶点的入度
        Map<Vertex<V, E>, Integer> map = new HashMap<>();

        //遍历顶点集合,将所有入度为0的顶点入队
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            int size = vertex.inEdges.size();
            if (size == 0) {
                queue.offer(vertex);
            } else {
                map.put(vertex, size);
            }
        });

        /**
         * 队列不为空则出队,将出队顶点的value添加到list中,遍历出队顶点的outEdges,
         *
         * 拿到每一个edge.to将其入度-1 如果等于0则入队否则将入度再次写入map
         */
        while (!queue.isEmpty()) {
            Vertex<V, E> poll = queue.poll();
            list.add(poll.value);
            poll.outEdges.forEach(edge -> {
                Integer toIn = map.get(edge.to) - 1;
                if (toIn == 0) {
                    queue.offer(edge.to);
                } else {
                    map.put(edge.to, toIn);
                }
            });
        }
        return list;
    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return kruskal();
    }

    @Override
    public Map<V, E> shortestPathOnlyWeight(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        /**
         * 最終返回的map,這裏的元素都是已經被拽起來的石頭
         */
        HashMap<V, E> selectPaths = new HashMap<>();
        /**
         * 臨時map，存放鬆弛過程中頂點和距離
         */
        HashMap<Vertex<V, E>, E> paths = new HashMap<>();

        //初始化paths
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            paths.put(outEdge.to, outEdge.weight);
        }

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, E> minEntry = getMinPathOnlyWeight(paths);
            Vertex<V, E> minVertex = minEntry.getKey();

            //minEntry離開桌面
            selectPaths.put(minVertex.value, minEntry.getValue());
            //從臨時map中刪除該元素
            paths.remove(minVertex);
            //對minVertex的outEdges進行鬆弛操作
            for (Edge<V, E> edge : minVertex.outEdges) {
                /**
                 * 如果edge.to已經離開桌面,則不需要進行鬆弛(如果是無向圖則會出現已經被拽起的石頭的邊再次被選擇到進行鬆弛)
                 *
                 * 并且需要排除beginVertex
                 */
                if (selectPaths.containsKey(edge.to.value) || edge.to.equals(beginVertex)) continue;
                //新的可選的最短路徑,beginVertex -->edge.from +edge.weight
                E newWeight = weightManager.add(minEntry.getValue(), edge.weight);
                //之前的最短路徑 beginVertex -->edge.to  oldWeight可能為空,因爲beginVertex到edge.to之前可能沒有路徑
                E oldWeight = paths.get(edge.to);
                if (oldWeight == null || weightManager.compare(newWeight, oldWeight) < 0) {
                    paths.put(edge.to, newWeight);
                }
            }
        }
        //如果上面不排除beginVertex,那麽這裏進行remove操作也可以
        // selectPaths.remove(beginVertex.value);
        return selectPaths;
    }

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        /**
         * 最終返回的map,這裏的元素都是已經被拽起來的石頭
         */
        HashMap<V, PathInfo<V, E>> selectPaths = new HashMap<>();
        /**
         * 臨時map，存放鬆弛過程中頂點和距離
         */
        HashMap<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();

        //初始化paths
        for (Edge<V, E> outEdge : beginVertex.outEdges) {
            PathInfo<V, E> pathInfo = new PathInfo<>();
            pathInfo.weight = outEdge.weight;
            pathInfo.edgeInfos.add(outEdge.info());
            paths.put(outEdge.to, pathInfo);
        }

        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = getMinPath(paths);
            Vertex<V, E> minVertex = minEntry.getKey();

            //minEntry離開桌面
            selectPaths.put(minVertex.value, minEntry.getValue());
            //從臨時map中刪除該元素
            paths.remove(minVertex);
            //對minVertex的outEdges進行鬆弛操作
            for (Edge<V, E> edge : minVertex.outEdges) {
                /**
                 * 如果edge.to已經離開桌面,則不需要進行鬆弛(如果是無向圖則會出現已經被拽起的石頭的邊再次被選擇到進行鬆弛)
                 *
                 * 并且需要排除beginVertex
                 */
                if (selectPaths.containsKey(edge.to.value)) continue;
                //新的可選的最短路徑,beginVertex -->edge.from +edge.weight
                E newWeight = weightManager.add(minEntry.getValue().weight, edge.weight);
                //之前的最短路徑 beginVertex -->edge.to  oldWeight可能為空,因爲beginVertex到edge.to之前可能沒有路徑
                PathInfo<V, E> oldPath = paths.get(edge.to);
                if (oldPath == null || weightManager.compare(newWeight, oldPath.weight) < 0) {
                    PathInfo<V, E> pathInfo = new PathInfo<>();
                    pathInfo.weight = newWeight;
                    pathInfo.edgeInfos.addAll(minEntry.getValue().edgeInfos);
                    pathInfo.edgeInfos.add(edge.info());
                    paths.put(edge.to, pathInfo);
                }
            }
        }
        //如果上面不排除beginVertex,那麽這裏進行remove操作也可以,优先选择这个,上面的判断可能每条边都需要判断,去掉判断后只有出边的to为起始顶点的边会重读写入到
        selectPaths.remove(beginVertex.value);
        return selectPaths;
    }

    /**
     * 鬆弛操作
     */
    private void relax() {

    }

    /**
     * 從paths挑出一個最小的路徑
     */
    private Map.Entry<Vertex<V, E>, E> getMinPathOnlyWeight(HashMap<Vertex<V, E>, E> paths) {
        Iterator<Map.Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, E> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, E> entry = it.next();
            if (weightManager.compare(entry.getValue(), minEntry.getValue()) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(HashMap<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, PathInfo<V, E>> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight, minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * 最小生成树 prim算法
     * <p>
     * 切分定理:切分（Cut）:把图中的节点分为两部分，称为一个切分
     * <p>
     * 横切边:如果一个边的两个顶点，分别属于切分的两部分，这个边称为横切边
     * <p>
     * 切分定理：给定任意切分，横切边中权值最小的边必然属于最小生成树
     */
    public Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V, E>> iterator = vertices.values().iterator();
        if (!iterator.hasNext()) return null;
        Vertex<V, E> vertex = iterator.next();
        //最后返回的set
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        //已经被添加过的顶点
        Set<Vertex<V, E>> addedVertices = new HashSet<>();
        addedVertices.add(vertex);
        //将拿到的vertex的所有出边放到最小堆中
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(vertex.outEdges, edgeComparator);
        int size = vertices.size();
        //当addedVertices中的节点数量等于所有节点的时候退出
        while (!minHeap.isEmpty() && addedVertices.size() < size) {
            Edge<V, E> edge = minHeap.remove();
            //如果已经添加过则continue
            if (addedVertices.contains(edge.to)) continue;
            //加入到最终的结果集中
            edgeInfos.add(edge.info());
            //将该边的to顶点设置为已经添加过
            addedVertices.add(edge.to);
            //将新添加的顶点的所有的outEdges加入到最小堆中
            minHeap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }

    /**
     * 最小生成树 Kruskal算法
     *
     * @return
     */
    public Set<EdgeInfo<V, E>> kruskal() {
        /**
         * 如果圖沒有2個頂點則直接return
         */
        int edgeSize = vertices.size() - 1;
        if (edgeSize <= 1) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        /**
         * 最小堆,存放邊
         */
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(edges, edgeComparator);
        UnionFind<Vertex<V, E>> unionFind = new UnionFind<>();
        /**
         * 初始化并查集,讓每個頂點成爲一個集合
         */
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            unionFind.makeSet(vertex);
        });
        while (!minHeap.isEmpty() && edgeInfos.size() < edgeSize) {
            //拿到最小的那條邊
            Edge<V, E> edge = minHeap.remove();
            //如果該條邊的from和to本來是屬於一個集合的那麽會構成環，直接continue
            if (unionFind.isSame(edge.from, edge.to)) continue;
            //加入結果集
            edgeInfos.add(edge.info());
            //將edge.from,和edge.to并入到一個集合
            unionFind.union(edge.from, edge.to);
        }
        return edgeInfos;
    }


    /**
     * 递归形式的深度优先搜索  类似二叉树的前序遍历先访问自身，再访问左子树,再访问右子树
     * <p>
     * 图是先访问顶点,再访问outEdges 中的某条边的edge.to
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

        public EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
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
