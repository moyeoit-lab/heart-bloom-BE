package com.heartbloom.be.infra.dao.jpa.notification;

import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationStatus;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto;
import com.heartbloom.be.infra.entity.domain.notification.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaDao extends JpaRepository<NotificationEntity, Long> {

    boolean existsByUserIdAndBouquetIdAndType(Long userId, Long bouquetId, NotificationType type);

    @Query("""
            SELECT new com.heartbloom.be.core.repository.domain.notification.dto.BouquetCompletionAlertQueryDto(
                n.id,
                b.id,
                b.displayName,
                bt.bouquetImageUrl,
                n.createdAt
            )
            FROM NotificationEntity n
            JOIN BouquetEntity b ON b.id = n.bouquetId
            LEFT JOIN BouquetTypeEntity bt ON bt.id = b.bouquetTypeId
            WHERE n.userId = :userId
              AND n.type = :type
              AND n.status = :status
              AND b.deleted = false
            ORDER BY n.createdAt DESC
            """)
    List<BouquetCompletionAlertQueryDto> queryUnreadBouquetCompletionAlerts(
            @Param("userId") Long userId,
            @Param("type") NotificationType type,
            @Param("status") NotificationStatus status
    );

}
