package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "꽃다발 타입 응답")
public record GetBouquetTypeResponse (
        @Schema(description = "꽃다발 타입 ID", example = "1")
        Long id,

        @Schema(description = "꽃다발 이름", example = "장미 꽃다발")
        String bouquetName,

        @Schema(description = "꽃다발 설명")
        String bouquetDescription,

        @Schema(description = "꽃다발 이미지 URL")
        String bouquetImageUrl,

        @Schema(description = "활성화 여부", example = "true")
        boolean active,

        @Schema(description = "생성 일시")
        LocalDateTime createdAt
) {

    public static GetBouquetTypeResponse of(BouquetType type) {
        return new GetBouquetTypeResponse(
                type.getId(),
                type.getBouquetName(),
                type.getBouquetDescription(),
                type.getBouquetImageUrl(),
                type.isActive(),
                type.getCreatedAt()
        );
    }

}
