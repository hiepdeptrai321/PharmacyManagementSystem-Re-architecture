SET NAMES utf8mb4;

USE `quan_ly_nha_thuoc`;

CREATE TABLE IF NOT EXISTS `code_sequence` (
    `code_type` VARCHAR(50) NOT NULL,
    `prefix` VARCHAR(20) NOT NULL,
    `current_value` BIGINT NOT NULL DEFAULT 0,
    `padding` INT NOT NULL DEFAULT 4,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`code_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `code_sequence` (`code_type`, `prefix`, `current_value`, `padding`) VALUES
('NHAN_VIEN', 'NV', 0, 3),
('KHACH_HANG', 'KH', 0, 3),
('NHA_CUNG_CAP', 'NCC', 0, 3),
('KE_HANG', 'KE', 0, 3),
('DON_VI_TINH', 'DVT', 0, 3),
('NHOM_DUOC_LY', 'NDL', 0, 3),
('THUOC', 'TS', 0, 3),
('LO_THUOC', 'LH', 0, 4),
('HOA_DON', 'HD', 0, 4),
('PHIEU_NHAP', 'PN', 0, 3),
('PHIEU_DAT_HANG', 'PD', 0, 3),
('PHIEU_DOI_HANG', 'PD', 0, 3),
('PHIEU_TRA_HANG', 'PT', 0, 3),
('LUONG_NHAN_VIEN', 'LNV', 0, 3),
('KHUYEN_MAI', 'KM', 0, 3),
('HOAT_DONG', 'LOG', 0, 4)
ON DUPLICATE KEY UPDATE
    `prefix` = VALUES(`prefix`),
    `padding` = VALUES(`padding`);

-- Dong bo current_value theo du lieu hien co trong MariaDB seed/schema hien tai.
-- Neu import du lieu SQL Server goc co prefix khac (vi du PDH001, LH00001),
-- can review va cap nhat lai script nay truoc khi dua vao moi truong dung that.

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaNV`, 3) AS UNSIGNED)) FROM `NhanVien` WHERE `MaNV` REGEXP '^NV[0-9]+$'), 0))
WHERE `code_type` = 'NHAN_VIEN';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaKH`, 3) AS UNSIGNED)) FROM `KhachHang` WHERE `MaKH` REGEXP '^KH[0-9]+$'), 0))
WHERE `code_type` = 'KHACH_HANG';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaNCC`, 4) AS UNSIGNED)) FROM `NhaCungCap` WHERE `MaNCC` REGEXP '^NCC[0-9]+$'), 0))
WHERE `code_type` = 'NHA_CUNG_CAP';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaKe`, 3) AS UNSIGNED)) FROM `KeHang` WHERE `MaKe` REGEXP '^KE[0-9]+$'), 0))
WHERE `code_type` = 'KE_HANG';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaDVT`, 4) AS UNSIGNED)) FROM `DonViTinh` WHERE `MaDVT` REGEXP '^DVT[0-9]+$'), 0))
WHERE `code_type` = 'DON_VI_TINH';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaNDL`, 4) AS UNSIGNED)) FROM `NhomDuocLy` WHERE `MaNDL` REGEXP '^NDL[0-9]+$'), 0))
WHERE `code_type` = 'NHOM_DUOC_LY';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaThuoc`, 3) AS UNSIGNED)) FROM `Thuoc_SanPham` WHERE `MaThuoc` REGEXP '^TS[0-9]+$'), 0))
WHERE `code_type` = 'THUOC';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaLH`, 3) AS UNSIGNED)) FROM `Thuoc_SP_TheoLo` WHERE `MaLH` REGEXP '^LH[0-9]+$'), 0))
WHERE `code_type` = 'LO_THUOC';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaHD`, 3) AS UNSIGNED)) FROM `HoaDon` WHERE `MaHD` REGEXP '^HD[0-9]+$'), 0))
WHERE `code_type` = 'HOA_DON';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaPN`, 3) AS UNSIGNED)) FROM `PhieuNhap` WHERE `MaPN` REGEXP '^PN[0-9]+$'), 0))
WHERE `code_type` = 'PHIEU_NHAP';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaPDat`, 3) AS UNSIGNED)) FROM `PhieuDatHang` WHERE `MaPDat` REGEXP '^PD[0-9]+$'), 0))
WHERE `code_type` = 'PHIEU_DAT_HANG';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaPD`, 3) AS UNSIGNED)) FROM `PhieuDoiHang` WHERE `MaPD` REGEXP '^PD[0-9]+$'), 0))
WHERE `code_type` = 'PHIEU_DOI_HANG';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaPT`, 3) AS UNSIGNED)) FROM `PhieuTraHang` WHERE `MaPT` REGEXP '^PT[0-9]+$'), 0))
WHERE `code_type` = 'PHIEU_TRA_HANG';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaLNV`, 4) AS UNSIGNED)) FROM `LuongNhanVien` WHERE `MaLNV` REGEXP '^LNV[0-9]+$'), 0))
WHERE `code_type` = 'LUONG_NHAN_VIEN';

UPDATE `code_sequence`
SET `current_value` = GREATEST(`current_value`,
    COALESCE((SELECT MAX(CAST(SUBSTRING(`MaKM`, 3) AS UNSIGNED)) FROM `KhuyenMai` WHERE `MaKM` REGEXP '^KM[0-9]+$'), 0))
WHERE `code_type` = 'KHUYEN_MAI';
