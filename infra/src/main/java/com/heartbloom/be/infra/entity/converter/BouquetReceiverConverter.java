package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.infra.entity.domain.receiver.BouquetReceiverEntity;

public class BouquetReceiverConverter {

    public static BouquetReceiverEntity toEntity(BouquetReceiver model) {
        return new BouquetReceiverEntity(
                model.getId(),
                model.getBouquetId(),
                model.getBouquetLinkId(),
                model.getReceiverName()
        );
    }

    public static BouquetReceiver toModel(BouquetReceiverEntity entity) {
        return new BouquetReceiver(
                entity.getId(),
                entity.getBouquetId(),
                entity.getBouquetLinkId(),
                entity.getReceiverName(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
