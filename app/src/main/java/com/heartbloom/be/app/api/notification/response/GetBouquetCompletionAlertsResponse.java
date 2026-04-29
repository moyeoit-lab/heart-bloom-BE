package com.heartbloom.be.app.api.notification.response;

import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;

import java.util.List;

public record GetBouquetCompletionAlertsResponse(
        List<BouquetCompletionAlertResponse> alerts
) {

    public static GetBouquetCompletionAlertsResponse of(List<BouquetCompletionAlertQueryDto> alerts) {
        return new GetBouquetCompletionAlertsResponse(
                alerts.stream()
                        .map(BouquetCompletionAlertResponse::of)
                        .toList()
        );
    }

}
