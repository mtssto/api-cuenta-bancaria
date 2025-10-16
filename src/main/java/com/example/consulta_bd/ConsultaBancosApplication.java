package com.example.consulta_bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ConsultaBancosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaBancosApplication.class, args);
	}
	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
				.build();
	}

}
