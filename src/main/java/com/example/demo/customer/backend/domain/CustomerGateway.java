package com.example.demo.customer.backend.domain;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Gateway;
import com.example.demo.customer.backend.event.CreateCustomerCommand;
import org.springframework.messaging.Message;

public interface CustomerGateway extends Gateway<CustomerInterface> {

    /** Async Request/Reply service */
    void onCreateCustomerCommand(Message<CreateCustomerCommand> command);
    void publishCreateCustomerReply(CloudEvent reply);
}