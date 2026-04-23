package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.infra.entity.domain.bouquet.BouquetTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BouquetTypeJpaDao extends JpaRepository<BouquetTypeEntity, Long> {
}
