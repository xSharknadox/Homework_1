package com.company.web;

public class FailSearchEngine {
    private Cluster cluster;

    public FailSearchEngine(Cluster cluster) {
        this.cluster = cluster;
    }

    public void search(int serverIndex) {
        int start = 0;
        int end = serverIndex == -1 ? cluster.getSize() : cluster.getFailable(serverIndex).getSize();
        int middle = (start + end) / 2;
        while (start != end) {
            if (start == (end - 1)) {
                if (start == 0) {
                    middle = start;
                } else {
                    middle = end;
                }
                break;
            }
            boolean result = serverIndex == -1 ? cluster.isFailed(middle, cluster.getFailable(middle).getSize() - 1) : cluster.isFailed(serverIndex, middle);
            if (result) {
                end = middle;
            } else {
                start = middle;
            }
            middle = (start + end) / 2;
        }
        if (start == ((serverIndex == -1 ? cluster.getSize() : cluster.getFailable(serverIndex).getSize()) - 1)) {
            System.out.println();
            System.out.println("We don't have broken node");
        } else {
            if (serverIndex == -1) {
                search(middle);
            } else {
                System.out.println();
                System.out.println("Server id: " + cluster.getFailable(serverIndex).getId() + ", Node id: " + cluster.getFailable(serverIndex).getFailable(middle).getId());
            }
        }
    }
}
