package com.example.demo.scoring.query.domain;

import com.example.demo.scoring.backend.domain.Score;

public interface ScoringQueryInterface {

    /** Sync Query */
    Score getScore(String scoreId);
}