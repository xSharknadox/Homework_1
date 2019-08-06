package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.exceptions.ElementNotFoundException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.company.interfaces.MessageSendable;

import java.util.ArrayList;

public class Cluster implements MessageSendable, Failable {
    private int id = 0;
    private ArrayList<Server> servers = new ArrayList<>();
    private boolean failed = true;

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

    public void sendMessage(int serverId, int nodeId) {
        if (serverId < getSize() && serverId > 0) {
            Server server = servers.get(serverId - 1);
            if (nodeId < server.getSize() && nodeId > 0) {
                servers.get(serverId - 1).getNode(nodeId - 1).callbackMessage(new Message(0, 0, "message"));
            } else throw new ElementNotFoundException("Node with id " + nodeId);
        } else throw new ElementNotFoundException("Server with id " + serverId);
    }

    public boolean isFailed(int serverNumber, int nodeNumber) {
        return servers.get(serverNumber).getNode(nodeNumber).isFailed();
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
            return servers.get(index);
        } else throw new DontHaveFailableOfChildException();
    }

    @Override
    public int getSize() {
        return servers.size();
    }
}
