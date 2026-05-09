package com.example.pharmacy.client.service;

import com.example.pharmacy.common.model.LuongNhanVien;
import com.example.pharmacy.common.model.NhanVien;

import java.util.List;

public interface NhanVienClientService {
    List<NhanVien> findAll();

    NhanVien findById(String maNhanVien);

    String generateNewMaNhanVien();

    boolean create(NhanVien nhanVien);

    boolean update(NhanVien nhanVien);

    boolean softDelete(String maNhanVien);

    boolean isUsernameAvailable(String username, String excludedMaNhanVien);

    List<LuongNhanVien> findLuongByMaNhanVien(String maNhanVien);

    String generateNewMaLuongNhanVien();

    boolean saveLuongNhanVien(LuongNhanVien luongNhanVien);
}
