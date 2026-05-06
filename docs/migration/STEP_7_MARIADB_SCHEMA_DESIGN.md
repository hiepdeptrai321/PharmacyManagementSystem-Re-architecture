# STEP 7 - Thiet Ke Lai Database Cho MariaDB

## Muc tieu buoc 7

Buoc nay tap trung chuyen doi va thiet ke lai schema database tu SQL Server sang MariaDB de lam nen cho JPA/Hibernate o cac buoc sau.

Pham vi buoc nay:

- Phan tich script SQL Server hien tai.
- Liet ke bang, khoa, trigger, stored procedure va diem phu thuoc SQL Server-specific.
- Tao file [schema_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/schema_mariadb.sql).
- Tao file [seed_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/seed_mariadb.sql).
- Ghi ro cac diem can review thu cong truoc khi map JPA dai tra.

Khong lam trong buoc nay:

- Khong sua code Java hang loat.
- Khong chuyen DAO sang JPA.
- Khong tao JPA Entity hang loat.
- Khong doi toan bo query Java sang JPQL.
- Khong xoa schema SQL Server cu.
- Khong de Hibernate tu sinh schema production.

## File SQL Server da phan tich

Da doc va phan tich:

- [QuanLyNhaThuoc.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/QuanLyNhaThuoc.sql)

Khong co file SQL tao schema nao khac trong thu muc `SQL/`.

Thu muc [SQL/imgThuoc](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/imgThuoc) chi chua anh thuoc, khong phai schema.

## Tong hop nhanh sau khi phan tich

- So bang: `29`
- So primary key declarations: `29`
- So foreign key declarations inline: `41`
- So trigger: `6`
- So stored procedure: `21`
- So function nguoi dung: `0`
- Co dung `CONTEXT_INFO`: `co`
- Co dung `IDENTITY`: `co`, 1 cho `HoatDong.ID`
- Co dung `GETDATE()`: `co`
- Co dung `TOP`: `co`
- Co dung `GO`: `co`
- Co dung `EXEC` / `sp_executesql`: `co`
- Co dung `CONVERT`, `DATEPART`, `ISNULL`, `FORMAT`, `STRING_AGG`, `FULL OUTER JOIN`, `SET DATEFIRST`, `TRY_CAST`, `DATEFROMPARTS`, `EOMONTH`, `WITH (TABLOCKX)`, `OPENROWSET`: `co`

## Danh sach bang hien tai

| STT | Bang | Khoa chinh | Khoa ngoai chinh | Ghi chu |
|---|---|---|---|---|
| 1 | `KhachHang` | `MaKH` | Khong co | Co `GioiTinh BIT`, `TrangThai BIT` |
| 2 | `NhanVien` | `MaNV` | Khong co | Script SQL dung `GioiTinh NVARCHAR(5)` |
| 3 | `LuongNhanVien` | `MaLNV` | `MaNV -> NhanVien` | Luong va phu cap dang dung `FLOAT` |
| 4 | `NhaCungCap` | `MaNCC` | Khong co | Co nhieu cot mo ta dia chi/ghi chu |
| 5 | `LoaiHang` | `MaLoaiHang` | Khong co | Danh muc don gian |
| 6 | `NhomDuocLy` | `MaNDL` | Khong co | Danh muc don gian |
| 7 | `KeHang` | `MaKe` | Khong co | Danh muc don gian |
| 8 | `Thuoc_SanPham` | `MaThuoc` | `MaLoaiHang`, `MaNDL`, `ViTri` | Co `VARBINARY(MAX)` cho hinh anh |
| 9 | `PhieuNhap` | `MaPN` | `MaNCC`, `MaNV` | Co `TrangThai BIT` |
| 10 | `ChiTietPhieuNhap` | `(MaPN, MaThuoc, MaLH)` | `MaPN`, `MaThuoc` | `MaDVT` khong duoc FK trong script cu |
| 11 | `Thuoc_SP_TheoLo` | `MaLH` | `(MaPN, MaThuoc, MaLH) -> ChiTietPhieuNhap` | Ton kho, lo, HSD, NSX |
| 12 | `DonViTinh` | `MaDVT` | Khong co | Danh muc don vi |
| 13 | `ChiTietDonViTinh` | `(MaThuoc, MaDVT)` | `MaThuoc`, `MaDVT` | He so quy doi, gia nhap, gia ban |
| 14 | `HoaDon` | `MaHD` | `MaKH`, `MaNV` | Script SQL dung `TrangThai NVARCHAR(10)` |
| 15 | `ChiTietHoaDon` | `(MaHD, MaLH, MaDVT)` | `MaHD`, `MaLH` | `MaDVT` khong duoc FK trong script cu |
| 16 | `HoatDong` | `ID` | `MaNV` | `ID IDENTITY`, `MaHDong` computed, `NoiDung NVARCHAR(MAX)` |
| 17 | `PhieuDatHang` | `MaPDat` | `MaKH`, `MaNV` | `TrangThai INT DEFAULT 0` |
| 18 | `ChiTietPhieuDatHang` | `(MaPDat, MaThuoc, MaDVT)` | `MaPDat`, `MaThuoc` | `MaDVT` khong duoc FK trong script cu |
| 19 | `PhieuDoiHang` | `MaPD` | `MaNV`, `MaKH`, `MaHD` | Phieu doi gan voi hoa don |
| 20 | `ChiTietPhieuDoiHang` | `(MaLH, MaPD, MaThuoc, MaDVT)` | `MaLH`, `MaPD`, `MaThuoc`, `MaDVT` | `SoLuong` co the am/duong theo nghiep vu |
| 21 | `PhieuTraHang` | `MaPT` | `MaNV`, `MaHD`, `MaKH` | Phieu tra gan voi hoa don |
| 22 | `ChiTietPhieuTraHang` | `(MaLH, MaPT, MaThuoc, MaDVT)` | `MaLH`, `MaPT`, `MaThuoc`, `MaDVT` | Gia/giam gia dang dung `FLOAT` |
| 23 | `HoatChat` | `MaHoatChat` | Khong co | Danh muc hoat chat |
| 24 | `ChiTietHoatChat` | `(MaHoatChat, MaThuoc)` | `MaHoatChat`, `MaThuoc` | `HamLuong FLOAT` |
| 25 | `LoaiKhuyenMai` | `MaLoai` | Khong co | Danh muc don gian |
| 26 | `KhuyenMai` | `MaKM` | `MaLoai` | `NgayTao DEFAULT GETDATE()` |
| 27 | `ChiTietKhuyenMai` | `(MaThuoc, MaKM)` | `MaThuoc`, `MaKM` | Gan thuoc voi chuong trinh KM |
| 28 | `Thuoc_SP_TangKem` | `(MaKM, MaThuocTangKem)` | `MaKM`, `MaThuocTangKem` | Quan he tang kem |
| 29 | `ThongSoUngDung` | `TenThongSo` | Khong co | Luu tham so VAT / ngay canh bao HSD |

## Danh sach khoa ngoai chi tiet

- `LuongNhanVien.MaNV -> NhanVien.MaNV`
- `Thuoc_SanPham.MaLoaiHang -> LoaiHang.MaLoaiHang`
- `Thuoc_SanPham.MaNDL -> NhomDuocLy.MaNDL`
- `Thuoc_SanPham.ViTri -> KeHang.MaKe`
- `PhieuNhap.MaNCC -> NhaCungCap.MaNCC`
- `PhieuNhap.MaNV -> NhanVien.MaNV`
- `ChiTietPhieuNhap.MaPN -> PhieuNhap.MaPN`
- `ChiTietPhieuNhap.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `Thuoc_SP_TheoLo.(MaPN, MaThuoc, MaLH) -> ChiTietPhieuNhap.(MaPN, MaThuoc, MaLH)`
- `ChiTietDonViTinh.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `ChiTietDonViTinh.MaDVT -> DonViTinh.MaDVT`
- `HoaDon.MaKH -> KhachHang.MaKH`
- `HoaDon.MaNV -> NhanVien.MaNV`
- `ChiTietHoaDon.MaHD -> HoaDon.MaHD`
- `ChiTietHoaDon.MaLH -> Thuoc_SP_TheoLo.MaLH`
- `HoatDong.MaNV -> NhanVien.MaNV`
- `PhieuDatHang.MaKH -> KhachHang.MaKH`
- `PhieuDatHang.MaNV -> NhanVien.MaNV`
- `ChiTietPhieuDatHang.MaPDat -> PhieuDatHang.MaPDat`
- `ChiTietPhieuDatHang.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `PhieuDoiHang.MaNV -> NhanVien.MaNV`
- `PhieuDoiHang.MaKH -> KhachHang.MaKH`
- `PhieuDoiHang.MaHD -> HoaDon.MaHD`
- `ChiTietPhieuDoiHang.MaLH -> Thuoc_SP_TheoLo.MaLH`
- `ChiTietPhieuDoiHang.MaPD -> PhieuDoiHang.MaPD`
- `ChiTietPhieuDoiHang.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `ChiTietPhieuDoiHang.MaDVT -> DonViTinh.MaDVT`
- `PhieuTraHang.MaNV -> NhanVien.MaNV`
- `PhieuTraHang.MaHD -> HoaDon.MaHD`
- `PhieuTraHang.MaKH -> KhachHang.MaKH`
- `ChiTietPhieuTraHang.MaLH -> Thuoc_SP_TheoLo.MaLH`
- `ChiTietPhieuTraHang.MaPT -> PhieuTraHang.MaPT`
- `ChiTietPhieuTraHang.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `ChiTietPhieuTraHang.MaDVT -> DonViTinh.MaDVT`
- `ChiTietHoatChat.MaHoatChat -> HoatChat.MaHoatChat`
- `ChiTietHoatChat.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `KhuyenMai.MaLoai -> LoaiKhuyenMai.MaLoai`
- `ChiTietKhuyenMai.MaThuoc -> Thuoc_SanPham.MaThuoc`
- `ChiTietKhuyenMai.MaKM -> KhuyenMai.MaKM`
- `Thuoc_SP_TangKem.MaKM -> KhuyenMai.MaKM`
- `Thuoc_SP_TangKem.MaThuocTangKem -> Thuoc_SanPham.MaThuoc`

## Danh sach cot / kieu du lieu can chu y

### Cot dung `NVARCHAR` / `NVARCHAR(MAX)`

- `KhachHang.TenKH`, `KhachHang.DiaChi`
- `NhanVien.TenNV`, `NhanVien.GioiTinh`, `NhanVien.DiaChi`, `NhanVien.VaiTro`
- `LuongNhanVien.GhiChu`
- `NhaCungCap.TenNCC`, `NhaCungCap.DiaChi`, `NhaCungCap.GhiChu`, `NhaCungCap.TenCongTy`
- `LoaiHang.TenLH`, `LoaiHang.MoTa`
- `NhomDuocLy.TenNDL`, `NhomDuocLy.MoTa`
- `KeHang.TenKe`, `KeHang.MoTa`
- `Thuoc_SanPham.TenThuoc`, `Thuoc_SanPham.DuongDung`, `Thuoc_SanPham.QuyCachDongGoi`, `Thuoc_SanPham.HangSX`, `Thuoc_SanPham.NuocSX`
- `PhieuNhap.GhiChu`
- `HoaDon.TrangThai`
- `HoatDong.LoaiHD`, `HoatDong.BangDL`, `HoatDong.NoiDung` (`NVARCHAR(MAX)`)
- `PhieuDatHang.GhiChu`
- `PhieuDoiHang.GhiChu`
- `ChiTietPhieuDoiHang.LyDoDoi`
- `PhieuTraHang.GhiChu`
- `ChiTietPhieuTraHang.LyDoTra`
- `HoatChat.TenHoatChat`
- `LoaiKhuyenMai.TenLoai`, `LoaiKhuyenMai.MoTa`
- `KhuyenMai.TenKM`, `KhuyenMai.MoTa`

### Cot dung `NCHAR` / `NTEXT`

- `NCHAR`: khong tim thay trong DDL bang.
- `NTEXT`: khong tim thay trong DDL bang.

### Cot dung `BIT`

- `KhachHang.GioiTinh`
- `KhachHang.TrangThai`
- `NhanVien.TrangThai`
- `NhanVien.TrangThaiXoa`
- `Thuoc_SanPham.TrangThaiXoa`
- `Thuoc_SanPham.ETC`
- `PhieuNhap.TrangThai`
- `ChiTietDonViTinh.DonViCoBan`
- `ChiTietPhieuDatHang.TrangThai`

Ghi chu:

- Procedure cung dung them `BIT` cho tham so `@GioiTinh`, `@TrangThai`.

### Cot dung `IDENTITY`

- `HoatDong.ID`

### Cot dung `GETDATE()` trong DDL

- `HoatDong.ThoiGian DEFAULT GETDATE()`
- `KhuyenMai.NgayTao DEFAULT GETDATE()`

### Cot dung `VARBINARY(MAX)` / `NVARCHAR(MAX)`

- `Thuoc_SanPham.HinhAnh -> VARBINARY(MAX)`
- `HoatDong.NoiDung -> NVARCHAR(MAX)`

## Trigger / stored procedure / function hien co

### Trigger

| STT | Trigger | Loai | Bang lien quan | Ghi chu |
|---|---|---|---|---|
| 1 | `trg_ThuocSanPham_Audit` | Audit/log | `Thuoc_SanPham`, `HoatDong` | Dung `CONTEXT_INFO`, `TOP 1`, `FULL JOIN` |
| 2 | `trg_ChiTietHoatChat_Audit` | Audit/log | `ChiTietHoatChat`, `HoatDong` | Dung `CONTEXT_INFO`, `STRING_AGG` |
| 3 | `trg_ThuocSPTheoLo_Audit` | Audit/log | `Thuoc_SP_TheoLo`, `HoatDong` | Dung `CONTEXT_INFO`, `FORMAT`, `STRING_AGG` |
| 4 | `trg_ChiTietDonViTinh_Audit` | Audit/log | `ChiTietDonViTinh`, `HoatDong` | Dung `CONTEXT_INFO`, `FORMAT`, `STRING_AGG` |
| 5 | `trg_CapNhatTrangThaiDatHang` | Nghiep vu ton kho/dat hang | `Thuoc_SP_TheoLo`, `ChiTietPhieuDatHang`, `PhieuDatHang` | Goi `EXEC sp_CapNhatTrangThaiDatHang` |
| 6 | `trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT` | Nghiep vu dat hang | `ChiTietPhieuDatHang`, `PhieuDatHang`, `Thuoc_SP_TheoLo` | Dung cursor, cap nhat trang thai |

### Stored procedure

#### Nhom sinh ma / tao du lieu

- `sp_InsertNhanVien`
- `sp_InsertThuoc_SanPham`

#### Nhom thong ke / bao cao

- `sp_ThongKeBanHang_HomNay`
- `sp_ThongKeBanHang_TuanNay`
- `sp_ThongKeBanHang_ThangNay`
- `sp_ThongKeBanHang_NamNay`
- `sp_ThongKeBanHang_TuyChon`
- `sp_Top5SanPham_HomNay`
- `sp_Top5SanPham_TuanNay`
- `sp_Top5SanPham_ThangNay`
- `sp_Top5SanPham_NamNay`
- `sp_Top5SanPham_TuyChon`
- `sp_ThongKeThuocHetHan`
- `sp_ThongKeXNT`
- `sp_HangHetHan`
- `sp_HangSapHetHan`
- `sp_GetHoaDonTheoThoiGian`
- `sp_GetHoaDonTheoTuyChon`

#### Nhom nghiep vu giao dich / ton kho

- `sp_LuuPhieuNhap`
- `sp_CapNhatTrangThaiDatHang`
- `sp_DuyetPhieuDatHang`

### Function

- Khong tim thay `CREATE FUNCTION` trong script hien tai.

## Diem phu thuoc SQL Server-specific

| Thanh phan | Phat hien | Vi du | Anh huong |
|---|---|---|---|
| `GO` | Co | Nhieu doan trong file | Bo trong MariaDB |
| `IDENTITY` | Co | `HoatDong.ID` | Chuyen sang `AUTO_INCREMENT` |
| `GETDATE()` | Co | default va procedure thong ke | Chuyen sang `CURRENT_TIMESTAMP` / `CURDATE()` / `NOW()` |
| `TOP n` | Co | top 5 san pham, trigger log | Chuyen sang `LIMIT n` sau nay |
| `ISNULL()` | Co | trigger/procedure thong ke | Chuyen sang `COALESCE()` hoac `IFNULL()` |
| `CONVERT(date, ...)` | Co | thong ke doanh thu, XNT | Chuyen sang `DATE(...)` |
| `DATEPART()` | Co | thong ke tuan/thang/nam | Chuyen sang `YEAR()`, `MONTH()`, `DAY()` |
| `FORMAT()` | Co | trigger log va thong ke | Can doi sang `DATE_FORMAT()` / `FORMAT()` MariaDB co hanh vi khac |
| `STRING_AGG()` | Co | trigger audit | Can doi sang `GROUP_CONCAT()` neu con muon DB-side aggregation |
| `FULL OUTER JOIN` | Co | procedure thong ke doanh thu | MariaDB khong ho tro truc tiep |
| `SET DATEFIRST` | Co | thong ke tuan | MariaDB khong co tuong duong truc tiep |
| `DATEFROMPARTS`, `EOMONTH` | Co | procedure doanh thu nam/thang | Can doi sang ham MariaDB khac |
| `TRY_CAST()` | Co | thong ke HSD, sinh ma thuoc | MariaDB can xu ly khac |
| `WITH (TABLOCKX)` | Co | `sp_InsertThuoc_SanPham` | Khong co trong MariaDB |
| `OPENROWSET(BULK...)` | Co | nap anh thuoc | Khong chuyen vao schema MariaDB |
| `sp_executesql` | Co | dynamic SQL nap anh | Khong chuyen vao schema MariaDB |
| `CONTEXT_INFO()` | Co | trigger audit, `sp_LuuPhieuNhap` | Khong chuyen truc tiep |
| `CREATE OR ALTER` | Co | trigger/procedure | Cu phap SQL Server-specific |

## Rui ro khi chuyen sang MariaDB

### Rui ro cao

- `CONTEXT_INFO` dang duoc dung de truyen nguoi thao tac vao trigger audit.
- Trigger audit dang chen vao `HoatDong` va noi chuoi noi dung chi tiet.
- `sp_LuuPhieuNhap` vua luu phieu, vua chi tiet, vua cap nhat ton, vua set/bat context trigger.
- `sp_DuyetPhieuDatHang` dung FEFO + cursor + giu hang theo lo.
- `sp_ThongKeXNT` co nhieu CTE, quy doi don vi, tong hop nhap/xuat/ton.
- Nhieu procedure thong ke dung `FULL OUTER JOIN`, `FORMAT`, `DATEPART`, `CONVERT`.

### Rui ro trung binh

- Hinh anh thuoc dang duoc nap bang `OPENROWSET(BULK...)`.
- Mot so cot tien te dang dung `FLOAT`, can chuyen sang `DECIMAL`.
- Nhieu bang chi tiet chua FK day du voi `DonViTinh` trong script SQL cu.

### Rui ro dac biet can review

- `NhanVien.GioiTinh`: script SQL la `NVARCHAR(5)` nhung Java DAO/model dang dung boolean.
- `HoaDon.TrangThai`: script SQL la `NVARCHAR(10)` nhung Java DAO/model dang dung boolean.
- `ChiTietPhieuDoiHang.SoLuong`: procedure `sp_ThongKeXNT` cho thay cot nay co the mang y nghia am/duong tuy loai doi hang.

## Cach xu ly trong `schema_mariadb.sql`

### Nguyen tac chung

- Giu ten bang/cot sat schema cu de giam ma sat cho buoc map JPA.
- Chuyen type va cu phap sang MariaDB mot cach ro rang.
- Khong port trigger/procedure SQL Server 1:1 trong buoc nay.
- Giu file SQL la nguon schema ro rang, khong phu thuoc `hibernate.hbm2ddl.auto=create`.

### Quyet dinh thiet ke chinh

- Database dich: `quan_ly_nha_thuoc`
- Charset/collation: `utf8mb4` + `utf8mb4_unicode_ci`
- Engine: `InnoDB`
- Co `DROP TABLE IF EXISTS` + `SET FOREIGN_KEY_CHECKS = 0/1` de ho tro moi truong dev
- Tiep tuc dung khoa chinh dang chuoi nghiep vu nhu `NV001`, `TS001`, `HD0001`, `PN001`
- Khong doi ma nghiep vu dang chuoi sang `AUTO_INCREMENT`
- `HoatDong.ID` chuyen thanh `AUTO_INCREMENT`
- `HoatDong.MaHDong` chuyen thanh generated stored column cua MariaDB
- Cot tien/so tien chuyen tu `FLOAT` sang `DECIMAL(18,2)` o cac bang nghiep vu
- `ChiTietDonViTinh.HeSoQuyDoi` chuyen sang `DECIMAL(18,4)`
- `Thuoc_SanPham.HinhAnh` chuyen sang `MEDIUMBLOB`
- `HoatDong.NoiDung` chuyen sang `LONGTEXT`

### Cac cot duoc chon theo huong "giam ma sat voi Java"

- `NhanVien.GioiTinh` trong schema MariaDB duoc chon la `TINYINT(1)` thay vi `NVARCHAR(5)`
- `HoaDon.TrangThai` trong schema MariaDB duoc chon la `TINYINT(1)` thay vi `NVARCHAR(10)`

Ly do:

- Java model/DAO hien tai dang doc/ghi hai cot nay theo `boolean`.
- Day la mismatch da ton tai giua SQL script va Java code.
- Schema moi uu tien lam nen cho JPA/Hibernate va giam vo mapping ve sau.
- Tuy nhien, day van la diem can review thu cong truoc khi chuyen nghiep vu toan bo.

## File da tao trong buoc nay

- [schema_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/schema_mariadb.sql)
- [seed_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/seed_mariadb.sql)

## Script seed du lieu mau

File [seed_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/seed_mariadb.sql) chu y vao:

- Tai khoan/nhan vien dang nhap:
  - `admin / 123`
  - `staff / 123`
- Khach hang mau
- Nha cung cap mau
- Loai hang / nhom duoc ly / ke hang / don vi tinh
- Thuoc mau
- He so quy doi / gia nhap / gia ban
- Phieu nhap va ton kho theo lo
- 1 hoa don mau
- 1 phieu dat hang mau
- 1 so khuyen mai mau

Chu y:

- Mat khau dang de plain text de khop logic dang nhap do an hien tai.
- Chua seed bang doi/tra de tranh khoa cung nghiep vu khi chua xac nhan domain.

## Mapping SQL Server -> MariaDB

| STT | Thanh phan SQL Server | Cach xu ly MariaDB | File/Bang lien quan | Trang thai | Ghi chu |
|---|---|---|---|---|---|
| 1 | `NVARCHAR(n)` | `VARCHAR(n)` voi `utf8mb4` | Nhieu bang | Da chuyen | Ho tro tieng Viet o muc schema |
| 2 | `NVARCHAR(MAX)` | `LONGTEXT` | `HoatDong.NoiDung` | Da chuyen | Noi dung log dai |
| 3 | `VARBINARY(MAX)` | `MEDIUMBLOB` | `Thuoc_SanPham.HinhAnh` | Da chuyen | Du cho anh thuoc co kich thuoc > 64KB |
| 4 | `IDENTITY(1,1)` | `AUTO_INCREMENT` | `HoatDong.ID` | Da chuyen | Giữ `MaHDong` la generated column |
| 5 | Cot chuoi ma nghiep vu | Giu `VARCHAR`, khong doi sang so tu tang | `MaNV`, `MaThuoc`, `MaHD`, `MaPN`, ... | Da chuyen | Trach gay vo nghiep vu hien tai |
| 6 | `GETDATE()` | `CURRENT_TIMESTAMP` / date function MariaDB | `HoatDong`, `KhuyenMai` | Da chuyen mot phan | Query/procedure se xu ly sau |
| 7 | `BIT` | `TINYINT(1)` | Nhieu bang | Da chuyen | Map tot hon voi Java boolean |
| 8 | `FLOAT` cho tien | `DECIMAL(18,2)` | Luong, gia, giam gia, tien coc | Da chuyen | Giam sai so so hoc |
| 9 | `FLOAT` cho he so | `DECIMAL(18,4)` | `ChiTietDonViTinh.HeSoQuyDoi` | Da chuyen | Phu hop quy doi don vi |
| 10 | `TOP n` | Chua port vao schema | Trigger/procedure | Can xu ly sau | Se doi sang `LIMIT n` neu con giu query DB-side |
| 11 | `CONVERT(date, col)` | Chua port vao schema | Procedure thong ke | Can xu ly sau | Se doi sang `DATE(col)` |
| 12 | `DATEPART(...)` | Chua port vao schema | Procedure thong ke | Can xu ly sau | Se doi sang `YEAR/MONTH/DAY` |
| 13 | `ISNULL()` | Chua port vao schema | Trigger/procedure | Can xu ly sau | Se doi sang `COALESCE()` hoac `IFNULL()` |
| 14 | `GO` | Bo khoi script MariaDB | Toan file SQL Server cu | Da xu ly | Khong con trong `schema_mariadb.sql` |
| 15 | `CONTEXT_INFO()` | Khong chuyen truc tiep | Trigger audit, `sp_LuuPhieuNhap` | Can thiet ke lai | Chuyen audit/user context len service layer |
| 16 | Trigger audit dung `CONTEXT_INFO` | Khong port 1:1 | `trg_*_Audit` | Can thiet ke lai | Nen xu ly bang `UserContext` + service audit |
| 17 | Stored procedure nghiep vu | Ghi nhan, chua port | `sp_LuuPhieuNhap`, `sp_DuyetPhieuDatHang` | Can thiet ke lai | Nen dua dan len service layer |
| 18 | Stored procedure thong ke | Ghi nhan, chua port | `sp_ThongKe*`, `sp_GetHoaDon*` | Can thiet ke lai | Sau nay doi sang repository query / service |
| 19 | `OPENROWSET(BULK...)` | Khong port | Nhap anh thuoc | Khong chuyen | Nen tach thanh step import du lieu rieng |
| 20 | `sp_executesql` | Khong port | Dynamic SQL nap anh | Khong chuyen | Khong phai schema can ban |
| 21 | `WITH (TABLOCKX)` | Khong port | `sp_InsertThuoc_SanPham` | Can thiet ke lai | Logic sinh ma can chuyen len service |
| 22 | `MAX(code) + 1` | Giu cot chuoi, danh dau logic can doi | `sp_InsertNhanVien`, `sp_InsertThuoc_SanPham` | Can thiet ke lai | Nguy hiem trong moi truong nhieu client |

## Cac diem can review thu cong

- `NhanVien.GioiTinh` dang lech giua SQL script (chuoi `Nam/Nu`) va Java model (boolean).
- `HoaDon.TrangThai` dang lech giua SQL script (chuoi `Hoan tat`) va Java model (boolean).
- `ChiTietPhieuDoiHang.SoLuong` co the mang y nghia am/duong theo nghiep vu doi hang.
- Cac cot tien dang la `FLOAT` trong script cu can duoc xac nhan lai nghia chinh xac:
  - la so tien
  - la don gia tren moi don vi
  - hay la ty le phan tram
- `ChiTietPhieuNhap.Thue` da duoc chon `DECIMAL(5,4)` trong schema MariaDB vi co dau hieu la thue suat, can review them.
- Cac bang chi tiet co `MaDVT` chua duoc FK day du trong script SQL cu:
  - `ChiTietPhieuNhap`
  - `ChiTietHoaDon`
  - `ChiTietPhieuDatHang`
- Trigger audit khong chuyen truc tiep sang MariaDB.
- Trigger cap nhat trang thai dat hang khong chuyen truc tiep sang MariaDB.
- Procedure `sp_LuuPhieuNhap` khong chuyen truc tiep, can service layer transaction-safe thay the.
- Procedure thong ke dang dung `TOP`, `CONVERT`, `DATEPART`, `FORMAT`, `FULL OUTER JOIN`.
- Logic sinh ma chuoi `NV001`, `TS001` bang `MAX + 1` can thay the o tang service.
- Seed du lieu tai khoan/mat khau dang plain text, can xac nhan truoc khi dua vao moi truong nghiem tuc.
- Collation tieng Viet da chon `utf8mb4_unicode_ci` de tuong thich rong, neu moi truong MariaDB co `utf8mb4_vietnamese_ci` on dinh thi co the review sau.

## Ghi chu quan trong cho Hibernate / JPA

- Khong nen dua hoan toan vao `hibernate.hbm2ddl.auto=create` de tao schema chinh.
- File [schema_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/schema_mariadb.sql) la nguon schema ro rang trong giai doan chuyen doi.
- Trong moi truong hoc tap/dev co the can nhac `hibernate.hbm2ddl.auto=validate` hoac `update` tam thoi.
- Khi mapping JPA on dinh, nen uu tien `validate` schema thay vi de Hibernate tu tao "lung tung".
- Neu can quan ly migration bai ban hon o buoc sau, co the can nhac Flyway hoac Liquibase.

## Huong dan chay script

Neu dung MySQL client:

```powershell
mysql -u root -p < SQL/schema_mariadb.sql
mysql -u root -p quan_ly_nha_thuoc < SQL/seed_mariadb.sql
```

Neu dung MariaDB client:

```powershell
mariadb -u root -p < SQL/schema_mariadb.sql
mariadb -u root -p quan_ly_nha_thuoc < SQL/seed_mariadb.sql
```

## Tinh trang kiem tra script

- Da kiem tra static tren file SQL moi tao.
- Chua chay thuc te tren MariaDB/MySQL vi moi truong hien tai khong co `mysql` hoac `mariadb` client.
- Canh bao nay can duoc giu ro cho buoc tiep theo de tranh hieu nham la da test runtime.

## Checklist kiem tra schema MariaDB

- [x] Tat ca bang SQL Server da co ban MariaDB tuong ung
- [x] Tat ca khoa chinh da duoc chuyen
- [x] Tat ca khoa ngoai da duoc kiem tra
- [x] `NVARCHAR` / `NVARCHAR(MAX)` da duoc chuyen sang kieu ho tro `utf8mb4`
- [x] `IDENTITY` da duoc chuyen sang `AUTO_INCREMENT` neu phu hop
- [x] `GETDATE()` da duoc doi sang `CURRENT_TIMESTAMP` o muc DDL
- [x] Cot tien chinh da duoc chuyen sang `DECIMAL`
- [x] `BIT` da duoc chuyen sang `TINYINT(1)`
- [x] `CONTEXT_INFO` khong con trong schema MariaDB moi
- [x] `GO` khong con trong script MariaDB moi
- [x] Trigger / procedure da duoc phan loai
- [x] Seed data duoc sap theo thu tu khoa ngoai
- [x] Co script schema rieng va seed rieng
- [x] Cac diem rui ro da duoc ghi chu
- [ ] Script da duoc chay thuc te tren MariaDB

