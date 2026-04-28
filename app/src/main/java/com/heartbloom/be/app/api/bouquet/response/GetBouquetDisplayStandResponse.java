package com.heartbloom.be.app.api.bouquet.response;

import java.util.List;

/**
 * 꽃다발 진열대 Response
 */
public record GetBouquetDisplayStandResponse (
        String senderName,
        List<GetBouquetResponse> sentBouquets,
        List<GetBouquetResponse> receivedBouquets
) {}
