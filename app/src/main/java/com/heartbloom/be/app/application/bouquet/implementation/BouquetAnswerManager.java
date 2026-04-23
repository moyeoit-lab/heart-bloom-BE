import com.heartbloom.be.app.api.bouquet.request.CreateBouquetAnswerRequest;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.model.domain.answer.enumerate.BouquetAnswerRespondentType;
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

    public List<BouquetAnswer> findByBouquetId(Long bouquetId) {
        return bouquetAnswerRepository.findByBouquetId(bouquetId);
    }

    public List<BouquetAnswer> createReceiverAnswers(Long bouquetId, Long userId, Long receiverId, List<CreateBouquetAnswerRequest> requests) {
        LocalDateTime now = timeProvider.now();
        return requests.stream()
                .map(request -> createReceiverAnswer(bouquetId, userId, receiverId, request, now))
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

    private BouquetAnswer createReceiverAnswer(Long bouquetId,
                                               Long userId,
                                               Long receiverId,
                                               CreateBouquetAnswerRequest request,
                                               LocalDateTime now) {
        BouquetAnswer bouquetAnswer = BouquetAnswer.create(
                bouquetId,
                request.questionId(),
                request.answerType(),
                BouquetAnswerRespondentType.RECEIVER,
                userId,
                receiverId,
                request.answer(),
                null,
                request.sortOrder(),
                now
        );

        return bouquetAnswerRepository.save(bouquetAnswer);
    }

}
