package org.example;

public class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String toString(){
        return String.format("%d,d%,d%", source,destination,weight);
    }

}
