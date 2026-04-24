package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.infra.entity.domain.bouquet.BouquetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BouquetTypeJpaDao extends JpaRepository<BouquetTypeEntity, Long> {

    @Query("SELECT bt FROM BouquetTypeEntity bt WHERE bt.active = :active ORDER BY bt.id ASC")
    List<BouquetTypeEntity> findALlByActive(@Param("active") boolean active);

}