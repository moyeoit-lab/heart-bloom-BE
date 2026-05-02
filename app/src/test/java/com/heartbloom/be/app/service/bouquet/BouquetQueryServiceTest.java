package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetDisplayStandResponse;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BouquetQueryServiceTest {

    @InjectMocks
    private BouquetQueryService bouquetQueryService;

    @Mock
    private BouquetRepository bouquetRepository;

    @Test
    @DisplayName("꽃다발 진열대 조회 시 현재 사용자 기준 답변 상태를 반환한다")
    void getBouquetDisplayStandWithAnswerStatus() {
        // given
        AuthenticateUser user = new AuthenticateUser(1L, "하트", "heart@test.com", false);
        GetBouquetQueryDto sentBouquet = new GetBouquetQueryDto(
                10L,
                1L,
                BouquetSenderType.USER,
                2L,
                BouquetReceiverType.USER,
                "보낸 꽃다발",
                100L,
                "장미",
                "설명",
                "image-url",
                true,
                false
        );
        GetBouquetQueryDto receivedBouquet = new GetBouquetQueryDto(
                20L,
                2L,
                BouquetSenderType.USER,
                1L,
                BouquetReceiverType.USER,
                "받은 꽃다발",
                100L,
                "장미",
                "설명",
                "image-url",
                false,
                true
        );

        given(bouquetRepository.querySentBouquets(1L, BouquetSenderType.USER)).willReturn(List.of(sentBouquet));
        given(bouquetRepository.queryReceivedBouquets(1L, BouquetReceiverType.USER)).willReturn(List.of(receivedBouquet));

        // when
        GetBouquetDisplayStandResponse response = bouquetQueryService.getBouquetDisplayStand(user);

        // then
        assertThat(response.sentBouquets()).hasSize(1);
        assertThat(response.sentBouquets().getFirst().myAnswered()).isTrue();
        assertThat(response.sentBouquets().getFirst().counterpartAnswered()).isFalse();
        assertThat(response.sentBouquets().getFirst().answerStatus().name())
                .isEqualTo("WAITING_FOR_COUNTERPART_ANSWER");

        assertThat(response.receivedBouquets()).hasSize(1);
        assertThat(response.receivedBouquets().getFirst().myAnswered()).isFalse();
        assertThat(response.receivedBouquets().getFirst().counterpartAnswered()).isTrue();
        assertThat(response.receivedBouquets().getFirst().answerStatus().name())
                .isEqualTo("WAITING_FOR_MY_ANSWER");
    }

    @Test
    @DisplayName("삭제 여부와 관계없이 전체 부케 개수를 반환한다")
    void getBouquetCount() {
        // given
        given(bouquetRepository.countAll()).willReturn(123L);

        // when
        GetBouquetCountResponse response = bouquetQueryService.getBouquetCount();

        // then
        assertThat(response.count()).isEqualTo(123L);
    }
}
