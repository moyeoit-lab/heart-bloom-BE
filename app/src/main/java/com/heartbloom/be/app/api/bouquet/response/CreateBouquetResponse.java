package com.heartbloom.be.app.api.bouquet.response;

public record CreateBouquetResponse (
        Long bouquetId,
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