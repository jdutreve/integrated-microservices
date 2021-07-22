package com.example.demo.scoring.backend.domain;

import com.example.demo.scoring.backend.event.ComputeScoreCommand;

public interface AsyncScoringInterface {

    /** Async callbacks */
    void onComputeScoreCommand(ComputeScoreCommand command);

}