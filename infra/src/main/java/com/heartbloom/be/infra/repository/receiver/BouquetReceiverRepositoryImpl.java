package com.heartbloom.be.infra.repository.receiver;

import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.repository.domain.receiver.BouquetReceiverRepository;
import com.heartbloom.be.infra.dao.jpa.receiver.BouquetReceiverJpaDao;
import com.heartbloom.be.infra.entity.converter.BouquetReceiverConverter;
import com.heartbloom.be.infra.entity.domain.receiver.BouquetReceiverEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BouquetReceiverRepositoryImpl implements BouquetReceiverRepository {

    private final BouquetReceiverJpaDao bouquetReceiverJpaDao;

    @Override
    public BouquetReceiver save(BouquetReceiver bouquetReceiver) {
        BouquetReceiverEntity entity = BouquetReceiverConverter.toEntity(bouquetReceiver);
        return BouquetReceiverConverter.toModel(bouquetReceiverJpaDao.save(entity));
    }

    @Override
    public Optional<BouquetReceiver> findByBouquetId(Long bouquetId) {
        return bouquetReceiverJpaDao.findByBouquetId(bouquetId)
                .map(BouquetReceiverConverter::toModel);
    }

    @Override
    public Optional<BouquetReceiver> findByBouquetLinkId(Long bouquetLinkId) {
        return bouquetReceiverJpaDao.findByBouquetLinkId(bouquetLinkId)
                .map(BouquetReceiverConverter::toModel);
    }
}
