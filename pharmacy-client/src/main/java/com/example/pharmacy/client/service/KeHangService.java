package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.KeHangClientService;
import com.example.pharmacy.client.service.RmiKeHangClientService;
import com.example.pharmacy.common.model.KeHangDto;

import java.util.List;

public class KeHangService {
    private final KeHangClientService keHangClientService =
            new RmiKeHangClientService(new RmiClientProvider());

    public List<KeHangDto> findAll() {
        return keHangClientService.findAll();
    }

    public KeHangDto findById(String maKeHang) {
        return keHangClientService.findById(maKeHang);
    }

    public KeHangDto selectByTenKe(String tenKe) {
        return keHangClientService.findByTenKe(tenKe);
    }

    public String generateNewMaKeHang() {
        return keHangClientService.generateNewMaKeHang();
    }

    public boolean create(KeHangDto keHang) {
        return keHangClientService.create(keHang);
    }

    public boolean update(KeHangDto keHang) {
        return keHangClientService.update(keHang);
    }

    public boolean deleteById(String maKeHang) {
        return keHangClientService.deleteById(maKeHang);
    }

    public List<String> getThuocTrongKe(String maKeHang) {
        return keHangClientService.findThuocNamesByKeHang(maKeHang);
    }

    public List<String> getAllTenKe() {
        return findAll().stream()
                .map(KeHangDto::getTenKe)
                .filter(tenKe -> tenKe != null && !tenKe.isBlank())
                .toList();
    }

    public List<KeHangDto> searchByKeyword(String keyword) {
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
