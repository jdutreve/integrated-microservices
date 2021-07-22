package com.example.demo.client;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("ALL")
@Configuration
public class ClientConfiguration {

	@Bean
	public FanoutExchange frontOutExchange() {
		return new FanoutExchange("frontendOutTopic");
	}

	@Bean
	public HeadersExchange clientInExchange() {
		return new HeadersExchange("clientInTopic");
	}

	@Bean()
	public Binding frontendBinding() {
		return BindingBuilder.bind(clientInExchange()).to(frontOutExchange());
	}

	@Bean
	public Queue logInQueue() {
		return new Queue("logInQueue");
	}

	@Bean
	public FanoutExchange clientOutExchange() {
		return new FanoutExchange("clientOutTopic");
	}

	@Bean()
	public Binding scoringOutLogBinding() {
		return BindingBuilder.bind(logInQueue()).to(clientOutExchange());
	}
}