package com.heartbloom.be.core.repository.domain.notification.dto;

import java.time.LocalDateTime;

public record BouquetCompletionAlertQueryDto(
        Long alertId,
        Long bouquetId,
        String displayName,
        String bouquetImageUrl,
        LocalDateTime completedAt
) {
}
