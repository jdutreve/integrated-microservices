package com.example.demo.customer.backend.domain;

import com.example.demo.common.AbstractSaga;
import com.example.demo.common.CloudEvent;
import com.example.demo.customer.backend.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@Scope("prototype")
public class ComputeScoreSaga extends AbstractSaga<CustomerInterface> implements
    SagaCallbacks.OnComputeScoreReply
{
    public interface Callbacks {
        void onComputeScoreSagaEnd(CloudEvent success, CloudEvent error);
    }

    private final ScoringGateway scoringGateway;
    private final CustomerGateway customerGateway;

    private CreateCustomerCommand createCustomerRequest;
    private ComputeScoreCommand computeScoreRequest;

    public ComputeScoreSaga(CustomerInterface businessInterface, ScoringGateway scoringGateway,
                            CustomerGateway customerGateway) {
        super(businessInterface, "ComputeScoreSaga", UUID.randomUUID().toString());
        this.scoringGateway = scoringGateway;
        this.customerGateway = customerGateway;
    }

    @Override
    protected String getSource() {
        return "customer";
    }

    @Override
    protected void doStart(CloudEvent initialMessage) {
        createCustomerRequest = (CreateCustomerCommand)initialMessage;

        // Async Request/Reply Pattern
        var data = createCustomerRequest.getData();
        computeScoreRequest = new ComputeScoreCommand(data.getName(), data.getValue());
        initMessageFromInitialEvent(createCustomerRequest, computeScoreRequest);
        scoringGateway.sendComputeScoreCommand(computeScoreRequest);
    }

    public void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error) {
        CloudEvent event = success != null ? success : error;
        assert computeScoreRequest.getId().equals(event.getCorrelationId());

        final CloudEvent reply;
        if (success != null) {
            // Success is a Claim Check Pattern with a Resource URL from which we can get the payload
            // Sync Request/Reply Pattern
            Score score = scoringGateway.getScore(success.getData().getScoreUrl());

            reply = new CustomerCreatedEvent(score.getName(), score.getValue());
            businessInterface.onComputeScoreSagaEnd(success, null);

        } else {
            reply = new CreateCustomerError(error.getData().getError(), error.getData().getCode());
            businessInterface.onComputeScoreSagaEnd(null, error);
        }
        initReplyFromCommand(createCustomerRequest, reply);
        customerGateway.publishCreateCustomerReply(reply);
    }
}