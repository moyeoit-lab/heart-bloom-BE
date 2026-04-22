package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetLinkRepository;
import com.heartbloom.be.infra.dao.jpa.bouquet.BouquetLinkJpaDao;
import com.heartbloom.be.infra.entity.converter.BouquetLinkConverter;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetLinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BouquetLinkRepositoryImpl implements BouquetLinkRepository {

    private final BouquetLinkJpaDao bouquetLinkJpaDao;

    @Override
    public BouquetLink save(BouquetLink bouquetLink) {
        BouquetLinkEntity entity = bouquetLinkJpaDao.save(BouquetLinkConverter.toEntity(bouquetLink));
        return BouquetLinkConverter.toModel(entity);
    }

    @Override
    public Optional<BouquetLink> findByBouquetId(Long bouquetId) {
        return bouquetLinkJpaDao.findByBouquetIdAndStatus(bouquetId, BouquetLinkStatus.ACTIVE)
                .map(BouquetLinkConverter::toModel);
    }

}