package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.KhachHangEntity;
import com.example.pharmacy.server.repository.KhachHangRepository;
import com.example.pharmacy.common.model.KhachHang;

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
    public List<KhachHang> findAll() {
        return khachHangRepository.findAllActive().stream().map(this::toModel).toList();
    }

    @Override
    public KhachHang findById(String maKhachHang) {
        return khachHangRepository.findById(maKhachHang).map(this::toModel).orElse(null);
    }

    @Override
    public String generateNewMaKH() {
        return codeGenerationService.nextCode(BusinessCodeType.KHACH_HANG);
    }

    @Override
    public boolean create(KhachHang khachHang) {
        if (khachHang == null) {
            return false;
        }
        try {
            KhachHangEntity entity = toEntity(khachHang);
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
    public boolean save(KhachHang khachHang) {
        if (khachHang == null) {
            return false;
        }
        if (khachHang.getMaKH() == null || khachHang.getMaKH().isBlank()) {
            return create(khachHang);
        }
        try {
            khachHangRepository.update(toEntity(khachHang));
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

    private KhachHang toModel(KhachHangEntity entity) {
        KhachHang khachHang = new KhachHang();
        khachHang.setMaKH(entity.getMaKH());
        khachHang.setTenKH(entity.getTenKH());
        khachHang.setSdt(entity.getSdt());
        khachHang.setEmail(entity.getEmail());
        khachHang.setNgaySinh(entity.getNgaySinh());
        khachHang.setGioiTinh(entity.isGioiTinh());
        khachHang.setDiaChi(entity.getDiaChi());
        khachHang.setTrangThai(entity.isTrangThai());
        return khachHang;
    }

    private KhachHangEntity toEntity(KhachHang model) {
        KhachHangEntity entity = new KhachHangEntity();
        entity.setMaKH(model.getMaKH());
        entity.setTenKH(model.getTenKH());
        entity.setSdt(model.getSdt());
        entity.setEmail(model.getEmail());
        entity.setNgaySinh(model.getNgaySinh());
        entity.setGioiTinh(Boolean.TRUE.equals(model.getGioiTinh()));
        entity.setDiaChi(model.getDiaChi());
        entity.setTrangThai(model.getTrangThai() == null || model.getTrangThai());
        return entity;
    }
}
