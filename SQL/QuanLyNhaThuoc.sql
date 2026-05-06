use master
CREATE DATABASE QuanLyNhaThuoc;
GO

USE QuanLyNhaThuoc;
GO

--Link thư mục hình ảnh thuốc
DECLARE @path NVARCHAR(255) = N'C:\Users\hiepdeptrai\Desktop\hk1_2025-2026\QLHT\SQL\imgThuoc\';

-- =========================
-- Bảng KhachHang
-- =========================
CREATE TABLE KhachHang (
    MaKH       VARCHAR(10) PRIMARY KEY,
    TenKH      NVARCHAR(50) NOT NULL,
    SDT        VARCHAR(15) NOT NULL,
    Email      VARCHAR(50),
    NgaySinh   DATE,
    GioiTinh   BIT NOT NULL,
    DiaChi     NVARCHAR(50),
    TrangThai  BIT NOT NULL
);
-- =========================
-- Bảng NhanVien
-- =========================
CREATE TABLE NhanVien (
    MaNV       VARCHAR(10) PRIMARY KEY,
    TenNV      NVARCHAR(50) NOT NULL,
    SDT        VARCHAR(15) NOT NULL,
    Email      VARCHAR(50),
    NgaySinh   DATE NOT NULL,
    GioiTinh   NVARCHAR(5) NOT NULL,
    DiaChi     NVARCHAR(50),
	VaiTro	   NVARCHAR(20) NOT NULL,
    TrangThai  BIT NOT NULL,
    TaiKhoan   VARCHAR(50) NOT NULL,
    MatKhau    VARCHAR(50) NOT NULL,
	NgayVaoLam Date NOT NULL,
	NgayKetThuc Date,
	TrangThaiXoa BIT NOT NULL

);
-- =========================
-- Bảng LuongNhanVien
-- =========================
CREATE TABLE LuongNhanVien (
    MaLNV      VARCHAR(10) PRIMARY KEY,
    TuNgay     DATE NOT NULL,
    DenNgay    DATE ,
    LuongCoBan FLOAT NOT NULL,
    PhuCap     FLOAT NOT NULL,
    GhiChu     NVARCHAR(255) NOT NULL,
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV)
);

-- =========================
-- Bảng NhaCungCap
-- =========================
CREATE TABLE NhaCungCap (
    MaNCC      VARCHAR(10) PRIMARY KEY,
    TenNCC     NVARCHAR(50) NOT NULL,
    DiaChi     NVARCHAR(100),
    SDT        VARCHAR(20) NOT NULL,
    Email      VARCHAR(50),
	GPKD       VARCHAR(50),
    GhiChu     NVARCHAR(255),
    TenCongTy  NVARCHAR(50),
    MSThue     VARCHAR(20)
);

-- =========================
-- Bảng LoaiHang
-- =========================
CREATE TABLE LoaiHang (
    MaLoaiHang       VARCHAR(10) PRIMARY KEY,
    TenLH      NVARCHAR(50),
    MoTa       NVARCHAR(255)
);

-- =========================
-- Bảng NhomDuocLy
-- =========================
CREATE TABLE NhomDuocLy (
    MaNDL      VARCHAR(10) PRIMARY KEY,
    TenNDL     NVARCHAR(50),
    MoTa       NVARCHAR(255)
);

-- =========================
-- Bảng KeHang
-- =========================
CREATE TABLE KeHang (
    MaKe      VARCHAR(10) PRIMARY KEY,
    TenKe     NVARCHAR(50),
    MoTa       NVARCHAR(255)
);

-- =========================
-- Bảng Thuoc_SanPham
-- =========================
CREATE TABLE Thuoc_SanPham (
    MaThuoc    VARCHAR(10) PRIMARY KEY,
    TenThuoc   NVARCHAR(100),
    HamLuong   INT,
    DonViHL    VARCHAR(20),
    DuongDung  NVARCHAR(20),
    QuyCachDongGoi NVARCHAR(100),
    SDK_GPNK   VARCHAR(20),
    HangSX     NVARCHAR(30),
    NuocSX     NVARCHAR(20),
    HinhAnh    VARBINARY(MAX) NULL,
	MaLoaiHang VARCHAR(10) FOREIGN KEY REFERENCES LoaiHang(MaLoaiHang),
    MaNDL      VARCHAR(10) FOREIGN KEY REFERENCES NhomDuocLy(MaNDL),
	ViTri	   VARCHAR(10) FOREIGN KEY REFERENCES KeHang(MaKe),
    TrangThaiXoa BIT NOT NULL,
    ETC BIT NOT NULL DEFAULT 0
);


-- =========================
-- Bảng PhieuNhap
-- =========================
CREATE TABLE PhieuNhap (
    MaPN       VARCHAR(10) PRIMARY KEY,
    NgayNhap   DATE NOT NULL,
    TrangThai  BIT,
    GhiChu     NVARCHAR(255),
    MaNCC      VARCHAR(10) FOREIGN KEY REFERENCES NhaCungCap(MaNCC),
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV)
);

-- =========================
-- Bảng ChiTietPhieuNhap
-- =========================
CREATE TABLE ChiTietPhieuNhap (
    MaPN       VARCHAR(10) FOREIGN KEY REFERENCES PhieuNhap(MaPN),
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
	MaLH       VARCHAR(10) ,
    SoLuong    INT NOT NULL,
	MaDVT      VARCHAR(10),
    GiaNhap    FLOAT NOT NULL,
    ChietKhau  FLOAT NOT NULL,
    Thue       FLOAT NOT NULL,
    PRIMARY KEY (MaPN, MaThuoc, MaLH)
);

-- =========================
-- Bảng Thuoc_SP_TheoLo
-- =========================
CREATE TABLE Thuoc_SP_TheoLo (
    MaPN    VARCHAR(10),
    MaThuoc VARCHAR(10),
    MaLH    VARCHAR(10),
    SoLuongTon INT,
	SoLuongDat INT DEFAULT 0,
	SoLuongGiu INT DEFAULT 0,
    NSX DATE,
    HSD DATE,
    PRIMARY KEY (MaLH),
    FOREIGN KEY (MaPN, MaThuoc,MaLH) REFERENCES ChiTietPhieuNhap(MaPN, MaThuoc,MaLH)
);
-- =========================
-- Bảng DonViTinh
-- =========================
CREATE TABLE DonViTinh (
    MaDVT      VARCHAR(10) PRIMARY KEY,
    TenDonViTinh NVARCHAR(50) NOT NULL,
    KiHieu     NVARCHAR(10) NOT NULL
);

-- =========================
-- Bảng ChiTietDonViTinh
-- =========================
CREATE TABLE ChiTietDonViTinh (
     MaThuoc       VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
     MaDVT      VARCHAR(10) FOREIGN KEY REFERENCES DonViTinh(MaDVT),
     HeSoQuyDoi FLOAT NOT NULL,
     GiaNhap    FLOAT NOT NULL,
     GiaBan     FLOAT NOT NULL,
     DonViCoBan BIT NOT NULL DEFAULT 0,
     PRIMARY KEY(MaThuoc, MaDVT)
);

-- =========================
-- Bảng HoaDon
-- =========================
CREATE TABLE HoaDon (
    MaHD       VARCHAR(10) PRIMARY KEY,
    NgayLap    DATETIME NOT NULL,
    TrangThai  NVARCHAR(10) NOT NULL,
    LoaiHoaDon VARCHAR(3) NOT NULL DEFAULT 'OTC',
    MaDonThuoc VARCHAR(20) NULL,
	MaKH       VARCHAR(10) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV)
);


-- =========================
-- Bảng ChiTietHoaDon
-- =========================
CREATE TABLE ChiTietHoaDon (
	MaHD       VARCHAR(10) FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaLH       VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SP_TheoLo(MaLH),
    SoLuong    INT NOT NULL,
	MaDVT      VARCHAR(10),
    DonGia     FLOAT NOT NULL,
    GiamGia    FLOAT NOT NULL,
	PRIMARY KEY (MaHD, MaLH, MaDVT)
);

-- =========================
-- Bảng HoatDong
-- =========================
CREATE TABLE HoatDong (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    MaHDong AS ('HD' + RIGHT('0000' + CAST(ID AS VARCHAR(4)), 4)) PERSISTED,
    LoaiHD NVARCHAR(20),
    ThoiGian DATETIME DEFAULT GETDATE(),
    BangDL NVARCHAR(50),
	MaNV VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
    NoiDung NVARCHAR(MAX)
);

-- =========================
-- Bảng PhieuDatHang
-- =========================
CREATE TABLE PhieuDatHang (
    MaPDat     VARCHAR(10) PRIMARY KEY,
    NgayLap    DATE NOT NULL,
    SoTienCoc  FLOAT,
    GhiChu     NVARCHAR(255),
    MaKH       VARCHAR(10) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
	TrangThai INT DEFAULT 0
);

-- =========================
-- Bảng ChiTietPhieuDatHang
-- =========================
CREATE TABLE ChiTietPhieuDatHang (
    MaPDat     VARCHAR(10) FOREIGN KEY REFERENCES PhieuDatHang(MaPDat),
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    SoLuong    INT NOT NULL,
	MaDVT	   VARCHAR(10),
    DonGia     FLOAT NOT NULL,
    GiamGia    FLOAT NOT NULL DEFAULT 0,
	TrangThai BIT DEFAULT 0,
    PRIMARY KEY (MaPDat, MaThuoc, MaDVT)
);


-- =========================
-- Bảng PhieuDoiHang
-- =========================
-- =========================
-- Bảng PhieuDoiHang
-- =========================
CREATE TABLE PhieuDoiHang (
    MaPD       VARCHAR(10) PRIMARY KEY,
    NgayLap    DATE NOT NULL,
    GhiChu     NVARCHAR(255),
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
    MaKH       VARCHAR(10) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaHD       VARCHAR(10) FOREIGN KEY REFERENCES HoaDon(MaHD)
);

-- =========================
-- Bảng ChiTietPhieuDoiHang
-- =========================
CREATE TABLE ChiTietPhieuDoiHang (
    MaLH       VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SP_TheoLo(MaLH),
    MaPD       VARCHAR(10) FOREIGN KEY REFERENCES PhieuDoiHang(MaPD),
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    SoLuong    INT NOT NULL,
    MaDVT      VARCHAR(10) FOREIGN KEY REFERENCES DonViTinh(MaDVT),
    LyDoDoi    NVARCHAR(255) NOT NULL,
    PRIMARY KEY (MaLH, MaPD,MaThuoc, MaDVT)
);

-- =========================
-- Bảng PhieuTraHang
-- =========================
CREATE TABLE PhieuTraHang (
    MaPT       VARCHAR(10) PRIMARY KEY,
    NgayLap    DATE NOT NULL,
    GhiChu     NVARCHAR(255),
    MaNV       VARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
    MaHD       VARCHAR(10) FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaKH       VARCHAR(10) FOREIGN KEY REFERENCES KhachHang(MaKH)
);

-- =========================
-- Bảng ChiTietPhieuTraHang
-- =========================
CREATE TABLE ChiTietPhieuTraHang (
    MaLH       VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SP_TheoLo(MaLH),
    MaPT       VARCHAR(10) NOT NULL FOREIGN KEY REFERENCES PhieuTraHang(MaPT),
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    SoLuong    INT NOT NULL,
    MaDVT      VARCHAR(10) FOREIGN KEY REFERENCES DonViTinh(MaDVT),
    DonGia     FLOAT NOT NULL,
    GiamGia    FLOAT NOT NULL,
    LyDoTra    NVARCHAR(20) NOT NULL,
    PRIMARY KEY (MaLH, MaPT,MaThuoc, MaDVT)
);


-- =========================
-- Bảng HoatChat
-- =========================
CREATE TABLE HoatChat (
    MaHoatChat VARCHAR(10) PRIMARY KEY,
    TenHoatChat NVARCHAR(50) NOT NULL
);

-- =========================
-- Bảng ChiTietHoatChat
-- =========================
CREATE TABLE ChiTietHoatChat (
    MaHoatChat VARCHAR(10) FOREIGN KEY REFERENCES HoatChat(MaHoatChat),
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    HamLuong   FLOAT NOT NULL,
    PRIMARY KEY (MaHoatChat, MaThuoc)
);

-- =========================
-- Bảng LoaiKhuyenMai
-- =========================
CREATE TABLE LoaiKhuyenMai (
    MaLoai     VARCHAR(10) PRIMARY KEY,
    TenLoai    NVARCHAR(50),
    MoTa       NVARCHAR(255)
);

-- =========================
-- Bảng KhuyenMai
-- =========================
CREATE TABLE KhuyenMai (
    MaKM       VARCHAR(10) PRIMARY KEY,
    TenKM      NVARCHAR(50) NOT NULL,
    GiaTriKM   FLOAT,
	GiaTriApDung FLOAT DEFAULT 0,
    LoaiGiaTri  VARCHAR(10),
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    MoTa       NVARCHAR(255),
	NgayTao	   DATETIME NOT NULL DEFAULT GETDATE(),
    MaLoai     VARCHAR(10) FOREIGN KEY REFERENCES LoaiKhuyenMai(MaLoai)
);

-- =========================
-- Bảng ChiTietKhuyenMai
-- =========================
CREATE TABLE ChiTietKhuyenMai (
    MaThuoc    VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    MaKM       VARCHAR(10) FOREIGN KEY REFERENCES KhuyenMai(MaKM),
    SLApDung   INT NOT NULL,
    SLToiDa    INT NOT NULL,
    PRIMARY KEY (MaThuoc, MaKM)
);


-- =========================
-- Bảng Thuoc_SP_TangKem
-- =========================
CREATE TABLE Thuoc_SP_TangKem (
    MaKM       VARCHAR(10) FOREIGN KEY REFERENCES KhuyenMai(MaKM),
    MaThuocTangKem VARCHAR(10) FOREIGN KEY REFERENCES Thuoc_SanPham(MaThuoc),
    SoLuong    INT NOT NULL,
    PRIMARY KEY (MaKM, MaThuocTangKem)
);

CREATE TABLE ThongSoUngDung(
    TenThongSo VARCHAR(10) PRIMARY KEY,
    GiaTri VARCHAR(30)
)
INSERT INTO ThongSoUngDung (TenThongSo,GiaTri) VALUES ('GiaTriThue','0.05')
INSERT INTO ThongSoUngDung (TenThongSo,GiaTri) VALUES ('NgayHetHan','30')


INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai) VALUES
('KH001', N'Nguyễn Văn An', '0905123456', 'an.nguyen@gmail.com', '1990-05-12', 1, N'Hà Nội', 1),
('KH002', N'Lê Thị Hoa', '0905789456', 'hoa.le@gmail.com', '1995-08-21', 0, N'Hải Phòng', 1),
('KH003', N'Trần Văn Bình', '0912457896', 'binh.tran@gmail.com', '1988-11-03', 1, N'TP HCM', 1),
('KH004', N'Phạm Thị Mai', '0932458976', 'mai.pham@gmail.com', '1992-02-15', 0, N'Đà Nẵng', 1),
('KH005', N'Hoàng Văn Nam', '0987654321', 'nam.hoang@gmail.com', '1985-12-20', 1, N'Cần Thơ', 1),
('KH006', N'Vũ Thị Lan', '0978456123', 'lan.vu@gmail.com', '1998-09-09', 0, N'Hải Dương', 1),
('KH007', N'Đặng Văn Hùng', '0934567890', 'hung.dang@gmail.com', '1993-07-01', 1, N'Bắc Ninh', 1),
('KH008', N'Bùi Thị Thảo', '0967451230', 'thao.bui@gmail.com', '1996-01-22', 0, N'Quảng Ninh', 1),
('KH009', N'Ngô Văn Tuấn', '0945789632', 'tuan.ngo@gmail.com', '1991-04-17', 1, N'Thái Bình', 1),
('KH010', N'Đỗ Thị Hạnh', '0923456789', 'hanh.do@gmail.com', '1994-03-05', 0, N'Ninh Bình', 1),
('KH011', N'Nguyễn Nhựt Hảo', '0825902972', 'hao.dep.dzai3105@gmail.com', '2005-05-31', 1, N'Đồng Tháp', 0);




INSERT INTO NhanVien (MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai, TaiKhoan, MatKhau, NgayVaoLam, NgayKetThuc,TrangThaiXoa, VaiTro) VALUES
('NV001', N'Đàm Thái An', '0912345678', 'thaian@gmail.com', '2005-01-01', N'Nam', N'Củ Chi', 1, 'thaian', '123', '2025-1-12', null,0,N'Quản lý'),
('NV002', N'Hoàng Phước Thành Công', '0363636363', 'thanhcong@gmail.com', '2005-02-02', N'Nữ', N'Huế', 1, 'thanhcong', '123', '2025-1-12', null,0,N'Quản lý'),
('NV003', N'Đỗ Phú Hiệp', '0181818181', 'phuhiep@gmail.com', '2003-03-03', N'Nam', N'An Giang', 1, 'phuhiep', '123', '2025-1-12', null,0,N'Nhân viên'),
('NV004', N'Nguyễn Nhựt Hảo', '0636363636', 'nhuthao@gmail.com', '2005-05-31', N'Nam', N'Đồng Tháp',1, 'nhuthao', '123', '2025-1-12', null,0,N'Nhân viên');

INSERT INTO LuongNhanVien (MaLNV, TuNgay, DenNgay, LuongCoBan, PhuCap, GhiChu, MaNV) VALUES
('LNV001', '2025-01-01', null, 8000000, 500000, N'Lương tháng 1', 'NV001'),
('LNV002', '2025-01-01', null, 7500000, 400000, N'Lương tháng 1', 'NV002'),
('LNV003', '2025-01-01', null, 9000000, 600000, N'Lương tháng 1', 'NV003'),
('LNV004', '2025-01-01', null, 7000000, 350000, N'Lương tháng 1', 'NV004');

INSERT INTO LoaiHang (MaLoaiHang, TenLH, MoTa) VALUES
('LH01', N'Thuốc Tây', N'Thuốc kê đơn, thuốc không kê đơn, thuốc điều trị bệnh lý thông thường...'),
('LH02', N'Vaccine', N'Chế phẩm sinh học giúp tạo miễn dịch, phòng ngừa các bệnh truyền nhiễm...'),
('LH03', N'Đông Y', N'Thuốc y học cổ truyền, thảo dược, cao, trà, thuốc sắc...'),
('LH04', N'Thực Phẩm Chức Năng', N'Vitamin, khoáng chất, sản phẩm tăng sức đề kháng, hỗ trợ miễn dịch...'),
('LH05', N'Dụng Cụ Y Tế', N'Nhiệt kế, máy đo huyết áp, bông băng, khẩu trang y tế...'),
('LH06', N'Mỹ Phẩm', N'Sữa rửa mặt, kem dưỡng da, dầu gội, sản phẩm chăm sóc da và tóc...');

-- 1. Tác dụng lên hệ thần kinh trung ương
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL001', N'An thần, gây ngủ', N'Thuốc an thần, hỗ trợ giấc ngủ'),
('NDL002', N'Giảm đau', N'Thuốc giảm cảm giác đau'),
('NDL003', N'Gây mê', N'Thuốc gây mê toàn thân hoặc cục bộ'),
('NDL004', N'Chống co giật', N'Thuốc phòng và điều trị động kinh'),
('NDL005', N'Chống trầm cảm, hưng trí', N'Điều trị rối loạn tâm thần');

-- 2. Tác dụng lên hệ thần kinh thực vật
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL006', N'Kích thích giao cảm (sympathomimetic)', N'Thuốc tăng hoạt động giao cảm'),
('NDL007', N'Ức chế giao cảm (sympatholytic)', N'Thuốc giảm hoạt động giao cảm'),
('NDL008', N'Kích thích phó giao cảm (parasympathomimetic)', N'Thuốc tăng hoạt động phó giao cảm'),
('NDL009', N'Ức chế phó giao cảm (parasympatholytic)', N'Thuốc giảm hoạt động phó giao cảm');

-- 3. Tác dụng lên hệ tim mạch – huyết áp
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL010', N'Thuốc chống tăng huyết áp', N'Điều trị cao huyết áp'),
('NDL011', N'Thuốc trợ tim, chống suy tim', N'Hỗ trợ chức năng tim'),
('NDL012', N'Thuốc chống rối loạn nhịp tim', N'Ổn định nhịp tim'),
('NDL013', N'Thuốc lợi tiểu', N'Tăng đào thải nước và muối');

-- 4. Tác dụng chống viêm – giảm đau – hạ sốt
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL014', N'NSAIDs', N'Thuốc kháng viêm không steroid'),
('NDL015', N'Corticoid', N'Thuốc kháng viêm steroid'),
('NDL016', N'Thuốc giảm đau gây nghiện và không gây nghiện', N'Điều trị đau mức độ nặng và nhẹ');

-- 5. Tác dụng kháng vi sinh vật, kháng ký sinh trùng
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL017', N'Kháng sinh', N'Diệt hoặc kìm khuẩn'),
('NDL018', N'Thuốc kháng virus', N'Ức chế hoặc tiêu diệt virus'),
('NDL019', N'Thuốc chống nấm', N'Điều trị nấm'),
('NDL020', N'Thuốc diệt ký sinh trùng, chống sốt rét', N'Điều trị giun sán, ký sinh trùng');

-- 6. Tác dụng kháng ung thư
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL021', N'Thuốc chống tăng sinh tế bào', N'Điều trị ung thư');

-- 7. Tác dụng điều hòa nội tiết
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL022', N'Hormone thay thế', N'Insulin, thyroxin, estrogen, testosterone...'),
('NDL023', N'Thuốc kháng hormone', N'Kháng estrogen, kháng androgen, kháng giáp...');

-- 8. Tác dụng trên hô hấp
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL024', N'Giãn phế quản', N'Mở rộng đường thở'),
('NDL025', N'Ức chế ho', N'Giảm phản xạ ho'),
('NDL026', N'Long đờm', N'Hỗ trợ tống đờm ra khỏi đường hô hấp');

-- 9. Tác dụng trên hệ tiêu hóa
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL027', N'Thuốc chống loét dạ dày – tá tràng', N'Ức chế bơm proton, kháng H2'),
('NDL028', N'Thuốc nhuận tràng, cầm tiêu chảy', N'Hỗ trợ điều hòa tiêu hóa'),
('NDL029', N'Thuốc chống nôn', N'Ngăn ngừa và điều trị buồn nôn');

-- 10. Tác dụng bổ trợ
INSERT INTO NhomDuocLy (MaNDL, TenNDL, MoTa) VALUES
('NDL030', N'Vitamin, khoáng chất', N'Bổ sung dưỡng chất'),
('NDL031', N'Thuốc tăng sức đề kháng', N'Hỗ trợ miễn dịch'),
('NDL032', N'Chế phẩm sinh học, vaccine', N'Phòng và hỗ trợ điều trị bệnh');

-- Thêm các đơn vị tính mới với kí hiệu viết tắt ngắn gọn
INSERT INTO DonViTinh (MaDVT, TenDonViTinh, KiHieu) VALUES
('DVT01', N'Viên', N'v'),
('DVT02', N'Vỉ', N'vỉ'),
('DVT03', N'Hộp', N'h'),
('DVT04', N'Chai', N'c'),
('DVT05', N'Lọ', N'lọ'),
('DVT06', N'Tuýp', N't'),
('DVT07', N'Gói', N'gói'),
('DVT08', N'Ống', N'ống'),
('DVT09', N'Thùng', N'th'),
('DVT10', N'Cái', N'cái'),
('DVT11', N'Cuộn', N'cuộn'),
('DVT12', N'Bịch', N'bịch'),
('DVT13', N'Lốc', N'lốc')

INSERT INTO HoatChat (MaHoatChat, TenHoatChat) VALUES
('HC001','Paracetamol'),
('HC002','Ibuprofen'),
('HC003','Diclofenac'),
('HC004','Aspirin'),
('HC005','Naproxen'),
('HC006','Amoxicillin'),
('HC007','Clavulanic acid'),
('HC008','Cefuroxime'),
('HC009','Cephalexin'),
('HC010','Cefixime'),
('HC011','Ciprofloxacin'),
('HC012','Levofloxacin'),
('HC013','Azithromycin'),
('HC014','Clarithromycin'),
('HC015','Doxycycline'),
('HC016','Metronidazole'),
('HC017','Omeprazole'),
('HC018','Esomeprazole'),
('HC019','Pantoprazole'),
('HC020','Ranitidine'),
('HC021','Amlodipine'),
('HC022','Losartan'),
('HC023','Valsartan'),
('HC024','Lisinopril'),
('HC025','Enalapril'),
('HC026','Atenolol'),
('HC027','Metoprolol'),
('HC028','Furosemide'),
('HC029','Spironolactone'),
('HC030','Hydrochlorothiazide'),
('HC031','Atorvastatin'),
('HC032','Simvastatin'),
('HC033','Rosuvastatin'),
('HC034','Metformin'),
('HC035','Glibenclamide'),
('HC036','Gliclazide'),
('HC037','Insulin (Regular)'),
('HC038','Insulin Glargine'),
('HC039','Prednisone'),
('HC040','Dexamethasone'),
('HC041','Betamethasone'),
('HC042','Loratadine'),
('HC043','Cetirizine'),
('HC044','Chlorpheniramine'),
('HC045','Salbutamol'),
('HC046','Budesonide'),
('HC047','Montelukast'),
('HC048','Warfarin'),
('HC049','Clopidogrel'),
('HC050','Heparin'),
('HC051','Vitamin C'),
('HC052','Vitamin D3'),
('HC053','Omega-3 Fatty Acids'),
('HC054','Calcium'),
('HC055','Collagen Type II'),
('HC056','Probiotic (Multi-strain)'),
('HC057','Multivitamins'),
('HC058','Saponin (Sâm Ngọc Linh)'),
('HC059','Ginkgo Biloba Extract'),
('HC060','Zinc'),
('HC061','Glucosamine Sulfate'),
('HC062','BCG Vaccine'),
('HC063','Polio Vaccine (OPV)'),
('HC064','Hepatitis B Surface Antigen'),
('HC065','Diphtheria Toxoid'),
('HC066','Tetanus Toxoid'),
('HC067','Pertussis Antigen'),
('HC068','Measles Virus Antigen'),
('HC069','Mumps Virus Antigen'),
('HC070','Rubella Virus Antigen');




INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email, GPKD, GhiChu, TenCongTy, MSThue)
VALUES
-- Công ty thuốc tân dược
('NCC001', N'Công ty Dược Hậu Giang', N'Cần Thơ, Việt Nam', '02923888888', 'HauGiang@dhgpharma.com.vn', 'GPKD-001', N'Chuyên cung cấp thuốc tân dược', N'DHG Pharma', '18001001'),
('NCC002', N'Công ty Vắc xin và Sinh phẩm số 1', N'Hà Nội, Việt Nam', '02438683333', 'misto@vabiotech.com.vn', 'GPKD-002', N'Nhà cung cấp vaccine', N'VABIOTECH', '01001002'),
('NCC003', N'Công ty Traphaco', N'Hà Nội, Việt Nam', '02436888333', 'Traphaco@traphaco.com.vn', 'GPKD-003', N'Thực phẩm chức năng & đông dược', N'Traphaco', '01001003'),
('NCC004', N'Công ty Domesco', N'Đồng Tháp, Việt Nam', '02773888888', 'Domesco@domesco.com.vn', 'GPKD-004', N'Chuyên cung cấp thuốc kê đơn', N'Domesco', '14001004'),
-- Công ty đông y
('NCC005', N'Công ty CP Dược phẩm OPC', N'TP.HCM, Việt Nam', '02838999999', 'opc@opcpharma.com', 'GPKD-005', N'Thuốc đông y & TPCN', N'OPC Pharma', '03001005'),
('NCC006', N'Công ty CP Y tế Mediplantex', N'Hà Nội, Việt Nam', '02438556677', 'Mediphantex@mediplantex.com.vn', 'GPKD-006', N'Dược liệu và đông y', N'Mediplantex', '01001006'),
-- Công ty vaccine
('NCC007', N'Công ty CP Dược phẩm Imexpharm', N'Đồng Tháp, Việt Nam', '02773889999', 'imexpharm@imexpharm.com', 'GPKD-007', N'Thuốc generic và TPCN', N'Imexpharm', '14001007'),
('NCC008', N'Công ty CP Dược phẩm Bidiphar', N'Bình Định, Việt Nam', '02563886666', 'Bidiphar@bidiphar.com.vn', 'GPKD-008', N'Thuốc tiêm và vaccine', N'Bidiphar', '41001008'),
-- Công ty chuyên dụng cụ y tế
('NCC009', N'Công ty CP Trang thiết bị Y tế Vinamed', N'Hà Nội, Việt Nam', '02438223344', 'Vinamed@vinamed.vn', 'GPKD-009', N'Cung cấp dụng cụ y tế', N'Vinamed', '01001009'),
-- Công ty chuyên mỹ phẩm
('NCC010', N'Công ty TNHH Mỹ phẩm Sài Gòn', N'TP.HCM, Việt Nam', '02837778888', 'saigoncosmetics@saigoncosmetics.com.vn', 'GPKD-010', N'Mỹ phẩm chăm sóc da', N'Saigon Cosmetics', '03001010');

INSERT INTO KeHang (MaKe, TenKe)
VALUES
('KE001', N'Kệ thuốc cảm sốt'),
('KE002', N'Kệ vitamin và thực phẩm chức năng'),
('KE003', N'Kệ thuốc kháng sinh'),
('KE004', N'Kệ thuốc giảm đau'),
('KE005', N'Kệ mỹ phẩm và chăm sóc da');


-- Insert các thuốc có hình ảnh
DECLARE @sql NVARCHAR(MAX);

-- Thêm từng sản phẩm
SET @sql = N'
INSERT INTO Thuoc_SanPham
(MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,
 SDK_GPNK, HangSX, NuocSX, HinhAnh, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa)
VALUES
(''TS001'', N''Paracetamol 500mg'', 500, ''mg'', N''Uống'', N''Hộp 10 vỉ x 10 viên'',
 ''VN-2345-19'', N''DHG Pharma'', N''Việt Nam'',
 (SELECT * FROM OPENROWSET(BULK N''' + @path + N'TS001.jpg'', SINGLE_BLOB) AS img),
 ''LH01'', ''NDL016'', ''KE001'',0),

(''TS002'', N''Amoxicillin 500mg'', 500, ''mg'', N''Uống'', N''Hộp 2 vỉ x 10 viên'',
 ''VN-2134-19'', N''Traphaco'', N''Việt Nam'',
 (SELECT * FROM OPENROWSET(BULK N''' + @path + N'TS002.jpg'', SINGLE_BLOB) AS img),
 ''LH01'', ''NDL017'', ''KE001'',0),

(''TS003'', N''Cefuroxime 250mg'', 250, ''mg'', N''Uống'', N''Hộp 2 vỉ x 10 viên'',
 ''VN-3241-19'', N''GSK'', N''Anh'',
 (SELECT * FROM OPENROWSET(BULK N''' + @path + N'TS003.jpg'', SINGLE_BLOB) AS img),
 ''LH01'', ''NDL017'', ''KE001'',0),

(''TS004'', N''Vitamin C 1000mg'', 1000, ''mg'', N''Uống'', N''Hộp 10 ống'',
 ''VN-1232-19'', N''Bayer'', N''Đức'',
 (SELECT * FROM OPENROWSET(BULK N''' + @path + N'TS004.jpg'', SINGLE_BLOB) AS img),
 ''LH01'', ''NDL030'', ''KE001'',0),

(''TS005'', N''Ibuprofen 400mg'', 400, ''mg'', N''Uống'', N''Hộp 1 vỉ x 10 viên'',
 ''VN-5675-19'', N''Mekophar'', N''Việt Nam'',
 (SELECT * FROM OPENROWSET(BULK N''' + @path + N'TS005.jpg'', SINGLE_BLOB) AS img),
 ''LH01'', ''NDL014'', ''KE001'',0);
';


-- Thực thi câu lệnh động
EXEC sp_executesql @sql;

INSERT INTO Thuoc_SanPham
(MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi, SDK_GPNK, HangSX, NuocSX, MaLoaiHang, MaNDL,ViTri,TrangThaiXoa)
VALUES
-- Thuốc tân dược
('TS006',N'Aspirin 81mg',81,'mg',N'Uống',N'Hộp 3 vỉ x 10 viên','VN-8678-19','Sanofi',N'Pháp','LH01','NDL014','KE001',1),
('TS007',N'Loratadine 10mg',10,'mg',N'Uống',N'Hộp 1 vỉ x 10 viên','VN-4564-21','DHG Pharma',N'Việt Nam','LH01','NDL009','KE001',0),
('TS008',N'Omeprazole 20mg',20,'mg',N'Uống',N'Hộp 2 vỉ x 7 viên','VN-2344-21','Traphaco',N'Việt Nam','LH01','NDL027','KE001',0),
('TS009',N'Metformin 500mg',500,'mg',N'Uống',N'Hộp 3 vỉ x 10 viên','VN-4569-21','Mekophar',N'Việt Nam','LH01','NDL022','KE001',0),
('TS010',N'Atorvastatin 20mg',20,'mg',N'Uống',N'Hộp 2 vỉ x 10 viên','VN-8254-21','Bayer',N'Đức','LH01','NDL010','KE001',0),
('TS011',N'Paracetamol 650mg',650,'mg',N'Uống',N'Hộp 10 vỉ x 10 viên','VN-8542-21','GSK','Anh','LH01','NDL016','KE001',0),
('TS012',N'Amoxicillin 250mg',250,'mg',N'Uống',N'Hộp 1 vỉ x 10 viên','VN-6258-21','Sanofi',N'Pháp','LH01','NDL017','KE001',0),
('TS013',N'Cefuroxime 500mg',500,'mg',N'Uống',N'Hộp 2 vỉ x 10 viên','VN-8345-21','DHG Pharma',N'Việt Nam','LH01','NDL017','KE001',0),
('TS014',N'Vitamin C 500mg',500,'mg',N'Uống',N'Hộp 10 ống','VN-8351-21','Traphaco',N'Việt Nam','LH01','NDL030','KE001',0),
('TS015',N'Ibuprofen 200mg',200,'mg',N'Uống',N'Hộp 1 vỉ x 10 viên','VN-7242-21','Mekophar',N'Việt Nam','LH01','NDL014','KE001',0),
('TS016',N'Aspirin 500mg',500,'mg',N'Uống',N'Hộp 3 vỉ x 10 viên','VN-8462-22','Bayer',N'Đức','LH01','NDL014','KE001',0),
('TS017',N'Loratadine 5mg',5,'mg',N'Uống',N'Hộp 1 vỉ x 10 viên','VN-7834-22','GSK',N'Anh','LH01','NDL009','KE001',0),
('TS018',N'Omeprazole 40mg',40,'mg',N'Uống',N'Hộp 2 vỉ x 7 viên','VN-4264-22','Sanofi',N'Pháp','LH01','NDL027','KE001',0),
('TS019',N'Metformin 850mg',850,'mg',N'Uống',N'Hộp 3 vỉ x 10 viên','VN-7834-22','DHG Pharma',N'Việt Nam','LH01','NDL022','KE001',0),
('TS020',N'Atorvastatin 40mg',40,'mg',N'Uống',N'Hộp 2 vỉ x 10 viên','VN-6354-22','Traphaco',N'Việt Nam','LH01','NDL010','KE001',1),
-- Đông y
('TS226',N'Hoạt huyết dưỡng não',250,'mg',N'Uống',N'Hộp 3 vỉ x 10 viên','VD-0001-23','Traphaco',N'Việt Nam','LH03',null,'KE001',1),
('TS227',N'Boganic',null,null,N'Uống',N'Hộp 3 vỉ x 10 viên','VD-0002-23','Traphaco',N'Việt Nam','LH03',null,'KE001',0),
('TS228',N'Ích mẫu',250,'mg',N'Uống',N'Hộp 2 vỉ x 10 viên','VD-0003-23','DHG Pharma',N'Việt Nam','LH03',null,'KE001',0),
('TS229',N'Siro ho Bảo Thanh',10,'ml',N'Uống',N'Chai 125 ml','VD-0004-23',N'Nam Dược',N'Việt Nam','LH03',null,'KE001',0),
('TS230',N'Viên ngậm Strepsils thảo dược',null,null,N'Ngậm',N'Hộp 2 vỉ x 12 viên','VD-0005-23','Reckitt',N'Anh','LH03',null,'KE001',0),
('TS231',N'Cao ích mẫu',250,'mg',N'Uống',N'Lọ 100 viên','VD-0006-23','Traphaco',N'Việt Nam','LH03',null,'KE001',0),
('TS232',N'Sâm bổ chính khí',null,null,N'Uống',N'Lọ 30 viên','VD-0007-23',N'Công ty Dược OPC',N'Việt Nam','LH03',null,'KE001',0),
('TS233',N'Kim tiền thảo',null,null,N'Uống',N'Hộp 3 vỉ x 10 viên','VD-0008-23',N'Mekophar',N'Việt Nam','LH03',null,'KE001',0),
('TS234',N'Nhất nhất thống phong',null,null,N'Uống',N'Hộp 3 vỉ x 10 viên','VD-0009-23',N'Dược Nhất Nhất',N'Việt Nam','LH03',null,'KE001',0),
('TS235',N'Hoàng liên giải độc hoàn',null,null,N'Uống',N'Lọ 60 viên','VD-0010-23',N'Trung Quốc Dược',N'Trung Quốc','LH03',null,'KE001',0),
-- Thực phẩm chức năng
('TS336',N'Vitamin D3 1000IU',1000,'IU',N'Uống',N'Lọ 100 viên','TPCN-0001-23','Nature Made',N'Mỹ','LH04',null,'KE001',0),
('TS337',N'Omega-3 Fish Oil 1000mg',1000,'mg',N'Uống',N'Lọ 120 viên','TPCN-0002-23','Blackmores',N'Úc','LH04',null,'KE001',0),
('TS338',N'Calcium + Vitamin D',500,'mg',N'Uống',N'Lọ 60 viên','TPCN-0003-23','Traphaco',N'Việt Nam','LH04',null,'KE001',0),
('TS339',N'Collagen Type II',40,'mg',N'Uống',N'Lọ 30 viên','TPCN-0004-23','Neocell',N'Mỹ','LH04',null,'KE001',0),
('TS340',N'Probiotic 10 strains',10,'tỷ CFU',N'Uống',N'Hộp 30 gói','TPCN-0005-23','Yakult',N'Nhật Bản','LH04',null,'KE001',0),
('TS341',N'Multivitamin Daily',1,'viên',N'Uống',N'Lọ 100 viên','TPCN-0006-23','Centrum',N'Mỹ','LH04',null,'KE001',0),
('TS342',N'Sâm Ngọc Linh Extract',500,'mg',N'Uống',N'Lọ 30 viên','TPCN-0007-23',N'Sâm Ngọc Linh Quảng Nam',N'Việt Nam','LH04',null,'KE001',0),
('TS343',N'Ginkgo Biloba 120mg',120,'mg',N'Uống',N'Lọ 60 viên','TPCN-0008-23','Pharmaton',N'Thụy Sĩ','LH04',null,'KE001',0),
('TS344',N'Vitamin C + Zinc',1000,'mg',N'Uống',N'Lọ 20 viên sủi','TPCN-0009-23','DHG Pharma',N'Việt Nam','LH04',null,'KE001',0),
('TS345',N'Glucosamine 1500mg',1500,'mg',N'Uống',N'Lọ 60 viên','TPCN-0010-23','Puritan''s Pride',N'Mỹ','LH04',null,'KE001',0),
-- Dụng cụ y tế
('TS446',N'Nhiệt kế điện tử',null,null,N'Đo',N'Hộp 1 cái','DM-0001-23','Omron',N'Nhật Bản','LH05',null,'KE001',0),
('TS447',N'Máy đo huyết áp bắp tay',null,null,N'Đo',N'Hộp 1 cái','DM-0002-23','Microlife',N'Thụy Sĩ','LH05',null,'KE001',0),
('TS448',N'Máy đo đường huyết',null,null,N'Đo',N'Hộp 1 cái + que thử','DM-0003-23','Accu-Chek',N'Đức','LH05',null,'KE001',0),
('TS449',N'Ống nghe y tế',null,null,N'Khám',N'Hộp 1 cái','DM-0004-23','3M Littmann',N'Mỹ','LH05',null,'KE001',0),
('TS450',N'Khẩu trang y tế 3 lớp',null,null,N'Đeo',N'Hộp 50 cái','DM-0005-23',N'Bảo Thạch',N'Việt Nam','LH05',null,'KE001',0),
('TS451',N'Găng tay y tế',null,null,N'Đeo',N'Hộp 100 cái','DM-0006-23','Top Glove','Malaysia','LH05',null,'KE001',0),
('TS452',N'Bơm tiêm dùng một lần 5ml',null,null,N'Tiêm',N'Hộp 100 cái','DM-0007-23','Vinahankook',N'Việt Nam','LH05',null,'KE001',0),
('TS453',N'Kháng khuẩn rửa tay nhanh',null,null,N'Sát khuẩn',N'Chai 500ml','DM-0008-23','Lifebuoy',N'Việt Nam','LH05',null,'KE001',0),
('TS454',N'Máy xông khí dung',null,null,N'Hít',N'Hộp 1 cái','DM-0009-23','Omron',N'Nhật Bản','LH05',null,'KE001',0),
('TS455',N'Miếng dán nhiệt',null,null,N'Dán',N'Hộp 10 miếng','DM-0010-23','Kobayashi',N'Nhật Bản','LH05',null,'KE001',0),
-- Mỹ phẩm
('TS556',N'Kem chống nắng SPF50',50,'ml',N'Bôi',N'Tuýp 50ml','MP-0001-23','Anessa',N'Nhật Bản','LH05',null,'KE001',0),
('TS557',N'Sữa rửa mặt tạo bọt',100,'ml',N'Rửa mặt',N'Tuýp 100ml','MP-0002-23','Hada Labo',N'Nhật Bản','LH05',null,'KE001',0),
('TS558',N'Nước hoa hồng cân bằng da',150,'ml',N'Bôi',N'Chai 150ml','MP-0003-23','Innisfree',N'Hàn Quốc','LH05',null,'KE001',0),
('TS559',N'Serum Vitamin C 15%',30,'ml',N'Bôi',N'Lọ 30ml','MP-0004-23','Vichy',N'Pháp','LH05',null,'KE001',0),
('TS560',N'Kem dưỡng ẩm ban đêm',50,'ml',N'Bôi',N'Hũ 50ml','MP-0005-23','Laneige',N'Hàn Quốc','LH05',null,'KE001',0),
('TS561',N'Son dưỡng môi có màu',null,null,N'Bôi',N'Thỏi 3g','MP-0006-23','Maybelline',N'Mỹ','LH05',null,'KE001',0),
('TS562',N'Dầu gội thảo dược',300,'ml',N'Gội đầu',N'Chai 300ml','MP-0007-23',N'Thái Dương',N'Việt Nam','LH05',null,'KE001',0),
('TS563',N'Kem trị mụn',20,'g',N'Bôi',N'Tuýp 20g','MP-0008-23','La Roche-Posay',N'Pháp','LH05',null,'KE001',0),
('TS564',N'Mặt nạ dưỡng da Green Tea',25,'ml',N'Đắp mặt',N'Hộp 10 miếng','MP-0009-23','The Face Shop',N'Hàn Quốc','LH05',null,'KE001',0),
('TS565',N'Nước hoa nữ Eau de Parfum',50,'ml',N'Xịt',N'Chai 50ml','MP-0010-23','Chanel',N'Pháp','LH05',null,'KE001',0);

-- CẬP NHẬT CHI TIẾT ĐƠN VỊ TÍNH
-- ========================================

-- Paracetamol 500mg (TS001) - Hộp 10 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS001','DVT01',1,800,1000,1),     -- Viên (đơn vị cơ bản)
('TS001','DVT02',10,7800,9500,0),   -- Vỉ
('TS001','DVT03',100,75000,92000,0);-- Hộp

-- Amoxicillin 500mg (TS002) - Hộp 2 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS002','DVT01',1,1200,1500,1),
('TS002','DVT02',10,11800,14500,0),
('TS002','DVT03',20,23000,28000,0);

INSERT INTO ChiTietDonViTinh VALUES
-- Viên (đơn vị cơ bản)
('TS003','DVT01', 1, 2000, 2500, 1),
('TS003','DVT02',10, 19500, 24000, 0),
('TS003','DVT03',20, 38500, 47000, 0);

-- Ibuprofen 400mg (TS005) - Hộp 1 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS005','DVT01',1,900,1200,1),
('TS005','DVT03',10,8500,11000,0);

-- Aspirin 81mg (TS006) - Hộp 3 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS006','DVT01',1,500,700,1),
('TS006','DVT02',10,4800,6800,0),
('TS006','DVT03',30,14000,19500,0);

-- Vitamin C 1000mg (TS004) - Hộp 10 ống
INSERT INTO ChiTietDonViTinh VALUES
('TS004','DVT08',1,2500,3200,1),
('TS004','DVT03',10,24000,30000,0);

-- Atorvastatin 20mg (TS010) - Hộp 2 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS010','DVT01',1,3000,3800,1),
('TS010','DVT02',10,29000,37000,0),
('TS010','DVT03',20,57000,72000,0);

-- Hoạt huyết dưỡng não (TS226) - Hộp 3 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS226','DVT01',1,900,1100,1),
('TS226','DVT02',10,8800,10800,0),
('TS226','DVT03',30,26000,32000,0);

-- Siro ho Bảo Thanh (TS229) - Chai 125ml
INSERT INTO ChiTietDonViTinh VALUES
('TS229','DVT04',1,35000,42000,1),
('TS229','DVT09',20,680000,800000,0);

-- Cao ích mẫu (TS231) - Lọ 100 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS231','DVT01',1,1000,1300,1),
('TS231','DVT05',100,95000,120000,0);

-- Nhất nhất thống phong (TS234) - Hộp 3 vỉ x 10 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS234','DVT01',1,900,1100,1),
('TS234','DVT02',10,8800,10500,0),
('TS234','DVT03',30,25500,31000,0);

-- Vitamin D3 1000IU (TS336) - Lọ 100 viên
INSERT INTO ChiTietDonViTinh VALUES
('TS336','DVT01',1,1500,2000,1),
('TS336','DVT05',100,145000,190000,0);

-- Probiotic 10 strains (TS340) - Hộp 30 gói
INSERT INTO ChiTietDonViTinh VALUES
('TS340','DVT07',1,4000,5000,1),
('TS340','DVT03',30,115000,145000,0);

-- Nhiệt kế điện tử (TS446) - Hộp 1 cái
INSERT INTO ChiTietDonViTinh VALUES
('TS446','DVT10',1,75000,99000,1),
('TS446','DVT03',10,720000,950000,0);

-- Găng tay y tế (TS451) - Hộp 100 cái
INSERT INTO ChiTietDonViTinh VALUES
('TS451','DVT10',1,350,500,1),
('TS451','DVT03',100,32000,45000,0);

-- Kem chống nắng SPF50 (TS556) - Tuýp 50ml
INSERT INTO ChiTietDonViTinh VALUES
('TS556','DVT06',1,180000,230000,1),
('TS556','DVT09',20,3400000,4400000,0);

-- Kem dưỡng ẩm ban đêm (TS560) - Hũ 50ml
INSERT INTO ChiTietDonViTinh VALUES
('TS560','DVT11',1,200000,260000,1),
('TS560','DVT09',20,3800000,4900000,0);

-- TS007 - Loratadine 10mg (Hộp 1 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS007','DVT01',1,900,1200,1),
('TS007','DVT02',10,8800,11500,0);

-- TS008 - Omeprazole 20mg (Hộp 2 vỉ x 7 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS008','DVT01',1,1800,2300,1),
('TS008','DVT02',14,24000,31000,0);

-- TS009 - Metformin 500mg (Hộp 3 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS009','DVT01',1,1300,1700,1),
('TS009','DVT02',10,12500,16500,0),
('TS009','DVT03',30,36000,47000,0);

-- TS011 - Paracetamol 650mg (Hộp 10 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS011','DVT01',1,950,1200,1),
('TS011','DVT02',10,9200,11500,0),
('TS011','DVT03',100,88000,110000,0);

-- TS012 - Amoxicillin 250mg (Hộp 1 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS012','DVT01',1,1000,1300,1),
('TS012','DVT02',10,9800,12500,0);

-- TS013 - Cefuroxime 500mg (Hộp 2 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS013','DVT01',1,2800,3500,1),
('TS013','DVT02',10,27000,34000,0),
('TS013','DVT03',20,53000,67000,0);

-- TS014 - Vitamin C 500mg (Hộp 10 ống)
INSERT INTO ChiTietDonViTinh VALUES
('TS014','DVT08',1,1800,2400,1),
('TS014','DVT03',10,17000,23000,0);

-- TS227 - Boganic (Hộp 3 vỉ x 10 viên)
INSERT INTO ChiTietDonViTinh VALUES
('TS227','DVT01',1,850,1100,1),
('TS227','DVT02',10,8300,10500,0),
('TS227','DVT03',30,24500,31500,0);

-- TS447 - Máy đo huyết áp bắp tay
INSERT INTO ChiTietDonViTinh VALUES
('TS447','DVT10',1,850000,1050000,1);

-- TS450 - Khẩu trang y tế 3 lớp (Hộp 50 cái)
INSERT INTO ChiTietDonViTinh VALUES
('TS450','DVT10',1,800,1200,1),
('TS450','DVT03',50,38000,55000,0);



INSERT INTO ChiTietHoatChat (MaHoatChat, MaThuoc, HamLuong) VALUES
-- Thuốc tây
('HC001','TS001',500),
('HC006','TS002',500),
('HC008','TS003',250),
('HC051','TS004',1000),
('HC002','TS005',400),
('HC004','TS006',81),
('HC042','TS007',10),
('HC017','TS008',20),
('HC034','TS009',500),
('HC031','TS010',20),
('HC001','TS011',650),
('HC006','TS012',250),
('HC008','TS013',500),
('HC051','TS014',500),
('HC002','TS015',200),
('HC004','TS016',500),
('HC042','TS017',5),
('HC017','TS018',40),
('HC034','TS019',850),
('HC031','TS020',40);

INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
VALUES
('PN001', '2025-09-01', 1 , N'Nhập thuốc giảm đau', 'NCC001', 'NV001'),
('PN002', '2025-09-02', 1, N'Nhập kháng sinh', 'NCC002', 'NV001'),
('PN003', '2025-09-03', 1, N'Nhập vitamin và khoáng chất', 'NCC003', 'NV001'),
('PN004', '2025-09-04', 1, N'Nhập thuốc tim mạch', 'NCC004', 'NV001'),
('PN005', '2025-09-05', 1, N'Nhập vaccine phòng bệnh', 'NCC005', 'NV001'),
('PN006', '2025-09-06', 1, N'Nhập thực phẩm chức năng', 'NCC006', 'NV001'),
('PN007', '2025-09-07', 1, N'Nhập thuốc đông y', 'NCC007', 'NV001'),
('PN008', '2025-09-08', 1, N'Nhập dụng cụ y tế', 'NCC008', 'NV001'),
('PN009', '2025-09-09', 1, N'Nhập mỹ phẩm chăm sóc da', 'NCC009', 'NV001'),
('PN010', '2025-09-10', 1, N'Nhập hỗn hợp nhiều loại sản phẩm', 'NCC010', 'NV001');

INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, MaDVT, SoLuong, GiaNhap, ChietKhau, Thue)
VALUES
-- PN001: Thuốc tây
('PN001','TS001','LH00001','DVT01',100,1200,0.05,0.08),   -- Viên
('PN001','TS002','LH00002','DVT02',80,1500,0.02,0.08),    -- Vỉ
('PN002','TS005','LH00003','DVT03',50,1800,0.00,0.08),    -- Hộp
('PN002','TS006','LH00004','DVT03',60,2000,0.01,0.08),    -- Hộp
('PN003','TS004','LH00005','DVT01',120,900,0.00,0.05),    -- Viên
('PN003','TS010','LH00006','DVT03',70,2500,0.03,0.08),    -- Hộp

-- PN006: Đông y
('PN006','TS226','LH00007','DVT07',90,7500,0.01,0.05),    -- Gói
('PN006','TS229','LH00008','DVT04',60,18000,0.00,0.05),   -- Chai
('PN007','TS231','LH00009','DVT05',100,12000,0.02,0.05),  -- Lọ
('PN007','TS234','LH00010','DVT04',80,22000,0.00,0.05),   -- Chai

-- PN008: Thực phẩm chức năng
('PN008','TS336','LH00011','DVT03',100,120000,0.02,0.05), -- Hộp
('PN008','TS340','LH00012','DVT03',80,250000,0.03,0.05),  -- Hộp

-- PN009: Dụng cụ y tế
('PN009','TS446','LH00013','DVT10',40,95000,0.02,0.08),   -- Cái
('PN009','TS451','LH00014','DVT09',200,1200,0.00,0.08),   -- Thùng

-- PN010: Mỹ phẩm
('PN010','TS556','LH00015','DVT06',70,180000,0.05,0.05),  -- Tuýp
('PN010','TS560','LH00016','DVT04',50,250000,0.03,0.05);  -- Chai

INSERT INTO Thuoc_SP_TheoLo (MaLH, MaPN, MaThuoc, SoLuongTon, NSX, HSD)
VALUES
-- LH01: thuốc tây
('LH00001','PN001','TS001',100,'2025-01-01','2027-01-01'),
('LH00002','PN001','TS002',80,'2025-02-01','2027-02-01'),
('LH00003','PN002','TS005',50,'2025-03-01','2026-03-01'),
('LH00004','PN002','TS006',60,'2025-03-15','2026-03-15'),
('LH00005','PN003','TS004',120,'2025-04-01','2026-04-01'),
('LH00006','PN003','TS010',70,'2025-05-01','2027-05-01'),

-- LH02: đông y
('LH00007','PN006','TS226',90,'2025-01-10','2028-01-10'),
('LH00008','PN006','TS229',60,'2025-02-20','2028-02-20'),
('LH00009','PN007','TS231',100,'2025-03-05','2028-03-05'),
('LH00010','PN007','TS234',80,'2025-04-10','2028-04-10'),

-- LH03: thực phẩm chức năng
('LH00011','PN008','TS336',100,'2025-01-25','2026-07-25'),
('LH00012','PN008','TS340',80,'2025-02-15','2026-08-15'),

-- LH04: dụng cụ y tế
('LH00013','PN009','TS446',40,'2025-01-01','2030-01-01'),
('LH00014','PN009','TS451',200,'2025-03-01','2030-03-01'),

-- LH05: mỹ phẩm
('LH00015','PN010','TS556',70,'2025-02-10','2027-02-10'),
('LH00016','PN010','TS560',50,'2025-03-20','2027-03-20');



-- Thêm dữ liệu vào bảng LoaiKhuyenMai
INSERT INTO LoaiKhuyenMai (MaLoai, TenLoai, MoTa)
VALUES
('LKM001', N'Tặng kèm sản phẩm', N'Khi khách hàng mua sản phẩm nhất định sẽ được tặng kèm thêm sản phẩm khác'),
('LKM002', N'Giảm giá trực tiếp theo sản phẩm', N'Giảm trực tiếp một số tiền nhất định trên giá trị sản phẩm'),
('LKM003', N'Giảm giá phần trăm theo sản phẩm', N'Khách hàng được giảm theo tỷ lệ phần trăm trên giá trị sản phẩm'),
('LKM004', N'Giảm trực tiếp theo tổng hóa đơn', N'Khách hàng được giảm trực tiếp trên hóa đơn'),
('LKM005', N'Giảm phần trăm theo tổng hóa đơn', N'Khách hàng được giảm theo tỷ lệ phần trăm trên tổng hóa đơn');


INSERT INTO KhuyenMai
(MaKM, TenKM, GiaTriKM, GiaTriApDung, LoaiGiaTri, NgayBatDau, NgayKetThuc, MoTa, MaLoai)
VALUES
-- Giảm theo sản phẩm
('KM011', N'Paracetamol giảm 10%', 10, 0, '%', '2025-10-01', '2025-10-31', N'Giảm 10% cho Paracetamol 500mg', 'LKM003'),
('KM012', N'Amoxicillin giảm 500/viên', 500, 0, 'VND', '2025-10-05', '2025-10-25', N'Giảm 20.000đ khi mua Amoxicillin 500mg', 'LKM002'),
('KM013', N'Cefuroxime giảm 15%', 15, 0, '%', '2025-10-01', '2025-10-20', N'Giảm 15% cho Cefuroxime 250mg', 'LKM003'),
('KM014', N'Vitamin C tặng Ibu + Para', NULL, 0, NULL, '2025-10-01', '2025-10-31', N'Mua Vitamin C 1000mg tặng Ibuprofen 400mg và Paracetamol 500mg', 'LKM001'),
('KM015', N'Ibuprofen giảm 10k', 10000, 0, 'VND', '2025-10-10', '2025-11-10', N'Giảm 10.000đ cho Ibuprofen 400mg', 'LKM002'),
('KM016', N'Ginkgo giảm 12%', 12, 0, '%', '2025-10-01', '2025-10-31', N'Giảm 12% cho Ginkgo Biloba 120mg', 'LKM003'),
('KM017', N'C + Zinc tặng Ginkgo + Gluco', NULL, 0, NULL, '2025-10-05', '2025-11-05', N'Mua Vitamin C + Zinc tặng Ginkgo Biloba 120mg và Glucosamine 1500mg', 'LKM001'),
('KM018', N'Glucosamine giảm 50k', 50000, 0, 'VND', '2025-10-01', '2025-11-15', N'Giảm 50.000đ cho Glucosamine 1500mg', 'LKM002'),
('KM019', N'Nhiệt kế giảm 5%', 5, 0, '%', '2025-10-01', '2025-12-01', N'Giảm 5% cho Nhiệt kế điện tử', 'LKM003'),
('KM020', N'Máy đo HA giảm 100k', 100000, 0, 'VND', '2025-10-01', '2025-12-31', N'Giảm 100.000đ cho Máy đo huyết áp bắp tay', 'LKM002'),

-- Giảm trực tiếp theo tổng hóa đơn (LKM004)
('KM021', N'Hóa đơn trên 300k giảm 30k', 30000, 300000, 'VND', '2025-10-01', '2025-10-31',
 N'Khách hàng có hóa đơn từ 300.000đ trở lên sẽ được giảm trực tiếp 30.000đ', 'LKM004'),
('KM022', N'Hóa đơn trên 500k giảm 70k', 70000, 500000, 'VND', '2025-10-10', '2025-11-10',
 N'Khách hàng có hóa đơn từ 500.000đ trở lên sẽ được giảm trực tiếp 70.000đ', 'LKM004'),
('KM023', N'Hóa đơn trên 1 triệu giảm 150k', 150000, 1000000, 'VND', '2025-10-15', '2025-12-15',
 N'Giảm ngay 150.000đ khi tổng hóa đơn đạt từ 1.000.000đ', 'LKM004'),

-- Giảm phần trăm theo tổng hóa đơn (LKM005)
('KM024', N'Hóa đơn trên 200k giảm 5%', 5, 200000, '%', '2025-10-01', '2025-11-01',
 N'Khách hàng có hóa đơn từ 200.000đ trở lên được giảm 5% tổng giá trị hóa đơn', 'LKM005'),
('KM025', N'Hóa đơn trên 800k giảm 8%', 8, 800000, '%', '2025-10-05', '2025-11-30',
 N'Khách hàng có hóa đơn từ 800.000đ trở lên được giảm 8% tổng giá trị hóa đơn', 'LKM005'),
('KM026', N'Hóa đơn trên 1.5 triệu giảm 10%', 10, 1500000, '%', '2025-10-20', '2025-12-31',
 N'Khách hàng có hóa đơn từ 1.500.000đ trở lên được giảm 10% tổng giá trị hóa đơn', 'LKM005');



INSERT INTO ChiTietKhuyenMai (MaThuoc, MaKM, SLApDung, SLToiDa)
VALUES
('TS001', 'KM011', 1, 50),  -- Paracetamol giảm %
('TS002', 'KM012', 1, 50),  -- Amoxicillin giảm tiền
('TS007', 'KM012', 1, 50),  -- Amoxicillin giảm tiền
('TS015', 'KM012', 1, 50),  -- Amoxicillin giảm tiền
('TS003', 'KM013', 1, 50),  -- Cefuroxime giảm %
('TS005', 'KM013', 1, 50),  --
('TS004', 'KM014', 2, 20),  -- Vitamin C mua 2 tặng 1 (áp dụng tối đa 20 lần / hóa đơn)
('TS005', 'KM015', 1, 50),  -- Ibuprofen giảm tiền
('TS343', 'KM016', 1, 50),  -- Ginkgo giảm %
('TS344', 'KM017', 2, 15),  -- Vitamin C + Zinc mua 2 tặng 1 (áp dụng tối đa 15 lần)
('TS345', 'KM018', 1, 50),  -- Glucosamine giảm tiền
('TS446', 'KM019', 1, 30),  -- Nhiệt kế điện tử giảm %
('TS447', 'KM020', 1, 30);  -- Máy đo huyết áp giảm tiền


INSERT INTO Thuoc_SP_TangKem (MaKM, MaThuocTangKem, SoLuong)
VALUES
('KM014', 'TS005', 1),   -- Tặng 1 Ibuprofen 400mg
('KM014', 'TS001', 1),   -- Tặng 1 Paracetamol 500mg
('KM017', 'TS343', 1),   -- Tặng 1 Ginkgo Biloba 120mg
('KM017', 'TS345', 1);   -- Tặng 1 Glucosamine 1500mg


-- ====================================================
-- 1️⃣ BỔ SUNG CƠ SỞ DỮ LIỆU (NẾU CHƯA CÓ)
-- ====================================================
IF NOT EXISTS (SELECT * FROM LoaiHang WHERE MaLoaiHang = 'LH001')
    INSERT INTO LoaiHang VALUES ('LH001', N'Thuốc thông dụng', N'Dùng cho thuốc OTC');
IF NOT EXISTS (SELECT * FROM NhomDuocLy WHERE MaNDL = 'ND001')
    INSERT INTO NhomDuocLy VALUES ('ND001', N'Nhóm kháng sinh', N'Kháng khuẩn phổ biến');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE001')
    INSERT INTO KeHang VALUES ('KE001', N'Kệ A1', N'Kệ trưng bày thuốc');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE002')
    INSERT INTO KeHang VALUES ('KE002', N'Kệ A2', N'Kệ vitamin');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE003')
    INSERT INTO KeHang VALUES ('KE003', N'Kệ A3', N'Kệ thuốc dị ứng');

IF NOT EXISTS (SELECT * FROM NhaCungCap WHERE MaNCC = 'NCC001')
    INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email)
    VALUES ('NCC001', N'Công ty Dược TW1', N'Hà Nội', '0248888888', 'ncc001@gmail.com');
IF NOT EXISTS (SELECT * FROM NhaCungCap WHERE MaNCC = 'NCC002')
    INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email)
    VALUES ('NCC002', N'DHG Pharma', N'Cần Thơ', '0292388888', 'ncc002@gmail.com');

-- ====================================================
-- 2️⃣ DỮ LIỆU MẪU TEST THỐNG KÊ (MÃ BẮT ĐẦU TỪ 041)
-- ====================================================

-- KHÁCH HÀNG
INSERT INTO KhachHang VALUES
('KH041', N'Phan Thị Hạnh', '0914000001', 'hanh41@gmail.com', '1995-01-10', 0, N'Hà Nội', 1),
('KH042', N'Lê Văn Khải', '0914000002', 'khai42@gmail.com', '1990-05-15', 1, N'HCM', 1),
('KH043', N'Đỗ Minh Đức', '0914000003', 'duc43@gmail.com', '1988-03-05', 1, N'Đà Nẵng', 1);

-- NHÂN VIÊN
INSERT INTO NhanVien VALUES
('NV041', N'Trần Ngọc Huyền', '0904000001', 'huyen41@qlnt.vn', '1994-02-14', 0, N'Hà Nội', N'Bán hàng', 1, 'huyen41', '123', '2024-01-01', NULL, 0),
('NV042', N'Phạm Quang Minh', '0904000002', 'minh42@qlnt.vn', '1993-08-12', 1, N'HCM', N'Bán hàng', 1, 'minh42', '123', '2024-02-01', NULL, 0),
('NV043', N'Nguyễn Tấn Lộc', '0904000003', 'loc43@qlnt.vn', '1997-10-02', 1, N'Đà Nẵng', N'Bán hàng', 1, 'loc43', '123', '2024-03-01', NULL, 0);



-- PHIẾU NHẬP
INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV) VALUES
('PN041', '2025-01-10', 1, N'Nhập hàng đầu năm', 'NCC001', 'NV041'),
('PN042', '2025-03-15', 1, N'Nhập tháng 3', 'NCC002', 'NV042'),
('PN043', '2025-05-12', 1, N'Nhập tháng 5', 'NCC001', 'NV043'),
('PN044', '2025-07-05', 1, N'Nhập tháng 7', 'NCC001', 'NV041'),
('PN045', '2025-09-18', 1, N'Nhập tháng 9', 'NCC002', 'NV043'),
('PN046', '2025-10-01', 1, N'Nhập đầu tháng 10', 'NCC001', 'NV042'),
('PN047', '2025-10-10', 1, N'Nhập giữa tháng 10', 'NCC002', 'NV043'),
('PN048', '2025-10-20', 1, N'Nhập tuần này', 'NCC001', 'NV041'),
('PN049', '2025-10-23', 1, N'Nhập hôm qua', 'NCC002', 'NV042'),
('PN050', '2025-10-25', 1, N'Nhập hôm nay', 'NCC001', 'NV043'),
('PN051', '2025-11-05', 1, N'Nhập tháng 11', 'NCC001', 'NV041'),
('PN052', '2025-12-10', 1, N'Nhập tháng 12', 'NCC002', 'NV042'),
('PN053', '2025-12-31', 1, N'Nhập cuối năm', 'NCC001', 'NV043');

-- CHI TIẾT PHIẾU NHẬP + LÔ HÀNG
INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, GiaNhap, ChietKhau, Thue) VALUES
('PN041','TS001','LH00041',500,1200,0,5),
('PN042','TS002','LH00042',600,1300,0,5),
('PN043','TS003','LH00043',400,1100,0,5),
('PN044','TS005','LH00044',700,1400,0,5),
('PN045','TS004','LH00045',800,1000,0,5),
('PN046','TS001','LH00046',500,1200,0,5),
('PN047','TS002','LH00047',600,1300,0,5),
('PN048','TS003','LH00048',400,1100,0,5),
('PN049','TS005','LH00049',700,1400,0,5),
('PN050','TS004','LH00050',800,1000,0,5),
('PN051','TS001','LH00051',500,1200,0,5),
('PN052','TS002','LH00052',600,1300,0,5),
('PN053','TS003','LH00053',400,1100,0,5),
('PN043','TS003','LH00053',400,2000,0,5),
('PN044','TS007','LH00054',300,900,0,5),
('PN045','TS008','LH00055',280,1800,0,5),
('PN046','TS009','LH00056',500,1300,0,5),
('PN047','TS011','LH00057',800,950,0,5),
('PN048','TS012','LH00058',400,1000,0,5),
('PN049','TS013','LH00059',350,2800,0,5),
('PN050','TS014','LH00060',600,1800,0,5),
('PN041','TS227','LH00061',450,850,0,5),
('PN042','TS447','LH00062',50,850000,0,5),
('PN043','TS450','LH00063',1000,800,0,5);

INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, NSX, HSD) VALUES
('PN041','TS001','LH00041',500,'2025-01-01','2027-01-01'),
('PN042','TS002','LH00042',600,'2025-03-01','2027-03-01'),
('PN043','TS003','LH00043',400,'2025-05-01','2027-05-01'),
('PN044','TS005','LH00044',700,'2025-07-01','2027-07-01'),
('PN045','TS004','LH00045',800,'2025-09-01','2027-09-01'),
('PN046','TS001','LH00046',500,'2025-10-01','2027-10-01'),
('PN047','TS002','LH00047',600,'2025-10-10','2027-10-10'),
('PN048','TS003','LH00048',400,'2025-10-20','2027-10-20'),
('PN049','TS005','LH00049',700,'2025-10-23','2027-10-23'),
('PN050','TS004','LH00050',800,'2025-10-25','2027-10-25'),
('PN051','TS001','LH00051',500,'2025-11-05','2027-11-05'),
('PN052','TS002','LH00052',600,'2025-12-10','2027-12-10'),
('PN043','TS003','LH00053',400,'2025-12-31','2027-12-31'),
('PN044','TS007','LH00054',300,'2025-02-01','2027-02-01'),
('PN045','TS008','LH00055',280,'2025-02-15','2027-02-15'),
('PN046','TS009','LH00056',500,'2025-03-01','2027-03-01'),
('PN047','TS011','LH00057',800,'2025-03-20','2027-03-20'),
('PN048','TS012','LH00058',400,'2025-04-05','2027-04-05'),
('PN049','TS013','LH00059',350,'2025-04-18','2027-04-18'),
('PN050','TS014','LH00060',600,'2025-05-01','2027-05-01'),
('PN041','TS227','LH00061',450,'2025-05-10','2027-05-10'),
('PN042','TS447','LH00062',50,'2025-06-01','2029-06-01'),
('PN043','TS450','LH00063',1000,'2025-06-15','2028-06-15');

-- HÓA ĐƠN

INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, MaKH, MaNV)
VALUES
    ('HD111', '2025-09-16 09:00:00', N'Hoàn tất', 'KH007', 'NV002'),
    ('HD112', '2025-09-16 11:30:00', N'Hoàn tất', NULL, 'NV001'),
    ('HD213', '2025-09-16 15:45:00', N'Hoàn tất', 'KH008', 'NV003'),
    ('HD114', '2025-09-17 08:20:00', N'Hoàn tất', 'KH009', 'NV002'),
    ('HD315', '2025-09-17 10:15:00', N'Hoàn tất', NULL, 'NV001'),
    ('HD416', '2025-09-18 14:00:00', N'Hoàn tất', 'KH010', 'NV003'),
    ('HD117', '2025-09-18 17:30:00', N'Hoàn tất', 'KH011', 'NV002'),
    ('HD218', '2025-09-19 09:40:00', N'Hoàn tất', 'KH001', 'NV001'),
    ('HD319', '2025-09-19 13:00:00', N'Hoàn tất', NULL, 'NV003'),
    ('HD620', '2025-09-20 16:20:00', N'Hoàn tất', 'KH002', 'NV002');

-- CHI TIẾT HÓA ĐƠN
INSERT INTO ChiTietHoaDon (MaHD, MaLH, MaDVT, SoLuong, DonGia, GiamGia)
VALUES
-- HD011: Paracetamol (5 viên) + Vitamin C (1 chai)
('HD111', 'LH00001', 'DVT01', 5, 1500, 100),
('HD111', 'LH00005', 'DVT04', 1, 1200, 0),

-- HD012: Amoxicillin (2 vỉ) + Aspirin (1 hộp)
('HD112', 'LH00002', 'DVT02', 2, 1900, 0),
('HD112', 'LH00004', 'DVT03', 1, 3000, 0),

-- HD013: Kem chống nắng (1 tuýp) + Găng tay y tế (5 hộp)
('HD213', 'LH00015', 'DVT06', 1, 250000, 20000),
('HD213', 'LH00014', 'DVT03', 5, 1800, 0),

-- HD014: Cao ích mẫu (2 hộp) + Ibuprofen (1 hộp)
('HD114', 'LH00009', 'DVT03', 2, 15000, 0),
('HD114', 'LH00003', 'DVT03', 1, 2500, 0),

-- HD015: Hoạt huyết dưỡng não (1 hộp)
('HD315', 'LH00007', 'DVT03', 1, 9500, 0),

-- HD016: Probiotic 10 strains (1 hộp) + Vitamin D3 (1 hộp)
('HD416', 'LH00012', 'DVT03', 1, 320000, 0),
('HD416', 'LH00011', 'DVT03', 1, 150000, 5000),

-- HD017: Siro ho Bảo Thanh (3 chai)
('HD117', 'LH00008', 'DVT04', 3, 25000, 0),

-- HD018: Kem dưỡng ẩm (2 hộp) + Nhiệt kế điện tử (1 cái)
('HD218', 'LH00016', 'DVT03', 2, 350000, 20000),
('HD218', 'LH00013', 'DVT10', 1, 130000, 0),

-- HD019: Atorvastatin (2 hộp) + Amoxicillin (1 vỉ)
('HD319', 'LH00006', 'DVT03', 2, 3500, 0),
('HD319', 'LH00002', 'DVT02', 1, 1900, 0),

-- HD020: Vitamin C (2 chai) + Paracetamol (10 viên)
('HD620', 'LH00005', 'DVT04', 2, 1200, 0),
('HD620', 'LH00001', 'DVT01', 10, 1500, 150);


--------Hóa đơn
INSERT INTO HoaDon (MaHD, NgayLap, TrangThai, MaKH, MaNV)
VALUES
('HD001', '2025-09-11 08:30:00', N'Hoàn tất', 'KH001', 'NV001'),
('HD002', '2025-09-11 09:15:00', N'Hoàn tất', 'KH002', 'NV002'),
('HD003', '2025-09-11 10:45:00', N'Hoàn tất', NULL, 'NV003'),
('HD004', '2025-09-12 14:00:00', N'Hoàn tất', 'KH003', 'NV001'),
('HD005', '2025-09-12 16:30:00', N'Hoàn tất', NULL, 'NV002'),
('HD006', '2025-09-13 11:00:00', N'Hoàn tất', 'KH004', 'NV003'),
('HD007', '2025-09-13 15:20:00', N'Hoàn tất', NULL, 'NV001'),
('HD008', '2025-09-14 09:40:00', N'Hoàn tất', 'KH005', 'NV002'),
('HD009', '2025-09-14 13:00:00', N'Hoàn tất', 'KH006', 'NV003'),
('HD010', '2025-09-15 17:00:00', N'Hoàn tất', NULL, 'NV001'),

('HD011','2025-10-01 08:20:00',N'Hoàn tất','KH001','NV001'),
('HD012','2025-10-01 09:10:00',N'Hoàn tất',NULL,'NV002'),
('HD013','2025-10-01 10:45:00',N'Hoàn tất','KH002','NV003'),
('HD014','2025-10-01 14:00:00',N'Hoàn tất','KH003','NV001'),
('HD015','2025-10-01 16:30:00',N'Hoàn tất',NULL,'NV002'),

('HD016','2025-10-02 08:40:00',N'Hoàn tất','KH004','NV003'),
('HD017','2025-10-02 10:20:00',N'Hoàn tất',NULL,'NV001'),
('HD018','2025-10-02 14:15:00',N'Hoàn tất','KH005','NV002'),
('HD019','2025-10-02 16:50:00',N'Hoàn tất',NULL,'NV003'),
('HD020','2025-10-02 09:00:00',N'Hoàn tất','KH006','NV001'),

('HD021','2025-10-03 10:30:00',N'Hoàn tất',NULL,'NV002'),
('HD022','2025-10-03 14:10:00',N'Hoàn tất','KH001','NV003'),
('HD023','2025-10-03 16:40:00',N'Hoàn tất','KH002','NV001'),
('HD024','2025-10-04 08:50:00',N'Hoàn tất',NULL,'NV002'),
('HD025','2025-10-04 10:25:00',N'Hoàn tất','KH003','NV003'),

('HD026','2025-10-04 14:00:00',N'Hoàn tất',NULL,'NV001'),
('HD027','2025-10-05 16:15:00',N'Hoàn tất','KH004','NV002'),
('HD028','2025-10-06 09:10:00',N'Hoàn tất',NULL,'NV003'),
('HD029','2025-10-06 11:40:00',N'Hoàn tất','KH005','NV001'),
('HD030','2025-10-06 15:30:00',N'Hoàn tất',NULL,'NV002'),
('HD031','2025-11-01 08:15:00',N'Hoàn tất','KH001','NV001'),
('HD032','2025-11-01 10:30:00',N'Hoàn tất',NULL,'NV002'),
('HD033','2025-11-02 09:45:00',N'Hoàn tất','KH002','NV003'),
('HD034','2025-11-03 14:10:00',N'Hoàn tất','KH003','NV001'),
('HD035','2025-11-04 16:20:00',N'Hoàn tất',NULL,'NV002'),

('HD036','2025-11-05 08:40:00',N'Hoàn tất','KH004','NV003'),
('HD037','2025-11-06 10:00:00',N'Hoàn tất',NULL,'NV001'),
('HD038','2025-11-07 14:30:00',N'Hoàn tất','KH005','NV002'),
('HD039','2025-11-08 16:45:00',N'Hoàn tất',NULL,'NV003'),
('HD040','2025-11-09 09:20:00',N'Hoàn tất','KH006','NV001'),
('HD041','2025-12-01 08:10:00',N'Hoàn tất','KH001','NV002'),
('HD042','2025-12-02 10:25:00',N'Hoàn tất',NULL,'NV003'),
('HD043','2025-12-03 09:50:00',N'Hoàn tất','KH002','NV001'),
('HD044','2025-12-04 14:05:00',N'Hoàn tất','KH003','NV002'),
('HD045','2025-12-05 16:35:00',N'Hoàn tất',NULL,'NV003'),

('HD046','2025-12-06 08:55:00',N'Hoàn tất','KH004','NV001'),
('HD047','2025-12-07 10:40:00',N'Hoàn tất',NULL,'NV002'),
('HD048','2025-12-08 14:25:00',N'Hoàn tất','KH005','NV003'),
('HD049','2025-12-09 16:10:00',N'Hoàn tất',NULL,'NV001'),
('HD050','2025-12-10 09:30:00',N'Hoàn tất','KH006','NV002');

INSERT INTO ChiTietHoaDon (MaHD, MaLH, MaDVT, SoLuong, DonGia, GiamGia)
VALUES
-- HD001
('HD001', 'LH00001', 'DVT01', 10, 1500, 150),   -- Paracetamol - Viên
('HD001', 'LH00002', 'DVT02', 10, 1900, 0),     -- Amoxicillin - Vỉ

-- HD002: Vitamin C 1000mg (3 chai)
('HD002', 'LH00005', 'DVT04', 3, 1200, 0),      -- Chai

-- HD003: Ibuprofen 400mg (2 hộp)
('HD003', 'LH00003', 'DVT03', 2, 2500, 0),      -- Hộp

-- HD004: Hoạt huyết dưỡng não (2 hộp) và Cao ích mẫu (1 hộp)
('HD004', 'LH00007', 'DVT03', 2, 9500, 0),      -- Hộp
('HD004', 'LH00009', 'DVT03', 1, 15000, 0),     -- Hộp

-- HD005: Vitamin D3 1000IU (1 hộp)
('HD005', 'LH00011', 'DVT03', 1, 150000, 0),    -- Hộp

-- HD006: Nhiệt kế điện tử (1 cái) và Găng tay y tế (10 hộp)
('HD006', 'LH00013', 'DVT10', 1, 130000, 0),    -- Cái
('HD006', 'LH00014', 'DVT03', 10, 1800, 0),     -- Hộp

-- HD007: Aspirin 81mg (2 hộp)
('HD007', 'LH00004', 'DVT03', 2, 3000, 0),      -- Hộp

-- HD008: Kem chống nắng (1 tuýp) và Kem dưỡng ẩm (1 hộp)
('HD008', 'LH00015', 'DVT06', 1, 250000, 0),    -- Tuýp
('HD008', 'LH00016', 'DVT03', 1, 350000, 0),    -- Hộp

-- HD009: Siro ho Bảo Thanh (2 chai) và Probiotic 10 strains (1 hộp)
('HD009', 'LH00008', 'DVT04', 2, 25000, 0),     -- Chai
('HD009', 'LH00012', 'DVT03', 1, 320000, 0),    -- Hộp

-- HD010: Atorvastatin 20mg (4 hộp)
('HD010', 'LH00006', 'DVT03', 4, 3500, 0),

('HD011','LH00041','DVT01',20,1000,0),     -- TS001 Paracetamol
('HD011','LH00042','DVT02',1,14500,0),     -- TS002 Amoxicillin

-- HD012
('HD012','LH00053','DVT01',10,2500,200),   -- TS003 Cefuroxime 250
('HD012','LH00045','DVT03',1,30000,0),     -- TS004 Vitamin C 1000mg

-- HD013
('HD013','LH00054','DVT01',10,1200,0),     -- TS007 Loratadine
('HD013','LH00060','DVT08',5,2400,0),      -- TS014 Vitamin C 500mg

-- HD014
('HD014','LH00056','DVT01',30,1700,300),   -- TS009 Metformin
('HD014','LH00057','DVT02',2,11500,0),     -- TS011 Paracetamol 650

-- HD015
('HD015','LH00058','DVT02',1,12500,0),     -- TS012 Amoxicillin 250
('HD015','LH00061','DVT01',20,1100,0),     -- TS227 Boganic

-- HD016
('HD016','LH00059','DVT01',10,3500,0),     -- TS013 Cefuroxime 500
('HD016','LH00041','DVT02',1,9500,0),      -- TS001 Paracetamol

-- HD017
('HD017','LH00062','DVT10',1,1050000,50000), -- TS447 Máy đo huyết áp

-- HD018
('HD018','LH00063','DVT10',100,500,0),     -- TS450 Khẩu trang
('HD018','LH00054','DVT02',1,11500,0),     -- TS007 Loratadine

-- HD019
('HD019','LH00045','DVT08',3,3200,0),      -- TS004 Vitamin C 1000mg
('HD019','LH00056','DVT02',1,16500,0),     -- TS009 Metformin

-- HD020
('HD020','LH00041','DVT01',15,1000,0),
('HD020','LH00060','DVT03',1,23000,0),

-- HD021
('HD021','LH00057','DVT01',20,1200,0),
('HD021','LH00061','DVT02',1,10500,0),

-- HD022
('HD022','LH00058','DVT01',10,1300,0),
('HD022','LH00053','DVT02',1,24000,0),

-- HD023
('HD023','LH00059','DVT03',1,67000,2000),

-- HD024
('HD024','LH00063','DVT03',1,55000,0),

-- HD025
('HD025','LH00045','DVT08',2,3200,0),
('HD025','LH00054','DVT01',10,1200,0),

-- HD026
('HD026','LH00062','DVT10',1,1050000,0),

-- HD027
('HD027','LH00056','DVT01',20,1700,0),
('HD027','LH00041','DVT01',10,1000,0),

-- HD028
('HD028','LH00060','DVT08',10,2400,500),

-- HD029
('HD029','LH00061','DVT03',1,31500,0),

-- HD030
('HD030','LH00063','DVT10',200,500,0),
-- HD031
('HD031','LH00041','DVT01',10,1500,0),
('HD031','LH00042','DVT02',2,19000,1000),

-- HD032
('HD032','LH00043','DVT01',20,2500,500),
('HD032','LH00044','DVT02',1,24000,0),

-- HD033
('HD033','LH00045','DVT03',1,47000,2000),
('HD033','LH00046','DVT01',15,1500,0),

-- HD034
('HD034','LH00047','DVT02',2,19000,0),
('HD034','LH00048','DVT01',10,2500,0),

-- HD035
('HD035','LH00049','DVT02',1,24000,500),
('HD035','LH00050','DVT03',1,28000,0),

-- HD036
('HD036','LH00051','DVT01',20,1500,0),
('HD036','LH00052','DVT02',1,19000,0),

-- HD037
('HD037','LH00053','DVT01',30,2500,1000),
('HD037','LH00054','DVT02',1,26000,0),

-- HD038
('HD038','LH00055','DVT03',1,30000,0),
('HD038','LH00056','DVT01',20,1800,0),

-- HD039
('HD039','LH00057','DVT02',2,22000,1000),
('HD039','LH00058','DVT01',10,2500,0),

-- HD040
('HD040','LH00059','DVT01',15,2000,0),
('HD040','LH00060','DVT02',1,21000,0),
-- HD041
('HD041','LH00061','DVT01',20,1800,0),
('HD041','LH00042','DVT02',1,19000,0),

-- HD042
('HD042','LH00043','DVT01',10,2500,0),
('HD042','LH00044','DVT02',2,24000,1000),

-- HD043
('HD043','LH00045','DVT03',1,47000,0),
('HD043','LH00046','DVT01',15,1500,0),

-- HD044
('HD044','LH00047','DVT02',1,19000,0),
('HD044','LH00048','DVT01',20,2500,0),

-- HD045
('HD045','LH00049','DVT02',2,24000,1000),
('HD045','LH00050','DVT03',1,28000,0),

-- HD046
('HD046','LH00051','DVT01',25,1500,0),
('HD046','LH00052','DVT02',1,19000,0),

-- HD047
('HD047','LH00053','DVT01',20,2500,0),
('HD047','LH00054','DVT02',1,26000,0),

-- HD048
('HD048','LH00055','DVT03',1,30000,0),
('HD048','LH00056','DVT01',30,1800,0),

-- HD049
('HD049','LH00057','DVT02',2,22000,1000),
('HD049','LH00058','DVT01',10,2500,0),

-- HD050
('HD050','LH00059','DVT01',20,2000,0),
('HD050','LH00060','DVT02',1,21000,0);




-- Dữ liệu mẫu cho PhieuDatHang
INSERT INTO PhieuDatHang (MaPDat, NgayLap, SoTienCoc, GhiChu, MaKH, MaNV, TrangThai)
VALUES
('PDH001', '2025-10-01', 50000, N'Khách đặt hàng mới', 'KH001', 'NV001', 0),
('PDH002', '2025-10-02', 100000, N'Đặt hàng lại lô thuốc cũ', 'KH002', 'NV002', 0),
('PDH003', '2025-10-03', 0, N'Khách đặt hàng gấp', 'KH003', 'NV003', 0);
GO

-- Dữ liệu mẫu cho ChiTietPhieuDatHang
INSERT INTO ChiTietPhieuDatHang (MaPDat, MaThuoc, SoLuong, DonGia, GiamGia, MaDVT, TrangThai)
VALUES
('PDH001', 'TS001', 5, 12000, 0.05, 'DVT01', 0),  -- Paracetamol - Viên
('PDH001', 'TS002', 10, 8000, 0,    'DVT02', 0),  -- Amoxicillin - Vỉ
('PDH002', 'TS003', 3, 15000, 0.1,  'DVT03', 1);  -- Ibuprofen - Hộp


-- Dữ liệu mẫu cho PhieuDoiHang (Không thay đổi)
INSERT INTO PhieuDoiHang (MaPD, NgayLap, GhiChu, MaNV, MaKH, MaHD)
VALUES
('PD001', '2025-10-15',  N'Đổi 1 hộp Ibuprofen cùng loại', 'NV002', NULL, 'HD003'),
('PD002', '2025-10-16',  N'Đổi 5 viên Paracetamol', 'NV001', 'KH001', 'HD001'),
('PD003', '2025-10-17',  N'Đổi 1 hộp Vitamin D3', 'NV003', NULL, 'HD005')


INSERT INTO ChiTietPhieuDoiHang (MaLH, MaPD, MaThuoc, MaDVT, SoLuong, LyDoDoi)
VALUES
('LH00003', 'PD001', 'TS005', 'DVT03',  1, N'Hộp bị móp'),
('LH00001', 'PD002', 'TS001', 'DVT01',  5, N'Viên cũ bị gãy'),
('LH00011', 'PD003', 'TS336', 'DVT03',  1, N'Hộp bị ướt')

-- Dữ liệu mẫu cho PhieuTraHang (Sửa định dạng ngày tháng)
INSERT INTO PhieuTraHang (MaPT, NgayLap, GhiChu, MaNV, MaHD, MaKH)
VALUES
('PT001', '2025-09-13', N'Trả lại Hoạt huyết dưỡng não và Cao ích mẫu', 'NV001', 'HD004', 'KH003'),
('PT002', '2025-09-15', N'Trả lại Kem chống nắng', 'NV002', 'HD008', 'KH005'),
('PT003', '2025-09-16', N'Trả lại Găng tay y tế', 'NV003', 'HD006', 'KH004');

INSERT INTO ChiTietPhieuTraHang (MaLH, MaPT, MaThuoc, MaDVT, SoLuong, DonGia, GiamGia, LyDoTra)
VALUES
-- PT001: Trả Hoạt huyết dưỡng não (LH00007/TS226) và Cao ích mẫu (LH00009/TS231) từ HD004
('LH00007', 'PT001', 'TS226', 'DVT03', 1, 9500, 0, N'Dư thừa'),   -- Hộp
('LH00009', 'PT001', 'TS231', 'DVT03', 1, 15000, 0, N'Dư thừa'),  -- Hộp

-- PT002: Trả Kem chống nắng (LH00015/TS556) từ HD008
('LH00015', 'PT002', 'TS556', 'DVT06', 1, 250000, 0, N'Không phù hợp'), -- Tuýp

-- PT003: Trả Găng tay y tế (LH00014/TS451) từ HD006
('LH00014', 'PT003', 'TS451', 'DVT03', 1, 1800, 0, N'Mua nhầm');   -- Hộp


-- ====================================================
-- 1️⃣ BỔ SUNG CƠ SỞ DỮ LIỆU (NẾU CHƯA CÓ)
-- ====================================================
IF NOT EXISTS (SELECT * FROM LoaiHang WHERE MaLoaiHang = 'LH001')
    INSERT INTO LoaiHang VALUES ('LH001', N'Thuốc thông dụng', N'Dùng cho thuốc OTC');
IF NOT EXISTS (SELECT * FROM NhomDuocLy WHERE MaNDL = 'ND001')
    INSERT INTO NhomDuocLy VALUES ('ND001', N'Nhóm kháng sinh', N'Kháng khuẩn phổ biến');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE001')
    INSERT INTO KeHang VALUES ('KE001', N'Kệ A1', N'Kệ trưng bày thuốc');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE002')
    INSERT INTO KeHang VALUES ('KE002', N'Kệ A2', N'Kệ vitamin');
IF NOT EXISTS (SELECT * FROM KeHang WHERE MaKe = 'KE003')
    INSERT INTO KeHang VALUES ('KE003', N'Kệ A3', N'Kệ thuốc dị ứng');

IF NOT EXISTS (SELECT * FROM NhaCungCap WHERE MaNCC = 'NCC001')
    INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email)
    VALUES ('NCC001', N'Công ty Dược TW1', N'Hà Nội', '0248888888', 'ncc001@gmail.com');
IF NOT EXISTS (SELECT * FROM NhaCungCap WHERE MaNCC = 'NCC002')
    INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, SDT, Email)
    VALUES ('NCC002', N'DHG Pharma', N'Cần Thơ', '0292388888', 'ncc002@gmail.com');


--=======================================================================================================================
--=======================================================================================================================
--TRIGGER GHI LOG THÊM XÓA SỬA TRONG DANH MỤC THUỐC---------------------------------------------------------------------------------------------------------------
GO
CREATE OR ALTER TRIGGER trg_ThuocSanPham_Audit
ON Thuoc_SanPham
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @LoaiHD NVARCHAR(50);
    DECLARE @MaThuoc VARCHAR(10);
    DECLARE @NoiDung NVARCHAR(MAX) = N'';
    DECLARE @context VARBINARY(128) = CONTEXT_INFO();
    DECLARE @MaNV VARCHAR(10) = RTRIM(REPLACE(CAST(@context AS VARCHAR(128)), CHAR(0), ''));

    IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
        SET @LoaiHD = N'Cập nhật thuốc';
    ELSE IF EXISTS (SELECT 1 FROM inserted)
        SET @LoaiHD = N'Thêm thuốc';
    ELSE
        SET @LoaiHD = N'Xóa thuốc';

    SELECT TOP 1 @MaThuoc = COALESCE(i.MaThuoc, d.MaThuoc)
    FROM inserted i FULL JOIN deleted d ON i.MaThuoc = d.MaThuoc;

    IF @LoaiHD = N'Cập nhật thuốc'
    BEGIN
        SELECT @NoiDung = CONCAT(
            N'Cập nhật thuốc [', i.MaThuoc, N']', CHAR(13) + CHAR(10),
            N'Tên thuốc: "', d.TenThuoc, N'" → "', i.TenThuoc, N'"', CHAR(13) + CHAR(10),
            N'Hàm lượng: "', d.HamLuong, N'" → "', i.HamLuong, N'"', CHAR(13) + CHAR(10),
            N'Đơn vị HL: "', d.DonViHL, N'" → "', i.DonViHL, N'"', CHAR(13) + CHAR(10),
            N'Hãng SX: "', d.HangSX, N'" → "', i.HangSX, N'"', CHAR(13) + CHAR(10),
            N'Nước SX: "', d.NuocSX, N'" → "', i.NuocSX, N'"'
        )
        FROM inserted i
        JOIN deleted d ON i.MaThuoc = d.MaThuoc;
    END
    ELSE IF @LoaiHD = N'Thêm thuốc'
    BEGIN
        SELECT @NoiDung = CONCAT(
            N'Thêm thuốc [', i.MaThuoc, N']: "', i.TenThuoc, N'"'
        )
        FROM inserted i;
    END
    ELSE
    BEGIN
        SELECT @NoiDung = CONCAT(
            N'Xóa thuốc [', d.MaThuoc, N']: "', d.TenThuoc, N'"'
        )
        FROM deleted d;
    END

    INSERT INTO HoatDong (LoaiHD, BangDL, NoiDung, MaNV)
    VALUES (@LoaiHD, 'Thuoc_SanPham', @NoiDung, @MaNV);
END;
GO


-- TRIGGER GHI LOG CHỈNH SỬA HOẠT CHẤT TRONG DANH MỤC THUỐC
GO
CREATE OR ALTER TRIGGER trg_ChiTietHoatChat_Audit
ON ChiTietHoatChat
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @MaThuoc VARCHAR(10);
    DECLARE @NoiDung NVARCHAR(MAX) = N'';
    DECLARE @LoaiHD NVARCHAR(50);
    DECLARE @context VARBINARY(128) = CONTEXT_INFO();
    DECLARE @MaNV VARCHAR(10) = RTRIM(REPLACE(CAST(@context AS VARCHAR(128)), CHAR(0), ''));

    -- Lấy mã thuốc thay đổi
    SELECT TOP 1 @MaThuoc = MaThuoc FROM inserted;
    IF @MaThuoc IS NULL
        SELECT TOP 1 @MaThuoc = MaThuoc FROM deleted;

    -- Xác định loại hành động
    IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
        SET @LoaiHD = N'Cập nhật hoạt chất';
    ELSE IF EXISTS (SELECT 1 FROM inserted)
        SET @LoaiHD = N'Thêm hoạt chất';
    ELSE
        SET @LoaiHD = N'Xóa hoạt chất';

    -- Lấy danh sách hoạt chất hiện có
    SELECT @NoiDung = STRING_AGG(CONCAT(N'- ', hc.TenHoatChat, N' (', ct.HamLuong, N')'), CHAR(13) + CHAR(10))
    FROM ChiTietHoatChat ct
    JOIN HoatChat hc ON ct.MaHoatChat = hc.MaHoatChat
    WHERE ct.MaThuoc = @MaThuoc;

    IF @NoiDung IS NULL OR @NoiDung = ''
        SET @NoiDung = N'(Không còn hoạt chất)';

    -- Tìm log gần nhất của Thuoc_SanPham
    DECLARE @ID INT;
    SELECT TOP 1 @ID = ID
    FROM HoatDong
    WHERE BangDL = 'Thuoc_SanPham'
      AND NoiDung LIKE N'%[' + @MaThuoc + N']%'
    ORDER BY ID DESC;

    IF @ID IS NOT NULL
    BEGIN
        -- ✅ Nối vào log thuốc gần nhất
        UPDATE HoatDong
        SET NoiDung = NoiDung
            + CHAR(13) + CHAR(10)
            + N'Cập nhật hoạt chất:' + CHAR(13) + CHAR(10)
            + @NoiDung
        WHERE ID = @ID;
    END
    ELSE
    BEGIN
        -- ⚠️ Nếu không có log thuốc (vd: thêm trực tiếp hoạt chất)
        INSERT INTO HoatDong (LoaiHD, BangDL, NoiDung, MaNV)
        VALUES (
            @LoaiHD,
            'Thuoc_SanPham',
            N'Cập nhật hoạt chất cho thuốc [' + @MaThuoc + N']:'
            + CHAR(13) + CHAR(10) + @NoiDung,
            @MaNV
        );
    END
END;
GO


--TRIGGER GHI LOG CHỈNH SỬA SỐ LƯỢNG TRONG CẬP NHẬT SỐ LƯỢNG---------------------------------------------------------------------------------------------------------------
GO
CREATE OR ALTER TRIGGER trg_ThuocSPTheoLo_Audit
ON Thuoc_SP_TheoLo
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @LoaiHD NVARCHAR(20);
    DECLARE @BangDL NVARCHAR(50) = N'Thuoc_SP_TheoLo';
    DECLARE @NoiDung NVARCHAR(MAX) = N'';
    DECLARE @context VARBINARY(128) = CONTEXT_INFO();
    DECLARE @MaNV VARCHAR(10) = RTRIM(REPLACE(CAST(@context AS VARCHAR(128)), CHAR(0), ''));

    IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
        SET @LoaiHD = N'Cập nhật';
    ELSE IF EXISTS (SELECT 1 FROM inserted)
        SET @LoaiHD = N'Thêm mới';
    ELSE
        SET @LoaiHD = N'Xóa';

    IF @LoaiHD = N'Thêm mới'
    BEGIN
        SELECT @NoiDung = STRING_AGG(
            CONCAT(
                N'Thêm lô: [Mã lô hàng=', MaLH,
                N', Mã thuốc=', MaThuoc,
                N', Mã phiếu nhập=', MaPN,
                N', Số lượng tồn=', SoLuongTon,
                N', NSX=', FORMAT(NSX, 'dd/MM/yyyy'),
                N', HSD=', FORMAT(HSD, 'dd/MM/yyyy'), N']'
            ), CHAR(13) + CHAR(10)
        )
        FROM inserted;
    END
    ELSE IF @LoaiHD = N'Cập nhật'
    BEGIN
        SELECT @NoiDung = STRING_AGG(
            CONCAT(
                N'Cập nhật lô [', i.MaLH, N']:',
                CHAR(13) + CHAR(10),
                N'   • Số lượng tồn: ', d.SoLuongTon, N' → ', i.SoLuongTon
            ), CHAR(13) + CHAR(10)
        )
        FROM inserted i
        JOIN deleted d ON i.MaLH = d.MaLH
        WHERE ISNULL(i.SoLuongTon, 0) <> ISNULL(d.SoLuongTon, 0);
    END
    ELSE IF @LoaiHD = N'Xóa'
    BEGIN
        SELECT @NoiDung = STRING_AGG(
            CONCAT(
                N'Xóa lô: [Mã lô hàng=', MaLH,
                N', Mã thuốc=', MaThuoc,
                N', Số lượng tồn=', SoLuongTon, N']'
            ), CHAR(13) + CHAR(10)
        )
        FROM deleted;
    END

    IF (@NoiDung IS NOT NULL AND @NoiDung <> N''
    AND EXISTS (SELECT 1 FROM NhanVien WHERE MaNV = @MaNV))
    BEGIN
        INSERT INTO HoatDong (LoaiHD, BangDL, NoiDung, MaNV)
        VALUES (@LoaiHD, @BangDL, @NoiDung, @MaNV);
    END
END;
GO



--TRIGGER GHI LOG CHỈNH SỬA GÍA BÁN TRONG CẬP NHẬT GIÁ---------------------------------------------------------------------------------------------------------------
CREATE OR ALTER TRIGGER trg_ChiTietDonViTinh_Audit
ON ChiTietDonViTinh
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @MaThuoc VARCHAR(10);
    DECLARE @NoiDung NVARCHAR(MAX) = N'';
    DECLARE @LoaiHD NVARCHAR(50);
    DECLARE @context VARBINARY(128) = CONTEXT_INFO();
    DECLARE @MaNV VARCHAR(10) = RTRIM(REPLACE(CAST(@context AS VARCHAR(128)), CHAR(0), ''));

    -- 🔹 Xác định thuốc bị thay đổi
    SELECT TOP 1 @MaThuoc = COALESCE(i.MaThuoc, d.MaThuoc)
    FROM inserted i
    FULL JOIN deleted d ON i.MaThuoc = d.MaThuoc;

    -- 🔹 Xác định loại hành động
    IF EXISTS (SELECT 1 FROM inserted) AND EXISTS (SELECT 1 FROM deleted)
        SET @LoaiHD = N'Cập nhật đơn vị tính';
    ELSE IF EXISTS (SELECT 1 FROM inserted)
        SET @LoaiHD = N'Thêm đơn vị tính';
    ELSE
        SET @LoaiHD = N'Xóa đơn vị tính';

    ----------------------------------------------------------
    -- 🧠 Tạo mô tả chi tiết thay đổi
    ----------------------------------------------------------
    ;WITH ThayDoi AS (
        SELECT
            COALESCE(i.MaDVT, d.MaDVT) AS MaDVT,
            dvt.TenDonViTinh,
            ISNULL(d.GiaBan, 0) AS GiaBanCu,
            ISNULL(i.GiaBan, 0) AS GiaBanMoi,
            ISNULL(d.HeSoQuyDoi, 0) AS HeSoCu,
            ISNULL(i.HeSoQuyDoi, 0) AS HeSoMoi,
            CASE
                WHEN d.MaDVT IS NULL THEN N'Thêm mới'
                WHEN i.MaDVT IS NULL THEN N'Xóa'
                WHEN (ISNULL(d.GiaBan,0) <> ISNULL(i.GiaBan,0)
                   OR ISNULL(d.HeSoQuyDoi,0) <> ISNULL(i.HeSoQuyDoi,0)) THEN N'Cập nhật'
                ELSE N'Không đổi'
            END AS TrangThai
        FROM inserted i
        FULL JOIN deleted d ON i.MaDVT = d.MaDVT AND i.MaThuoc = d.MaThuoc
        LEFT JOIN DonViTinh dvt ON dvt.MaDVT = COALESCE(i.MaDVT, d.MaDVT)
    )
    SELECT @NoiDung = STRING_AGG(
        CONCAT(
            CHAR(13)+CHAR(10), N' - ', TenDonViTinh, N': ',
            CASE
                WHEN TrangThai = N'Thêm mới' THEN N'Thêm mới (Giá bán: ' + FORMAT(GiaBanMoi, 'N0') + N', Hệ số: ' + CAST(HeSoMoi AS NVARCHAR(10)) + N')'
                WHEN TrangThai = N'Xóa' THEN N'Xóa (Giá bán cũ: ' + FORMAT(GiaBanCu, 'N0') + N', Hệ số: ' + CAST(HeSoCu AS NVARCHAR(10)) + N')'
                WHEN TrangThai = N'Cập nhật' THEN
                    N'Cập nhật: Giá bán ' + FORMAT(GiaBanCu, 'N0') + N' → ' + FORMAT(GiaBanMoi, 'N0') +
                    N'; Hệ số ' + CAST(HeSoCu AS NVARCHAR(10)) + N' → ' + CAST(HeSoMoi AS NVARCHAR(10))
                ELSE N'Không thay đổi'
            END
        ), N''
    )
    FROM ThayDoi;

    IF @NoiDung IS NULL OR @NoiDung = ''
        SET @NoiDung = N'(Không còn đơn vị tính)';

----------------------------------------------------------
-- 🧾 Ghi vào bảng HoatDong (chỉ khi MaNV tồn tại)
----------------------------------------------------------
IF EXISTS (SELECT 1 FROM NhanVien WHERE MaNV = @MaNV)
BEGIN
    DECLARE @ID INT;
    SELECT TOP 1 @ID = ID
    FROM HoatDong
    WHERE BangDL = 'Thuoc_SanPham'
      AND NoiDung LIKE '%MaThuoc=' + @MaThuoc + '%'
    ORDER BY ID DESC;

    IF @ID IS NOT NULL
        UPDATE HoatDong
        SET NoiDung = NoiDung + CHAR(13)+CHAR(10) +
                      CASE
                          WHEN @LoaiHD = N'Cập nhật đơn vị tính' THEN N'Cập nhật đơn vị tính:' + @NoiDung
                          WHEN @LoaiHD = N'Thêm đơn vị tính' THEN N'Thêm đơn vị tính:' + @NoiDung
                          WHEN @LoaiHD = N'Xóa đơn vị tính' THEN N'Xóa đơn vị tính:' + @NoiDung
                          ELSE N'Hoạt động không xác định'
                      END
        WHERE ID = @ID;
    ELSE
        INSERT INTO HoatDong (LoaiHD, BangDL, NoiDung, MaNV)
        VALUES (
            @LoaiHD,
            'Thuoc_SanPham',
            N'[MaThuoc=' + @MaThuoc + N'] ' +
            CASE
                WHEN @LoaiHD = N'Cập nhật đơn vị tính' THEN N'Cập nhật đơn vị tính:' + @NoiDung
                WHEN @LoaiHD = N'Thêm đơn vị tính' THEN N'Thêm đơn vị tính:' + @NoiDung
                WHEN @LoaiHD = N'Xóa đơn vị tính' THEN N'Xóa đơn vị tính:' + @NoiDung
                ELSE N'Hoạt động không xác định'
            END,
            @MaNV
        );
    END
END
GO




CREATE PROCEDURE sp_InsertNhanVien
    @HoTen NVARCHAR(50),
    @SDT VARCHAR(15),
    @Email VARCHAR(100),
    @NamSinh DATE,
	@GioiTinh BIT,
    @DiaChi NVARCHAR(100),
	@TrangThai BIT,
    @NgayVaoLam DATE,
    @MaTK VARCHAR(30),
	@MatKhau VARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @NewMaNV VARCHAR(10);
    DECLARE @MaxMaNV VARCHAR(10);
    DECLARE @NumPart INT;

    -- Lấy mã cao nhất hiện có (ví dụ NV012)
    SELECT @MaxMaNV = MAX(MaNV)
    FROM NhanVien;

    IF @MaxMaNV IS NULL
        SET @NewMaNV = 'NV001';
    ELSE
    BEGIN
        -- Cắt phần số, +1 và định dạng lại
        SET @NumPart = CAST(SUBSTRING(@MaxMaNV, 3, LEN(@MaxMaNV)) AS INT) + 1;
        SET @NewMaNV = 'NV' + RIGHT('000' + CAST(@NumPart AS VARCHAR(3)), 3);
    END

    -- Thêm nhân viên mới
    INSERT INTO NhanVien(MaNV, TenNV, SDT, Email, NgaySinh, GioiTinh, DiaChi, TrangThai, TaiKhoan, MatKhau, NgayVaoLam, NgayKetThuc,TrangThaiXoa,VaiTro)
    VALUES(@NewMaNV, @HoTen, @SDT, @Email, @NamSinh,@GioiTinh, @DiaChi, @TrangThai,@MaTK,@MatKhau,@NgayVaoLam,null,0, N'Nhân viên');

    -- Xuất mã nhân viên mới
    SELECT @NewMaNV AS MaNhanVienMoi;
END;


-----------Proc thống kê


PRINT N'=== Bắt đầu tạo 10 Stored Procedure Thống kê ===';
GO

-- ==========================================================
-- Phần 1: THỐNG KÊ DOANH THU (5 SPs)
-- ==========================================================

-- 1. DOANH THU: Hôm nay (Theo giờ)

CREATE OR ALTER PROCEDURE sp_ThongKeBanHang_HomNay
AS
BEGIN
    SET NOCOUNT ON;
    WITH DoanhSoTheoGio AS (
        SELECT FORMAT(HD.NgayLap, 'HH:00') AS ThoiGian, COUNT(DISTINCT HD.MaHD) AS SoLuongHoaDon, ISNULL(SUM(CTHD.SoLuong * CTHD.DonGia), 0) AS TongGiaTri, ISNULL(SUM(CTHD.SoLuong * CTHD.GiamGia), 0) AS GiamGia
        FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD
        WHERE CONVERT(date, HD.NgayLap) = CONVERT(date, GETDATE())
        GROUP BY FORMAT(HD.NgayLap, 'HH:00')
    ),
    TraHangTheoGio AS (
        SELECT FORMAT(PT.NgayLap, 'HH:00') AS ThoiGian, COUNT(DISTINCT PT.MaPT) AS SoLuongDonTra, ISNULL(SUM(CTPT.SoLuong * (CTPT.DonGia - CTPT.GiamGia)), 0) AS GiaTriDonTra
        FROM PhieuTraHang PT JOIN ChiTietPhieuTraHang CTPT ON PT.MaPT = CTPT.MaPT
        WHERE CONVERT(date, PT.NgayLap) = CONVERT(date, GETDATE())
        GROUP BY FORMAT(PT.NgayLap, 'HH:00')
    )
    SELECT
        ISNULL(DS.ThoiGian, TH.ThoiGian) AS ThoiGian,
        ISNULL(DS.SoLuongHoaDon, 0) AS SoLuongHoaDon,
        ISNULL(DS.TongGiaTri, 0) AS TongGiaTri,
        ISNULL(DS.GiamGia, 0) AS GiamGia,
        ISNULL(TH.SoLuongDonTra, 0) AS SoLuongDonTra,
        ISNULL(TH.GiaTriDonTra, 0) AS GiaTriDonTra,
        -- [ĐÃ CẬP NHẬT] Doanh thu ròng * 1.05
        ((ISNULL(DS.TongGiaTri, 0) - ISNULL(DS.GiamGia, 0)) - ISNULL(TH.GiaTriDonTra, 0)) * 1.05 AS DoanhThu
    FROM DoanhSoTheoGio DS FULL OUTER JOIN TraHangTheoGio TH ON DS.ThoiGian = TH.ThoiGian
    ORDER BY ThoiGian;
END;
GO

CREATE OR ALTER PROCEDURE sp_ThongKeBanHang_TuanNay
AS
BEGIN
    SET NOCOUNT ON;
    SET DATEFIRST 1;
    DECLARE @Today DATE = GETDATE();
    DECLARE @StartOfWeek DATE = DATEADD(dd, 1 - DATEPART(dw, @Today), @Today);
    DECLARE @EndOfWeek DATE = DATEADD(dd, 6, @StartOfWeek);

    WITH DoanhSoTheoNgay AS (
        SELECT CONVERT(date, HD.NgayLap) AS Ngay,
               COUNT(DISTINCT HD.MaHD) AS SoLuongHoaDon,
               ISNULL(SUM(CTHD.SoLuong * CTHD.DonGia), 0) AS TongGiaTri,
               ISNULL(SUM(CTHD.SoLuong * CTHD.GiamGia), 0) AS GiamGia
        FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD
        -- Lọc theo khoảng ngày đã tính
        WHERE CONVERT(date, HD.NgayLap) BETWEEN @StartOfWeek AND @EndOfWeek
        GROUP BY CONVERT(date, HD.NgayLap)
    ),
    TraHangTheoNgay AS (
        SELECT CONVERT(date, PT.NgayLap) AS Ngay,
               COUNT(DISTINCT PT.MaPT) AS SoLuongDonTra,
               ISNULL(SUM(CTPT.SoLuong * (CTPT.DonGia - CTPT.GiamGia)), 0) AS GiaTriDonTra
        FROM PhieuTraHang PT JOIN ChiTietPhieuTraHang CTPT ON PT.MaPT = CTPT.MaPT
        -- Lọc theo khoảng ngày đã tính
        WHERE CONVERT(date, PT.NgayLap) BETWEEN @StartOfWeek AND @EndOfWeek
        GROUP BY CONVERT(date, PT.NgayLap)
    )
    SELECT
        FORMAT(ISNULL(DS.Ngay, TH.Ngay), 'dd/MM') AS ThoiGian,
        ISNULL(DS.SoLuongHoaDon, 0) AS SoLuongHoaDon,
        ISNULL(DS.TongGiaTri, 0) AS TongGiaTri,
        ISNULL(DS.GiamGia, 0) AS GiamGia,
        ISNULL(TH.SoLuongDonTra, 0) AS SoLuongDonTra,
        ISNULL(TH.GiaTriDonTra, 0) AS GiaTriDonTra,
        -- [ĐÃ CẬP NHẬT] Doanh thu ròng * 1.05
        ((ISNULL(DS.TongGiaTri, 0) - ISNULL(DS.GiamGia, 0)) - ISNULL(TH.GiaTriDonTra, 0)) * 1.05 AS DoanhThu
    FROM DoanhSoTheoNgay DS FULL OUTER JOIN TraHangTheoNgay TH ON DS.Ngay = TH.Ngay
    ORDER BY ISNULL(DS.Ngay, TH.Ngay);
END;
GO

CREATE OR ALTER PROCEDURE sp_ThongKeBanHang_ThangNay
AS
BEGIN
    SET NOCOUNT ON;
    WITH DoanhSoTheoNgay AS (
        SELECT CONVERT(date, HD.NgayLap) AS Ngay, COUNT(DISTINCT HD.MaHD) AS SoLuongHoaDon, ISNULL(SUM(CTHD.SoLuong * CTHD.DonGia), 0) AS TongGiaTri, ISNULL(SUM(CTHD.SoLuong * CTHD.GiamGia), 0) AS GiamGia
        FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD
        WHERE DATEPART(month, HD.NgayLap) = DATEPART(month, GETDATE()) AND DATEPART(year, HD.NgayLap) = DATEPART(year, GETDATE())
        GROUP BY CONVERT(date, HD.NgayLap)
    ),
    TraHangTheoNgay AS (
        SELECT CONVERT(date, PT.NgayLap) AS Ngay, COUNT(DISTINCT PT.MaPT) AS SoLuongDonTra, ISNULL(SUM(CTPT.SoLuong * (CTPT.DonGia - CTPT.GiamGia)), 0) AS GiaTriDonTra
        FROM PhieuTraHang PT JOIN ChiTietPhieuTraHang CTPT ON PT.MaPT = CTPT.MaPT
        WHERE DATEPART(month, PT.NgayLap) = DATEPART(month, GETDATE()) AND DATEPART(year, PT.NgayLap) = DATEPART(year, GETDATE())
        GROUP BY CONVERT(date, PT.NgayLap)
    )
    SELECT
        FORMAT(ISNULL(DS.Ngay, TH.Ngay), 'dd/MM') AS ThoiGian,
        ISNULL(DS.SoLuongHoaDon, 0) AS SoLuongHoaDon,
        ISNULL(DS.TongGiaTri, 0) AS TongGiaTri,
        ISNULL(DS.GiamGia, 0) AS GiamGia,
        ISNULL(TH.SoLuongDonTra, 0) AS SoLuongDonTra,
        ISNULL(TH.GiaTriDonTra, 0) AS GiaTriDonTra,
        -- [ĐÃ CẬP NHẬT] Doanh thu ròng * 1.05
        ((ISNULL(DS.TongGiaTri, 0) - ISNULL(DS.GiamGia, 0)) - ISNULL(TH.GiaTriDonTra, 0)) * 1.05 AS DoanhThu
    FROM DoanhSoTheoNgay DS FULL OUTER JOIN TraHangTheoNgay TH ON DS.Ngay = TH.Ngay
    ORDER BY ISNULL(DS.Ngay, TH.Ngay);
END;
GO

CREATE OR ALTER PROCEDURE sp_ThongKeBanHang_NamNay
AS
BEGIN
    SET NOCOUNT ON;
    WITH DoanhSoTheoThang AS (
        SELECT DATEPART(month, HD.NgayLap) AS Thang,
               COUNT(DISTINCT HD.MaHD) AS SoLuongHoaDon,
               ISNULL(SUM(CTHD.SoLuong * CTHD.DonGia), 0) AS TongGiaTri,
               ISNULL(SUM(CTHD.SoLuong * CTHD.GiamGia), 0) AS GiamGia
        FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD
        WHERE DATEPART(year, HD.NgayLap) = DATEPART(year, GETDATE())
        GROUP BY DATEPART(month, HD.NgayLap)
    ),
    TraHangTheoThang AS (
        SELECT DATEPART(month, PT.NgayLap) AS Thang,
               COUNT(DISTINCT PT.MaPT) AS SoLuongDonTra,
               ISNULL(SUM(CTPT.SoLuong * (CTPT.DonGia - CTPT.GiamGia)), 0) AS GiaTriDonTra
        FROM PhieuTraHang PT JOIN ChiTietPhieuTraHang CTPT ON PT.MaPT = CTPT.MaPT
        WHERE DATEPART(year, PT.NgayLap) = DATEPART(year, GETDATE())
        GROUP BY DATEPART(month, PT.NgayLap)
    )
    SELECT
        FORMAT(DATEFROMPARTS(DATEPART(year, GETDATE()), ISNULL(DS.Thang, TH.Thang), 1), 'MM/yyyy') AS ThoiGian,
        ISNULL(DS.SoLuongHoaDon, 0) AS SoLuongHoaDon,
        ISNULL(DS.TongGiaTri, 0) AS TongGiaTri,
        ISNULL(DS.GiamGia, 0) AS GiamGia,
        ISNULL(TH.SoLuongDonTra, 0) AS SoLuongDonTra,
        ISNULL(TH.GiaTriDonTra, 0) AS GiaTriDonTra,
        -- [ĐÃ CẬP NHẬT] Doanh thu ròng * 1.05
        ((ISNULL(DS.TongGiaTri, 0) - ISNULL(DS.GiamGia, 0)) - ISNULL(TH.GiaTriDonTra, 0)) * 1.05 AS DoanhThu
    FROM DoanhSoTheoThang DS FULL OUTER JOIN TraHangTheoThang TH ON DS.Thang = TH.Thang
    ORDER BY ISNULL(DS.Thang, TH.Thang);
END;
GO

CREATE OR ALTER PROCEDURE sp_ThongKeBanHang_TuyChon
    @NgayBatDau DATE,
    @NgayKetThuc DATE
AS
BEGIN
    SET NOCOUNT ON;
    WITH DoanhSoTheoNgay AS (
        SELECT CONVERT(date, HD.NgayLap) AS Ngay, COUNT(DISTINCT HD.MaHD) AS SoLuongHoaDon, ISNULL(SUM(CTHD.SoLuong * CTHD.DonGia), 0) AS TongGiaTri, ISNULL(SUM(CTHD.SoLuong * CTHD.GiamGia), 0) AS GiamGia
        FROM HoaDon HD JOIN ChiTietHoaDon CTHD ON HD.MaHD = CTHD.MaHD
        WHERE CONVERT(date, HD.NgayLap) BETWEEN @NgayBatDau AND @NgayKetThuc
        GROUP BY CONVERT(date, HD.NgayLap)
    ),
    TraHangTheoNgay AS (
        SELECT CONVERT(date, PT.NgayLap) AS Ngay, COUNT(DISTINCT PT.MaPT) AS SoLuongDonTra, ISNULL(SUM(CTPT.SoLuong * (CTPT.DonGia - CTPT.GiamGia)), 0) AS GiaTriDonTra
        FROM PhieuTraHang PT JOIN ChiTietPhieuTraHang CTPT ON PT.MaPT = CTPT.MaPT
        WHERE CONVERT(date, PT.NgayLap) BETWEEN @NgayBatDau AND @NgayKetThuc
        GROUP BY CONVERT(date, PT.NgayLap)
    )
    SELECT
        FORMAT(ISNULL(DS.Ngay, TH.Ngay), 'dd/MM') AS ThoiGian,
        ISNULL(DS.SoLuongHoaDon, 0) AS SoLuongHoaDon,
        ISNULL(DS.TongGiaTri, 0) AS TongGiaTri,
        ISNULL(DS.GiamGia, 0) AS GiamGia,
        ISNULL(TH.SoLuongDonTra, 0) AS SoLuongDonTra,
        ISNULL(TH.GiaTriDonTra, 0) AS GiaTriDonTra,
        -- [ĐÃ CẬP NHẬT] Doanh thu ròng * 1.05
        ((ISNULL(DS.TongGiaTri, 0) - ISNULL(DS.GiamGia, 0)) - ISNULL(TH.GiaTriDonTra, 0)) * 1.05 AS DoanhThu
    FROM DoanhSoTheoNgay DS FULL OUTER JOIN TraHangTheoNgay TH ON DS.Ngay = TH.Ngay
    ORDER BY ISNULL(DS.Ngay, TH.Ngay);
END;
GO

-- ==========================================================
-- Phần 2: TOP 5 SẢN PHẨM (5 SPs)
-- ==========================================================

-- 6. TOP 5: Hôm nay
CREATE OR ALTER PROCEDURE sp_Top5SanPham_HomNay
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5 T.MaThuoc, T.TenThuoc, SUM(CTHD.SoLuong) AS SoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS ThanhTien
    FROM ChiTietHoaDon CTHD
    JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD
    JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH
    JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc
    WHERE CONVERT(date, HD.NgayLap) = CONVERT(date, GETDATE())
    GROUP BY T.MaThuoc, T.TenThuoc ORDER BY SoLuong DESC;
END;
GO

-- 7. TOP 5: Tuần này
CREATE OR ALTER PROCEDURE sp_Top5SanPham_TuanNay
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Đặt ngày đầu tuần là Thứ Hai
    SET DATEFIRST 1;

    -- 2. Tính toán ngày đầu tuần (Thứ Hai) và cuối tuần (Chủ Nhật)
    DECLARE @Today DATE = GETDATE();
    DECLARE @StartOfWeek DATE = DATEADD(dd, 1 - DATEPART(dw, @Today), @Today);
    DECLARE @EndOfWeek DATE = DATEADD(dd, 6, @StartOfWeek);

    -- 3. Truy vấn
    SELECT TOP 5
        T.MaThuoc,
        T.TenThuoc,
        SUM(CTHD.SoLuong) AS SoLuong,
        SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS ThanhTien
    FROM ChiTietHoaDon CTHD
    JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD
    JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH
    JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc

    -- 4. Sửa điều kiện WHERE để dùng khoảng ngày tuyệt đối
    WHERE CONVERT(date, HD.NgayLap) BETWEEN @StartOfWeek AND @EndOfWeek

    GROUP BY T.MaThuoc, T.TenThuoc
    ORDER BY SoLuong DESC;
END;
GO

-- 8. TOP 5: Tháng này
CREATE OR ALTER PROCEDURE sp_Top5SanPham_ThangNay
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5 T.MaThuoc, T.TenThuoc, SUM(CTHD.SoLuong) AS SoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS ThanhTien
    FROM ChiTietHoaDon CTHD
    JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD
    JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH
    JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc
    WHERE DATEPART(month, HD.NgayLap) = DATEPART(month, GETDATE()) AND DATEPART(year, HD.NgayLap) = DATEPART(year, GETDATE())
    GROUP BY T.MaThuoc, T.TenThuoc ORDER BY SoLuong DESC;
END;
GO

-- 9. TOP 5: Quý này
CREATE OR ALTER PROCEDURE sp_Top5SanPham_NamNay
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5 T.MaThuoc, T.TenThuoc, SUM(CTHD.SoLuong) AS SoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS ThanhTien
    FROM ChiTietHoaDon CTHD
    JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD
    JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH
    JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc
    -- 👇 [THAY ĐỔI] Lọc theo năm hiện tại (bỏ điều kiện quý)
    WHERE DATEPART(year, HD.NgayLap) = DATEPART(year, GETDATE())
    GROUP BY T.MaThuoc, T.TenThuoc ORDER BY SoLuong DESC;
END;
GO

-- 10. TOP 5: Tùy chọn (Theo khoảng ngày)
CREATE OR ALTER PROCEDURE sp_Top5SanPham_TuyChon
    @NgayBatDau DATE,
    @NgayKetThuc DATE
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5 T.MaThuoc, T.TenThuoc, SUM(CTHD.SoLuong) AS SoLuong, SUM(CTHD.SoLuong * (CTHD.DonGia - CTHD.GiamGia)) AS ThanhTien
    FROM ChiTietHoaDon CTHD
    JOIN HoaDon HD ON CTHD.MaHD = HD.MaHD
    JOIN Thuoc_SP_TheoLo L ON CTHD.MaLH = L.MaLH
    JOIN Thuoc_SanPham T ON L.MaThuoc = T.MaThuoc
    WHERE CONVERT(date, HD.NgayLap) BETWEEN @NgayBatDau AND @NgayKetThuc
    GROUP BY T.MaThuoc, T.TenThuoc ORDER BY SoLuong DESC;
END;
GO

--------- THỐNG KÊ XUẤT NHẬP TỒN
GO

-- ==========================================================
-- 1. SP THỐNG KÊ THUỐC HẾT HẠN
-- ==========================================================

USE QuanLyNhaThuoc;
GO

-- 1. Xóa Procedure cũ để tránh lỗi cache
IF OBJECT_ID('sp_ThongKeThuocHetHan', 'P') IS NOT NULL
    DROP PROCEDURE sp_ThongKeThuocHetHan;
GO

-- 2. Tạo lại Procedure với logic chuẩn
CREATE PROCEDURE sp_ThongKeThuocHetHan
AS
BEGIN
    SET NOCOUNT ON;

    -- Lấy số ngày từ cài đặt
    DECLARE @SoNgayCanhBao INT;

    -- Dùng TRY_CAST để an toàn, Trim khoảng trắng
    SELECT @SoNgayCanhBao = TRY_CAST(RTRIM(LTRIM(GiaTri)) AS INT)
    FROM ThongSoUngDung
    WHERE TenThongSo = 'NgayHetHan';

    -- Nếu không tìm thấy hoặc lỗi, mặc định 30 ngày
    IF @SoNgayCanhBao IS NULL SET @SoNgayCanhBao = 30;

    -- Truy vấn
    SELECT
        T.MaThuoc       AS maThuocHH,
        T.TenThuoc      AS tenThuocHH,
        SUM(L.SoLuongTon) AS soLuong,
        L.HSD           AS ngayHetHan
    FROM
        Thuoc_SP_TheoLo AS L
    JOIN
        Thuoc_SanPham AS T ON L.MaThuoc = T.MaThuoc
    WHERE
        L.HSD <= DATEADD(DAY, @SoNgayCanhBao, CAST(GETDATE() AS DATE))
        AND L.SoLuongTon > 0
    GROUP BY
        T.MaThuoc, T.TenThuoc, L.HSD
    ORDER BY
        L.HSD ASC;
END;
GO

-- ==========================================================
-- 2. SP THỐNG KÊ XUẤT - NHẬP - TỒN
-- ==========================================================
IF OBJECT_ID('sp_ThongKeXNT', 'P') IS NOT NULL
    DROP PROCEDURE sp_ThongKeXNT;
GO

CREATE PROCEDURE sp_ThongKeXNT
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    SET NOCOUNT ON;

    -- CTE 0: Lấy hệ số quy đổi CƠ BẢN cho mỗi thuốc
    -- Thêm ISNULL để tránh lỗi chia cho 0 nếu chưa set ĐVTCB
    WITH DonViCoBan AS (
        SELECT
            MaThuoc,
            MaDVT,
            ISNULL(HeSoQuyDoi, 1) AS HeSoQuyDoi
        FROM
            ChiTietDonViTinh
        WHERE
            DonViCoBan = 1
    ),

    -- CTE 1: Lấy đơn vị tính cơ bản (base unit) để hiển thị
    BaseUnits AS (
        SELECT
            CT.MaThuoc,
            DVT.KiHieu AS DVT
        FROM
            ChiTietDonViTinh AS CT
        JOIN
            DonViTinh AS DVT ON CT.MaDVT = DVT.MaDVT
        WHERE
            CT.DonViCoBan = 1
    ),

    -- CTE 2: Lấy danh sách tất cả sản phẩm và DVT cơ bản
    AllProducts AS (
        SELECT
            T.MaThuoc,
            T.TenThuoc,
            ISNULL(BU.DVT, N'N/A') AS DVT
        FROM
            Thuoc_SanPham AS T
        LEFT JOIN
            BaseUnits AS BU ON T.MaThuoc = BU.MaThuoc
    ),

    -- CTE 3: Tổng hợp tất cả các giao dịch (ĐÃ SỬA LỖI QUY ĐỔI)
    Transactions AS (
        -- 1. Nhập hàng từ Nhà cung cấp
        SELECT
            CTPN.MaThuoc,
            PN.NgayNhap AS NgayGiaoDich,
            -- [SỬA] Công thức quy đổi: SoLuong_Goc * (HeSo_Goc / HeSo_CoBan)
            CEILING(
                CTPN.SoLuong * ISNULL(DVT.HeSoQuyDoi, 1) / DVTCB.HeSoQuyDoi
            ) AS SoLuongNhap,
            0 AS SoLuongXuat
        FROM
            ChiTietPhieuNhap AS CTPN
        JOIN
            PhieuNhap AS PN ON CTPN.MaPN = PN.MaPN
        -- Join để lấy hệ số của đơn vị GIAO DỊCH
        LEFT JOIN
            ChiTietDonViTinh AS DVT ON CTPN.MaThuoc = DVT.MaThuoc AND CTPN.MaDVT = DVT.MaDVT
        -- Join để lấy hệ số của đơn vị CƠ BẢN
        JOIN
            DonViCoBan AS DVTCB ON CTPN.MaThuoc = DVTCB.MaThuoc

        UNION ALL

        -- 2. Nhập hàng từ Khách trả hàng
        SELECT
            L.MaThuoc,
            PT.NgayLap AS NgayGiaoDich,
            -- [SỬA] Áp dụng quy đổi
            CEILING(
                CTPT.SoLuong * ISNULL(DVT.HeSoQuyDoi, 1) / DVTCB.HeSoQuyDoi
            ) AS SoLuongNhap,
            0 AS SoLuongXuat
        FROM
            ChiTietPhieuTraHang AS CTPT
        JOIN
            PhieuTraHang AS PT ON CTPT.MaPT = PT.MaPT
        JOIN
            Thuoc_SP_TheoLo AS L ON CTPT.MaLH = L.MaLH
        LEFT JOIN
            ChiTietDonViTinh AS DVT ON L.MaThuoc = DVT.MaThuoc AND CTPT.MaDVT = DVT.MaDVT
        JOIN
            DonViCoBan AS DVTCB ON L.MaThuoc = DVTCB.MaThuoc

        UNION ALL

        -- 3. Nhập hàng từ Đổi hàng (Khách trả lại, SoLuong < 0)
        SELECT
            CTPD.MaThuoc,
            PD.NgayLap AS NgayGiaoDich,
            -- [SỬA] Áp dụng quy đổi
            CEILING(
                ABS(CTPD.SoLuong) * ISNULL(DVT.HeSoQuyDoi, 1) / DVTCB.HeSoQuyDoi
            ) AS SoLuongNhap,
            0 AS SoLuongXuat
        FROM
            ChiTietPhieuDoiHang AS CTPD
        JOIN
            PhieuDoiHang AS PD ON CTPD.MaPD = PD.MaPD
        LEFT JOIN
            ChiTietDonViTinh AS DVT ON CTPD.MaThuoc = DVT.MaThuoc AND CTPD.MaDVT = DVT.MaDVT
        JOIN
            DonViCoBan AS DVTCB ON CTPD.MaThuoc = DVTCB.MaThuoc
        WHERE
            CTPD.SoLuong < 0

        UNION ALL

        -- 4. Xuất hàng do Bán hàng (Hóa đơn)
        SELECT
            L.MaThuoc,
            HD.NgayLap AS NgayGiaoDich,
            0 AS SoLuongNhap,
            -- [SỬA] Áp dụng quy đổi
            CEILING(
                CTHD.SoLuong * ISNULL(DVT.HeSoQuyDoi, 1) / DVTCB.HeSoQuyDoi
            ) AS SoLuongXuat
        FROM
            ChiTietHoaDon AS CTHD
        JOIN
            HoaDon AS HD ON CTHD.MaHD = HD.MaHD
        JOIN
            Thuoc_SP_TheoLo AS L ON CTHD.MaLH = L.MaLH
        LEFT JOIN
            ChiTietDonViTinh AS DVT ON L.MaThuoc = DVT.MaThuoc AND CTHD.MaDVT = DVT.MaDVT
        JOIN
            DonViCoBan AS DVTCB ON L.MaThuoc = DVTCB.MaThuoc

        UNION ALL

        -- 5. Xuất hàng do Đổi hàng (Khách lấy hàng mới, SoLuong > 0)
        SELECT
            CTPD.MaThuoc,
            PD.NgayLap AS NgayGiaoDich,
            0 AS SoLuongNhap,
            -- [SỬA] Áp dụng quy đổi
            CEILING(
                CTPD.SoLuong * ISNULL(DVT.HeSoQuyDoi, 1) / DVTCB.HeSoQuyDoi
            ) AS SoLuongXuat
        FROM
            ChiTietPhieuDoiHang AS CTPD
        JOIN
            PhieuDoiHang AS PD ON CTPD.MaPD = PD.MaPD
        LEFT JOIN
            ChiTietDonViTinh AS DVT ON CTPD.MaThuoc = DVT.MaThuoc AND CTPD.MaDVT = DVT.MaDVT
        JOIN
            DonViCoBan AS DVTCB ON CTPD.MaThuoc = DVTCB.MaThuoc
        WHERE
            CTPD.SoLuong > 0
    ),

    -- CTE 4: Tổng hợp các giao dịch theo ngày
    DailySummary AS (
        SELECT
            MaThuoc,
            CONVERT(date, NgayGiaoDich) AS Ngay,
            SUM(SoLuongNhap) AS TongNhap,
            SUM(SoLuongXuat) AS TongXuat
        FROM
            Transactions
        GROUP BY
            MaThuoc, CONVERT(date, NgayGiaoDich)
    )

    -- Tính toán cuối cùng (Không thay đổi)
    SELECT
        P.MaThuoc,
        P.TenThuoc,
        P.DVT,

        -- Tồn Đầu Kỳ: Tổng (Nhập - Xuất) TRƯỚC @TuNgay
        ISNULL(SUM(CASE WHEN DS.Ngay < @TuNgay THEN DS.TongNhap - DS.TongXuat ELSE 0 END), 0) AS TonDauKy,

        -- Nhập Trong Kỳ: Tổng Nhập TRONG KHOẢNG @TuNgay VÀ @DenNgay
        ISNULL(SUM(CASE WHEN DS.Ngay BETWEEN @TuNgay AND @DenNgay THEN DS.TongNhap ELSE 0 END), 0) AS NhapTrongKy,

        -- Xuất Trong Kỳ: Tổng Xuất TRONG KHOẢNG @TuNgay VÀ @DenNgay
        ISNULL(SUM(CASE WHEN DS.Ngay BETWEEN @TuNgay AND @DenNgay THEN DS.TongXuat ELSE 0 END), 0) AS XuatTrongKy,

        -- Tồn Cuối Kỳ: Tổng (Nhập - Xuất) TÍNH ĐẾN HẾT @DenNgay
        ISNULL(SUM(CASE WHEN DS.Ngay <= @DenNgay THEN DS.TongNhap - DS.TongXuat ELSE 0 END), 0) AS TonCuoiKy
    FROM
        AllProducts AS P
    LEFT JOIN
        DailySummary AS DS ON P.MaThuoc = DS.MaThuoc
    GROUP BY
        P.MaThuoc, P.TenThuoc, P.DVT
    -- Chỉ hiển thị những thuốc có tồn kho hoặc có giao dịch trong kỳ
    HAVING
        (ISNULL(SUM(CASE WHEN DS.Ngay <= @DenNgay THEN DS.TongNhap - DS.TongXuat ELSE 0 END), 0) <> 0) -- Có tồn cuối kỳ
        OR (ISNULL(SUM(CASE WHEN DS.Ngay BETWEEN @TuNgay AND @DenNgay THEN DS.TongNhap ELSE 0 END), 0) <> 0) -- Có nhập trong kỳ
        OR (ISNULL(SUM(CASE WHEN DS.Ngay BETWEEN @TuNgay AND @DenNgay THEN DS.TongXuat ELSE 0 END), 0) <> 0) -- Có xuất trong kỳ
    ORDER BY
        P.TenThuoc;
END;
GO

GO

PRINT N'=== HOÀN TẤT! Đã tạo 2 SP cho Thống kê XNT. ===';

go

CREATE OR ALTER PROCEDURE sp_LuuPhieuNhap
    @MaPN VARCHAR(10),
    @NgayNhap DATE,
    @GhiChu NVARCHAR(255),
    @MaNCC VARCHAR(10),
    @MaNV VARCHAR(10),
    @MaThuoc VARCHAR(10),
    @MaLH VARCHAR(10),
    @SoLuong INT,
    @GiaNhap FLOAT,
    @ChietKhau FLOAT,
    @Thue FLOAT,
    @SoLuongTon INT = NULL,
    @NSX DATE = NULL,
    @HSD DATE = NULL,
    @MaDVT VARCHAR(10) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        ---------------------------------------------------------
        -- 1️⃣ Đọc context hiện tại để tạm thời bỏ qua trigger
        ---------------------------------------------------------
        DECLARE @oldContextText NVARCHAR(128) =
            RTRIM(REPLACE(CAST(CONTEXT_INFO() AS NVARCHAR(128)), CHAR(0), ''));
        DECLARE @MaNVContext NVARCHAR(50) = ISNULL(@oldContextText, @MaNV);

        DECLARE @newContextText NVARCHAR(128);
        IF ISNULL(@MaNVContext, '') = ''
            SET @newContextText = @MaNV + '|IGNORE_TRG';
        ELSE
            SET @newContextText = @MaNVContext + '|IGNORE_TRG';

        DECLARE @newContext VARBINARY(128) = CAST(@newContextText AS VARBINARY(128));
        SET CONTEXT_INFO @newContext;

        ---------------------------------------------------------
        -- 2️⃣ Xác định hệ số quy đổi dựa trên đơn vị nhập
        ---------------------------------------------------------
        DECLARE @HeSoQuyDoi FLOAT;

        -- 🔹 Lấy hệ số quy đổi của đơn vị hiện tại
        SELECT @HeSoQuyDoi = HeSoQuyDoi
        FROM ChiTietDonViTinh
        WHERE MaThuoc = @MaThuoc AND MaDVT = @MaDVT;

        -- 🔹 Lấy hệ số của đơn vị cơ bản
        DECLARE @HeSoCoBan FLOAT;
        SELECT @HeSoCoBan = HeSoQuyDoi
        FROM ChiTietDonViTinh
        WHERE MaThuoc = @MaThuoc AND DonViCoBan = 1;

        -- 🔹 Mặc định nếu null
        SET @HeSoQuyDoi = ISNULL(@HeSoQuyDoi, 1);
        SET @HeSoCoBan = ISNULL(@HeSoCoBan, 1);

        ---------------------------------------------------------
        -- 3️⃣ Phiếu nhập
        ---------------------------------------------------------
        IF NOT EXISTS (SELECT 1 FROM PhieuNhap WHERE MaPN = @MaPN)
            INSERT INTO PhieuNhap (MaPN, NgayNhap, TrangThai, GhiChu, MaNCC, MaNV)
            VALUES (@MaPN, @NgayNhap, 1, @GhiChu, @MaNCC, @MaNV);
        ELSE
            UPDATE PhieuNhap
            SET NgayNhap = @NgayNhap, TrangThai = 1, GhiChu = @GhiChu, MaNCC = @MaNCC, MaNV = @MaNV
            WHERE MaPN = @MaPN;

        ---------------------------------------------------------
        -- 4️⃣ Chi tiết phiếu nhập
        ---------------------------------------------------------
        IF EXISTS (SELECT 1 FROM ChiTietPhieuNhap WHERE MaPN = @MaPN AND MaThuoc = @MaThuoc AND MaLH = @MaLH)
            UPDATE ChiTietPhieuNhap
            SET SoLuong = @SoLuong, 
                GiaNhap = @GiaNhap, 
                ChietKhau = @ChietKhau, 
                Thue = @Thue,
                MaDVT = @MaDVT  -- [ĐÃ SỬA] Thêm MaDVT vào UPDATE
            WHERE MaPN = @MaPN AND MaThuoc = @MaThuoc AND MaLH = @MaLH;
        ELSE
            INSERT INTO ChiTietPhieuNhap (MaPN, MaThuoc, MaLH, SoLuong, GiaNhap, ChietKhau, Thue, MaDVT) -- [ĐÃ SỬA] Thêm MaDVT
            VALUES (@MaPN, @MaThuoc, @MaLH, @SoLuong, @GiaNhap, @ChietKhau, @Thue, @MaDVT); -- [ĐÃ SỬA] Thêm @MaDVT

        ---------------------------------------------------------
        -- 5️⃣ Cập nhật kho
           DECLARE @SoLuongTonQuyDoi INT = CEILING(@SoLuong * @HeSoQuyDoi / @HeSoCoBan); -- Dùng CEILING

        IF EXISTS (SELECT 1 FROM Thuoc_SP_TheoLo WHERE MaLH = @MaLH)
        UPDATE Thuoc_SP_TheoLo
        SET SoLuongTon = SoLuongTon + @SoLuongTonQuyDoi
        WHERE MaLH = @MaLH;
        ELSE
        INSERT INTO Thuoc_SP_TheoLo (MaPN, MaThuoc, MaLH, SoLuongTon, NSX, HSD)
        VALUES (@MaPN, @MaThuoc, @MaLH, @SoLuongTonQuyDoi, @NSX, @HSD);


        ---------------------------------------------------------
        -- 6️⃣ Cập nhật giá nhập/bán
        ---------------------------------------------------------
        UPDATE ChiTietDonViTinh
        SET GiaNhap = @GiaNhap,
            GiaBan = CASE WHEN @GiaNhap > GiaBan THEN @GiaNhap ELSE GiaBan END
        WHERE MaThuoc = @MaThuoc AND MaDVT = @MaDVT;

        COMMIT TRANSACTION;

        ---------------------------------------------------------
        -- 7️⃣ Khôi phục lại context cũ
        ---------------------------------------------------------
        DECLARE @restoreContext VARBINARY(128) = CAST(@MaNVContext AS VARBINARY(128));
        SET CONTEXT_INFO @restoreContext;

    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END
GO



CREATE PROCEDURE sp_HangHetHan
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        MaThuoc,
        MaLH,
        HSD
    FROM Thuoc_SP_TheoLo
    WHERE HSD < CAST(GETDATE() AS DATE)
    ORDER BY HSD ASC;
END;
GO

CREATE OR ALTER PROCEDURE sp_HangSapHetHan
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @SoNgayCanhBao INT;

    -- Dùng TRY_CAST để an toàn, Trim khoảng trắng
    SELECT @SoNgayCanhBao = TRY_CAST(RTRIM(LTRIM(GiaTri)) AS INT)
    FROM ThongSoUngDung
    WHERE TenThongSo = 'NgayHetHan';

    SELECT
        MaLH,
        HSD,
        MaThuoc
    FROM Thuoc_SP_TheoLo
    WHERE HSD <= DATEADD(DAY, @SoNgayCanhBao, CAST(GETDATE() AS DATE))
    ORDER BY HSD ASC;
END;
GO




CREATE OR ALTER PROCEDURE sp_InsertThuoc_SanPham
    @TenThuoc NVARCHAR(100),
    @HamLuong INT,
    @DonViHL VARCHAR(20),
    @DuongDung NVARCHAR(20),
    @QuyCachDongGoi NVARCHAR(20),
    @SDK_GPNK VARCHAR(20),
    @HangSX NVARCHAR(30),
    @NuocSX NVARCHAR(20),
    @MaLoaiHang VARCHAR(10),
    @MaNDL VARCHAR(10),
    @ViTri VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @NewMaThuoc VARCHAR(10);
    DECLARE @MaxMa VARCHAR(10);
    DECLARE @Num INT;

    BEGIN TRANSACTION;

    -- 🔍 Lấy mã lớn nhất hiện có
    SELECT @MaxMa = MAX(MaThuoc)
    FROM Thuoc_SanPham WITH (TABLOCKX)
    WHERE MaThuoc LIKE 'TS%';

    -- 🧩 Nếu chưa có dữ liệu, tạo TS001
    IF @MaxMa IS NULL
        SET @NewMaThuoc = 'TS001';
    ELSE
    BEGIN
        SET @Num = TRY_CAST(SUBSTRING(@MaxMa, 3, LEN(@MaxMa)) AS INT);
        IF @Num IS NULL SET @Num = 0;
        SET @Num += 1;
        SET @NewMaThuoc = 'TS' + RIGHT('000' + CAST(@Num AS VARCHAR(3)), 3);
    END;

    -- 🧾 Thêm bản ghi mới vào Thuoc_SanPham
    INSERT INTO Thuoc_SanPham (
        MaThuoc, TenThuoc, HamLuong, DonViHL, DuongDung, QuyCachDongGoi,
        SDK_GPNK, HangSX, NuocSX, MaLoaiHang, MaNDL, ViTri, TrangThaiXoa
    ) VALUES (
        @NewMaThuoc, @TenThuoc, @HamLuong, @DonViHL, @DuongDung, @QuyCachDongGoi,
        @SDK_GPNK, @HangSX, @NuocSX, @MaLoaiHang, @MaNDL, @ViTri, 0
    );

    -- ➕ Thêm luôn vào ChiTietDonViTinh
    INSERT INTO ChiTietDonViTinh (
        MaThuoc, MaDVT, HeSoQuyDoi, GiaNhap, GiaBan, DonViCoBan
    ) VALUES (
        @NewMaThuoc, 'DVT01', 1, 0, 0, 1
    );

    COMMIT TRANSACTION;

    SELECT @NewMaThuoc AS MaThuoc;
END;
GO




--================================================================================================================================================================================================
--================================================================================================================================================================================================
--================================================================================================================================================================================================
--XỬ LÝ ĐƠN ĐẶT HÀNG

CREATE OR ALTER PROCEDURE sp_CapNhatTrangThaiDatHang @MaThuoc VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @TongTon INT = (
        SELECT SUM(SoLuongTon)
        FROM Thuoc_SP_TheoLo
        WHERE MaThuoc = @MaThuoc
    );

    -- Cập nhật trạng thái từng chi tiết phiếu đặt có liên quan
    UPDATE ctpd
    SET TrangThai =
        CASE
            WHEN @TongTon >= CEILING(ctpd.SoLuong *
                ISNULL(
                    (SELECT HeSoQuyDoi FROM ChiTietDonViTinh dvt
                     WHERE dvt.MaThuoc = ctpd.MaThuoc AND dvt.MaDVT = ctpd.MaDVT),
                    1
                )
            )
            THEN 1
            ELSE 0
        END
    FROM ChiTietPhieuDatHang ctpd
    WHERE ctpd.MaThuoc = @MaThuoc;

    -- Nếu tất cả chi tiết đủ hàng thì cập nhật phiếu
    UPDATE pd
    SET TrangThai =
        CASE
            WHEN NOT EXISTS (
                SELECT 1 FROM ChiTietPhieuDatHang
                WHERE MaPDat = pd.MaPDat AND TrangThai = 0
            ) THEN 1
            ELSE 0
        END
    FROM PhieuDatHang pd
    WHERE EXISTS (
        SELECT 1
        FROM ChiTietPhieuDatHang ctpd
        WHERE ctpd.MaPDat = pd.MaPDat AND ctpd.MaThuoc = @MaThuoc
    );
END;
GO

--====================================================================================
--====================================================================================
CREATE OR ALTER TRIGGER trg_CapNhatTrangThaiDatHang
ON Thuoc_SP_TheoLo
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @MaThuoc VARCHAR(10);

    DECLARE cur_cntt CURSOR FOR
        SELECT DISTINCT MaThuoc FROM inserted;
    OPEN cur_cntt; FETCH NEXT FROM cur_cntt INTO @MaThuoc;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        EXEC sp_CapNhatTrangThaiDatHang @MaThuoc;
        FETCH NEXT FROM cur_cntt INTO @MaThuoc;
    END

    CLOSE cur_cntt; DEALLOCATE cur_cntt;
END;
GO

--====================================================================================
--====================================================================================

CREATE OR ALTER TRIGGER trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT
ON ChiTietPhieuDatHang
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @MaPDat VARCHAR(10);
    DECLARE @MaThuoc VARCHAR(10);

    DECLARE cur CURSOR FOR
        SELECT DISTINCT MaPDat, MaThuoc FROM inserted;

    OPEN cur;
    FETCH NEXT FROM cur INTO @MaPDat, @MaThuoc;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        DECLARE @TongTon INT = (
            SELECT SUM(SoLuongTon)
            FROM Thuoc_SP_TheoLo
            WHERE MaThuoc = @MaThuoc
        );

        -- Cập nhật trạng thái từng chi tiết
        UPDATE ctpd
        SET TrangThai =
            CASE
                WHEN @TongTon >= CEILING(ctpd.SoLuong *
                    ISNULL(
                        (SELECT HeSoQuyDoi 
                         FROM ChiTietDonViTinh dvt
                         WHERE dvt.MaThuoc = ctpd.MaThuoc AND dvt.MaDVT = ctpd.MaDVT),
                        1
                    ))
                THEN 1 ELSE 0 END
        FROM ChiTietPhieuDatHang ctpd
        WHERE ctpd.MaPDat = @MaPDat AND ctpd.MaThuoc = @MaThuoc;

        -- Cập nhật trạng thái phiếu (1 nếu tất cả chi tiết đủ hàng)
        UPDATE pd
        SET TrangThai =
        CASE 
            WHEN NOT EXISTS (
                SELECT 1 
                FROM ChiTietPhieuDatHang 
                WHERE MaPDat = @MaPDat AND TrangThai = 0
            )
            THEN 1 ELSE 0 END
        FROM PhieuDatHang pd
        WHERE pd.MaPDat = @MaPDat;

        FETCH NEXT FROM cur INTO @MaPDat, @MaThuoc;
    END

    CLOSE cur;
    DEALLOCATE cur;
END;
GO




--Procedure – Duyệt Phiếu Đặt (Giữ hàng theo lô FEFO)
CREATE OR ALTER PROCEDURE sp_DuyetPhieuDatHang
    @MaPDat VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE
        @MaThuoc VARCHAR(10),
        @MaDVT VARCHAR(10),
        @SoLuong INT,
        @HeSo FLOAT,
        @SoLuongCoBan INT;

    DECLARE cur CURSOR FOR
        SELECT MaThuoc, MaDVT, SoLuong
        FROM ChiTietPhieuDatHang
        WHERE MaPDat = @MaPDat;

    OPEN cur;
    FETCH NEXT FROM cur INTO @MaThuoc, @MaDVT, @SoLuong;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Lấy hệ số quy đổi sang đơn vị cơ bản
        SELECT @HeSo = ISNULL(HeSoQuyDoi, 1)
        FROM ChiTietDonViTinh
        WHERE MaThuoc = @MaThuoc AND MaDVT = @MaDVT;

        SET @SoLuongCoBan = CEILING(@SoLuong * @HeSo);
        DECLARE @CanTru INT = @SoLuongCoBan;

        DECLARE @MaLH VARCHAR(10), @Ton INT;

        -- Duyệt theo thứ tự hạn sử dụng gần nhất (FEFO)
        DECLARE curLo CURSOR FOR
            SELECT MaLH, SoLuongTon
            FROM Thuoc_SP_TheoLo
            WHERE MaThuoc = @MaThuoc
              AND HSD > GETDATE()
            ORDER BY HSD ASC;  -- FEFO: gần hết hạn trước
        OPEN curLo;
        FETCH NEXT FROM curLo INTO @MaLH, @Ton;

        WHILE @@FETCH_STATUS = 0 AND @CanTru > 0
        BEGIN
            DECLARE @Tru INT = CASE WHEN @Ton >= @CanTru THEN @CanTru ELSE @Ton END;

            UPDATE Thuoc_SP_TheoLo
            SET SoLuongTon = SoLuongTon - @Tru,
                SoLuongGiu = SoLuongGiu + @Tru
            WHERE MaLH = @MaLH;

            SET @CanTru = @CanTru - @Tru;
            FETCH NEXT FROM curLo INTO @MaLH, @Ton;
        END

        CLOSE curLo;
        DEALLOCATE curLo;

        FETCH NEXT FROM cur INTO @MaThuoc, @MaDVT, @SoLuong;
    END

    CLOSE cur;
    DEALLOCATE cur;

    -- Sau khi duyệt xong phiếu
    UPDATE PhieuDatHang
    SET TrangThai = 2 -- đã duyệt, đang giữ hàng
    WHERE MaPDat = @MaPDat;
END;
GO

-- STORED PROCEDURE 1: (Đã cập nhật VAT)

CREATE PROCEDURE sp_GetHoaDonTheoThoiGian
    @ThoiGian NVARCHAR(20)
AS
BEGIN
    DECLARE @TuNgay DATE
    DECLARE @DenNgay DATE
    
    -- ----- SỬA LỖI Ở ĐÂY -----
    -- Di chuyển dòng này lên đầu, bên ngoài tất cả các khối IF
    DECLARE @Today DATE = GETDATE() 
    -- -------------------------

    IF @ThoiGian = 'Hôm nay'
    BEGIN
        SET @TuNgay = CAST(GETDATE() AS DATE)
        SET @DenNgay = CAST(GETDATE() AS DATE)
    END
    ELSE IF @ThoiGian = 'Tuần này'
    BEGIN
        SET DATEFIRST 1; -- Đặt Thứ Hai là ngày đầu tuần
        SET @TuNgay = DATEADD(dd, 1 - DATEPART(dw, @Today), @Today);
        SET @DenNgay = DATEADD(dd, 6, @TuNgay);
    END
    ELSE IF @ThoiGian = 'Tháng này'
    BEGIN
        SET @TuNgay = DATEADD(month, DATEDIFF(month, 0, GETDATE()), 0)
        SET @DenNgay = EOMONTH(GETDATE())
    END
    ELSE IF @ThoiGian = 'Năm Nay'
    BEGIN
        SET @TuNgay = DATEFROMPARTS(YEAR(GETDATE()), 1, 1)
        SET @DenNgay = DATEFROMPARTS(YEAR(GETDATE()), 12, 31)
    END

    -- Logic SELECT (Phần này đã đúng)
    SELECT 
        h.maHD, 
        CAST(h.ngayLap AS DATE) as ngayLap,
        h.maKH, 
        h.maNV,
        ISNULL(SUM(ct.SoLuong * (ct.DonGia - ct.GiamGia)), 0) * 1.05 AS tongTienNet
    FROM 
        HoaDon h
    LEFT JOIN 
        ChiTietHoaDon ct ON h.maHD = ct.maHD
    WHERE 
        CAST(h.ngayLap AS DATE) BETWEEN @TuNgay AND @DenNgay
    GROUP BY
        h.maHD, CAST(h.ngayLap AS DATE), h.maKH, h.maNV
    ORDER BY 
        ngayLap DESC;
END
GO

-- STORED PROCEDURE 2: (Đã cập nhật VAT)
CREATE PROCEDURE sp_GetHoaDonTheoTuyChon
    @TuNgay DATE,
    @DenNgay DATE
AS
BEGIN
    -- Lấy danh sách hóa đơn VÀ tính tổng tiền NET (đã bao gồm 5% VAT)
    SELECT 
        h.maHD, 
        CAST(h.ngayLap AS DATE) as ngayLap,
        h.maKH, 
        h.maNV,
        -- SỬA Ở ĐÂY: Thêm * 1.05 để tính 5% VAT
        ISNULL(SUM(ct.SoLuong * (ct.DonGia - ct.GiamGia)), 0) * 1.05 AS tongTienNet
    FROM 
        HoaDon h
    LEFT JOIN 
        ChiTietHoaDon ct ON h.maHD = ct.maHD
    WHERE 
        CAST(h.ngayLap AS DATE) BETWEEN @TuNgay AND @DenNgay
    GROUP BY
        h.maHD, CAST(h.ngayLap AS DATE), h.maKH, h.maNV
    ORDER BY 
        ngayLap DESC;
END
GO


