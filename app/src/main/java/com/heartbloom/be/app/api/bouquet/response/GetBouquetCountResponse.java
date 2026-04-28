package com.heartbloom.be.app.api.bouquet.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "꽃다발 개수 응답")
public record GetBouquetCountResponse(
        @Schema(description = "생성된 꽃다발 개수", example = "10")
        long count
) {}
