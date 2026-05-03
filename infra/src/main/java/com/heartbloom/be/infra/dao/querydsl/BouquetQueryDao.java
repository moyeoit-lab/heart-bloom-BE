package com.heartbloom.be.infra.dao.querydsl;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;
import com.heartbloom.be.infra.entity.domain.answer.QBouquetAnswerEntity;
import com.heartbloom.be.infra.entity.domain.bouquet.QBouquetEntity;
import com.heartbloom.be.infra.entity.domain.bouquet.QBouquetTypeEntity;
import com.heartbloom.be.infra.entity.domain.receiver.QBouquetReceiverEntity;
import com.heartbloom.be.infra.entity.domain.user.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
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
    private final QBouquetReceiverEntity bouquetReceiver = QBouquetReceiverEntity.bouquetReceiverEntity;
    private final QBouquetAnswerEntity senderAnswer = new QBouquetAnswerEntity("senderAnswer");
    private final QBouquetAnswerEntity receiverAnswer = new QBouquetAnswerEntity("receiverAnswer");
    private final QUserEntity user = QUserEntity.userEntity;

    public List<GetBouquetQueryDto> querySentBouquets(Long senderId, BouquetSenderType senderType) {
        BooleanExpression hasSenderAnswer = hasAnswer("SENDER", senderAnswer);
        BooleanExpression hasReceiverAnswer = hasAnswer("RECEIVER", receiverAnswer);

        return queryFactory
                .select(Projections.constructor(GetBouquetQueryDto.class,
                        bouquet.id,
                        bouquet.senderId,
                        bouquet.senderType,
                        bouquet.receiverId,
                        bouquetReceiver.receiverName,
                        bouquet.receiverType,
                        bouquet.displayName,
                        bouquet.bouquetTypeId,
                        bouquetType.bouquetName,
                        bouquetType.bouquetDescription,
                        bouquetType.bouquetImageUrl,
                        hasSenderAnswer,
                        hasReceiverAnswer
                ))
                .from(bouquet)
                .leftJoin(bouquetType).on(bouquet.bouquetTypeId.eq(bouquetType.id))
                .leftJoin(bouquetReceiver).on(bouquet.receiverId.eq(bouquetReceiver.id))
                .where(
                        bouquet.senderId.eq(senderId),
                        bouquet.senderType.eq(senderType),
                        bouquet.deleted.isFalse()
                )
                .fetch();
    }

    public List<GetBouquetQueryDto> queryReceivedBouquets(Long receiverId, BouquetReceiverType receiverType) {
        BooleanExpression hasSenderAnswer = hasAnswer("SENDER", senderAnswer);
        BooleanExpression hasReceiverAnswer = hasAnswer("RECEIVER", receiverAnswer);

        return queryFactory
                .select(Projections.constructor(GetBouquetQueryDto.class,
                        bouquet.id,
                        bouquet.senderId,
                        bouquet.senderType,
                        bouquet.receiverId,
                        user.name,
                        bouquet.receiverType,
                        bouquet.displayName,
                        bouquet.bouquetTypeId,
                        bouquetType.bouquetName,
                        bouquetType.bouquetDescription,
                        bouquetType.bouquetImageUrl,
                        hasReceiverAnswer,
                        hasSenderAnswer
                ))
                .from(bouquet)
                .leftJoin(bouquetType).on(bouquet.bouquetTypeId.eq(bouquetType.id))
                .leftJoin(user).on(bouquet.receiverId.eq(user.id))
                .where(
                        bouquet.receiverId.eq(receiverId),
                        bouquet.receiverType.eq(receiverType),
                        bouquet.deleted.isFalse()
                )
                .fetch();
    }

    private BooleanExpression hasAnswer(String respondentType, QBouquetAnswerEntity answer) {
        return JPAExpressions
                .selectOne()
                .from(answer)
                .where(
                        answer.bouquetId.eq(bouquet.id),
                        answer.respondentType.eq(respondentType)
                )
                .exists();
    }

}
