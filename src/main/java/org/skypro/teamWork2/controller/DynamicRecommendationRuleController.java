package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.model.DynamicRecommendationRule;
import org.skypro.teamWork2.model.DynamicRecommendationRuleIn;
import org.skypro.teamWork2.model.ResponseWrapper;
import org.skypro.teamWork2.service.DynamicRulesService;
import org.skypro.teamWork2.service.StatsRulesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRecommendationRuleController {
    private final DynamicRulesService dynamicRulesService;
    private final StatsRulesService statsRulesService;

    public DynamicRecommendationRuleController(DynamicRulesService dynamicRulesService, StatsRulesService statsRulesService) {
        this.dynamicRulesService = dynamicRulesService;
        this.statsRulesService = statsRulesService;
    }

    @PostMapping
    public DynamicRecommendationRule createRule(@RequestBody DynamicRecommendationRuleIn ruleIn) {
        return dynamicRulesService.createRule(ruleIn);
    }

    @GetMapping
    public ResponseWrapper getRules() {
        return new ResponseWrapper(dynamicRulesService.getAllRules());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteRule(@PathVariable UUID productId) {
        dynamicRulesService.deleteRule(productId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/stats")
//    public ResponseWrapper getRulesStats() {
//        return new ResponseWrapper(statsRulesService.)
//    }
}
