package com.example.demo.frontend.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class CustomerScoredEvent extends CloudEvent<CustomerScoredEvent.Payload> {

    public CustomerScoredEvent(String surname, int scores) {
        this.setData(new Payload(surname, scores));
        setEntity("Customer");
    }

    @Value
    public static class Payload extends Data {
        String surname;
        int scores;
    }
}