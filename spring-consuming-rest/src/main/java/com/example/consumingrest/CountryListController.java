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
public class CountryListController {
    private static final Logger logger = LoggerFactory.getLogger(CountryListController.class);

    @GetMapping("/countries")
    public String countries(@RequestParam(name="count", required=false, defaultValue = "2") String count,
                          Model model, RestTemplate restTemplate) {
        Instant start = Instant.now();
        LinkedList<Country> countryLinkedList = new LinkedList<>();
        for (int i = 0; i < Integer.parseInt(count); i++)
            countryLinkedList.add(restTemplate.getForObject("http://localhost:8083/", Country.class));

        model.addAttribute("count", count);
        model.addAttribute("countries", countryLinkedList);

        logger.info("Time to get " + count + " countries: " + Duration.between(start, Instant.now()).toMillis() + "ms");

        return "country-list";
    }
}
