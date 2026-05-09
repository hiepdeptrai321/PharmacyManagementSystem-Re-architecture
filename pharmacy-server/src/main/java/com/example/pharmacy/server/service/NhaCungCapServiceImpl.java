package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.NhaCungCapEntity;
import com.example.pharmacy.server.mapper.NhaCungCapMapper;
import com.example.pharmacy.server.repository.NhaCungCapRepository;
import com.example.pharmacy.common.model.NhaCungCapDto;

import java.util.List;
import java.util.Objects;

public class NhaCungCapServiceImpl implements NhaCungCapService {
    private final NhaCungCapRepository nhaCungCapRepository;
    private final CodeGenerationService codeGenerationService;

    public NhaCungCapServiceImpl(NhaCungCapRepository nhaCungCapRepository, CodeGenerationService codeGenerationService) {
        this.nhaCungCapRepository = Objects.requireNonNull(nhaCungCapRepository, "nhaCungCapRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<NhaCungCapDto> findAll() {
        return nhaCungCapRepository.findAll().stream().map(NhaCungCapMapper::toDto).toList();
    }

    @Override
    public NhaCungCapDto findById(String maNhaCungCap) {
        return nhaCungCapRepository.findById(maNhaCungCap).map(NhaCungCapMapper::toDto).orElse(null);
    }

    @Override
    public String generateNewMaNCC() {
        return codeGenerationService.nextCode(BusinessCodeType.NHA_CUNG_CAP);
    }

    @Override
    public boolean create(NhaCungCapDto nhaCungCap) {
        if (nhaCungCap == null) {
            return false;
        }
        try {
            NhaCungCapEntity entity = NhaCungCapMapper.toEntity(nhaCungCap);
            if (entity.getMaNCC() == null || entity.getMaNCC().isBlank()) {
                entity.setMaNCC(generateNewMaNCC());
                nhaCungCap.setMaNCC(entity.getMaNCC());
            }
            nhaCungCapRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(NhaCungCapDto nhaCungCap) {
        if (nhaCungCap == null || nhaCungCap.getMaNCC() == null || nhaCungCap.getMaNCC().isBlank()) {
            return false;
        }
        try {
            nhaCungCapRepository.update(NhaCungCapMapper.toEntity(nhaCungCap));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String maNhaCungCap) {
        if (maNhaCungCap == null || maNhaCungCap.isBlank()) {
            return false;
        }
        try {
            return nhaCungCapRepository.deleteById(maNhaCungCap);
        } catch (RuntimeException exception) {
            return false;
        }
    }
}
