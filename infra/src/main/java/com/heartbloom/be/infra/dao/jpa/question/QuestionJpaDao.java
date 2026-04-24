package com.heartbloom.be.infra.dao.jpa.question;

import com.heartbloom.be.infra.entity.domain.question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionJpaDao extends JpaRepository<QuestionEntity, Long> {

    @Query("SELECT q FROM QuestionEntity q ORDER BY q.id ASC")
    List<QuestionEntity> findAllOrderByIdAsc();

}
