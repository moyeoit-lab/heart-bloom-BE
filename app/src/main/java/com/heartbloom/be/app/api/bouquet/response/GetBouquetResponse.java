package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;

public record GetBouquetResponse (

        Long bouquetId,
        Long senderId,
        BouquetSenderType senderType,
        Long receiverId,
        BouquetReceiverType receiverType,
        Long bouquetTypeId,
        String bouquetName,
        String bouquetDescription,
        String bouquetImageUrl

) {

    public static GetBouquetResponse of(GetBouquetQueryDto queryDto) {
        return new GetBouquetResponse(
                queryDto.bouquetId(),
                queryDto.senderId(),
                queryDto.senderType(),
                queryDto.receiverId(),
                queryDto.receiverType(),
                queryDto.bouquetTypeId(),
                queryDto.bouquetName(),
                queryDto.bouquetDescription(),
                queryDto.bouquetImageUrl()
        );
    }

}
