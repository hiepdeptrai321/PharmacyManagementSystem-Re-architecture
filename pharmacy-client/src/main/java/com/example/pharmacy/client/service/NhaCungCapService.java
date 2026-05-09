package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.NhaCungCapClientService;
import com.example.pharmacy.client.service.RmiNhaCungCapClientService;
import com.example.pharmacy.common.model.NhaCungCap;

import java.util.List;

public class NhaCungCapService {
    private final NhaCungCapClientService nhaCungCapClientService =
            new RmiNhaCungCapClientService(new RmiClientProvider());

    public List<NhaCungCap> findAll() {
        return nhaCungCapClientService.findAll();
    }

    public NhaCungCap findById(String maNcc) {
        return nhaCungCapClientService.findById(maNcc);
    }

    public String generateNewMaNCC() {
        return nhaCungCapClientService.generateNewMaNCC();
    }

    public boolean create(NhaCungCap nhaCungCap) {
        return nhaCungCapClientService.create(nhaCungCap);
    }

    public boolean update(NhaCungCap nhaCungCap) {
        return nhaCungCapClientService.update(nhaCungCap);
    }

    public boolean deleteById(String maNcc) {
        return nhaCungCapClientService.deleteById(maNcc);
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
