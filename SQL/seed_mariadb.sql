SET NAMES utf8mb4;

USE quan_ly_nha_thuoc;

-- Cấu hình ứng dụng

INSERT INTO ThongSoUngDung (TenThongSo, GiaTri) VALUES('GiaTriThue', '0.05'),('NgayHetHan', '30');

-- Danh mục cơ bản

INSERT INTO LoaiHang (MaLoaiHang, TenLH, MoTa) VALUES('LH01', 'Thuốc Tây', 'Thuốc tân dược, OTC và ETC'),('LH04', 'Thực Phẩm Chức Năng', 'Vitamin, khoáng chất, sản phẩm bổ sung'),('LH05', 'Dụng Cụ Y Tế', 'Dụng cụ, vật tư và thiết bị y tế cơ bản');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES('NDL001', 'Giảm đau hạ sốt', 'Nhóm thuốc giảm đau, hạ sốt'),('NDL002', 'Vitamin', 'Nhóm vitamin và chất bổ sung'),('NDL003', 'Hỗ trợ hô hấp', 'Nhóm hỗ trợ điều trị bệnh hô hấp');

INSERT INTO KeHang (MaKe, TenKe, MoTa) VALUES('KE001', 'Kệ A1', 'Kệ thuốc giảm đau, hạ sốt'),('KE002', 'Kệ B1', 'Kệ vitamin và thực phẩm chức năng'),('KE003', 'Kệ C1', 'Kệ dụng cụ y tế');

INSERT INTO DonViTinh (MaDVT, TenDonViTinh, KiHieu) VALUES('DVT01', 'Viên', 'viên'),('DVT02', 'Hộp', 'hộp'),('DVT03', 'Chai', 'chai'),('DVT04', 'Cái', 'cái');

INSERT INTO HoatChat (MaHoatChat, TenHoatChat) VALUES('HC001', 'Paracetamol'),('HC002', 'Ascorbic Acid');

INSERT INTO LoaiKhuyenMai (MaLoai, TenLoai, MoTa) VALUES('LKM01', 'Giảm phần trăm', 'Áp dụng theo tỷ lệ phần trăm'),('LKM02', 'Tặng kèm', 'Tặng sản phẩm kèm theo');

-- Người dùng và đối tác

INSERT INTO NhanVien (MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi,VaiTro, TrangThai, TaiKhoan, MatKhau, NgayVaoLam, NgayKetThuc, TrangThaiXoa) VALUES('NV001', 'Phạm Quản Lý', '0905000001', 'quanly@nhathuoc.local', '1995-01-15', 1, 'TP Hồ Chí Minh','Quản lý', 1, 'admin', '123', '2024-01-01', NULL, 0),('NV002', 'Trần Nhân Viên', '0905000002', 'nhanvien@nhathuoc.local', '1998-08-20', 0, 'TP Hồ Chí Minh','Nhân viên', 1, 'staff', '123', '2024-03-01', NULL, 0);

INSERT INTO LuongNhanVien (MaLNV, TuNgay, DenNgay, LuongCoBan, PhuCap, GhiChu, MaNV) VALUES('LNV001', '2026-01-01', NULL, 9000000.00, 1000000.00, 'Lương cơ bản nhân viên quản lý', 'NV001'),('LNV002', '2026-01-01', NULL, 7000000.00, 500000.00, 'Lương cơ bản nhân viên bán hàng', 'NV002');

INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai) VALUES('KH001', 'Nguyễn Văn A', '0911000001', 'kh001@example.com', '1990-05-12', 1, 'Gò Vấp, TP Hồ Chí Minh', 1),('KH002', 'Lê Thị B', '0911000002', 'kh002@example.com', '1993-09-21', 0, 'Thủ Đức, TP Hồ Chí Minh', 1),('KH003', 'Khách Lẻ', '0911000003', NULL, NULL, 1, NULL, 1);

INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email, GPKD, GhiChu, TenCongTy, MSThue) VALUES('NCC001', 'Công ty Dược Hậu Giang', 'Cần Thơ, Việt Nam', '02923888888', 'contact@dhgpharma.vn', 'GPKD-001', 'Nhà cung cấp thuốc tây', 'DHG Pharma', '18001001'),('NCC002', 'Công ty Traphaco', 'Hà Nội, Việt Nam', '02436888333', 'contact@traphaco.vn', 'GPKD-002', 'Nhà cung cấp TPCN và đông dược', 'Traphaco', '01001003');

-- Thuốc và chi tiết thuốc

INSERT INTO Thuoc_SanPham (MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,SDK_GPNK, HangSX, NuocSX, HinhAnh, MaLoaiHang, MaNDL, ViTri,TrangThaiXoa, ETC) VALUES('TS001', 'Paracetamol 500mg', 500, 'mg', 'Uống', 'Hộp 10 vỉ x 10 viên','VN-2345-19', 'DHG Pharma', 'Việt Nam', NULL, 'LH01', 'NDL001', 'KE001', 0, 0),('TS002', 'Vitamin C 500mg', 500, 'mg', 'Uống', 'Hộp 10 ống','VN-8351-21', 'Traphaco', 'Việt Nam', NULL, 'LH04', 'NDL002', 'KE002', 0, 0),('TS003', 'Nhiệt kế điện tử', NULL, NULL, 'Đo', 'Hộp 1 cái','DM-0001-26', 'Omron', 'Nhật Bản', NULL, 'LH05', NULL, 'KE003', 0, 0);

INSERT INTO ChiTietHoatChat (MaHoatChat, MaThuoc, HamLuong) VALUES('HC001', 'TS001', 500.00),('HC002', 'TS002', 500.00);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan) VALUES('TS001', 'DVT01', 1.0000, 2000.00, 3000.00, 1),('TS001', 'DVT02', 100.0000, 180000.00, 250000.00, 0),('TS002', 'DVT01', 1.0000, 2500.00, 3500.00, 1),('TS002', 'DVT02', 10.0000, 22000.00, 32000.00, 0),('TS003', 'DVT04', 1.0000, 85000.00, 125000.00, 1);

-- Khuyến mãi mẫu

INSERT INTO KhuyenMai (MaKM, TenKM, GiaTriKM, GiaTriApDung, LoaiGiaTri,NgayBatDau, NgayKetThuc, MoTa, NgayTao, MaLoai) VALUES('KM001', 'Giảm 10 phần trăm cho Paracetamol', 10.00, 50000.00, 'PERCENT','2026-01-01', '2026-12-31', 'Khuyến mãi giảm 10 phần trăm cho đơn thuốc đạt điều kiện', '2026-01-01 08:00:00', 'LKM01'),('KM002', 'Mua Paracetamol tặng Vitamin C', 0.00, 0.00, 'BONUS','2026-01-01', '2026-12-31', 'Tặng kèm Vitamin C cho sản phẩm Paracetamol', '2026-01-01 08:30:00', 'LKM02');

INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa) VALUES('TS001', 'KM001', 1, 5),('TS001', 'KM002', 1, 1);

INSERT INTO Thuoc_SP_TangKem (MaKM, MaThuocTangKem, SoLuong) VALUES('KM002', 'TS002', 1);

-- Phiếu nhập và tồn kho theo lô

INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV) VALUES('PN001', '2026-04-25', 1, 'Nhập lô thuốc giảm đau', 'NCC001', 'NV001'),('PN002', '2026-05-01', 1, 'Nhập lô vitamin và dụng cụ', 'NCC002', 'NV001');

INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue) VALUES('PN001', 'TS001', 'LH0001', 10, 'DVT02', 180000.00, 0.00, 0.0500),('PN002', 'TS002', 'LH0002', 20, 'DVT02', 22000.00, 0.00, 0.0500),('PN002', 'TS003', 'LH0003', 5, 'DVT04', 85000.00, 0.00, 0.0500);

INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD) VALUES('PN001', 'TS001', 'LH0001', 998, 0, 0, '2026-04-01', '2027-04-01'),('PN002', 'TS002', 'LH0002', 200, 0, 0, '2026-05-01', '2026-06-15'),('PN002', 'TS003', 'LH0003', 5, 0, 0, '2026-04-10', '2029-04-10');

-- Hóa đơn và đặt hàng mẫu để test các màn có giao dịch

INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, LoaiHoaDon, MaDonThuoc, MaKH, MaNV) VALUES('HD0001', '2026-05-05 09:30:00', 1, 'OTC', NULL, 'KH001', 'NV002');

INSERT INTO ChiTietHoaDon (MaHD, MaLH, SoLuong, MaDVT, DonGia, GiamGia) VALUES('HD0001', 'LH0001', 2, 'DVT01', 3000.00, 0.00);

INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV, TrangThai) VALUES('PD001', '2026-05-06', 20000.00, 'Khách đặt trước 1 hộp Vitamin C', 'KH002', 'NV002', 1);

INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, TrangThai) VALUES('PD001', 'TS002', 1, 'DVT02', 32000.00, 0.00, 1);

-- Ghi chú

-- 1. Mật khẩu seed hiện đang ở dạng plain text để khớp logic đăng nhập đồ án cũ.-- 2. Chưa seed các bảng đổi/trả để tránh đóng băng nghiệp vụ chưa được xác nhận.-- 3. Có thể mở rộng seed sau khi chốt luồng nghiệp vụ và mapping JPA.

-- =============================================================================-- Bổ sung dữ liệu mẫu (tiếp nối dữ liệu hiện có)-- =============================================================================

-- Danh mục bổ sung

INSERT INTO LoaiHang (MaLoaiHang, TenLH, MoTa) VALUES('LH02', 'Vaccine', 'Chế phẩm sinh học phòng ngừa bệnh truyền nhiễm'),('LH03', 'Đông Y', 'Thuốc y học cổ truyền, thảo dược, cao'),('LH06', 'Mỹ Phẩm', 'Sản phẩm chăm sóc da, tóc, làm đẹp');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES('NDL004', 'Kháng sinh', 'Nhóm thuốc diệt hoặc kìm khuẩn'),('NDL005', 'Kháng viêm NSAID', 'Nhóm thuốc kháng viêm không steroid'),('NDL006', 'Tiêu hóa', 'Nhóm thuốc điều trị bệnh tiêu hóa'),('NDL007', 'Kháng dị ứng', 'Nhóm thuốc kháng histamine'),('NDL008', 'Nội tiết', 'Nhóm thuốc điều trị rối loạn nội tiết'),('NDL009', 'Tim mạch', 'Nhóm thuốc hỗ trợ tim mạch, hạ mỡ máu');

INSERT INTO KeHang (MaKe, TenKe, MoTa) VALUES('KE004', 'Kệ D1', 'Kệ thuốc tim mạch và tiêu hóa'),('KE005', 'Kệ E1', 'Kệ mỹ phẩm và chăm sóc cá nhân');

INSERT INTO DonViTinh (MaDVT, TenDonViTinh, KiHieu) VALUES('DVT05', 'Vỉ', 'vỉ'),('DVT06', 'Lọ', 'lọ'),('DVT07', 'Gói', 'gói'),('DVT08', 'Ống', 'ống'),('DVT09', 'Tuýp', 'tuýp');

INSERT INTO HoatChat (MaHoatChat, TenHoatChat) VALUES('HC003', 'Ibuprofen'),('HC004', 'Aspirin'),('HC005', 'Amoxicillin'),('HC006', 'Omeprazole'),('HC007', 'Metformin'),('HC008', 'Loratadine'),('HC009', 'Atorvastatin'),('HC010', 'Cefuroxime');

-- Đối tác bổ sung

INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email, GPKD, GhiChu, TenCongTy, MSThue) VALUES('NCC003', 'Công ty Domesco', 'Đồng Tháp, Việt Nam', '02773888888', 'contact@domesco.vn', 'GPKD-003', 'Chuyên cung cấp thuốc kê đơn', 'Domesco', '14001004'),('NCC004', 'Công ty CP Dược phẩm OPC', 'TP.HCM, Việt Nam', '02838999999', 'contact@opcpharma.com', 'GPKD-004', 'Thuốc đông y và TPCN', 'OPC Pharma', '03001005'),('NCC005', 'Công ty CP Imexpharm', 'Đồng Tháp, Việt Nam', '02773889999', 'contact@imexpharm.com', 'GPKD-005', 'Thuốc generic và TPCN', 'Imexpharm', '14001007');

INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai) VALUES('KH004', 'Phạm Thị Mai', '0911000004', 'kh004@example.com', '1992-02-15', 0, 'Đà Nẵng', 1),('KH005', 'Hoàng Văn Nam', '0911000005', 'kh005@example.com', '1985-12-20', 1, 'Cần Thơ', 1),('KH006', 'Vũ Thị Lan', '0911000006', 'kh006@example.com', '1998-09-09', 0, 'Hải Dương', 1),('KH007', 'Đặng Văn Hùng', '0911000007', 'kh007@example.com', '1993-07-01', 1, 'Bắc Ninh', 1),('KH008', 'Bùi Thị Thảo', '0911000008', 'kh008@example.com', '1996-01-22', 0, 'Quảng Ninh', 1);

INSERT INTO NhanVien (MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi,VaiTro, TrangThai, TaiKhoan, MatKhau, NgayVaoLam, NgayKetThuc, TrangThaiXoa) VALUES('NV003', 'Đỗ Phú Hiệp', '0905000003', 'hiep@nhathuoc.local', '2003-03-03', 1, 'An Giang','Nhân viên', 1, 'hiep', '123', '2024-06-01', NULL, 0),('NV004', 'Nguyễn Nhựt Hào', '0905000004', 'hao@nhathuoc.local', '2005-05-31', 1, 'Đồng Tháp','Nhân viên', 1, 'hao', '123', '2024-09-01', NULL, 0);

INSERT INTO LuongNhanVien (MaLNV, TuNgay, DenNgay, LuongCoBan, PhuCap, GhiChu, MaNV) VALUES('LNV003', '2026-01-01', NULL, 7000000.00, 500000.00, 'Lương cơ bản nhân viên bán hàng', 'NV003'),('LNV004', '2026-01-01', NULL, 7000000.00, 350000.00, 'Lương cơ bản nhân viên bán hàng', 'NV004');

-- Thuốc bổ sung (TS004-TS015)

INSERT INTO Thuoc_SanPham (MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,SDK_GPNK, HangSX, NuocSX, HinhAnh, MaLoaiHang, MaNDL, ViTri,TrangThaiXoa, ETC) VALUES('TS004', 'Vitamin C 1000mg', 1000, 'mg', 'Uống', 'Hộp 10 ống','VN-1232-19', 'Bayer', 'Đức', NULL, 'LH01', 'NDL002', 'KE002', 0, 0),('TS005', 'Ibuprofen 400mg', 400, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên','VN-5675-19', 'Mekophar', 'Việt Nam', NULL, 'LH01', 'NDL005', 'KE001', 0, 0),('TS006', 'Amoxicillin 500mg', 500, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên','VN-2134-19', 'DHG Pharma', 'Việt Nam', NULL, 'LH01', 'NDL004', 'KE001', 0, 1),('TS007', 'Omeprazole 20mg', 20, 'mg', 'Uống', 'Hộp 2 vỉ x 7 viên','VN-2344-21', 'Traphaco', 'Việt Nam', NULL, 'LH01', 'NDL006', 'KE004', 0, 0),('TS008', 'Loratadine 10mg', 10, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên','VN-4564-21', 'DHG Pharma', 'Việt Nam', NULL, 'LH01', 'NDL007', 'KE001', 0, 0),('TS009', 'Aspirin 81mg', 81, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên','VN-8678-19', 'Sanofi', 'Pháp', NULL, 'LH01', 'NDL005', 'KE001', 0, 0),('TS010', 'Metformin 500mg', 500, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên','VN-4569-21', 'Mekophar', 'Việt Nam', NULL, 'LH01', 'NDL008', 'KE004', 0, 1),('TS011', 'Atorvastatin 20mg', 20, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên','VN-8254-21', 'Bayer', 'Đức', NULL, 'LH01', 'NDL009', 'KE004', 0, 1),('TS012', 'Cefuroxime 250mg', 250, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên','VN-3241-19', 'GSK', 'Anh', NULL, 'LH01', 'NDL004', 'KE001', 0, 1),('TS013', 'Boganic', NULL, NULL, 'Uống', 'Hộp 3 vỉ x 10 viên','VD-0002-23', 'Traphaco', 'Việt Nam', NULL, 'LH03', NULL, 'KE002', 0, 0),('TS014', 'Omega-3 Fish Oil 1000mg', 1000, 'mg', 'Uống', 'Lọ 120 viên','TPCN-0002-23', 'Blackmores', 'Úc', NULL, 'LH04', NULL, 'KE002', 0, 0),('TS015', 'Máy đo huyết áp bắp tay', NULL, NULL, 'Đo', 'Hộp 1 cái','DM-0002-23', 'Microlife', 'Thụy Sĩ', NULL, 'LH05', NULL, 'KE003', 0, 0);

INSERT INTO ChiTietHoatChat (MaHoatChat, MaThuoc, HamLuong) VALUES('HC002', 'TS004', 1000.00),('HC003', 'TS005', 400.00),('HC005', 'TS006', 500.00),('HC006', 'TS007', 20.00),('HC008', 'TS008', 10.00),('HC004', 'TS009', 81.00),('HC007', 'TS010', 500.00),('HC009', 'TS011', 20.00),('HC010', 'TS012', 250.00);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan) VALUES('TS004', 'DVT08', 1.0000, 2500.00, 3200.00, 1),('TS004', 'DVT02', 10.0000, 24000.00, 30000.00, 0),('TS005', 'DVT01', 1.0000, 900.00, 1200.00, 1),('TS005', 'DVT02', 10.0000, 8500.00, 11000.00, 0),('TS006', 'DVT01', 1.0000, 1200.00, 1500.00, 1),('TS006', 'DVT05', 10.0000, 11800.00, 14500.00, 0),('TS006', 'DVT02', 20.0000, 23000.00, 28000.00, 0),('TS007', 'DVT01', 1.0000, 1800.00, 2300.00, 1),('TS007', 'DVT02', 14.0000, 24000.00, 31000.00, 0),('TS008', 'DVT01', 1.0000, 900.00, 1200.00, 1),('TS008', 'DVT02', 10.0000, 8800.00, 11500.00, 0),('TS009', 'DVT01', 1.0000, 500.00, 700.00, 1),('TS009', 'DVT05', 10.0000, 4800.00, 6800.00, 0),('TS009', 'DVT02', 30.0000, 14000.00, 19500.00, 0),('TS010', 'DVT01', 1.0000, 1300.00, 1700.00, 1),('TS010', 'DVT05', 10.0000, 12500.00, 16500.00, 0),('TS010', 'DVT02', 30.0000, 36000.00, 47000.00, 0),('TS011', 'DVT01', 1.0000, 3000.00, 3800.00, 1),('TS011', 'DVT05', 10.0000, 29000.00, 37000.00, 0),('TS011', 'DVT02', 20.0000, 57000.00, 72000.00, 0),('TS012', 'DVT01', 1.0000, 2000.00, 2500.00, 1),('TS012', 'DVT05', 10.0000, 19500.00, 24000.00, 0),('TS012', 'DVT02', 20.0000, 38500.00, 47000.00, 0),('TS013', 'DVT01', 1.0000, 850.00, 1100.00, 1),('TS013', 'DVT02', 30.0000, 24500.00, 31500.00, 0),('TS014', 'DVT01', 1.0000, 3000.00, 4000.00, 1),('TS014', 'DVT06', 120.0000, 340000.00, 450000.00, 0),('TS015', 'DVT04', 1.0000, 850000.00, 1050000.00, 1);

-- Khuyến mãi bổ sung

INSERT INTO KhuyenMai (MaKM, TenKM, GiaTriKM, GiaTriApDung, LoaiGiaTri,NgayBatDau, NgayKetThuc, MoTa, NgayTao, MaLoai) VALUES('KM003', 'Giảm 5% cho Ibuprofen', 5.00, 10000.00, 'PERCENT','2026-05-01', '2026-12-31', 'Khuyến mãi giảm 5% cho Ibuprofen 400mg', '2026-05-01 08:00:00', 'LKM01'),('KM004', 'Mua Omeprazole tặng Loratadine', 0.00, 0.00, 'BONUS','2026-05-01', '2026-12-31', 'Tặng kèm Loratadine khi mua Omeprazole', '2026-05-01 08:30:00', 'LKM02');

INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa) VALUES('TS005', 'KM003', 1, 5),('TS007', 'KM004', 1, 3);

INSERT INTO Thuoc_SP_TangKem (MaKM, MaThuocTangKem, SoLuong) VALUES('KM004', 'TS008', 1);

-- Phiếu nhập và tồn kho bổ sung (PN003-PN005, LH0004-LH0015)

INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV) VALUES('PN003', '2026-05-05', 1, 'Nhập lô thuốc giảm đau và kháng sinh', 'NCC003', 'NV001'),('PN004', '2026-05-06', 1, 'Nhập lô thuốc tiêu hóa và dị ứng', 'NCC001', 'NV002'),('PN005', '2026-05-07', 1, 'Nhập hỗn hợp thuốc, đông y, TPCN và DCYT', 'NCC002', 'NV003');

INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue) VALUES('PN003', 'TS004', 'LH0004', 15, 'DVT02', 24000.00, 0.00, 0.0500),('PN003', 'TS005', 'LH0005', 20, 'DVT02', 8500.00, 0.00, 0.0500),('PN003', 'TS006', 'LH0006', 10, 'DVT02', 23000.00, 0.00, 0.0500),('PN004', 'TS007', 'LH0007', 25, 'DVT02', 24000.00, 0.00, 0.0500),('PN004', 'TS008', 'LH0008', 30, 'DVT02', 8800.00, 0.00, 0.0500),('PN004', 'TS009', 'LH0009', 15, 'DVT02', 14000.00, 0.00, 0.0500),('PN005', 'TS010', 'LH0010', 20, 'DVT02', 36000.00, 0.00, 0.0500),('PN005', 'TS011', 'LH0011', 10, 'DVT02', 57000.00, 0.00, 0.0500),('PN005', 'TS012', 'LH0012', 15, 'DVT02', 38500.00, 0.00, 0.0500),('PN005', 'TS013', 'LH0013', 20, 'DVT02', 24500.00, 0.00, 0.0500),('PN005', 'TS014', 'LH0014', 10, 'DVT06', 340000.00, 0.00, 0.0500),('PN005', 'TS015', 'LH0015', 5, 'DVT04', 850000.00, 0.00, 0.0500);

INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD) VALUES('PN003', 'TS004', 'LH0004', 145, 0, 0, '2026-04-01', '2027-10-01'),('PN003', 'TS005', 'LH0005', 190, 0, 0, '2026-03-15', '2028-03-15'),('PN003', 'TS006', 'LH0006', 180, 0, 0, '2026-04-10', '2028-04-10'),('PN004', 'TS007', 'LH0007', 336, 0, 0, '2026-03-01', '2028-03-01'),('PN004', 'TS008', 'LH0008', 290, 0, 0, '2026-04-15', '2028-04-15'),('PN004', 'TS009', 'LH0009', 420, 0, 0, '2026-03-20', '2029-03-20'),('PN005', 'TS010', 'LH0010', 570, 0, 0, '2026-04-01', '2028-04-01'),('PN005', 'TS011', 'LH0011', 180, 0, 0, '2026-03-10', '2028-03-10'),('PN005', 'TS012', 'LH0012', 300, 0, 0, '2026-04-20', '2028-04-20'),('PN005', 'TS013', 'LH0013', 590, 0, 0, '2026-02-01', '2029-02-01'),('PN005', 'TS014', 'LH0014', 1200, 0, 0, '2026-01-15', '2028-01-15'),('PN005', 'TS015', 'LH0015', 5, 0, 0, '2025-12-01', '2030-12-01');

-- Hóa đơn bổ sung (HD0002-HD0006)

INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, LoaiHoaDon, MaDonThuoc, MaKH, MaNV) VALUES('HD0002', '2026-05-06 10:00:00', 1, 'OTC', NULL, 'KH004', 'NV003'),('HD0003', '2026-05-06 14:30:00', 1, 'OTC', NULL, 'KH005', 'NV002'),('HD0004', '2026-05-07 09:15:00', 1, 'OTC', NULL, NULL, 'NV004'),('HD0005', '2026-05-07 11:00:00', 1, 'OTC', NULL, 'KH001', 'NV003'),('HD0006', '2026-05-07 15:45:00', 1, 'OTC', NULL, 'KH006', 'NV002');

INSERT INTO ChiTietHoaDon (MaHD, MaLH, SoLuong, MaDVT, DonGia, GiamGia) VALUES('HD0002', 'LH0004', 3, 'DVT08', 3200.00, 0.00),('HD0002', 'LH0005', 10, 'DVT01', 1200.00, 0.00),('HD0003', 'LH0006', 20, 'DVT01', 1500.00, 0.00),('HD0003', 'LH0007', 14, 'DVT01', 2300.00, 0.00),('HD0004', 'LH0008', 10, 'DVT01', 1200.00, 0.00),('HD0004', 'LH0009', 30, 'DVT01', 700.00, 0.00),('HD0005', 'LH0010', 30, 'DVT01', 1700.00, 300.00),('HD0005', 'LH0004', 2, 'DVT08', 3200.00, 0.00),('HD0006', 'LH0011', 20, 'DVT01', 3800.00, 0.00),('HD0006', 'LH0013', 10, 'DVT01', 1100.00, 0.00);

-- Phiếu đặt hàng bổ sung (PD002-PD003)

INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV, TrangThai) VALUES('PD002', '2026-05-07', 30000.00, 'Khách đặt trước 2 hộp Omeprazole', 'KH007', 'NV003', 1),('PD003', '2026-05-08', 50000.00, 'Khách đặt trước Aspirin và Loratadine', 'KH008', 'NV004', 0);

INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, TrangThai) VALUES('PD002', 'TS007', 2, 'DVT02', 31000.00, 0.00, 1),('PD003', 'TS009', 1, 'DVT02', 19500.00, 0.00, 0),('PD003', 'TS008', 1, 'DVT02', 11500.00, 0.00, 0);