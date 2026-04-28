package com.heartbloom.be.infra.dao.jpa.question;

import com.heartbloom.be.infra.entity.domain.question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionJpaDao extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q ORDER BY q.id ASC")
    List<QuestionEntity> findAllOrderByIdAsc();

    @Query(value = """
            SELECT q.*
            FROM tb_question q
            INNER JOIN tb_bouquet_type_question btq ON btq.question_id = q.question_id
            WHERE btq.bouquet_type_id = :bouquetTypeId
            ORDER BY btq.sort_order ASC, q.question_id ASC
            """, nativeQuery = true)
    List<QuestionEntity> findByBouquetTypeIdOrderBySortOrder(@Param("bouquetTypeId") Long bouquetTypeId);

}
