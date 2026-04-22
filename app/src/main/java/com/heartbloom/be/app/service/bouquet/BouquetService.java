package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetLinkManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetManager;
import com.heartbloom.be.app.application.bouquet.implementation.BouquetAnswerManager;
import com.heartbloom.be.app.application.bouquet.implementation.generator.BouquetLinkTokenGenerator;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BouquetService {

    private final TimeProvider timeProvider;

    private final BouquetManager bouquetManager;
    private final BouquetAnswerManager bouquetAnswerManager;
    private final BouquetLinkManager bouquetLinkManager;

    private final BouquetLinkTokenGenerator linkTokenGenerator;

    public CreateBouquetResponse create(CreateBouquetRequest request, AccessUser user) {
        LocalDateTime now = timeProvider.now();

        Bouquet bouquet = bouquetManager.create(request, user.getId());
        List<BouquetAnswer> answers = bouquetAnswerManager.create(bouquet.getId(), user.getId(), request.answers());

        String linkToken = linkTokenGenerator.generate();
        BouquetLink bouquetLink = bouquetLinkManager.create(bouquet.getId(), linkToken, now);

        return CreateBouquetResponse.of(bouquet.getId(), linkToken);
    }

}