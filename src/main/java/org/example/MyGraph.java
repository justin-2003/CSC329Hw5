package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGraph {
    List<Integer> vertices;
    Map<Integer, List<Edge>> adjacencyLists;

    public MyGraph(){
        vertices = new ArrayList<>();
        adjacencyLists = new HashMap<Integer,List<Edge>>();
    }

    public void addVertex(int v){
        vertices.add(v);
        adjacencyLists.put(v,new ArrayList<>());
    }

    public void addEdge(int source, int destination, int weight) {
        Edge e = new Edge(source, destination, weight);
        adjacencyLists.get(source).add(e);

        // Make sure to initialize the adjacency list for the destination vertex
        if (!adjacencyLists.containsKey(destination)) {
            adjacencyLists.put(destination, new ArrayList<>());
        }
        adjacencyLists.get(destination).add(e);
    }

    public void showGraph() {
        System.out.print("V = { ");
        for (int v : vertices) {
            System.out.print(v + " ");
        }
        System.out.println("}");

        System.out.println("Adj Lists");
        for (int v : vertices) {
            System.out.print(v + ": ");
            List<Edge> adjList = adjacencyLists.get(v);
            for (Edge edge : adjList) {
                System.out.print("(" + edge.source + ", " + edge.destination + ", " + edge.weight + ") ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge : adjacencyLists.get(vertex)) {
            int otherVertex = (edge.source == vertex) ? edge.destination : edge.source;
            neighbors.add(otherVertex);
        }
        return neighbors;
    }


    public int getWeight(int source, int destination) {
        for (Edge edge : adjacencyLists.get(source)) {
            if ((edge.source == source && edge.destination == destination) ||
                    (edge.source == destination && edge.destination == source)) {
                return edge.weight;
            }
        }
        return -1;
    }
}
