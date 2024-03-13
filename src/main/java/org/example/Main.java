package org.example;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;
import io.r2dbc.spi.Connection;

//import java.sql.Connection;
import java.util.ArrayList;

public class Main {
    private static int numConnections = 200;

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://yugabyte:yugabyte@127.0.0.1:5433/yugabyte?loadBalanceHosts=true");
//        ConnectionFactory connectionFactory = ConnectionFactories.get("r2dbc:postgresql://yugabyte:yugabyte@127.0.0.1:5433/yugabyte?");

        long startTime = System.currentTimeMillis();
        ArrayList<Connection> connections = new ArrayList<>();

        for(int i = 0; i < numConnections; i++){
            Mono<Connection> connectionMono = Mono.from(connectionFactory.create());

            Connection connection = connectionMono.block();

            connections.add(connection);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Connections Created in " + (endTime - startTime));

        for (Connection connection: connections) {
            connection.close();
        }
    }
}