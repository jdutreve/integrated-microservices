package com.example.demo.scoring.proxy.event;

import com.example.demo.common.CloudEvent;
import com.example.demo.common.Data;
import lombok.Value;

public class ComputeScoreCommand extends CloudEvent<ComputeScoreCommand.Payload> {

    public ComputeScoreCommand(String name, int value) {
        this.setData(new Payload(name, value));
    }

    @Value
    public static class Payload extends Data {
        String name;
        int value;
    }
}
