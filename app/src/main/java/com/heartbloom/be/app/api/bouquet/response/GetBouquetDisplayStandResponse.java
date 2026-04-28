package com.heartbloom.be.app.api.bouquet.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "꽃다발 진열대 응답")
public record GetBouquetDisplayStandResponse (
        @Schema(description = "사용자 이름", example = "하트")
        String senderName,

        @Schema(description = "보낸 꽃다발 목록")
        List<GetBouquetResponse> sentBouquets,

        @Schema(description = "받은 꽃다발 목록")
        List<GetBouquetResponse> receivedBouquets
) {}
