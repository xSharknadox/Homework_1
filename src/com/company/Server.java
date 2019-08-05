package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Server {
    private int id;
    private ArrayList<Node> nodes = new ArrayList<>();
    private MessageCallback clusterMessageCallback;
    private MessageCallback callback = new MessageCallback() {

        @Override
        public void callbackMessage(Message message) {
            clusterMessageCallback.callbackMessage(message);
        }
    };

    public Server(int id, MessageCallback clusterMessageCallback) {
        this.id = id;
        this.clusterMessageCallback = clusterMessageCallback;
        for(int i=1; i<=10; i++){
            nodes.add(new Node(i, callback));
        }
    }

    public Node getNode(int node){
        return nodes.get(node);
    }

//    public void setRandomNodes(int size, int failedNode) {
//        for (int i = 1; i <= size; i++) {
//            nodes.add(new Node(i, (failedNode != -1 && i >= failedNode)));
//        }
//    }

    @Override
    public String toString() {
        return "\nServer:" +
                " id=" + id +
                ", nodes=" + nodes;
    }

    public void setMessage(Message message){
        nodes.get(message.getNodeId()-1).setMessage(message);
    }
}
