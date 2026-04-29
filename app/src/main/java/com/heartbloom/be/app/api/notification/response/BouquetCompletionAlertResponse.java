package com.heartbloom.be.app.api.notification.response;

import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;

import java.time.LocalDateTime;

public record BouquetCompletionAlertResponse(
        Long alertId,
        Long bouquetId,
        String displayName,
        String bouquetImageUrl,
        LocalDateTime completedAt
) {

    public static BouquetCompletionAlertResponse of(BouquetCompletionAlertQueryDto queryDto) {
        return new BouquetCompletionAlertResponse(
                queryDto.alertId(),
                queryDto.bouquetId(),
                queryDto.displayName(),
                queryDto.bouquetImageUrl(),
                queryDto.completedAt()
        );
    }

}
