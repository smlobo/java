package org.sheldon;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    static int count = 0;

    abstract static class ContinentHandler implements HttpHandler {
        abstract String getCountry();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }

            // Request headers
            Headers headers = exchange.getRequestHeaders();
            String userAgent = headers.getFirst("User-Agent");

            System.out.println("[" + count + "] Received " + exchange.getRequestURI().getPath() + " request from: " +
                    userAgent);

            // Response headers
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.add("Server", "Java " + Runtime.version());

            exchange.sendResponseHeaders(200, getCountry().getBytes().length);
            exchange.getResponseBody().write(getCountry().getBytes());
            exchange.close();

            count++;
        }
    }

    static class AsiaHandler extends ContinentHandler {
        String getCountry() { return Constants.ASIA_COUNTRY; }
    }

    static class AmericaHandler extends ContinentHandler {
        String getCountry() { return Constants.AMERICA_COUNTRY; }
    }

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: Server [port]");
            System.exit(1);
        }

        int port = Constants.PORT;
        if (args.length == 1) {
            port = Integer.parseInt(args[0]);
        }

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 10);
        httpServer.setExecutor(Executors.newFixedThreadPool(10));
        httpServer.createContext(Constants.ASIA_PATH, new AsiaHandler());
        httpServer.createContext(Constants.AMERICA_PATH, new AmericaHandler());

        System.out.println("Starting Java server on port " + httpServer.getAddress());
        httpServer.start();
    }
}
