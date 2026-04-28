package com.heartbloom.be.infra.dao.jpa.question;

import com.heartbloom.be.infra.entity.domain.question.QuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionJpaDao extends JpaRepository<QuestionOptionEntity, Long> {

    @Query("""
            SELECT qo
            FROM QuestionOptionEntity qo
            WHERE qo.questionId IN :questionIds
            ORDER BY qo.questionId ASC, qo.sortOrder ASC, qo.id ASC
            """)
    List<QuestionOptionEntity> findByQuestionIds(@Param("questionIds") List<Long> questionIds);

}
