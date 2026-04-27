package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;

import java.util.List;

public record GetBouquetQuestionAnswersResponse(
        Long questionId,
        BouquetAnswerResponse senderAnswer,
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

    public record BouquetAnswerResponse(
            Long answerId,
            String subjectiveContent,
            Long selectedOptionId,
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
