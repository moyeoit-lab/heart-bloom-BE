package com.heartbloom.be.core.model.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
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
    private Long senderId;
    private BouquetSenderType senderType;
    private Long receiverId;
    private BouquetReceiverType receiverType;
    private String displayName;
    private RelationType relationType;
    private Long bouquetTypeId;

    private boolean deleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Bouquet create(Long senderId,
                          BouquetSenderType senderType,
                          Long receiverId,
                          BouquetReceiverType receiverType,
                          String displayName,
                          RelationType relationType,
                          Long bouquetTypeId,
                          LocalDateTime now) {
        return new Bouquet(
                null,
                senderId,
                senderType,
                receiverId,
                receiverType,
                displayName,
                relationType,
                bouquetTypeId,
                false,
                null,
                now,
                now
        );
    }

    public Bouquet setReceiver(Long receiverId) {
        return this.toBuilder()
                .receiverId(receiverId)
                .build();
    }

}