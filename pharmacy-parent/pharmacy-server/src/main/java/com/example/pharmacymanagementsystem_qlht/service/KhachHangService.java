package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.KhachHang_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KhachHang;

import java.util.List;

public class KhachHangService {
    private static final String SEARCH_BY_EMAIL = "Theo email";
    private static final String SEARCH_BY_PHONE = "Theo SDT";

    private final KhachHang_Dao khachHangDao = new KhachHang_Dao();

    public List<KhachHang> findAll() {
        return khachHangDao.selectAll();
    }

    public KhachHang findById(String maKhachHang) {
        return khachHangDao.selectById(maKhachHang);
    }

    public String generateNewMaKH() {
        return khachHangDao.generateNewMaKH();
    }

    public boolean create(KhachHang khachHang) {
        return khachHangDao.insert(khachHang);
    }

    public boolean save(KhachHang khachHang) {
        if (khachHang == null) {
            return false;
        }

        if (khachHang.getMaKH() == null || khachHang.getMaKH().trim().isEmpty()) {
            return khachHangDao.insert(khachHang);
        }

        return khachHangDao.update(khachHang);
    }

    public boolean deleteById(String maKhachHang) {
        return khachHangDao.deleteById(maKhachHang);
    }

    public List<KhachHang> searchByKeyword(String keyword) {
        String tuKhoa = normalize(keyword);
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(khachHang ->
                        containsIgnoreCase(khachHang.getMaKH(), tuKhoa) ||
                                containsIgnoreCase(khachHang.getTenKH(), tuKhoa))
                .toList();
    }

    public List<KhachHang> search(String criteria, String keyword) {
        String tuKhoa = normalize(keyword);
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(khachHang -> matches(khachHang, criteria, tuKhoa))
                .toList();
    }

    private boolean matches(KhachHang khachHang, String criteria, String keyword) {
        if (SEARCH_BY_EMAIL.equals(criteria)) {
            return containsIgnoreCase(khachHang.getEmail(), keyword);
        }

        if (SEARCH_BY_PHONE.equals(criteria)) {
            return containsIgnoreCase(khachHang.getSdt(), keyword);
        }

        return containsIgnoreCase(khachHang.getMaKH(), keyword) ||
                containsIgnoreCase(khachHang.getTenKH(), keyword);
    }

    private String normalize(String keyword) {
        return keyword == null ? "" : keyword.trim().toLowerCase();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
