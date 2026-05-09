package com.example.pharmacy.client.service.interfa;

import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.util.List;

public interface NhanVienClientService {
    List<NhanVienDto> findAll();

    NhanVienDto findById(String maNhanVien);

    String generateNewMaNhanVien();

    boolean create(NhanVienDto nhanVien);

    boolean update(NhanVienDto nhanVien);

    boolean softDelete(String maNhanVien);

    boolean isUsernameAvailable(String username, String excludedMaNhanVien);

    List<LuongNhanVienDto> findLuongByMaNhanVien(String maNhanVien);

    String generateNewMaLuongNhanVien();

    boolean saveLuongNhanVien(LuongNhanVienDto luongNhanVien);
}
