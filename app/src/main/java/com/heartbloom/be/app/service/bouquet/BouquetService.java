package com.heartbloom.be.app.service.bouquet;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.app.api.bouquet.request.CreateBouquetRequest;
import com.heartbloom.be.app.api.bouquet.response.CreateBouquetResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetForReceiverResponse;
import com.heartbloom.be.app.api.bouquet.response.GetBouquetQuestionAnswersResponse;
import com.heartbloom.be.app.application.bouquet.implementation.*;
import com.heartbloom.be.app.application.bouquet.implementation.generator.BouquetLinkTokenGenerator;
import com.heartbloom.be.app.application.notification.implementation.NotificationManager;
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
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.model.domain.receiver.BouquetReceiver;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
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
    private final NotificationManager notificationManager;
    private final BouquetRepository bouquetRepository;

    private final BouquetLinkTokenGenerator linkTokenGenerator;

    @Transactional
    public CreateBouquetResponse create(CreateBouquetRequest request, AccessUser user) {
        LocalDateTime now = timeProvider.now();
        Long currentUserId = user.getId();

        // 1. 임시 토큰 생성
        String linkToken = linkTokenGenerator.generate();

        // 2. Bouquet 생성 (회원 -> 비회원 기본 케이스)
        Bouquet bouquet = bouquetManager.create(
                request,
                currentUserId, BouquetSenderType.USER,
                null, BouquetReceiverType.GUEST
        );

        // 3. 답변 저장
        bouquetAnswerManager.create(bouquet.getId(), currentUserId, request.answers());

        // 4. 링크 생성
        BouquetLink bouquetLink = bouquetLinkManager.create(bouquet.getId(), linkToken, now);

        // 5. BouquetReceiver 생성 (익명 프로필)
        bouquetReceiverManager.create(bouquet.getId(), bouquetLink.getId(), null, null);

        return CreateBouquetResponse.of(bouquet.getId(), linkToken);
    }

    /**
     * 답례 부케 생성 로직 (수신자 -> 발신자)
     */
    @Transactional
    public CreateBouquetResponse reply(String originalLinkToken, CreateBouquetRequest request, AccessUser user) {
        LocalDateTime now = timeProvider.now();
        
        BouquetLink originalLink = bouquetLinkManager.findByToken(originalLinkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));
        Bouquet originalBouquet = bouquetManager.findById(originalLink.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));
        BouquetReceiver originalReceiver = bouquetReceiverManager.findByBouquetLinkId(originalLink.getId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.RECEIVER_NOT_FOUND));

        String newLinkToken = linkTokenGenerator.generate();

        Bouquet replyBouquet = bouquetManager.create(
                request,
                originalReceiver.getId(), BouquetSenderType.GUEST,
                originalBouquet.getSenderId(), BouquetReceiverType.USER
        );

        bouquetAnswerManager.create(replyBouquet.getId(), null, request.answers());
        bouquetLinkManager.create(replyBouquet.getId(), newLinkToken, now);

        return CreateBouquetResponse.of(replyBouquet.getId(), newLinkToken);
    }

    @Transactional(readOnly = true)
    public GetBouquetForReceiverResponse getBouquetForReceiver(String linkToken) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));

        if (link.getStatus() == BouquetLinkStatus.EXPIRED) {
            throw new ServiceException(BouquetErrorCode.LINK_EXPIRED);
        }

        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));

        BouquetType bouquetType = bouquetTypeManager.findById(bouquet.getBouquetTypeId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.TYPE_NOT_FOUND));

        return GetBouquetForReceiverResponse.of(bouquet.getDisplayName(), bouquetType);
    }

    @Transactional(readOnly = true)
    public GetBouquetQuestionAnswersResponse getQuestionAnswers(String linkToken, Long questionId) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));

        if (link.getStatus() == BouquetLinkStatus.EXPIRED) {
            throw new ServiceException(BouquetErrorCode.LINK_EXPIRED);
        }

        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));

        List<BouquetAnswer> answers = bouquetAnswerManager.findByBouquetIdAndQuestionId(bouquet.getId(), questionId);
        return GetBouquetQuestionAnswersResponse.of(questionId, answers);
    }

    @Transactional
    public void completeBouquet(String linkToken, String receiverName, List<CreateBouquetAnswerRequest> answers, AccessUser user) {
        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));

        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));

        if (bouquet.getReceiverType() != BouquetReceiverType.EVERYONE && link.getStatus() == BouquetLinkStatus.COMPLETED) {
            throw new ServiceException(BouquetErrorCode.LINK_ALREADY_COMPLETED);
        }

        BouquetReceiver receiver = bouquetReceiverManager.findByBouquetLinkId(link.getId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.RECEIVER_NOT_FOUND));
        BouquetReceiver namedReceiver = bouquetReceiverManager.updateReceiverName(receiver, receiverName);

        Long currentUserId = user instanceof AuthenticateUser ? user.getId() : null;

        bouquetAnswerManager.createReceiverAnswers(link.getBouquetId(), currentUserId, namedReceiver.getId(), answers);

        if (currentUserId != null) {
            connectUserToBouquet(namedReceiver, bouquet, currentUserId);
        }

        bouquetLinkManager.complete(link);

        if (bouquet.getSenderType() == BouquetSenderType.USER) {
            notificationManager.createBouquetCompletedIfAbsent(bouquet.getSenderId(), bouquet.getId());
        }
    }

    @Transactional
    public void claimBouquet(String linkToken, AccessUser user) {
        if (!(user instanceof AuthenticateUser)) {
            return;
        }

        BouquetLink link = bouquetLinkManager.findByToken(linkToken)
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.LINK_NOT_FOUND));
        Bouquet bouquet = bouquetManager.findById(link.getBouquetId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.NOT_FOUND));
        BouquetReceiver receiver = bouquetReceiverManager.findByBouquetLinkId(link.getId())
                .orElseThrow(() -> new ServiceException(BouquetErrorCode.RECEIVER_NOT_FOUND));

        if (receiver.getUserId() == null) {
            connectUserToBouquet(receiver, bouquet, user.getId());
        }
    }

    private void connectUserToBouquet(BouquetReceiver receiver, Bouquet bouquet, Long userId) {
        bouquetReceiverManager.connectUser(receiver, userId);
        
        Bouquet updatedBouquet = bouquet.toBuilder()
                .receiverId(userId)
                .receiverType(BouquetReceiverType.USER)
                .modifiedAt(timeProvider.now())
                .build();
        bouquetRepository.save(updatedBouquet);
    }

}
