package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetLinkEntity;

public class BouquetLinkConverter {

    public static BouquetLinkEntity toEntity(BouquetLink model) {
        return new BouquetLinkEntity(
                model.getId(),
                model.getBouquetId(),
                model.getLinkToken(),
                model.getStatus().name(),
                model.getExpiredAt()
        );

    }

    public static BouquetLink toModel(BouquetLinkEntity entity) {
        return new BouquetLink(
                entity.getId(),
                entity.getBouquetId(),
                entity.getLinkToken(),
                BouquetLinkStatus.findByCode(entity.getStatus()),
                entity.getExpiredAt(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
