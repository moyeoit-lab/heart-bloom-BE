package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.security.access.RequestUserArgumentResolver;
import com.heartbloom.be.app.service.bouquet.BouquetQueryService;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import com.heartbloom.be.app.service.question.QuestionService;
import com.heartbloom.be.core.model.domain.question.Question;
import com.heartbloom.be.core.model.domain.question.enumerate.QuestionAnswerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Mock
    private QuestionService questionService;

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

    @Test
    @DisplayName("내 부케 질문 목록을 조회할 수 있다")
    void getBouquetQuestions() throws Exception {
        // given
        Question question = Question.builder()
                .id(1L)
                .title("처음 단둘이 있었던 때")
                .answerType(QuestionAnswerType.REQUIRED)
                .build();
        given(questionService.getBouquetQuestions(eq(1L), any()))
                .willReturn(GetQuestionLandingResponse.from(List.of(question)));

        // when & then
        mockMvc.perform(get("/api/v1/bouquet/{bouquetId}/questions", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.questions[0].questionId").value(1L))
                .andExpect(jsonPath("$.data.questions[0].title").value("처음 단둘이 있었던 때"));
    }
}
