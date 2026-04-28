package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.bouquet.request.CompleteBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetQuestionAnswersResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Bouquet Receiver", description = "수신자용 꽃다발 API")
public interface BouquetReceiverApi {

    @Operation(summary = "수신자용 꽃다발 조회", description = "공유 링크 토큰으로 수신자에게 보여줄 꽃다발 정보를 조회합니다.")
    ResponseEntity<ApiResponse<GetBouquetForReceiverResponse>> getBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") String token
    );

    @Operation(summary = "질문별 답변 조회", description = "공유 링크 토큰과 질문 ID로 발신자/수신자 답변을 조회합니다.")
    ResponseEntity<ApiResponse<GetBouquetQuestionAnswersResponse>> getQuestionAnswers(
            @Parameter(description = "꽃다발 공유 링크 토큰") String token,
            @Parameter(description = "질문 ID", example = "1") Long questionId
    );

    @Operation(summary = "수신자 답변 제출", description = "수신자 이름과 답변을 저장하고 꽃다발 링크를 완료 처리합니다.")
    ResponseEntity<ApiResponse<Void>> completeBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") String token,
            CompleteBouquetRequest request,
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "꽃다발 소유권 연결", description = "비로그인으로 답변 완료한 꽃다발을 로그인 사용자와 연결합니다.")
    ResponseEntity<ApiResponse<Void>> claimBouquet(
            @Parameter(description = "꽃다발 공유 링크 토큰") String token,
            @Parameter(hidden = true) AccessUser user
    );

}
