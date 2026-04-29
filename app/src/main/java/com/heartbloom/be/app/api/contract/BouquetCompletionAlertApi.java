package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.notification.response.GetBouquetCompletionAlertsResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Bouquet Completion Alert", description = "완성 꽃다발 확인 알림 API")
public interface BouquetCompletionAlertApi {

    @Operation(summary = "완성 꽃다발 확인 알림 목록 조회", description = "로그인 사용자가 아직 확인하지 않은 완성 꽃다발 알림 목록을 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<GetBouquetCompletionAlertsResponse>> getBouquetCompletionAlerts(
            @Parameter(hidden = true) AccessUser user
    );

    @Operation(summary = "완성 꽃다발 확인 알림 읽음 처리", description = "완성 꽃다발 확인 알림을 읽음 처리하여 다시 노출되지 않도록 합니다.")
    @PatchMapping("/{alertId}/read")
    ResponseEntity<ApiResponse<Void>> readBouquetCompletionAlert(
            @Parameter(description = "완성 꽃다발 확인 알림 ID", example = "1") @PathVariable Long alertId,
            @Parameter(hidden = true) AccessUser user
    );

}
