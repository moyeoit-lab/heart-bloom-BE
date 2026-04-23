-- tb_bouquet_receiver 테이블에 user_id 컬럼 추가
ALTER TABLE tb_bouquet_receiver ADD COLUMN user_id BIGINT NULL COMMENT '유저 ID' AFTER bouquet_link_id;

-- tb_bouquet_link 상태값 설명
ALTER TABLE tb_bouquet_link MODIFY COLUMN status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT '상태 (ACTIVE | EXPIRED | COMPLETED)';
-- tb_bouquet 테이블 리팩토링: 다형성 연관 관계 반영
ALTER TABLE tb_bouquet DROP COLUMN user_id;

ALTER TABLE tb_bouquet ADD COLUMN sender_id BIGINT NOT NULL COMMENT '발신자 ID' AFTER bouquet_id;
ALTER TABLE tb_bouquet ADD COLUMN sender_type VARCHAR(20) NOT NULL COMMENT '발신자 타입 (USER | GUEST)' AFTER sender_id;
ALTER TABLE tb_bouquet ADD COLUMN receiver_id BIGINT NULL COMMENT '수신자 ID' AFTER sender_type;
ALTER TABLE tb_bouquet ADD COLUMN receiver_type VARCHAR(20) NOT NULL COMMENT '수신자 타입 (USER | GUEST | EVERYONE)' AFTER receiver_id;
