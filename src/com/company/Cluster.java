package com.company;

import java.util.LinkedList;
import java.util.Random;

public class Cluster {
    LinkedList<Server> servers = new LinkedList<>();

    public Cluster() {
    }

    public void sendMessage() {
        //random size of servers and choose by random one where will be failed node
        Random random = new Random();
        int randomServersSize = random.nextInt(10) + 1;
        int randomServerWithFailedNode = random.nextInt(randomServersSize);
        System.out.println("Random failed server: " + randomServerWithFailedNode);

        //set servers data
        for (int i = 0; i < randomServersSize; i++) {
            //random size of nodes in servers
            int randomNodesSize = random.nextInt(10) + 1;

            //if it's server with node
            if (i == randomServerWithFailedNode) {
                //random id of failed node and add server with data in cluster collection
                int randomFailedNode = random.nextInt(randomNodesSize);
                System.out.println("Random failed node in failed server: " + randomFailedNode);
                Server server  = new Server(i);
                server.setRandomNodes(randomNodesSize, randomFailedNode);
                servers.add(server);
            } else if (i > randomServerWithFailedNode) {
                //if it's servers which located after server with failed node
                Server server  = new Server(i);
                server.setRandomNodes(randomNodesSize, 0);
                servers.add(server);
            } else {
                //if it's servers which located before server with failed node
                Server server  = new Server(i);
                server.setRandomNodes(randomNodesSize, -1);
                servers.add(server);
            }
        }
    }

    public boolean isFailed(int serverId, int nodeId){
        return servers.get(serverId).getNodes().get(nodeId).isFailed();
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "servers=\n" + servers +
                '}';
    }
}
