package com.heartbloom.be.infra.entity.domain.bouquet;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_bouquet_link")
@Entity
public class BouquetLinkEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_link_id")
    private Long id;

    @Column(name = "bouquet_id", nullable = false)
    private Long bouquetId;

    @Column(name = "link_token", nullable = false, unique = true, length = 100)
    private String linkToken;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    public BouquetLinkEntity(Long id,
                             Long bouquetId,
                             String linkToken,
                             String status,
                             LocalDateTime expiredAt) {
        this.id = id;
        this.bouquetId = bouquetId;
        this.linkToken = linkToken;
        this.status = status != null ? status : "ACTIVE";
        this.expiredAt = expiredAt;
    }

}
