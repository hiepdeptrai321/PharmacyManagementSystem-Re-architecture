package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.server.entity.LuongNhanVienEntity;
import com.example.pharmacy.server.entity.NhanVienEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public final class NhanVienMapper {
    private NhanVienMapper() {}

    public static NhanVienDto toDto(NhanVienEntity entity) {
        return new NhanVienDto(
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

    public static NhanVienEntity toEntity(NhanVienDto model, NhanVienEntity existing) {
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

    public static LuongNhanVienEntity toLuongEntity(LuongNhanVienDto model) {
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

    public static LuongNhanVienDto toLuongDto(LuongNhanVienEntity entity) {
        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(entity.getMaNV());
        return new LuongNhanVienDto(
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
