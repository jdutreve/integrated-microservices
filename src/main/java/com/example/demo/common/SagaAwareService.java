package com.example.demo.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Slf4j
@Service
@RequiredArgsConstructor
public abstract class SagaAwareService {

	private final ApplicationContext context;

	// In-memory Sagas, keyed by CloudEvent.flowId. Not clusterable!!!
	private static Map<String, AbstractSaga> sagas = new HashMap<>();

	protected CloudEvent onSagaEnd(CloudEvent success, CloudEvent error) {
		CloudEvent event = success != null ? success : error;
		AbstractSaga saga = sagas.remove(event.getSagaId());
		if (error != null) {
			log.warn("SAGA IN ERROR {} ", saga);
		} else {
			log.info("SAGA IN SUCCESS {}", saga);
		}
		return event;
	}

	protected <T extends AbstractSaga> T createSaga(Class<T> clazz) {
		T saga = context.getBean(clazz);
		sagas.put(saga.getSagaId(), saga);
		return saga;
	}

	protected AbstractSaga getSaga(CloudEvent success, CloudEvent error) {
		CloudEvent event = success != null ? success : error;
		AbstractSaga saga = sagas.get(event.getSagaId());
		return saga;
	}
}