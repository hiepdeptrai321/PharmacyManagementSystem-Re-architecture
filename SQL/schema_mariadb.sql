SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `quan_ly_nha_thuoc`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `quan_ly_nha_thuoc`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `ChiTietPhieuTraHang`;
DROP TABLE IF EXISTS `PhieuTraHang`;
DROP TABLE IF EXISTS `ChiTietPhieuDoiHang`;
DROP TABLE IF EXISTS `PhieuDoiHang`;
DROP TABLE IF EXISTS `ChiTietPhieuDatHang`;
DROP TABLE IF EXISTS `PhieuDatHang`;
DROP TABLE IF EXISTS `HoatDong`;
DROP TABLE IF EXISTS `ChiTietHoaDon`;
DROP TABLE IF EXISTS `HoaDon`;
DROP TABLE IF EXISTS `Thuoc_SP_TheoLo`;
DROP TABLE IF EXISTS `ChiTietPhieuNhap`;
DROP TABLE IF EXISTS `PhieuNhap`;
DROP TABLE IF EXISTS `Thuoc_SP_TangKem`;
DROP TABLE IF EXISTS `ChiTietKhuyenMai`;
DROP TABLE IF EXISTS `KhuyenMai`;
DROP TABLE IF EXISTS `LoaiKhuyenMai`;
DROP TABLE IF EXISTS `ChiTietHoatChat`;
DROP TABLE IF EXISTS `HoatChat`;
DROP TABLE IF EXISTS `ChiTietDonViTinh`;
DROP TABLE IF EXISTS `DonViTinh`;
DROP TABLE IF EXISTS `Thuoc_SanPham`;
DROP TABLE IF EXISTS `KeHang`;
DROP TABLE IF EXISTS `NhomDuocLy`;
DROP TABLE IF EXISTS `LoaiHang`;
DROP TABLE IF EXISTS `NhaCungCap`;
DROP TABLE IF EXISTS `LuongNhanVien`;
DROP TABLE IF EXISTS `NhanVien`;
DROP TABLE IF EXISTS `KhachHang`;
DROP TABLE IF EXISTS `ThongSoUngDung`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `KhachHang` (
    `MaKH` VARCHAR(10) NOT NULL,
    `TenKH` VARCHAR(50) NOT NULL,
    `SDT` VARCHAR(15) NOT NULL,
    `Email` VARCHAR(100) NULL,
    `NgaySinh` DATE NULL,
    `GioiTinh` TINYINT(1) NOT NULL COMMENT '0 = Nu, 1 = Nam',
    `DiaChi` VARCHAR(255) NULL,
    `TrangThai` TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`MaKH`),
    KEY `idx_khachhang_sdt` (`SDT`),
    KEY `idx_khachhang_tenkh` (`TenKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `NhanVien` (
    `MaNV` VARCHAR(10) NOT NULL,
    `TenNV` VARCHAR(50) NOT NULL,
    `SDT` VARCHAR(15) NOT NULL,
    `Email` VARCHAR(100) NULL,
    `NgaySinh` DATE NOT NULL,
    `GioiTinh` TINYINT(1) NOT NULL COMMENT 'Chon TINYINT(1) de khop Java model hien tai; can review vi script cu luu gia tri chuoi Nam/Nu',
    `DiaChi` VARCHAR(255) NULL,
    `VaiTro` VARCHAR(20) NOT NULL,
    `TrangThai` TINYINT(1) NOT NULL DEFAULT 1,
    `TaiKhoan` VARCHAR(50) NOT NULL,
    `MatKhau` VARCHAR(255) NOT NULL COMMENT 'Tam thoi cho phep plain text theo logic do an hien tai; nen hash o buoc bao mat sau',
    `NgayVaoLam` DATE NOT NULL,
    `NgayKetThuc` DATE NULL,
    `TrangThaiXoa` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`MaNV`),
    UNIQUE KEY `uq_nhanvien_taikhoan` (`TaiKhoan`),
    KEY `idx_nhanvien_tennv` (`TenNV`),
    KEY `idx_nhanvien_sdt` (`SDT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `LuongNhanVien` (
    `MaLNV` VARCHAR(10) NOT NULL,
    `TuNgay` DATE NOT NULL,
    `DenNgay` DATE NULL,
    `LuongCoBan` DECIMAL(18,2) NOT NULL,
    `PhuCap` DECIMAL(18,2) NOT NULL,
    `GhiChu` VARCHAR(255) NOT NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaLNV`),
    KEY `idx_luongnhanvien_manv` (`MaNV`),
    CONSTRAINT `fk_luongnhanvien_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `NhaCungCap` (
    `MaNCC` VARCHAR(10) NOT NULL,
    `TenNCC` VARCHAR(100) NOT NULL,
    `DiaChi` VARCHAR(255) NULL,
    `SDT` VARCHAR(20) NOT NULL,
    `Email` VARCHAR(100) NULL,
    `GPKD` VARCHAR(50) NULL,
    `GhiChu` TEXT NULL,
    `TenCongTy` VARCHAR(100) NULL,
    `MSThue` VARCHAR(20) NULL,
    PRIMARY KEY (`MaNCC`),
    KEY `idx_nhacungcap_tenncc` (`TenNCC`),
    KEY `idx_nhacungcap_sdt` (`SDT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `LoaiHang` (
    `MaLoaiHang` VARCHAR(10) NOT NULL,
    `TenLH` VARCHAR(50) NULL,
    `MoTa` TEXT NULL,
    PRIMARY KEY (`MaLoaiHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `NhomDuocLy` (
    `MaNDL` VARCHAR(10) NOT NULL,
    `TenNDL` VARCHAR(50) NULL,
    `MoTa` TEXT NULL,
    PRIMARY KEY (`MaNDL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `KeHang` (
    `MaKe` VARCHAR(10) NOT NULL,
    `TenKe` VARCHAR(50) NULL,
    `MoTa` TEXT NULL,
    PRIMARY KEY (`MaKe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `DonViTinh` (
    `MaDVT` VARCHAR(10) NOT NULL,
    `TenDonViTinh` VARCHAR(50) NOT NULL,
    `KiHieu` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `HoatChat` (
    `MaHoatChat` VARCHAR(10) NOT NULL,
    `TenHoatChat` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`MaHoatChat`),
    KEY `idx_hoatchat_ten` (`TenHoatChat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `LoaiKhuyenMai` (
    `MaLoai` VARCHAR(10) NOT NULL,
    `TenLoai` VARCHAR(50) NULL,
    `MoTa` TEXT NULL,
    PRIMARY KEY (`MaLoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ThongSoUngDung` (
    `TenThongSo` VARCHAR(50) NOT NULL,
    `GiaTri` VARCHAR(255) NULL,
    PRIMARY KEY (`TenThongSo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Thuoc_SanPham` (
    `MaThuoc` VARCHAR(10) NOT NULL,
    `TenThuoc` VARCHAR(100) NOT NULL,
    `HamLuong` INT NULL,
    `DonViHL` VARCHAR(20) NULL,
    `DuongDung` VARCHAR(50) NULL,
    `QuyCachDongGoi` VARCHAR(255) NULL,
    `SDK_GPNK` VARCHAR(20) NULL,
    `HangSX` VARCHAR(100) NULL,
    `NuocSX` VARCHAR(100) NULL,
    `HinhAnh` MEDIUMBLOB NULL,
    `MaLoaiHang` VARCHAR(10) NULL,
    `MaNDL` VARCHAR(10) NULL,
    `ViTri` VARCHAR(10) NULL,
    `TrangThaiXoa` TINYINT(1) NOT NULL DEFAULT 0,
    `ETC` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`MaThuoc`),
    KEY `idx_thuoc_tenthuoc` (`TenThuoc`),
    KEY `idx_thuoc_maloaihang` (`MaLoaiHang`),
    KEY `idx_thuoc_mandl` (`MaNDL`),
    KEY `idx_thuoc_vitri` (`ViTri`),
    CONSTRAINT `fk_thuoc_loaihang`
        FOREIGN KEY (`MaLoaiHang`) REFERENCES `LoaiHang` (`MaLoaiHang`),
    CONSTRAINT `fk_thuoc_nhomduocly`
        FOREIGN KEY (`MaNDL`) REFERENCES `NhomDuocLy` (`MaNDL`),
    CONSTRAINT `fk_thuoc_kehang`
        FOREIGN KEY (`ViTri`) REFERENCES `KeHang` (`MaKe`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietHoatChat` (
    `MaHoatChat` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `HamLuong` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`MaHoatChat`, `MaThuoc`),
    KEY `idx_cthoatchat_mathuoc` (`MaThuoc`),
    CONSTRAINT `fk_cthoatchat_hoatchat`
        FOREIGN KEY (`MaHoatChat`) REFERENCES `HoatChat` (`MaHoatChat`),
    CONSTRAINT `fk_cthoatchat_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietDonViTinh` (
    `MaThuoc` VARCHAR(10) NOT NULL,
    `MaDVT` VARCHAR(10) NOT NULL,
    `HeSoQuyDoi` DECIMAL(18,4) NOT NULL,
    `GiaNhap` DECIMAL(18,2) NOT NULL,
    `GiaBan` DECIMAL(18,2) NOT NULL,
    `DonViCoBan` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`MaThuoc`, `MaDVT`),
    KEY `idx_ctdvt_madvt` (`MaDVT`),
    KEY `idx_ctdvt_donvicoban` (`DonViCoBan`),
    CONSTRAINT `fk_ctdvt_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctdvt_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `KhuyenMai` (
    `MaKM` VARCHAR(10) NOT NULL,
    `TenKM` VARCHAR(50) NOT NULL,
    `GiaTriKM` DECIMAL(18,2) NULL,
    `GiaTriApDung` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    `LoaiGiaTri` VARCHAR(20) NULL,
    `NgayBatDau` DATE NOT NULL,
    `NgayKetThuc` DATE NOT NULL,
    `MoTa` TEXT NULL,
    `NgayTao` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `MaLoai` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaKM`),
    KEY `idx_khuyenmai_maloai` (`MaLoai`),
    KEY `idx_khuyenmai_ngay` (`NgayBatDau`, `NgayKetThuc`),
    CONSTRAINT `fk_khuyenmai_loaikhuyenmai`
        FOREIGN KEY (`MaLoai`) REFERENCES `LoaiKhuyenMai` (`MaLoai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietKhuyenMai` (
    `MaThuoc` VARCHAR(10) NOT NULL,
    `MaKM` VARCHAR(10) NOT NULL,
    `SLApDung` INT NOT NULL,
    `SLToiDa` INT NOT NULL,
    PRIMARY KEY (`MaThuoc`, `MaKM`),
    KEY `idx_ctkm_makm` (`MaKM`),
    CONSTRAINT `fk_ctkm_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctkm_khuyenmai`
        FOREIGN KEY (`MaKM`) REFERENCES `KhuyenMai` (`MaKM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Thuoc_SP_TangKem` (
    `MaKM` VARCHAR(10) NOT NULL,
    `MaThuocTangKem` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    PRIMARY KEY (`MaKM`, `MaThuocTangKem`),
    KEY `idx_tangkem_mathuoc` (`MaThuocTangKem`),
    CONSTRAINT `fk_tangkem_khuyenmai`
        FOREIGN KEY (`MaKM`) REFERENCES `KhuyenMai` (`MaKM`),
    CONSTRAINT `fk_tangkem_thuoc`
        FOREIGN KEY (`MaThuocTangKem`) REFERENCES `Thuoc_SanPham` (`MaThuoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `PhieuNhap` (
    `MaPN` VARCHAR(10) NOT NULL,
    `NgayNhap` DATE NOT NULL,
    `TrangThai` TINYINT(1) NULL DEFAULT 1,
    `GhiChu` TEXT NULL,
    `MaNCC` VARCHAR(10) NOT NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaPN`),
    KEY `idx_phieunhap_ngaynhap` (`NgayNhap`),
    KEY `idx_phieunhap_mancc` (`MaNCC`),
    KEY `idx_phieunhap_manv` (`MaNV`),
    CONSTRAINT `fk_phieunhap_nhacungcap`
        FOREIGN KEY (`MaNCC`) REFERENCES `NhaCungCap` (`MaNCC`),
    CONSTRAINT `fk_phieunhap_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietPhieuNhap` (
    `MaPN` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `MaLH` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    `MaDVT` VARCHAR(10) NULL,
    `GiaNhap` DECIMAL(18,2) NOT NULL,
    `ChietKhau` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    `Thue` DECIMAL(5,4) NOT NULL DEFAULT 0.0000,
    PRIMARY KEY (`MaPN`, `MaThuoc`, `MaLH`),
    KEY `idx_ctpn_mathuoc` (`MaThuoc`),
    KEY `idx_ctpn_madvt` (`MaDVT`),
    KEY `idx_ctpn_malh` (`MaLH`),
    CONSTRAINT `fk_ctpn_phieunhap`
        FOREIGN KEY (`MaPN`) REFERENCES `PhieuNhap` (`MaPN`),
    CONSTRAINT `fk_ctpn_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctpn_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Thuoc_SP_TheoLo` (
    `MaPN` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `MaLH` VARCHAR(10) NOT NULL,
    `SoLuongTon` INT NOT NULL DEFAULT 0,
    `SoLuongDat` INT NOT NULL DEFAULT 0,
    `SoLuongGiu` INT NOT NULL DEFAULT 0,
    `NSX` DATE NULL,
    `HSD` DATE NULL,
    PRIMARY KEY (`MaLH`),
    KEY `idx_lothuoc_mathuoc` (`MaThuoc`),
    KEY `idx_lothuoc_hsd` (`HSD`),
    KEY `idx_lothuoc_mathuoc_hsd` (`MaThuoc`, `HSD`),
    KEY `idx_lothuoc_ctpn` (`MaPN`, `MaThuoc`, `MaLH`),
    CONSTRAINT `fk_lothuoc_ctpn`
        FOREIGN KEY (`MaPN`, `MaThuoc`, `MaLH`)
        REFERENCES `ChiTietPhieuNhap` (`MaPN`, `MaThuoc`, `MaLH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `HoaDon` (
    `MaHD` VARCHAR(10) NOT NULL,
    `NgayLap` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `TrangThai` TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'Chon TINYINT(1) de khop Java model/DAO hien tai; can review vi script cu luu gia tri chuoi nhu Hoan tat',
    `LoaiHoaDon` VARCHAR(10) NOT NULL DEFAULT 'OTC',
    `MaDonThuoc` VARCHAR(20) NULL,
    `MaKH` VARCHAR(10) NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaHD`),
    KEY `idx_hoadon_ngaylap` (`NgayLap`),
    KEY `idx_hoadon_makh` (`MaKH`),
    KEY `idx_hoadon_manv` (`MaNV`),
    CONSTRAINT `fk_hoadon_khachhang`
        FOREIGN KEY (`MaKH`) REFERENCES `KhachHang` (`MaKH`),
    CONSTRAINT `fk_hoadon_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietHoaDon` (
    `MaHD` VARCHAR(10) NOT NULL,
    `MaLH` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    `MaDVT` VARCHAR(10) NOT NULL,
    `DonGia` DECIMAL(18,2) NOT NULL,
    `GiamGia` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (`MaHD`, `MaLH`, `MaDVT`),
    KEY `idx_cthd_malh` (`MaLH`),
    KEY `idx_cthd_madvt` (`MaDVT`),
    CONSTRAINT `fk_cthd_hoadon`
        FOREIGN KEY (`MaHD`) REFERENCES `HoaDon` (`MaHD`),
    CONSTRAINT `fk_cthd_lothuoc`
        FOREIGN KEY (`MaLH`) REFERENCES `Thuoc_SP_TheoLo` (`MaLH`),
    CONSTRAINT `fk_cthd_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `HoatDong` (
    `ID` INT NOT NULL AUTO_INCREMENT,
    `MaHDong` VARCHAR(16) NULL COMMENT 'TODO: sinh o service layer theo format HD + LPAD(ID, 4, 0); khong dung generated column vi MariaDB khong cho phep tham chieu AUTO_INCREMENT o day',
    `LoaiHD` VARCHAR(50) NULL,
    `ThoiGian` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `BangDL` VARCHAR(50) NULL,
    `MaNV` VARCHAR(10) NULL,
    `NoiDung` LONGTEXT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uq_hoatdong_mahdong` (`MaHDong`),
    KEY `idx_hoatdong_thoigian` (`ThoiGian`),
    KEY `idx_hoatdong_manv` (`MaNV`),
    CONSTRAINT `fk_hoatdong_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `PhieuDatHang` (
    `MaPDat` VARCHAR(10) NOT NULL,
    `NgayLap` DATE NOT NULL,
    `SoTienCoc` DECIMAL(18,2) NULL,
    `GhiChu` TEXT NULL,
    `MaKH` VARCHAR(10) NOT NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    `TrangThai` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '0 = moi tao, 1 = du hang, 2 = da duyet/giu hang; can confirm voi nghiep vu that',
    PRIMARY KEY (`MaPDat`),
    KEY `idx_phieudathang_ngaylap` (`NgayLap`),
    KEY `idx_phieudathang_makh` (`MaKH`),
    KEY `idx_phieudathang_manv` (`MaNV`),
    CONSTRAINT `fk_phieudathang_khachhang`
        FOREIGN KEY (`MaKH`) REFERENCES `KhachHang` (`MaKH`),
    CONSTRAINT `fk_phieudathang_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietPhieuDatHang` (
    `MaPDat` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    `MaDVT` VARCHAR(10) NOT NULL,
    `DonGia` DECIMAL(18,2) NOT NULL,
    `GiamGia` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    `TrangThai` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`MaPDat`, `MaThuoc`, `MaDVT`),
    KEY `idx_ctpdh_mathuoc` (`MaThuoc`),
    KEY `idx_ctpdh_madvt` (`MaDVT`),
    CONSTRAINT `fk_ctpdh_phieudathang`
        FOREIGN KEY (`MaPDat`) REFERENCES `PhieuDatHang` (`MaPDat`),
    CONSTRAINT `fk_ctpdh_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctpdh_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `PhieuDoiHang` (
    `MaPD` VARCHAR(10) NOT NULL,
    `NgayLap` DATE NOT NULL,
    `GhiChu` TEXT NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    `MaKH` VARCHAR(10) NOT NULL,
    `MaHD` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaPD`),
    KEY `idx_phieudoi_manv` (`MaNV`),
    KEY `idx_phieudoi_makh` (`MaKH`),
    KEY `idx_phieudoi_mahd` (`MaHD`),
    CONSTRAINT `fk_phieudoi_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`),
    CONSTRAINT `fk_phieudoi_khachhang`
        FOREIGN KEY (`MaKH`) REFERENCES `KhachHang` (`MaKH`),
    CONSTRAINT `fk_phieudoi_hoadon`
        FOREIGN KEY (`MaHD`) REFERENCES `HoaDon` (`MaHD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietPhieuDoiHang` (
    `MaLH` VARCHAR(10) NOT NULL,
    `MaPD` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    `MaDVT` VARCHAR(10) NOT NULL,
    `LyDoDoi` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`MaLH`, `MaPD`, `MaThuoc`, `MaDVT`),
    KEY `idx_ctpdhang_mapd` (`MaPD`),
    KEY `idx_ctpdhang_mathuoc` (`MaThuoc`),
    KEY `idx_ctpdhang_madvt` (`MaDVT`),
    CONSTRAINT `fk_ctpdhang_lothuoc`
        FOREIGN KEY (`MaLH`) REFERENCES `Thuoc_SP_TheoLo` (`MaLH`),
    CONSTRAINT `fk_ctpdhang_phieudoi`
        FOREIGN KEY (`MaPD`) REFERENCES `PhieuDoiHang` (`MaPD`),
    CONSTRAINT `fk_ctpdhang_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctpdhang_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `PhieuTraHang` (
    `MaPT` VARCHAR(10) NOT NULL,
    `NgayLap` DATE NOT NULL,
    `GhiChu` TEXT NULL,
    `MaNV` VARCHAR(10) NOT NULL,
    `MaHD` VARCHAR(10) NOT NULL,
    `MaKH` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`MaPT`),
    KEY `idx_phieutra_manv` (`MaNV`),
    KEY `idx_phieutra_mahd` (`MaHD`),
    KEY `idx_phieutra_makh` (`MaKH`),
    CONSTRAINT `fk_phieutra_nhanvien`
        FOREIGN KEY (`MaNV`) REFERENCES `NhanVien` (`MaNV`),
    CONSTRAINT `fk_phieutra_hoadon`
        FOREIGN KEY (`MaHD`) REFERENCES `HoaDon` (`MaHD`),
    CONSTRAINT `fk_phieutra_khachhang`
        FOREIGN KEY (`MaKH`) REFERENCES `KhachHang` (`MaKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `ChiTietPhieuTraHang` (
    `MaLH` VARCHAR(10) NOT NULL,
    `MaPT` VARCHAR(10) NOT NULL,
    `MaThuoc` VARCHAR(10) NOT NULL,
    `SoLuong` INT NOT NULL,
    `MaDVT` VARCHAR(10) NOT NULL,
    `DonGia` DECIMAL(18,2) NOT NULL,
    `GiamGia` DECIMAL(18,2) NOT NULL DEFAULT 0.00,
    `LyDoTra` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`MaLH`, `MaPT`, `MaThuoc`, `MaDVT`),
    KEY `idx_ctpth_mapt` (`MaPT`),
    KEY `idx_ctpth_mathuoc` (`MaThuoc`),
    KEY `idx_ctpth_madvt` (`MaDVT`),
    CONSTRAINT `fk_ctpth_lothuoc`
        FOREIGN KEY (`MaLH`) REFERENCES `Thuoc_SP_TheoLo` (`MaLH`),
    CONSTRAINT `fk_ctpth_phieutra`
        FOREIGN KEY (`MaPT`) REFERENCES `PhieuTraHang` (`MaPT`),
    CONSTRAINT `fk_ctpth_thuoc`
        FOREIGN KEY (`MaThuoc`) REFERENCES `Thuoc_SanPham` (`MaThuoc`),
    CONSTRAINT `fk_ctpth_donvitinh`
        FOREIGN KEY (`MaDVT`) REFERENCES `DonViTinh` (`MaDVT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- -----------------------------------------------------------------------------
-- Deferred SQL Server objects - intentionally not ported 1:1 in this step
-- -----------------------------------------------------------------------------
-- 1. CONTEXT_INFO-based audit:
--    trg_ThuocSanPham_Audit
--    trg_ChiTietHoatChat_Audit
--    trg_ThuocSPTheoLo_Audit
--    trg_ChiTietDonViTinh_Audit
--
-- 2. Business trigger/procedure for stock reservation and order status:
--    trg_CapNhatTrangThaiDatHang
--    trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT
--    sp_CapNhatTrangThaiDatHang
--    sp_DuyetPhieuDatHang
--
-- 3. Transaction-heavy procedure:
--    sp_LuuPhieuNhap
--
-- 4. Reporting/statistics procedures:
--    sp_ThongKeBanHang_*
--    sp_Top5SanPham_*
--    sp_ThongKeThuocHetHan
--    sp_ThongKeXNT
--    sp_HangHetHan
--    sp_HangSapHetHan
--    sp_GetHoaDonTheoThoiGian
--    sp_GetHoaDonTheoTuyChon
--
-- 5. String-code generation procedures:
--    sp_InsertNhanVien
--    sp_InsertThuoc_SanPham
--
-- 6. OPENROWSET / sp_executesql image import block is not ported.
