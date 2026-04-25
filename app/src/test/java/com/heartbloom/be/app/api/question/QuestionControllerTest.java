package com.heartbloom.be.app.api.question;

import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }

    @Test
    @DisplayName("질문 목록을 조회할 수 있다")
    void getLandingQuestions() throws Exception {
        // given
        Question requiredQuestion = Question.builder()
                .id(1L)
                .title("가족에게 가장 듣고 싶은 말은?")
                .answerType(QuestionAnswerType.REQUIRED)
                .build();
        Question optionalQuestion = Question.builder()
                .id(2L)
                .title("선택 질문")
                .answerType(QuestionAnswerType.OPTIONAL)
                .build();
        given(questionService.getLandingQuestions())
                .willReturn(GetQuestionLandingResponse.from(List.of(requiredQuestion, optionalQuestion)));

        // when & then
        mockMvc.perform(get("/api/v1/questions/landing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.questions[0].questionId").value(1))
                .andExpect(jsonPath("$.data.questions[0].title").value("가족에게 가장 듣고 싶은 말은?"))
                .andExpect(jsonPath("$.data.questions[0].answerType").value("REQUIRED"))
                .andExpect(jsonPath("$.data.questions[1].questionId").value(2))
                .andExpect(jsonPath("$.data.questions[1].title").value("선택 질문"))
                .andExpect(jsonPath("$.data.questions[1].answerType").value("OPTIONAL"));
    }
}
