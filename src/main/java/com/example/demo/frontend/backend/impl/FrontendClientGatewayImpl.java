package com.example.demo.frontend.backend.impl;

import com.example.demo.common.GatewayImpl;
import com.example.demo.frontend.backend.domain.FrontendClientGateway;
import com.example.demo.frontend.backend.domain.FrontendInterface;
import com.example.demo.frontend.backend.event.ClientStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.example.demo.frontend.backend.impl.FrontendChannels.FRONTEND_IN;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
class FrontendClientGatewayImpl extends GatewayImpl<FrontendInterface> implements FrontendClientGateway {

	@StreamListener(target = FRONTEND_IN, condition = "headers['type']=='ClientStartedEvent'")
	public void onClientStartedEvent(Message<ClientStartedEvent> event) {
		log.debug("onClientStartedEvent: {}", event);

		businessInterface.onClientStartedEvent(event.getPayload());
	}
}