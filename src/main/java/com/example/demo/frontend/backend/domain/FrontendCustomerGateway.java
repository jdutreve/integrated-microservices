package com.example.demo.frontend.backend.domain;

import com.example.demo.common.Gateway;
import com.example.demo.frontend.backend.event.CreateCustomerCommand;
import com.example.demo.frontend.backend.event.CreateCustomerError;
import com.example.demo.frontend.backend.event.CustomerCreatedEvent;
import org.springframework.messaging.Message;

public interface FrontendCustomerGateway extends Gateway<FrontendInterface> {

    /** Async Request/Reply client */
    int sendCreateCustomerCommand(CreateCustomerCommand command);  // TODO fixe int
    void onCreateCustomerSuccess(Message<CustomerCreatedEvent> success);
    void onCreateCustomerError(Message<CreateCustomerError> error);
}