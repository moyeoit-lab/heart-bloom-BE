package com.heartbloom.be.core.model.domain.bouquet.enumerate;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RelationType {

    MOTHER("엄마"),
    FATHER("아빠"),
    DAUGHTER("딸"),
    SON("아들"),
    NUNA("누나"),
    UNNI("언니"),
    HYUNG("형"),
    OPPA("오빠"),
    ETC("ETC");

    private final String description;

    RelationType (String description) {
        this.description = description;
    }

    public static RelationType findByCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(code))
                .findFirst()
                .orElse(ETC);
    }

}