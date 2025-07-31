package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.service.RecommendationService;
import org.skypro.teamWork2.model.RecommendationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecomendationsController {
    private final RecommendationService recommendationService;

    public RecomendationsController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable UUID userId){
        return ResponseEntity.ok(recommendationService.getRecommendations(userId));
    }
}
