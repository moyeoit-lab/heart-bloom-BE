package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.service.bouquet.BouquetTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet/type")
public class BouquetTypeController {

    private final BouquetTypeService bouquetTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBouquetTypeResponse>>> getBouquetTypes() {
        List<GetBouquetTypeResponse> result = bouquetTypeService.getBouquetTypes();
        return ResponseEntity.ok(ApiResponse.success(result));
    }

}