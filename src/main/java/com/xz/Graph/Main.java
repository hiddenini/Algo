package com.xz.Graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ListGraph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);
/*        graph.print();
        graph.removeEdge("v1", "v0");
        System.out.println("after remove");
        graph.print();*/
/*        graph.print();
        graph.removeVertex("v0");
        System.out.println("after remove");
        graph.print();*/

/*        graph.bfs("v1", new Graph.VertexVisitor<String>() {
            @Override
            public boolean visit(String s) {
                System.out.println(s);
                return false;
            }
        });*/

/*        graph.bfs("v1", (String v) -> {
            System.out.println(v);
            return false;
        });*/

/*        graph.dfs("v1", (String v) -> {
            System.out.println(v);
            return false;
        });*/

        List<String> strings = graph.topologicalSort();
        System.out.println(strings);
    }
}
