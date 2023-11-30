package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class Main {
    public static void main(String[] args) {
        MyGraph myGraph = new MyGraph();

        // Adding vertices and edges to the graph
        for (int i = 0; i < 10; i++) {
            myGraph.addVertex(i);
        }



        myGraph.addEdge(0, 2, 1);
        myGraph.addEdge(0, 1, 10);
        myGraph.addEdge(0, 3, 8);
        myGraph.addEdge(1, 3, 2);
        myGraph.addEdge(2, 4, 1);
        myGraph.addEdge(2, 5, 7);
        myGraph.addEdge(3, 5, 8);
        myGraph.addEdge(3, 6, 1);
        myGraph.addEdge(4, 7, 1);
        myGraph.addEdge(5, 7, 1);
        myGraph.addEdge(5, 8, 9);
        myGraph.addEdge(6, 8, 1);
        myGraph.addEdge(6, 9, 9);
        myGraph.addEdge(7, 8, 2);
        myGraph.addEdge(8, 9, 1);

    myGraph.showGraph();

    int startingVertexMST = 0;
    MyGraph mstGraph = minimumSpanningTree(myGraph, startingVertexMST);
    mstGraph.showGraph();

    int destinationVertex = 5;
    shortestPath(mstGraph, startingVertexMST, destinationVertex);
}


    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex){
        MyGraph mstGraph = new MyGraph();

        boolean[] visited = new boolean[g.vertices.size()];
        visited[startingVertex] = true;
       // System.out.println("HEHEH3");
        for(int v: g.vertices){
            mstGraph.addVertex(v);
            visited[v] = false;
        }

        visited[startingVertex] = true;

        while (hasUnvisitedVertex(visited)) {
            Edge minEdge = getMinFrontierEdge(g, visited);
            visited[minEdge.source] = true;
            visited[minEdge.destination] = true;
            mstGraph.addEdge(minEdge.source, minEdge.destination, minEdge.weight);
        }
        return mstGraph;
    }
    public static Edge getMinFrontierEdge(MyGraph g, boolean[] visited) {
        Edge minEdge = new Edge(0, 0, Integer.MAX_VALUE);

        for (int v : g.vertices) {
            if (visited[v]) {
                List<Edge> vAdjList = g.adjacencyLists.get(v);

                for (Edge e : vAdjList) {
                    int otherVertex = (e.source == v) ? e.destination : e.source;

                    if (!visited[otherVertex] && (e.weight < minEdge.weight)) {
                        minEdge = e;
                    }
                }
            }
        }
        return minEdge;
    }

    private static boolean hasUnvisitedVertex(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) {
                return true;
            }
        }
        return false;
    }

    private static int getMinDistVertex(int[] distances, boolean[] visited) {
        int minVertex = -1;
        int minDist = Integer.MAX_VALUE;

        for (int v = 0; v < distances.length; v++) {
            if (!visited[v] && distances[v] < minDist) {
                minDist = distances[v];
                minVertex = v;
            }
        }

        return minVertex;
    }

    public static void shortestPath(MyGraph g, int startingVertex, int destVertex) {
        int numVertices = g.vertices.size();
        int[] distances = new int[numVertices];
        int[] previous = new int[numVertices];
        boolean[] visited = new boolean[numVertices];

        // Initialize distances and previous array
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        // Set distance to starting vertex as 0
        distances[startingVertex] = 0;

        // Dijkstra's algorithm
        while (hasUnvisitedVertex(visited)) {
            int currentVertex = getMinDistVertex(distances, visited);
            visited[currentVertex] = true;

            List<Integer> neighbors = g.getNeighbors(currentVertex);
            for (int neighbor : neighbors) {
                if (!visited[neighbor]) {
                    int possibleDist = distances[currentVertex] + g.getWeight(currentVertex, neighbor);

                    if (possibleDist < distances[neighbor]) {
                        distances[neighbor] = possibleDist;
                        previous[neighbor] = currentVertex;
                    }
                }
            }
        }
        System.out.println("Shortest Path Data");
        System.out.println("Starting Vertex: " + startingVertex);
        System.out.println("Destination Vertex: " + destVertex);

        // Print the distance array
        System.out.println("Vertex       Distance");
        for (int i = 0; i < numVertices; i++) {
            System.out.println( "     "+ i + "            " + distances[i]);
        }

        // Print the previous array
        System.out.println("\nVertex     Previous");
        for (int i = 0; i < numVertices; i++) {
            System.out.println("     "+ i + "            " + previous[i]);
        }

        System.out.print("Shortest path to vertex " + destVertex + ": ");

        List<Integer> path = new ArrayList<>();
        int currentVertex = destVertex;

        // Build the path by traversing the previous array
        while (currentVertex != -1) {
            path.add(currentVertex);
            currentVertex = previous[currentVertex];
        }

        // Print the path in reverse order
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
    }
}
