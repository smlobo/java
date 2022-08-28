package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HorrorController {
	@GetMapping("/horror")
	public Movie greeting() {
		return DatabaseLogic.queryGenre("horror");
	}
}
