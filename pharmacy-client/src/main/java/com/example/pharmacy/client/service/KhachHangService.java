package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.KhachHangClientService;
import com.example.pharmacy.client.service.RmiKhachHangClientService;
import com.example.pharmacy.common.model.KhachHangDto;

import java.util.List;

public class KhachHangService {
    private static final String SEARCH_BY_EMAIL = "Theo email";
    private static final String SEARCH_BY_PHONE = "Theo SDT";

    private final KhachHangClientService khachHangClientService =
            new RmiKhachHangClientService(new RmiClientProvider());

    public List<KhachHangDto> findAll() {
        return khachHangClientService.findAll();
    }

    public KhachHangDto findById(String maKhachHang) {
        return khachHangClientService.findById(maKhachHang);
    }

    public String generateNewMaKH() {
        return khachHangClientService.generateNewMaKH();
    }

    public boolean create(KhachHangDto khachHang) {
        return khachHangClientService.create(khachHang);
    }

    public boolean save(KhachHangDto khachHang) {
        return khachHangClientService.save(khachHang);
    }

    public boolean deleteById(String maKhachHang) {
        return khachHangClientService.deleteById(maKhachHang);
    }

    public List<KhachHangDto> searchByKeyword(String keyword) {
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

    public List<KhachHangDto> search(String criteria, String keyword) {
        String tuKhoa = normalize(keyword);
        if (tuKhoa.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(khachHang -> matches(khachHang, criteria, tuKhoa))
                .toList();
    }

    private boolean matches(KhachHangDto khachHang, String criteria, String keyword) {
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
