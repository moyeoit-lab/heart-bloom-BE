package com.heartbloom.be.infra.repository.notification;

import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationStatus;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.core.repository.domain.notification.NotificationRepository;
import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;
import com.heartbloom.be.infra.dao.jpa.notification.NotificationJpaDao;
import com.heartbloom.be.infra.entity.converter.NotificationConverter;
import com.heartbloom.be.infra.entity.domain.notification.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaDao notificationJpaDao;

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationJpaDao.save(NotificationConverter.toEntity(notification));
        return NotificationConverter.toModel(entity);
    }

    @Override
    public Optional<Notification> findById(Long notificationId) {
        return notificationJpaDao.findById(notificationId)
                .map(NotificationConverter::toModel);
    }

    @Override
    public boolean existsByUserIdAndBouquetIdAndType(Long userId, Long bouquetId, NotificationType type) {
        return notificationJpaDao.existsByUserIdAndBouquetIdAndType(userId, bouquetId, type);
    }

    @Override
    public List<BouquetCompletionAlertQueryDto> queryUnreadBouquetCompletionAlerts(Long userId) {
        return notificationJpaDao.queryUnreadBouquetCompletionAlerts(
                userId,
                NotificationType.BOUQUET_COMPLETED,
                NotificationStatus.UNREAD
        );
    }

}
