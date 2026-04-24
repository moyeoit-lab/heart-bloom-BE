package com.heartbloom.be.core.model.domain.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuestionOption {

    private Long id;
    private Long questionId;
    private String optionText;
    private Integer sortOrder;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static QuestionOption create(Long questionId,
                                        String optionText,
                                        Integer sortOrder,
                                        LocalDateTime now) {
        return new QuestionOption(
                null,
                questionId,
                optionText,
                sortOrder,
                now,
                now
        );
    }


}
