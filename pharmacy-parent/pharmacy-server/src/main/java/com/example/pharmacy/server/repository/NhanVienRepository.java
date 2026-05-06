package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhanVienEntity;

import java.util.Optional;

public interface NhanVienRepository {
    Optional<NhanVienEntity> findByUsername(String username);
}
