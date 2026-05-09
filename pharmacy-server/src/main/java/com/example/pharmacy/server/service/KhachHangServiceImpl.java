package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.KhachHangEntity;
import com.example.pharmacy.server.mapper.KhachHangMapper;
import com.example.pharmacy.server.repository.KhachHangRepository;
import com.example.pharmacy.common.model.KhachHangDto;

import java.util.List;
import java.util.Objects;

public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;
    private final CodeGenerationService codeGenerationService;

    public KhachHangServiceImpl(KhachHangRepository khachHangRepository, CodeGenerationService codeGenerationService) {
        this.khachHangRepository = Objects.requireNonNull(khachHangRepository, "khachHangRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<KhachHangDto> findAll() {
        return khachHangRepository.findAllActive().stream().map(KhachHangMapper::toDto).toList();
    }

    @Override
    public KhachHangDto findById(String maKhachHang) {
        return khachHangRepository.findById(maKhachHang).map(KhachHangMapper::toDto).orElse(null);
    }

    @Override
    public String generateNewMaKH() {
        return codeGenerationService.nextCode(BusinessCodeType.KHACH_HANG);
    }

    @Override
    public boolean create(KhachHangDto khachHang) {
        if (khachHang == null) {
            return false;
        }
        try {
            KhachHangEntity entity = KhachHangMapper.toEntity(khachHang);
            if (entity.getMaKH() == null || entity.getMaKH().isBlank()) {
                entity.setMaKH(generateNewMaKH());
                khachHang.setMaKH(entity.getMaKH());
            }
            khachHangRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean save(KhachHangDto khachHang) {
        if (khachHang == null) {
            return false;
        }
        if (khachHang.getMaKH() == null || khachHang.getMaKH().isBlank()) {
            return create(khachHang);
        }
        try {
            khachHangRepository.update(KhachHangMapper.toEntity(khachHang));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String maKhachHang) {
        if (maKhachHang == null || maKhachHang.isBlank()) {
            return false;
        }
        try {
            return khachHangRepository.softDelete(maKhachHang);
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
