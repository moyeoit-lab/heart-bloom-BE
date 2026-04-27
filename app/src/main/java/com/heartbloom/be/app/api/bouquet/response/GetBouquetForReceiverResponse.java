package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;

public record GetBouquetForReceiverResponse(
        String senderName,
        String bouquetName,
        String bouquetImageUrl
) {
    public static GetBouquetForReceiverResponse of(String senderName, BouquetType bouquetType) {
        return new GetBouquetForReceiverResponse(
                senderName,
                bouquetType.getBouquetName(),
                bouquetType.getBouquetImageUrl()
        );
    }
}
