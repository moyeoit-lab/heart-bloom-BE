package com.heartbloom.be.core.repository.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;

import java.util.Optional;

public interface BouquetTypeRepository {
    Optional<BouquetType> findById(Long id);
}
