package com.company.utils;

public class Result {
    private int serverId;
    private int nodeId;

    public Result(int serverId, int nodeId) {
        this.serverId = serverId;
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "serverId=" + serverId +
                ", nodeId=" + nodeId +
                '}';
    }
}
