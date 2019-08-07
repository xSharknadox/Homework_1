package com.company;

import com.company.web.Cluster;
import com.company.web.FailSearchEngine;

public class Main {

    public static void main(String[] args) {
        Cluster cluster = new Cluster();
        cluster.sendMessage(1, 1);
        System.out.println(cluster);

        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
        failSearchEngine.search(0);
    }
}
