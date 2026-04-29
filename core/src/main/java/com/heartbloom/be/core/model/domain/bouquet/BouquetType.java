package com.heartbloom.be.core.model.domain.bouquet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class BouquetType {

    private Long id;
    private String bouquetName;
    private String bouquetDescription;
    private String bouquetImageUrl;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BouquetType create(String bouquetName,
                              String bouquetDescription,
                              String bouquetImageUrl,
                              boolean active,
                              LocalDateTime now) {
        return new BouquetType(
                null,
                bouquetName,
                bouquetDescription,
                bouquetImageUrl,
                active,
                now,
                now
        );
    }

    public BouquetType disable(LocalDateTime now) {
        return this.toBuilder()
                .active(false)
                .modifiedAt(now)
                .build();
    }

}