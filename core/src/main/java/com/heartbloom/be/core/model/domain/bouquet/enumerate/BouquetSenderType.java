package com.heartbloom.be.core.model.domain.bouquet.enumerate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BouquetSenderType {
    USER("user", "회원"),
    GUEST("guest", "비회원")
    ;

    private final String serializedValue;
    private final String description;

    BouquetSenderType(String serializedValue, String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

    public static BouquetSenderType findByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown BouquetSenderType: " + code));
    }
}
