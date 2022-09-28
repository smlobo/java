package org.lobo.jetty;

import org.eclipse.jetty.server.Server;

import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger("jetty-server");

    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] %4$s %5$s%6$s%n");

        Server server = new Server(8080);
        server.setHandler(new HelloHandler("Hello"));
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LOGGER.info("Shutting down the server ...");
                server.stop();
            } catch (Exception e) {
                LOGGER.severe("Could not create the shutdown hook");
            }
        }));

        LOGGER.info("Server started. Stop using CTRL+C");

        server.join();
    }
}
