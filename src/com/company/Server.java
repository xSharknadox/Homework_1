package com.company;

import java.util.LinkedList;

public class Server {
    private int id;
    private LinkedList<Node> nodes = new LinkedList<>();

    public Server(int id) {
        this.id = id;
    }

    public void setRandomNodes(int size, int failedNode){
        for(int i=0; i<size; i++){
            nodes.add(new Node(i, (failedNode!=-1 && i>=failedNode)));
        }
    }

    @Override
    public String toString() {
        return "\nServer{" +
                "id=" + id +
                ", nodes=" + nodes +
                "}";
    }

    public int getId() {
        return id;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }
}
