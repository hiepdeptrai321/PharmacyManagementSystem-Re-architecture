SET NAMES utf8mb4;

USE `quan_ly_nha_thuoc`;

-- -----------------------------------------------------------------------------
-- Cau hinh ung dung
-- -----------------------------------------------------------------------------
INSERT INTO `ThongSoUngDung` (`TenThongSo`, `GiaTri`) VALUES
('GiaTriThue', '0.05'),
('NgayHetHan', '30');

-- -----------------------------------------------------------------------------
-- Danh muc co ban
-- -----------------------------------------------------------------------------
INSERT INTO `LoaiHang` (`MaLoaiHang`, `TenLH`, `MoTa`) VALUES
('LH01', 'Thuoc Tay', 'Thuoc tan duoc, OTC va ETC'),
('LH04', 'Thuc Pham Chuc Nang', 'Vitamin, khoang chat, san pham bo sung'),
('LH05', 'Dung Cu Y Te', 'Dung cu, vat tu va thiet bi y te co ban');

INSERT INTO `NhomDuocLy` (`MaNDL`, `TenNDL`, `MoTa`) VALUES
('NDL001', 'Giam dau ha sot', 'Nhom thuoc giam dau, ha sot'),
('NDL002', 'Vitamin', 'Nhom vitamin va chat bo sung'),
('NDL003', 'Ho tro ho hap', 'Nhom ho tro dieu tri benh ho hap');

INSERT INTO `KeHang` (`MaKe`, `TenKe`, `MoTa`) VALUES
('KE001', 'Ke A1', 'Ke thuoc giam dau, ha sot'),
('KE002', 'Ke B1', 'Ke vitamin va thuc pham chuc nang'),
('KE003', 'Ke C1', 'Ke dung cu y te');

INSERT INTO `DonViTinh` (`MaDVT`, `TenDonViTinh`, `KiHieu`) VALUES
('DVT01', 'Vien', 'vien'),
('DVT02', 'Hop', 'hop'),
('DVT03', 'Chai', 'chai'),
('DVT04', 'Cai', 'cai');

INSERT INTO `HoatChat` (`MaHoatChat`, `TenHoatChat`) VALUES
('HC001', 'Paracetamol'),
('HC002', 'Ascorbic Acid');

INSERT INTO `LoaiKhuyenMai` (`MaLoai`, `TenLoai`, `MoTa`) VALUES
('LKM01', 'Giam phan tram', 'Ap dung theo ty le phan tram'),
('LKM02', 'Tang kem', 'Tang san pham kem theo');

-- -----------------------------------------------------------------------------
-- Nguoi dung va doi tac
-- -----------------------------------------------------------------------------
INSERT INTO `NhanVien` (
    `MaNV`, `TenNV`, `SDT`, `Email`, `NgaySinh`, `GioiTinh`, `DiaChi`,
    `VaiTro`, `TrangThai`, `TaiKhoan`, `MatKhau`, `NgayVaoLam`, `NgayKetThuc`, `TrangThaiXoa`
) VALUES
('NV001', 'Pham Quan Ly', '0905000001', 'quanly@nhathuoc.local', '1995-01-15', 1, 'TP Ho Chi Minh',
 'Quan ly', 1, 'admin', '123', '2024-01-01', NULL, 0),
('NV002', 'Tran Nhan Vien', '0905000002', 'nhanvien@nhathuoc.local', '1998-08-20', 0, 'TP Ho Chi Minh',
 'Nhan vien', 1, 'staff', '123', '2024-03-01', NULL, 0);

INSERT INTO `LuongNhanVien` (`MaLNV`, `TuNgay`, `DenNgay`, `LuongCoBan`, `PhuCap`, `GhiChu`, `MaNV`) VALUES
('LNV001', '2026-01-01', NULL, 9000000.00, 1000000.00, 'Luong co ban nhan vien quan ly', 'NV001'),
('LNV002', '2026-01-01', NULL, 7000000.00, 500000.00, 'Luong co ban nhan vien ban hang', 'NV002');

INSERT INTO `KhachHang` (`MaKH`, `TenKH`, `SDT`, `Email`, `NgaySinh`, `GioiTinh`, `DiaChi`, `TrangThai`) VALUES
('KH001', 'Nguyen Van A', '0911000001', 'kh001@example.com', '1990-05-12', 1, 'Go Vap, TP Ho Chi Minh', 1),
('KH002', 'Le Thi B', '0911000002', 'kh002@example.com', '1993-09-21', 0, 'Thu Duc, TP Ho Chi Minh', 1),
('KH003', 'Khach Le', '0911000003', NULL, NULL, 1, NULL, 1);

INSERT INTO `NhaCungCap` (`MaNCC`, `TenNCC`, `DiaChi`, `SDT`, `Email`, `GPKD`, `GhiChu`, `TenCongTy`, `MSThue`) VALUES
('NCC001', 'Cong ty Duoc Hau Giang', 'Can Tho, Viet Nam', '02923888888', 'contact@dhgpharma.vn', 'GPKD-001', 'Nha cung cap thuoc tay', 'DHG Pharma', '18001001'),
('NCC002', 'Cong ty Traphaco', 'Ha Noi, Viet Nam', '02436888333', 'contact@traphaco.vn', 'GPKD-002', 'Nha cung cap TPCN va dong duoc', 'Traphaco', '01001003');

-- -----------------------------------------------------------------------------
-- Thuoc va chi tiet thuoc
-- -----------------------------------------------------------------------------
INSERT INTO `Thuoc_SanPham` (
    `MaThuoc`, `TenThuoc`, `HamLuong`, `DonViHL`, `DuongDung`, `QuyCachDongGoi`,
    `SDK_GPNK`, `HangSX`, `NuocSX`, `HinhAnh`, `MaLoaiHang`, `MaNDL`, `ViTri`,
    `TrangThaiXoa`, `ETC`
) VALUES
('TS001', 'Paracetamol 500mg', 500, 'mg', 'Uong', 'Hop 10 vi x 10 vien',
 'VN-2345-19', 'DHG Pharma', 'Viet Nam', NULL, 'LH01', 'NDL001', 'KE001', 0, 0),
('TS002', 'Vitamin C 500mg', 500, 'mg', 'Uong', 'Hop 10 ong',
 'VN-8351-21', 'Traphaco', 'Viet Nam', NULL, 'LH04', 'NDL002', 'KE002', 0, 0),
('TS003', 'Nhiet ke dien tu', NULL, NULL, 'Do', 'Hop 1 cai',
 'DM-0001-26', 'Omron', 'Nhat Ban', NULL, 'LH05', NULL, 'KE003', 0, 0);

INSERT INTO `ChiTietHoatChat` (`MaHoatChat`, `MaThuoc`, `HamLuong`) VALUES
('HC001', 'TS001', 500.00),
('HC002', 'TS002', 500.00);

INSERT INTO `ChiTietDonViTinh` (`MaThuoc`, `MaDVT`, `HeSoQuyDoi`, `GiaNhap`, `GiaBan`, `DonViCoBan`) VALUES
('TS001', 'DVT01', 1.0000, 2000.00, 3000.00, 1),
('TS001', 'DVT02', 100.0000, 180000.00, 250000.00, 0),
('TS002', 'DVT01', 1.0000, 2500.00, 3500.00, 1),
('TS002', 'DVT02', 10.0000, 22000.00, 32000.00, 0),
('TS003', 'DVT04', 1.0000, 85000.00, 125000.00, 1);

-- -----------------------------------------------------------------------------
-- Khuyen mai mau
-- -----------------------------------------------------------------------------
INSERT INTO `KhuyenMai` (
    `MaKM`, `TenKM`, `GiaTriKM`, `GiaTriApDung`, `LoaiGiaTri`,
    `NgayBatDau`, `NgayKetThuc`, `MoTa`, `NgayTao`, `MaLoai`
) VALUES
('KM001', 'Giam 10 phan tram cho Paracetamol', 10.00, 50000.00, 'PERCENT',
 '2026-01-01', '2026-12-31', 'Khuyen mai giam 10 phan tram cho don thuoc dat dieu kien', '2026-01-01 08:00:00', 'LKM01'),
('KM002', 'Mua Paracetamol tang Vitamin C', 0.00, 0.00, 'BONUS',
 '2026-01-01', '2026-12-31', 'Tang kem Vitamin C cho san pham Paracetamol', '2026-01-01 08:30:00', 'LKM02');

INSERT INTO `ChiTietKhuyenMai` (`MaThuoc`, `MaKM`, `SLApDung`, `SLToiDa`) VALUES
('TS001', 'KM001', 1, 5),
('TS001', 'KM002', 1, 1);

INSERT INTO `Thuoc_SP_TangKem` (`MaKM`, `MaThuocTangKem`, `SoLuong`) VALUES
('KM002', 'TS002', 1);

-- -----------------------------------------------------------------------------
-- Phieu nhap va ton kho theo lo
-- -----------------------------------------------------------------------------
INSERT INTO `PhieuNhap` (`MaPN`, `NgayNhap`, `TrangThai`, `GhiChu`, `MaNCC`, `MaNV`) VALUES
('PN001', '2026-04-25', 1, 'Nhap lo thuoc giam dau', 'NCC001', 'NV001'),
('PN002', '2026-05-01', 1, 'Nhap lo vitamin va dung cu', 'NCC002', 'NV001');

INSERT INTO `ChiTietPhieuNhap` (`MaPN`, `MaThuoc`, `MaLH`, `SoLuong`, `MaDVT`, `GiaNhap`, `ChietKhau`, `Thue`) VALUES
('PN001', 'TS001', 'LH0001', 10, 'DVT02', 180000.00, 0.00, 0.0500),
('PN002', 'TS002', 'LH0002', 20, 'DVT02', 22000.00, 0.00, 0.0500),
('PN002', 'TS003', 'LH0003', 5, 'DVT04', 85000.00, 0.00, 0.0500);

INSERT INTO `Thuoc_SP_TheoLo` (`MaPN`, `MaThuoc`, `MaLH`, `SoLuongTon`, `SoLuongDat`, `SoLuongGiu`, `NSX`, `HSD`) VALUES
('PN001', 'TS001', 'LH0001', 998, 0, 0, '2026-04-01', '2027-04-01'),
('PN002', 'TS002', 'LH0002', 200, 0, 0, '2026-05-01', '2026-06-15'),
('PN002', 'TS003', 'LH0003', 5, 0, 0, '2026-04-10', '2029-04-10');

-- -----------------------------------------------------------------------------
-- Hoa don va dat hang mau de test cac man co giao dich
-- -----------------------------------------------------------------------------
INSERT INTO `HoaDon` (`MaHD`, `NgayLap`, `TrangThai`, `LoaiHoaDon`, `MaDonThuoc`, `MaKH`, `MaNV`) VALUES
('HD0001', '2026-05-05 09:30:00', 1, 'OTC', NULL, 'KH001', 'NV002');

INSERT INTO `ChiTietHoaDon` (`MaHD`, `MaLH`, `SoLuong`, `MaDVT`, `DonGia`, `GiamGia`) VALUES
('HD0001', 'LH0001', 2, 'DVT01', 3000.00, 0.00);

INSERT INTO `PhieuDatHang` (`MaPDat`, `NgayLap`, `SoTienCoc`, `GhiChu`, `MaKH`, `MaNV`, `TrangThai`) VALUES
('PD001', '2026-05-06', 20000.00, 'Khach dat truoc 1 hop Vitamin C', 'KH002', 'NV002', 1);

INSERT INTO `ChiTietPhieuDatHang` (`MaPDat`, `MaThuoc`, `SoLuong`, `MaDVT`, `DonGia`, `GiamGia`, `TrangThai`) VALUES
('PD001', 'TS002', 1, 'DVT02', 32000.00, 0.00, 1);

-- -----------------------------------------------------------------------------
-- Ghi chu
-- -----------------------------------------------------------------------------
-- 1. Mat khau seed hien dang o dang plain text de khop logic dang nhap do an cu.
-- 2. Chua seed cac bang doi/tra de tranh dong bang nghiep vu chua duoc xac nhan.
-- 3. Co the mo rong seed sau khi chot luong nghiep vu va mapping JPA.
