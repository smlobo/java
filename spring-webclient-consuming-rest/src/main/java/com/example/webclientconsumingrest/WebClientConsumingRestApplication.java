package com.example.webclientconsumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
public class WebClientConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(WebClientConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WebClientConsumingRestApplication.class, args);
	}

//	@Bean
//	public CommonsRequestLoggingFilter requestLoggingFilter() {
//		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
//		loggingFilter.setIncludeClientInfo(true);
//		loggingFilter.setIncludeQueryString(true);
//		loggingFilter.setIncludePayload(true);
//		loggingFilter.setIncludeHeaders(false);
//		return loggingFilter;
//	}
}
