package com.example.demo.client.impl;

import com.example.demo.client.domain.ClientFrontendGateway;
import com.example.demo.client.domain.ClientInterface;
import com.example.demo.client.event.CustomerScoredEvent;
import com.example.demo.common.GatewayImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.example.demo.client.impl.ClientChannels.CLIENT_IN;


@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
class ClientFrontendGatewayImpl extends GatewayImpl<ClientInterface> implements ClientFrontendGateway {

	@StreamListener(target = CLIENT_IN, condition = "headers['type']=='CustomerScoredEvent'")
	public void onCustomerScoredEvent(Message<CustomerScoredEvent> event) {
		log.debug("onCustomerScoredEvent: {}", event);

		businessInterface.onCustomerScoredEvent(event.getPayload());
	}
}