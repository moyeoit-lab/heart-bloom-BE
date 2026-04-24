package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BouquetQueryServiceTest {

    @InjectMocks
    private BouquetQueryService bouquetQueryService;

    @Mock
    private BouquetRepository bouquetRepository;

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
