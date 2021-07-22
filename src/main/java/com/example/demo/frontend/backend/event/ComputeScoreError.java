package com.example.demo.frontend.backend.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class ComputeScoreError extends CloudEvent<ComputeScoreError.Payload> {

    public ComputeScoreError(String error, int code) {
        this.setData(new Payload(error, code));
        setEntity("Score");
    }

    @Value
    public static class Payload extends Data {
        String error;
        int code;
    }
}