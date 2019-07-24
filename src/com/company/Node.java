package com.company;

public class Node {
    private int id;
    private boolean failed = false;

    public Node(int id, boolean failed) {
        this.id = id;
        this.failed = failed;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", failed=" + failed +
                '}';
    }

    public int getId() {
        return id;
    }

    public boolean isFailed() {
        return failed;
    }
}
