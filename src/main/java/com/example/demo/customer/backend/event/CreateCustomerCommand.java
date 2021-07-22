package com.example.demo.customer.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class CreateCustomerCommand extends CloudEvent<CreateCustomerCommand.Payload> {

    public CreateCustomerCommand(String name, int value) {
        this.setData(new Payload(name, value));
    }

    @Value
    public static class Payload extends Data {
        String name;
        int value;
    }
}