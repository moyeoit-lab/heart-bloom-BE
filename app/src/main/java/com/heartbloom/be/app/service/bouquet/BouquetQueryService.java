package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.response.GetBouquetCountResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetDisplayStandResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetResponse;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BouquetQueryService {

    private final BouquetRepository bouquetRepository;

    @Transactional(readOnly = true)
    public GetBouquetDisplayStandResponse getBouquetDisplayStand(AccessUser user) {
        // 내가 보낸 부케 목록 조회
        List<GetBouquetResponse> sent = bouquetRepository.querySentBouquets(user.getId(), BouquetSenderType.USER)
                .stream()
                .map(GetBouquetResponse::of)
                .toList();

        // 내가 받은 부케 목록 조회
        List<GetBouquetResponse> received = bouquetRepository.queryReceivedBouquets(user.getId(), BouquetReceiverType.USER)
                .stream()
                .map(GetBouquetResponse::of)
                .toList();

        return new GetBouquetDisplayStandResponse(user.getName(), sent, received);
    }

    @Transactional(readOnly = true)
    public GetBouquetCountResponse getBouquetCount() {
        return new GetBouquetCountResponse(bouquetRepository.countAll());
    }

}
