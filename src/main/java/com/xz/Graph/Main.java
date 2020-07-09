package com.xz.Graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }
    };


    static void tesDfs() {
        Graph<Object, Double> graph = undirectedGraph(Data.DFS_01);
        graph.dfs(0, (Object v) -> {
            System.out.println(v);
            return false;
        });
    }

    static void tesBfs() {
        Graph<Object, Double> graph = undirectedGraph(Data.BFS_01);
        graph.dfs("A", (Object v) -> {
            System.out.println(v);
            return false;
        });
    }

    static void testTopo() {
        Graph<Object, Double> graph = directedGraph(Data.TOPO);
        List<Object> list = graph.topologicalSort();
        System.out.println(list);
    }

    static void testMst() {
        Graph<Object, Double> graph = undirectedGraph(Data.MST_01);
        Set<Graph.EdgeInfo<Object, Double>> infos = graph.mst();
        for (Graph.EdgeInfo<Object, Double> info : infos) {
            System.out.println(info);
        }
    }

    static void tesSpOnlyWeight() {
        //Graph<Object, Double> graph = directedGraph(Data.SP);
        Graph<Object, Double> graph = undirectedGraph(Data.SP);
        Map<Object, Double> sp = graph.shortestPathOnlyWeight("A");
        System.out.println(sp);
    }

    static void tesSp() {
        Graph<Object, Double> graph = undirectedGraph(Data.SP);
        Map<Object, Graph.PathInfo<Object, Double>> map = graph.shortestPath("A");
        map.forEach((Object v, Graph.PathInfo<Object, Double> pathInfo) -> {
            System.out.println(v + " - " + pathInfo);
        });
    }

    /**
     * 測試帶有負權邊的bell
     */
    static void tesSp1() {
        Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
        Map<Object, Graph.PathInfo<Object, Double>> map = graph.shortestPath("A");
        map.forEach((Object v, Graph.PathInfo<Object, Double> pathInfo) -> {
            System.out.println(v + " - " + pathInfo);
        });
    }

    /**
     * 測試帶有負權環的bell
     */
    static void tesSp2() {
        Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT2);
        Map<Object, Graph.PathInfo<Object, Double>> map = graph.shortestPath(0);
        if (map == null) return;
        map.forEach((Object v, Graph.PathInfo<Object, Double> pathInfo) -> {
            System.out.println(v + " - " + pathInfo);
        });
    }


    /**
     * 測試ford
     */
    static void tesSp3() {
        Graph<Object, Double> graph = directedGraph(Data.SP);
        Map<Object, Map<Object, Graph.PathInfo<Object, Double>>> map = graph.shortestPath();
        if (map == null) return;
        map.forEach((Object from, Map<Object, Graph.PathInfo<Object, Double>> pathInfoMap) -> {
            System.out.println(from + " - ------");
            pathInfoMap.forEach((Object to, Graph.PathInfo<Object, Double> pathInfo) -> {
                System.out.println(to + "   " + pathInfo);
            });
        });
    }

    /**
     * 測試ford 負權邊
     */
    static void tesSp4() {
        Graph<Object, Double> graph = directedGraph(Data.NEGATIVE_WEIGHT1);
        Map<Object, Map<Object, Graph.PathInfo<Object, Double>>> map = graph.shortestPath();
        if (map == null) return;
        map.forEach((Object from, Map<Object, Graph.PathInfo<Object, Double>> pathInfoMap) -> {
            System.out.println(from + " - ------");
            pathInfoMap.forEach((Object to, Graph.PathInfo<Object, Double> pathInfo) -> {
                System.out.println(to + "   " + pathInfo);
            });
        });
    }

    public static void main(String[] args) {
        tesSp4();
    }

    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     *
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
