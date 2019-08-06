package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.company.interfaces.MessageSendable;

import java.util.ArrayList;

public class Server implements MessageSendable, Failable {
    private int id;
    private ArrayList<Node> nodes = new ArrayList<>();
    private MessageCallback clusterMessageCallback;
    private boolean failed = false;
    private MessageCallback callback = new MessageCallback() {

        @Override
        public void callbackMessage(Message message) {
            clusterMessageCallback.callbackMessage(message);
        }
    };

    public Server(int id, MessageCallback clusterMessageCallback) {
        this.id = id;
        this.clusterMessageCallback = clusterMessageCallback;
        for (int i = 1; i <= 10; i++) {
            nodes.add(new Node(i, callback));
        }
    }

    public Node getNode(int node) {
        return nodes.get(node);
    }

    @Override
    public String toString() {
        return "\nServer:" +
                " id=" + id +
                ", nodes=" + nodes;
    }

    public boolean sendMessageToChild(Message message) {
        return nodes.get(message.getNodeId() - 1).setMessage(message);
    }

    public boolean sendMessageToAll(Message message) {
        boolean allNextFalse = false;
        for (Node node : nodes) {
            if (!allNextFalse) {
                allNextFalse = !node.setMessage(message);
            }
        }

        return !allNextFalse;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isFailed() {
        return this.failed;
    }

    @Override
    public Failable getFailable(int index) {
        if (getSize() != 0) {
            return nodes.get(index);
        } else throw new DontHaveFailableOfChildException();
    }

    @Override
    public int getSize() {
        return nodes.size();
    }
}
