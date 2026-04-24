package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetReceiverType;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetSenderType;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BouquetJpaDao extends JpaRepository<BouquetEntity, Long> {

    @Query("SELECT b FROM BouquetEntity b WHERE b.id = :bouquetId AND b.deleted = FALSE")
    Optional<BouquetEntity> findById(@Param("bouquetId") Long bouquetId);

    @Query("SELECT COUNT(b) FROM BouquetEntity b")
    long countAll();

    @Query("SELECT b FROM BouquetEntity b WHERE b.senderId = :senderId AND b.senderType = :senderType AND b.deleted = FALSE")
    List<BouquetEntity> findBySender(@Param("senderId") Long senderId, @Param("senderType") BouquetSenderType senderType);

    @Query("SELECT b FROM BouquetEntity b WHERE b.receiverId = :receiverId AND b.receiverType = :receiverType AND b.deleted = FALSE")
    List<BouquetEntity> findByReceiver(@Param("receiverId") Long receiverId, @Param("receiverType") BouquetReceiverType receiverType);

}
