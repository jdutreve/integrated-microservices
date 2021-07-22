package com.example.demo.scoring.query.rest;

import com.example.demo.scoring.backend.domain.Score;
import com.example.demo.scoring.query.domain.ScoringQueryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
class ScoringQueryController {

	@Autowired
	private ScoringQueryInterface scoringService;

	@GetMapping("/{id}")
	public Score index(@PathVariable String id) {
		return scoringService.getScore(id);
	}
}