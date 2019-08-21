package com.company.web;

import com.company.Message;
import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.exceptions.ElementNotFoundException;
import com.company.interfaces.Failable;
import com.company.interfaces.MessageCallback;
import com.company.interfaces.MessageSendable;
import com.company.utils.Optional;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Cluster implements MessageSendable, Failable {
    private int id = 0;
    private ArrayList<Optional<Server>> servers = new ArrayList<>();
    private boolean failed = true;
    private Random random = new Random();

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
    }

    public Cluster(int numberOfRandom) {
        for (int i = 1; i <= numberOfRandom; i++) {
            if (random.nextBoolean()) {
                servers.add(new Optional<>(new Server(i, callback)));
            } else {
                servers.add(new Optional<>());
            }
        }
        System.out.println();
    }

    public String toString() {
        return "Cluster{" +
                "servers=\n" + servers.stream().map(Optional::get).collect(Collectors.toCollection(ArrayList::new)) +
                '}';
    }

    public boolean sendMessageToChild(Message message) {
        Optional<Server> optServer = servers.get(message.getServerId() - 1);
        if (optServer.isPresent()) {
            return optServer.get().sendMessageToChild(message);
        } else return false;
    }

    public boolean sendMessageToAll(Message message) {
        boolean allNextFalse = false;
        for (Optional<Server> server : servers) {
            if (server.isPresent()) {
                allNextFalse = !server.get().sendMessageToAll(message);
                if (allNextFalse) {
                    break;
                }
            } else allNextFalse = false;
        }
        return allNextFalse;
    }

    public void sendMessage() {
        Server server = null;
        for (Optional<Server> optServer : servers) {
            if (optServer.isPresent()) {
                server = optServer.get();
                for (int i = 0; i < server.getSize(); i++) {
                    Node node = server.getNode(i);
                    if (node.getId() != -1) {
                        node.callbackMessage(new Message(0, 0, "message"));
                        return;
                    }
                }
            }
        }
        if (server == null) {
            throw new ElementNotFoundException("Don't have any server and nodes");
        }
    }

    public boolean isFailed(int serverNumber, int nodeNumber) {
        Optional<Server> optServer = servers.get(serverNumber);
        if (optServer.isPresent()) {
            return optServer.get().getNode(nodeNumber).isFailed();
        } else return true;
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
            Optional<Server> optServer = servers.get(index);
            if (optServer.isPresent()) {
                return optServer.get();
            } else throw new DontHaveFailableOfChildException("Server with index " + index);
        } else throw new DontHaveFailableOfChildException("Server with index " + index);
    }

    @Override
    public int getSize() {
        return servers.size();
    }

    public void setServers(ArrayList<Optional<Server>> servers) {
        this.servers = servers;
    }
}
