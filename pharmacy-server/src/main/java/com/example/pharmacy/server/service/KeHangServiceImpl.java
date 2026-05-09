package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.KeHangEntity;
import com.example.pharmacy.server.repository.KeHangRepository;
import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.server.mapper.ClassMapper;

import java.util.List;
import java.util.Objects;

public class KeHangServiceImpl implements KeHangService {
    private final KeHangRepository keHangRepository;
    private final CodeGenerationService codeGenerationService;

    public KeHangServiceImpl(KeHangRepository keHangRepository, CodeGenerationService codeGenerationService) {
        this.keHangRepository = Objects.requireNonNull(keHangRepository, "keHangRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<KeHangDto> findAll() {
        return keHangRepository.findAll().stream().map(ClassMapper::toDto).toList();
    }

    @Override
    public KeHangDto findById(String maKeHang) {
        return keHangRepository.findById(maKeHang).map(ClassMapper::toDto).orElse(null);
    }

    @Override
    public KeHangDto findByTenKe(String tenKe) {
        return keHangRepository.findByTenKe(tenKe).map(ClassMapper::toDto).orElse(null);
    }

    @Override
    public String generateNewMaKeHang() {
        return codeGenerationService.nextCode(BusinessCodeType.KE_HANG);
    }

    @Override
    public boolean create(KeHangDto keHang) {
        if (keHang == null) {
            return false;
        }
        try {
            KeHangEntity entity = ClassMapper.toEntity(keHang);
            if (entity.getMaKe() == null || entity.getMaKe().isBlank()) {
                entity.setMaKe(generateNewMaKeHang());
                keHang.setMaKe(entity.getMaKe());
            }
            keHangRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(KeHangDto keHang) {
        if (keHang == null || keHang.getMaKe() == null || keHang.getMaKe().isBlank()) {
            return false;
        }
        try {
            keHangRepository.update(ClassMapper.toEntity(keHang));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String maKeHang) {
        if (maKeHang == null || maKeHang.isBlank()) {
            return false;
        }
        try {
            return keHangRepository.deleteById(maKeHang);
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<String> findThuocNamesByKeHang(String maKeHang) {
        if (maKeHang == null || maKeHang.isBlank()) {
            return List.of();
        }
        return keHangRepository.findThuocNamesByKeHang(maKeHang);
    }
}
