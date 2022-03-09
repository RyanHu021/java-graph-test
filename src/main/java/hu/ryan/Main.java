package hu.ryan;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Graph graph = exampleGraph3();
        System.out.println(graph);
        System.out.println("Topological Sort: " + graph.tsort());
        System.out.println("Depth First Search: " + graph.dfs(1));
        System.out.println("Breadth First Search: " + graph.bfs(1));
        System.out.println("Dijkstra's: " + Arrays.toString(graph.dijkstras(1)));
        System.out.println("Dijkstra's Single Path: " + graph.dijkstrasSinglePath(13, 5));
    }

    // cpe 202 tsort graph
    public static Graph exampleGraph1() {
        Graph graph = new Graph();
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 100);
        graph.addEdge(2, 4, 10);
        graph.addEdge(2, 5, 100);
        graph.addEdge(1, 4, 10);
        graph.addEdge(3, 6, 100);
        graph.addEdge(5, 4, 10);
        graph.addEdge(4, 3, 100);
        graph.addEdge(4, 6, 10);
        graph.addEdge(7, 6, 100);
        graph.addEdge(4, 7, 10);
        graph.addEdge(5, 7, 100);
        graph.addEdge(8, 6, 100);
        return graph;
    }

    // https://www.youtube.com/watch?v=XB4MIexjvY0
    public static Graph exampleGraph2() {
        Graph graph = new Graph();
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 4);
        graph.addEdge(2, 3, 1);
        graph.addEdge(2, 4, 7);
        graph.addEdge(3, 5, 3);
        graph.addEdge(4, 6, 1);
        graph.addEdge(5, 4, 2);
        graph.addEdge(5, 6, 5);
        return graph;
    }

    // https://www.youtube.com/watch?v=GazC3A4OQTE
    public static Graph exampleGraph3() {
        Graph graph = new Graph();
        int[][] edges = new int[][] {
                { 1, 2, 3 },
                { 1, 4, 4 },
                { 1, 13, 7 },
                { 2, 4, 4 },
                { 2, 8, 1 },
                { 2, 13, 2 },
                { 3, 12, 2 },
                { 3, 13, 3 },
                { 4, 6, 5 },
                { 5, 7, 2 },
                { 5, 11, 5 },
                { 6, 8, 3 },
                { 7, 8, 2 },
                { 9, 10, 6 },
                { 9, 11, 4 },
                { 9, 12, 4 },
                { 10, 11, 4 },
                { 10, 12, 4 }
        };
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1], edge[2]);
            graph.addEdge(edge[1], edge[0], edge[2]);
        }
        return graph;
    }
}
