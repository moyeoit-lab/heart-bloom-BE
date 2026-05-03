package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.application.bouquet.implementation.generator.BouquetLinkUrlGenerator;
import com.heartbloom.be.app.application.bouquet.implementation.reader.BouquetLinkReader;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BouquetLinkService {

    private final BouquetLinkReader bouquetLinkReader;
    private final BouquetLinkUrlGenerator urlGenerator;

    @Transactional(readOnly = true)
    public String getBouquetLinkUrl(Long bouquetId, String origin) {
        BouquetLink link = bouquetLinkReader.readByBouquetId(bouquetId);
        return urlGenerator.generate(link.getLinkToken(), origin);
    }

}