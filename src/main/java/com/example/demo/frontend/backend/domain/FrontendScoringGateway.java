package com.example.demo.frontend.backend.domain;

import com.example.demo.common.Gateway;
import com.example.demo.frontend.backend.event.ComputeScoreCommand;
import com.example.demo.frontend.backend.event.ComputeScoreError;
import com.example.demo.frontend.backend.event.ScoreComputedEvent;
import org.springframework.messaging.Message;

public interface FrontendScoringGateway extends Gateway<FrontendInterface> {

    /** Async Request/Reply client */
    void sendComputeScoreCommand(ComputeScoreCommand command);
    void onComputeScoreSuccess(Message<ScoreComputedEvent> success);
    void onComputeScoreError(Message<ComputeScoreError> error);

    /** Remote API call or local in-process call depending on "(a)synchronous" Spring Profile */
    Score getScore(String scoreUrl);
}