package com.example.demo.frontend.backend.event;

/** Orchestration reply callbacks */
public interface SagaCallbacks {

    interface OnCreateCustomerReply {
        void onCreateCustomerReply(CustomerCreatedEvent success, CreateCustomerError error);
    }
    interface OnComputeScoreReply {
        void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error);
    }
}