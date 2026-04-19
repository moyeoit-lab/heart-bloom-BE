package com.heartbloom.be.infra.entity.domain.bouquet;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_bouquet_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BouquetTypeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_type_id")
    private Long id;

    @Column(name = "bouquet_name", nullable = false, length = 100)
    private String bouquetName;

    @Column(name = "bouquet_description", length = 500)
    private String bouquetDescription;

    @Column(name = "bouquet_image_url", length = 500)
    private String bouquetImageUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Builder
    public BouquetTypeEntity(String bouquetName, String bouquetDescription, String bouquetImageUrl, Boolean active) {
        this.bouquetName = bouquetName;
        this.bouquetDescription = bouquetDescription;
        this.bouquetImageUrl = bouquetImageUrl;
        this.active = active != null ? active : true;
    }

}
