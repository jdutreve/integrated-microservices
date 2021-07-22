package com.example.demo.frontend.backend.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


@SuppressWarnings("ALL")
interface ScoringChannels {

    /**
     * WARNING : the Channel name is xxxIn because we want to produce Commands into the INBOUND Channel of the Scoring Service.
     * BUT we use @Output because we are SENDING Commands to the Scoring Service.
     */
    @Output
    MessageChannel scoringIn();
}