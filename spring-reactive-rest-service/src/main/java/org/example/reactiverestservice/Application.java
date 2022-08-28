package org.example.reactiverestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		CountryWebClient gwc = new CountryWebClient();
		//System.out.println(gwc.getResult());
	}
}
