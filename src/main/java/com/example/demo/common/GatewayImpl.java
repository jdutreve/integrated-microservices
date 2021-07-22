package com.example.demo.common;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@SuppressWarnings("ALL")
@Slf4j
public abstract class GatewayImpl<T> extends ProxyGatewayImpl implements Gateway<T> {

	@Setter
    protected T businessInterface;

    protected Message prepareEvent(CloudEvent payload) {
        return prepareAsyncMessage(payload, false, true);
    }
}