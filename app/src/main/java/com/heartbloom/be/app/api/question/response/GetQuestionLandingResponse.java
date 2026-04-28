package com.heartbloom.be.app.api.question.response;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.QuestionOption;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Schema(description = "질문 목록 응답")
public record GetQuestionLandingResponse(
        @Schema(description = "꽃다발 타입에 맞게 정렬된 질문 목록")
        List<QuestionSummary> questions
) {

    public static GetQuestionLandingResponse from(List<Question> questions) {
        return from(questions, List.of());
    }

    public static GetQuestionLandingResponse from(List<Question> questions,
                                                  List<QuestionOption> options) {
        Map<Long, List<QuestionOption>> optionsByQuestionId = options.stream()
                .collect(Collectors.groupingBy(QuestionOption::getQuestionId));

        return new GetQuestionLandingResponse(
                questions.stream()
                        .map(question -> QuestionSummary.from(
                                question,
                                optionsByQuestionId.getOrDefault(question.getId(), List.of())
                        ))
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
            QuestionAnswerType answerType,

            @Schema(description = "질문 옵션 목록. 옵션이 없는 질문은 빈 배열로 반환됩니다.")
            List<QuestionOptionSummary> options
    ) {

        public static QuestionSummary from(Question question) {
            return from(question, List.of());
        }

        public static QuestionSummary from(Question question,
                                           List<QuestionOption> options) {
            return new QuestionSummary(
                    question.getId(),
                    question.getTitle(),
                    question.getAnswerType(),
                    options.stream()
                            .map(QuestionOptionSummary::from)
                            .toList()
            );
        }
    }

    @Schema(description = "질문 옵션 요약")
    public record QuestionOptionSummary(
            @Schema(description = "질문 옵션 ID", example = "1")
            Long optionId,

            @Schema(description = "옵션 텍스트", example = "매우 그렇다")
            String optionText,

            @Schema(description = "옵션 정렬 순서", example = "1")
            Integer sortOrder
    ) {

        public static QuestionOptionSummary from(QuestionOption option) {
            return new QuestionOptionSummary(
                    option.getId(),
                    option.getOptionText(),
                    option.getSortOrder()
            );
        }
    }
}
