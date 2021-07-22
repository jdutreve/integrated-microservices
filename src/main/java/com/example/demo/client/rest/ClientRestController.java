package com.example.demo.client.rest;

import com.example.demo.client.domain.ClientFrontendGateway;
import com.example.demo.client.domain.ClientGateway;
import com.example.demo.client.domain.ClientInterface;
import com.example.demo.client.event.ClientStartedEvent;
import com.example.demo.client.event.CustomerScoredEvent;
import com.example.demo.common.ProxyRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SuppressWarnings("ALL")
@Slf4j
@RestController
@RequiredArgsConstructor
class ClientRestController extends ProxyRestController implements ClientInterface {

	private final ClientGateway clientGateway;
	private final ClientFrontendGateway frontendGateway;

	@PostConstruct
	private void injectThisIntoGateways() {
		this.clientGateway.setBusinessInterface(this);
		this.frontendGateway.setBusinessInterface(this);
	}

	@GetMapping("/start")
	public void start() {
		log.info("Start the client and publish ClientStartedEvent");

		ClientStartedEvent event = new ClientStartedEvent("New Client Event fired!");
		event.initMessage();
		clientGateway.publishClientStartedEvent(event);
	}

	public void onCustomerScoredEvent(CustomerScoredEvent event) {
		log.info("onCustomerScoredEvent {}", event);
	}
}