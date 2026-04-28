package com.heartbloom.be.infra.repository.question;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.QuestionOption;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import com.heartbloom.be.infra.dao.jpa.question.QuestionJpaDao;
import com.heartbloom.be.infra.dao.jpa.question.QuestionOptionJpaDao;
import com.heartbloom.be.infra.entity.converter.QuestionConverter;
import com.heartbloom.be.infra.entity.converter.QuestionOptionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaDao questionJpaDao;
    private final QuestionOptionJpaDao questionOptionJpaDao;

    @Override
    public List<Question> findAll() {
        return questionJpaDao.findAllOrderByIdAsc()
                .stream()
                .map(QuestionConverter::toModel)
                .toList();
    }

    @Override
    public List<Question> findByBouquetTypeId(Long bouquetTypeId) {
        return questionJpaDao.findByBouquetTypeIdOrderBySortOrder(bouquetTypeId)
                .stream()
                .map(QuestionConverter::toModel)
                .toList();
    }

    @Override
    public List<QuestionOption> findOptionsByQuestionIds(List<Long> questionIds) {
        if (questionIds.isEmpty()) {
            return Collections.emptyList();
        }

        return questionOptionJpaDao.findByQuestionIds(questionIds)
                .stream()
                .map(QuestionOptionConverter::toModel)
                .toList();
    }

}
