package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.app.api.contract.BouquetTypeApi;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.service.bouquet.BouquetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet/type")
public class BouquetTypeController implements BouquetTypeApi {

    private final BouquetTypeService bouquetTypeService;

    /**
     * 꽃다발 타입 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBouquetTypeResponse>>> getBouquetTypes() {
        List<GetBouquetTypeResponse> result = bouquetTypeService.getBouquetTypes();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
