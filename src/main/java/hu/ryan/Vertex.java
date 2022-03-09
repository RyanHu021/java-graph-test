package hu.ryan;

import java.util.ArrayList;
import java.util.Iterator;

public class Vertex {

    private final int id;
    private int inDeg;

    private ArrayList<int[]> adjTo;

    public Vertex(int id) {
        this.id = id;
        this.inDeg = 0;
        this.adjTo = new ArrayList<>();
    }

    public Vertex(Vertex v) {
        this.id = v.id;
        this.inDeg = v.inDeg;
        this.adjTo = new ArrayList<>(v.adjTo);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{id=");
        sb.append(id);
        sb.append(',').append(' ');
        sb.append("inDegree=");
        sb.append(inDeg);
        sb.append("adjList=[");

        Iterator<int[]> i = adjTo.iterator();
        if (!i.hasNext()) {
            return sb.append(']').append('}').toString();
        }

        for (;;) {
            int[] v = i.next();
            sb.append('(');
            sb.append(v[0]);
            sb.append(", weight=");
            sb.append(v[1]);
            sb.append(')');
            if (!i.hasNext())
                return sb.append(']').append('}').toString();
            sb.append(',').append(' ');
        }
    }

    public int getId() {
        return id;
    }

    public int getInDegree() {
        return inDeg;
    }

    public void incrementInDegree(int i) {
        inDeg += i;
    }

    public ArrayList<int[]> getAdjacentVertices() {
        return adjTo;
    }

    public void addAdjacentVertex(int v, int weight) {
        adjTo.add(new int[] { v, weight });
    }
}
