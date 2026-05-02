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

        @Schema(description = "꽃다발 표시 이름", example = "하트")
        String displayName,

        @Schema(description = "꽃다발 타입 ID", example = "1")
        Long bouquetTypeId,

        @Schema(description = "꽃다발 이름", example = "장미 꽃다발")
        String bouquetName,

        @Schema(description = "꽃다발 설명")
        String bouquetDescription,

        @Schema(description = "꽃다발 이미지 URL")
        String bouquetImageUrl,

        @Schema(description = "현재 조회자 답변 완료 여부", example = "true")
        Boolean myAnswered,

        @Schema(description = "상대방 답변 완료 여부", example = "false")
        Boolean counterpartAnswered,

        @Schema(description = "현재 조회자 기준 답변 상태", example = "WAITING_FOR_COUNTERPART_ANSWER")
        BouquetAnswerStatus answerStatus

) {

    public static GetBouquetResponse of(GetBouquetQueryDto queryDto) {
        boolean myAnswered = Boolean.TRUE.equals(queryDto.myAnswered());
        boolean counterpartAnswered = Boolean.TRUE.equals(queryDto.counterpartAnswered());

        return new GetBouquetResponse(
                queryDto.bouquetId(),
                queryDto.senderId(),
                queryDto.senderType(),
                queryDto.receiverId(),
                queryDto.receiverType(),
                queryDto.displayName(),
                queryDto.bouquetTypeId(),
                queryDto.bouquetName(),
                queryDto.bouquetDescription(),
                queryDto.bouquetImageUrl(),
                myAnswered,
                counterpartAnswered,
                BouquetAnswerStatus.of(myAnswered, counterpartAnswered)
        );
    }

}
