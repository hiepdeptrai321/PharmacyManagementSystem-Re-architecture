package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.NhanVienClientService;
import com.example.pharmacy.client.service.RmiNhanVienClientService;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.util.List;

public class NhanVienService {
    private final NhanVienClientService nhanVienClientService =
            new RmiNhanVienClientService(new RmiClientProvider());

    public List<NhanVienDto> findAll() {
        return nhanVienClientService.findAll();
    }

    public NhanVienDto findById(String maNhanVien) {
        return nhanVienClientService.findById(maNhanVien);
    }

    public String generateNewMaNhanVien() {
        return nhanVienClientService.generateNewMaNhanVien();
    }

    public boolean create(NhanVienDto nhanVien) {
        return nhanVienClientService.create(nhanVien);
    }

    public boolean update(NhanVienDto nhanVien) {
        return nhanVienClientService.update(nhanVien);
    }

    public boolean softDelete(String maNhanVien) {
        return nhanVienClientService.softDelete(maNhanVien);
    }

    public boolean isUsernameAvailable(String username, String excludedMaNhanVien) {
        return nhanVienClientService.isUsernameAvailable(username, excludedMaNhanVien);
    }

    public List<LuongNhanVienDto> findLuongByMaNhanVien(String maNhanVien) {
        return nhanVienClientService.findLuongByMaNhanVien(maNhanVien);
    }

    public String generateNewMaLuongNhanVien() {
        return nhanVienClientService.generateNewMaLuongNhanVien();
    }

    public boolean saveLuongNhanVien(LuongNhanVienDto luongNhanVien) {
        return nhanVienClientService.saveLuongNhanVien(luongNhanVien);
    }

    public List<NhanVienDto> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(nhanVien ->
                        containsIgnoreCase(nhanVien.getMaNV(), tuKhoa) ||
                                containsIgnoreCase(nhanVien.getTenNV(), tuKhoa))
                .toList();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
