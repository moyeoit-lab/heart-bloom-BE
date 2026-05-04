package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetAnswerManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetLinkManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetReceiverManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetTypeManager;
import com.heartbloom.be.app.application.bouquet.implementation.generator.BouquetLinkTokenGenerator;
import com.heartbloom.be.app.application.notification.implementation.NotificationManager;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BouquetServiceTest {

    @Mock private TimeProvider timeProvider;
    @Mock private BouquetManager bouquetManager;
    @Mock private BouquetAnswerManager bouquetAnswerManager;
    @Mock private BouquetLinkManager bouquetLinkManager;
    @Mock private BouquetReceiverManager bouquetReceiverManager;
    @Mock private BouquetTypeManager bouquetTypeManager;
    @Mock private NotificationManager notificationManager;
    @Mock private BouquetRepository bouquetRepository;
    @Mock private BouquetLinkTokenGenerator linkTokenGenerator;

    @InjectMocks
    private BouquetService bouquetService;

    @Nested
    @DisplayName("수신자용 부케 조회")
    class GetBouquetForReceiver {

        @Test
        @DisplayName("완료되지 않은 부케 정보를 조회할 수 있다")
        void getActiveBouquet() {
            String token = "valid-token";
            Long bouquetId = 1L;
            Long bouquetTypeId = 2L;
            LocalDateTime now = LocalDateTime.now();

            BouquetLink link = BouquetLink.builder()
                    .id(1L)
                    .bouquetId(bouquetId)
                    .linkToken(token)
                    .status(BouquetLinkStatus.ACTIVE)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId)
                    .senderId(10L)
                    .senderType(BouquetSenderType.USER)
                    .receiverType(BouquetReceiverType.EVERYONE)
                    .displayName("DisplayName")
                    .bouquetTypeId(bouquetTypeId)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            BouquetType type = BouquetType.builder()
                    .id(bouquetTypeId)
                    .bouquetName("Rose")
                    .bouquetDescription("Desc")
                    .bouquetImageUrl("url")
                    .active(true)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetTypeManager.findById(bouquetTypeId)).willReturn(Optional.of(type));

            GetBouquetForReceiverResponse response = bouquetService.getBouquetForReceiver(token);

            assertThat(response.senderName()).isEqualTo("DisplayName");
            assertThat(response.bouquetName()).isEqualTo("Rose");
            assertThat(response.bouquetImageUrl()).isEqualTo("url");
            assertThat(response.isCompleted()).isFalse();
            verify(bouquetAnswerManager, never()).findByBouquetId(anyLong());
        }

        @Test
        @DisplayName("완료된 링크면 완료 여부를 true로 반환한다")
        void getCompletedBouquet() {
            String token = "completed-token";
            Long bouquetId = 2L;
            Long bouquetTypeId = 3L;
            LocalDateTime now = LocalDateTime.now();

            BouquetLink link = BouquetLink.builder()
                    .id(2L)
                    .bouquetId(bouquetId)
                    .linkToken(token)
                    .status(BouquetLinkStatus.COMPLETED)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            Bouquet bouquet = Bouquet.builder()
                    .id(bouquetId)
                    .senderType(BouquetSenderType.GUEST)
                    .receiverType(BouquetReceiverType.USER)
                    .displayName("GuestSender")
                    .bouquetTypeId(bouquetTypeId)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            BouquetType type = BouquetType.builder()
                    .id(bouquetTypeId)
                    .bouquetName("Tulip")
                    .bouquetDescription("Desc")
                    .bouquetImageUrl("image-url")
                    .active(true)
                    .createdAt(now)
                    .modifiedAt(now)
                    .build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));
            given(bouquetManager.findById(bouquetId)).willReturn(Optional.of(bouquet));
            given(bouquetTypeManager.findById(bouquetTypeId)).willReturn(Optional.of(type));

            GetBouquetForReceiverResponse response = bouquetService.getBouquetForReceiver(token);

            assertThat(response.senderName()).isEqualTo("GuestSender");
            assertThat(response.bouquetName()).isEqualTo("Tulip");
            assertThat(response.isCompleted()).isTrue();
        }

        @Test
        @DisplayName("만료된 링크로는 조회할 수 없다")
        void expiredLink() {
            String token = "expired-token";
            BouquetLink link = BouquetLink.builder()
                    .id(1L)
                    .linkToken(token)
                    .status(BouquetLinkStatus.EXPIRED)
                    .build();

            given(bouquetLinkManager.findByToken(token)).willReturn(Optional.of(link));

            assertThatThrownBy(() -> bouquetService.getBouquetForReceiver(token))
                    .isInstanceOf(ServiceException.class)
                    .hasMessageContaining("만료된 부케 링크입니다.");
        }
    }
}
