package com.example.demo.client.domain;

import com.example.demo.client.event.ClientStartedEvent;
import com.example.demo.common.Gateway;

public interface ClientGateway extends Gateway<ClientInterface> {

    void publishClientStartedEvent(ClientStartedEvent event);
}