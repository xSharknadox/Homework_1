package com.company;

import com.company.exceptions.SizeException;
import com.company.interfaces.Failable;
import com.company.utils.Optional;
import com.company.web.Cluster;
import com.company.web.Node;
import com.company.web.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ClusterTest {

    @Test
    public void verifySizeOfChildren() {
        Cluster cluster = new Cluster();
        ArrayList<Optional<Server>> servers = new ArrayList<>();
        servers.add(new Optional<>(new Server()));
        servers.add(new Optional<>(new Server()));
        servers.add(new Optional<>(new Server()));

        ArrayList<Optional<Node>> nodes = new ArrayList<>();
        nodes.add(new Optional<>(new Node()));
        nodes.add(new Optional<>(new Node()));

        servers.get(0).get().setNodes(nodes);
        cluster.setServers(servers);

        assertThat("Cluster must have " + servers.size() + " children", servers.size(), equalTo(cluster.getSize()));

        for (int i = 0; i < cluster.getSize(); i++) {
            assertThat("Server must have " + servers.get(i).get().getSize() + " children", servers.get(i).get().getSize(), equalTo(cluster.getFailable(i).getSize()));
        }

        int nodeChildren;
        Random random = new Random();
        Failable randomServer = cluster.getFailable(random.nextInt(cluster.getSize()));
        for (int i = 0; i < randomServer.getSize(); i++) {
            try {
                nodeChildren = randomServer.getFailable(i).getSize();
            } catch (SizeException e) {
                nodeChildren = 0;
            }
            assertThat("Node can't have children", 0, equalTo(nodeChildren));
        }
    }
}
