package com.example.demo.frontend.backend.domain;

import com.example.demo.common.AbstractSaga;
import com.example.demo.common.CloudEvent;
import com.example.demo.common.SagaAwareService;
import com.example.demo.frontend.backend.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SuppressWarnings("ALL")
@Slf4j
@Service
/**
 * FrontendService start UpdateCustomerSaga after in case of an error during CreateCustomerSaga.
 */
class FrontendService extends SagaAwareService implements FrontendInterface {

	private final FrontendClientGateway clientGateway;
	private final FrontendCustomerGateway customerGateway;
	private final FrontendScoringGateway scoringGateway;

	FrontendService(ApplicationContext context, FrontendClientGateway clientGateway,
					FrontendCustomerGateway customerGateway, FrontendScoringGateway scoringGateway) {
		super(context);
		this.clientGateway = clientGateway;
		this.customerGateway = customerGateway;
		this.scoringGateway = scoringGateway;
	}

	@PostConstruct
	private void injectThisIntoGateways() {
		this.customerGateway.setBusinessInterface(this);
		this.scoringGateway.setBusinessInterface(this);
		this.clientGateway.setBusinessInterface(this);
	}

	public void onClientStartedEvent(ClientStartedEvent event) {
		log.info("onClientStartedEvent()");

		createSaga(CreateCustomerSaga.class).start(event);
	}

	public void onCreateCustomerSagaEnd(CloudEvent success, CloudEvent error) {
		onSagaEnd(success, error);
		if (error != null) {
			// Try another Saga in the same flow
			createSaga(UpdateCustomerSaga.class).start(error);
		} else {
			log.info("FLOW IN SUCCESS {}", success.getFlowId());
		}
	}

	public void onUpdateCustomerSagaEnd(CloudEvent success, CloudEvent error) {
		CloudEvent event = onSagaEnd(success, error);
		if (success != null) {
			log.info("FLOW IN SUCCESS {}", success.getFlowId());
		} else {
			log.warn("FLOW IN ERROR {}", error.getFlowId());
		}
	}

	public void onCreateCustomerReply(CustomerCreatedEvent success, CreateCustomerError error) {
		AbstractSaga saga = getSaga(success, error);
		if (saga instanceof SagaCallbacks.OnCreateCustomerReply) {
			((SagaCallbacks.OnCreateCustomerReply) saga).onCreateCustomerReply(success, error);
		}
	}

	public void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error) {
		AbstractSaga saga = getSaga(success, error);
		if (saga instanceof SagaCallbacks.OnComputeScoreReply) {
			((SagaCallbacks.OnComputeScoreReply) saga).onComputeScoreReply(success, error);
		}
	}
}