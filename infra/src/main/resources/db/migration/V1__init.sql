-- 유저 테이블
CREATE TABLE tb_user (
    user_id       BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL COMMENT '유저 이름',
    email         VARCHAR(255) NOT NULL COMMENT '유저 이메일',
    provider_type VARCHAR(20)  NOT NULL COMMENT '인증 제공자 타입 (ex.KAKAO)',
    deleted       BOOLEAN      NOT NULL DEFAULT false COMMENT '삭제 여부',
    deleted_at    DATETIME     NULL                   COMMENT '삭제 일자',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 질문 테이블
CREATE TABLE tb_question (
    question_id BIGINT       NOT NULL AUTO_INCREMENT,
    title       VARCHAR(255) NOT NULL COMMENT '질문 제목',
    description VARCHAR(500) NULL     COMMENT '질문 설명',
    created_at  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 질문 옵션 테이블
CREATE TABLE tb_question_option (
    question_option_id BIGINT       NOT NULL AUTO_INCREMENT,
    question_id        BIGINT       NOT NULL COMMENT '질문 ID',
    option_text        VARCHAR(500) NOT NULL COMMENT '옵션 이름',
    sort_order         INT          NOT NULL DEFAULT 0 COMMENT '옵션 정렬 순서',
    created_at         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (question_option_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 꽃다발 테이블
CREATE TABLE tb_bouquet (
    bouquet_id        BIGINT       NOT NULL AUTO_INCREMENT,
    user_id           BIGINT       NOT NULL COMMENT '유저 ID',
    display_name      VARCHAR(100) NOT NULL COMMENT '꽃다발 표시 이름',
    receiver_relation VARCHAR(100) NOT NULL COMMENT '수신자의 관계 (ex. 부모님, 여자친구)',
    bouquet_type_id   BIGINT       NOT NULL COMMENT '꽃다발 타입 ID',
    deleted           BOOLEAN     NOT NULL DEFAULT false COMMENT '삭제 여부',
    deleted_at        DATETIME    NULL                   COMMENT '삭제일시',
    created_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at       DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 꽃다발 타입 테이블
CREATE TABLE tb_bouquet_type (
    bouquet_type_id     BIGINT       NOT NULL AUTO_INCREMENT,
    bouquet_name        VARCHAR(100) NOT NULL COMMENT '꽃다발 이름',
    bouquet_description VARCHAR(500) NULL     COMMENT '꽃다발 설명',
    bouquet_image_url   VARCHAR(500) NULL     COMMENT '꽃다발 이미지 URL',
    is_active           BOOLEAN      NOT NULL DEFAULT TRUE COMMENT '활성화 여부',
    created_at          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 꽃다발 공유 링크 테이블
CREATE TABLE tb_bouquet_link (
    bouquet_link_id BIGINT       NOT NULL AUTO_INCREMENT,
    bouquet_id      BIGINT       NOT NULL COMMENT '꽃다발 ID',
    link_token      VARCHAR(100) NOT NULL COMMENT '링크 토큰',
    status          VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE' COMMENT '상태 (ACTIVE | EXPIRED)',
    expired_at      DATETIME    NOT NULL COMMENT '만료일시',
    created_at      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_link_id),
    UNIQUE KEY uk_link_token (link_token)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 꽃다발 수신자 테이블
CREATE TABLE tb_bouquet_receiver (
    bouquet_receiver_id BIGINT       NOT NULL AUTO_INCREMENT,
    bouquet_id          BIGINT       NOT NULL COMMENT '꽃다발 ID',
    bouquet_link_id     BIGINT       NOT NULL COMMENT '꽃다발 공유 링크 ID',
    receiver_name       VARCHAR(100) NOT NULL COMMENT '꽃다발 수신자 이름',
    created_at          DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_receiver_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 꽃다발 답변 테이블
CREATE TABLE tb_bouquet_answer (
    bouquet_answer_id   BIGINT      NOT NULL AUTO_INCREMENT,
    bouquet_id          BIGINT      NOT NULL COMMENT '꽃다발 ID',
    question_id         BIGINT      NOT NULL COMMENT '질문 ID',
    answer_type         VARCHAR(20) NOT NULL DEFAULT 'SUBJECTIVE' COMMENT '답변 타입 (SUBJECTIVE | MULTIPLE_CHOICE)',
    respondent_type     VARCHAR(20) NOT NULL DEFAULT 'SENDER' COMMENT '응답자 타입 (SENDER | RECEIVER)',
    user_id             BIGINT      NULL     COMMENT '유저 ID',
    receiver_id         BIGINT      NULL     COMMENT '수신자 ID',
    subjective_content  TEXT        NULL     COMMENT '주관식 답변 내용',
    selected_option_id  BIGINT      NULL     COMMENT '객관식 옵션 ID',
    sort_order          INTEGER    NOT NULL DEFAULT 1 COMMENT '정렬 순서',
    created_at          DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at         DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (bouquet_answer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;