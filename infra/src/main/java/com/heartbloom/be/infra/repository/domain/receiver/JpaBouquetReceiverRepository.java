package com.heartbloom.be.infra.repository.domain.receiver;

import com.heartbloom.be.infra.entity.domain.receiver.BouquetReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBouquetReceiverRepository extends JpaRepository<BouquetReceiverEntity, Long> {
    Optional<BouquetReceiverEntity> findByBouquetId(Long bouquetId);
    Optional<BouquetReceiverEntity> findByBouquetLinkId(Long bouquetLinkId);
}
