package com.heartbloom.be.app.api.bouquet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartbloom.be.app.api.bouquet.request.CompleteBouquetRequest;
import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.security.access.RequestUserArgumentResolver;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BouquetReceiverControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BouquetService bouquetService;

    @InjectMocks
    private BouquetReceiverController bouquetReceiverController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bouquetReceiverController)
                .setCustomArgumentResolvers(new RequestUserArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("부케 토큰으로 수신자용 부케 정보를 조회할 수 있다")
    void getBouquet() throws Exception {
        // given
        String token = "test-token";
        GetBouquetForReceiverResponse response = new GetBouquetForReceiverResponse("Sender", "Bouquet", "url", List.of());
        given(bouquetService.getBouquetForReceiver(token)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/bouquets/links/{token}", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.senderName").value("Sender"));
    }

    @Test
    @DisplayName("수신자 답변 제출 시 성공 응답을 반환한다")
    void completeBouquet() throws Exception {
        // given
        String token = "test-token";
        CompleteBouquetRequest request = new CompleteBouquetRequest("Receiver", List.of(
                new CreateBouquetAnswerRequest(1L, BouquetAnswerType.SUBJECTIVE, "Answer", 1)
        ));

        // when & then
        mockMvc.perform(post("/api/v1/bouquets/links/{token}/answers", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("소유권 연결(Claim) 요청 시 성공 응답을 반환한다")
    void claimBouquet() throws Exception {
        // given
        String token = "test-token";

        // when & then
        mockMvc.perform(post("/api/v1/bouquets/links/{token}/claim", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
