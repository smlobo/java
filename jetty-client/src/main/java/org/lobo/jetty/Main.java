package org.lobo.jetty;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger("jetty-client");

    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] %4$s %2$s %5$s%6$s%n");

        HttpClient client = new HttpClient();
        client.start();

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("Enter to send a new random name OR q(uit): ");
            String input = in.nextLine();
            if ("q".equals(input)) {
                LOGGER.info("Thanks for playing, goodbye!");
                client.stop();
                System.exit(0);
            }
            List<String> names = DatabaseLogic.randomNames(1);
            LOGGER.info("Sending: " + names.get(0));
            ContentResponse res = client.GET("http://localhost:8080/" + names.get(0));
            LOGGER.info("Received: " + res.getContentAsString());
        }
    }
}
