package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.RelationType;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;

public class BouquetConverter {

    public static BouquetEntity toEntity(Bouquet model) {
        return new BouquetEntity(
            model.getId(),
            model.getUserId(),
            model.getDisplayName(),
            model.getRelationType().name(),
            model.getBouquetTypeId()
        );
    }

    public static Bouquet toModel(BouquetEntity entity) {
        return new Bouquet(
                entity.getId(),
                entity.getUserId(),
                entity.getDisplayName(),
                RelationType.findByCode(entity.getReceiverRelation()),
                entity.getBouquetTypeId(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
