package com.heartbloom.be.core.model.domain.receiver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class BouquetReceiver {

    private Long id;
    private Long bouquetId;
    private Long bouquetLinkId;
    private String receiverName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BouquetReceiver create(Long bouquetId,
                                  Long bouquetLinkId,
                                  String receiverName,
                                  LocalDateTime now) {
        return new BouquetReceiver(
                null,
                bouquetId,
                bouquetLinkId,
                receiverName,
                now,
                now
        );
    }

}
