package com.example.pharmacy.server.service;

import com.example.pharmacy.common.enums.BusinessCodeType;
import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.server.entity.LuongNhanVienEntity;
import com.example.pharmacy.server.entity.NhanVienEntity;
import com.example.pharmacy.server.repository.LuongNhanVienRepository;
import com.example.pharmacy.server.repository.NhanVienManagementRepository;
import com.example.pharmacymanagementsystem_qlht.model.LuongNhanVien;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
    public List<NhanVien> findAll() {
        return nhanVienRepository.findAllNotDeleted().stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public NhanVien findById(String maNhanVien) {
        return nhanVienRepository.findById(maNhanVien)
                .filter(entity -> !entity.isTrangThaiXoa())
                .map(this::toModel)
                .orElse(null);
    }

    @Override
    public String generateNewMaNhanVien() {
        return codeGenerationService.nextCode(BusinessCodeType.NHAN_VIEN);
    }

    @Override
    public boolean create(NhanVien nhanVien) {
        if (!isValidEmployeeRequest(nhanVien)) {
            return false;
        }
        try {
            if (!isUsernameAvailable(nhanVien.getTaiKhoan(), null)) {
                return false;
            }
            NhanVienEntity entity = toEntity(nhanVien, null);
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
    public boolean update(NhanVien nhanVien) {
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
            nhanVienRepository.update(toEntity(nhanVien, existing));
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
    public List<LuongNhanVien> findLuongByMaNhanVien(String maNhanVien) {
        if (maNhanVien == null || maNhanVien.isBlank()) {
            return List.of();
        }
        return luongNhanVienRepository.findByMaNhanVien(maNhanVien).stream()
                .map(this::toLuongModel)
                .toList();
    }

    @Override
    public String generateNewMaLuongNhanVien() {
        return codeGenerationService.nextCode(BusinessCodeType.LUONG_NHAN_VIEN);
    }

    @Override
    public boolean saveLuongNhanVien(LuongNhanVien luongNhanVien) {
        if (luongNhanVien == null || luongNhanVien.getNhanVien() == null
                || luongNhanVien.getNhanVien().getMaNV() == null || luongNhanVien.getNhanVien().getMaNV().isBlank()) {
            return false;
        }
        try {
            LuongNhanVienEntity entity = toLuongEntity(luongNhanVien);
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

    private boolean isValidEmployeeRequest(NhanVien nhanVien) {
        return nhanVien != null
                && nhanVien.getTenNV() != null && !nhanVien.getTenNV().isBlank()
                && nhanVien.getSdt() != null && !nhanVien.getSdt().isBlank()
                && nhanVien.getNgaySinh() != null
                && nhanVien.getTaiKhoan() != null && !nhanVien.getTaiKhoan().isBlank()
                && nhanVien.getMatKhau() != null && !nhanVien.getMatKhau().isBlank();
    }

    private NhanVienEntity toEntity(NhanVien model, NhanVienEntity existing) {
        NhanVienEntity entity = existing == null ? new NhanVienEntity() : existing;
        entity.setMaNV(model.getMaNV());
        entity.setTenNV(model.getTenNV());
        entity.setSdt(model.getSdt());
        entity.setEmail(model.getEmail());
        entity.setNgaySinh(model.getNgaySinh());
        entity.setGioiTinh(model.isGioiTinh());
        entity.setDiaChi(model.getDiaChi());
        entity.setTrangThai(model.isTrangThai());
        entity.setTaiKhoan(model.getTaiKhoan() == null || model.getTaiKhoan().isBlank() ? entity.getTaiKhoan() : model.getTaiKhoan().trim());
        entity.setMatKhau(model.getMatKhau() == null || model.getMatKhau().isBlank() ? entity.getMatKhau() : model.getMatKhau());
        entity.setNgayVaoLam(model.getNgayVaoLam() != null ? model.getNgayVaoLam() : Date.valueOf(LocalDate.now()));
        entity.setNgayKetThuc(model.getNgayNghiLam());
        entity.setTrangThaiXoa(model.isTrangThaiXoa());

        String vaiTro = model.getVaiTro();
        if (vaiTro == null || vaiTro.isBlank()) {
            vaiTro = existing != null && existing.getVaiTro() != null && !existing.getVaiTro().isBlank()
                    ? existing.getVaiTro()
                    : UserRole.STAFF.toLegacyValue();
        }
        entity.setVaiTro(vaiTro);
        return entity;
    }

    private NhanVien toModel(NhanVienEntity entity) {
        return new NhanVien(
                entity.getMaNV(),
                entity.getTenNV(),
                entity.getSdt(),
                entity.getEmail(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getDiaChi(),
                entity.isTrangThai(),
                entity.getTaiKhoan(),
                entity.getMatKhau(),
                entity.getNgayVaoLam(),
                entity.getNgayKetThuc(),
                entity.isTrangThaiXoa(),
                entity.getVaiTro()
        );
    }

    private LuongNhanVienEntity toLuongEntity(LuongNhanVien model) {
        LuongNhanVienEntity entity = new LuongNhanVienEntity();
        entity.setMaLNV(model.getMaLNV());
        entity.setTuNgay(model.getTuNgay());
        entity.setDenNgay(model.getDenNgay());
        entity.setLuongCoBan(BigDecimal.valueOf(model.getLuongCoBan()));
        entity.setPhuCap(BigDecimal.valueOf(model.getPhuCap()));
        entity.setGhiChu(model.getGhiChu());
        entity.setMaNV(model.getNhanVien().getMaNV());
        return entity;
    }

    private LuongNhanVien toLuongModel(LuongNhanVienEntity entity) {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(entity.getMaNV());
        return new LuongNhanVien(
                entity.getMaLNV(),
                entity.getTuNgay(),
                entity.getDenNgay(),
                entity.getLuongCoBan() == null ? 0.0 : entity.getLuongCoBan().doubleValue(),
                entity.getPhuCap() == null ? 0.0 : entity.getPhuCap().doubleValue(),
                entity.getGhiChu(),
                nhanVien
        );
    }
}
