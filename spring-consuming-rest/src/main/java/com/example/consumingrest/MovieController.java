package com.example.consumingrest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Controller
public class MovieController {
    @GetMapping("/movie")
    public String movie(@RequestParam(name="genre", required=false, defaultValue = "") String genre,
                           Model model, RestTemplate restTemplate) {
        String template = "movie";

        model.addAttribute("genre", genre);

        Movie movie;
        try {
            movie = restTemplate.getForObject("http://localhost:8085/" + genre, Movie.class);
        }
        catch (RestClientException e) {
            movie = restTemplate.getForObject("http://localhost:8085/", Movie.class);
            template = "badgenre";
        }

        model.addAttribute("title", movie.getName());
        model.addAttribute("date", movie.getRelease());

        return template;
    }
}
