package com.heartbloom.be.app.api.question.response;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "랜딩 질문 목록 응답")
public record GetQuestionLandingResponse(
        @Schema(description = "질문 목록")
        List<QuestionSummary> questions
) {

    public static GetQuestionLandingResponse from(List<Question> questions) {
        return new GetQuestionLandingResponse(
                questions.stream()
                        .map(QuestionSummary::from)
                        .toList()
        );
    }

    @Schema(description = "질문 요약")
    public record QuestionSummary(
            @Schema(description = "질문 ID", example = "1")
            Long questionId,

            @Schema(description = "질문 제목", example = "처음 단둘이 있었던 때")
            String title,

            @Schema(description = "질문 필수/선택 여부", example = "REQUIRED")
            QuestionAnswerType answerType
    ) {

        public static QuestionSummary from(Question question) {
            return new QuestionSummary(
                    question.getId(),
                    question.getTitle(),
                    question.getAnswerType()
            );
        }
    }
}
