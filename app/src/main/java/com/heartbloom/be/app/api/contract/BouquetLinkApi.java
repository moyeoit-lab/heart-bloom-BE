package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetLinkUrlResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Bouquet Link", description = "꽃다발 링크 API")
public interface BouquetLinkApi {

    @Operation(summary = "꽃다발 링크 URL 조회", description = "꽃다발 ID로 공유 가능한 링크 URL을 조회합니다.")
    ResponseEntity<ApiResponse<GetBouquetLinkUrlResponse>> getBouquetLinkUrl(
            @Parameter(description = "꽃다발 ID", example = "1") Long bouquetId
    );

}
