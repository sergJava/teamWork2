package org.skypro.teamWork2.model;

import java.util.UUID;

public record ProductRecommendation(
        String name,
        UUID id,
        String text
) {
}
