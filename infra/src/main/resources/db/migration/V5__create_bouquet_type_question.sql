CREATE TABLE tb_bouquet_type_question (
    bouquet_type_question_id BIGINT NOT NULL AUTO_INCREMENT,
    bouquet_type_id          BIGINT NOT NULL COMMENT '꽃다발 타입 ID',
    question_id              BIGINT NOT NULL COMMENT '질문 ID',
    sort_order               INT    NOT NULL DEFAULT 0 COMMENT '질문 정렬 순서',
    created_at               DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_type_question_id),
    UNIQUE KEY uk_bouquet_type_question (bouquet_type_id, question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO tb_bouquet_type_question (bouquet_type_id, question_id, sort_order)
VALUES
    (1, 1, 1),
    (1, 2, 2),
    (1, 3, 3),
    (1, 4, 4),
    (2, 5, 1),
    (2, 6, 2),
    (2, 7, 3),
    (2, 8, 4),
    (3, 9, 1),
    (3, 10, 2),
    (3, 11, 3),
    (3, 12, 4),
    (4, 13, 1),
    (4, 14, 2),
    (4, 15, 3),
    (4, 16, 4),
    (1, 17, 5),
    (2, 17, 5),
    (3, 17, 5),
    (4, 17, 5);
