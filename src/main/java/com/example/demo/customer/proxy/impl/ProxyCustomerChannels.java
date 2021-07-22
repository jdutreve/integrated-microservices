package com.example.demo.customer.proxy.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


@SuppressWarnings("ALL")
public interface ProxyCustomerChannels {

    @Output
    MessageChannel customerIn();
}