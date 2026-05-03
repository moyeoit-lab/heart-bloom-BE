package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetLinkUrlResponse;
import com.heartbloom.be.app.api.contract.BouquetLinkApi;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.service.bouquet.BouquetLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet/link")
public class BouquetLinkController implements BouquetLinkApi {

    private final BouquetLinkService bouquetLinkService;

    /**
     * 꽃다발 링크 URL 조회
     */
    @GetMapping("/{bouquetId}/url")
    public ResponseEntity<ApiResponse<GetBouquetLinkUrlResponse>> getBouquetLinkUrl(@PathVariable Long bouquetId,
                                                                                    @RequestHeader(value = "Origin", required = false) String origin) {
        String url = bouquetLinkService.getBouquetLinkUrl(bouquetId, origin);
        ApiResponse<GetBouquetLinkUrlResponse> response = ApiResponse.success(new GetBouquetLinkUrlResponse(url));
        return ResponseEntity.ok(response);
    }

}
