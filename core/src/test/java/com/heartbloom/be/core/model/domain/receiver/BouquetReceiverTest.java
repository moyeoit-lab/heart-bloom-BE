package com.heartbloom.be.core.model.domain.receiver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BouquetReceiverTest {

    @Test
    @DisplayName("비로그인 상태에서 부케 수신자를 생성하면 userId는 null이어야 한다")
    void create_without_user() {
        // given
        Long bouquetId = 1L;
        Long bouquetLinkId = 10L;
        String receiverName = "수신자";
        LocalDateTime now = LocalDateTime.now();

        // when
        BouquetReceiver receiver = BouquetReceiver.create(bouquetId, bouquetLinkId, null, receiverName, now);

        // then
        assertThat(receiver.getUserId()).isNull();
        assertThat(receiver.getReceiverName()).isEqualTo(receiverName);
        assertThat(receiver.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("로그인 상태에서 부케 수신자를 생성하면 userId가 정상적으로 할당되어야 한다")
    void create_with_user() {
        // given
        Long bouquetId = 1L;
        Long bouquetLinkId = 10L;
        Long userId = 100L;
        String receiverName = "수신자";
        LocalDateTime now = LocalDateTime.now();

        // when
        BouquetReceiver receiver = BouquetReceiver.create(bouquetId, bouquetLinkId, userId, receiverName, now);

        // then
        assertThat(receiver.getUserId()).isEqualTo(userId);
    }
}
