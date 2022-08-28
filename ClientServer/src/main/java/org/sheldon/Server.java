package org.sheldon;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    abstract static class ContinentHandler implements HttpHandler {
        abstract String getCountry();
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(400, -1);
                return;
            }
            System.out.println("Received request to " + this.getClass() + " -> " + exchange.getRemoteAddress());
            exchange.sendResponseHeaders(200, getCountry().getBytes().length);
            exchange.getResponseBody().write(getCountry().getBytes());
            exchange.close();
        }
    }

    static class AsiaHandler extends ContinentHandler {
        String getCountry() { return "India"; }
    }

    static class AmericaHandler extends ContinentHandler {
        String getCountry() { return "USA"; }
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 10);
        httpServer.setExecutor(Executors.newFixedThreadPool(10));
        httpServer.createContext("/country/asia", new AsiaHandler());
        httpServer.createContext("/country/america", new AmericaHandler());
        httpServer.start();
    }
}
