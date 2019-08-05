package com.company;

import java.util.ArrayList;

public class Node implements MessageCallback{
    private int id;
    private boolean failed = false;
    private String message;
    private MessageCallback serverMessageCallback;

    public Node(int id, MessageCallback serverMessageCallback) {
        this.id = id;
        this.serverMessageCallback = serverMessageCallback;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", failed=" + failed +
                '}';
    }

    public int getSize() {
        return 0;
    }

    public int getId() {
        return id;
    }


    public boolean isFailed() {
        return failed;
    }

    public void setMessage(Message message){
        this.message = message.getMessage();
    }

    public String getMessage(){
        return message;
    }

    @Override
    public void callbackMessage(Message message) {
        serverMessageCallback.callbackMessage(message);
    }
}
