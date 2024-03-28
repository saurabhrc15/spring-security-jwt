package com.example.auth.repository;

import com.example.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndIsDeletedEquals(String token, boolean isDeleted);

    Optional<Token> findByValueAndIsDeletedEqualsAndExpiryAtGreaterThan(
            String token, boolean isDeleted, Date expiryAt);
}
