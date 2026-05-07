package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.KhachHangEntity;

import java.util.List;
import java.util.Optional;

public interface KhachHangRepository {
    List<KhachHangEntity> findAllActive();

    Optional<KhachHangEntity> findById(String maKhachHang);

    KhachHangEntity save(KhachHangEntity entity);

    KhachHangEntity update(KhachHangEntity entity);

    boolean softDelete(String maKhachHang);
}
