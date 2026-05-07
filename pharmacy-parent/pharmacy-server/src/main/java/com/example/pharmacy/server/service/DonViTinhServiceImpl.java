package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.DonViTinhEntity;
import com.example.pharmacy.server.repository.DonViTinhRepository;
import com.example.pharmacymanagementsystem_qlht.model.DonViTinh;

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
    public List<DonViTinh> findAll() {
        return donViTinhRepository.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public DonViTinh findById(String maDonViTinh) {
        return donViTinhRepository.findById(maDonViTinh).map(this::toModel).orElse(null);
    }

    @Override
    public DonViTinh findByTenDonViTinh(String tenDonViTinh) {
        return donViTinhRepository.findByTenDonViTinh(tenDonViTinh).map(this::toModel).orElse(null);
    }

    @Override
    public String generateNewMaDVT() {
        return codeGenerationService.nextCode(BusinessCodeType.DON_VI_TINH);
    }

    @Override
    public boolean create(DonViTinh donViTinh) {
        if (donViTinh == null) {
            return false;
        }
        try {
            DonViTinhEntity entity = toEntity(donViTinh);
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
    public boolean update(DonViTinh donViTinh) {
        if (donViTinh == null || donViTinh.getMaDVT() == null || donViTinh.getMaDVT().isBlank()) {
            return false;
        }
        try {
            donViTinhRepository.update(toEntity(donViTinh));
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

    private DonViTinh toModel(DonViTinhEntity entity) {
        DonViTinh donViTinh = new DonViTinh();
        donViTinh.setMaDVT(entity.getMaDVT());
        donViTinh.setTenDonViTinh(entity.getTenDonViTinh());
        donViTinh.setKiHieu(entity.getKiHieu());
        return donViTinh;
    }

    private DonViTinhEntity toEntity(DonViTinh model) {
        DonViTinhEntity entity = new DonViTinhEntity();
        entity.setMaDVT(model.getMaDVT());
        entity.setTenDonViTinh(model.getTenDonViTinh());
        entity.setKiHieu(model.getKiHieu());
        return entity;
    }
}
