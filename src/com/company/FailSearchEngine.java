package com.company;

public class FailSearchEngine {
    private Cluster cluster;

    public FailSearchEngine(Cluster cluster) {
        this.cluster = cluster;
    }

    public void search(int serverIndex) {
        int start = 0;
        int end = serverIndex == 0 ? cluster.getSize() : cluster.getFailable(serverIndex).getSize();
        int middle = (start + end) / 2;
        while (start != end) {
            boolean result = serverIndex == 0 ? cluster.isFailed(middle, cluster.getFailable(middle).getSize() - 1) : cluster.isFailed(serverIndex, middle);
            ;
            if (result) {
                end = middle;
            } else {
                start = middle;
            }
            if (start == (end - 1)) {
                middle = end;
                break;
            }
            middle = (start + end) / 2;
        }
        if (serverIndex == 0) {
            search(middle);
        } else {
            System.out.println();
            System.out.println("Server id: " + cluster.getFailable(serverIndex).getId() + ", Node id: " + cluster.getFailable(serverIndex).getFailable(middle).getId());
        }
    }

    private void nodeSearch(int serverIndex) {
        int start = 0;
        int end = cluster.getSize();
        int middle = (start + end) / 2;
        while (start != end) {
            boolean result = cluster.isFailed(serverIndex, middle);
            if (result) {
                end = middle;
            } else {
                start = middle;
            }
            if (start == (end - 1)) {
                middle = end;
                break;
            }
            middle = (start + end) / 2;
        }

    }
}
