package com.example.demo.frontend.backend.impl;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


@SuppressWarnings("ALL")
public interface FrontendChannels {

    String FRONTEND_IN = "frontendIn";

    @Input
    SubscribableChannel frontendIn();

    @Output
    MessageChannel frontendOut();
}