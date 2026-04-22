package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.infra.entity.domain.answer.BouquetAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BouquetAnswerJpaDao extends JpaRepository<BouquetAnswerEntity, Long> {

    @Query("SELECT ba FROM BouquetAnswerEntity ba WHERE ba.id = :bouquetAnswerId")
    Optional<BouquetAnswerEntity> findById(@Param("bouquetAnswerId") Long bouquetAnswerId);

    @Query("SELECT ba FROM BouquetAnswerEntity ba WHERE ba.bouquetId = :bouquetId")
    List<BouquetAnswerEntity> findByBouquetId(@Param("bouquetId") Long bouquetId);

}