-- 수신자 이름은 꽃다발 생성 시점에는 알 수 없고, 수신자가 답변할 때 저장된다.
ALTER TABLE tb_bouquet_receiver MODIFY COLUMN receiver_name VARCHAR(100) NULL COMMENT '꽃다발 수신자 이름';
