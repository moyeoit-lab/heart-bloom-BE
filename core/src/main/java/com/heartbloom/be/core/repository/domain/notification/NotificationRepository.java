package com.heartbloom.be.core.repository.domain.notification;

import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(Long notificationId);

    boolean existsByUserIdAndBouquetIdAndType(Long userId, Long bouquetId, NotificationType type);

    List<BouquetCompletionAlertQueryDto> queryUnreadBouquetCompletionAlerts(Long userId);

}
