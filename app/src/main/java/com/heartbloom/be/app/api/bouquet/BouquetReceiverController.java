package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CompleteBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUser;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquets/links/{token}")
public class BouquetReceiverController {

    private final BouquetService bouquetService;

    /* 수신자용 부케 정보 및 질문 조회 (비로그인 허용) */
    @GetMapping
    public ResponseEntity<ApiResponse<GetBouquetForReceiverResponse>> getBouquet(@PathVariable String token) {
        ApiResponse<GetBouquetForReceiverResponse> response = ApiResponse.success(bouquetService.getBouquetForReceiver(token));
        return ResponseEntity.ok(response);
    }

    /* 부케 답변 제출 및 완료 (비로그인 허용, 로그인 시 자동 연결) */
    @PostMapping("/answers")
    public ResponseEntity<ApiResponse<Void>> completeBouquet(@PathVariable String token,
                                                            @RequestBody CompleteBouquetRequest request,
                                                            @RequestUser AccessUser user) {
        bouquetService.completeBouquet(token, request.receiverName(), request.answers(), user);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /* 비로그인 상태 답변 완료 후 소유권 연결 (로그인 필수) */
    @PostMapping("/claim")
    public ResponseEntity<ApiResponse<Void>> claimBouquet(@PathVariable String token,
                                                         @RequestUser AccessUser user) {
        bouquetService.claimBouquet(token, user);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
