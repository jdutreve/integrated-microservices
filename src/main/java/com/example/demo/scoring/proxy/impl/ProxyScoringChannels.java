package com.example.demo.scoring.proxy.impl;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;


@SuppressWarnings("ALL")
public interface ProxyScoringChannels {

    @Output
    MessageChannel scoringIn();
}