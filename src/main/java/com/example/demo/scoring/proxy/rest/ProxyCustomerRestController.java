package com.example.demo.scoring.proxy.rest;

import com.example.demo.common.ProxyRestController;
import com.example.demo.scoring.proxy.domain.ProxyScoringGateway;
import com.example.demo.scoring.proxy.event.ComputeScoreCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/scores")
class ProxyScoringRestController extends ProxyRestController {

	private final ProxyScoringGateway scoringGateway;

	@PostMapping
	public void computeScore(@RequestBody ComputeScoreCommand command) {
		log.info("Forwarding Command to Scoring... {}", command);

		// Do some checks on command
		scoringGateway.sendComputeScoreCommand(command);
	}
}