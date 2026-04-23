package com.heartbloom.be.core.model.domain.bouquet.enumerate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BouquetReceiverType {
    USER("user", "회원"),
    GUEST("guest", "비회원"),
    EVERYONE("everyone", "불특정 다수")
    ;

    private final String serializedValue;
    private final String description;

    BouquetReceiverType(String serializedValue, String description) {
        this.serializedValue = serializedValue;
        this.description = description;
    }

    public static BouquetReceiverType findByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown BouquetReceiverType: " + code));
    }
}
