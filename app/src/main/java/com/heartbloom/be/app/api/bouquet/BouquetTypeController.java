package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetTypeRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetTypeResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.app.api.contract.BouquetTypeApi;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.service.bouquet.BouquetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet/type")
public class BouquetTypeController implements BouquetTypeApi {

    private final BouquetTypeService bouquetTypeService;

    /**
     * 꽃다발 타입 생성
     */
    @PostMapping
    public ApiResponse<CreateBouquetTypeResponse> createBouquetType(@Validated @RequestBody CreateBouquetTypeRequest request) {
        Long id = bouquetTypeService.createBouquetType(request);
        return ApiResponse.success(CreateBouquetTypeResponse.of(id, request));
    }

    /**
     * 꽃다발 타입 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBouquetTypeResponse>>> getBouquetTypes() {
        List<GetBouquetTypeResponse> result = bouquetTypeService.getBouquetTypes();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 꽃다발 비활성화
     */
    @PostMapping("/{bouquetTypeId}")
    public ApiResponse<Void> disableBouquetType(@PathVariable Long bouquetTypeId) {
        bouquetTypeService.disable(bouquetTypeId);
        return ApiResponse.success(null);
    }

}