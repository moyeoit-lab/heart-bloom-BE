package com.heartbloom.be.core.model.domain.answer;

import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class BouquetAnswer {

    private Long id;
    private Long bouquetId;
    private Long questionId;
    private BouquetAnswerType answerType;
    private BouquetAnswerRespondentType respondentType;
    private Long userId;
    private Long receiverId;
    private String subjectiveContent;
    private Long selectedOptionId;
    private Integer sortOrder;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BouquetAnswer createOfSender(Long bouquetId,
                                               Long questionId,
                                               BouquetAnswerType answerType,
                                               Long userId,
                                               String subjectiveContent,
                                               Long selectedOptionId,
                                               Integer sortOrder,
                                               LocalDateTime now) {
        return new BouquetAnswer(
                null,
                bouquetId,
                questionId,
                answerType,
                BouquetAnswerRespondentType.SENDER,
                userId,
                null,
                subjectiveContent,
                selectedOptionId,
                sortOrder,
                now,
                now
        );
    }

    public static BouquetAnswer create(Long bouquetId,
                                Long questionId,
                                BouquetAnswerType answerType,
                                BouquetAnswerRespondentType respondentType,
                                Long userId,
                                Long receiverId,
                                String subjectiveContent,
                                Long selectedOptionId,
                                Integer sortOrder,
                                LocalDateTime now) {
        return new BouquetAnswer(
                null,
                bouquetId,
                questionId,
                answerType,
                respondentType,
                userId,
                receiverId,
                subjectiveContent,
                selectedOptionId,
                sortOrder,
                now,
                now
        );

    }

}
