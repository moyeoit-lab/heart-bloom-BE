package com.heartbloom.be.app.service.question;

import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public GetQuestionLandingResponse getLandingQuestions() {
        List<Question> questions = questionRepository.findAll();
        return GetQuestionLandingResponse.from(questions);
    }

}
