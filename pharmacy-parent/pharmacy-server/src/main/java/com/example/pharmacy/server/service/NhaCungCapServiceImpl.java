package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.NhaCungCapEntity;
import com.example.pharmacy.server.repository.NhaCungCapRepository;
import com.example.pharmacymanagementsystem_qlht.model.NhaCungCap;

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
    public List<NhaCungCap> findAll() {
        return nhaCungCapRepository.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public NhaCungCap findById(String maNhaCungCap) {
        return nhaCungCapRepository.findById(maNhaCungCap).map(this::toModel).orElse(null);
    }

    @Override
    public String generateNewMaNCC() {
        return codeGenerationService.nextCode(BusinessCodeType.NHA_CUNG_CAP);
    }

    @Override
    public boolean create(NhaCungCap nhaCungCap) {
        if (nhaCungCap == null) {
            return false;
        }
        try {
            NhaCungCapEntity entity = toEntity(nhaCungCap);
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
    public boolean update(NhaCungCap nhaCungCap) {
        if (nhaCungCap == null || nhaCungCap.getMaNCC() == null || nhaCungCap.getMaNCC().isBlank()) {
            return false;
        }
        try {
            nhaCungCapRepository.update(toEntity(nhaCungCap));
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

    private NhaCungCap toModel(NhaCungCapEntity entity) {
        NhaCungCap nhaCungCap = new NhaCungCap();
        nhaCungCap.setMaNCC(entity.getMaNCC());
        nhaCungCap.setTenNCC(entity.getTenNCC());
        nhaCungCap.setDiaChi(entity.getDiaChi());
        nhaCungCap.setSDT(entity.getSdt());
        nhaCungCap.setEmail(entity.getEmail());
        nhaCungCap.setGPKD(entity.getGpKD());
        nhaCungCap.setGhiChu(entity.getGhiChu());
        nhaCungCap.setTenCongTy(entity.getTenCongTy());
        nhaCungCap.setMSThue(entity.getMsThue());
        return nhaCungCap;
    }

    private NhaCungCapEntity toEntity(NhaCungCap model) {
        NhaCungCapEntity entity = new NhaCungCapEntity();
        entity.setMaNCC(model.getMaNCC());
        entity.setTenNCC(model.getTenNCC());
        entity.setDiaChi(model.getDiaChi());
        entity.setSdt(model.getSDT());
        entity.setEmail(model.getEmail());
        entity.setGpKD(model.getGPKD());
        entity.setGhiChu(model.getGhiChu());
        entity.setTenCongTy(model.getTenCongTy());
        entity.setMsThue(model.getMSThue());
        return entity;
    }
}
