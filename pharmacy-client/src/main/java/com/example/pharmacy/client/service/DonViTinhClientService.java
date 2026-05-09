package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.DonViTinh;

import java.util.List;

public interface DonViTinhClientService {
    List<DonViTinh> findAll();

    DonViTinh findById(String maDonViTinh);

    DonViTinh findByTenDonViTinh(String tenDonViTinh);

    String generateNewMaDVT();

    boolean create(DonViTinh donViTinh);

    boolean update(DonViTinh donViTinh);

    boolean deleteById(String maDonViTinh);
}
