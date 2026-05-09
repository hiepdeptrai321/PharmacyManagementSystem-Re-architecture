package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.DonViTinhEntity;
import com.example.pharmacy.server.mapper.DonViTinhMapper;
import com.example.pharmacy.server.repository.DonViTinhRepository;
import com.example.pharmacy.common.model.DonViTinhDto;

import java.util.List;
import java.util.Objects;

public class DonViTinhServiceImpl implements DonViTinhService {
    private final DonViTinhRepository donViTinhRepository;
    private final CodeGenerationService codeGenerationService;

    public DonViTinhServiceImpl(DonViTinhRepository donViTinhRepository, CodeGenerationService codeGenerationService) {
        this.donViTinhRepository = Objects.requireNonNull(donViTinhRepository, "donViTinhRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<DonViTinhDto> findAll() {
        return donViTinhRepository.findAll().stream().map(DonViTinhMapper::toDto).toList();
    }

    @Override
    public DonViTinhDto findById(String maDonViTinh) {
        return donViTinhRepository.findById(maDonViTinh).map(DonViTinhMapper::toDto).orElse(null);
    }

    @Override
    public DonViTinhDto findByTenDonViTinh(String tenDonViTinh) {
        return donViTinhRepository.findByTenDonViTinh(tenDonViTinh).map(DonViTinhMapper::toDto).orElse(null);
    }

    @Override
    public String generateNewMaDVT() {
        return codeGenerationService.nextCode(BusinessCodeType.DON_VI_TINH);
    }

    @Override
    public boolean create(DonViTinhDto donViTinh) {
        if (donViTinh == null) {
            return false;
        }
        try {
            DonViTinhEntity entity = DonViTinhMapper.toEntity(donViTinh);
            if (entity.getMaDVT() == null || entity.getMaDVT().isBlank()) {
                entity.setMaDVT(generateNewMaDVT());
                donViTinh.setMaDVT(entity.getMaDVT());
            }
            donViTinhRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(DonViTinhDto donViTinh) {
        if (donViTinh == null || donViTinh.getMaDVT() == null || donViTinh.getMaDVT().isBlank()) {
            return false;
        }
        try {
            donViTinhRepository.update(DonViTinhMapper.toEntity(donViTinh));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String maDonViTinh) {
        if (maDonViTinh == null || maDonViTinh.isBlank()) {
            return false;
        }
        try {
            return donViTinhRepository.deleteById(maDonViTinh);
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
