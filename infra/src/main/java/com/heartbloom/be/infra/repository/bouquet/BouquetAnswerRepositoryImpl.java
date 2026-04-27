package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;
import com.heartbloom.be.core.repository.domain.bouquetanswer.BouquetAnswerRepository;
import com.heartbloom.be.infra.dao.jpa.bouquet.BouquetAnswerJpaDao;
import com.heartbloom.be.infra.entity.converter.BouquetAnswerConverter;
import com.heartbloom.be.infra.entity.domain.answer.BouquetAnswerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BouquetAnswerRepositoryImpl implements BouquetAnswerRepository {

    private final BouquetAnswerJpaDao bouquetAnswerJpaDao;

    @Override
    public BouquetAnswer save(BouquetAnswer bouquetAnswer) {
        BouquetAnswerEntity entity = bouquetAnswerJpaDao.save(BouquetAnswerConverter.toEntity(bouquetAnswer));
        return BouquetAnswerConverter.toModel(entity);
    }

    @Override
    public Optional<BouquetAnswer> findById(Long bouquetAnswerId) {
        return bouquetAnswerJpaDao.findById(bouquetAnswerId)
                .map(BouquetAnswerConverter::toModel);
    }

    @Override
    public List<BouquetAnswer> findByBouquetId(Long bouquetId) {
        return bouquetAnswerJpaDao.findByBouquetId(bouquetId)
                .stream()
                .map(BouquetAnswerConverter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BouquetAnswer> findByBouquetIdAndQuestionId(Long bouquetId, Long questionId) {
        return bouquetAnswerJpaDao.findByBouquetIdAndQuestionId(bouquetId, questionId)
                .stream()
                .map(BouquetAnswerConverter::toModel)
                .collect(Collectors.toList());
    }

}
