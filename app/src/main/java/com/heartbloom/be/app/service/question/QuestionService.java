package com.heartbloom.be.app.service.question;

import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetLinkManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetManager;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.QuestionOption;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final BouquetLinkManager bouquetLinkManager;
    private final BouquetManager bouquetManager;

    @Transactional(readOnly = true)
    public GetQuestionLandingResponse getLandingQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionOption> options = findOptions(questions);
        return GetQuestionLandingResponse.from(questions, options);
    }

    @Transactional(readOnly = true)
    public GetQuestionLandingResponse getLandingQuestions(String linkToken) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));

        if (link.getStatus() == BouquetLinkStatus.EXPIRED) {
            throw new ServiceException(BouquetErrorCode.LINK_EXPIRED);
        }

        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));

        List<Question> questions = questionRepository.findByBouquetTypeId(bouquet.getBouquetTypeId());
        List<QuestionOption> options = findOptions(questions);
        return GetQuestionLandingResponse.from(questions, options);
    }

    private List<QuestionOption> findOptions(List<Question> questions) {
        List<Long> questionIds = questions.stream()
                .map(Question::getId)
                .toList();
        return questionRepository.findOptionsByQuestionIds(questionIds);
    }

}
