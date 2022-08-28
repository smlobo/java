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
public class MovieController {
    private final WebClient client = WebClient.create("http://localhost:8085");
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    public Mono<Movie> movie() {
        return client
                .get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Movie.class);
    }

    @GetMapping("/movies-json")
    public Flux<Movie> movies(@RequestParam(name="count", required=false, defaultValue="2") String count) {
        LinkedList<Mono<Movie>> monos = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            monos.add(movie());
        return Flux.merge(monos);
    }

    @GetMapping("/movies")
    public String movies(@RequestParam(name="count", required=false, defaultValue = "2") String count,
                            Model model) {
        Instant start = Instant.now();

        LinkedList<Mono<Movie>> monos = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            monos.add(movie());

        Flux<Movie> movieFlux = Flux.merge(monos);

        model.addAttribute("count", count);
        model.addAttribute("movies", movieFlux.collectList().block());

        logger.info("Time to reactively get " + count + " movies: " +
                Duration.between(start, Instant.now()).toMillis() + "ms");

        return "movie-list";
    }
}
