package com.example.demo.customer.backend.impl;

import com.example.demo.common.GatewayImpl;
import com.example.demo.customer.backend.domain.CustomerInterface;
import com.example.demo.customer.backend.domain.Score;
import com.example.demo.customer.backend.domain.ScoringGateway;
import com.example.demo.customer.backend.event.ComputeScoreCommand;
import com.example.demo.customer.backend.event.ComputeScoreError;
import com.example.demo.customer.backend.event.ScoreComputedEvent;
import com.example.demo.frontend.backend.event.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.customer.backend.impl.CustomerChannels.CUSTOMER_IN;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ScoringChannels.class)
@Primary
class CustomerScoringGatewayImpl extends GatewayImpl<CustomerInterface> implements ScoringGateway {

	protected final ScoringChannels scoringChannels;
	protected final RestTemplate restTemplate;

	public void sendComputeScoreCommand(ComputeScoreCommand command) {
		log.info("sendComputeScoreCommand HTTP {}", command);

		restTemplate.postForObject("http://localhost:8082/scores", command, Void.class);
	}

	@StreamListener(target = CUSTOMER_IN, condition = "headers['type']=='ScoreComputedEvent'")
	public void onComputeScoreSuccess(Message<ScoreComputedEvent> success) {
		log.info("onComputeScoreSuccess: {}", success);

		businessInterface.onComputeScoreReply(success.getPayload(), null);
	}

	@StreamListener(target = CUSTOMER_IN, condition = "headers['type']=='ComputeScoreError'")
	public void onComputeScoreError(Message<ComputeScoreError> error) {
		log.info("onComputeScoreError: {}", error);

		businessInterface.onComputeScoreReply(null, error.getPayload());
	}

	public Score getScore(String scoreUrl) {
		Score score = restTemplate.getForObject(scoreUrl, Score.class);
		return score;
	}
}