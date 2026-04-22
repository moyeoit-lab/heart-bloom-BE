package com.heartbloom.be.core.repository.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;

import java.util.Optional;

public interface BouquetLinkRepository {

    BouquetLink save(BouquetLink bouquetLink);

    Optional<BouquetLink> findByBouquetId(Long bouquetId);

}
