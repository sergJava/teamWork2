package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RecomendationsController {
    private final RecommendationsRepository recommendationsRepository;

    public RecomendationsController(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getRandomTransactionAmount(@PathVariable UUID id){
        return ResponseEntity.ok(recommendationsRepository.getRandomTransactionAmount(id));
    }
}
