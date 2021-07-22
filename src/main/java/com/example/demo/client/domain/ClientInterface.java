package com.example.demo.client.domain;

import com.example.demo.client.event.CustomerScoredEvent;

public interface ClientInterface {

    void onCustomerScoredEvent(CustomerScoredEvent event);
}