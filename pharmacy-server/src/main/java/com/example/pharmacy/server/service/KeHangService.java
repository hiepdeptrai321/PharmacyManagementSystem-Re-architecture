package com.example.pharmacy.server.service;

import com.example.pharmacy.common.model.KeHangDto;

import java.util.List;

public interface KeHangService {
    List<KeHangDto> findAll();

    KeHangDto findById(String maKeHang);

    KeHangDto findByTenKe(String tenKe);

    String generateNewMaKeHang();

    boolean create(KeHangDto keHang);

    boolean update(KeHangDto keHang);

    boolean deleteById(String maKeHang);

    List<String> findThuocNamesByKeHang(String maKeHang);
}
