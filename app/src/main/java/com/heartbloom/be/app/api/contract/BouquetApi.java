package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetDisplayStandResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Bouquet", description = "꽃다발 API")
public interface BouquetApi {

    @Operation(summary = "꽃다발 생성", description = "꽃다발 정보와 발신자 답변을 저장하고 공유 링크 토큰을 발급합니다.")
    @PostMapping
    ResponseEntity<ApiResponse<CreateBouquetResponse>> createBouquet(
            @RequestBody CreateBouquetRequest request,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "꽃다발 진열대 조회", description = "사용자가 보낸 꽃다발과 받은 꽃다발 목록을 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<GetBouquetDisplayStandResponse>> getBouquetsOfSent(
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "꽃다발 개수 조회", description = "서비스에 생성된 꽃다발 개수를 조회합니다.")
    @GetMapping("/count")
    ResponseEntity<ApiResponse<GetBouquetCountResponse>> getBouquetCount();

    @Operation(summary = "내 꽃다발 질문 목록 조회", description = "로그인 사용자가 접근 가능한 꽃다발의 질문 목록과 옵션을 조회합니다.")
    @GetMapping("/{bouquetId}/questions")
    ResponseEntity<ApiResponse<GetQuestionLandingResponse>> getBouquetQuestions(
            @Parameter(description = "꽃다발 ID", example = "1") @PathVariable Long bouquetId,
            @Parameter(hidden = true) AccessUser user
    );

}
