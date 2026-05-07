package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.KeHangEntity;

import java.util.List;
import java.util.Optional;

public interface KeHangRepository {
    List<KeHangEntity> findAll();

    Optional<KeHangEntity> findById(String maKeHang);

    Optional<KeHangEntity> findByTenKe(String tenKe);

    KeHangEntity save(KeHangEntity entity);

    KeHangEntity update(KeHangEntity entity);

    boolean deleteById(String maKeHang);

    List<String> findThuocNamesByKeHang(String maKeHang);
}
