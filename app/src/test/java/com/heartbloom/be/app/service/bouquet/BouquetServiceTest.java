package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetQuestionAnswersResponse;
import com.heartbloom.be.app.application.bouquet.implementation.*;
import com.heartbloom.be.app.application.notification.implementation.NotificationManager;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.app.security.access.AnonymousUser;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BouquetServiceTest {

    @InjectMocks
    private BouquetService bouquetService;

    @Mock
    private TimeProvider timeProvider;
    @Mock
    private BouquetManager bouquetManager;
    @Mock
    private BouquetAnswerManager bouquetAnswerManager;
    @Mock
    private BouquetLinkManager bouquetLinkManager;
    @Mock
    private BouquetReceiverManager bouquetReceiverManager;
    @Mock
    private BouquetTypeManager bouquetTypeManager;
    @Mock
    private NotificationManager notificationManager;
    @Mock
    private BouquetRepository bouquetRepository;

    @Nested
    @DisplayName("getBouquetForReceiver 테스트")
    class GetBouquetForReceiver {

        @Test
        @DisplayName("유효한 토큰으로 조회 시 부케 정보만 반환한다")
        void success() {
            // given
            String token = "valid-token";
            Long bouquetId = 1L;
            Long bouquetTypeId = 2L;
            Long senderId = 3L;
            LocalDateTime now = LocalDateTime.now();

            BouquetLink link = BouquetLink.builder()
                    .id(1L).bouquetId(bouquetId).linkToken(token)
                    .status(BouquetLinkStatus.ACTIVE).expiredAt(now.plusDays(7))
                    .createdAt(now).modifiedAt(now).build();

            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId).senderId(senderId).senderType(BouquetSenderType.USER)
                    .receiverId(null).receiverType(BouquetReceiverType.GUEST)
                    .displayName("DisplayName")
                    .bouquetTypeId(bouquetTypeId).deleted(false)
                    .createdAt(now).modifiedAt(now).build();

            BouquetType type = BouquetType.builder()
                    .id(bouquetTypeId).bouquetName("Rose").bouquetDescription("Desc")
                    .bouquetImageUrl("url").active(true)
                    .createdAt(now).modifiedAt(now).build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetTypeManager.findById(bouquetTypeId)).willReturn(Optional.of(type));

            // when
            GetBouquetForReceiverResponse response = bouquetService.getBouquetForReceiver(token);

            // then
            assertThat(response.senderName()).isEqualTo("DisplayName");
            assertThat(response.bouquetName()).isEqualTo("Rose");
            verify(bouquetAnswerManager, never()).findByBouquetId(anyLong());
        }

        @Test
        @DisplayName("답례 부케의 발신자가 게스트여도 부케 표시 이름을 반환한다")
        void guestSender() {
            // given
            String token = "valid-token";
            Long bouquetId = 1L;
            Long senderReceiverId = 10L;
            Long bouquetTypeId = 2L;
            LocalDateTime now = LocalDateTime.now();

            BouquetLink link = BouquetLink.builder()
                    .id(1L).bouquetId(bouquetId).linkToken(token)
                    .status(BouquetLinkStatus.ACTIVE).expiredAt(now.plusDays(7))
                    .createdAt(now).modifiedAt(now).build();

            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId).senderId(senderReceiverId).senderType(BouquetSenderType.GUEST)
                    .receiverId(3L).receiverType(BouquetReceiverType.USER)
                    .displayName("DisplayName")
                    .bouquetTypeId(bouquetTypeId).deleted(false)
                    .createdAt(now).modifiedAt(now).build();

            BouquetType type = BouquetType.builder()
                    .id(bouquetTypeId).bouquetName("Rose").bouquetDescription("Desc")
                    .bouquetImageUrl("url").active(true)
                    .createdAt(now).modifiedAt(now).build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetTypeManager.findById(bouquetTypeId)).willReturn(Optional.of(type));
            // when
            GetBouquetForReceiverResponse response = bouquetService.getBouquetForReceiver(token);

            // then
            assertThat(response.senderName()).isEqualTo("DisplayName");
            verify(bouquetReceiverManager, never()).findById(senderReceiverId);
        }

        @Test
        @DisplayName("만료된 링크 토큰이면 예외를 던진다")
        void expiredLink() {
            // given
            String token = "expired-token";
            BouquetLink link = BouquetLink.builder().status(BouquetLinkStatus.EXPIRED).build();
            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));

            // when & then
            assertThatThrownBy(() -> bouquetService.getBouquetForReceiver(token))
                    .isInstanceOf(ServiceException.class)
                    .hasFieldOrPropertyWithValue("errorCode", BouquetErrorCode.LINK_EXPIRED);
        }

    }

    @Nested
    @DisplayName("getQuestionAnswers 테스트")
    class GetQuestionAnswers {

        @Test
        @DisplayName("유효한 토큰과 질문 ID로 발신자/수신자 답변을 조회한다")
        void success() {
            // given
            String token = "valid-token";
            Long bouquetId = 1L;
            Long questionId = 100L;
            LocalDateTime now = LocalDateTime.now();

            BouquetLink link = BouquetLink.builder()
                    .id(1L).bouquetId(bouquetId).linkToken(token)
                    .status(BouquetLinkStatus.ACTIVE).expiredAt(now.plusDays(7))
                    .createdAt(now).modifiedAt(now).build();

            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId)
                    .deleted(false)
                    .createdAt(now).modifiedAt(now).build();

            BouquetAnswer senderAnswer = BouquetAnswer.builder()
                    .id(10L).bouquetId(bouquetId).questionId(questionId)
                    .answerType(BouquetAnswerType.SUBJECTIVE)
                    .respondentType(BouquetAnswerRespondentType.SENDER)
                    .subjectiveContent("Sender answer")
                    .sortOrder(1)
                    .createdAt(now).modifiedAt(now).build();
            BouquetAnswer receiverAnswer = BouquetAnswer.builder()
                    .id(20L).bouquetId(bouquetId).questionId(questionId)
                    .answerType(BouquetAnswerType.SUBJECTIVE)
                    .respondentType(BouquetAnswerRespondentType.RECEIVER)
                    .subjectiveContent("Receiver answer")
                    .sortOrder(1)
                    .createdAt(now).modifiedAt(now).build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetAnswerManager.findByBouquetIdAndQuestionId(bouquetId, questionId))
                    .willReturn(List.of(senderAnswer, receiverAnswer));

            // when
            GetBouquetQuestionAnswersResponse response = bouquetService.getQuestionAnswers(token, questionId);

            // then
            assertThat(response.questionId()).isEqualTo(questionId);
            assertThat(response.senderAnswer().subjectiveContent()).isEqualTo("Sender answer");
            assertThat(response.receiverAnswer().subjectiveContent()).isEqualTo("Receiver answer");
        }

        @Test
        @DisplayName("만료된 링크 토큰이면 예외를 던진다")
        void expiredLink() {
            // given
            String token = "expired-token";
            BouquetLink link = BouquetLink.builder().status(BouquetLinkStatus.EXPIRED).build();
            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));

            // when & then
            assertThatThrownBy(() -> bouquetService.getQuestionAnswers(token, 1L))
                    .isInstanceOf(ServiceException.class)
                    .hasFieldOrPropertyWithValue("errorCode", BouquetErrorCode.LINK_EXPIRED);
        }
    }

    @Nested
    @DisplayName("completeBouquet 테스트")
    class CompleteBouquet {

        @Test
        @DisplayName("로그인한 수신자가 답변을 완료하면 답변을 저장하고 유저 정보를 연결한다")
        void success_with_login() {
            // given
            String token = "token";
            Long bouquetId = 1L;
            Long receiverId = 10L;
            Long userId = 100L;
            AuthenticateUser user = new AuthenticateUser(userId, "Name", "email", true);
            
            BouquetLink link = BouquetLink.builder().id(1L).bouquetId(bouquetId).status(BouquetLinkStatus.ACTIVE).build();
            Bouquet bouquet = Bouquet.builder().id(bouquetId).receiverType(BouquetReceiverType.GUEST).build();
            BouquetReceiver receiver = BouquetReceiver.builder().id(receiverId).build();
            BouquetReceiver namedReceiver = receiver.toBuilder().receiverName("Receiver").build();
            List<CreateBouquetAnswerRequest> answers = List.of(new CreateBouquetAnswerRequest(1L, BouquetAnswerType.SUBJECTIVE, "Answer", 1));

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetReceiverManager.findByBouquetLinkId(1L)).willReturn(Optional.of(receiver));
            given(bouquetReceiverManager.updateReceiverName(receiver, "Receiver")).willReturn(namedReceiver);

            // when
            bouquetService.completeBouquet(token, "Receiver", answers, user);

            // then
            verify(bouquetAnswerManager).createReceiverAnswers(eq(bouquetId), eq(userId), eq(receiverId), anyList());
            verify(bouquetReceiverManager).updateReceiverName(receiver, "Receiver");
            verify(bouquetReceiverManager).connectUser(namedReceiver, userId);
            verify(bouquetLinkManager).complete(any());
        }

        @Test
        @DisplayName("비로그인 수신자가 답변을 완료하면 답변만 저장하고 유저 연결은 건너뛴다")
        void success_without_login() {
            // given
            String token = "token";
            AnonymousUser anonymousUser = new AnonymousUser();
            
            BouquetLink link = BouquetLink.builder().id(1L).bouquetId(1L).status(BouquetLinkStatus.ACTIVE).build();
            Bouquet bouquet = Bouquet.builder().id(1L).receiverType(BouquetReceiverType.GUEST).build();
            BouquetReceiver receiver = BouquetReceiver.builder().id(10L).build();
            BouquetReceiver namedReceiver = receiver.toBuilder().receiverName("Receiver").build();
            
            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(1L)).willReturn(Optional.of(bouquet));
            given(bouquetReceiverManager.findByBouquetLinkId(1L)).willReturn(Optional.of(receiver));
            given(bouquetReceiverManager.updateReceiverName(receiver, "Receiver")).willReturn(namedReceiver);

            // when
            bouquetService.completeBouquet(token, "Receiver", List.of(), anonymousUser);

            // then
            verify(bouquetAnswerManager).createReceiverAnswers(anyLong(), isNull(), anyLong(), anyList());
            verify(bouquetReceiverManager).updateReceiverName(receiver, "Receiver");
            verify(bouquetReceiverManager, never()).connectUser(any(), anyLong());
            verify(bouquetLinkManager).complete(any());
        }

        @Test
        @DisplayName("회원이 보낸 꽃다발이 완료되면 발신자에게 완성 알림을 생성한다")
        void create_notification_when_sender_is_user() {
            // given
            String token = "token";
            Long bouquetId = 1L;
            Long senderId = 200L;
            AnonymousUser anonymousUser = new AnonymousUser();

            BouquetLink link = BouquetLink.builder().id(1L).bouquetId(bouquetId).status(BouquetLinkStatus.ACTIVE).build();
            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId)
                    .senderId(senderId)
                    .senderType(BouquetSenderType.USER)
                    .receiverType(BouquetReceiverType.GUEST)
                    .build();
            BouquetReceiver receiver = BouquetReceiver.builder().id(10L).build();
            BouquetReceiver namedReceiver = receiver.toBuilder().receiverName("Receiver").build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetReceiverManager.findByBouquetLinkId(1L)).willReturn(Optional.of(receiver));
            given(bouquetReceiverManager.updateReceiverName(receiver, "Receiver")).willReturn(namedReceiver);

            // when
            bouquetService.completeBouquet(token, "Receiver", List.of(), anonymousUser);

            // then
            verify(notificationManager).createBouquetCompletedIfAbsent(senderId, bouquetId);
        }

        @Test
        @DisplayName("게스트가 보낸 꽃다발이 완료되면 완성 알림을 생성하지 않는다")
        void skip_notification_when_sender_is_guest() {
            // given
            String token = "token";
            AnonymousUser anonymousUser = new AnonymousUser();

            BouquetLink link = BouquetLink.builder().id(1L).bouquetId(1L).status(BouquetLinkStatus.ACTIVE).build();
            Bouquet bouquet = Bouquet.builder()
                    .id(1L)
                    .senderType(BouquetSenderType.GUEST)
                    .receiverType(BouquetReceiverType.USER)
                    .build();
            BouquetReceiver receiver = BouquetReceiver.builder().id(10L).build();
            BouquetReceiver namedReceiver = receiver.toBuilder().receiverName("Receiver").build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(1L)).willReturn(Optional.of(bouquet));
            given(bouquetReceiverManager.findByBouquetLinkId(1L)).willReturn(Optional.of(receiver));
            given(bouquetReceiverManager.updateReceiverName(receiver, "Receiver")).willReturn(namedReceiver);

            // when
            bouquetService.completeBouquet(token, "Receiver", List.of(), anonymousUser);

            // then
            verify(notificationManager, never()).createBouquetCompletedIfAbsent(anyLong(), anyLong());
        }
    }

    @Nested
    @DisplayName("claimBouquet 테스트")
    class ClaimBouquet {

        @Test
        @DisplayName("유저 정보가 있으면 비어있는 수신자 정보를 계정에 연결한다")
        void success() {
            // given
            String token = "token";
            Long userId = 100L;
            AuthenticateUser user = new AuthenticateUser(userId, "Name", "email", true);
            
            BouquetLink link = BouquetLink.builder().id(1L).bouquetId(1L).build();
            Bouquet bouquet = Bouquet.builder().id(1L).receiverType(BouquetReceiverType.GUEST).build();
            BouquetReceiver receiver = BouquetReceiver.builder().id(10L).userId(null).build();
            
            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(1L)).willReturn(Optional.of(bouquet));
            given(bouquetReceiverManager.findByBouquetLinkId(1L)).willReturn(Optional.of(receiver));
            given(timeProvider.now()).willReturn(LocalDateTime.now());

            // when
            bouquetService.claimBouquet(token, user);

            // then
            verify(bouquetReceiverManager).connectUser(any(), eq(userId));
        }

        @Test
        @DisplayName("익명 유저가 요청하면 소유권 이전을 무시한다")
        void anonymous_user_claim_ignored() {
            // given
            String token = "token";
            AnonymousUser user = new AnonymousUser();

            // when
            bouquetService.claimBouquet(token, user);

            // then
            verify(bouquetLinkManager, never()).findByToken(anyString());
        }
    }
}
