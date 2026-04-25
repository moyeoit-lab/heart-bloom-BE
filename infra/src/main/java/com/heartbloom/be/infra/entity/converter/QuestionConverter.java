package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import com.heartbloom.be.infra.entity.domain.question.QuestionEntity;

public class QuestionConverter {

    public static QuestionEntity toEntity(Question model) {
        return new QuestionEntity(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getAnswerType().name()
        );
    }

    public static Question toModel(QuestionEntity entity) {
        return new Question(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                QuestionAnswerType.findByCode(entity.getAnswerType()),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
