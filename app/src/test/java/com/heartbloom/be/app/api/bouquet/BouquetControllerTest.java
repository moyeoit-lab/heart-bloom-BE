package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.security.access.RequestUserArgumentResolver;
import com.heartbloom.be.app.service.bouquet.BouquetQueryService;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BouquetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BouquetService bouquetService;

    @Mock
    private BouquetQueryService bouquetQueryService;

    @InjectMocks
    private BouquetController bouquetController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bouquetController)
                .setCustomArgumentResolvers(new RequestUserArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("전체 부케 개수를 조회할 수 있다")
    void getBouquetCount() throws Exception {
        // given
        given(bouquetQueryService.getBouquetCount()).willReturn(new GetBouquetCountResponse(123L));

        // when & then
        mockMvc.perform(get("/api/v1/bouquet/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.count").value(123));
    }
}
