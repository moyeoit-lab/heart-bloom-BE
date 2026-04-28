package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "꽃다발 요약 응답")
public record GetBouquetResponse (

        @Schema(description = "꽃다발 ID", example = "1")
        Long bouquetId,

        @Schema(description = "발신자 ID", example = "1")
        Long senderId,

        @Schema(description = "발신자 타입", example = "USER")
        BouquetSenderType senderType,

        @Schema(description = "수신자 ID", example = "1")
        Long receiverId,

        @Schema(description = "수신자 타입", example = "GUEST")
        BouquetReceiverType receiverType,

        @Schema(description = "꽃다발 타입 ID", example = "1")
        Long bouquetTypeId,

        @Schema(description = "꽃다발 이름", example = "장미 꽃다발")
        String bouquetName,

        @Schema(description = "꽃다발 설명")
        String bouquetDescription,

        @Schema(description = "꽃다발 이미지 URL")
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
