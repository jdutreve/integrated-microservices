package com.example.demo.customer.proxy.rest;

import com.example.demo.common.ProxyRestController;
import com.example.demo.customer.proxy.domain.ProxyCustomerGateway;
import com.example.demo.customer.proxy.event.CreateCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
class ProxyCustomerRestController extends ProxyRestController {

	private final ProxyCustomerGateway customerGateway;

	@PostMapping
	public Integer createCustomer(@RequestBody CreateCustomerCommand command) {
		log.info("Forwarding Command to Customer... {}", command);

		// Do some checks on command
		customerGateway.sendCreateCustomerCommand(command);
		return command.getData().getValue();
	}
}