package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.PhieuDatHang;

import java.util.List;

public interface PhieuDatHangClientService {
    String generateNewMaPhieuDatHang();

    String create(PhieuDatHang phieuDatHang, List<ChiTietPhieuDatHang> details, UserContext actor);

    List<PhieuDatHang> findAll();

    PhieuDatHang findById(String maPhieuDat);

    List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat, UserContext actor);
}
