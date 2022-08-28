package com.example.consumingrest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class CountryController {
    @GetMapping("/country")
    public String country(@RequestParam(name="income", required=false, defaultValue = "") String income,
                           Model model, RestTemplate restTemplate) {
        String template = "country";

        model.addAttribute("income", income);

        Country country;
        try {
            country = restTemplate.getForObject("http://localhost:8083/" + income, Country.class);
        }
        catch (HttpClientErrorException e) {
            country = restTemplate.getForObject("http://localhost:8083/", Country.class);
            template = "badincome";
        }

        model.addAttribute("name", country.getName());
        model.addAttribute("currency", country.getCurrency());
        model.addAttribute("region", country.getRegion());

        return template;
    }
}
