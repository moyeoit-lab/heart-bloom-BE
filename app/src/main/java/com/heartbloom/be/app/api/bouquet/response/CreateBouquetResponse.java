package com.heartbloom.be.app.api.bouquet.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "꽃다발 생성 응답")
public record CreateBouquetResponse (
        @Schema(description = "생성된 꽃다발 ID", example = "1")
        Long bouquetId,

        @Schema(description = "꽃다발 공유 링크 토큰")
        String linkToken
) {

    public static CreateBouquetResponse of(Long bouquetId,
                                           String linkToken) {
        return new CreateBouquetResponse(
                bouquetId,
                linkToken
        );
    }

}
