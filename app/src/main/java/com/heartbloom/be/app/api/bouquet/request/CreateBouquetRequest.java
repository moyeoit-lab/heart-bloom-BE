package com.heartbloom.be.app.api.bouquet.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "꽃다발 생성 요청")
public record CreateBouquetRequest (
        @Schema(description = "상대방에게 보여질 이름", example = "하트")
        String displayName, // 6자 이내

        @Schema(description = "상대방과의 관계", example = "친구")
        String relationName,

        @Schema(description = "꽃다발 타입 ID", example = "1")
        Long bouquetTypeId,

        @Schema(description = "발신자 답변 목록")
        List<CreateBouquetAnswerRequest> answers

) {}
