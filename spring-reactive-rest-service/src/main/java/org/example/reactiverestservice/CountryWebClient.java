package org.example.reactiverestservice;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class CountryWebClient {
	private WebClient client = WebClient.create("http://localhost:" + System.getProperty("server.port"));

//	private Mono<ClientResponse> result = client.get()
//			.uri("/")
//			.accept(MediaType.APPLICATION_JSON)
//			.exchange();
//
//	public String getResult() {
//		return ">> result = " + result.flatMap(res -> res.bodyToMono(String.class)).block();
//	}
}
