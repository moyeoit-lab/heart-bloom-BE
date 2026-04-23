package com.heartbloom.be.core.repository.domain.bouquet.dto;

public record GetBouquetQueryDto (
        Long bouquetId,
        Long userId,
        Long bouquetTypeId,
        String bouquetName,
        String bouquetDescription,
        String bouquetImageUrl
) {}