package com.example.demo.customer.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class CustomerCreatedEvent extends CloudEvent<CustomerCreatedEvent.Payload> {

    public CustomerCreatedEvent(String surname, int scores) {
        this.setData(new Payload(surname, scores));
        setEntity("Customer");
    }

    @Value
    public static class Payload extends Data {
        String surname;
        int scores;
    }
}