package com.example.webclientconsumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

@Controller
public class CountryController {
    private final WebClient client = WebClient.create("http://localhost:8083");
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @GetMapping("/countries-json")
    public Flux<Country> countries(@RequestParam(name="count", required=false, defaultValue="2") String count) {
        Instant start = Instant.now();

        LinkedList<Mono<Country>> monos = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            monos.add(country());

//        logger.info("Time to reactively get " + count  + " countries: " +
//                Duration.between(start, Instant.now()).toMillis() + "ms");

        return Flux.merge(monos);
    }

    @GetMapping("/countries")
    public String countries(@RequestParam(name="count", required=false, defaultValue = "2") String count,
                            Model model) {
        Instant start = Instant.now();

        LinkedList<Mono<Country>> monos = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            monos.add(country());

        Flux<Country> countryFlux = Flux.merge(monos);

        model.addAttribute("count", count);
        model.addAttribute("countries", countryFlux.collectList().block());

        logger.info("Time to reactively get " + count + " countries: " +
                Duration.between(start, Instant.now()).toMillis() + "ms");

        return "country-list";
    }

    public Mono<Country> country() {
        return client
                .get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Country.class);
    }
}
