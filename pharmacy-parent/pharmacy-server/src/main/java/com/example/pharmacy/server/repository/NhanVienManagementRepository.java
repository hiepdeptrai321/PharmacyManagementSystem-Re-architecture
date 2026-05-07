package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.NhanVienEntity;

import java.util.List;
import java.util.Optional;

public interface NhanVienManagementRepository {
    List<NhanVienEntity> findAllNotDeleted();

    Optional<NhanVienEntity> findById(String maNhanVien);

    boolean existsByUsername(String username, String excludedMaNhanVien);

    NhanVienEntity save(NhanVienEntity entity);

    NhanVienEntity update(NhanVienEntity entity);

    boolean softDelete(String maNhanVien);
}
