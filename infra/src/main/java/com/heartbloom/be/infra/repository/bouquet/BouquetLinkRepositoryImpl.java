package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetLinkRepository;
import com.heartbloom.be.infra.entity.converter.BouquetLinkConverter;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetLinkEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BouquetLinkRepositoryImpl implements BouquetLinkRepository {

    private final JpaBouquetLinkRepository jpaBouquetLinkRepository;

    @Override
    public BouquetLink save(BouquetLink bouquetLink) {
        BouquetLinkEntity entity = BouquetLinkConverter.toEntity(bouquetLink);
        return BouquetLinkConverter.toModel(jpaBouquetLinkRepository.save(entity));
    }

    @Override
    public Optional<BouquetLink> findByToken(String token) {
        return jpaBouquetLinkRepository.findByLinkToken(token)
                .map(BouquetLinkConverter::toModel);
    }

    @Override
    public Optional<BouquetLink> findByBouquetId(Long bouquetId) {
        return jpaBouquetLinkRepository.findByBouquetId(bouquetId)
                .map(BouquetLinkConverter::toModel);
    }
}
