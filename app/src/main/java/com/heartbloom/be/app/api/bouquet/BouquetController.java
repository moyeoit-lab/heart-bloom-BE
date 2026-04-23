package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUser;
import com.heartbloom.be.app.service.bouquet.BouquetQueryService;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet")
public class BouquetController {

    private final BouquetService bouquetService;
    private final BouquetQueryService bouquetQueryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBouquetResponse>> createBouquet(@RequestBody CreateBouquetRequest request,
                                        @RequestUser AccessUser user) {
        ApiResponse<CreateBouquetResponse> response = ApiResponse.success(bouquetService.create(request, user));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBouquetResponse>>> getBouquets(@RequestUser AccessUser user) {
        List<GetBouquetResponse> results = bouquetQueryService.getBouquets(user);
        ApiResponse<List<GetBouquetResponse>> response = ApiResponse.success(results);
        return ResponseEntity.ok(response);
    }

}