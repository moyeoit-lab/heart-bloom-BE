package com.heartbloom.be.core.model.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.RelationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Bouquet {

    private Long id;
    private Long userId;
    private String displayName;
    private RelationType relationType;
    private Long bouquetTypeId;

    private boolean deleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Bouquet create(Long userId,
                          String displayName,
                          RelationType relationType,
                          Long bouquetTypeId,
                          LocalDateTime now) {
        return new Bouquet(
                null,
                userId,
                displayName,
                relationType,
                bouquetTypeId,
                false,
                null,
                now,
                now
        );
    }

}