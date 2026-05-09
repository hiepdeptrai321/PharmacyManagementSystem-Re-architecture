package com.example.pharmacy.client.service;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;

import java.util.List;

public interface PhieuDatHangClientService {
    String generateNewMaPhieuDatHang();

    String create(PhieuDatHangDto phieuDatHang, List<ChiTietPhieuDatHangDto> details, UserContext actor);

    List<PhieuDatHangDto> findAll();

    PhieuDatHangDto findById(String maPhieuDat);

    List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat, UserContext actor);
}
