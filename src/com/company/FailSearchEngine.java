package com.company;

public class FailSearchEngine {
    private Cluster cluster;

    public FailSearchEngine(Cluster cluster) {
        this.cluster = cluster;
    }

    public void search() {
        //find server with failed node
        int first = 0;
        int last = cluster.servers.size()-1;
        int position = (first + last) / 2;
        while (first != last) {
            if (cluster.isFailed(position, cluster.servers.get(position).getNodes().size()-1)) {
                last = position;
            } else {
                first++;
            }
            position = (first + last) / 2;
        }
        int failedServerPosition = position;

        //find failed node in server
        first = 0;
        last = cluster.servers.get(failedServerPosition).getNodes().size()-1;
        position = (first + last) / 2;
        while (first != last) {
            if (cluster.isFailed(failedServerPosition, position)) {
                last = position;
            } else {
                first++;
            }
            position = (first + last) / 2;
        }

        //print results
        System.out.println("Failed server: " + cluster.servers.get(failedServerPosition).getId());
        System.out.println("Failed node: " + cluster.servers.get(failedServerPosition).getNodes().get(position).getId());
    }
}
