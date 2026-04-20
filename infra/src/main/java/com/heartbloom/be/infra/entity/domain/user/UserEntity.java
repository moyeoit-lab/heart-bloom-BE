package com.heartbloom.be.infra.entity.domain.user;

import com.heartbloom.be.infra.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_user")
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "provider_type", nullable = false, length = 20)
    private String providerType;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Builder
    public UserEntity(Long id,
                      String name,
                      String email,
                      String providerType,
                      boolean deleted,
                      LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.providerType = providerType;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

}