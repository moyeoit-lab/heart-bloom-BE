package com.heartbloom.be.core.repository.domain.bouquetanswer;

import com.heartbloom.be.core.model.domain.answer.BouquetAnswer;

import java.util.List;
import java.util.Optional;

public interface BouquetAnswerRepository {

    BouquetAnswer save(BouquetAnswer bouquetAnswer);

    Optional<BouquetAnswer> findById(Long bouquetAnswerId);

    List<BouquetAnswer> findByBouquetId(Long bouquetId);

}