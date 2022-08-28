package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComedyController {
	@GetMapping("/comedy")
	public Movie greeting() {
		return DatabaseLogic.queryGenre("comedy");
	}
}
