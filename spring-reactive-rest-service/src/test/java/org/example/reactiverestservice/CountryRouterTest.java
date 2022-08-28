package org.example.reactiverestservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
//  We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRouterTest {

	// Spring Boot will create a `WebTestClient` for you,
	// already configure and ready to issue requests against "localhost:RANDOM_PORT"
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testDefault() {
		webTestClient
			// Create a GET request to test an endpoint
			.get().uri("/")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			// and use the dedicated DSL to test assertions against the response
			.expectStatus().isOk()
			//.expectBody(String.class).isEqualTo("Hello, Spring!");
			//.expectBody(String.class).value((x) -> Assertions.assertTrue(x.startsWith("Hello, " +
			//	 " - it is: ")));
			.expectBody()
				.jsonPath("$.name").isNotEmpty()
				.jsonPath("$.currency").exists()
				.jsonPath("$.region").exists();
	}

	@Test
	public void testLow() {
		webTestClient
				// Create a GET request to test an endpoint
				.get().uri("/low")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.name").isNotEmpty()
				.jsonPath("$.currency").exists()
				.jsonPath("$.region").exists();
	}

	@Test
	public void testBad() {
		webTestClient
				// Create a GET request to test an endpoint
				.get().uri("/bad")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				// and use the dedicated DSL to test assertions against the response
				.expectStatus().isBadRequest();
	}

}
