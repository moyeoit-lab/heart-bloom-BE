package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BouquetJpaDao extends JpaRepository<BouquetEntity, Long> {

    @Query("SELECT b FROM BouquetEntity b WHERE b.id = :bouquetId AND b.deleted = FALSE")
    Optional<BouquetEntity> findById(@Param("bouquetId") Long bouquetId);

    @Query("SELECT b FROM BouquetEntity b WHERE b.userId = :userId AND b.deleted = FALSE")
    List<BouquetEntity> findByUserId(@Param("userId") Long userId);

}