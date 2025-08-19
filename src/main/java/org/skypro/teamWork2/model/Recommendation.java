package org.skypro.teamWork2.model;

import java.util.UUID;

public record Recommendation(
        String name,
        UUID id,
        String text
) {
}
