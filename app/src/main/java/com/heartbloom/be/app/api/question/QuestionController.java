package com.heartbloom.be.app.api.question;

import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    /**
     * 질문 목록 조회
     */
    @GetMapping("/landing")
    public ResponseEntity<ApiResponse<GetQuestionLandingResponse>> getLandingQuestions() {
        GetQuestionLandingResponse result = questionService.getLandingQuestions();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
