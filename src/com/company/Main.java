package com.company;

public class Main {

    public static void main(String[] args) {
	    Cluster cluster = new Cluster();
	    cluster.getServer(2).getNode(5).callbackMessage(new Message(1, 2,"тупа масадж"));
        System.out.println(cluster.getServer(0).getNode(1).getMessage());
    }
}
