package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.KeHang_Dao;
import com.example.pharmacymanagementsystem_qlht.dao.Thuoc_SanPham_Dao;
import com.example.pharmacymanagementsystem_qlht.model.KeHang;

import java.util.List;

public class KeHangService {
    private final KeHang_Dao keHangDao = new KeHang_Dao();
    private final Thuoc_SanPham_Dao thuocDao = new Thuoc_SanPham_Dao();

    public List<KeHang> findAll() {
        return keHangDao.selectAll();
    }

    public String generateNewMaKeHang() {
        return keHangDao.generateNewMaKeHang();
    }

    public boolean create(KeHang keHang) {
        return keHangDao.insert(keHang);
    }

    public boolean update(KeHang keHang) {
        return keHangDao.update(keHang);
    }

    public boolean deleteById(String maKe) {
        return keHangDao.deleteById(maKe);
    }

    public List<String> getThuocTrongKe(String maKe) {
        return thuocDao.layDanhSachThuocTheoKe(maKe);
    }

    public List<KeHang> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(keHang ->
                        containsIgnoreCase(keHang.getMaKe(), tuKhoa) ||
                                containsIgnoreCase(keHang.getTenKe(), tuKhoa))
                .toList();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
