package com.example.demo.customer.proxy.impl;

import com.example.demo.common.ProxyGatewayImpl;
import com.example.demo.customer.proxy.domain.ProxyCustomerGateway;
import com.example.demo.customer.proxy.event.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(ProxyCustomerChannels.class)
class ProxyCustomerGatewayImpl extends ProxyGatewayImpl implements ProxyCustomerGateway {

	private final ProxyCustomerChannels customerChannels;

	public void sendCreateCustomerCommand(CreateCustomerCommand command) {
		log.info("Sending {} to SELF", command);

		Message message = prepareCommand(command);
		customerChannels.customerIn().send(message);
	}
}