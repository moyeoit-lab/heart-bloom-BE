package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetTypeRequest;

public record CreateBouquetTypeResponse (
        Long id,
        String bouquetTypeName,
        String bouquetTypeDescription,
        String bouquetTypeImageUrl,
        Boolean active
) {

    public static CreateBouquetTypeResponse of(Long id, CreateBouquetTypeRequest request) {
        return new CreateBouquetTypeResponse(
                id,
                request.bouquetName(),
                request.bouquetDescription(),
                request.bouquetImageUrl(),
                request.active()
        );
    }

}