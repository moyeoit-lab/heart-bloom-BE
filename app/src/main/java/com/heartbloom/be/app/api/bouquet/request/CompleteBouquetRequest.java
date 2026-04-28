package com.heartbloom.be.app.api.bouquet.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "수신자 꽃다발 완료 요청")
public record CompleteBouquetRequest(
        @Schema(description = "수신자 이름", example = "하트")
        String receiverName,

        @Schema(description = "수신자 답변 목록")
        List<CreateBouquetAnswerRequest> answers
) {}
