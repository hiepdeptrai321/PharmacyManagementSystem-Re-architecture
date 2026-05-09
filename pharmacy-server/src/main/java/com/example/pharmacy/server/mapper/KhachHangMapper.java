package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.server.entity.KhachHangEntity;

public final class KhachHangMapper {
    private KhachHangMapper() {}

    public static KhachHangDto toDto(KhachHangEntity entity) {
        KhachHangDto dto = new KhachHangDto();
        dto.setMaKH(entity.getMaKH());
        dto.setTenKH(entity.getTenKH());
        dto.setSdt(entity.getSdt());
        dto.setEmail(entity.getEmail());
        dto.setNgaySinh(entity.getNgaySinh());
        dto.setGioiTinh(entity.isGioiTinh());
        dto.setDiaChi(entity.getDiaChi());
        dto.setTrangThai(entity.isTrangThai());
        return dto;
    }

    public static KhachHangEntity toEntity(KhachHangDto dto) {
        KhachHangEntity entity = new KhachHangEntity();
        entity.setMaKH(dto.getMaKH());
        entity.setTenKH(dto.getTenKH());
        entity.setSdt(dto.getSdt());
        entity.setEmail(dto.getEmail());
        entity.setNgaySinh(dto.getNgaySinh());
        entity.setGioiTinh(Boolean.TRUE.equals(dto.getGioiTinh()));
        entity.setDiaChi(dto.getDiaChi());
        entity.setTrangThai(dto.getTrangThai() == null || dto.getTrangThai());
        return entity;
    }
}
