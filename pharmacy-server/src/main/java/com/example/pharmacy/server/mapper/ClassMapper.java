package com.example.pharmacy.server.mapper;

import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.mapper.JacksonMapper;
import com.example.pharmacy.common.model.DonViTinhDto;
import com.example.pharmacy.common.model.KeHangDto;
import com.example.pharmacy.common.model.KhachHangDto;
import com.example.pharmacy.common.model.LuongNhanVienDto;
import com.example.pharmacy.common.model.NhaCungCapDto;
import com.example.pharmacy.common.model.NhanVienDto;
import com.example.pharmacy.common.model.NhomDuocLyDto;
import com.example.pharmacy.server.entity.DonViTinhEntity;
import com.example.pharmacy.server.entity.KeHangEntity;
import com.example.pharmacy.server.entity.KhachHangEntity;
import com.example.pharmacy.server.entity.LuongNhanVienEntity;
import com.example.pharmacy.server.entity.NhaCungCapEntity;
import com.example.pharmacy.server.entity.NhanVienEntity;
import com.example.pharmacy.server.entity.NhomDuocLyEntity;

import java.sql.Date;
import java.time.LocalDate;

public final class ClassMapper {
    private ClassMapper() {
    }

    public static DonViTinhDto toDto(DonViTinhEntity entity) {
        return JacksonMapper.map(entity, DonViTinhDto.class);
    }

    public static DonViTinhEntity toEntity(DonViTinhDto dto) {
        return JacksonMapper.map(dto, DonViTinhEntity.class);
    }

    public static KeHangDto toDto(KeHangEntity entity) {
        return JacksonMapper.map(entity, KeHangDto.class);
    }

    public static KeHangEntity toEntity(KeHangDto dto) {
        return JacksonMapper.map(dto, KeHangEntity.class);
    }

    public static NhomDuocLyDto toDto(NhomDuocLyEntity entity) {
        return JacksonMapper.map(entity, NhomDuocLyDto.class);
    }

    public static NhomDuocLyEntity toEntity(NhomDuocLyDto dto) {
        return JacksonMapper.map(dto, NhomDuocLyEntity.class);
    }

    public static NhaCungCapDto toDto(NhaCungCapEntity entity) {
        NhaCungCapDto dto = JacksonMapper.map(entity, NhaCungCapDto.class);
        if (dto == null) {
            return null;
        }
        dto.setSDT(entity.getSdt());
        dto.setGPKD(entity.getGpKD());
        dto.setMSThue(entity.getMsThue());
        return dto;
    }

    public static NhaCungCapEntity toEntity(NhaCungCapDto dto) {
        NhaCungCapEntity entity = JacksonMapper.map(dto, NhaCungCapEntity.class);
        if (entity == null) {
            return null;
        }
        entity.setSdt(dto.getSDT());
        entity.setGpKD(dto.getGPKD());
        entity.setMsThue(dto.getMSThue());
        return entity;
    }

    public static KhachHangDto toDto(KhachHangEntity entity) {
        return JacksonMapper.map(entity, KhachHangDto.class);
    }

    public static KhachHangEntity toEntity(KhachHangDto dto) {
        KhachHangEntity entity = JacksonMapper.map(dto, KhachHangEntity.class);
        if (entity == null) {
            return null;
        }
        entity.setGioiTinh(Boolean.TRUE.equals(dto.getGioiTinh()));
        entity.setTrangThai(dto.getTrangThai() == null || dto.getTrangThai());
        return entity;
    }

    public static NhanVienDto toDto(NhanVienEntity entity) {
        NhanVienDto dto = JacksonMapper.map(entity, NhanVienDto.class);
        if (dto == null) {
            return null;
        }
        dto.setNgayNghiLam(entity.getNgayKetThuc());
        return dto;
    }

    public static NhanVienEntity toEntity(NhanVienDto model, NhanVienEntity existing) {
        if (model == null) {
            return null;
        }
        NhanVienEntity entity = JacksonMapper.map(model, NhanVienEntity.class);
        entity.setNgayKetThuc(model.getNgayNghiLam());
        entity.setTaiKhoan(model.getTaiKhoan() == null || model.getTaiKhoan().isBlank()
                ? existing != null ? existing.getTaiKhoan() : entity.getTaiKhoan()
                : model.getTaiKhoan().trim());
        entity.setMatKhau(model.getMatKhau() == null || model.getMatKhau().isBlank()
                ? existing != null ? existing.getMatKhau() : entity.getMatKhau()
                : model.getMatKhau());
        entity.setNgayVaoLam(model.getNgayVaoLam() != null ? model.getNgayVaoLam() : Date.valueOf(LocalDate.now()));

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
        LuongNhanVienEntity entity = JacksonMapper.map(model, LuongNhanVienEntity.class);
        if (entity == null) {
            return null;
        }
        entity.setMaNV(model.getNhanVien() == null ? null : model.getNhanVien().getMaNV());
        return entity;
    }

    public static LuongNhanVienDto toLuongDto(LuongNhanVienEntity entity) {
        LuongNhanVienDto dto = JacksonMapper.map(entity, LuongNhanVienDto.class);
        if (dto == null) {
            return null;
        }
        NhanVienDto nhanVien = new NhanVienDto();
        nhanVien.setMaNV(entity.getMaNV());
        dto.setNhanVien(nhanVien);
        return dto;
    }
}

