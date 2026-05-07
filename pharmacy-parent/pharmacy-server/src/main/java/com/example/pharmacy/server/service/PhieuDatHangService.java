package com.example.pharmacy.server.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDatHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;

import java.util.List;

public interface PhieuDatHangService {
    String generateNewMaPhieuDatHang();

    String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor);

    List<PhieuDatHang> findAll();

    PhieuDatHang findById(String maPhieuDat);

    List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat, UserContext actor);
}
