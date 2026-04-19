package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.question.QuestionOption;
import com.heartbloom.be.infra.entity.domain.question.QuestionOptionEntity;

public class QuestionOptionConverter {

    public static QuestionOptionEntity toEntity(QuestionOption model) {
        return new QuestionOptionEntity(
                model.getId(),
                model.getQuestionId(),
                model.getOptionText(),
                model.getSortOrder()
        );
    }

    public static QuestionOption toModel(QuestionOptionEntity entity) {
        return new QuestionOption(
                entity.getId(),
                entity.getQuestionId(),
                entity.getOptionText(),
                entity.getSortOrder(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
