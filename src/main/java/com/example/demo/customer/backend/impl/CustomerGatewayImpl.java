package com.example.demo.customer.backend.impl;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.GatewayImpl;
import com.example.demo.customer.backend.domain.CustomerGateway;
import com.example.demo.customer.backend.domain.CustomerInterface;
import com.example.demo.customer.backend.event.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import static com.example.demo.customer.backend.impl.CustomerChannels.CUSTOMER_IN;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
@EnableBinding(CustomerChannels.class)
class CustomerGatewayImpl extends GatewayImpl<CustomerInterface> implements CustomerGateway {

	private final CustomerChannels customerChannels;

	@StreamListener(target = CUSTOMER_IN, condition = "headers['type']=='CreateCustomerCommand'")
	public void onCreateCustomerCommand(Message<CreateCustomerCommand> command) {
		log.info("onCreateCustomerCommand() {}", command);

		businessInterface.onCreateCustomerCommand(command.getPayload());
	}

	public void publishCreateCustomerReply(CloudEvent reply) {
		log.info("publishCreateCustomerReply {}", reply);

		Message event = prepareEvent(reply);
		customerChannels.customerOut().send(event);
	}
}