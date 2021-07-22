package com.example.demo.customer.backend.domain;

import com.example.demo.common.Gateway;
import com.example.demo.customer.backend.event.ComputeScoreCommand;
import com.example.demo.customer.backend.event.ComputeScoreError;
import com.example.demo.customer.backend.event.ScoreComputedEvent;
import org.springframework.messaging.Message;

public interface ScoringGateway extends Gateway<CustomerInterface> {

    /** Async Request/Reply client */
    void sendComputeScoreCommand(ComputeScoreCommand command);
    void onComputeScoreSuccess(Message<ScoreComputedEvent> success);
    void onComputeScoreError(Message<ComputeScoreError> error);

    /** Remote API call or local in-process call depending on "(a)synchronous" Spring Profile */
    Score getScore(String scoreUrl);
}