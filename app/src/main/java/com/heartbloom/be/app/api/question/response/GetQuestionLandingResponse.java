package com.heartbloom.be.app.api.question.response;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;

import java.util.List;

public record GetQuestionLandingResponse(
        List<QuestionSummary> questions
) {

    public static GetQuestionLandingResponse from(List<Question> questions) {
        return new GetQuestionLandingResponse(
                questions.stream()
                        .map(QuestionSummary::from)
                        .toList()
        );
    }

    public record QuestionSummary(
            Long questionId,
            String title,
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
