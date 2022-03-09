package hu.ryan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph {

    private ArrayList<ArrayList<Integer>> adjMatrix;
    private HashMap<Integer, Vertex> adjList;
    // private ArrayList<Integer[]> edgeList;

    public Graph() {
        adjMatrix = new ArrayList<>();
        adjList = new HashMap<>();
    }

    public Graph(Graph g) {
        adjMatrix = new ArrayList<>();
        for (ArrayList<Integer> row : g.adjMatrix) {
            adjMatrix.add(new ArrayList<>(row));
        }
        adjList = new HashMap<>();
        for (Entry<Integer, Vertex> e : g.adjList.entrySet()) {
            adjList.put(e.getKey(), new Vertex(e.getValue()));
        }
    }

    @Override
    public String toString() {
        Iterator<Vertex> i = adjList.values().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{').append('\n');
        while (i.hasNext()) {
            Vertex v = i.next();
            sb.append(v);
            sb.append('\n');
        }
        return sb.append('}').toString();
    }

    public int size() {
        return adjList.size();
    }

    public Vertex getVertex(int v) {
        return adjList.get(v);
    }

    public boolean hasVertex(int v) {
        return adjList.containsKey(v);
    }

    public void addVertex(int v) {
        if (!hasVertex(v)) {
            adjList.put(v, new Vertex(v));
        }
    }

    public void addEdge(int v1, int v2, int weight) {
        if (!hasVertex(v1)) {
            addVertex(v1);
        }
        if (!hasVertex(v2)) {
            addVertex(v2);
        }
        getVertex(v1).addAdjacentVertex(v2, weight);
        getVertex(v2).incrementInDegree(1);
    }

    public List<Integer> tsort() {
        ArrayList<Integer> result = new ArrayList<>();
        Graph dup = new Graph(this);
        Stack<Vertex> stack = new Stack<>();

        for (Vertex v : dup.adjList.values()) {
            if (v.getInDegree() == 0) {
                stack.push(v);
            }
        }

        while (!stack.isEmpty()) {
            Vertex vCurr = stack.pop();
            result.add(vCurr.getId());
            for (int[] v : vCurr.getAdjacentVertices()) {
                Vertex vTemp = dup.getVertex(v[0]);
                vTemp.incrementInDegree(-1);
                if (vTemp.getInDegree() == 0) {
                    stack.push(vTemp);
                }
            }
        }
        if (result.size() != dup.adjList.size()) {
            return null;
        }
        return result;
    }

    public List<Integer> dfs(int root) {
        ArrayList<Integer> result = new ArrayList<>(size());
        if (!hasVertex(root)) {
            return result;
        }
        HashSet<Integer> visited = new HashSet<>();
        dfsHelper(root, result, visited);
        return result;
    }

    private void dfsHelper(int curr, ArrayList<Integer> result, HashSet<Integer> visited) {
        Vertex vCurr = getVertex(curr);
        result.add(curr);
        visited.add(curr);
        for (int[] v : vCurr.getAdjacentVertices()) {
            if (!visited.contains(v[0])) {
                dfsHelper(v[0], result, visited);
            }
        }
    }

    public List<Integer> bfs(int root) {
        ArrayList<Integer> result = new ArrayList<>(size());
        if (!hasVertex(root)) {
            return result;
        }
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            Vertex vCurr = getVertex(queue.remove());
            result.add(vCurr.getId());
            for (int[] v : vCurr.getAdjacentVertices()) {
                if (!visited.contains(v[0])) {
                    queue.add(v[0]);
                    visited.add(v[0]);
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Integer>[] dijkstras(int source) {
        HashMap<Integer, Integer>[] result = new HashMap[2];
        if (!hasVertex(source)) {
            return result;
        }

        // visited vertices
        HashSet<Integer> visited = new HashSet<>();

        // distance map (dist[v] = minimum distance from source to v)
        HashMap<Integer, Integer> dist = new HashMap<>();

        // previous vertex map (prev[v] = previous vertex such that distance from
        // prev[v] to v is minimum)
        HashMap<Integer, Integer> prev = new HashMap<>();

        // priority queue to store vertex id and distance as comparator
        PriorityQueue<int[]> pq = new PriorityQueue<>(adjList.size(), Comparator.comparing(e -> e[1]));

        // set source distance to 0
        dist.put(source, 0);

        // set distances of other vertices to infinity and previous vertex to null
        for (int v : adjList.keySet()) {
            if (v != source) {
                dist.put(v, Integer.MAX_VALUE);
                prev.put(v, null);
            }
        }

        // add source to priority queue
        pq.add(new int[] { source, 0 });
        while (!pq.isEmpty()) {

            // get vertex u with minimum distance
            Vertex vCurr = adjList.get(pq.poll()[0]);

            // add u to visited set
            visited.add(vCurr.getId());

            // iterate through adjacent vertices (v[0] = vertex id, v[1] = edge(u, v))
            for (int[] v : vCurr.getAdjacentVertices()) {

                // set alternate distance to dist[u] + edge(u, v)
                int alt = dist.get(vCurr.getId()) + v[1];

                // if alternate distance < dist[v]
                if (alt < dist.get(v[0])) {

                    // set dist[v] to alternate distance
                    dist.put(v[0], alt);

                    // set prev[v] to u
                    prev.put(v[0], vCurr.getId());
                    if (!visited.contains(v[0])) {

                        // add v to priority with updated dist[v] if v has not been visited
                        pq.add(new int[] { v[0], alt });
                    }
                }
            }
        }

        // return distance and previous vertex maps
        result[0] = dist;
        result[1] = prev;
        return result;
    }

    public List<Integer> dijkstrasSinglePath(int source, int target) {
        ArrayList<Integer> result = new ArrayList<>();
        if (!hasVertex(source) || !hasVertex(target)) {
            return result;
        }

        HashMap<Integer, Integer> prev = dijkstras(source)[1];
        Stack<Integer> path = new Stack<>();
        int u = target;

        // traverse from target to source using previous vertex map
        while (u != source) {
            path.push(u);
            u = prev.get(u);
        }

        // reverse stack to resulting list
        result.add(source);
        while (!path.isEmpty()) {
            result.add(path.pop());
        }

        return result;
    }
}
