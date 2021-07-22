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
public class UpdateCustomerSaga extends AbstractSaga<FrontendInterface> implements
    SagaCallbacks.OnCreateCustomerReply,
    SagaCallbacks.OnComputeScoreReply
{
    public interface Callbacks {
        void onUpdateCustomerSagaEnd(CloudEvent success, CloudEvent error);
    }

    private final FrontendCustomerGateway customerGateway;
    private final FrontendScoringGateway scoringGateway;
    private final FrontendGateway frontendGateway;

    private CreateCustomerCommand createCustomerRequest;
    private ComputeScoreCommand computeScoreRequest;

    public UpdateCustomerSaga(FrontendInterface businessInterface, FrontendCustomerGateway customerGateway,
                              FrontendScoringGateway scoringGateway, FrontendGateway frontendGateway) {
        super(businessInterface, "UpdateCustomerSaga", UUID.randomUUID().toString());
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
        computeScoreRequest = new ComputeScoreCommand("Default Customer", 100);
        initMessageFromInitialEvent(initialEvent, computeScoreRequest);
        scoringGateway.sendComputeScoreCommand(computeScoreRequest);
    }

    public void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error) {
        CloudEvent reply = success != null ? success : error;
        assert computeScoreRequest.getId().equals(reply.getCorrelationId());

        if (success != null) {
            // Success is a Claim Check Pattern with a Resource URL from which we can get the payload
            // Sync Request/Reply Pattern
            Score score = scoringGateway.getScore(success.getData().getScoreUrl());

            // Publish/Subscribe Pattern
            CustomerScoredEvent message = new CustomerScoredEvent(score.getFirstname(), score.getValue());
            initMessageFromInitialEvent(reply, message);
            frontendGateway.publishCustomerScoredEvent(message);
            businessInterface.onUpdateCustomerSagaEnd(success, null);

        } else {
            log.info("Error while computing score {}. Trying fallback by creating customer...", error);

            // Async Request/Reply Pattern
            createCustomerRequest = new CreateCustomerCommand("Mr Dupond en €", 400);
            initMessageFromInitialEvent(reply, createCustomerRequest);
            customerGateway.sendCreateCustomerCommand(createCustomerRequest);
        }
    }

    public void onCreateCustomerReply(CustomerCreatedEvent success, CreateCustomerError error) {
        CloudEvent event = success != null ? success : error;
        assert createCustomerRequest.getId().equals(event.getCorrelationId());

        if (success != null) {
            // Publish/Subscribe Pattern
            CustomerScoredEvent message = new CustomerScoredEvent(success.getData().getSurname(), success.getData().getScores());
            initMessageFromInitialEvent(event, message);
            frontendGateway.publishCustomerScoredEvent(message);
            businessInterface.onUpdateCustomerSagaEnd(success, null);

        } else {
            businessInterface.onUpdateCustomerSagaEnd(null, error);
        }
    }
}