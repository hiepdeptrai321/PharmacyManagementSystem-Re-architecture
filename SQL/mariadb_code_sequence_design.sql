SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS `quan_ly_nha_thuoc`
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE `quan_ly_nha_thuoc`;

-- Proposal only:
-- Bang nay chua duoc dua vao schema chinh o buoc 7.
-- Muc dich la sinh ma nghiep vu an toan trong moi truong nhieu client
-- bang cach SELECT ... FOR UPDATE tren tung dong sequence.

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
('THUOC', 'TS', 0, 3),
('LO_THUOC', 'LH', 0, 5),
('HOA_DON', 'HD', 0, 4),
('PHIEU_NHAP', 'PN', 0, 3),
('PHIEU_DAT_HANG', 'PDH', 0, 3),
('PHIEU_DOI_HANG', 'PD', 0, 3),
('PHIEU_TRA_HANG', 'PT', 0, 3),
('LUONG_NHAN_VIEN', 'LNV', 0, 3),
('KHUYEN_MAI', 'KM', 0, 3),
('HOAT_DONG', 'LOG', 0, 4)
ON DUPLICATE KEY UPDATE
    `prefix` = VALUES(`prefix`),
    `padding` = VALUES(`padding`);

-- Ghi chu:
-- 1. Prefix `LOG` cho HOAT_DONG duoc de xuat de tranh nham voi `HoaDon` dang dung prefix `HD`.
-- 2. Cac danh muc on dinh nhu LoaiHang, DonViTinh, NhomDuocLy co the khong can sequence runtime.
-- 3. Service layer se la noi goi SELECT ... FOR UPDATE va format ma:
--    prefix + LPAD(current_value + 1, padding, '0')
