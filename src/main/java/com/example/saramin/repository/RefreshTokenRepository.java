package com.example.saramin.repository;

import com.example.saramin.entity.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findAllByValue(String value);
}
