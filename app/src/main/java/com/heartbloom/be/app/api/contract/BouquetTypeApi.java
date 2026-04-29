package com.heartbloom.be.app.api.contract;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetTypeRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetTypeResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Bouquet Type", description = "꽃다발 타입 API")
public interface BouquetTypeApi {

    @Operation(summary = "꽃다발 타입 목록 조회", description = "활성화된 꽃다발 타입 목록을 조회합니다.")
    @GetMapping
    ResponseEntity<ApiResponse<List<GetBouquetTypeResponse>>> getBouquetTypes();

    @Operation(summary = "꽃다발 타입 생성", description = "꽃다발 타입을 생성합니다.")
    ApiResponse<CreateBouquetTypeResponse> createBouquetType(CreateBouquetTypeRequest request);

    @Operation(summary = "꽃다발 타입 비활성화", description = "ID 기반으로 꽃다발 타입을 비활성화합니다.")
    ApiResponse<Void> disableBouquetType(Long bouquetTypeId);

}
