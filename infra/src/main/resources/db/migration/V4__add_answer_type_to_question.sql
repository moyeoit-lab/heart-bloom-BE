-- 질문별 필수/선택 여부를 구분한다. 기존 질문은 필수 질문으로 취급한다.
ALTER TABLE tb_question ADD COLUMN answer_type VARCHAR(20) NOT NULL DEFAULT 'REQUIRED' COMMENT '질문 응답 필수 여부 (REQUIRED | OPTIONAL)' AFTER description;
