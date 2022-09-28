package com.example.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DefaultController {
	private static AtomicLong requestCount = new AtomicLong();
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@GetMapping("/")
	public Movie greeting(HttpServletRequest request) {
		logger.info("[" + requestCount.incrementAndGet() + "] " + request.getRemoteHost() + ":" +
				request.getRemotePort() + " -> " + request.getLocalAddr() + ":" + request.getLocalPort());
		return DatabaseLogic.queryGenre(null);
	}
}
