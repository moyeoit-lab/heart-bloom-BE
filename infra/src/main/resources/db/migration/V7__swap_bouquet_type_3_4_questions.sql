DELETE FROM tb_bouquet_type_question
WHERE bouquet_type_id IN (3, 4);

INSERT INTO tb_bouquet_type_question (bouquet_type_id, question_id, sort_order)
VALUES
    (3, 13, 1),
    (3, 14, 2),
    (3, 15, 3),
    (3, 16, 4),
    (3, 17, 5),
    (4, 9, 1),
    (4, 10, 2),
    (4, 11, 3),
    (4, 12, 4),
    (4, 17, 5);
