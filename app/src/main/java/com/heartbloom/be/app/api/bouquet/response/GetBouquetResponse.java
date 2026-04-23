package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;

public record GetBouquetResponse (

        Long bouquetId,
        Long userId,
        Long bouquetTypeId,
        String bouquetName,
        String bouquetDescription,
        String bouquetImageUrl

) {

    public static GetBouquetResponse of(GetBouquetQueryDto queryDto) {
        return new GetBouquetResponse(
                queryDto.bouquetId(),
                queryDto.userId(),
                queryDto.bouquetTypeId(),
                queryDto.bouquetName(),
                queryDto.bouquetDescription(),
                queryDto.bouquetImageUrl()
        );
    }

}