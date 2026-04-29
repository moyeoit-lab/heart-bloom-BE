package com.heartbloom.be.app.service.notification;

import com.heartbloom.be.app.application.notification.implementation.NotificationManager;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.NotificationErrorCode;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationStatus;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.core.repository.domain.notification.NotificationRepository;
import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private NotificationManager notificationManager;
    @Mock
    private TimeProvider timeProvider;

    @Test
    @DisplayName("안 읽은 완성 꽃다발 알림 목록을 조회한다")
    void getBouquetCompletionAlerts() {
        // given
        AuthenticateUser user = new AuthenticateUser(1L, "user", "user@email.com", false);
        LocalDateTime completedAt = LocalDateTime.of(2026, 4, 29, 12, 0);
        given(notificationRepository.queryUnreadBouquetCompletionAlerts(1L))
                .willReturn(List.of(new BouquetCompletionAlertQueryDto(10L, 100L, "생일 꽃다발", "image-url", completedAt)));

        // when
        var response = notificationService.getBouquetCompletionAlerts(user);

        // then
        assertThat(response.alerts()).hasSize(1);
        assertThat(response.alerts().get(0).alertId()).isEqualTo(10L);
        assertThat(response.alerts().get(0).bouquetId()).isEqualTo(100L);
    }

    @Test
    @DisplayName("본인 알림이면 읽음 처리한다")
    void readBouquetCompletionAlert() {
        // given
        AuthenticateUser user = new AuthenticateUser(1L, "user", "user@email.com", false);
        LocalDateTime now = LocalDateTime.of(2026, 4, 29, 12, 0);
        Notification notification = Notification.builder()
                .id(10L)
                .userId(1L)
                .bouquetId(100L)
                .type(NotificationType.BOUQUET_COMPLETED)
                .status(NotificationStatus.UNREAD)
                .build();

        given(notificationManager.findById(10L)).willReturn(Optional.of(notification));
        given(timeProvider.now()).willReturn(now);

        // when
        notificationService.readBouquetCompletionAlert(10L, user);

        // then
        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationManager).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(NotificationStatus.READ);
        assertThat(captor.getValue().getReadAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("다른 유저의 알림이면 접근을 거부한다")
    void readBouquetCompletionAlert_accessDenied() {
        // given
        AuthenticateUser user = new AuthenticateUser(1L, "user", "user@email.com", false);
        Notification notification = Notification.builder()
                .id(10L)
                .userId(2L)
                .bouquetId(100L)
                .type(NotificationType.BOUQUET_COMPLETED)
                .status(NotificationStatus.UNREAD)
                .build();

        given(notificationManager.findById(10L)).willReturn(Optional.of(notification));

        // when & then
        assertThatThrownBy(() -> notificationService.readBouquetCompletionAlert(10L, user))
                .isInstanceOf(ServiceException.class)
                .hasFieldOrPropertyWithValue("errorCode", NotificationErrorCode.ACCESS_DENIED);
    }

}
