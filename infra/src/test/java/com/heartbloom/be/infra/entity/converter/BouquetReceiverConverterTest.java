package com.heartbloom.be.infra.entity.converter;

import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.infra.entity.domain.receiver.BouquetReceiverEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BouquetReceiverConverterTest {

    @Test
    @DisplayName("도메인 모델을 엔티티로 변환할 때 userId가 누락되지 않아야 한다")
    void toEntity() {
        // given
        Long userId = 100L;
        BouquetReceiver model = BouquetReceiver.builder()
                .id(1L)
                .bouquetId(10L)
                .bouquetLinkId(20L)
                .userId(userId)
                .receiverName("수신자")
                .build();

        // when
        BouquetReceiverEntity entity = BouquetReceiverConverter.toEntity(model);

        // then
        assertThat(entity.getUserId()).isEqualTo(userId);
        assertThat(entity.getReceiverName()).isEqualTo(model.getReceiverName());
    }

    @Test
    @DisplayName("엔티티를 도메인 모델로 변환할 때 userId가 정상적으로 반영되어야 한다")
    void toModel() {
        // given
        Long userId = 100L;
        BouquetReceiverEntity entity = BouquetReceiverEntity.builder()
                .id(1L)
                .bouquetId(10L)
                .bouquetLinkId(20L)
                .userId(userId)
                .receiverName("수신자")
                .build();

        // when
        BouquetReceiver model = BouquetReceiverConverter.toModel(entity);

        // then
        assertThat(model.getUserId()).isEqualTo(userId);
    }
}
