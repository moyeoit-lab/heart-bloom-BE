package com.heartbloom.be.core.model.domain.bouquet.enumerate;

import lombok.Getter;

@Getter
public enum BouquetLinkStatus {

    ACTIVE("active", "활성화"),
    EXPIRED("expired", "만료")
    ;

    private final String serializedValue;
    private final String description;

    BouquetLinkStatus(String serializedValue,
                      String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

}