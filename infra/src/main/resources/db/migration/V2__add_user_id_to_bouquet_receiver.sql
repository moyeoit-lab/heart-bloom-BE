-- tb_bouquet_receiver 테이블에 user_id 컬럼 추가
ALTER TABLE tb_bouquet_receiver ADD COLUMN user_id BIGINT NULL COMMENT '유저 ID' AFTER bouquet_link_id;

-- tb_bouquet_link 상태값 설명
ALTER TABLE tb_bouquet_link MODIFY COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '상태 (ACTIVE | EXPIRED | COMPLETED)';
