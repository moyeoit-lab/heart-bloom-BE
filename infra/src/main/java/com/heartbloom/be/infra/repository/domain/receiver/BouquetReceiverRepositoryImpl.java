package com.heartbloom.be.infra.repository.domain.receiver;

import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.repository.domain.receiver.BouquetReceiverRepository;
import com.heartbloom.be.infra.entity.converter.BouquetReceiverConverter;
import com.heartbloom.be.infra.entity.domain.receiver.BouquetReceiverEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BouquetReceiverRepositoryImpl implements BouquetReceiverRepository {

    private final JpaBouquetReceiverRepository jpaBouquetReceiverRepository;

    @Override
    public BouquetReceiver save(BouquetReceiver bouquetReceiver) {
        BouquetReceiverEntity entity = BouquetReceiverConverter.toEntity(bouquetReceiver);
        return BouquetReceiverConverter.toModel(jpaBouquetReceiverRepository.save(entity));
    }

    @Override
    public Optional<BouquetReceiver> findByBouquetId(Long bouquetId) {
        return jpaBouquetReceiverRepository.findByBouquetId(bouquetId)
                .map(BouquetReceiverConverter::toModel);
    }

    @Override
    public Optional<BouquetReceiver> findByBouquetLinkId(Long bouquetLinkId) {
        return jpaBouquetReceiverRepository.findByBouquetLinkId(bouquetLinkId)
                .map(BouquetReceiverConverter::toModel);
    }
}
