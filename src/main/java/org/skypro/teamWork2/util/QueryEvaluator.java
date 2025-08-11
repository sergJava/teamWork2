package org.skypro.teamWork2.util;

import org.skypro.teamWork2.repository.RecommendationsRepository;

import java.util.UUID;

public interface QueryEvaluator {
    boolean evaluate(UUID userId, RecommendationsRepository repository);
}
