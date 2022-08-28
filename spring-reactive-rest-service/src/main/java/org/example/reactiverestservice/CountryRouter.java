package org.example.reactiverestservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CountryRouter {

	@Bean
	public RouterFunction<ServerResponse> routes(CountryHandler countryHandler) {
		return RouterFunctions
			.route(RequestPredicates.GET("/")
					.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::anyCountry)
				.andRoute(RequestPredicates.GET("/{incomeGroup}")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), countryHandler::incomeCountry);
	}
}
