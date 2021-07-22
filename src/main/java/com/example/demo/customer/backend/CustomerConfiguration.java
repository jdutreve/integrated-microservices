package com.example.demo.customer.backend;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SuppressWarnings("ALL")
@Configuration
public class CustomerConfiguration {

	@Bean
	public FanoutExchange scoringOutExchange() {
		return new FanoutExchange("scoringOutTopic");
	}

	@Bean
	public HeadersExchange customerInExchange() {
		return new HeadersExchange("customerInTopic");
	}

	@Bean()
	public Binding customerScoringBinding() {
		return BindingBuilder.bind(customerInExchange()).to(scoringOutExchange());
	}

	@Bean
	public Queue logInQueue() {
		return new Queue("logInQueue");
	}

	@Bean()
	public Binding customerLogbinding() {
		return BindingBuilder.bind(logInQueue()).to(customerInExchange()).where("isCommand").matches(true);
	}

	@Bean
	public FanoutExchange customerOutExchange() {
		return new FanoutExchange("customerOutTopic");
	}

	@Bean()
	public Binding scoringOutLogBinding() {
		return BindingBuilder.bind(logInQueue()).to(customerOutExchange());
	}

	@Bean
	public Queue customerInQueue() {
		return new Queue("customerInQueue");
	}

	@Bean()
	public Binding customerInQueueBinding() {
		return BindingBuilder.bind(customerInQueue()).to(customerInExchange()).where("isCommand").matches(true);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}