package org.skypro.teamWork2.controller;

import org.skypro.teamWork2.model.DynamicRecommendationRule;
import org.skypro.teamWork2.model.DynamicRecommendationRuleIn;
import org.skypro.teamWork2.model.ResponseWrapper;
import org.skypro.teamWork2.service.DynamicRulesService;
import org.skypro.teamWork2.service.StaticRulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRecommendationRuleController {
    private final DynamicRulesService dynamicRulesService;
    private final StaticRulesService staticRulesService;

    public DynamicRecommendationRuleController(DynamicRulesService dynamicRulesService, StaticRulesService staticRulesService) {
        this.dynamicRulesService = dynamicRulesService;
        this.staticRulesService = staticRulesService;
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
    public ResponseEntity<Object> deleteRuleByProductId(@PathVariable UUID productId) {
        dynamicRulesService.deleteRuleByProductId(productId);
        return ResponseEntity.noContent().build();
    }

}
