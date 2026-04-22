package com.heartbloom.be.app.application.bouquet.implementation;

import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.repository.domain.bouquetanswer.BouquetAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BouquetAnswerManager {

    private final TimeProvider timeProvider;
    private final BouquetAnswerRepository bouquetAnswerRepository;

    public List<BouquetAnswer> create(Long bouquetId, Long userId, List<CreateBouquetAnswerRequest> requests) {
        LocalDateTime now = timeProvider.now();
        return requests.stream()
                .map(request -> create(bouquetId, userId, request, now))
                .toList();
    }

    private BouquetAnswer create(Long bouquetId,
                                 Long userId,
                                 CreateBouquetAnswerRequest request,
                                 LocalDateTime now) {
        BouquetAnswer bouquetAnswer = BouquetAnswer.createOfSender(
                bouquetId,
                request.questionId(),
                request.answerType(),
                userId,
                request.answer(),
                null,
                request.sortOrder(),
                now
        );

        return bouquetAnswerRepository.save(bouquetAnswer);
    }

}
