package com.example.pharmacy.client.service;

import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

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
