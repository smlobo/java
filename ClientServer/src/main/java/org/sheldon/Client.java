package org.sheldon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.LinkedList;
import java.util.Random;

public class Client {
    private static int count = 0;

    private static void makeRequest(String restEndpoint, int port) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://" + Constants.SERVER + ":" + port + restEndpoint))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Headers
        HttpHeaders headers = response.headers();
        Optional<String> server = headers.firstValue("Server");

        System.out.println("[" + count + "] To: " + port + "; Got: " + response.statusCode() + " {" +
                server.orElse("no-server") + "} : " + response.body());
        count++;
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            System.out.println("Usage: Client [start-port[:end-port]]");
            System.exit(1);
        }

        LinkedList<Integer> ports = new LinkedList<>();
        if (args.length == 1) {
            if (args[0].contains(":")) {
                String[] portTokens = args[0].split(":");
                assert portTokens.length == 2;
                int portStart = Integer.parseInt(portTokens[0]);
                int portEnd = Integer.parseInt(portTokens[1]);
                while (portStart <= portEnd) {
                    ports.add(portStart++);
                }
            } else {
                ports.add(Integer.parseInt(args[0]));
            }
        } else {
            ports.add(Constants.PORT);
        }
        System.out.println("Sending requests to ports: " + ports);

        Random random = new Random();

        while (true) {
            int index = random.nextInt(ports.size());
            makeRequest(Constants.ASIA_PATH, ports.get(index));
            index = random.nextInt(ports.size());
            makeRequest(Constants.AMERICA_PATH, ports.get(index));
        }
    }
}
