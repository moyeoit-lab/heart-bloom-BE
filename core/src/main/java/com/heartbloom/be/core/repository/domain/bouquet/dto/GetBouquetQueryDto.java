package com.heartbloom.be.core.repository.domain.bouquet.dto;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;

public record GetBouquetQueryDto (
        Long bouquetId,
        Long senderId,
        BouquetSenderType senderType,
        Long receiverId,
        BouquetReceiverType receiverType,
        Long bouquetTypeId,
        String bouquetName,
        String bouquetDescription,
        String bouquetImageUrl
) {}
