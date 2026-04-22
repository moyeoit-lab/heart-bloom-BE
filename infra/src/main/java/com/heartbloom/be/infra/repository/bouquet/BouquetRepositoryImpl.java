package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import com.heartbloom.be.infra.dao.jpa.bouquet.BouquetJpaDao;
import com.heartbloom.be.infra.entity.converter.BouquetConverter;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BouquetRepositoryImpl implements BouquetRepository {

    private final BouquetJpaDao bouquetJpaDao;

    @Override
    public Bouquet save(Bouquet bouquet) {
        BouquetEntity entity = bouquetJpaDao.save(BouquetConverter.toEntity(bouquet));
        return BouquetConverter.toModel(entity);
    }

    @Override
    public Optional<Bouquet> findById(Long bouquetId) {
        return bouquetJpaDao.findById(bouquetId)
                .map(BouquetConverter::toModel);
    }

    @Override
    public List<Bouquet> findByUserId(Long userId) {
        return bouquetJpaDao.findByUserId(userId)
                .stream()
                .map(BouquetConverter::toModel)
                .collect(Collectors.toList());
    }

}