package com.example.demo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public abstract class AbstractService {

    @Value("${spring.application.name}")
    private String appName;

    protected abstract String getSource();

    public void initReplyFromCommand(CloudEvent command, CloudEvent reply) {
        reply.initMessage();
        reply.setServer(appName);
        reply.setSource(getSource());
        reply.setFlowName(command.getFlowName());
        reply.setFlowId(command.getFlowId());
        reply.setSagaName(command.getSagaName());
        reply.setSagaId(command.getSagaId());
        reply.setCorrelationId(command.getId());
        reply.setDestination(command.getReplyTo());
        reply.setReplyTo(command.getReplyTo());
    }
}