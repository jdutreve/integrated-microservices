package com.example.demo.frontend.backend;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("ALL")
@Configuration
public class FrontendConfiguration {

	@Bean
	public FanoutExchange scoringOutExchange() {
		return new FanoutExchange("scoringOutTopic");
	}

	@Bean
	public FanoutExchange customerOutExchange() {
		return new FanoutExchange("customerOutTopic");
	}

	@Bean
	public HeadersExchange frontendInExchange() {
		return new HeadersExchange("frontendInTopic");
	}

	@Bean
	public Queue frontendInQueue() {
		return new Queue("frontendInQueue");
	}

	@Bean()
	public Binding frontendClientBinding() {
		return BindingBuilder.bind(frontendInQueue()).to(frontendInExchange()).where("source").matches("client");
	}

	@Bean()
	public Binding scoringBinding() {
		return BindingBuilder.bind(frontendInExchange()).to(scoringOutExchange());
	}

	@Bean()
	public Binding customerBinding() {
		return BindingBuilder.bind(frontendInExchange()).to(customerOutExchange());
	}

	@Bean
	public FanoutExchange clientOutExchange() {
		return new FanoutExchange("clientOutTopic");
	}

	@Bean()
	public Binding clientBinding() {
		return BindingBuilder.bind(frontendInExchange()).to(clientOutExchange());
	}

	@Bean
	public Queue logInQueue() {
		return new Queue("logInQueue");
	}

	@Bean
	public FanoutExchange frontendOutExchange() {
		return new FanoutExchange("frontendOutTopic");
	}

	@Bean()
	public Binding frontendOutLogBinding() {
		return BindingBuilder.bind(logInQueue()).to(frontendOutExchange());
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}