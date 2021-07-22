package com.example.demo.scoring.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * Command line parameters :
 * 		-ea -Dspring.profiles.active=scoring,async
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.scoring.backend", "com.example.demo.scoring.query"})
@Profile("scoring")
public class ScoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoringApplication.class, args);
	}
}