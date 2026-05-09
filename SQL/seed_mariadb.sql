SET NAMES utf8mb4;

USE
quan_ly_nha_thuoc;

-- =============================================================================
-- Import dữ liệu mẫu MariaDB được chuyển từ file QuanLyNhaThuoc.sql (MSSQL)
-- Chỉ chứa dữ liệu INSERT, không chứa CREATE TABLE / PROCEDURE / TRIGGER.
-- Ảnh VARBINARY từ OPENROWSET của SQL Server đã được chuyển thành NULL.
-- =============================================================================
INSERT INTO ThongSoUngDung (TenThongSo, GiaTri)
VALUES ('GiaTriThue', '0.05');

INSERT INTO ThongSoUngDung (TenThongSo, GiaTri)
VALUES ('NgayHetHan', '30');

INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai)
VALUES ('KH001', 'Nguyễn Văn An', '0905123456', 'an.nguyen@gmail.com', '1990-05-12', 1, 'Hà Nội', 1),
       ('KH002', 'Lê Thị Hoa', '0905789456', 'hoa.le@gmail.com', '1995-08-21', 0, 'Hải Phòng', 1),
       ('KH003', 'Trần Văn Bình', '0912457896', 'binh.tran@gmail.com', '1988-11-03', 1, 'TP HCM', 1),
       ('KH004', 'Phạm Thị Mai', '0932458976', 'mai.pham@gmail.com', '1992-02-15', 0, 'Đà Nẵng', 1),
       ('KH005', 'Hoàng Văn Nam', '0987654321', 'nam.hoang@gmail.com', '1985-12-20', 1, 'Cần Thơ', 1),
       ('KH006', 'Vũ Thị Lan', '0978456123', 'lan.vu@gmail.com', '1998-09-09', 0, 'Hải Dương', 1),
       ('KH007', 'Đặng Văn Hùng', '0934567890', 'hung.dang@gmail.com', '1993-07-01', 1, 'Bắc Ninh', 1),
       ('KH008', 'Bùi Thị Thảo', '0967451230', 'thao.bui@gmail.com', '1996-01-22', 0, 'Quảng Ninh', 1),
       ('KH009', 'Ngô Văn Tuấn', '0945789632', 'tuan.ngo@gmail.com', '1991-04-17', 1, 'Thái Bình', 1),
       ('KH010', 'Đỗ Thị Hạnh', '0923456789', 'hanh.do@gmail.com', '1994-03-05', 0, 'Ninh Bình', 1),
       ('KH011', 'Nguyễn Nhựt Hảo', '0825902972', 'hao.dep.dzai3105@gmail.com', '2005-05-31', 1, 'Đồng Tháp', 0);

INSERT INTO NhanVien (MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi, VaiTro, TrangThai, TaiKhoan, MatKhau,
                      NgayVaoLam, NgayKetThuc, TrangThaiXoa)
VALUES ('NV001', 'Đàm Thái An', '0912345678', 'thaian@gmail.com', '2005-01-01', 1, 'Củ Chi', 'Quản lý', 1, 'thaian',
        '123', '2025-01-12', NULL, 0),
       ('NV002', 'Hoàng Phước Thành Công', '0363636363', 'thanhcong@gmail.com', '2005-02-02', 0, 'Huế', 'Quản lý', 1,
        'thanhcong', '123', '2025-01-12', NULL, 0),
       ('NV003', 'Đỗ Phú Hiệp', '0181818181', 'phuhiep@gmail.com', '2003-03-03', 1, 'An Giang', 'Nhân viên', 1,
        'phuhiep', '123', '2025-01-12', NULL, 0),
       ('NV004', 'Nguyễn Nhựt Hảo', '0636363636', 'nhuthao@gmail.com', '2005-05-31', 1, 'Đồng Tháp', 'Nhân viên', 1,
        'nhuthao', '123', '2025-01-12', NULL, 0);

INSERT INTO LuongNhanVien (MaLNV, TuNgay, DenNgay, LuongCoBan, PhuCap, GhiChu, MaNV)
VALUES ('LNV001', '2025-01-01', NULL, 8000000, 500000, 'Lương tháng 1', 'NV001'),
       ('LNV002', '2025-01-01', NULL, 7500000, 400000, 'Lương tháng 1', 'NV002'),
       ('LNV003', '2025-01-01', NULL, 9000000, 600000, 'Lương tháng 1', 'NV003'),
       ('LNV004', '2025-01-01', NULL, 7000000, 350000, 'Lương tháng 1', 'NV004');

INSERT INTO LoaiHang (MaLoaiHang, TenLH, MoTa)
VALUES ('LH01', 'Thuốc Tây', 'Thuốc kê đơn, thuốc không kê đơn, thuốc điều trị bệnh lý thông thường...'),
       ('LH02', 'Vaccine', 'Chế phẩm sinh học giúp tạo miễn dịch, phòng ngừa các bệnh truyền nhiễm...'),
       ('LH03', 'Đông Y', 'Thuốc y học cổ truyền, thảo dược, cao, trà, thuốc sắc...'),
       ('LH04', 'Thực Phẩm Chức Năng', 'Vitamin, khoáng chất, sản phẩm tăng sức đề kháng, hỗ trợ miễn dịch...'),
       ('LH05', 'Dụng Cụ Y Tế', 'Nhiệt kế, máy đo huyết áp, bông băng, khẩu trang y tế...'),
       ('LH06', 'Mỹ Phẩm', 'Sữa rửa mặt, kem dưỡng da, dầu gội, sản phẩm chăm sóc da và tóc...');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL001', 'An thần, gây ngủ', 'Thuốc an thần, hỗ trợ giấc ngủ'),
       ('NDL002', 'Giảm đau', 'Thuốc giảm cảm giác đau'),
       ('NDL003', 'Gây mê', 'Thuốc gây mê toàn thân hoặc cục bộ'),
       ('NDL004', 'Chống co giật', 'Thuốc phòng và điều trị động kinh'),
       ('NDL005', 'Chống trầm cảm, hưng trí', 'Điều trị rối loạn tâm thần');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL006', 'Kích thích giao cảm (sympathomimetic)', 'Thuốc tăng hoạt động giao cảm'),
       ('NDL007', 'Ức chế giao cảm (sympatholytic)', 'Thuốc giảm hoạt động giao cảm'),
       ('NDL008', 'Kích thích phó giao cảm (parasympathomimetic)', 'Thuốc tăng hoạt động phó giao cảm'),
       ('NDL009', 'Ức chế phó giao cảm (parasympatholytic)', 'Thuốc giảm hoạt động phó giao cảm');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL010', 'Thuốc chống tăng huyết áp', 'Điều trị cao huyết áp'),
       ('NDL011', 'Thuốc trợ tim, chống suy tim', 'Hỗ trợ chức năng tim'),
       ('NDL012', 'Thuốc chống rối loạn nhịp tim', 'Ổn định nhịp tim'),
       ('NDL013', 'Thuốc lợi tiểu', 'Tăng đào thải nước và muối');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL014', 'NSAIDs', 'Thuốc kháng viêm không steroid'),
       ('NDL015', 'Corticoid', 'Thuốc kháng viêm steroid'),
       ('NDL016', 'Thuốc giảm đau gây nghiện và không gây nghiện', 'Điều trị đau mức độ nặng và nhẹ');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL017', 'Kháng sinh', 'Diệt hoặc kìm khuẩn'),
       ('NDL018', 'Thuốc kháng virus', 'Ức chế hoặc tiêu diệt virus'),
       ('NDL019', 'Thuốc chống nấm', 'Điều trị nấm'),
       ('NDL020', 'Thuốc diệt ký sinh trùng, chống sốt rét', 'Điều trị giun sán, ký sinh trùng');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL021', 'Thuốc chống tăng sinh tế bào', 'Điều trị ung thư');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL022', 'Hormone thay thế', 'Insulin, thyroxin, estrogen, testosterone...'),
       ('NDL023', 'Thuốc kháng hormone', 'Kháng estrogen, kháng androgen, kháng giáp...');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL024', 'Giãn phế quản', 'Mở rộng đường thở'),
       ('NDL025', 'Ức chế ho', 'Giảm phản xạ ho'),
       ('NDL026', 'Long đờm', 'Hỗ trợ tống đờm ra khỏi đường hô hấp');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL027', 'Thuốc chống loét dạ dày – tá tràng', 'Ức chế bơm proton, kháng H2'),
       ('NDL028', 'Thuốc nhuận tràng, cầm tiêu chảy', 'Hỗ trợ điều hòa tiêu hóa'),
       ('NDL029', 'Thuốc chống nôn', 'Ngăn ngừa và điều trị buồn nôn');

INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa)
VALUES ('NDL030', 'Vitamin, khoáng chất', 'Bổ sung dưỡng chất'),
       ('NDL031', 'Thuốc tăng sức đề kháng', 'Hỗ trợ miễn dịch'),
       ('NDL032', 'Chế phẩm sinh học, vaccine', 'Phòng và hỗ trợ điều trị bệnh');

INSERT INTO DonViTinh (MaDVT, TenDonViTinh, KiHieu)
VALUES ('DVT01', 'Viên', 'v'),
       ('DVT02', 'Vỉ', 'vỉ'),
       ('DVT03', 'Hộp', 'h'),
       ('DVT04', 'Chai', 'c'),
       ('DVT05', 'Lọ', 'lọ'),
       ('DVT06', 'Tuýp', 't'),
       ('DVT07', 'Gói', 'gói'),
       ('DVT08', 'Ống', 'ống'),
       ('DVT09', 'Thùng', 'th'),
       ('DVT10', 'Cái', 'cái'),
       ('DVT11', 'Cuộn', 'cuộn'),
       ('DVT12', 'Bịch', 'bịch'),
       ('DVT13', 'Lốc', 'lốc');

INSERT INTO HoatChat (MaHoatChat, TenHoatChat)
VALUES ('HC001', 'Paracetamol'),
       ('HC002', 'Ibuprofen'),
       ('HC003', 'Diclofenac'),
       ('HC004', 'Aspirin'),
       ('HC005', 'Naproxen'),
       ('HC006', 'Amoxicillin'),
       ('HC007', 'Clavulanic acid'),
       ('HC008', 'Cefuroxime'),
       ('HC009', 'Cephalexin'),
       ('HC010', 'Cefixime'),
       ('HC011', 'Ciprofloxacin'),
       ('HC012', 'Levofloxacin'),
       ('HC013', 'Azithromycin'),
       ('HC014', 'Clarithromycin'),
       ('HC015', 'Doxycycline'),
       ('HC016', 'Metronidazole'),
       ('HC017', 'Omeprazole'),
       ('HC018', 'Esomeprazole'),
       ('HC019', 'Pantoprazole'),
       ('HC020', 'Ranitidine'),
       ('HC021', 'Amlodipine'),
       ('HC022', 'Losartan'),
       ('HC023', 'Valsartan'),
       ('HC024', 'Lisinopril'),
       ('HC025', 'Enalapril'),
       ('HC026', 'Atenolol'),
       ('HC027', 'Metoprolol'),
       ('HC028', 'Furosemide'),
       ('HC029', 'Spironolactone'),
       ('HC030', 'Hydrochlorothiazide'),
       ('HC031', 'Atorvastatin'),
       ('HC032', 'Simvastatin'),
       ('HC033', 'Rosuvastatin'),
       ('HC034', 'Metformin'),
       ('HC035', 'Glibenclamide'),
       ('HC036', 'Gliclazide'),
       ('HC037', 'Insulin (Regular)'),
       ('HC038', 'Insulin Glargine'),
       ('HC039', 'Prednisone'),
       ('HC040', 'Dexamethasone'),
       ('HC041', 'Betamethasone'),
       ('HC042', 'Loratadine'),
       ('HC043', 'Cetirizine'),
       ('HC044', 'Chlorpheniramine'),
       ('HC045', 'Salbutamol'),
       ('HC046', 'Budesonide'),
       ('HC047', 'Montelukast'),
       ('HC048', 'Warfarin'),
       ('HC049', 'Clopidogrel'),
       ('HC050', 'Heparin'),
       ('HC051', 'Vitamin C'),
       ('HC052', 'Vitamin D3'),
       ('HC053', 'Omega-3 Fatty Acids'),
       ('HC054', 'Calcium'),
       ('HC055', 'Collagen Type II'),
       ('HC056', 'Probiotic (Multi-strain)'),
       ('HC057', 'Multivitamins'),
       ('HC058', 'Saponin (Sâm Ngọc Linh)'),
       ('HC059', 'Ginkgo Biloba Extract'),
       ('HC060', 'Zinc'),
       ('HC061', 'Glucosamine Sulfate'),
       ('HC062', 'BCG Vaccine'),
       ('HC063', 'Polio Vaccine (OPV)'),
       ('HC064', 'Hepatitis B Surface Antigen'),
       ('HC065', 'Diphtheria Toxoid'),
       ('HC066', 'Tetanus Toxoid'),
       ('HC067', 'Pertussis Antigen'),
       ('HC068', 'Measles Virus Antigen'),
       ('HC069', 'Mumps Virus Antigen'),
       ('HC070', 'Rubella Virus Antigen');

INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email, GPKD, GhiChu, TenCongTy, MSThue)
VALUES ('NCC001', 'Công ty Dược Hậu Giang', 'Cần Thơ, Việt Nam', '02923888888', 'HauGiang@dhgpharma.com.vn', 'GPKD-001',
        'Chuyên cung cấp thuốc tân dược', 'DHG Pharma', '18001001'),
       ('NCC002', 'Công ty Vắc xin và Sinh phẩm số 1', 'Hà Nội, Việt Nam', '02438683333', 'misto@vabiotech.com.vn',
        'GPKD-002', 'Nhà cung cấp vaccine', 'VABIOTECH', '01001002'),
       ('NCC003', 'Công ty Traphaco', 'Hà Nội, Việt Nam', '02436888333', 'Traphaco@traphaco.com.vn', 'GPKD-003',
        'Thực phẩm chức năng & đông dược', 'Traphaco', '01001003'),
       ('NCC004', 'Công ty Domesco', 'Đồng Tháp, Việt Nam', '02773888888', 'Domesco@domesco.com.vn', 'GPKD-004',
        'Chuyên cung cấp thuốc kê đơn', 'Domesco', '14001004'),
       ('NCC005', 'Công ty CP Dược phẩm OPC', 'TP.HCM, Việt Nam', '02838999999', 'opc@opcpharma.com', 'GPKD-005',
        'Thuốc đông y & TPCN', 'OPC Pharma', '03001005'),
       ('NCC006', 'Công ty CP Y tế Mediplantex', 'Hà Nội, Việt Nam', '02438556677', 'Mediphantex@mediplantex.com.vn',
        'GPKD-006', 'Dược liệu và đông y', 'Mediplantex', '01001006'),
       ('NCC007', 'Công ty CP Dược phẩm Imexpharm', 'Đồng Tháp, Việt Nam', '02773889999', 'imexpharm@imexpharm.com',
        'GPKD-007', 'Thuốc generic và TPCN', 'Imexpharm', '14001007'),
       ('NCC008', 'Công ty CP Dược phẩm Bidiphar', 'Bình Định, Việt Nam', '02563886666', 'Bidiphar@bidiphar.com.vn',
        'GPKD-008', 'Thuốc tiêm và vaccine', 'Bidiphar', '41001008'),
       ('NCC009', 'Công ty CP Trang thiết bị Y tế Vinamed', 'Hà Nội, Việt Nam', '02438223344', 'Vinamed@vinamed.vn',
        'GPKD-009', 'Cung cấp dụng cụ y tế', 'Vinamed', '01001009'),
       ('NCC010', 'Công ty TNHH Mỹ phẩm Sài Gòn', 'TP.HCM, Việt Nam', '02837778888',
        'saigoncosmetics@saigoncosmetics.com.vn', 'GPKD-010', 'Mỹ phẩm chăm sóc da', 'Saigon Cosmetics', '03001010');

INSERT INTO KeHang (MaKe, TenKe, MoTa)
VALUES ('KE001', 'Kệ thuốc cảm sốt', NULL),
       ('KE002', 'Kệ vitamin và thực phẩm chức năng', NULL),
       ('KE003', 'Kệ thuốc kháng sinh', NULL),
       ('KE004', 'Kệ thuốc giảm đau', NULL),
       ('KE005', 'Kệ mỹ phẩm và chăm sóc da', NULL);

INSERT INTO Thuoc_SanPham (MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi, SDK_GPNK, HangSX, NuocSX,
                           HinhAnh, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa, ETC)
VALUES ('TS001', 'Paracetamol 500mg', 500, 'mg', 'Uống', 'Hộp 10 vỉ x 10 viên', 'VN-2345-19', 'DHG Pharma', 'Việt Nam',
        NULL, 'LH01', 'NDL016', 'KE001', 0, 0),
       ('TS002', 'Amoxicillin 500mg', 500, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VN-2134-19', 'Traphaco', 'Việt Nam',
        NULL, 'LH01', 'NDL017', 'KE001', 0, 0),
       ('TS003', 'Cefuroxime 250mg', 250, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VN-3241-19', 'GSK', 'Anh', NULL, 'LH01',
        'NDL017', 'KE001', 0, 0),
       ('TS004', 'Vitamin C 1000mg', 1000, 'mg', 'Uống', 'Hộp 10 ống', 'VN-1232-19', 'Bayer', 'Đức', NULL, 'LH01',
        'NDL030', 'KE001', 0, 0),
       ('TS005', 'Ibuprofen 400mg', 400, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên', 'VN-5675-19', 'Mekophar', 'Việt Nam', NULL,
        'LH01', 'NDL014', 'KE001', 0, 0);

INSERT INTO Thuoc_SanPham (MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi, SDK_GPNK, HangSX, NuocSX,
                           HinhAnh, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa, ETC)
VALUES ('TS006', 'Aspirin 81mg', 81, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên', 'VN-8678-19', 'Sanofi', 'Pháp', NULL, 'LH01',
        'NDL014', 'KE001', 1, 0),
       ('TS007', 'Loratadine 10mg', 10, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên', 'VN-4564-21', 'DHG Pharma', 'Việt Nam',
        NULL, 'LH01', 'NDL009', 'KE001', 0, 0),
       ('TS008', 'Omeprazole 20mg', 20, 'mg', 'Uống', 'Hộp 2 vỉ x 7 viên', 'VN-2344-21', 'Traphaco', 'Việt Nam', NULL,
        'LH01', 'NDL027', 'KE001', 0, 0),
       ('TS009', 'Metformin 500mg', 500, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên', 'VN-4569-21', 'Mekophar', 'Việt Nam', NULL,
        'LH01', 'NDL022', 'KE001', 0, 0),
       ('TS010', 'Atorvastatin 20mg', 20, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VN-8254-21', 'Bayer', 'Đức', NULL,
        'LH01', 'NDL010', 'KE001', 0, 0),
       ('TS011', 'Paracetamol 650mg', 650, 'mg', 'Uống', 'Hộp 10 vỉ x 10 viên', 'VN-8542-21', 'GSK', 'Anh', NULL,
        'LH01', 'NDL016', 'KE001', 0, 0),
       ('TS012', 'Amoxicillin 250mg', 250, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên', 'VN-6258-21', 'Sanofi', 'Pháp', NULL,
        'LH01', 'NDL017', 'KE001', 0, 0),
       ('TS013', 'Cefuroxime 500mg', 500, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VN-8345-21', 'DHG Pharma', 'Việt Nam',
        NULL, 'LH01', 'NDL017', 'KE001', 0, 0),
       ('TS014', 'Vitamin C 500mg', 500, 'mg', 'Uống', 'Hộp 10 ống', 'VN-8351-21', 'Traphaco', 'Việt Nam', NULL, 'LH01',
        'NDL030', 'KE001', 0, 0),
       ('TS015', 'Ibuprofen 200mg', 200, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên', 'VN-7242-21', 'Mekophar', 'Việt Nam', NULL,
        'LH01', 'NDL014', 'KE001', 0, 0),
       ('TS016', 'Aspirin 500mg', 500, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên', 'VN-8462-22', 'Bayer', 'Đức', NULL, 'LH01',
        'NDL014', 'KE001', 0, 0),
       ('TS017', 'Loratadine 5mg', 5, 'mg', 'Uống', 'Hộp 1 vỉ x 10 viên', 'VN-7834-22', 'GSK', 'Anh', NULL, 'LH01',
        'NDL009', 'KE001', 0, 0),
       ('TS018', 'Omeprazole 40mg', 40, 'mg', 'Uống', 'Hộp 2 vỉ x 7 viên', 'VN-4264-22', 'Sanofi', 'Pháp', NULL, 'LH01',
        'NDL027', 'KE001', 0, 0),
       ('TS019', 'Metformin 850mg', 850, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên', 'VN-7834-22', 'DHG Pharma', 'Việt Nam',
        NULL, 'LH01', 'NDL022', 'KE001', 0, 0),
       ('TS020', 'Atorvastatin 40mg', 40, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VN-6354-22', 'Traphaco', 'Việt Nam',
        NULL, 'LH01', 'NDL010', 'KE001', 1, 0),
       ('TS226', 'Hoạt huyết dưỡng não', 250, 'mg', 'Uống', 'Hộp 3 vỉ x 10 viên', 'VD-0001-23', 'Traphaco', 'Việt Nam',
        NULL, 'LH03', NULL, 'KE001', 1, 0),
       ('TS227', 'Boganic', NULL, NULL, 'Uống', 'Hộp 3 vỉ x 10 viên', 'VD-0002-23', 'Traphaco', 'Việt Nam', NULL,
        'LH03', NULL, 'KE001', 0, 0),
       ('TS228', 'Ích mẫu', 250, 'mg', 'Uống', 'Hộp 2 vỉ x 10 viên', 'VD-0003-23', 'DHG Pharma', 'Việt Nam', NULL,
        'LH03', NULL, 'KE001', 0, 0),
       ('TS229', 'Siro ho Bảo Thanh', 10, 'ml', 'Uống', 'Chai 125 ml', 'VD-0004-23', 'Nam Dược', 'Việt Nam', NULL,
        'LH03', NULL, 'KE001', 0, 0),
       ('TS230', 'Viên ngậm Strepsils thảo dược', NULL, NULL, 'Ngậm', 'Hộp 2 vỉ x 12 viên', 'VD-0005-23', 'Reckitt',
        'Anh', NULL, 'LH03', NULL, 'KE001', 0, 0),
       ('TS231', 'Cao ích mẫu', 250, 'mg', 'Uống', 'Lọ 100 viên', 'VD-0006-23', 'Traphaco', 'Việt Nam', NULL, 'LH03',
        NULL, 'KE001', 0, 0),
       ('TS232', 'Sâm bổ chính khí', NULL, NULL, 'Uống', 'Lọ 30 viên', 'VD-0007-23', 'Công ty Dược OPC', 'Việt Nam',
        NULL, 'LH03', NULL, 'KE001', 0, 0),
       ('TS233', 'Kim tiền thảo', NULL, NULL, 'Uống', 'Hộp 3 vỉ x 10 viên', 'VD-0008-23', 'Mekophar', 'Việt Nam', NULL,
        'LH03', NULL, 'KE001', 0, 0),
       ('TS234', 'Nhất nhất thống phong', NULL, NULL, 'Uống', 'Hộp 3 vỉ x 10 viên', 'VD-0009-23', 'Dược Nhất Nhất',
        'Việt Nam', NULL, 'LH03', NULL, 'KE001', 0, 0),
       ('TS235', 'Hoàng liên giải độc hoàn', NULL, NULL, 'Uống', 'Lọ 60 viên', 'VD-0010-23', 'Trung Quốc Dược',
        'Trung Quốc', NULL, 'LH03', NULL, 'KE001', 0, 0),
       ('TS336', 'Vitamin D3 1000IU', 1000, 'IU', 'Uống', 'Lọ 100 viên', 'TPCN-0001-23', 'Nature Made', 'Mỹ', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS337', 'Omega-3 Fish Oil 1000mg', 1000, 'mg', 'Uống', 'Lọ 120 viên', 'TPCN-0002-23', 'Blackmores', 'Úc', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS338', 'Calcium + Vitamin D', 500, 'mg', 'Uống', 'Lọ 60 viên', 'TPCN-0003-23', 'Traphaco', 'Việt Nam', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS339', 'Collagen Type II', 40, 'mg', 'Uống', 'Lọ 30 viên', 'TPCN-0004-23', 'Neocell', 'Mỹ', NULL, 'LH04',
        NULL, 'KE001', 0, 0),
       ('TS340', 'Probiotic 10 strains', 10, 'tỷ CFU', 'Uống', 'Hộp 30 gói', 'TPCN-0005-23', 'Yakult', 'Nhật Bản', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS341', 'Multivitamin Daily', 1, 'viên', 'Uống', 'Lọ 100 viên', 'TPCN-0006-23', 'Centrum', 'Mỹ', NULL, 'LH04',
        NULL, 'KE001', 0, 0),
       ('TS342', 'Sâm Ngọc Linh Extract', 500, 'mg', 'Uống', 'Lọ 30 viên', 'TPCN-0007-23', 'Sâm Ngọc Linh Quảng Nam',
        'Việt Nam', NULL, 'LH04', NULL, 'KE001', 0, 0),
       ('TS343', 'Ginkgo Biloba 120mg', 120, 'mg', 'Uống', 'Lọ 60 viên', 'TPCN-0008-23', 'Pharmaton', 'Thụy Sĩ', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS344', 'Vitamin C + Zinc', 1000, 'mg', 'Uống', 'Lọ 20 viên sủi', 'TPCN-0009-23', 'DHG Pharma', 'Việt Nam',
        NULL, 'LH04', NULL, 'KE001', 0, 0),
       ('TS345', 'Glucosamine 1500mg', 1500, 'mg', 'Uống', 'Lọ 60 viên', 'TPCN-0010-23', 'Puritan''s Pride', 'Mỹ', NULL,
        'LH04', NULL, 'KE001', 0, 0),
       ('TS446', 'Nhiệt kế điện tử', NULL, NULL, 'Đo', 'Hộp 1 cái', 'DM-0001-23', 'Omron', 'Nhật Bản', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS447', 'Máy đo huyết áp bắp tay', NULL, NULL, 'Đo', 'Hộp 1 cái', 'DM-0002-23', 'Microlife', 'Thụy Sĩ', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS448', 'Máy đo đường huyết', NULL, NULL, 'Đo', 'Hộp 1 cái + que thử', 'DM-0003-23', 'Accu-Chek', 'Đức', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS449', 'Ống nghe y tế', NULL, NULL, 'Khám', 'Hộp 1 cái', 'DM-0004-23', '3M Littmann', 'Mỹ', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS450', 'Khẩu trang y tế 3 lớp', NULL, NULL, 'Đeo', 'Hộp 50 cái', 'DM-0005-23', 'Bảo Thạch', 'Việt Nam', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS451', 'Găng tay y tế', NULL, NULL, 'Đeo', 'Hộp 100 cái', 'DM-0006-23', 'Top Glove', 'Malaysia', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS452', 'Bơm tiêm dùng một lần 5ml', NULL, NULL, 'Tiêm', 'Hộp 100 cái', 'DM-0007-23', 'Vinahankook',
        'Việt Nam', NULL, 'LH05', NULL, 'KE001', 0, 0),
       ('TS453', 'Kháng khuẩn rửa tay nhanh', NULL, NULL, 'Sát khuẩn', 'Chai 500ml', 'DM-0008-23', 'Lifebuoy',
        'Việt Nam', NULL, 'LH05', NULL, 'KE001', 0, 0),
       ('TS454', 'Máy xông khí dung', NULL, NULL, 'Hít', 'Hộp 1 cái', 'DM-0009-23', 'Omron', 'Nhật Bản', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS455', 'Miếng dán nhiệt', NULL, NULL, 'Dán', 'Hộp 10 miếng', 'DM-0010-23', 'Kobayashi', 'Nhật Bản', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS556', 'Kem chống nắng SPF50', 50, 'ml', 'Bôi', 'Tuýp 50ml', 'MP-0001-23', 'Anessa', 'Nhật Bản', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS557', 'Sữa rửa mặt tạo bọt', 100, 'ml', 'Rửa mặt', 'Tuýp 100ml', 'MP-0002-23', 'Hada Labo', 'Nhật Bản', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS558', 'Nước hoa hồng cân bằng da', 150, 'ml', 'Bôi', 'Chai 150ml', 'MP-0003-23', 'Innisfree', 'Hàn Quốc',
        NULL, 'LH05', NULL, 'KE001', 0, 0),
       ('TS559', 'Serum Vitamin C 15%', 30, 'ml', 'Bôi', 'Lọ 30ml', 'MP-0004-23', 'Vichy', 'Pháp', NULL, 'LH05', NULL,
        'KE001', 0, 0),
       ('TS560', 'Kem dưỡng ẩm ban đêm', 50, 'ml', 'Bôi', 'Hũ 50ml', 'MP-0005-23', 'Laneige', 'Hàn Quốc', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS561', 'Son dưỡng môi có màu', NULL, NULL, 'Bôi', 'Thỏi 3g', 'MP-0006-23', 'Maybelline', 'Mỹ', NULL, 'LH05',
        NULL, 'KE001', 0, 0),
       ('TS562', 'Dầu gội thảo dược', 300, 'ml', 'Gội đầu', 'Chai 300ml', 'MP-0007-23', 'Thái Dương', 'Việt Nam', NULL,
        'LH05', NULL, 'KE001', 0, 0),
       ('TS563', 'Kem trị mụn', 20, 'g', 'Bôi', 'Tuýp 20g', 'MP-0008-23', 'La Roche-Posay', 'Pháp', NULL, 'LH05', NULL,
        'KE001', 0, 0),
       ('TS564', 'Mặt nạ dưỡng da Green Tea', 25, 'ml', 'Đắp mặt', 'Hộp 10 miếng', 'MP-0009-23', 'The Face Shop',
        'Hàn Quốc', NULL, 'LH05', NULL, 'KE001', 0, 0),
       ('TS565', 'Nước hoa nữ Eau de Parfum', 50, 'ml', 'Xịt', 'Chai 50ml', 'MP-0010-23', 'Chanel', 'Pháp', NULL,
        'LH05', NULL, 'KE001', 0, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS001', 'DVT01', 1, 800, 1000, 1),
       ('TS001', 'DVT02', 10, 7800, 9500, 0),
       ('TS001', 'DVT03', 100, 75000, 92000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS002', 'DVT01', 1, 1200, 1500, 1),
       ('TS002', 'DVT02', 10, 11800, 14500, 0),
       ('TS002', 'DVT03', 20, 23000, 28000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS003', 'DVT01', 1, 2000, 2500, 1),
       ('TS003', 'DVT02', 10, 19500, 24000, 0),
       ('TS003', 'DVT03', 20, 38500, 47000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS005', 'DVT01', 1, 900, 1200, 1),
       ('TS005', 'DVT03', 10, 8500, 11000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS006', 'DVT01', 1, 500, 700, 1),
       ('TS006', 'DVT02', 10, 4800, 6800, 0),
       ('TS006', 'DVT03', 30, 14000, 19500, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS004', 'DVT08', 1, 2500, 3200, 1),
       ('TS004', 'DVT03', 10, 24000, 30000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS010', 'DVT01', 1, 3000, 3800, 1),
       ('TS010', 'DVT02', 10, 29000, 37000, 0),
       ('TS010', 'DVT03', 20, 57000, 72000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS226', 'DVT01', 1, 900, 1100, 1),
       ('TS226', 'DVT02', 10, 8800, 10800, 0),
       ('TS226', 'DVT03', 30, 26000, 32000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS229', 'DVT04', 1, 35000, 42000, 1),
       ('TS229', 'DVT09', 20, 680000, 800000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS231', 'DVT01', 1, 1000, 1300, 1),
       ('TS231', 'DVT05', 100, 95000, 120000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS234', 'DVT01', 1, 900, 1100, 1),
       ('TS234', 'DVT02', 10, 8800, 10500, 0),
       ('TS234', 'DVT03', 30, 25500, 31000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS336', 'DVT01', 1, 1500, 2000, 1),
       ('TS336', 'DVT05', 100, 145000, 190000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS340', 'DVT07', 1, 4000, 5000, 1),
       ('TS340', 'DVT03', 30, 115000, 145000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS446', 'DVT10', 1, 75000, 99000, 1),
       ('TS446', 'DVT03', 10, 720000, 950000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS451', 'DVT10', 1, 350, 500, 1),
       ('TS451', 'DVT03', 100, 32000, 45000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS556', 'DVT06', 1, 180000, 230000, 1),
       ('TS556', 'DVT09', 20, 3400000, 4400000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS560', 'DVT11', 1, 200000, 260000, 1),
       ('TS560', 'DVT09', 20, 3800000, 4900000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS007', 'DVT01', 1, 900, 1200, 1),
       ('TS007', 'DVT02', 10, 8800, 11500, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS008', 'DVT01', 1, 1800, 2300, 1),
       ('TS008', 'DVT02', 14, 24000, 31000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS009', 'DVT01', 1, 1300, 1700, 1),
       ('TS009', 'DVT02', 10, 12500, 16500, 0),
       ('TS009', 'DVT03', 30, 36000, 47000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS011', 'DVT01', 1, 950, 1200, 1),
       ('TS011', 'DVT02', 10, 9200, 11500, 0),
       ('TS011', 'DVT03', 100, 88000, 110000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS012', 'DVT01', 1, 1000, 1300, 1),
       ('TS012', 'DVT02', 10, 9800, 12500, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS013', 'DVT01', 1, 2800, 3500, 1),
       ('TS013', 'DVT02', 10, 27000, 34000, 0),
       ('TS013', 'DVT03', 20, 53000, 67000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS014', 'DVT08', 1, 1800, 2400, 1),
       ('TS014', 'DVT03', 10, 17000, 23000, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS227', 'DVT01', 1, 850, 1100, 1),
       ('TS227', 'DVT02', 10, 8300, 10500, 0),
       ('TS227', 'DVT03', 30, 24500, 31500, 0);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS447', 'DVT10', 1, 850000, 1050000, 1);

INSERT INTO ChiTietDonViTinh (MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan)
VALUES ('TS450', 'DVT10', 1, 800, 1200, 1),
       ('TS450', 'DVT03', 50, 38000, 55000, 0);

INSERT INTO ChiTietHoatChat (MaHoatChat, MaThuoc, HamLuong)
VALUES ('HC001', 'TS001', 500),
       ('HC006', 'TS002', 500),
       ('HC008', 'TS003', 250),
       ('HC051', 'TS004', 1000),
       ('HC002', 'TS005', 400),
       ('HC004', 'TS006', 81),
       ('HC042', 'TS007', 10),
       ('HC017', 'TS008', 20),
       ('HC034', 'TS009', 500),
       ('HC031', 'TS010', 20),
       ('HC001', 'TS011', 650),
       ('HC006', 'TS012', 250),
       ('HC008', 'TS013', 500),
       ('HC051', 'TS014', 500),
       ('HC002', 'TS015', 200),
       ('HC004', 'TS016', 500),
       ('HC042', 'TS017', 5),
       ('HC017', 'TS018', 40),
       ('HC034', 'TS019', 850),
       ('HC031', 'TS020', 40);

INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
VALUES ('PN001', '2025-09-01', 1, 'Nhập thuốc giảm đau', 'NCC001', 'NV001'),
       ('PN002', '2025-09-02', 1, 'Nhập kháng sinh', 'NCC002', 'NV001'),
       ('PN003', '2025-09-03', 1, 'Nhập vitamin và khoáng chất', 'NCC003', 'NV001'),
       ('PN004', '2025-09-04', 1, 'Nhập thuốc tim mạch', 'NCC004', 'NV001'),
       ('PN005', '2025-09-05', 1, 'Nhập vaccine phòng bệnh', 'NCC005', 'NV001'),
       ('PN006', '2025-09-06', 1, 'Nhập thực phẩm chức năng', 'NCC006', 'NV001'),
       ('PN007', '2025-09-07', 1, 'Nhập thuốc đông y', 'NCC007', 'NV001'),
       ('PN008', '2025-09-08', 1, 'Nhập dụng cụ y tế', 'NCC008', 'NV001'),
       ('PN009', '2025-09-09', 1, 'Nhập mỹ phẩm chăm sóc da', 'NCC009', 'NV001'),
       ('PN010', '2025-09-10', 1, 'Nhập hỗn hợp nhiều loại sản phẩm', 'NCC010', 'NV001');

INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue)
VALUES ('PN001', 'TS001', 'LH00001', 100, 'DVT01', 1200, 0.05, 0.08),
       ('PN001', 'TS002', 'LH00002', 80, 'DVT02', 1500, 0.02, 0.08),
       ('PN002', 'TS005', 'LH00003', 50, 'DVT03', 1800, 0.00, 0.08),
       ('PN002', 'TS006', 'LH00004', 60, 'DVT03', 2000, 0.01, 0.08),
       ('PN003', 'TS004', 'LH00005', 120, 'DVT01', 900, 0.00, 0.05),
       ('PN003', 'TS010', 'LH00006', 70, 'DVT03', 2500, 0.03, 0.08),
       ('PN006', 'TS226', 'LH00007', 90, 'DVT07', 7500, 0.01, 0.05),
       ('PN006', 'TS229', 'LH00008', 60, 'DVT04', 18000, 0.00, 0.05),
       ('PN007', 'TS231', 'LH00009', 100, 'DVT05', 12000, 0.02, 0.05),
       ('PN007', 'TS234', 'LH00010', 80, 'DVT04', 22000, 0.00, 0.05),
       ('PN008', 'TS336', 'LH00011', 100, 'DVT03', 120000, 0.02, 0.05),
       ('PN008', 'TS340', 'LH00012', 80, 'DVT03', 250000, 0.03, 0.05),
       ('PN009', 'TS446', 'LH00013', 40, 'DVT10', 95000, 0.02, 0.08),
       ('PN009', 'TS451', 'LH00014', 200, 'DVT09', 1200, 0.00, 0.08),
       ('PN010', 'TS556', 'LH00015', 70, 'DVT06', 180000, 0.05, 0.05),
       ('PN010', 'TS560', 'LH00016', 50, 'DVT04', 250000, 0.03, 0.05);

INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD)
VALUES ('PN001', 'TS001', 'LH00001', 100, 0, 0, '2025-01-01', '2027-01-01'),
       ('PN001', 'TS002', 'LH00002', 80, 0, 0, '2025-02-01', '2027-02-01'),
       ('PN002', 'TS005', 'LH00003', 50, 0, 0, '2025-03-01', '2026-03-01'),
       ('PN002', 'TS006', 'LH00004', 60, 0, 0, '2025-03-15', '2026-03-15'),
       ('PN003', 'TS004', 'LH00005', 120, 0, 0, '2025-04-01', '2026-04-01'),
       ('PN003', 'TS010', 'LH00006', 70, 0, 0, '2025-05-01', '2027-05-01'),
       ('PN006', 'TS226', 'LH00007', 90, 0, 0, '2025-01-10', '2028-01-10'),
       ('PN006', 'TS229', 'LH00008', 60, 0, 0, '2025-02-20', '2028-02-20'),
       ('PN007', 'TS231', 'LH00009', 100, 0, 0, '2025-03-05', '2028-03-05'),
       ('PN007', 'TS234', 'LH00010', 80, 0, 0, '2025-04-10', '2028-04-10'),
       ('PN008', 'TS336', 'LH00011', 100, 0, 0, '2025-01-25', '2026-07-25'),
       ('PN008', 'TS340', 'LH00012', 80, 0, 0, '2025-02-15', '2026-08-15'),
       ('PN009', 'TS446', 'LH00013', 40, 0, 0, '2025-01-01', '2030-01-01'),
       ('PN009', 'TS451', 'LH00014', 200, 0, 0, '2025-03-01', '2030-03-01'),
       ('PN010', 'TS556', 'LH00015', 70, 0, 0, '2025-02-10', '2027-02-10'),
       ('PN010', 'TS560', 'LH00016', 50, 0, 0, '2025-03-20', '2027-03-20');

INSERT INTO LoaiKhuyenMai (MaLoai, TenLoai, MoTa)
VALUES ('LKM001', 'Tặng kèm sản phẩm', 'Khi khách hàng mua sản phẩm nhất định sẽ được tặng kèm thêm sản phẩm khác'),
       ('LKM002', 'Giảm giá trực tiếp theo sản phẩm', 'Giảm trực tiếp một số tiền nhất định trên giá trị sản phẩm'),
       ('LKM003', 'Giảm giá phần trăm theo sản phẩm',
        'Khách hàng được giảm theo tỷ lệ phần trăm trên giá trị sản phẩm'),
       ('LKM004', 'Giảm trực tiếp theo tổng hóa đơn', 'Khách hàng được giảm trực tiếp trên hóa đơn'),
       ('LKM005', 'Giảm phần trăm theo tổng hóa đơn', 'Khách hàng được giảm theo tỷ lệ phần trăm trên tổng hóa đơn');

INSERT INTO KhuyenMai (MaKM, TenKM, GiaTriKM, GiaTriApDung, LoaiGiaTri, NgayBatDau, NgayKetThuc, MoTa, NgayTao, MaLoai)
VALUES ('KM011', 'Paracetamol giảm 10%', 10, 0, '%', '2025-10-01', '2025-10-31', 'Giảm 10% cho Paracetamol 500mg',
        '2025-10-01 08:00:00', 'LKM003'),
       ('KM012', 'Amoxicillin giảm 500/viên', 500, 0, 'VND', '2025-10-05', '2025-10-25',
        'Giảm 20.000đ khi mua Amoxicillin 500mg', '2025-10-05 08:00:00', 'LKM002'),
       ('KM013', 'Cefuroxime giảm 15%', 15, 0, '%', '2025-10-01', '2025-10-20', 'Giảm 15% cho Cefuroxime 250mg',
        '2025-10-01 08:00:00', 'LKM003'),
       ('KM014', 'Vitamin C tặng Ibu + Para', NULL, 0, NULL, '2025-10-01', '2025-10-31',
        'Mua Vitamin C 1000mg tặng Ibuprofen 400mg và Paracetamol 500mg', '2025-10-01 08:00:00', 'LKM001'),
       ('KM015', 'Ibuprofen giảm 10k', 10000, 0, 'VND', '2025-10-10', '2025-11-10', 'Giảm 10.000đ cho Ibuprofen 400mg',
        '2025-10-10 08:00:00', 'LKM002'),
       ('KM016', 'Ginkgo giảm 12%', 12, 0, '%', '2025-10-01', '2025-10-31', 'Giảm 12% cho Ginkgo Biloba 120mg',
        '2025-10-01 08:00:00', 'LKM003'),
       ('KM017', 'C + Zinc tặng Ginkgo + Gluco', NULL, 0, NULL, '2025-10-05', '2025-11-05',
        'Mua Vitamin C + Zinc tặng Ginkgo Biloba 120mg và Glucosamine 1500mg', '2025-10-05 08:00:00', 'LKM001'),
       ('KM018', 'Glucosamine giảm 50k', 50000, 0, 'VND', '2025-10-01', '2025-11-15',
        'Giảm 50.000đ cho Glucosamine 1500mg', '2025-10-01 08:00:00', 'LKM002'),
       ('KM019', 'Nhiệt kế giảm 5%', 5, 0, '%', '2025-10-01', '2025-12-01', 'Giảm 5% cho Nhiệt kế điện tử',
        '2025-10-01 08:00:00', 'LKM003'),
       ('KM020', 'Máy đo HA giảm 100k', 100000, 0, 'VND', '2025-10-01', '2025-12-31',
        'Giảm 100.000đ cho Máy đo huyết áp bắp tay', '2025-10-01 08:00:00', 'LKM002'),
       ('KM021', 'Hóa đơn trên 300k giảm 30k', 30000, 300000, 'VND', '2025-10-01', '2025-10-31',
        'Khách hàng có hóa đơn từ 300.000đ trở lên sẽ được giảm trực tiếp 30.000đ', '2025-10-01 08:00:00', 'LKM004'),
       ('KM022', 'Hóa đơn trên 500k giảm 70k', 70000, 500000, 'VND', '2025-10-10', '2025-11-10',
        'Khách hàng có hóa đơn từ 500.000đ trở lên sẽ được giảm trực tiếp 70.000đ', '2025-10-10 08:00:00', 'LKM004'),
       ('KM023', 'Hóa đơn trên 1 triệu giảm 150k', 150000, 1000000, 'VND', '2025-10-15', '2025-12-15',
        'Giảm ngay 150.000đ khi tổng hóa đơn đạt từ 1.000.000đ', '2025-10-15 08:00:00', 'LKM004'),
       ('KM024', 'Hóa đơn trên 200k giảm 5%', 5, 200000, '%', '2025-10-01', '2025-11-01',
        'Khách hàng có hóa đơn từ 200.000đ trở lên được giảm 5% tổng giá trị hóa đơn', '2025-10-01 08:00:00', 'LKM005'),
       ('KM025', 'Hóa đơn trên 800k giảm 8%', 8, 800000, '%', '2025-10-05', '2025-11-30',
        'Khách hàng có hóa đơn từ 800.000đ trở lên được giảm 8% tổng giá trị hóa đơn', '2025-10-05 08:00:00', 'LKM005'),
       ('KM026', 'Hóa đơn trên 1.5 triệu giảm 10%', 10, 1500000, '%', '2025-10-20', '2025-12-31',
        'Khách hàng có hóa đơn từ 1.500.000đ trở lên được giảm 10% tổng giá trị hóa đơn', '2025-10-20 08:00:00',
        'LKM005');

INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa)
VALUES ('TS001', 'KM011', 1, 50),
       ('TS002', 'KM012', 1, 50),
       ('TS007', 'KM012', 1, 50),
       ('TS015', 'KM012', 1, 50),
       ('TS003', 'KM013', 1, 50),
       ('TS005', 'KM013', 1, 50),
       ('TS004', 'KM014', 2, 20),
       ('TS005', 'KM015', 1, 50),
       ('TS343', 'KM016', 1, 50),
       ('TS344', 'KM017', 2, 15),
       ('TS345', 'KM018', 1, 50),
       ('TS446', 'KM019', 1, 30),
       ('TS447', 'KM020', 1, 30);

INSERT INTO Thuoc_SP_TangKem (MaKM, MaThuocTangKem, SoLuong)
VALUES ('KM014', 'TS005', 1),
       ('KM014', 'TS001', 1),
       ('KM017', 'TS343', 1),
       ('KM017', 'TS345', 1);

INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai)
VALUES ('KH041', 'Phan Thị Hạnh', '0914000001', 'hanh41@gmail.com', '1995-01-10', 0, 'Hà Nội', 1),
       ('KH042', 'Lê Văn Khải', '0914000002', 'khai42@gmail.com', '1990-05-15', 1, 'HCM', 1),
       ('KH043', 'Đỗ Minh Đức', '0914000003', 'duc43@gmail.com', '1988-03-05', 1, 'Đà Nẵng', 1);

INSERT INTO NhanVien (MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi, VaiTro, TrangThai, TaiKhoan, MatKhau,
                      NgayVaoLam, NgayKetThuc, TrangThaiXoa)
VALUES ('NV041', 'Trần Ngọc Huyền', '0904000001', 'huyen41@qlnt.vn', '1994-02-14', 0, 'Hà Nội', 'Bán hàng', 1,
        'huyen41', '123', '2024-01-01', NULL, 0),
       ('NV042', 'Phạm Quang Minh', '0904000002', 'minh42@qlnt.vn', '1993-08-12', 1, 'HCM', 'Bán hàng', 1, 'minh42',
        '123', '2024-02-01', NULL, 0),
       ('NV043', 'Nguyễn Tấn Lộc', '0904000003', 'loc43@qlnt.vn', '1997-10-02', 1, 'Đà Nẵng', 'Bán hàng', 1, 'loc43',
        '123', '2024-03-01', NULL, 0);

INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
VALUES ('PN041', '2025-01-10', 1, 'Nhập hàng đầu năm', 'NCC001', 'NV041'),
       ('PN042', '2025-03-15', 1, 'Nhập tháng 3', 'NCC002', 'NV042'),
       ('PN043', '2025-05-12', 1, 'Nhập tháng 5', 'NCC001', 'NV043'),
       ('PN044', '2025-07-05', 1, 'Nhập tháng 7', 'NCC001', 'NV041'),
       ('PN045', '2025-09-18', 1, 'Nhập tháng 9', 'NCC002', 'NV043'),
       ('PN046', '2025-10-01', 1, 'Nhập đầu tháng 10', 'NCC001', 'NV042'),
       ('PN047', '2025-10-10', 1, 'Nhập giữa tháng 10', 'NCC002', 'NV043'),
       ('PN048', '2025-10-20', 1, 'Nhập tuần này', 'NCC001', 'NV041'),
       ('PN049', '2025-10-23', 1, 'Nhập hôm qua', 'NCC002', 'NV042'),
       ('PN050', '2025-10-25', 1, 'Nhập hôm nay', 'NCC001', 'NV043'),
       ('PN051', '2025-11-05', 1, 'Nhập tháng 11', 'NCC001', 'NV041'),
       ('PN052', '2025-12-10', 1, 'Nhập tháng 12', 'NCC002', 'NV042'),
       ('PN053', '2025-12-31', 1, 'Nhập cuối năm', 'NCC001', 'NV043');

INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, MaDVT, GiaNhap, ChietKhau, Thue)
VALUES ('PN041', 'TS001', 'LH00041', 500, NULL, 1200, 0, 5),
       ('PN042', 'TS002', 'LH00042', 600, NULL, 1300, 0, 5),
       ('PN043', 'TS003', 'LH00043', 400, NULL, 1100, 0, 5),
       ('PN044', 'TS005', 'LH00044', 700, NULL, 1400, 0, 5),
       ('PN045', 'TS004', 'LH00045', 800, NULL, 1000, 0, 5),
       ('PN046', 'TS001', 'LH00046', 500, NULL, 1200, 0, 5),
       ('PN047', 'TS002', 'LH00047', 600, NULL, 1300, 0, 5),
       ('PN048', 'TS003', 'LH00048', 400, NULL, 1100, 0, 5),
       ('PN049', 'TS005', 'LH00049', 700, NULL, 1400, 0, 5),
       ('PN050', 'TS004', 'LH00050', 800, NULL, 1000, 0, 5),
       ('PN051', 'TS001', 'LH00051', 500, NULL, 1200, 0, 5),
       ('PN052', 'TS002', 'LH00052', 600, NULL, 1300, 0, 5),
       ('PN053', 'TS003', 'LH00053', 400, NULL, 1100, 0, 5),
       ('PN043', 'TS003', 'LH00053', 400, NULL, 2000, 0, 5),
       ('PN044', 'TS007', 'LH00054', 300, NULL, 900, 0, 5),
       ('PN045', 'TS008', 'LH00055', 280, NULL, 1800, 0, 5),
       ('PN046', 'TS009', 'LH00056', 500, NULL, 1300, 0, 5),
       ('PN047', 'TS011', 'LH00057', 800, NULL, 950, 0, 5),
       ('PN048', 'TS012', 'LH00058', 400, NULL, 1000, 0, 5),
       ('PN049', 'TS013', 'LH00059', 350, NULL, 2800, 0, 5),
       ('PN050', 'TS014', 'LH00060', 600, NULL, 1800, 0, 5),
       ('PN041', 'TS227', 'LH00061', 450, NULL, 850, 0, 5),
       ('PN042', 'TS447', 'LH00062', 50, NULL, 850000, 0, 5),
       ('PN043', 'TS450', 'LH00063', 1000, NULL, 800, 0, 5);

INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, SoLuongDat, SoLuongGiu, NSX, HSD)
VALUES ('PN041', 'TS001', 'LH00041', 500, 0, 0, '2025-01-01', '2027-01-01'),
       ('PN042', 'TS002', 'LH00042', 600, 0, 0, '2025-03-01', '2027-03-01'),
       ('PN043', 'TS003', 'LH00043', 400, 0, 0, '2025-05-01', '2027-05-01'),
       ('PN044', 'TS005', 'LH00044', 700, 0, 0, '2025-07-01', '2027-07-01'),
       ('PN045', 'TS004', 'LH00045', 800, 0, 0, '2025-09-01', '2027-09-01'),
       ('PN046', 'TS001', 'LH00046', 500, 0, 0, '2025-10-01', '2027-10-01'),
       ('PN047', 'TS002', 'LH00047', 600, 0, 0, '2025-10-10', '2027-10-10'),
       ('PN048', 'TS003', 'LH00048', 400, 0, 0, '2025-10-20', '2027-10-20'),
       ('PN049', 'TS005', 'LH00049', 700, 0, 0, '2025-10-23', '2027-10-23'),
       ('PN050', 'TS004', 'LH00050', 800, 0, 0, '2025-10-25', '2027-10-25'),
       ('PN051', 'TS001', 'LH00051', 500, 0, 0, '2025-11-05', '2027-11-05'),
       ('PN052', 'TS002', 'LH00052', 600, 0, 0, '2025-12-10', '2027-12-10'),
       ('PN043', 'TS003', 'LH00053', 400, 0, 0, '2025-12-31', '2027-12-31'),
       ('PN044', 'TS007', 'LH00054', 300, 0, 0, '2025-02-01', '2027-02-01'),
       ('PN045', 'TS008', 'LH00055', 280, 0, 0, '2025-02-15', '2027-02-15'),
       ('PN046', 'TS009', 'LH00056', 500, 0, 0, '2025-03-01', '2027-03-01'),
       ('PN047', 'TS011', 'LH00057', 800, 0, 0, '2025-03-20', '2027-03-20'),
       ('PN048', 'TS012', 'LH00058', 400, 0, 0, '2025-04-05', '2027-04-05'),
       ('PN049', 'TS013', 'LH00059', 350, 0, 0, '2025-04-18', '2027-04-18'),
       ('PN050', 'TS014', 'LH00060', 600, 0, 0, '2025-05-01', '2027-05-01'),
       ('PN041', 'TS227', 'LH00061', 450, 0, 0, '2025-05-10', '2027-05-10'),
       ('PN042', 'TS447', 'LH00062', 50, 0, 0, '2025-06-01', '2029-06-01'),
       ('PN043', 'TS450', 'LH00063', 1000, 0, 0, '2025-06-15', '2028-06-15');

INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, LoaiHoaDon, MaDonThuoc, MaKH, MaNV)
VALUES ('HD111', '2025-09-16 09:00:00', 1, 'OTC', NULL, 'KH007', 'NV002'),
       ('HD112', '2025-09-16 11:30:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD213', '2025-09-16 15:45:00', 1, 'OTC', NULL, 'KH008', 'NV003'),
       ('HD114', '2025-09-17 08:20:00', 1, 'OTC', NULL, 'KH009', 'NV002'),
       ('HD315', '2025-09-17 10:15:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD416', '2025-09-18 14:00:00', 1, 'OTC', NULL, 'KH010', 'NV003'),
       ('HD117', '2025-09-18 17:30:00', 1, 'OTC', NULL, 'KH011', 'NV002'),
       ('HD218', '2025-09-19 09:40:00', 1, 'OTC', NULL, 'KH001', 'NV001'),
       ('HD319', '2025-09-19 13:00:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD620', '2025-09-20 16:20:00', 1, 'OTC', NULL, 'KH002', 'NV002');

INSERT INTO ChiTietHoaDon (MaHD, MaLH, SoLuong, MaDVT, DonGia, GiamGia)
VALUES ('HD111', 'LH00001', 5, 'DVT01', 1500, 100),
       ('HD111', 'LH00005', 1, 'DVT04', 1200, 0),
       ('HD112', 'LH00002', 2, 'DVT02', 1900, 0),
       ('HD112', 'LH00004', 1, 'DVT03', 3000, 0),
       ('HD213', 'LH00015', 1, 'DVT06', 250000, 20000),
       ('HD213', 'LH00014', 5, 'DVT03', 1800, 0),
       ('HD114', 'LH00009', 2, 'DVT03', 15000, 0),
       ('HD114', 'LH00003', 1, 'DVT03', 2500, 0),
       ('HD315', 'LH00007', 1, 'DVT03', 9500, 0),
       ('HD416', 'LH00012', 1, 'DVT03', 320000, 0),
       ('HD416', 'LH00011', 1, 'DVT03', 150000, 5000),
       ('HD117', 'LH00008', 3, 'DVT04', 25000, 0),
       ('HD218', 'LH00016', 2, 'DVT03', 350000, 20000),
       ('HD218', 'LH00013', 1, 'DVT10', 130000, 0),
       ('HD319', 'LH00006', 2, 'DVT03', 3500, 0),
       ('HD319', 'LH00002', 1, 'DVT02', 1900, 0),
       ('HD620', 'LH00005', 2, 'DVT04', 1200, 0),
       ('HD620', 'LH00001', 10, 'DVT01', 1500, 150);

INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, LoaiHoaDon, MaDonThuoc, MaKH, MaNV)
VALUES ('HD001', '2025-09-11 08:30:00', 1, 'OTC', NULL, 'KH001', 'NV001'),
       ('HD002', '2025-09-11 09:15:00', 1, 'OTC', NULL, 'KH002', 'NV002'),
       ('HD003', '2025-09-11 10:45:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD004', '2025-09-12 14:00:00', 1, 'OTC', NULL, 'KH003', 'NV001'),
       ('HD005', '2025-09-12 16:30:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD006', '2025-09-13 11:00:00', 1, 'OTC', NULL, 'KH004', 'NV003'),
       ('HD007', '2025-09-13 15:20:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD008', '2025-09-14 09:40:00', 1, 'OTC', NULL, 'KH005', 'NV002'),
       ('HD009', '2025-09-14 13:00:00', 1, 'OTC', NULL, 'KH006', 'NV003'),
       ('HD010', '2025-09-15 17:00:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD011', '2025-10-01 08:20:00', 1, 'OTC', NULL, 'KH001', 'NV001'),
       ('HD012', '2025-10-01 09:10:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD013', '2025-10-01 10:45:00', 1, 'OTC', NULL, 'KH002', 'NV003'),
       ('HD014', '2025-10-01 14:00:00', 1, 'OTC', NULL, 'KH003', 'NV001'),
       ('HD015', '2025-10-01 16:30:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD016', '2025-10-02 08:40:00', 1, 'OTC', NULL, 'KH004', 'NV003'),
       ('HD017', '2025-10-02 10:20:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD018', '2025-10-02 14:15:00', 1, 'OTC', NULL, 'KH005', 'NV002'),
       ('HD019', '2025-10-02 16:50:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD020', '2025-10-02 09:00:00', 1, 'OTC', NULL, 'KH006', 'NV001'),
       ('HD021', '2025-10-03 10:30:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD022', '2025-10-03 14:10:00', 1, 'OTC', NULL, 'KH001', 'NV003'),
       ('HD023', '2025-10-03 16:40:00', 1, 'OTC', NULL, 'KH002', 'NV001'),
       ('HD024', '2025-10-04 08:50:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD025', '2025-10-04 10:25:00', 1, 'OTC', NULL, 'KH003', 'NV003'),
       ('HD026', '2025-10-04 14:00:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD027', '2025-10-05 16:15:00', 1, 'OTC', NULL, 'KH004', 'NV002'),
       ('HD028', '2025-10-06 09:10:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD029', '2025-10-06 11:40:00', 1, 'OTC', NULL, 'KH005', 'NV001'),
       ('HD030', '2025-10-06 15:30:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD031', '2025-11-01 08:15:00', 1, 'OTC', NULL, 'KH001', 'NV001'),
       ('HD032', '2025-11-01 10:30:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD033', '2025-11-02 09:45:00', 1, 'OTC', NULL, 'KH002', 'NV003'),
       ('HD034', '2025-11-03 14:10:00', 1, 'OTC', NULL, 'KH003', 'NV001'),
       ('HD035', '2025-11-04 16:20:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD036', '2025-11-05 08:40:00', 1, 'OTC', NULL, 'KH004', 'NV003'),
       ('HD037', '2025-11-06 10:00:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD038', '2025-11-07 14:30:00', 1, 'OTC', NULL, 'KH005', 'NV002'),
       ('HD039', '2025-11-08 16:45:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD040', '2025-11-09 09:20:00', 1, 'OTC', NULL, 'KH006', 'NV001'),
       ('HD041', '2025-12-01 08:10:00', 1, 'OTC', NULL, 'KH001', 'NV002'),
       ('HD042', '2025-12-02 10:25:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD043', '2025-12-03 09:50:00', 1, 'OTC', NULL, 'KH002', 'NV001'),
       ('HD044', '2025-12-04 14:05:00', 1, 'OTC', NULL, 'KH003', 'NV002'),
       ('HD045', '2025-12-05 16:35:00', 1, 'OTC', NULL, NULL, 'NV003'),
       ('HD046', '2025-12-06 08:55:00', 1, 'OTC', NULL, 'KH004', 'NV001'),
       ('HD047', '2025-12-07 10:40:00', 1, 'OTC', NULL, NULL, 'NV002'),
       ('HD048', '2025-12-08 14:25:00', 1, 'OTC', NULL, 'KH005', 'NV003'),
       ('HD049', '2025-12-09 16:10:00', 1, 'OTC', NULL, NULL, 'NV001'),
       ('HD050', '2025-12-10 09:30:00', 1, 'OTC', NULL, 'KH006', 'NV002');

INSERT INTO ChiTietHoaDon (MaHD, MaLH, SoLuong, MaDVT, DonGia, GiamGia)
VALUES ('HD001', 'LH00001', 10, 'DVT01', 1500, 150),
       ('HD001', 'LH00002', 10, 'DVT02', 1900, 0),
       ('HD002', 'LH00005', 3, 'DVT04', 1200, 0),
       ('HD003', 'LH00003', 2, 'DVT03', 2500, 0),
       ('HD004', 'LH00007', 2, 'DVT03', 9500, 0),
       ('HD004', 'LH00009', 1, 'DVT03', 15000, 0),
       ('HD005', 'LH00011', 1, 'DVT03', 150000, 0),
       ('HD006', 'LH00013', 1, 'DVT10', 130000, 0),
       ('HD006', 'LH00014', 10, 'DVT03', 1800, 0),
       ('HD007', 'LH00004', 2, 'DVT03', 3000, 0),
       ('HD008', 'LH00015', 1, 'DVT06', 250000, 0),
       ('HD008', 'LH00016', 1, 'DVT03', 350000, 0),
       ('HD009', 'LH00008', 2, 'DVT04', 25000, 0),
       ('HD009', 'LH00012', 1, 'DVT03', 320000, 0),
       ('HD010', 'LH00006', 4, 'DVT03', 3500, 0),
       ('HD011', 'LH00041', 20, 'DVT01', 1000, 0),
       ('HD011', 'LH00042', 1, 'DVT02', 14500, 0),
       ('HD012', 'LH00053', 10, 'DVT01', 2500, 200),
       ('HD012', 'LH00045', 1, 'DVT03', 30000, 0),
       ('HD013', 'LH00054', 10, 'DVT01', 1200, 0),
       ('HD013', 'LH00060', 5, 'DVT08', 2400, 0),
       ('HD014', 'LH00056', 30, 'DVT01', 1700, 300),
       ('HD014', 'LH00057', 2, 'DVT02', 11500, 0),
       ('HD015', 'LH00058', 1, 'DVT02', 12500, 0),
       ('HD015', 'LH00061', 20, 'DVT01', 1100, 0),
       ('HD016', 'LH00059', 10, 'DVT01', 3500, 0),
       ('HD016', 'LH00041', 1, 'DVT02', 9500, 0),
       ('HD017', 'LH00062', 1, 'DVT10', 1050000, 50000),
       ('HD018', 'LH00063', 100, 'DVT10', 500, 0),
       ('HD018', 'LH00054', 1, 'DVT02', 11500, 0),
       ('HD019', 'LH00045', 3, 'DVT08', 3200, 0),
       ('HD019', 'LH00056', 1, 'DVT02', 16500, 0),
       ('HD020', 'LH00041', 15, 'DVT01', 1000, 0),
       ('HD020', 'LH00060', 1, 'DVT03', 23000, 0),
       ('HD021', 'LH00057', 20, 'DVT01', 1200, 0),
       ('HD021', 'LH00061', 1, 'DVT02', 10500, 0),
       ('HD022', 'LH00058', 10, 'DVT01', 1300, 0),
       ('HD022', 'LH00053', 1, 'DVT02', 24000, 0),
       ('HD023', 'LH00059', 1, 'DVT03', 67000, 2000),
       ('HD024', 'LH00063', 1, 'DVT03', 55000, 0),
       ('HD025', 'LH00045', 2, 'DVT08', 3200, 0),
       ('HD025', 'LH00054', 10, 'DVT01', 1200, 0),
       ('HD026', 'LH00062', 1, 'DVT10', 1050000, 0),
       ('HD027', 'LH00056', 20, 'DVT01', 1700, 0),
       ('HD027', 'LH00041', 10, 'DVT01', 1000, 0),
       ('HD028', 'LH00060', 10, 'DVT08', 2400, 500),
       ('HD029', 'LH00061', 1, 'DVT03', 31500, 0),
       ('HD030', 'LH00063', 200, 'DVT10', 500, 0),
       ('HD031', 'LH00041', 10, 'DVT01', 1500, 0),
       ('HD031', 'LH00042', 2, 'DVT02', 19000, 1000),
       ('HD032', 'LH00043', 20, 'DVT01', 2500, 500),
       ('HD032', 'LH00044', 1, 'DVT02', 24000, 0),
       ('HD033', 'LH00045', 1, 'DVT03', 47000, 2000),
       ('HD033', 'LH00046', 15, 'DVT01', 1500, 0),
       ('HD034', 'LH00047', 2, 'DVT02', 19000, 0),
       ('HD034', 'LH00048', 10, 'DVT01', 2500, 0),
       ('HD035', 'LH00049', 1, 'DVT02', 24000, 500),
       ('HD035', 'LH00050', 1, 'DVT03', 28000, 0),
       ('HD036', 'LH00051', 20, 'DVT01', 1500, 0),
       ('HD036', 'LH00052', 1, 'DVT02', 19000, 0),
       ('HD037', 'LH00053', 30, 'DVT01', 2500, 1000),
       ('HD037', 'LH00054', 1, 'DVT02', 26000, 0),
       ('HD038', 'LH00055', 1, 'DVT03', 30000, 0),
       ('HD038', 'LH00056', 20, 'DVT01', 1800, 0),
       ('HD039', 'LH00057', 2, 'DVT02', 22000, 1000),
       ('HD039', 'LH00058', 10, 'DVT01', 2500, 0),
       ('HD040', 'LH00059', 15, 'DVT01', 2000, 0),
       ('HD040', 'LH00060', 1, 'DVT02', 21000, 0),
       ('HD041', 'LH00061', 20, 'DVT01', 1800, 0),
       ('HD041', 'LH00042', 1, 'DVT02', 19000, 0),
       ('HD042', 'LH00043', 10, 'DVT01', 2500, 0),
       ('HD042', 'LH00044', 2, 'DVT02', 24000, 1000),
       ('HD043', 'LH00045', 1, 'DVT03', 47000, 0),
       ('HD043', 'LH00046', 15, 'DVT01', 1500, 0),
       ('HD044', 'LH00047', 1, 'DVT02', 19000, 0),
       ('HD044', 'LH00048', 20, 'DVT01', 2500, 0),
       ('HD045', 'LH00049', 2, 'DVT02', 24000, 1000),
       ('HD045', 'LH00050', 1, 'DVT03', 28000, 0),
       ('HD046', 'LH00051', 25, 'DVT01', 1500, 0),
       ('HD046', 'LH00052', 1, 'DVT02', 19000, 0),
       ('HD047', 'LH00053', 20, 'DVT01', 2500, 0),
       ('HD047', 'LH00054', 1, 'DVT02', 26000, 0),
       ('HD048', 'LH00055', 1, 'DVT03', 30000, 0),
       ('HD048', 'LH00056', 30, 'DVT01', 1800, 0),
       ('HD049', 'LH00057', 2, 'DVT02', 22000, 1000),
       ('HD049', 'LH00058', 10, 'DVT01', 2500, 0),
       ('HD050', 'LH00059', 20, 'DVT01', 2000, 0),
       ('HD050', 'LH00060', 1, 'DVT02', 21000, 0);

INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV, TrangThai)
VALUES ('PDH001', '2025-10-01', 50000, 'Khách đặt hàng mới', 'KH001', 'NV001', 0),
       ('PDH002', '2025-10-02', 100000, 'Đặt hàng lại lô thuốc cũ', 'KH002', 'NV002', 0),
       ('PDH003', '2025-10-03', 0, 'Khách đặt hàng gấp', 'KH003', 'NV003', 0);

INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, TrangThai)
VALUES ('PDH001', 'TS001', 5, 'DVT01', 12000, 0.05, 0),
       ('PDH001', 'TS002', 10, 'DVT02', 8000, 0, 0),
       ('PDH002', 'TS003', 3, 'DVT03', 15000, 0.1, 1);

INSERT INTO PhieuDoiHang (MaPD, NgayLap, GhiChu, MaNV, MaKH, MaHD)
VALUES ('PD001', '2025-10-15', 'Đổi 1 hộp Ibuprofen cùng loại', 'NV002', 'KH003', 'HD003'),
       ('PD002', '2025-10-16', 'Đổi 5 viên Paracetamol', 'NV001', 'KH001', 'HD001'),
       ('PD003', '2025-10-17', 'Đổi 1 hộp Vitamin D3', 'NV003', 'KH005', 'HD005');

INSERT INTO ChiTietPhieuDoiHang (MaLH, MaPD, MaThuoc, SoLuong, MaDVT, LyDoDoi)
VALUES ('LH00003', 'PD001', 'TS005', 1, 'DVT03', 'Hộp bị móp'),
       ('LH00001', 'PD002', 'TS001', 5, 'DVT01', 'Viên cũ bị gãy'),
       ('LH00011', 'PD003', 'TS336', 1, 'DVT03', 'Hộp bị ướt');

INSERT INTO PhieuTraHang (MaPT, NgayLap, GhiChu, MaNV, MaHD, MaKH)
VALUES ('PT001', '2025-09-13', 'Trả lại Hoạt huyết dưỡng não và Cao ích mẫu', 'NV001', 'HD004', 'KH003'),
       ('PT002', '2025-09-15', 'Trả lại Kem chống nắng', 'NV002', 'HD008', 'KH005'),
       ('PT003', '2025-09-16', 'Trả lại Găng tay y tế', 'NV003', 'HD006', 'KH004');

INSERT INTO ChiTietPhieuTraHang (MaLH, MaPT, MaThuoc, SoLuong, MaDVT, DonGia, GiamGia, LyDoTra)
VALUES ('LH00007', 'PT001', 'TS226', 1, 'DVT03', 9500, 0, 'Dư thừa'),
       ('LH00009', 'PT001', 'TS231', 1, 'DVT03', 15000, 0, 'Dư thừa'),
       ('LH00015', 'PT002', 'TS556', 1, 'DVT06', 250000, 0, 'Không phù hợp'),
       ('LH00014', 'PT003', 'TS451', 1, 'DVT03', 1800, 0, 'Mua nhầm');


-- =============================================================================
-- Thống kê số dòng đã chuyển theo bảng
-- =============================================================================
-- ThongSoUngDung: 2 dòng
-- KhachHang: 14 dòng
-- NhanVien: 7 dòng
-- LuongNhanVien: 4 dòng
-- LoaiHang: 8 dòng
-- NhomDuocLy: 34 dòng
-- DonViTinh: 13 dòng
-- HoatChat: 70 dòng
-- NhaCungCap: 14 dòng
-- KeHang: 11 dòng
-- Thuoc_SanPham: 60 dòng
-- ChiTietDonViTinh: 64 dòng
-- ChiTietHoatChat: 20 dòng
-- PhieuNhap: 23 dòng
-- ChiTietPhieuNhap: 40 dòng
-- Thuoc_SP_TheoLo: 39 dòng
-- LoaiKhuyenMai: 5 dòng
-- KhuyenMai: 16 dòng
-- ChiTietKhuyenMai: 13 dòng
-- Thuoc_SP_TangKem: 4 dòng
-- HoaDon: 60 dòng
-- ChiTietHoaDon: 106 dòng
-- PhieuDatHang: 3 dòng
-- ChiTietPhieuDatHang: 3 dòng
-- PhieuDoiHang: 3 dòng
-- ChiTietPhieuDoiHang: 3 dòng
-- PhieuTraHang: 3 dòng
-- ChiTietPhieuTraHang: 4 dòng
-- Không phát hiện statement INSERT nào bị bỏ qua khi parse.
