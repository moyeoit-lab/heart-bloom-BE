package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.infra.entity.domain.notification.NotificationEntity;

public class NotificationConverter {

    public static NotificationEntity toEntity(Notification model) {
        return new NotificationEntity(
                model.getId(),
                model.getUserId(),
                model.getBouquetId(),
                model.getType(),
                model.getStatus(),
                model.getReadAt()
        );
    }

    public static Notification toModel(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getUserId(),
                entity.getBouquetId(),
                entity.getType(),
                entity.getStatus(),
                entity.getReadAt(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
