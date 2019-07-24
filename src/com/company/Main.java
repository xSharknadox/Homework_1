package com.company;

public class Main {

    public static void main(String[] args) {
	    Cluster cluster = new Cluster();
	    cluster.sendMessage();
        System.out.println(cluster.toString());

        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
        failSearchEngine.search();
    }
}
