package com.example.demo.scoring.backend;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("ALL")
@Configuration
public class ScoringConfiguration {

	@Bean
	public HeadersExchange scoringInExchange() {
		return new HeadersExchange("scoringInTopic");
	}

	@Bean
	public Queue logInQueue() {
		return new Queue("logInQueue");
	}

	@Bean()
	public Binding scoringInLogBinding() {
		return BindingBuilder.bind(logInQueue()).to(scoringInExchange()).where("isCommand").matches(true);
	}

	@Bean
	public FanoutExchange scoringOutExchange() {
		return new FanoutExchange("scoringOutTopic");
	}

	@Bean()
	public Binding scoringOutLogBinding() {
		return BindingBuilder.bind(logInQueue()).to(scoringOutExchange());
	}
}