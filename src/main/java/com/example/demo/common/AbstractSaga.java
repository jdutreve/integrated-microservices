package com.example.demo.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractSaga<T> extends AbstractService {

    @Value("${spring.application.name}")
    private String appName;

    protected final T businessInterface;

    private String flowName;
    private String flowId;

    private final String sagaName;
    private final String sagaId;

    // Template Method Pattern
    protected abstract void doStart(CloudEvent initialMessage);

    public final <T extends CloudEvent> void start(T initialMessage)  {
        if (initialMessage.getFlowId() == null)  {
            // No flow exists yet, create one!
            setFlowName(sagaName.replace("Saga","Flow"));
            setFlowId(sagaId);
        } else {
            // A flow is on-going, just keep it!
            setFlowName(initialMessage.getFlowName());
            setFlowId(initialMessage.getFlowId());
        }
        log.info("Starting SAGA {}", getSagaName());
        doStart(initialMessage);
    }

    @Override
    public String toString() {
        return sagaName+"{sagaId=" + sagaId + '}';
    }

    public void initMessage(CloudEvent message) {
        message.initMessage();
        message.setServer(appName);
        message.setSource(getSource());
        message.setFlowName(getFlowName());
        message.setFlowId(getFlowId());
        message.setSagaName(getSagaName());
        message.setSagaId(getSagaId());
    }

    public void initMessageFromInitialEvent(CloudEvent initialMessage, CloudEvent message) {
        initMessage(message);
        message.setCorrelationId(initialMessage.getId());
    }

    public String getFlowId() {
        return flowId;
    }

    public String getFlowName() {
        return flowName;
    }

    public String getSagaName() {
        return sagaName;
    }

    public String getSagaId() {
        return sagaId;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }
}