package com.example.demo.frontend.backend.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


@SuppressWarnings("ALL")
interface CustomerChannels {

    /**
     * WARNING : the Channel name is xxxIn because we want to produce Commands into the INBOUND Channel of the Customer Service.
     * BUT we use @Output because we are SENDING Commands to the Customer Service.
     */
    @Output
    MessageChannel customerIn();
}