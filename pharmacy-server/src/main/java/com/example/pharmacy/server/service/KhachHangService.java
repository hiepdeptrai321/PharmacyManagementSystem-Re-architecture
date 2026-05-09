package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.KhachHangDto;

import java.util.List;

public interface KhachHangService {
    List<KhachHangDto> findAll();

    KhachHangDto findById(String maKhachHang);

    String generateNewMaKH();

    boolean create(KhachHangDto khachHang);

    boolean save(KhachHangDto khachHang);

    boolean deleteById(String maKhachHang);
}
