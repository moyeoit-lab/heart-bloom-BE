package com.heartbloom.be.infra.entity.domain.receiver;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_bouquet_receiver")
@Entity
public class BouquetReceiverEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_receiver_id")
    private Long id;

    @Column(name = "bouquet_id", nullable = false)
    private Long bouquetId;

    @Column(name = "bouquet_link_id", nullable = false)
    private Long bouquetLinkId;

    @Column(name = "receiver_name", nullable = false, length = 100)
    private String receiverName;

    @Builder
    public BouquetReceiverEntity(Long id,
                                 Long bouquetId,
                                 Long bouquetLinkId,
                                 String receiverName) {
        this.id = id;
        this.bouquetId = bouquetId;
        this.bouquetLinkId = bouquetLinkId;
        this.receiverName = receiverName;
    }

}