SET NAMES utf8mb4;

USE `quan_ly_nha_thuoc`;

INSERT INTO `NhanVien` (
    `MaNV`, `TenNV`, `SDT`, `Email`, `NgaySinh`, `GioiTinh`, `DiaChi`,
    `VaiTro`, `TrangThai`, `TaiKhoan`, `MatKhau`, `NgayVaoLam`, `NgayKetThuc`, `TrangThaiXoa`
) VALUES
('NV001', 'Pham Quan Ly', '0905000001', 'quanly@nhathuoc.local', '1995-01-15', 1, 'TP Ho Chi Minh',
 'Quan ly', 1, 'admin', '123', '2024-01-01', NULL, 0),
('NV003', 'Nguyen Tai Khoan Khoa', '0905000003', 'locked@nhathuoc.local', '1997-06-18', 1, 'TP Ho Chi Minh',
 'Nhan vien', 0, 'locked', '123', '2024-06-01', NULL, 0)
ON DUPLICATE KEY UPDATE
    `TenNV` = VALUES(`TenNV`),
    `SDT` = VALUES(`SDT`),
    `Email` = VALUES(`Email`),
    `NgaySinh` = VALUES(`NgaySinh`),
    `GioiTinh` = VALUES(`GioiTinh`),
    `DiaChi` = VALUES(`DiaChi`),
    `VaiTro` = VALUES(`VaiTro`),
    `TrangThai` = VALUES(`TrangThai`),
    `TaiKhoan` = VALUES(`TaiKhoan`),
    `MatKhau` = VALUES(`MatKhau`),
    `NgayVaoLam` = VALUES(`NgayVaoLam`),
    `NgayKetThuc` = VALUES(`NgayKetThuc`),
    `TrangThaiXoa` = VALUES(`TrangThaiXoa`);

-- Password dang o dang plain text de khop logic dang nhap POC hien tai.
