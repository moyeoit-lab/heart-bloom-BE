package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "수신자용 꽃다발 정보 응답")
public record GetBouquetForReceiverResponse(
        @Schema(description = "발신자 이름", example = "하트")
        String senderName,

        @Schema(description = "꽃다발 이름", example = "장미 꽃다발")
        String bouquetName,

        @Schema(description = "꽃다발 이미지 URL")
        String bouquetImageUrl,

        @Schema(description = "답변 완료 여부")
        boolean isCompleted
) {
    public static GetBouquetForReceiverResponse of(String senderName, BouquetType bouquetType, boolean isCompleted) {
        return new GetBouquetForReceiverResponse(
                senderName,
                bouquetType.getBouquetName(),
                bouquetType.getBouquetImageUrl(),
                isCompleted
        );
    }
}