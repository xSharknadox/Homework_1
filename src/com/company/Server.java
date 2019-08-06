package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Server implements MessageSendable, Failable{
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
        for (Node node : nodes){
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
        return nodes.get(index);
    }

    @Override
    public int getSize() {
        return nodes.size();
    }
}
