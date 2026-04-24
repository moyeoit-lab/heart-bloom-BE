package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetTypeRepository;
import com.heartbloom.be.infra.dao.jpa.bouquet.BouquetTypeJpaDao;
import com.heartbloom.be.infra.entity.converter.BouquetTypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BouquetTypeRepositoryImpl implements BouquetTypeRepository {

    private final BouquetTypeJpaDao bouquetTypeJpaDao;

    @Override
    public Optional<BouquetType> findById(Long id) {
        return bouquetTypeJpaDao.findById(id)
                .map(BouquetTypeConverter::toModel);
    }

    @Override
    public List<BouquetType> findAll() {
        return bouquetTypeJpaDao.findALlByActive(true)
                .stream()
                .map(BouquetTypeConverter::toModel)
                .collect(Collectors.toList());
    }

}