package com.example.electricStation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ElectricStationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricStationApplication.class, args);

		// branch test
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
