package com.example.demo.frontend.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal=false, level= AccessLevel.PRIVATE)
public class ScoreComputedEvent extends CloudEvent<ScoreComputedEvent.Payload> {

    public ScoreComputedEvent() {}

    public ScoreComputedEvent(String scoreUrl) {
        Payload payload = new Payload();
        payload.setScoreUrl(scoreUrl);
        this.setData(payload);
        setEntity("Score");
    }

    @lombok.Data
    public static class Payload extends Data {
        String scoreUrl;
    }
}