package com.heartbloom.be.infra.entity.domain.bouquet;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_bouquet")
@Entity
public class BouquetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "receiver_relation", nullable = false, length = 100)
    private String receiverRelation;

    @Column(name = "bouquet_type_id", nullable = false)
    private Long bouquetTypeId;

    @Builder
    public BouquetEntity(Long userId, String displayName, String receiverRelation, Long bouquetTypeId) {
        this.userId = userId;
        this.displayName = displayName;
        this.receiverRelation = receiverRelation;
        this.bouquetTypeId = bouquetTypeId;
    }

}
