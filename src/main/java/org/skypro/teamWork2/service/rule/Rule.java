package org.skypro.teamWork2.service.rule;

import org.skypro.teamWork2.repository.RecommendationsRepository;

import java.util.UUID;

public interface Rule {
    boolean evaluate(UUID userId, RecommendationsRepository repository);
}
