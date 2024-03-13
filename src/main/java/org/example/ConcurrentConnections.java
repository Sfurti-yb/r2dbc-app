package org.example;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.api.PostgresqlConnection;

import java.util.HashMap;
import java.util.Map;

public class ConcurrentConnections {
    private static int numConnectionsPerThread = 2;
    private static int numThreads = 5;

    public static void main(String[] args) throws  InterruptedException {
        testConcurrentConnectionCreations();
    }

    private static void testConcurrentConnectionCreations() throws
            InterruptedException {
        try {
            int total = numThreads * numConnectionsPerThread;
            Thread[] threads = new Thread[numThreads];

            PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                    .addHost("127.0.0.1")
                    .username("yugabyte")
                    .password("yugabyte")
                    .database("yugabyte")
                    .loadBalanceHosts(true)
                    .build());

//            PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
//                    .addHost("127.0.0.1",5433)
//                    .username("yugabyte")
//                    .password("yugabyte")
//                    .database("yugabyte")
//                    .build());
            PostgresqlConnection[] connections = new PostgresqlConnection[total];

            for (int i = 0 ; i < numThreads ; i++) {
                final int j = i;
                threads[i] = new Thread(() -> {
                    try {
                        connections[j] = connectionFactory.create().block();
                        connections[j + numThreads] = connectionFactory.create().block();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });
            }

            long startTime = System.currentTimeMillis();

            for (int i = 0 ; i < numThreads; i++) {
                threads[i].start();
            }
            System.out.println("Launched " + numThreads + " threads to create " + numConnectionsPerThread + " connections each");

            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
            long endTime = System.currentTimeMillis();

            System.out.println("Connections Created in " + (endTime - startTime));

            System.out.println("Closing connections ...");
            for (int i = 0 ; i < total; i++) {
                connections[i].close().block();
            }

        } finally {
            System.out.println("Done");
        }
    }
}
