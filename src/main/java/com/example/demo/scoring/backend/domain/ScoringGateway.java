package com.example.demo.scoring.backend.domain;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Gateway;
import com.example.demo.scoring.backend.event.ComputeScoreCommand;
import org.springframework.messaging.Message;

public interface ScoringGateway extends Gateway<AsyncScoringInterface> {

	/** Async Request/Reply handler */
	void onComputeScoreCommand(Message<ComputeScoreCommand> command);
	void publishReply(CloudEvent reply);
}