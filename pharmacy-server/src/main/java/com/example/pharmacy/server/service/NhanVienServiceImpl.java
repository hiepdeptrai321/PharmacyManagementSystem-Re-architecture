package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.server.entity.LuongNhanVienEntity;
import com.example.pharmacy.server.entity.NhanVienEntity;
import com.example.pharmacy.server.mapper.NhanVienMapper;
import com.example.pharmacy.server.repository.LuongNhanVienRepository;
import com.example.pharmacy.server.repository.NhanVienManagementRepository;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;

import java.util.List;
import java.util.Objects;

public class NhanVienServiceImpl implements NhanVienService {
    private final NhanVienManagementRepository nhanVienRepository;
    private final LuongNhanVienRepository luongNhanVienRepository;
    private final CodeGenerationService codeGenerationService;

    public NhanVienServiceImpl(NhanVienManagementRepository nhanVienRepository,
                               LuongNhanVienRepository luongNhanVienRepository,
                               CodeGenerationService codeGenerationService) {
        this.nhanVienRepository = Objects.requireNonNull(nhanVienRepository, "nhanVienRepository must not be null");
        this.luongNhanVienRepository = Objects.requireNonNull(luongNhanVienRepository, "luongNhanVienRepository must not be null");
        this.codeGenerationService = Objects.requireNonNull(codeGenerationService, "codeGenerationService must not be null");
    }

    @Override
    public List<NhanVienDto> findAll() {
        return nhanVienRepository.findAllNotDeleted().stream()
                .map(NhanVienMapper::toDto)
                .toList();
    }

    @Override
    public NhanVienDto findById(String maNhanVien) {
        return nhanVienRepository.findById(maNhanVien)
                .filter(entity -> !entity.isTrangThaiXoa())
                .map(NhanVienMapper::toDto)
                .orElse(null);
    }

    @Override
    public String generateNewMaNhanVien() {
        return codeGenerationService.nextCode(BusinessCodeType.NHAN_VIEN);
    }

    @Override
    public boolean create(NhanVienDto nhanVien) {
        if (!isValidEmployeeRequest(nhanVien)) {
            return false;
        }
        try {
            if (!isUsernameAvailable(nhanVien.getTaiKhoan(), null)) {
                return false;
            }
            NhanVienEntity entity = NhanVienMapper.toEntity(nhanVien, null);
            if (entity.getMaNV() == null || entity.getMaNV().isBlank()) {
                entity.setMaNV(generateNewMaNhanVien());
                nhanVien.setMaNV(entity.getMaNV());
            }
            nhanVienRepository.save(entity);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean update(NhanVienDto nhanVien) {
        if (nhanVien == null || nhanVien.getMaNV() == null || nhanVien.getMaNV().isBlank()) {
            return false;
        }
        try {
            NhanVienEntity existing = nhanVienRepository.findById(nhanVien.getMaNV()).orElse(null);
            if (existing == null || existing.isTrangThaiXoa()) {
                return false;
            }
            if (!isUsernameAvailable(nhanVien.getTaiKhoan(), nhanVien.getMaNV())) {
                return false;
            }
            nhanVienRepository.update(NhanVienMapper.toEntity(nhanVien, existing));
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean softDelete(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isBlank()) {
            return false;
        }
        try {
            return nhanVienRepository.softDelete(maNhanVien);
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean isUsernameAvailable(String username, String excludedMaNhanVien) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return !nhanVienRepository.existsByUsername(username.trim(), excludedMaNhanVien);
    }

    @Override
    public List<LuongNhanVienDto> findLuongByMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isBlank()) {
            return List.of();
        }
        return luongNhanVienRepository.findByMaNhanVien(maNhanVien).stream()
                .map(NhanVienMapper::toLuongDto)
                .toList();
    }

    @Override
    public String generateNewMaLuongNhanVien() {
        return codeGenerationService.nextCode(BusinessCodeType.LUONG_NHAN_VIEN);
    }

    @Override
    public boolean saveLuongNhanVien(LuongNhanVienDto luongNhanVien) {
        if (luongNhanVien == null || luongNhanVien.getNhanVien() == null
                || luongNhanVien.getNhanVien().getMaNV() == null || luongNhanVien.getNhanVien().getMaNV().isBlank()) {
            return false;
        }
        try {
            LuongNhanVienEntity entity = NhanVienMapper.toLuongEntity(luongNhanVien);
            if (entity.getMaLNV() == null || entity.getMaLNV().isBlank()) {
                entity.setMaLNV(generateNewMaLuongNhanVien());
                luongNhanVien.setMaLNV(entity.getMaLNV());
            }

            if (luongNhanVienRepository.findById(entity.getMaLNV()).isPresent()) {
                luongNhanVienRepository.update(entity);
            } else {
                luongNhanVienRepository.save(entity);
            }
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private boolean isValidEmployeeRequest(NhanVienDto nhanVien) {
        return nhanVien != null
                && nhanVien.getTenNV() != null && !nhanVien.getTenNV().isBlank()
                && nhanVien.getSdt() != null && !nhanVien.getSdt().isBlank()
                && nhanVien.getNgaySinh() != null
                && nhanVien.getTaiKhoan() != null && !nhanVien.getTaiKhoan().isBlank()
                && nhanVien.getMatKhau() != null && !nhanVien.getMatKhau().isBlank();
    }
}
