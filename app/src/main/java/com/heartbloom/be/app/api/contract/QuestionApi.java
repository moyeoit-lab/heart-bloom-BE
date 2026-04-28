package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Question", description = "질문 API")
public interface QuestionApi {

    @Operation(summary = "랜딩 질문 목록 조회", description = "꽃다발 작성 화면에서 사용할 질문 목록을 조회합니다.")
    @GetMapping("/landing")
    ResponseEntity<ApiResponse<GetQuestionLandingResponse>> getLandingQuestions();

}
