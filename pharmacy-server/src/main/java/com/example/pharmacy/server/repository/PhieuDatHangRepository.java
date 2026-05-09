package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.model.ChiTietPhieuDatHangDto;
import com.example.pharmacy.common.model.PhieuDatHangDto;

import java.util.List;

public interface PhieuDatHangRepository {
    void insertHeader(PhieuDatHangDto phieuDatHang, String maPhieuDat, String employeeId);

    void insertDetail(String maPhieuDat, ChiTietPhieuDatHangDto detail);

    List<PhieuDatHangDto> findAll();

    PhieuDatHangDto findById(String maPhieuDat);

    List<ChiTietPhieuDatHangDto> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat);
}
