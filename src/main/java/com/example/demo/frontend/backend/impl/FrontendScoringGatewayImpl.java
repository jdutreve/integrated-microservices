package com.example.demo.frontend.backend.impl;

import com.example.demo.common.GatewayImpl;
import com.example.demo.frontend.backend.domain.FrontendInterface;
import com.example.demo.frontend.backend.domain.FrontendScoringGateway;
import com.example.demo.frontend.backend.domain.Score;
import com.example.demo.frontend.backend.event.ComputeScoreCommand;
import com.example.demo.frontend.backend.event.ComputeScoreError;
import com.example.demo.frontend.backend.event.ScoreComputedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.web.client.RestTemplate;

import static com.example.demo.frontend.backend.impl.FrontendChannels.FRONTEND_IN;

@SuppressWarnings("ALL")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ScoringChannels.class)
class FrontendScoringGatewayImpl extends GatewayImpl<FrontendInterface> implements FrontendScoringGateway {

	protected final ScoringChannels scoringChannels;
	protected final RestTemplate restTemplate;

	public void sendComputeScoreCommand(ComputeScoreCommand payload) {
		log.debug("sendComputeScoreCommand {}", payload);

		Message message = prepareCommand(payload);
		scoringChannels.scoringIn().send(message);
	}

	@StreamListener(target = FRONTEND_IN, condition = "headers['type']=='ScoreComputedEvent'")
	public void onComputeScoreSuccess(Message<ScoreComputedEvent> success) {
		log.debug("onComputeScoreSuccess: {}", success);

		businessInterface.onComputeScoreReply(success.getPayload(), null);
	}

	@StreamListener(target = FRONTEND_IN, condition = "headers['type']=='ComputeScoreError'")
	public void onComputeScoreError(Message<ComputeScoreError> error) {
		log.debug("onComputeScoreError: {}", error);

		businessInterface.onComputeScoreReply(null, error.getPayload());
	}

	public Score getScore(String scoreUrl) {
		Score score = restTemplate.getForObject(scoreUrl, Score.class);
		return score;
	}
}