package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SuppressWarnings("ALL")
@Slf4j
public abstract class ProxyGatewayImpl {

    protected Message prepareCommand(CloudEvent payload) {
        return prepareAsyncMessage(payload, true, true);
    }

    protected Message prepareAsyncMessage(CloudEvent payload, boolean isCommand, boolean isPublic) {

        MessageBuilder builder = MessageBuilder
            .withPayload(payload)
            .setHeader("isCommand", isCommand)
            .setHeader("isPublic", isPublic)
            .setHeader("type", payload.getType())
            .setHeader("entity", payload.getEntity())
            .setHeader("flowName", payload.getFlowName())
            .setHeader("source", payload.getSource())
            .setHeader("replyTo", payload.getReplyTo());
        Message result = builder.build();

        return result;
    }
}