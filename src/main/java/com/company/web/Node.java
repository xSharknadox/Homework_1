package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.exceptions.SizeException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Random;

public class Node implements MessageCallback, Failable {
    private int id;
    private String message = null;

    @JsonIgnore
    private MessageCallback serverMessageCallback;

    @JsonIgnore
    private final Random random = new Random();

    public Node(int id, MessageCallback serverMessageCallback) {
        this.id = id;
        this.serverMessageCallback = serverMessageCallback;
    }

    public Node() {
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
        if (randomWithSize > 20) {
            this.message = message.getMessage();
        }
        return this.message != null;
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
        throw new DontHaveFailableOfChildException("Don't have childs");
    }

    @Override
    @JsonIgnore
    public int getSize() {
        throw new SizeException();
    }
}
