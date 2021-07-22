package com.example.demo.frontend.backend.domain;

import com.example.demo.frontend.backend.event.ClientStartedEvent;
import com.example.demo.frontend.backend.event.SagaCallbacks;

public interface FrontendInterface extends
    SagaCallbacks.OnCreateCustomerReply,
    SagaCallbacks.OnComputeScoreReply,
    CreateCustomerSaga.Callbacks,
    UpdateCustomerSaga.Callbacks
{
    /** Choreography Client event handler */
    void onClientStartedEvent(ClientStartedEvent event);
}