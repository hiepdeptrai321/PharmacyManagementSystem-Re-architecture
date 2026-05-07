package com.example.pharmacy.server.service;

import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> findAll();

    KhachHang findById(String maKhachHang);

    String generateNewMaKH();

    boolean create(KhachHang khachHang);

    boolean save(KhachHang khachHang);

    boolean deleteById(String maKhachHang);
}
