package com.heartbloom.be.infra.dao.jpa.user;

import com.heartbloom.be.infra.entity.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaDao extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.id = :userId AND u.deleted = FALSE")
    Optional<UserEntity> findById(@Param("userId") Long userId);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.deleted = FALSE")
    Optional<UserEntity> findByEmail(@Param("email") String email);

}
