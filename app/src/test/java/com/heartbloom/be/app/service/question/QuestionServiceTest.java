package com.heartbloom.be.app.service.question;

import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetLinkManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetManager;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.RelationType;
import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.QuestionOption;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import com.heartbloom.be.core.repository.domain.question.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private BouquetLinkManager bouquetLinkManager;

    @Mock
    private BouquetManager bouquetManager;

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
        given(questionRepository.findOptionsByQuestionIds(List.of(1L, 2L))).willReturn(List.of());

        // when
        GetQuestionLandingResponse response = questionService.getLandingQuestions();

        // then
        assertThat(response.questions()).hasSize(2);
        assertThat(response.questions().get(0).answerType()).isEqualTo(QuestionAnswerType.REQUIRED);
        assertThat(response.questions().get(1).answerType()).isEqualTo(QuestionAnswerType.OPTIONAL);
    }

    @Test
    @DisplayName("부케 링크 토큰으로 부케 타입에 맞는 질문과 옵션을 조회한다")
    void getLandingQuestionsByLinkToken() {
        // given
        String token = "test-token";
        BouquetLink link = BouquetLink.builder()
                .id(1L)
                .bouquetId(10L)
                .linkToken(token)
                .status(BouquetLinkStatus.ACTIVE)
                .build();
        Bouquet bouquet = Bouquet.builder()
                .id(10L)
                .senderId(1L)
                .senderType(BouquetSenderType.USER)
                .receiverType(BouquetReceiverType.GUEST)
                .displayName("Sender")
                .relationType(RelationType.ETC)
                .bouquetTypeId(2L)
                .build();
        Question question = Question.builder()
                .id(5L)
                .title("가장 기억에 남은 상대의 한마디")
                .answerType(QuestionAnswerType.REQUIRED)
                .build();
        QuestionOption option = QuestionOption.builder()
                .id(1L)
                .questionId(5L)
                .optionText("옵션")
                .sortOrder(1)
                .build();
        given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
        given(bouquetManager.findById(10L)).willReturn(Optional.of(bouquet));
        given(questionRepository.findByBouquetTypeId(2L)).willReturn(List.of(question));
        given(questionRepository.findOptionsByQuestionIds(List.of(5L))).willReturn(List.of(option));

        // when
        GetQuestionLandingResponse response = questionService.getLandingQuestions(token);

        // then
        assertThat(response.questions()).hasSize(1);
        assertThat(response.questions().get(0).questionId()).isEqualTo(5L);
        assertThat(response.questions().get(0).options()).hasSize(1);
        assertThat(response.questions().get(0).options().get(0).optionText()).isEqualTo("옵션");
    }

    @Test
    @DisplayName("로그인 사용자가 접근 가능한 부케 ID로 질문과 옵션을 조회한다")
    void getBouquetQuestions() {
        // given
        Long bouquetId = 10L;
        AuthenticateUser user = new AuthenticateUser(1L, "Sender", "sender@test.com", false);
        Bouquet bouquet = Bouquet.builder()
                .id(bouquetId)
                .senderId(1L)
                .senderType(BouquetSenderType.USER)
                .receiverType(BouquetReceiverType.GUEST)
                .displayName("Sender")
                .relationType(RelationType.ETC)
                .bouquetTypeId(2L)
                .build();
        Question question = Question.builder()
                .id(5L)
                .title("가장 기억에 남은 상대의 한마디")
                .answerType(QuestionAnswerType.REQUIRED)
                .build();
        given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
        given(questionRepository.findByBouquetTypeId(2L)).willReturn(List.of(question));
        given(questionRepository.findOptionsByQuestionIds(List.of(5L))).willReturn(List.of());

        // when
        GetQuestionLandingResponse response = questionService.getBouquetQuestions(bouquetId, user);

        // then
        assertThat(response.questions()).hasSize(1);
        assertThat(response.questions().get(0).questionId()).isEqualTo(5L);
        assertThat(response.questions().get(0).options()).isEmpty();
    }
}
