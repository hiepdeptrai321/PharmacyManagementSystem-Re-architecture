package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacy.common.model.ChiTietPhieuNhap;
import com.example.pharmacy.common.model.PhieuNhap;

import java.util.List;

public interface PhieuNhapService {
    String generateNewMaPhieuNhap();

    String createPurchaseOrder(PhieuNhapRequest request, UserContext actor);

    List<PhieuNhap> findAll();

    PhieuNhap findById(String maPhieuNhap);

    List<ChiTietPhieuNhap> findDetailsByMaPhieuNhap(String maPhieuNhap);
}
