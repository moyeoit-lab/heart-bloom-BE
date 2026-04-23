package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.RelationType;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;

public class BouquetConverter {

    public static BouquetEntity toEntity(Bouquet model) {
        return new BouquetEntity(
            model.getId(),
            model.getSenderId(),
            model.getSenderType(),
            model.getReceiverId(),
            model.getReceiverType(),
            model.getDisplayName(),
            model.getRelationType().name(),
            model.getBouquetTypeId(),
            model.isDeleted(),
            model.getDeletedAt()
        );
    }

    public static Bouquet toModel(BouquetEntity entity) {
        return new Bouquet(
                entity.getId(),
                entity.getSenderId(),
                entity.getSenderType(),
                entity.getReceiverId(),
                entity.getReceiverType(),
                entity.getDisplayName(),
                RelationType.findByCode(entity.getReceiverRelation()),
                entity.getBouquetTypeId(),
                entity.isDeleted(),
                entity.getDeletedAt(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
