package com.example.pharmacy.server.repository;

import com.example.pharmacy.common.model.ChiTietPhieuDatHang;
import com.example.pharmacy.common.model.PhieuDatHang;

import java.util.List;

public interface PhieuDatHangRepository {
    void insertHeader(PhieuDatHang phieuDatHang, String maPhieuDat, String employeeId);

    void insertDetail(String maPhieuDat, ChiTietPhieuDatHang detail);

    List<PhieuDatHang> findAll();

    PhieuDatHang findById(String maPhieuDat);

    List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat);
}
