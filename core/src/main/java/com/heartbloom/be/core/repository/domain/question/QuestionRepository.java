package com.heartbloom.be.core.repository.domain.question;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.QuestionOption;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAll();

    List<Question> findByBouquetTypeId(Long bouquetTypeId);

    List<QuestionOption> findOptionsByQuestionIds(List<Long> questionIds);

}
