package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetTypeRequest;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetTypeResponse;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BouquetTypeService {

    private final TimeProvider timeProvider;

    private final BouquetTypeRepository bouquetTypeRepository;

    @Transactional(readOnly = true)
    public List<GetBouquetTypeResponse> getBouquetTypes() {
        List<BouquetType> types = bouquetTypeRepository.findAll();
        return types.stream()
                .map(GetBouquetTypeResponse::of)
                .toList();
    }

    @Transactional
    public Long createBouquetType(CreateBouquetTypeRequest request) {
        LocalDateTime now = timeProvider.now();
        BouquetType newBouquetType = BouquetType.create(request.bouquetName(),
                request.bouquetDescription(),
                request.bouquetImageUrl(),
                request.active(),
                now);
        BouquetType savedNewBouquetType = bouquetTypeRepository.save(newBouquetType);
        return savedNewBouquetType.getId();
    }

    @Transactional
    public Long disable(Long id) {
        LocalDateTime now = timeProvider.now();
        BouquetType bouquetType = bouquetTypeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.TYPE_NOT_FOUND));

        bouquetTypeRepository.save(bouquetType.disable(now));
        return bouquetType.getId();
    }

}