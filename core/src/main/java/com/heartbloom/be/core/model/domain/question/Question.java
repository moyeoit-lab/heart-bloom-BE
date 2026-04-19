package com.heartbloom.be.core.model.domain.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Question {

    private Long id;
    private String title;
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Question create(String title,
                                  String description,
                                  LocalDateTime now) {
        return new Question(
                null,
                title,
                description,
                now,
                now
        );

    }

}