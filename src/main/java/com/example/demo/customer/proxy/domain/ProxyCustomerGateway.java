package com.example.demo.customer.proxy.domain;

import com.example.demo.customer.proxy.event.CreateCustomerCommand;

public interface ProxyCustomerGateway {

    /** Called from REST Controller */
    void sendCreateCustomerCommand(CreateCustomerCommand command);
}