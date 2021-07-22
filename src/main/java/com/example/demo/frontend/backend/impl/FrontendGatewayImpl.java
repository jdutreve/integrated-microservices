package com.example.demo.frontend.backend.impl;

import com.example.demo.common.GatewayImpl;
import com.example.demo.frontend.backend.domain.FrontendGateway;
import com.example.demo.frontend.backend.domain.FrontendInterface;
import com.example.demo.frontend.backend.event.CustomerScoredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;

@SuppressWarnings("ALL")
@Slf4j
@RequiredArgsConstructor
@EnableBinding(FrontendChannels.class)
class FrontendGatewayImpl extends GatewayImpl<FrontendInterface> implements FrontendGateway {

	protected final FrontendChannels frontendChannels;

	public void publishCustomerScoredEvent(CustomerScoredEvent payload) {
		log.debug("Sending {} to SELF", payload);

		Message event = prepareEvent(payload);
		frontendChannels.frontendOut().send(event);
	}
}