package com.example.consumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

@Controller
public class MovieListController {
    private static final Logger logger = LoggerFactory.getLogger(MovieListController.class);

    @GetMapping("/movies")
    public String movies(@RequestParam(name="count", required=false, defaultValue = "2") String count,
                          Model model, RestTemplate restTemplate) {
        Instant start = Instant.now();
        LinkedList<Movie> movieLinkedList = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            movieLinkedList.add(restTemplate.getForObject("http://localhost:8085/", Movie.class));

        model.addAttribute("count", count);
        model.addAttribute("movies", movieLinkedList);

        logger.info("Time to get " + count + " movies: " + Duration.between(start, Instant.now()).toMillis() + "ms");

        return "movie-list";
    }
}
