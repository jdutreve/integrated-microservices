package com.example.demo.client.domain;

import com.example.demo.client.event.CustomerScoredEvent;
import com.example.demo.common.Gateway;
import org.springframework.messaging.Message;

public interface ClientFrontendGateway extends Gateway<ClientInterface> {

    void onCustomerScoredEvent(Message<CustomerScoredEvent> event);
}