package com.heartbloom.be.infra.dao.querydsl;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;
import com.heartbloom.be.infra.entity.domain.bouquet.QBouquetEntity;
import com.heartbloom.be.infra.entity.domain.bouquet.QBouquetTypeEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class BouquetQueryDao {

    private final JPAQueryFactory queryFactory;
    private final QBouquetEntity bouquet = QBouquetEntity.bouquetEntity;
    private final QBouquetTypeEntity bouquetType = QBouquetTypeEntity.bouquetTypeEntity;

    public List<GetBouquetQueryDto> querySentBouquets(Long senderId, BouquetSenderType senderType) {
        return queryFactory
                .select(Projections.constructor(GetBouquetQueryDto.class,
                        bouquet.id,
                        bouquet.senderId,
                        bouquet.senderType,
                        bouquet.receiverId,
                        bouquet.receiverType,
                        bouquet.bouquetTypeId,
                        bouquetType.bouquetName,
                        bouquetType.bouquetDescription,
                        bouquetType.bouquetImageUrl
                ))
                .from(bouquet)
                .leftJoin(bouquetType).on(bouquet.bouquetTypeId.eq(bouquetType.id))
                .where(
                        bouquet.senderId.eq(senderId),
                        bouquet.senderType.eq(senderType),
                        bouquet.deleted.isFalse()
                )
                .fetch();
    }

    public List<GetBouquetQueryDto> queryReceivedBouquets(Long receiverId, BouquetReceiverType receiverType) {
        return queryFactory
                .select(Projections.constructor(GetBouquetQueryDto.class,
                        bouquet.id,
                        bouquet.senderId,
                        bouquet.senderType,
                        bouquet.receiverId,
                        bouquet.receiverType,
                        bouquet.bouquetTypeId,
                        bouquetType.bouquetName,
                        bouquetType.bouquetDescription,
                        bouquetType.bouquetImageUrl
                ))
                .from(bouquet)
                .leftJoin(bouquetType).on(bouquet.bouquetTypeId.eq(bouquetType.id))
                .where(
                        bouquet.receiverId.eq(receiverId),
                        bouquet.receiverType.eq(receiverType),
                        bouquet.deleted.isFalse()
                )
                .fetch();
    }

}
