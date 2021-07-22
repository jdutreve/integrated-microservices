package com.example.demo.frontend.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * Command line parameters :
 * 		-ea -Dspring.profiles.active=frontend,sync -Dspring.main.allow-bean-definition-overriding=true
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.frontend.backend"
	, "com.example.demo.customer.proxy"
	, "com.example.demo.scoring.backend", "com.example.demo.scoring.query"
})
@Profile("frontend")
public class FrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}
}