package com.heartbloom.be.app.api.bouquet.request;

import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;

public record CreateBouquetAnswerRequest (
        Long questionId,
        BouquetAnswerType answerType,
        String answer,
        Integer sortOrder

) {}