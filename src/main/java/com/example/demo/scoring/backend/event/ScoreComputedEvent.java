package com.example.demo.scoring.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class ScoreComputedEvent extends CloudEvent<ScoreComputedEvent.Payload> {

    public ScoreComputedEvent(String scoreUrl) {
        this.setData(new Payload(scoreUrl));
        setEntity("Score");
    }

    @Value
    public static class Payload extends Data {
        String scoreUrl;
    }
}