package com.example.demo.scoring.proxy.domain;

import com.example.demo.scoring.proxy.event.ComputeScoreCommand;

public interface ProxyScoringGateway {

    /** Called from REST Controller */
    void sendComputeScoreCommand(ComputeScoreCommand command);
}