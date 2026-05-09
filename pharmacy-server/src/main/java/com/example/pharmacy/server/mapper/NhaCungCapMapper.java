package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.model.NhaCungCapDto;
import com.example.pharmacy.server.entity.NhaCungCapEntity;

public final class NhaCungCapMapper {
    private NhaCungCapMapper() {}

    public static NhaCungCapDto toDto(NhaCungCapEntity entity) {
        NhaCungCapDto dto = new NhaCungCapDto();
        dto.setMaNCC(entity.getMaNCC());
        dto.setTenNCC(entity.getTenNCC());
        dto.setDiaChi(entity.getDiaChi());
        dto.setSDT(entity.getSdt());
        dto.setEmail(entity.getEmail());
        dto.setGPKD(entity.getGpKD());
        dto.setGhiChu(entity.getGhiChu());
        dto.setTenCongTy(entity.getTenCongTy());
        dto.setMSThue(entity.getMsThue());
        return dto;
    }

    public static NhaCungCapEntity toEntity(NhaCungCapDto dto) {
        NhaCungCapEntity entity = new NhaCungCapEntity();
        entity.setMaNCC(dto.getMaNCC());
        entity.setTenNCC(dto.getTenNCC());
        entity.setDiaChi(dto.getDiaChi());
        entity.setSdt(dto.getSDT());
        entity.setEmail(dto.getEmail());
        entity.setGpKD(dto.getGPKD());
        entity.setGhiChu(dto.getGhiChu());
        entity.setTenCongTy(dto.getTenCongTy());
        entity.setMsThue(dto.getMSThue());
        return entity;
    }
}
