package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetTypeEntity;

public class BouquetTypeConverter {

    public static BouquetTypeEntity toEntity(BouquetType model) {
        return new BouquetTypeEntity(
                model.getId(),
                model.getBouquetName(),
                model.getBouquetDescription(),
                model.getBouquetImageUrl(),
                model.isActive()
        );

    }

    public static BouquetType toModel(BouquetTypeEntity entity) {
        return new BouquetType(
                entity.getId(),
                entity.getBouquetName(),
                entity.getBouquetDescription(),
                entity.getBouquetImageUrl(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );


    }


}
