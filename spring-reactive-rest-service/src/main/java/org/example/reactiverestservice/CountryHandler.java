package org.example.reactiverestservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CountryHandler {
	private static AtomicLong requestCount = new AtomicLong();
	private static final Logger logger = LoggerFactory.getLogger(CountryHandler.class);

	public Mono<ServerResponse> anyCountry(ServerRequest request) {
		logger.info("[" + requestCount.incrementAndGet() + "] " + request.remoteAddress() + " -> " + request.uri());
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
			.bodyValue(DatabaseLogic.queryIncomeGroup(null));
	}

	public Mono<ServerResponse> incomeCountry(ServerRequest request) {
		String incomeGroup = request.pathVariable("incomeGroup");

		// Convert the path income group to the DB income group
		String dbIncomeGroup;
		switch (incomeGroup) {
			case "low":
				dbIncomeGroup = "Low income";
				break;
			case "upper-middle":
				dbIncomeGroup = "Upper middle income";
				break;
			case "high-non-oecd":
				dbIncomeGroup = "High income: nonOECD";
				break;
			case "lower-middle":
				dbIncomeGroup = "Lower middle income";
				break;
			case "high-oecd":
				dbIncomeGroup = "High income: OECD";
				break;
			default:
				return ServerResponse.badRequest().build();
		}

		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.bodyValue(DatabaseLogic.queryIncomeGroup(dbIncomeGroup));
	}
}
