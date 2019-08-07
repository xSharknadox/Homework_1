package com.company.web;

import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.interfaces.Failable;

public class FailSearchEngine {
    private Cluster cluster;

    public FailSearchEngine(Cluster cluster) {
        this.cluster = cluster;
    }

    public void search(int serverIndex) {
        boolean haveElement = false;
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
            boolean result = false;
            try {
                result = serverIndex == -1 ? cluster.isFailed(middle, cluster.getFailable(middle).getSize() - 1) : cluster.isFailed(serverIndex, middle);
                haveElement = true;
            } catch (DontHaveFailableOfChildException e){
                System.out.println(e.getMessage());
                end--;
                haveElement = false;
            }
            if(haveElement) {
                if (result) {
                    end = middle;
                } else {
                    start = middle;
                }
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

    public void searchWithOptional(){
        int serverFailedIndex = -1;
        int nodeFailedIndex = -1;
        for(int i = 0; i< cluster.getSize(); i++){
            try {
                if (cluster.getFailable(i).isFailed()) {
                    serverFailedIndex = i;
                    break;
                }
            }catch (DontHaveFailableOfChildException e){

            }
        }
        for(int i = 0; i< cluster.getFailable(serverFailedIndex).getSize(); i++){
            try {
                if (cluster.getFailable(serverFailedIndex).getFailable(i).isFailed()) {
                    nodeFailedIndex = i;
                    break;
                }
            } catch (DontHaveFailableOfChildException e){

            }
        }
        System.out.println("Server id: " + cluster.getFailable(serverFailedIndex).getId() + ", Node id: " + cluster.getFailable(serverFailedIndex).getFailable(nodeFailedIndex).getId());
    }
}
