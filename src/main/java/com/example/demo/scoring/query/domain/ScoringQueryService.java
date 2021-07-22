package com.example.demo.scoring.query.domain;

import com.example.demo.scoring.backend.domain.Score;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
@Slf4j
@RequiredArgsConstructor
class ScoringQueryService implements ScoringQueryInterface {

	public Score getScore(String scoreId) {
		return new Score("MyScore", scoreId, 0);
	}
}