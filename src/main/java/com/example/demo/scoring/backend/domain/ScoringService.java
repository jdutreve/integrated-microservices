package com.example.demo.scoring.backend.domain;

import com.example.demo.common.AbstractService;
import com.example.demo.common.CloudEvent;
import com.example.demo.scoring.backend.event.ComputeScoreCommand;
import com.example.demo.scoring.backend.event.ComputeScoreError;
import com.example.demo.scoring.backend.event.ScoreComputedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
public class ScoringService extends AbstractService implements AsyncScoringInterface {

	private final ScoringGateway scoringGateway;
	private int counter = 0;

	@PostConstruct
	private void injectThisIntoGateways() {
		this.scoringGateway.setBusinessInterface(this);
	}

	public void onComputeScoreCommand(ComputeScoreCommand command) {
		counter++;

		final CloudEvent reply;
		if (counter % 3 == 0) {  // Simulate different business logic
			reply = new ComputeScoreError("UnknownScore", 404);
		} else {
			// Claim Check Pattern
			reply = new ScoreComputedEvent("http://localhost:8082/scores/"+command.getData().getValue());
		}
		initReplyFromCommand(command, reply);
		scoringGateway.publishReply(reply);
	}

	@Override
	protected String getSource() {
		return "scoring";
	}
}