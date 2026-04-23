package com.heartbloom.be.app.application.bouquet.implementation;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BouquetTypeManager {

    private final BouquetTypeRepository bouquetTypeRepository;

    public Optional<BouquetType> findById(Long id) {
        return bouquetTypeRepository.findById(id);
    }
}
