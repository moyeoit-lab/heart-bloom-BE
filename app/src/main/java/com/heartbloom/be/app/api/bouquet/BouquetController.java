package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetDisplayStandResponse;
import com.heartbloom.be.app.api.contract.BouquetApi;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.question.response.GetQuestionLandingResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUser;
import com.heartbloom.be.app.service.bouquet.BouquetQueryService;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import com.heartbloom.be.app.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet")
public class BouquetController implements BouquetApi {

    private final BouquetService bouquetService;
    private final BouquetQueryService bouquetQueryService;
    private final QuestionService questionService;

    /**
     * 꽃다발 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CreateBouquetResponse>> createBouquet(@RequestBody CreateBouquetRequest request,
                                        @RequestUser AccessUser user) {
        CreateBouquetResponse result = bouquetService.create(request, user);
        ApiResponse<CreateBouquetResponse> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    /**
     * 꽃다발 진열대 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<GetBouquetDisplayStandResponse>> getBouquetsOfSent(@RequestUser AccessUser user) {
        GetBouquetDisplayStandResponse results = bouquetQueryService.getBouquetDisplayStand(user);
        ApiResponse<GetBouquetDisplayStandResponse> response = ApiResponse.success(results);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<GetBouquetCountResponse>> getBouquetCount() {
        GetBouquetCountResponse result = bouquetQueryService.getBouquetCount();
        ApiResponse<GetBouquetCountResponse> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bouquetId}/questions")
    public ResponseEntity<ApiResponse<GetQuestionLandingResponse>> getBouquetQuestions(@PathVariable Long bouquetId,
                                                                                      @RequestUser AccessUser user) {
        GetQuestionLandingResponse result = questionService.getBouquetQuestions(bouquetId, user);
        ApiResponse<GetQuestionLandingResponse> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

}
