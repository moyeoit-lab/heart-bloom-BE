CREATE TABLE tb_notification (
    notification_id BIGINT      NOT NULL AUTO_INCREMENT,
    user_id         BIGINT      NOT NULL COMMENT '알림 받을 유저 ID',
    bouquet_id      BIGINT      NOT NULL COMMENT '관련 꽃다발 ID',
    type            VARCHAR(50) NOT NULL COMMENT '알림 타입',
    status          VARCHAR(20) NOT NULL DEFAULT 'UNREAD' COMMENT '알림 상태',
    read_at         DATETIME    NULL COMMENT '읽은 시각',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (notification_id),
    UNIQUE KEY uk_notification_once (user_id, bouquet_id, type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
