package com.company;

import java.util.ArrayList;

public class Cluster {
    private ArrayList<Server> servers = new ArrayList<>();
    private boolean failed;
    private String clusterMessage;

    MessageCallback callback = new MessageCallback() {

        @Override
        public void callbackMessage(Message message) {
            servers.get(message.getServerId()-1).setMessage(message);
        }
    };


    public Cluster() {
        for(int i=1; i<=10; i++){
            servers.add(new Server(i, callback));
        }
    }

    public Server getServer(int server){
        return servers.get(server);
    }

    public String getClusterMessage(){
        return clusterMessage;
    }

    public String toString() {
        return "Cluster{" +
                "servers=\n" + servers +
                '}';
    }



}
