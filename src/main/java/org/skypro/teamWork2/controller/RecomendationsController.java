package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.service.StaticRulesService;
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
    private final StaticRulesService staticRulesService;

    public RecomendationsController(StaticRulesService staticRulesService) {
        this.staticRulesService = staticRulesService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RecommendationResponse> getRecommendations(@PathVariable UUID userId){
        return ResponseEntity.ok(staticRulesService.getRecommendations(userId));
    }
}
