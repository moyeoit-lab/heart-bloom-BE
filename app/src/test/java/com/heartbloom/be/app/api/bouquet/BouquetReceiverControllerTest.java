package com.heartbloom.be.app.api.bouquet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heartbloom.be.app.api.bouquet.request.CompleteBouquetRequest;
import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetQuestionAnswersResponse;
import com.heartbloom.be.app.api.exception.ExceptionController;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUserArgumentResolver;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import com.heartbloom.be.app.service.question.QuestionService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BouquetReceiverControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BouquetService bouquetService;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private BouquetReceiverController bouquetReceiverController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bouquetReceiverController)
                .setControllerAdvice(new ExceptionController())
                .setCustomArgumentResolvers(new RequestUserArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("수신자용 부케 정보를 조회할 수 있다")
    void getBouquet() throws Exception {
        String token = "test-token";
        GetBouquetForReceiverResponse response = new GetBouquetForReceiverResponse("Sender", "Bouquet", "url", false);
        given(bouquetService.getBouquetForReceiver(token)).willReturn(response);

        mockMvc.perform(get("/api/v1/bouquets/links/{token}", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.senderName").value("Sender"))
                .andExpect(jsonPath("$.data.bouquetName").value("Bouquet"))
                .andExpect(jsonPath("$.data.bouquetImageUrl").value("url"))
                .andExpect(jsonPath("$.data.isCompleted").value(false));
    }

    @Test
    @DisplayName("수신자용 질문 목록을 조회할 수 있다")
    void getQuestions() throws Exception {
        String token = "test-token";
        GetQuestionLandingResponse response = new GetQuestionLandingResponse(List.of());
        given(questionService.getLandingQuestions(token)).willReturn(response);

        mockMvc.perform(get("/api/v1/bouquets/links/{token}/questions", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questions").isArray())
                .andExpect(jsonPath("$.data.questions").isEmpty());
    }

    @Test
    @DisplayName("질문별 답변 목록을 조회할 수 있다")
    void getQuestionAnswers() throws Exception {
        String token = "test-token";
        Long questionId = 1L;
        GetBouquetQuestionAnswersResponse.BouquetAnswerResponse senderAnswer =
                new GetBouquetQuestionAnswersResponse.BouquetAnswerResponse(11L, "Question", null, 1);
        GetBouquetQuestionAnswersResponse response =
                new GetBouquetQuestionAnswersResponse(1L, senderAnswer, null);
        given(bouquetService.getQuestionAnswers(token, questionId)).willReturn(response);

        mockMvc.perform(get("/api/v1/bouquets/links/{token}/questions/{questionId}/answers", token, questionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(1L))
                .andExpect(jsonPath("$.data.senderAnswer.subjectiveContent").value("Question"));
    }

    @Test
    @DisplayName("부케 답변을 제출하고 완료할 수 있다")
    void completeBouquet() throws Exception {
        String token = "test-token";
        CompleteBouquetRequest request = new CompleteBouquetRequest("Receiver", List.of(
                new CreateBouquetAnswerRequest(1L, BouquetAnswerType.SUBJECTIVE, "Answer", 1)
        ));

        mockMvc.perform(post("/api/v1/bouquets/links/{token}/answers", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(bouquetService).completeBouquet(eq(token), eq("Receiver"), any(), any(AccessUser.class));
    }

    @Test
    @DisplayName("부케 소유권을 연결할 수 있다")
    void claimBouquet() throws Exception {
        String token = "test-token";

        mockMvc.perform(post("/api/v1/bouquets/links/{token}/claim", token))
                .andExpect(status().isOk());

        verify(bouquetService).claimBouquet(eq(token), any(AccessUser.class));
    }
}
