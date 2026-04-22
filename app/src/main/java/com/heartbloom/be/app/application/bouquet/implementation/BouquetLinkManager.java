package com.heartbloom.be.app.application.bouquet.implementation;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class BouquetLinkManager {

    private final static Long DEFAULT_EXPIRE_DAYS_POLICY = 7L;

    private final BouquetLinkRepository bouquetLinkRepository;

    public BouquetLink create(Long bouquetId, String linkToken, LocalDateTime now) {
        BouquetLink bouquetLink = BouquetLink.create(
                bouquetId,
                linkToken,
                BouquetLinkStatus.ACTIVE,
                now.plusDays(DEFAULT_EXPIRE_DAYS_POLICY).with(LocalTime.MAX),
                now
        );

        return bouquetLinkRepository.save(bouquetLink);
    }

}
