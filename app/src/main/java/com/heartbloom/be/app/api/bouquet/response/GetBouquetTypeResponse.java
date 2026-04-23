package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;

import java.time.LocalDateTime;

public record GetBouquetTypeResponse (
        Long id,
        String bouquetName,
        String bouquetDescription,
        String bouquetImageUrl,
        boolean active,
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