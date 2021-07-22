package com.example.demo.frontend.backend.domain;

import com.example.demo.common.Gateway;
import com.example.demo.frontend.backend.event.ClientStartedEvent;
import org.springframework.messaging.Message;

public interface FrontendClientGateway extends Gateway<FrontendInterface> {

    void onClientStartedEvent(Message<ClientStartedEvent> event);
}