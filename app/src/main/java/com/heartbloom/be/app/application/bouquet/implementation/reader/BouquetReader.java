package com.heartbloom.be.app.application.bouquet.implementation.reader;

import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BouquetReader {

    private final BouquetRepository bouquetRepository;

    public Bouquet read(Long bouquetId) {
        return bouquetRepository.findById(bouquetId)
                .orElseThrow((() -> new ServiceException(BouquetErrorCode.NOT_FOUND)));
    }

    public List<Bouquet> readBouquets(Long bouquetId, Long userId) {
        return bouquetRepository.findByUserId(userId);
    }

}