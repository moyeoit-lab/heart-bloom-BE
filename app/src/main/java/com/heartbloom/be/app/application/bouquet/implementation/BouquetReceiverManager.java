package com.heartbloom.be.app.application.bouquet.implementation;

import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.repository.domain.receiver.BouquetReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BouquetReceiverManager {

    private final TimeProvider timeProvider;
    private final BouquetReceiverRepository bouquetReceiverRepository;

    public BouquetReceiver create(Long bouquetId, Long bouquetLinkId, Long userId, String receiverName) {
        LocalDateTime now = timeProvider.now();
        BouquetReceiver bouquetReceiver = BouquetReceiver.create(
                bouquetId,
                bouquetLinkId,
                userId,
                receiverName,
                now
        );
        return bouquetReceiverRepository.save(bouquetReceiver);
    }

    public Optional<BouquetReceiver> findByBouquetId(Long bouquetId) {
        return bouquetReceiverRepository.findByBouquetId(bouquetId);
    }

    public Optional<BouquetReceiver> findById(Long id) {
        return bouquetReceiverRepository.findById(id);
    }

    public Optional<BouquetReceiver> findByBouquetLinkId(Long bouquetLinkId) {
        return bouquetReceiverRepository.findByBouquetLinkId(bouquetLinkId);
    }

    public BouquetReceiver updateReceiverName(BouquetReceiver receiver, String receiverName) {
        BouquetReceiver updatedReceiver = receiver.toBuilder()
                .receiverName(receiverName)
                .modifiedAt(timeProvider.now())
                .build();
        return bouquetReceiverRepository.save(updatedReceiver);
    }

    public BouquetReceiver connectUser(BouquetReceiver receiver, Long userId) {
        BouquetReceiver updatedReceiver = receiver.toBuilder()
                .userId(userId)
                .modifiedAt(timeProvider.now())
                .build();
        return bouquetReceiverRepository.save(updatedReceiver);
    }
}
