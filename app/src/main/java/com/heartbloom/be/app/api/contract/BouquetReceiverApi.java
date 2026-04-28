package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.bouquet.request.CompleteBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetQuestionAnswersResponse;
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

@Tag(name = "Bouquet Receiver", description = "수신자용 꽃다발 API")
public interface BouquetReceiverApi {

    @Operation(summary = "수신자용 꽃다발 조회", description = "공유 링크 토큰으로 수신자에게 보여줄 꽃다발 정보를 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<GetBouquetForReceiverResponse>> getBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") @PathVariable String token
    );

    @Operation(
            summary = "수신자용 꽃다발 질문 목록 조회",
            description = "공유 링크로 진입한 비로그인 수신자가 link token만으로 해당 꽃다발 타입의 질문 목록과 옵션을 조회합니다. 링크가 만료되면 LINK_EXPIRED를 반환합니다."
    )
    @GetMapping("/questions")
    ResponseEntity<ApiResponse<GetQuestionLandingResponse>> getQuestions(
            @Parameter(description = "꽃다발 공유 링크 토큰") @PathVariable String token
    );

    @Operation(summary = "질문별 답변 조회", description = "공유 링크 토큰과 질문 ID로 발신자/수신자 답변을 조회합니다.")
    @GetMapping("/questions/{questionId}/answers")
    ResponseEntity<ApiResponse<GetBouquetQuestionAnswersResponse>> getQuestionAnswers(
            @Parameter(description = "꽃다발 공유 링크 토큰") @PathVariable String token,
            @Parameter(description = "질문 ID", example = "1") @PathVariable Long questionId
    );

    @Operation(summary = "수신자 답변 제출", description = "수신자 이름과 답변을 저장하고 꽃다발 링크를 완료 처리합니다.")
    @PostMapping("/answers")
    ResponseEntity<ApiResponse<Void>> completeBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") @PathVariable String token,
            @RequestBody CompleteBouquetRequest request,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "꽃다발 소유권 연결", description = "비로그인으로 답변 완료한 꽃다발을 로그인 사용자와 연결합니다.")
    @PostMapping("/claim")
    ResponseEntity<ApiResponse<Void>> claimBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") @PathVariable String token,
            @Parameter(hidden = true) AccessUser user
    );

}
