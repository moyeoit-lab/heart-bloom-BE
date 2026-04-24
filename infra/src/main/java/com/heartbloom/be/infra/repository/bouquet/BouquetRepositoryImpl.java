package com.heartbloom.be.infra.repository.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.BouquetRepository;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;
import com.heartbloom.be.infra.dao.jpa.bouquet.BouquetJpaDao;
import com.heartbloom.be.infra.dao.querydsl.BouquetQueryDao;
import com.heartbloom.be.infra.entity.converter.BouquetConverter;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BouquetRepositoryImpl implements BouquetRepository {

    private final BouquetJpaDao bouquetJpaDao;
    private final BouquetQueryDao bouquetQueryDao;

    @Override
    public Bouquet save(Bouquet bouquet) {
        BouquetEntity entity = bouquetJpaDao.save(BouquetConverter.toEntity(bouquet));
        return BouquetConverter.toModel(entity);
    }

    @Override
    public Optional<Bouquet> findById(Long bouquetId) {
        return bouquetJpaDao.findById(bouquetId)
                .map(BouquetConverter::toModel);
    }

    @Override
    public long countAll() {
        return bouquetJpaDao.countAll();
    }

    @Override
    public List<Bouquet> findBySender(Long senderId, BouquetSenderType senderType) {
        return bouquetJpaDao.findBySender(senderId, senderType).stream()
                .map(BouquetConverter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Bouquet> findByReceiver(Long receiverId, BouquetReceiverType receiverType) {
        return bouquetJpaDao.findByReceiver(receiverId, receiverType).stream()
                .map(BouquetConverter::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<GetBouquetQueryDto> querySentBouquets(Long senderId, BouquetSenderType senderType) {
        return bouquetQueryDao.querySentBouquets(senderId, senderType);
    }

    @Override
    public List<GetBouquetQueryDto> queryReceivedBouquets(Long receiverId, BouquetReceiverType receiverType) {
        return bouquetQueryDao.queryReceivedBouquets(receiverId, receiverType);
    }

}
