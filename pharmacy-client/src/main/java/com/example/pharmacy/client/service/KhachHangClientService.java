package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.KhachHang;

import java.util.List;

public interface KhachHangClientService {
    List<KhachHang> findAll();

    KhachHang findById(String maKhachHang);

    String generateNewMaKH();

    boolean create(KhachHang khachHang);

    boolean save(KhachHang khachHang);

    boolean deleteById(String maKhachHang);
}
