package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.model.NhomDuocLyDto;
import com.example.pharmacy.server.entity.NhomDuocLyEntity;

public final class NhomDuocLyMapper {
    private NhomDuocLyMapper() {}

    public static NhomDuocLyDto toDto(NhomDuocLyEntity entity) {
        NhomDuocLyDto dto = new NhomDuocLyDto();
        dto.setMaNDL(entity.getMaNDL());
        dto.setTenNDL(entity.getTenNDL());
        dto.setMoTa(entity.getMoTa());
        return dto;
    }

    public static NhomDuocLyEntity toEntity(NhomDuocLyDto dto) {
        NhomDuocLyEntity entity = new NhomDuocLyEntity();
        entity.setMaNDL(dto.getMaNDL());
        entity.setTenNDL(dto.getTenNDL());
        entity.setMoTa(dto.getMoTa());
        return entity;
    }
}
