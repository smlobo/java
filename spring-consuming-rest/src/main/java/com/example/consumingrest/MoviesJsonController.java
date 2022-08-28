package com.example.consumingrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MoviesJsonController {
    @GetMapping("/movies-json")
    public List<Movie> movies(@RequestParam(name="count", required=false, defaultValue = "2") String count,
                              RestTemplate restTemplate) {
        LinkedList<Movie> movieLinkedList = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            movieLinkedList.add(restTemplate.getForObject("http://localhost:8085/", Movie.class));
        return movieLinkedList;
    }
}
