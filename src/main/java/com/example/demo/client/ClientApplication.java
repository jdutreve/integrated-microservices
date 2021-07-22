package com.example.demo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

/**
 * Command line parameters :
 * 		-ea -Dspring.profiles.active=client,async
 *
 * then call http://localhost:8083/start to send en ClientStartEvent to Frontend component
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.client"})
@Profile("client")
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
}