package com.heartbloom.be.app.application.notification.implementation;

import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.core.repository.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationManager {

    private final NotificationRepository notificationRepository;
    private final TimeProvider timeProvider;

    public void createBouquetCompletedIfAbsent(Long userId, Long bouquetId) {
        if (notificationRepository.existsByUserIdAndBouquetIdAndType(userId, bouquetId, NotificationType.BOUQUET_COMPLETED)) {
            return;
        }

        try {
            notificationRepository.save(Notification.createBouquetCompleted(userId, bouquetId, timeProvider.now()));
        } catch (DataIntegrityViolationException ignored) {
            // The database unique key is the final guard for concurrent completion requests.
        }
    }

    public Optional<Notification> findById(Long notificationId) {
        return notificationRepository.findById(notificationId);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

}
