package com.example.demo.customer.proxy.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class CreateCustomerCommand extends CloudEvent<CreateCustomerCommand.Payload> {

    public CreateCustomerCommand(String name, int value) {
        this.setData(new Payload(name, value));
        setEntity("Customer");
        setDestination("customer");
    }

    @Value
    public static class Payload extends Data {
        String name;
        int value;
    }
}