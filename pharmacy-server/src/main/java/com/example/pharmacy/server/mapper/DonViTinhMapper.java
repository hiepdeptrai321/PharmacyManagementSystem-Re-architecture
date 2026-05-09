package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.server.entity.DonViTinhEntity;

public final class DonViTinhMapper {
    private DonViTinhMapper() {}

    public static DonViTinhDto toDto(DonViTinhEntity entity) {
        DonViTinhDto dto = new DonViTinhDto();
        dto.setMaDVT(entity.getMaDVT());
        dto.setTenDonViTinh(entity.getTenDonViTinh());
        dto.setKiHieu(entity.getKiHieu());
        return dto;
    }

    public static DonViTinhEntity toEntity(DonViTinhDto dto) {
        DonViTinhEntity entity = new DonViTinhEntity();
        entity.setMaDVT(dto.getMaDVT());
        entity.setTenDonViTinh(dto.getTenDonViTinh());
        entity.setKiHieu(dto.getKiHieu());
        return entity;
    }
}
