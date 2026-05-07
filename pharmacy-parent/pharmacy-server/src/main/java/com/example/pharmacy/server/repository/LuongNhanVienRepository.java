package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.LuongNhanVienEntity;

import java.util.List;
import java.util.Optional;

public interface LuongNhanVienRepository {
    List<LuongNhanVienEntity> findByMaNhanVien(String maNhanVien);

    Optional<LuongNhanVienEntity> findById(String maLuongNhanVien);

    LuongNhanVienEntity save(LuongNhanVienEntity entity);

    LuongNhanVienEntity update(LuongNhanVienEntity entity);
}
