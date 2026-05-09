package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.server.entity.KeHangEntity;

public final class KeHangMapper {
    private KeHangMapper() {}

    public static KeHangDto toDto(KeHangEntity entity) {
        return new KeHangDto(entity.getMaKe(), entity.getTenKe(), entity.getMoTa());
    }

    public static KeHangEntity toEntity(KeHangDto dto) {
        KeHangEntity entity = new KeHangEntity();
        entity.setMaKe(dto.getMaKe());
        entity.setTenKe(dto.getTenKe());
        entity.setMoTa(dto.getMoTa());
        return entity;
    }
}
