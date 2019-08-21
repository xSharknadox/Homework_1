package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.company.interfaces.MessageSendable;
import com.company.utils.Optional;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Server implements MessageSendable, Failable {
    private int id;
    @JsonProperty("nodes")
    private ArrayList<Optional<Node>> nodes = new ArrayList<>();
    private boolean failed = true;

    @JsonIgnore
    private Random random = new Random();

    @JsonIgnore
    private MessageCallback clusterMessageCallback;

    @JsonIgnore
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
            if (random.nextBoolean()) {
                nodes.add(new Optional<>(new Node(i, callback)));
            } else {
                if (i == 1) {
                    nodes.add(new Optional<>(new Node(i, callback)));
                } else {
                    nodes.add(new Optional<>());
                }
            }
        }
    }

    public Server() {
    }

    public Node getNode(int node) {
        Optional<Node> optNode = nodes.get(node);
        if (optNode.isPresent()) {
            return optNode.get();
        } else {
            return new Node(-1, null);
        }
    }

    @Override
    public String toString() {
        return "\nServer:" +
                " id=" + id +
                " failed=" + failed +
                ", nodes=" + nodes.stream().map(Optional::get).collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean sendMessageToChild(Message message) {
        Optional<Node> optNode = nodes.get(message.getNodeId() - 1);
        if (optNode.isPresent()) {
            return nodes.get(message.getNodeId() - 1).get().setMessage(message);
        } else return false;
    }

    public boolean sendMessageToAll(Message message) {
        boolean allNextFalse = false;
        for (Optional<Node> node : nodes) {
            if (allNextFalse) break;
            else {
                if (node.isPresent()) {
                    if (!allNextFalse) {
                        allNextFalse = !node.get().setMessage(message);
                    }
                } else {
                    allNextFalse = false;
                }
            }
        }
        this.failed = allNextFalse;
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
            Optional<Node> optNode = nodes.get(index);
            if (optNode.isPresent()) {
                return optNode.get();
            } else throw new DontHaveFailableOfChildException("Node with index " + index);
        } else throw new DontHaveFailableOfChildException("Node with index " + index);
    }

    @Override
    public int getSize() {
        return nodes.size();
    }

    public void setNodes(ArrayList<Optional<Node>> nodes) {
        this.nodes = nodes;
    }
}
