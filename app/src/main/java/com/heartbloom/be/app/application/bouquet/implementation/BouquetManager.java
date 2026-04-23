package com.heartbloom.be.app.application.bouquet.implementation;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.RelationType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BouquetManager {

    private final TimeProvider timeProvider;
    private final BouquetRepository bouquetRepository;

    public Bouquet create(CreateBouquetRequest request, Long userId) {
        LocalDateTime now = timeProvider.now();
        Bouquet bouquet = Bouquet.create(
                userId,
                request.displayName(),
                RelationType.findByCode(request.relationName()),
                request.bouquetTypeId(),
                now
        );
        return bouquetRepository.save(bouquet);
    }

    public Optional<Bouquet> findById(Long id) {
        return bouquetRepository.findById(id);
    }

}
