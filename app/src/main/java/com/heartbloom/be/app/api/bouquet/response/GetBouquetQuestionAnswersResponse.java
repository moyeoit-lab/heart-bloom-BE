package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "질문별 발신자/수신자 답변 응답")
public record GetBouquetQuestionAnswersResponse(
        @Schema(description = "질문 ID", example = "1")
        Long questionId,

        @Schema(description = "발신자 답변")
        BouquetAnswerResponse senderAnswer,

        @Schema(description = "수신자 답변")
        BouquetAnswerResponse receiverAnswer
) {
    public static GetBouquetQuestionAnswersResponse of(Long questionId, List<BouquetAnswer> answers) {
        return new GetBouquetQuestionAnswersResponse(
                questionId,
                findAnswer(answers, BouquetAnswerRespondentType.SENDER),
                findAnswer(answers, BouquetAnswerRespondentType.RECEIVER)
        );
    }

    private static BouquetAnswerResponse findAnswer(List<BouquetAnswer> answers, BouquetAnswerRespondentType respondentType) {
        return answers.stream()
                .filter(answer -> answer.getRespondentType() == respondentType)
                .findFirst()
                .map(BouquetAnswerResponse::from)
                .orElse(null);
    }

    @Schema(description = "꽃다발 답변 응답")
    public record BouquetAnswerResponse(
            @Schema(description = "답변 ID", example = "1")
            Long answerId,

            @Schema(description = "주관식 답변 내용")
            String subjectiveContent,

            @Schema(description = "선택한 객관식 옵션 ID", example = "1")
            Long selectedOptionId,

            @Schema(description = "답변 정렬 순서", example = "1")
            Integer sortOrder
    ) {
        public static BouquetAnswerResponse from(BouquetAnswer answer) {
            return new BouquetAnswerResponse(
                    answer.getId(),
                    answer.getSubjectiveContent(),
                    answer.getSelectedOptionId(),
                    answer.getSortOrder()
            );
        }
    }
}
