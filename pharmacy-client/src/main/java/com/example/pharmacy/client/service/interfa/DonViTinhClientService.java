package com.example.pharmacy.client.service.interfa;

import com.example.pharmacy.common.model.DonViTinhDto;

import java.util.List;

public interface DonViTinhClientService {
    List<DonViTinhDto> findAll();

    DonViTinhDto findById(String maDonViTinh);

    DonViTinhDto findByTenDonViTinh(String tenDonViTinh);

    String generateNewMaDVT();

    boolean create(DonViTinhDto donViTinh);

    boolean update(DonViTinhDto donViTinh);

    boolean deleteById(String maDonViTinh);
}
