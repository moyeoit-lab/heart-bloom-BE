package com.heartbloom.be.app.api.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.exception.response.ApiResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.RequestUser;
import com.heartbloom.be.app.service.bouquet.BouquetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bouquet")
public class BouquetController {

    private final BouquetService bouquetService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateBouquetResponse>> createBouquet(@RequestBody CreateBouquetRequest request,
                                        @RequestUser AccessUser user) {
        ApiResponse<CreateBouquetResponse> response = ApiResponse.success(bouquetService.create(request, user));
        return ResponseEntity.ok(response);
    }

}