package com.example.pharmacy.client.service;

import com.example.pharmacymanagementsystem_qlht.model.KeHang;

import java.util.List;

public interface KeHangClientService {
    List<KeHang> findAll();

    KeHang findById(String maKeHang);

    KeHang findByTenKe(String tenKe);

    String generateNewMaKeHang();

    boolean create(KeHang keHang);

    boolean update(KeHang keHang);

    boolean deleteById(String maKeHang);

    List<String> findThuocNamesByKeHang(String maKeHang);
}
