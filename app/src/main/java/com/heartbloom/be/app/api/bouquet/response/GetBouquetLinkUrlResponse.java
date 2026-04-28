package com.heartbloom.be.app.api.bouquet.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "꽃다발 링크 URL 응답")
public record GetBouquetLinkUrlResponse (
        @Schema(description = "꽃다발 공유 링크 URL")
        String url
) {}
