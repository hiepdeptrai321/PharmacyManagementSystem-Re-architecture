package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.NhomDuocLyEntity;
import com.example.pharmacy.server.repository.NhomDuocLyRepository;
import com.example.pharmacymanagementsystem_qlht.model.NhomDuocLy;

import java.util.List;
import java.util.Objects;

public class NhomDuocLyServiceImpl implements NhomDuocLyService {
    private final NhomDuocLyRepository nhomDuocLyRepository;
    private final CodeGenerationService codeGenerationService;

    public NhomDuocLyServiceImpl(NhomDuocLyRepository nhomDuocLyRepository, CodeGenerationService codeGenerationService) {
        this.nhomDuocLyRepository = Objects.requireNonNull(nhomDuocLyRepository, "nhomDuocLyRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<NhomDuocLy> findAll() {
        return nhomDuocLyRepository.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public NhomDuocLy findById(String maNhomDuocLy) {
        return nhomDuocLyRepository.findById(maNhomDuocLy).map(this::toModel).orElse(null);
    }

    @Override
    public String generateNewMaNhomDuocLy() {
        return codeGenerationService.nextCode(BusinessCodeType.NHOM_DUOC_LY);
    }

    @Override
    public boolean create(NhomDuocLy nhomDuocLy) {
        if (nhomDuocLy == null) {
            return false;
        }
        try {
            NhomDuocLyEntity entity = toEntity(nhomDuocLy);
            if (entity.getMaNDL() == null || entity.getMaNDL().isBlank()) {
                entity.setMaNDL(generateNewMaNhomDuocLy());
                nhomDuocLy.setMaNDL(entity.getMaNDL());
            }
            nhomDuocLyRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(NhomDuocLy nhomDuocLy) {
        if (nhomDuocLy == null || nhomDuocLy.getMaNDL() == null || nhomDuocLy.getMaNDL().isBlank()) {
            return false;
        }
        try {
            nhomDuocLyRepository.update(toEntity(nhomDuocLy));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean deleteById(String maNhomDuocLy) {
        if (maNhomDuocLy == null || maNhomDuocLy.isBlank()) {
            return false;
        }
        if (!findThuocNamesByNhomDuocLy(maNhomDuocLy).isEmpty()) {
            return false;
        }
        try {
            return nhomDuocLyRepository.deleteById(maNhomDuocLy);
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<String> findThuocNamesByNhomDuocLy(String maNhomDuocLy) {
        if (maNhomDuocLy == null || maNhomDuocLy.isBlank()) {
            return List.of();
        }
        return nhomDuocLyRepository.findThuocNamesByNhomDuocLy(maNhomDuocLy);
    }

    private NhomDuocLy toModel(NhomDuocLyEntity entity) {
        NhomDuocLy nhomDuocLy = new NhomDuocLy();
        nhomDuocLy.setMaNDL(entity.getMaNDL());
        nhomDuocLy.setTenNDL(entity.getTenNDL());
        nhomDuocLy.setMoTa(entity.getMoTa());
        return nhomDuocLy;
    }

    private NhomDuocLyEntity toEntity(NhomDuocLy model) {
        NhomDuocLyEntity entity = new NhomDuocLyEntity();
        entity.setMaNDL(model.getMaNDL());
        entity.setTenNDL(model.getTenNDL());
        entity.setMoTa(model.getMoTa());
        return entity;
    }
}
