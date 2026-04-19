package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;
import com.heartbloom.be.infra.entity.domain.answer.BouquetAnswerEntity;

public class BouquetAnswerConverter {

    public static BouquetAnswerEntity toEntity(BouquetAnswer model) {
        return new BouquetAnswerEntity(
                model.getId(),
                model.getBouquetId(),
                model.getQuestionId(),
                model.getAnswerType().name(),
                model.getRespondentType().name(),
                model.getUserId(),
                model.getReceiverId(),
                model.getSubjectiveContent(),
                model.getSelectedOptionId()
        );
    }

    public static BouquetAnswer toModel(BouquetAnswerEntity entity) {
        return new BouquetAnswer(
                entity.getId(),
                entity.getBouquetId(),
                entity.getQuestionId(),
                BouquetAnswerType.findByCode(entity.getAnswerType()),
                BouquetAnswerRespondentType.findByCode(entity.getRespondentType()),
                entity.getUserId(),
                entity.getReceiverId(),
                entity.getSubjectiveContent(),
                entity.getSelectedOptionId(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
