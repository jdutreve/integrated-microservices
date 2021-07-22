package com.example.demo.frontend.backend.domain;

import com.example.demo.common.AbstractSaga;
import com.example.demo.common.CloudEvent;
import com.example.demo.frontend.backend.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Scope("prototype")
public class CreateCustomerSaga extends AbstractSaga<FrontendInterface> implements
    SagaCallbacks.OnCreateCustomerReply,
    SagaCallbacks.OnComputeScoreReply
{
    public interface Callbacks {
        void onCreateCustomerSagaEnd(CloudEvent success, CloudEvent error);
    }

    private final FrontendCustomerGateway customerGateway;
    private final FrontendScoringGateway scoringGateway;
    private final FrontendGateway frontendGateway;

    private CreateCustomerCommand createCustomerRequest;
    private ComputeScoreCommand computeScoreRequest;

    public CreateCustomerSaga(FrontendInterface businessInterface, FrontendCustomerGateway customerGateway,
                              FrontendScoringGateway scoringGateway, FrontendGateway frontendGateway) {
        super(businessInterface, "CreateCustomerSaga", UUID.randomUUID().toString());
        this.customerGateway = customerGateway;
        this.scoringGateway = scoringGateway;
        this.frontendGateway = frontendGateway;
    }

    @Override
    protected String getSource() {
        return "frontend";
    }

    protected void doStart(CloudEvent initialEvent) {
        // Async Request/Reply Pattern
        createCustomerRequest = new CreateCustomerCommand("Mr Dupont en €", 200);
        initMessageFromInitialEvent(initialEvent, createCustomerRequest);
        customerGateway.sendCreateCustomerCommand(createCustomerRequest);
    }

    public void onCreateCustomerReply(CustomerCreatedEvent success, CreateCustomerError error) {
        CloudEvent event = success != null ? success : error;
        assert createCustomerRequest.getId().equals(event.getCorrelationId());

        if (success != null) {
            // Publish/Subscribe Pattern
            CustomerScoredEvent message = new CustomerScoredEvent(success.getData().getSurname(), success.getData().getScores());
            initMessageFromInitialEvent(event, message);
            frontendGateway.publishCustomerScoredEvent(message);
            businessInterface.onCreateCustomerSagaEnd(success, null);

        } else {
            log.info("Error while creating Customer {}. Trying fallback by computing score...", error);

            // Trying a direct scoring of the default customer...
            // Async Request/Reply Pattern
            computeScoreRequest = new ComputeScoreCommand("Default Customer", 300);
            initMessageFromInitialEvent(event, computeScoreRequest);
            scoringGateway.sendComputeScoreCommand(computeScoreRequest);
        }
    }

    public void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error) {
        CloudEvent event = success != null ? success : error;
        assert computeScoreRequest.getId().equals(event.getCorrelationId());

        if (success != null) {
            // Sync Request/Reply Pattern (Claim Check Pattern)
            Score score = scoringGateway.getScore(success.getData().getScoreUrl());

            // Publish/Subscribe Pattern
            CustomerScoredEvent message = new CustomerScoredEvent(score.getFirstname(), score.getValue());
            initMessageFromInitialEvent(event, message);
            frontendGateway.publishCustomerScoredEvent(message);
            businessInterface.onCreateCustomerSagaEnd(success, null);

        } else {
            businessInterface.onCreateCustomerSagaEnd(null, error);
        }
    }
}