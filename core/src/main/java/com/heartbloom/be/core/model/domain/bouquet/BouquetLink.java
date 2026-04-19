package com.heartbloom.be.core.model.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class BouquetLink {

    private Long id;
    private Long bouquetId;
    private String linkToken;
    private BouquetLinkStatus status;
    private LocalDateTime expiredAt;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BouquetLink create(Long bouquetId,
                              String linkToken,
                              BouquetLinkStatus status,
                              LocalDateTime expiredAt,
                              LocalDateTime now) {
        return new BouquetLink(
                null,
                bouquetId,
                linkToken,
                status,
                expiredAt,
                now,
                now
        );
    }

}