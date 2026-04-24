package com.heartbloom.be.core.repository.domain.question;

import com.heartbloom.be.core.model.domain.question.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAll();

}
