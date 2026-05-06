package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.NhaCungCap_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;

import java.util.List;

public class NhaCungCapService {
    private final NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();

    public List<NhaCungCap> findAll() {
        return nhaCungCapDao.selectAll();
    }

    public boolean create(NhaCungCap nhaCungCap) {
        return nhaCungCapDao.insert(nhaCungCap);
    }

    public boolean update(NhaCungCap nhaCungCap) {
        return nhaCungCapDao.update(nhaCungCap);
    }

    public boolean deleteById(String maNcc) {
        return nhaCungCapDao.deleteById(maNcc);
    }

    public List<NhaCungCap> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(nhaCungCap ->
                        containsIgnoreCase(nhaCungCap.getMaNCC(), tuKhoa) ||
                                containsIgnoreCase(nhaCungCap.getTenNCC(), tuKhoa))
                .toList();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
