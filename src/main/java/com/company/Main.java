package com.company;

import com.company.web.Cluster;
import com.company.web.FailSearchEngine;

import static com.company.utils.JsonSerialize.objectToJSON;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = new Cluster(10);
        cluster.sendMessage();
        System.out.println(cluster);
        objectToJSON(cluster, "C:\\Users\\Пользователь\\Documents\\cluster.json");
        System.out.println();

        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
        failSearchEngine.search();
    }
}
