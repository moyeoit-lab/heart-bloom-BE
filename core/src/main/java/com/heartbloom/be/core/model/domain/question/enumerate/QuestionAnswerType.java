package com.heartbloom.be.core.model.domain.question.enumerate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum QuestionAnswerType {

    REQUIRED("required", "필수"),
    OPTIONAL("optional", "선택")
    ;

    private final String serializedValue;
    private final String description;

    QuestionAnswerType(String serializedValue,
                       String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

    public static QuestionAnswerType findByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown QuestionAnswerType: " + code));
    }

}
