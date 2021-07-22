package com.example.demo.customer.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * Command line parameters :
 * 		-ea -Dspring.profiles.active=customer,async
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.customer.backend"
	, "com.example.demo.scoring.proxy", "com.example.demo.scoring.query"
})
@Profile("customer")
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}
}