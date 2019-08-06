package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.company.interfaces.MessageSendable;
import com.company.utils.Optional;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Server implements MessageSendable, Failable {
    private int id;
    private ArrayList<Optional<Node>> nodes = new ArrayList<>();
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
            nodes.add(new Optional<>(new Node(i, callback)));
        }
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
            if (node.isPresent()) {
                if (!allNextFalse) {
                    allNextFalse = !node.get().setMessage(message);
                }
            } else {
                allNextFalse = false;
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
            Optional<Node> optNode = nodes.get(index);
            if (optNode.isPresent()) {
                return optNode.get();
            } else throw new DontHaveFailableOfChildException();
        } else throw new DontHaveFailableOfChildException();
    }

    @Override
    public int getSize() {
        return nodes.size();
    }
}
