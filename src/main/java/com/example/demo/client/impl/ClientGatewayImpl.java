package com.example.demo.client.impl;

import com.example.demo.client.domain.ClientGateway;
import com.example.demo.client.domain.ClientInterface;
import com.example.demo.client.event.ClientStartedEvent;
import com.example.demo.common.GatewayImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ClientChannels.class)
class ClientGatewayImpl extends GatewayImpl<ClientInterface> implements ClientGateway {

	protected final ClientChannels clientChannels;

	public void publishClientStartedEvent(ClientStartedEvent payload) {
		log.info("Publishing event {} to world", payload);

		Message event = prepareEvent(payload);
		clientChannels.clientOut().send(event);
	}
}