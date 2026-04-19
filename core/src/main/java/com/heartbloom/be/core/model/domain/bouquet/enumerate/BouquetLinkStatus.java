package com.heartbloom.be.core.model.domain.bouquet.enumerate;

import lombok.Getter;

import java.util.Arrays;

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

    public static BouquetLinkStatus findByCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown BouquetLinkStatus: " + code));
    }

}