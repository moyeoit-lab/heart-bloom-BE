package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetLinkUrlResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.service.bouquet.BouquetLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet/link")
public class BouquetLinkController {

    private final BouquetLinkService bouquetLinkService;

    /**
     * 꽃다발 링크 URL 조회
     */
    @GetMapping("/{bouquetId}/url")
    public ResponseEntity<ApiResponse<GetBouquetLinkUrlResponse>> getBouquetLinkUrl(@PathVariable Long bouquetId) {
        String url = bouquetLinkService.getBouquetLinkUrl(bouquetId);
        ApiResponse<GetBouquetLinkUrlResponse> response = ApiResponse.success(new GetBouquetLinkUrlResponse(url));
        return ResponseEntity.ok(response);
    }

}