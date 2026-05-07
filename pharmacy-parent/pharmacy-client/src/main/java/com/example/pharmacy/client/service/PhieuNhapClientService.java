package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.PhieuNhapRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuNhap;
import com.example.pharmacymanagementsystem_qlht.model.PhieuNhap;

import java.util.List;

public interface PhieuNhapClientService {
    String generateNewMaPhieuNhap();

    String createPurchaseOrder(PhieuNhapRequest request, UserContext actor);

    List<PhieuNhap> findAll();

    PhieuNhap findById(String maPhieuNhap);

    List<ChiTietPhieuNhap> findDetailsByMaPhieuNhap(String maPhieuNhap);
}
