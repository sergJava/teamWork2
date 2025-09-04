package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.model.ServiceInfo;
import org.skypro.teamWork2.repository.RecommendationsRepository;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final RecommendationsRepository repository;
    private final BuildProperties buildProperties;

    public ManagementController(RecommendationsRepository repository, BuildProperties buildProperties) {
        this.repository = repository;
        this.buildProperties = buildProperties;
    }


    @PostMapping("/clear-caches")
    public ResponseEntity<String> clearCashes() {
        repository.invalidateAllCaches();
        return ResponseEntity.ok("кэш очищен");
    }

    @GetMapping("/info")
    public ServiceInfo getServiceInfo() {
        return new ServiceInfo(
                buildProperties.getName(),
                buildProperties.getVersion()
        );
    }
}
