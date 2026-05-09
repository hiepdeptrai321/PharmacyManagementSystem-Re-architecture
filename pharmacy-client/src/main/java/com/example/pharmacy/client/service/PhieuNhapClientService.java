package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.common.model.ChiTietPhieuNhapDto;
import com.example.pharmacy.common.model.PhieuNhapDto;

import java.util.List;

public interface PhieuNhapClientService {
    String generateNewMaPhieuNhap();

    String createPurchaseOrder(PhieuNhapRequest request, UserContext actor);

    List<PhieuNhapDto> findAll();

    PhieuNhapDto findById(String maPhieuNhap);

    List<ChiTietPhieuNhapDto> findDetailsByMaPhieuNhap(String maPhieuNhap);
}
