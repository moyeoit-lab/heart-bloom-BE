package com.heartbloom.be.core.model.domain.notification;

import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationStatus;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Notification {

    private Long id;
    private Long userId;
    private Long bouquetId;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Notification createBouquetCompleted(Long userId, Long bouquetId, LocalDateTime now) {
        return new Notification(
                null,
                userId,
                bouquetId,
                NotificationType.BOUQUET_COMPLETED,
                NotificationStatus.UNREAD,
                null,
                now,
                now
        );
    }

    public Notification read(LocalDateTime now) {
        if (status == NotificationStatus.READ) {
            return this;
        }

        return toBuilder()
                .status(NotificationStatus.READ)
                .readAt(now)
                .modifiedAt(now)
                .build();
    }

    public boolean isOwnedBy(Long userId) {
        return this.userId.equals(userId);
    }

}
