package com.example.pharmacy.server.repository;

import com.example.pharmacymanagementsystem_qlht.model.ChiTietPhieuDatHang;
import com.example.pharmacymanagementsystem_qlht.model.PhieuDatHang;

import java.util.List;

public interface PhieuDatHangRepository {
    void insertHeader(PhieuDatHang phieuDatHang, String maPhieuDat, String employeeId);

    void insertDetail(String maPhieuDat, ChiTietPhieuDatHang detail);

    List<PhieuDatHang> findAll();

    PhieuDatHang findById(String maPhieuDat);

    List<ChiTietPhieuDatHang> findDetailsByMaPhieuDat(String maPhieuDat);

    boolean approve(String maPhieuDat);
}
