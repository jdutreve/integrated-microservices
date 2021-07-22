package com.example.demo.scoring.proxy.impl;

import com.example.demo.common.ProxyGatewayImpl;
import com.example.demo.scoring.proxy.domain.ProxyScoringGateway;
import com.example.demo.scoring.proxy.event.ComputeScoreCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ProxyScoringChannels.class)
class ProxyScoringGatewayImpl extends ProxyGatewayImpl implements ProxyScoringGateway {

	private final ProxyScoringChannels scoringChannels;

	public void sendComputeScoreCommand(ComputeScoreCommand command) {
		log.info("Sending {} to SELF", command);

		Message message = prepareCommand(command);
		scoringChannels.scoringIn().send(message);
	}
}