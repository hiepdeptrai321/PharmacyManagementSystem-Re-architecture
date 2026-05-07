package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.NhanVienClientService;
import com.example.pharmacy.client.service.RmiNhanVienClientService;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

import java.util.List;

public class NhanVienService {
    private final NhanVienClientService nhanVienClientService =
            new RmiNhanVienClientService(new RmiClientProvider());

    public List<NhanVien> findAll() {
        return nhanVienClientService.findAll();
    }

    public NhanVien findById(String maNhanVien) {
        return nhanVienClientService.findById(maNhanVien);
    }

    public String generateNewMaNhanVien() {
        return nhanVienClientService.generateNewMaNhanVien();
    }

    public boolean create(NhanVien nhanVien) {
        return nhanVienClientService.create(nhanVien);
    }

    public boolean update(NhanVien nhanVien) {
        return nhanVienClientService.update(nhanVien);
    }

    public boolean softDelete(String maNhanVien) {
        return nhanVienClientService.softDelete(maNhanVien);
    }

    public boolean isUsernameAvailable(String username, String excludedMaNhanVien) {
        return nhanVienClientService.isUsernameAvailable(username, excludedMaNhanVien);
    }

    public List<LuongNhanVien> findLuongByMaNhanVien(String maNhanVien) {
        return nhanVienClientService.findLuongByMaNhanVien(maNhanVien);
    }

    public String generateNewMaLuongNhanVien() {
        return nhanVienClientService.generateNewMaLuongNhanVien();
    }

    public boolean saveLuongNhanVien(LuongNhanVien luongNhanVien) {
        return nhanVienClientService.saveLuongNhanVien(luongNhanVien);
    }

    public List<NhanVien> searchByKeyword(String keyword) {
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
