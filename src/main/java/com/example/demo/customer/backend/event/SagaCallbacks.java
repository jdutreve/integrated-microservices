package com.example.demo.customer.backend.event;

/** Orchestration reply callbacks */
public interface SagaCallbacks {

    interface OnComputeScoreReply {
        void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error);
    }
}