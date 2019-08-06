package com.company;

import java.util.ArrayList;

public class Cluster implements MessageSendable, Failable {
    private ArrayList<Server> servers = new ArrayList<>();
    private boolean failed;

    MessageCallback callback = new MessageCallback() {

        @Override
        public void callbackMessage(Message message) {
            if (message.getServerId() == 0 || message.getNodeId() == 0) {
                sendMessageToAll(message);
            } else {
                sendMessageToChild(message);
            }
        }
    };


    public Cluster() {
        for (int i = 1; i <= 10; i++) {
            servers.add(new Server(i, callback));
        }
    }

    public Server getServer(int server) {
        return servers.get(server);
    }

    public String toString() {
        return "Cluster{" +
                "servers=\n" + servers +
                '}';
    }

    public boolean sendMessageToChild(Message message) {
        return servers.get(message.getServerId() - 1).sendMessageToChild(message);
    }

    public boolean sendMessageToAll(Message message) {
        boolean allNextFalse = false;
        for (Server server : servers) {
            allNextFalse = !server.sendMessageToAll(message);
            if (allNextFalse) {
                break;
            }
        }
        return allNextFalse;
    }

    public void sendMessage() {
        servers.get(0).getNode(0).callbackMessage(new Message(0, 0, "message"));
    }

    public boolean isFailed(int serverNumber, int nodeNumber) {
        return servers.get(serverNumber).getNode(nodeNumber).isFailed();
    }


    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isFailed() {
        return this.failed;
    }

    @Override
    public Failable getFailable(int index) {
        return servers.get(index);
    }

    @Override
    public int getSize() {
        return servers.size();
    }
}
