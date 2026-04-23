package com.heartbloom.be.core.repository.domain.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.Bouquet;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.core.repository.domain.bouquet.dto.GetBouquetQueryDto;

import java.util.List;
import java.util.Optional;

public interface BouquetRepository {

    Bouquet save(Bouquet bouquet);

    Optional<Bouquet> findById(Long bouquetId);

    /* 내가 발신자인 부케 목록 조회 */
    List<Bouquet> findBySender(Long senderId, BouquetSenderType senderType);

    /* 내가 수신자인 부케 목록 조회 */
    List<Bouquet> findByReceiver(Long receiverId, BouquetReceiverType receiverType);

    /* 보낸 부케 상세 목록 조회 (DTO) */
    List<GetBouquetQueryDto> querySentBouquets(Long senderId, BouquetSenderType senderType);

    /* 받은 부케 상세 목록 조회 (DTO) */
    List<GetBouquetQueryDto> queryReceivedBouquets(Long receiverId, BouquetReceiverType receiverType);

}
