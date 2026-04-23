package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.application.bouquet.implementation.*;
import com.heartbloom.be.app.application.bouquet.implementation.generator.BouquetLinkTokenGenerator;
import com.heartbloom.be.app.application.user.implementation.UserManager;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BouquetErrorCode;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.BouquetType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.model.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final BouquetReceiverManager bouquetReceiverManager;
    private final BouquetTypeManager bouquetTypeManager;
    private final UserManager userManager;

    private final BouquetLinkTokenGenerator linkTokenGenerator;

    @Transactional
    public CreateBouquetResponse create(CreateBouquetRequest request, AccessUser user) {
        LocalDateTime now = timeProvider.now();

        Long userId = user instanceof AuthenticateUser ? user.getId() : null;
        Bouquet bouquet = bouquetManager.create(request, userId);
        List<BouquetAnswer> answers = bouquetAnswerManager.create(bouquet.getId(), userId, request.answers());

        String linkToken = linkTokenGenerator.generate();
        BouquetLink bouquetLink = bouquetLinkManager.create(bouquet.getId(), linkToken, now);

        // BouquetReceiver 생성 (초기에는 userId 없이 이름만 저장)
        bouquetReceiverManager.create(bouquet.getId(), bouquetLink.getId(), null, request.displayName());

        return CreateBouquetResponse.of(bouquet.getId(), linkToken);
    }

    @Transactional(readOnly = true)
    public GetBouquetForReceiverResponse getBouquetForReceiver(String linkToken) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_LINK_NOT_FOUND));

        if (link.getStatus() == BouquetLinkStatus.EXPIRED) {
            throw new ServiceException(BouquetErrorCode.BOUQUET_LINK_EXPIRED);
        }

        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_NOT_FOUND));

        User sender = userManager.findById(bouquet.getUserId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_NOT_FOUND));

        BouquetType bouquetType = bouquetTypeManager.findById(bouquet.getBouquetTypeId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_TYPE_NOT_FOUND));

        List<BouquetAnswer> senderAnswers = bouquetAnswerManager.findByBouquetId(bouquet.getId());

        return GetBouquetForReceiverResponse.of(sender.getName(), bouquetType, senderAnswers);
    }

    @Transactional
    public void completeBouquet(String linkToken, List<CreateBouquetAnswerRequest> answers, AccessUser user) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_LINK_NOT_FOUND));

        if (link.getStatus() == BouquetLinkStatus.COMPLETED) {
            throw new ServiceException(BouquetErrorCode.BOUQUET_LINK_ALREADY_COMPLETED);
        }

        BouquetReceiver receiver = bouquetReceiverManager.findByBouquetLinkId(link.getId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_RECEIVER_NOT_FOUND));

        Long userId = user instanceof AuthenticateUser ? user.getId() : null;

        // 수신자 답변 저장
        bouquetAnswerManager.createReceiverAnswers(link.getBouquetId(), userId, receiver.getId(), answers);

        // 로그인 상태라면 수신자 정보에 userId 연결
        if (userId != null) {
            bouquetReceiverManager.connectUser(receiver, userId);
        }

        // 링크 상태 완료로 변경
        bouquetLinkManager.complete(link);
    }

    @Transactional
    public void claimBouquet(String linkToken, AccessUser user) {
        if (!(user instanceof AuthenticateUser)) {
            return;
        }

        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_LINK_NOT_FOUND));

        BouquetReceiver receiver = bouquetReceiverManager.findByBouquetLinkId(link.getId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.BOUQUET_RECEIVER_NOT_FOUND));

        if (receiver.getUserId() == null) {
            bouquetReceiverManager.connectUser(receiver, user.getId());
        }
    }

}
