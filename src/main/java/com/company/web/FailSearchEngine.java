package com.company.web;

import com.company.exceptions.DontHaveFailableOfChildException;
import com.company.utils.Result;

import static com.company.utils.JsonSerialize.objectToJSON;

public class FailSearchEngine {
    private Cluster cluster;

    public FailSearchEngine(Cluster cluster) {
        this.cluster = cluster;
    }

    public void search() {
        int serverFailedIndex = -1;
        int nodeFailedIndex = -1;

        for (int i = 0; i < cluster.getSize(); i++) {
            try {
                if (cluster.getFailable(i).isFailed()) {
                    serverFailedIndex = i;
                    break;
                }
            } catch (DontHaveFailableOfChildException e) {

            }
        }
        if (serverFailedIndex == -1) {
            System.out.println("We don't have broken node");
        } else {
            for (int i = 0; i < cluster.getFailable(serverFailedIndex).getSize(); i++) {
                try {
                    if (cluster.getFailable(serverFailedIndex).getFailable(i).isFailed()) {
                        nodeFailedIndex = i;
                        break;
                    }
                } catch (DontHaveFailableOfChildException e) {

                }
            }
            Result result = new Result(cluster.getFailable(serverFailedIndex).getId(), cluster.getFailable(serverFailedIndex).getFailable(nodeFailedIndex).getId());
            objectToJSON(result, "C:\\Users\\Пользователь\\Documents\\result.json");
            System.out.println(result.toString());
        }
    }
}
