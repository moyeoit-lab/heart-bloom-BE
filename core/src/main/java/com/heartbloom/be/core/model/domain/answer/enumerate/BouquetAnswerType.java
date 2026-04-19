package com.heartbloom.be.core.model.domain.answer.enumerate;

import lombok.Getter;

@Getter
public enum BouquetAnswerType {

    SUBJECTIVE("subjective", "주관식"),
    MULTIPLE_CHOICE("multipleChoice", "객관식")
    ;

    private final String serializedValue;
    private final String description;

    BouquetAnswerType (String serializedValue,
                       String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

}