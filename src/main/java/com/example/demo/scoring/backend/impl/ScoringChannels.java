package com.example.demo.scoring.backend.impl;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


@SuppressWarnings("ALL")
interface ScoringChannels {

    // We name channel "scoringInIn" because in case of single process deployment (dev, DemoApplication),
    // Topic/Queue scoringIn channel is both @INPUT and @OUTPUT. So SCS needs a mean to distinguish both,
    // so another name : scoringInIn for @INPUT, scoringIn for @OUTPUT.
    String SCORING_IN = "scoringInIn";

    @Input(SCORING_IN)
    SubscribableChannel scoringIn();

    @Output
    MessageChannel scoringOut();
}