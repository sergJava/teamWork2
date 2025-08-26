package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final RecommendationsRepository repository;

    public ManagementController(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/clear-caches")
    public ResponseEntity<String> clearCashes(){
        repository.invalidateAllCaches();
        return ResponseEntity.ok("кэш очищен");
    }

}
