package com.y.Y.models.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID> {

    @Query("SELECT a FROM Auth a WHERE a.user = ?1")
    Optional<Auth> findAuthByUserId(UUID id);
}
