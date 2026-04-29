package com.heartbloom.be.app.api.notification;

import com.heartbloom.be.app.api.contract.BouquetCompletionAlertApi;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.api.notification.response.GetBouquetCompletionAlertsResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUser;
import com.heartbloom.be.app.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet-completion-alerts")
public class BouquetCompletionAlertController implements BouquetCompletionAlertApi {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<GetBouquetCompletionAlertsResponse>> getBouquetCompletionAlerts(
            @RequestUser AccessUser user
    ) {
        GetBouquetCompletionAlertsResponse result = notificationService.getBouquetCompletionAlerts(user);
        ApiResponse<GetBouquetCompletionAlertsResponse> response = ApiResponse.success(result);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{alertId}/read")
    public ResponseEntity<ApiResponse<Void>> readBouquetCompletionAlert(@PathVariable Long alertId,
                                                                        @RequestUser AccessUser user) {
        notificationService.readBouquetCompletionAlert(alertId, user);
        ApiResponse<Void> response = ApiResponse.success(null);
        return ResponseEntity.ok(response);
    }

}
