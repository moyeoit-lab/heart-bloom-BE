package com.heartbloom.be.app.api.bouquet.request;

import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "꽃다발 질문 답변 요청")
public record CreateBouquetAnswerRequest (
        @Schema(description = "질문 ID", example = "1")
        Long questionId,

        @Schema(description = "답변 타입", example = "SUBJECTIVE")
        BouquetAnswerType answerType,

        @Schema(description = "주관식 답변 내용", example = "처음 같이 산책했던 날이 기억나.")
        String answer,

        @Schema(description = "답변 정렬 순서", example = "1")
        Integer sortOrder

) {}
