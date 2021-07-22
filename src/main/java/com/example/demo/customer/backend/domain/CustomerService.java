package com.example.demo.customer.backend.domain;

import com.example.demo.common.AbstractSaga;
import com.example.demo.common.CloudEvent;
import com.example.demo.common.SagaAwareService;
import com.example.demo.customer.backend.event.ComputeScoreError;
import com.example.demo.customer.backend.event.CreateCustomerCommand;
import com.example.demo.customer.backend.event.SagaCallbacks;
import com.example.demo.customer.backend.event.ScoreComputedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@SuppressWarnings("ALL")
@Service
@Slf4j
class CustomerService extends SagaAwareService implements CustomerInterface {

	private final CustomerGateway customerGateway;
	private final ScoringGateway scoringGateway;

	public CustomerService(ApplicationContext context, CustomerGateway customerGateway, ScoringGateway scoringGateway) {
		super(context);
		this.customerGateway = customerGateway;
		this.scoringGateway = scoringGateway;
	}

	@PostConstruct
	private void injectThisIntoGateways() {
		this.customerGateway.setBusinessInterface(this);
		this.scoringGateway.setBusinessInterface(this);
	}

	public void onCreateCustomerCommand(CreateCustomerCommand createCustomerRequest) {
		log.info("onCreateCustomerCommand {}", createCustomerRequest);

		createSaga(ComputeScoreSaga.class).start(createCustomerRequest);
	}

	public void onComputeScoreSagaEnd(CloudEvent success, CloudEvent error) {
		onSagaEnd(success, error);
	}

	public void onComputeScoreReply(ScoreComputedEvent success, ComputeScoreError error) {
		AbstractSaga saga = getSaga(success, error);
		if (saga instanceof SagaCallbacks.OnComputeScoreReply) {
			((SagaCallbacks.OnComputeScoreReply) saga).onComputeScoreReply(success, error);
		}
	}
}