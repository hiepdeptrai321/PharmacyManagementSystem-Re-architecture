package com.example.pharmacy.server.repository;

import com.example.pharmacy.server.entity.DonViTinhEntity;

import java.util.List;
import java.util.Optional;

public interface DonViTinhRepository {
    List<DonViTinhEntity> findAll();

    Optional<DonViTinhEntity> findById(String maDonViTinh);

    Optional<DonViTinhEntity> findByTenDonViTinh(String tenDonViTinh);

    DonViTinhEntity save(DonViTinhEntity entity);

    DonViTinhEntity update(DonViTinhEntity entity);

    boolean deleteById(String maDonViTinh);
}
