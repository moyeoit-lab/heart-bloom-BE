package com.heartbloom.be.core.repository.domain.receiver;

import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;

import java.util.Optional;

public interface BouquetReceiverRepository {
    BouquetReceiver save(BouquetReceiver bouquetReceiver);
    Optional<BouquetReceiver> findByBouquetId(Long bouquetId);
    Optional<BouquetReceiver> findByBouquetLinkId(Long bouquetLinkId);
}
