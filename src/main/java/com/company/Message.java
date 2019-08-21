package com.company;

public class Message {
    private int serverId;
    private int nodeId;
    private String message;

    public Message(int serverId, int nodeId, String message) {
        this.serverId = serverId;
        this.nodeId = nodeId;
        this.message = message;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
