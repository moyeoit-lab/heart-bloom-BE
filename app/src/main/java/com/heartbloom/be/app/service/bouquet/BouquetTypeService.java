package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BouquetTypeService {

    private final BouquetTypeRepository bouquetTypeRepository;

    public List<GetBouquetTypeResponse> getBouquetTypes() {
        List<BouquetType> types = bouquetTypeRepository.findAll();
        return types.stream()
                .map(GetBouquetTypeResponse::of)
                .toList();
    }

}