package com.company;

import java.util.Random;

public class Node implements MessageCallback, Failable {
    private int id;
    private String message = null;
    private MessageCallback serverMessageCallback;
    private Random random = new Random();

    public Node(int id, MessageCallback serverMessageCallback) {
        this.id = id;
        this.serverMessageCallback = serverMessageCallback;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean setMessage(Message message) {
        int randomWithSize = random.nextInt(100);
        if (randomWithSize > 2) {
            this.message = message.getMessage();
        }
        return this.message != null;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void callbackMessage(Message message) {
        serverMessageCallback.callbackMessage(message);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isFailed() {
        return message == null;
    }

    @Override
    public Failable getFailable(int index) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
