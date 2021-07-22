package com.example.demo.customer.backend.impl;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


@SuppressWarnings("ALL")
public interface CustomerChannels {

    // We name channel "customerInIn" because in case of single process deployment (dev, DemoApplication),
    // Topic/Queue customerIn is both @INPUT and @OUTPUT. So SCS needs a mean to distinguish both,
    // so another name : customerInIn for @INPUT, customerIn for @OUTPUT.
    String CUSTOMER_IN = "customerInIn";

    @Input(CUSTOMER_IN)
    SubscribableChannel customerIn();

    @Output
    MessageChannel customerOut();
}