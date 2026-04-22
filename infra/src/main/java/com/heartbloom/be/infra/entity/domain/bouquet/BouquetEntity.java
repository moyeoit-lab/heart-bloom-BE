package com.heartbloom.be.infra.entity.domain.bouquet;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

}