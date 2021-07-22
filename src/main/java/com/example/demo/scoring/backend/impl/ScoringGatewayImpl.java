package com.example.demo.scoring.backend.impl;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.GatewayImpl;
import com.example.demo.scoring.backend.domain.ScoringGateway;
import com.example.demo.scoring.backend.domain.AsyncScoringInterface;
import com.example.demo.scoring.backend.event.ComputeScoreCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.example.demo.scoring.backend.impl.ScoringChannels.SCORING_IN;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ScoringChannels.class)
class ScoringGatewayImpl extends GatewayImpl<AsyncScoringInterface> implements ScoringGateway {

	private final ScoringChannels scoringChannels;

	@StreamListener(target = SCORING_IN, condition = "headers['type']=='ComputeScoreCommand'")
	public void onComputeScoreCommand(Message<ComputeScoreCommand> message) {
		log.info("onComputeScoreCommand: {}", message);

		businessInterface.onComputeScoreCommand(message.getPayload());
	}

	public void publishReply(CloudEvent reply) {
		log.info("publishReply: {}", reply);

		Message message = prepareEvent(reply);
		scoringChannels.scoringOut().send(message);
	}
}