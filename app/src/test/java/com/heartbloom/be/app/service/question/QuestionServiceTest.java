package com.heartbloom.be.app.service.question;

import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("질문 목록을 응답 형식으로 변환한다")
    void getLandingQuestions() {
        // given
        Question requiredQuestion = Question.builder()
                .id(1L)
                .title("필수 질문")
                .answerType(QuestionAnswerType.REQUIRED)
                .build();
        Question optionalQuestion = Question.builder()
                .id(2L)
                .title("선택 질문")
                .answerType(QuestionAnswerType.OPTIONAL)
                .build();
        given(questionRepository.findAll()).willReturn(List.of(requiredQuestion, optionalQuestion));

        // when
        GetQuestionLandingResponse response = questionService.getLandingQuestions();

        // then
        assertThat(response.questions()).hasSize(2);
        assertThat(response.questions().get(0).answerType()).isEqualTo(QuestionAnswerType.REQUIRED);
        assertThat(response.questions().get(1).answerType()).isEqualTo(QuestionAnswerType.OPTIONAL);
    }
}
