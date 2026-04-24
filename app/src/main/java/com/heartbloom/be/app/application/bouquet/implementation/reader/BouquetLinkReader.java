package com.heartbloom.be.app.application.bouquet.implementation.reader;

import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BouquetLinkReader {

    private final BouquetLinkRepository bouquetLinkRepository;

    public BouquetLink readByBouquetId(Long bouquetId) {
        return bouquetLinkRepository.findByBouquetId(bouquetId)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));
    }

}