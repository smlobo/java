package org.sheldon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class Client {
    private static void makeRequest(String restEndpoint) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/country" + restEndpoint))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Headers
        HttpHeaders headers = response.headers();
        Optional<String> server = headers.firstValue("Server");

        System.out.println("Got: " + response.statusCode() + " {" + server.orElse("no-server") + "} : " +
                response.body());
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            makeRequest("/asia");
            makeRequest("/america");
        }
    }
}
