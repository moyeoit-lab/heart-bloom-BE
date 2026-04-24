package com.heartbloom.be.infra.repository.question;

import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import com.heartbloom.be.infra.dao.jpa.question.QuestionJpaDao;
import com.heartbloom.be.infra.entity.converter.QuestionConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {

    private final QuestionJpaDao questionJpaDao;

    @Override
    public List<Question> findAll() {
        return questionJpaDao.findAllOrderByIdAsc()
                .stream()
                .map(QuestionConverter::toModel)
                .toList();
    }

}
