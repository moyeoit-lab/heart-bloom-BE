package com.heartbloom.be.infra.entity.domain.notification;

import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationStatus;
import com.heartbloom.be.core.model.domain.notification.enumerate.NotificationType;
import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
        name = "tb_notification",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_notification_once",
                columnNames = {"user_id", "bouquet_id", "type"}
        )
)
@Entity
public class NotificationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "bouquet_id", nullable = false)
    private Long bouquetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private NotificationStatus status;

    @Column(name = "read_at")
    private LocalDateTime readAt;

}
