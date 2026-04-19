package com.heartbloom.be.core.model.domain.answer.enumerate;

import lombok.Getter;

@Getter
public enum BouquetAnswerRespondentType {

    SENDER("sender", "송신자"),
    RECEIVER("receiver", "수신자");

    private final String serializedValue;
    private final String description;

    BouquetAnswerRespondentType(String serializedValue,
                                String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

}