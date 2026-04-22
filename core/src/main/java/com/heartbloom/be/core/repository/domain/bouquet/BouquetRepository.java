package com.heartbloom.be.core.repository.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository {

    Bouquet save(Bouquet bouquet);

    Optional<Bouquet> findById(Long bouquetId);

    List<Bouquet> findByUserId(Long userId);

}