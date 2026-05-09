package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.NhomDuocLyClientService;
import com.example.pharmacy.client.service.RmiNhomDuocLyClientService;
import com.example.pharmacy.common.model.NhomDuocLyDto;

import java.util.List;

public class NhomDuocLyService {
    private final NhomDuocLyClientService nhomDuocLyClientService =
            new RmiNhomDuocLyClientService(new RmiClientProvider());

    public List<NhomDuocLyDto> findAll() {
        return nhomDuocLyClientService.findAll();
    }

    public NhomDuocLyDto findById(String maNhomDuocLy) {
        return nhomDuocLyClientService.findById(maNhomDuocLy);
    }

    public String generateNewMaNhomDL() {
        return nhomDuocLyClientService.generateNewMaNhomDuocLy();
    }

    public boolean create(NhomDuocLyDto nhomDuocLy) {
        return nhomDuocLyClientService.create(nhomDuocLy);
    }

    public boolean update(NhomDuocLyDto nhomDuocLy) {
        return nhomDuocLyClientService.update(nhomDuocLy);
    }

    public boolean deleteById(String maNhomDuocLy) {
        return nhomDuocLyClientService.deleteById(maNhomDuocLy);
    }

    public List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) {
        return nhomDuocLyClientService.findThuocNamesByNhomDuocLy(maNhomDuocLy);
    }

    public List<String> getAllTenNhomDuocLy() {
        return findAll().stream()
                .map(NhomDuocLyDto::getTenNDL)
                .filter(tenNDL -> tenNDL != null && !tenNDL.isBlank())
                .toList();
    }

    public NhomDuocLyDto selectByTenNhomDuocLy(String tenNhomDuocLy) {
        if (tenNhomDuocLy == null || tenNhomDuocLy.isBlank()) {
            return null;
        }
        return findAll().stream()
                .filter(nhomDuocLy -> tenNhomDuocLy.equalsIgnoreCase(nhomDuocLy.getTenNDL()))
                .findFirst()
                .orElse(null);
    }

    public List<NhomDuocLyDto> searchByKeyword(String keyword) {
        String tuKhoa = keyword == null ? "" : keyword.trim().toLowerCase();
        if (tuKhoa.isEmpty()) {
            return findAll();
        }
        return findAll().stream()
                .filter(nhomDuocLy ->
                        containsIgnoreCase(nhomDuocLy.getMaNDL(), tuKhoa) ||
                                containsIgnoreCase(nhomDuocLy.getTenNDL(), tuKhoa))
                .toList();
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toLowerCase().contains(keyword);
    }
}
