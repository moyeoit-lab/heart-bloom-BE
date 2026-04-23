package com.heartbloom.be.infra.dao.jpa.bouquet;

import com.heartbloom.be.core.model.domain.bouquet.BouquetLink;
import com.heartbloom.be.core.model.domain.bouquet.enumerate.BouquetLinkStatus;
import com.heartbloom.be.infra.entity.domain.bouquet.BouquetLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BouquetLinkJpaDao extends JpaRepository<BouquetLinkEntity, Long> {

    @Query("SELECT bl FROM BouquetLinkEntity bl WHERE bl.id = :bouquetLinkId AND bl.status = :bouquetLinkStatus")
    Optional<BouquetLinkEntity> findByIdAndStatus(@Param("bouquetLinkId") Long bouquetLinkId,
                                            @Param("bouquetLinkStatus") BouquetLinkStatus bouquetLinkStatus);

    @Query("SELECT bl FROM BouquetLinkEntity bl WHERE bl.bouquetId = :bouquetId AND bl.status = :bouquetLinkStatus")
    Optional<BouquetLinkEntity> findByBouquetIdAndStatus(@Param("bouquetId") Long bouquetId,
                                                   @Param("bouquetLinkStatus") BouquetLinkStatus bouquetLinkStatus);

    Optional<BouquetLinkEntity> findByLinkToken(String linkToken);

    Optional<BouquetLinkEntity> findByBouquetId(Long bouquetId);

}
