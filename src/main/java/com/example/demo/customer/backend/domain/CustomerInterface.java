package com.example.demo.customer.backend.domain;

import com.example.demo.customer.backend.event.CreateCustomerCommand;
import com.example.demo.customer.backend.event.SagaCallbacks;

public interface CustomerInterface extends
    SagaCallbacks.OnComputeScoreReply,
    ComputeScoreSaga.Callbacks
{
    /** Command handler */
    void onCreateCustomerCommand(CreateCustomerCommand command);
}