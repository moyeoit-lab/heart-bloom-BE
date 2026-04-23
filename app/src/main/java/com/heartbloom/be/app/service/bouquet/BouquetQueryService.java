package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BouquetQueryService {

    private final BouquetRepository bouquetRepository;

    public List<GetBouquetResponse> getBouquets(AccessUser user) {
        return bouquetRepository.queryBouquets(user.getId())
                .stream()
                .map(GetBouquetResponse::of)
                .toList();
    }

}