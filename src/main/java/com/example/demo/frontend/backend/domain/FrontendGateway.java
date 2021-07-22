package com.example.demo.frontend.backend.domain;

import com.example.demo.common.Gateway;
import com.example.demo.frontend.backend.event.CustomerScoredEvent;

public interface FrontendGateway extends Gateway<FrontendInterface> {

    void publishCustomerScoredEvent(CustomerScoredEvent event);
}