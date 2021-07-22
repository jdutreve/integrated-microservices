package com.example.demo.client.impl;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


@SuppressWarnings("ALL")
public interface ClientChannels {

    String CLIENT_IN = "clientIn";

    @Input
    SubscribableChannel clientIn();

    @Output
    MessageChannel clientOut();
}