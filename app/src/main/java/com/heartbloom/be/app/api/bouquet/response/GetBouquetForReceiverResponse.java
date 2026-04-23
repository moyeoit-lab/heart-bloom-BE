package com.heartbloom.be.app.api.bouquet.response;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;

import java.util.List;

public record GetBouquetForReceiverResponse(
        String senderName,
        String bouquetName,
        String bouquetImageUrl,
        List<BouquetAnswerResponse> senderAnswers
) {
    public static GetBouquetForReceiverResponse of(String senderName, BouquetType bouquetType, List<BouquetAnswer> senderAnswers) {
        return new GetBouquetForReceiverResponse(
                senderName,
                bouquetType.getBouquetName(),
                bouquetType.getBouquetImageUrl(),
                senderAnswers.stream()
                        .map(BouquetAnswerResponse::from)
                        .toList()
        );
    }

    public record BouquetAnswerResponse(
            Long questionId,
            String subjectiveContent,
            Long selectedOptionId,
            Integer sortOrder
    ) {
        public static BouquetAnswerResponse from(BouquetAnswer answer) {
            return new BouquetAnswerResponse(
                    answer.getQuestionId(),
                    answer.getSubjectiveContent(),
                    answer.getSelectedOptionId(),
                    answer.getSortOrder()
            );
        }
    }
}
