# STEP 8 - Thiet Ke Chien Luoc Thay Trigger Va Stored Procedure

## Muc tieu buoc 8

Buoc nay tap trung phan tich toan bo trigger, stored procedure va function trong SQL Server hien tai de quyet dinh:

- Logic nao nen giu o database.
- Logic nao phai chuyen len service layer.
- Logic nao phai chuyen thanh transaction service.
- Logic sinh ma nghiep vu nen thay the bang co che nao de an toan voi nhieu client.
- Logic thong ke nao se duoc viet lai bang repository query / native SQL / view sau nay.

Pham vi buoc nay chi la phan tich va thiet ke chien luoc. Khong co rewrite nghiep vu lon, khong co chuyen DAO sang JPA, khong co sua code Java hang loat.

## Co so phan tich

Da doc va doi chieu:

- [QuanLyNhaThuoc.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/QuanLyNhaThuoc.sql)
- [STEP_7_MARIADB_SCHEMA_DESIGN.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md)
- [schema_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/schema_mariadb.sql)

## Vi sao khong nen be nguyen trigger SQL Server sang MariaDB

- Trigger audit hien tai phu thuoc vao `CONTEXT_INFO`, trong kien truc moi khong con hop le.
- Trigger nghiep vu dang am tham thay doi ton kho, trang thai dat hang va log, khien service khong biet DB da tu lam gi.
- Stored procedure thong ke dang dung nhieu cu phap SQL Server-specific nhu `TOP`, `DATEPART`, `CONVERT`, `FORMAT`, `FULL OUTER JOIN`, `SET DATEFIRST`.
- Logic sinh ma bang `MAX(code) + 1` khong an toan khi nhieu client cung goi vao server.
- Trigger/procedure phuc tap kho debug, kho test tu service va kho mapping sang JPA/Hibernate sau nay.

Ket luan thiet ke:

- Database moi chi nen giu logic bao ve toan ven du lieu co ban.
- Nghiep vu chinh phai nam o server service.
- Audit can biet user/request phai nam o service, khong o trigger.
- Tinh toan ton kho / giu hang / lap hoa don / tao phieu nhap phai nam trong transaction service.

## Tong hop nhanh

- So trigger: `6`
- So stored procedure: `21`
- So function: `0`
- So logic audit/log: `4`
- So logic sinh ma nghiep vu ro rang: `2`
- So logic cap nhat ton kho / dat hang / giao dich can transaction: `5`
- So logic thong ke / bao cao / query tong hop: `16`
- So logic phu thuoc `CONTEXT_INFO`: `5`

## Bang kiem ke trigger / procedure / function

| STT | Ten | Loai | Bang/Doi tuong lien quan | Su kien/Tham so | Logic hien tai | Nhom logic | Phu thuoc SQL Server | Quyet dinh de xuat | Rui ro | Ghi chu |
|---|---|---|---|---|---|---|---|---|---|---|
| 1 | `trg_ThuocSanPham_Audit` | Trigger | `Thuoc_SanPham`, `HoatDong` | `AFTER INSERT, UPDATE, DELETE` | Ghi log them/sua/xoa thuoc vao `HoatDong` | Audit/Log | `CONTEXT_INFO`, `inserted`, `deleted`, `TOP`, `FULL JOIN` | Chuyen len service layer | Cao | Can `UserContext`; khong nen dung trigger de doan user |
| 2 | `trg_ChiTietHoatChat_Audit` | Trigger | `ChiTietHoatChat`, `HoatDong`, `Thuoc_SanPham` | `AFTER INSERT, UPDATE, DELETE` | Ghi/chen tiep log thay doi hoat chat cua thuoc | Audit/Log | `CONTEXT_INFO`, `STRING_AGG`, `TOP`, `inserted`, `deleted` | Chuyen len service layer | Cao | Nen de `ThuocCatalogService` + `AuditService` phoi hop |
| 3 | `trg_ThuocSPTheoLo_Audit` | Trigger | `Thuoc_SP_TheoLo`, `HoatDong` | `AFTER INSERT, UPDATE, DELETE` | Ghi log thay doi ton kho theo lo | Audit/Log | `CONTEXT_INFO`, `STRING_AGG`, `FORMAT`, `inserted`, `deleted` | Chuyen len service layer | Cao | Co lien quan ton kho, can log trong cung transaction nghiep vu |
| 4 | `trg_ChiTietDonViTinh_Audit` | Trigger | `ChiTietDonViTinh`, `DonViTinh`, `HoatDong` | `AFTER INSERT, UPDATE, DELETE` | Ghi log thay doi gia ban / he so quy doi theo don vi tinh | Audit/Log | `CONTEXT_INFO`, `FULL JOIN`, `STRING_AGG`, `FORMAT`, `inserted`, `deleted` | Chuyen len service layer | Cao | Nen de `ThuocCatalogService` hoac `PricingService` xu ly |
| 5 | `trg_CapNhatTrangThaiDatHang` | Trigger | `Thuoc_SP_TheoLo`, `ChiTietPhieuDatHang`, `PhieuDatHang` | `AFTER INSERT, UPDATE` | Moi khi ton kho lo thay doi, goi proc de cap nhat trang thai chi tiet dat hang va phieu dat hang | Cap nhat ton kho | `inserted`, `EXEC`, cursor | Chuyen thanh transaction trong service | Cao | Trigger dang gay side effect an; service can chu dong goi |
| 6 | `trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT` | Trigger | `ChiTietPhieuDatHang`, `PhieuDatHang`, `Thuoc_SP_TheoLo` | `AFTER INSERT` | Sau khi them chi tiet phieu dat, tu cap nhat trang thai du hang/khong du hang | Dong bo du lieu phu | `inserted`, cursor | Chuyen thanh transaction trong service | Cao | Nen la internal method trong `DatHangService` |
| 7 | `sp_InsertNhanVien` | Stored procedure | `NhanVien` | `@HoTen, @SDT, @Email, @NamSinh, @GioiTinh, ...` | Sinh `MaNV` bang `MAX(MaNV) + 1`, sau do insert nhan vien | Sinh ma nghiep vu | `LEN`, `SUBSTRING`, `MAX`, SQL Server string handling | Chuyen len service layer | Cao | Phai thay bang `CodeGenerationService` an toan |
| 8 | `sp_ThongKeBanHang_HomNay` | Stored procedure | `HoaDon`, `ChiTietHoaDon`, `PhieuTraHang`, `ChiTietPhieuTraHang` | Khong tham so | Tinh doanh thu hom nay theo gio, tru gia tri don tra, cong VAT 5% | Thong ke/Bao cao | `GETDATE`, `CONVERT`, `FORMAT`, `ISNULL`, `FULL OUTER JOIN` | Chuyen thanh repository query | Trung binh | Co the dung native SQL hoac view/report query |
| 9 | `sp_ThongKeBanHang_TuanNay` | Stored procedure | Giong tren | Khong tham so | Doanh thu tuan nay theo ngay | Thong ke/Bao cao | `SET DATEFIRST`, `DATEPART`, `CONVERT`, `FORMAT`, `FULL OUTER JOIN`, `ISNULL` | Chuyen thanh repository query | Trung binh | Can viet lai cach tinh tuan cho MariaDB |
| 10 | `sp_ThongKeBanHang_ThangNay` | Stored procedure | Giong tren | Khong tham so | Doanh thu thang nay theo ngay | Thong ke/Bao cao | `DATEPART`, `GETDATE`, `CONVERT`, `FORMAT`, `FULL OUTER JOIN` | Chuyen thanh repository query | Trung binh | Co the de trong `ThongKeService` |
| 11 | `sp_ThongKeBanHang_NamNay` | Stored procedure | Giong tren | Khong tham so | Doanh thu nam nay theo thang | Thong ke/Bao cao | `DATEPART`, `DATEFROMPARTS`, `FORMAT`, `FULL OUTER JOIN` | Chuyen thanh repository query | Trung binh | Co the can native SQL ro rang hon JPQL |
| 12 | `sp_ThongKeBanHang_TuyChon` | Stored procedure | Giong tren | `@NgayBatDau, @NgayKetThuc` | Doanh thu theo khoang ngay tuy chon | Thong ke/Bao cao | `CONVERT`, `FORMAT`, `FULL OUTER JOIN`, `ISNULL` | Chuyen thanh repository query | Trung binh | Nen de `ThongKeService.getDoanhThuTheoKhoang(...)` |
| 13 | `sp_Top5SanPham_HomNay` | Stored procedure | `HoaDon`, `ChiTietHoaDon`, `Thuoc_SP_TheoLo`, `Thuoc_SanPham` | Khong tham so | Lay top 5 san pham ban chay hom nay | Thong ke/Bao cao | `TOP`, `CONVERT`, `GETDATE` | Chuyen thanh repository query | Trung binh | Co the la native SQL don gian |
| 14 | `sp_Top5SanPham_TuanNay` | Stored procedure | Giong tren | Khong tham so | Lay top 5 san pham ban chay tuan nay | Thong ke/Bao cao | `TOP`, `SET DATEFIRST`, `DATEPART`, `CONVERT` | Chuyen thanh repository query | Trung binh | Can viet lai logic week boundary |
| 15 | `sp_Top5SanPham_ThangNay` | Stored procedure | Giong tren | Khong tham so | Lay top 5 san pham ban chay thang nay | Thong ke/Bao cao | `TOP`, `DATEPART`, `GETDATE` | Chuyen thanh repository query | Trung binh | Co the dung `LIMIT 5` sau nay |
| 16 | `sp_Top5SanPham_NamNay` | Stored procedure | Giong tren | Khong tham so | Lay top 5 san pham ban chay nam nay | Thong ke/Bao cao | `TOP`, `DATEPART`, `GETDATE` | Chuyen thanh repository query | Trung binh | Native SQL hoac report query deu duoc |
| 17 | `sp_Top5SanPham_TuyChon` | Stored procedure | Giong tren | `@NgayBatDau, @NgayKetThuc` | Lay top 5 san pham ban chay theo khoang ngay | Thong ke/Bao cao | `TOP`, `CONVERT` | Chuyen thanh repository query | Trung binh | Nen de `ReportService` |
| 18 | `sp_ThongKeThuocHetHan` | Stored procedure | `Thuoc_SP_TheoLo`, `Thuoc_SanPham`, `ThongSoUngDung` | Khong tham so | Lay thuoc het han / sap het han dua tren tham so so ngay canh bao | Thong ke/Bao cao | `TRY_CAST`, `DATEADD`, `GETDATE` | Chuyen thanh repository query | Trung binh | Query bao cao; khong can procedure DB |
| 19 | `sp_ThongKeXNT` | Stored procedure | `ChiTietPhieuNhap`, `ChiTietPhieuTraHang`, `ChiTietPhieuDoiHang`, `ChiTietHoaDon`, `ChiTietDonViTinh`, `Thuoc_SanPham`, ... | `@TuNgay, @DenNgay` | Tong hop xuat-nhap-ton theo quy doi don vi, nhieu nguon giao dich | Thong ke/Bao cao | CTE, `ISNULL`, `CONVERT`, nhieu `JOIN`, quy doi, SQL Server date funcs | Chuyen thanh repository query | Cao | Rat phuc tap; co the can native SQL hoac DB view |
| 20 | `sp_LuuPhieuNhap` | Stored procedure | `PhieuNhap`, `ChiTietPhieuNhap`, `Thuoc_SP_TheoLo`, `ChiTietDonViTinh`, `HoatDong` | `@MaPN, @NgayNhap, @MaNCC, @MaNV, @MaThuoc, @MaLH, ...` | Upsert phieu nhap, chi tiet, cong ton theo lo, cap nhat gia, set/restore `CONTEXT_INFO` de bo qua trigger | Cap nhat ton kho | `CONTEXT_INFO`, transaction T-SQL, `TRY/CATCH`, `ISNULL` | Chuyen thanh transaction trong service | Cao | Day la use case mau cho `PhieuNhapService.taoPhieuNhap(...)` |
| 21 | `sp_HangHetHan` | Stored procedure | `Thuoc_SP_TheoLo` | Khong tham so | Liet ke cac lo het han | Thong ke/Bao cao | `GETDATE`, `CAST` | Chuyen thanh repository query | Thap | Query don gian cho report/alert |
| 22 | `sp_HangSapHetHan` | Stored procedure | `Thuoc_SP_TheoLo`, `ThongSoUngDung` | Khong tham so | Liet ke lo sap het han theo tham so cai dat | Thong ke/Bao cao | `TRY_CAST`, `DATEADD`, `GETDATE` | Chuyen thanh repository query | Thap | Query don gian, co the native SQL hoac service query |
| 23 | `sp_InsertThuoc_SanPham` | Stored procedure | `Thuoc_SanPham`, `ChiTietDonViTinh` | `@TenThuoc, @HamLuong, @MaLoaiHang, @MaNDL, @ViTri, ...` | Sinh `MaThuoc` bang `MAX(TS...) + 1`, insert thuoc va auto tao don vi co ban `DVT01` | Sinh ma nghiep vu | `WITH (TABLOCKX)`, `TRY_CAST`, `SUBSTRING`, transaction | Chuyen len service layer | Cao | Nen la `ThuocCatalogService.createThuocWithBaseUnit(...)` |
| 24 | `sp_CapNhatTrangThaiDatHang` | Stored procedure | `Thuoc_SP_TheoLo`, `ChiTietPhieuDatHang`, `PhieuDatHang`, `ChiTietDonViTinh` | `@MaThuoc` | Tinh du ton, quy doi don vi, cap nhat trang thai tung dong dat hang va phieu | Dong bo du lieu phu | `ISNULL`, procedure duoc trigger goi | Chuyen thanh transaction trong service | Cao | Nen la internal method `DatHangService.recalculateAvailabilityByThuoc(...)` |
| 25 | `sp_DuyetPhieuDatHang` | Stored procedure | `ChiTietPhieuDatHang`, `Thuoc_SP_TheoLo`, `PhieuDatHang`, `ChiTietDonViTinh` | `@MaPDat` | Giu hang theo FEFO, tru `SoLuongTon`, cong `SoLuongGiu`, cap nhat trang thai phieu | Cap nhat ton kho | cursor, `GETDATE`, FEFO logic | Chuyen thanh transaction trong service | Cao | Can `InventoryService.reserveLotsFEFO(...)` |
| 26 | `sp_GetHoaDonTheoThoiGian` | Stored procedure | `HoaDon`, `ChiTietHoaDon` | `@ThoiGian` | Lay danh sach hoa don theo hom nay/tuan nay/thang nay/nam nay va tinh tong tien | Thong ke/Bao cao | `GETDATE`, `DATEFIRST`, `EOMONTH`, `DATEFROMPARTS`, `ISNULL` | Chuyen thanh repository query | Trung binh | Nen la query service cho man tim kiem hoa don |
| 27 | `sp_GetHoaDonTheoTuyChon` | Stored procedure | `HoaDon`, `ChiTietHoaDon` | `@TuNgay, @DenNgay` | Lay danh sach hoa don theo khoang ngay tuy chon va tinh tong tien | Thong ke/Bao cao | `ISNULL`, SQL Server date cast usage | Chuyen thanh repository query | Trung binh | Thich hop cho `HoaDonQueryService` |

Ghi chu:

- So function hien tai: `0`
- Khong tim thay `INSTEAD OF` trigger.

## Logic SQL khac ngoai trigger / procedure

Ngoai 27 thanh phan tren, script SQL con co 2 logic nen ghi nho:

1. Block nap hinh thuoc bang dynamic SQL:

- Dung `OPENROWSET(BULK...)` + `sp_executesql`
- Thuoc ve logic import du lieu / moi truong, khong phai nghiep vu runtime
- De xuat tach thanh script import rieng hoac tool import anh sau nay

2. `HoatDong.MaHDong` la generated code:

- Dang dung cong thuc `'HD' + RIGHT('0000' + CAST(ID AS VARCHAR(4)), 4)`
- Prefix `HD` de xuat doi lai vi dang de nham voi `HoaDon.MaHD`
- MariaDB thuc te khong cho generated column tham chieu truc tiep cot `AUTO_INCREMENT` theo cach nay
- Neu giu bang `HoatDong`, nen:
  - bo generated column o DB
  - insert ban ghi truoc de lay `ID`
  - sinh `MaHDong` o service layer va update lai
  - hoac bo display code, chi dung `ID`

## Phan loai tong the theo chien luoc thay the

### 1. Audit / log

Thanh phan:

- `trg_ThuocSanPham_Audit`
- `trg_ChiTietHoatChat_Audit`
- `trg_ThuocSPTheoLo_Audit`
- `trg_ChiTietDonViTinh_Audit`

Quyet dinh:

- Khong be nguyen sang MariaDB trigger.
- Chuyen len service layer.
- Tiep tuc co the giu bang `HoatDong` de luu log, nhung service se la noi ghi log.

Huong thiet ke:

```text
Controller/Client
-> request co user context
-> Server Service
-> xu ly nghiep vu
-> AuditService.logAction(...)
-> commit transaction
```

De xuat interface muc thiet ke:

```java
AuditService.logAction(userContext, action, entityName, entityId, description)
```

Nguyen tac:

- Audit can biet `userId` / `employeeId` / request context thi khong duoc dat trong trigger.
- Service phai biet ro khi nao log duoc tao, noi dung log la gi, va log co trong cung transaction hay la after-commit event.

### 2. Sinh ma nghiep vu

Logic phat hien ro rang trong trigger/procedure:

- `sp_InsertNhanVien` sinh `NV001`
- `sp_InsertThuoc_SanPham` sinh `TS001`

Logic sinh ma khac phat hien qua schema / du lieu mau:

- `MaKH`: `KH001`
- `MaNCC`: `NCC001`
- `MaLNV`: `LNV001`
- `MaPN`: `PN001`
- `MaHD`: `HD0001` / `HD001` dang chua thong nhat
- `MaPDat`: `PDH001`
- `MaPD`: `PD001`
- `MaPT`: `PT001`
- `MaLH` lo thuoc: `LH00001`
- `MaKM`: `KM001`
- `MaHDong`: dang de xuat doi prefix khoi `HD`

Quyet dinh:

- Khong dung `MAX(code) + 1` trong moi truong nhieu client.
- Khong de database trigger/procedure sinh ma phuc tap cho nghiep vu chinh.
- Chuyen viec sinh ma sang `CodeGenerationService`.
- Su dung sequence table + `SELECT ... FOR UPDATE` trong transaction.

De xuat file thiet ke:

- [mariadb_code_sequence_design.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/mariadb_code_sequence_design.sql)

Chien luoc de xuat theo nhom ma:

| Nhom ma | Vi du | De xuat |
|---|---|---|
| Ma giao dich chinh | `HD`, `PN`, `PDH`, `PD`, `PT` | Sinh bang `code_sequence` trong service transaction-safe |
| Ma master data co phat sinh tu UI | `NV`, `KH`, `NCC`, `TS`, `KM` | Sinh bang `code_sequence` trong service |
| Ma theo lo | `LH00001` | Sinh bang `code_sequence`, nhung can review prefix vi dang trung voi `LoaiHang` |
| Ma danh muc co tinh on dinh | `LH01` (LoaiHang), `NDL001`, `DVT01`, `KE001` | Co the seed/manual, khong nhat thiet sequence runtime |
| Ma log hoat dong | `MaHDong` | Neu giu, doi prefix sang `LOG`/`ACT`; uu tien service-generated vi MariaDB khong hop voi generated column dua tren `AUTO_INCREMENT` |

### 3. Cap nhat ton kho / giu hang / giao dich

Thanh phan:

- `sp_LuuPhieuNhap`
- `sp_CapNhatTrangThaiDatHang`
- `sp_DuyetPhieuDatHang`
- `trg_CapNhatTrangThaiDatHang`
- `trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT`

Quyet dinh:

- Tat ca nghiep vu nay phai dua ve server service co transaction ro rang.
- Khong de trigger DB am tham cap nhat ton kho/trang thai.
- Khong de client tu goi nhieu DAO le roi hy vong du lieu tu dong dong bo.

Huong thiet ke:

```text
PhieuNhapService.taoPhieuNhap(...)
  begin tx
  tao/cap nhat phieu nhap
  tao/cap nhat chi tiet phieu nhap
  cong ton theo lo
  cap nhat gia nhap/gia ban neu nghiep vu yeu cau
  ghi audit
  commit / rollback
```

```text
DatHangService.taoPhieuDatHang(...)
  begin tx
  tao phieu dat
  tao chi tiet
  recalculate availability
  ghi audit
  commit / rollback
```

```text
DatHangService.duyetPhieuDatHang(...)
  begin tx
  quy doi ve don vi co ban
  reserve lots theo FEFO
  cap nhat SoLuongTon / SoLuongGiu
  cap nhat TrangThai phieu
  ghi audit
  commit / rollback
```

### 4. Rang buoc nghiep vu

Nen giu o database:

- `PRIMARY KEY`
- `FOREIGN KEY`
- `UNIQUE`
- `NOT NULL`
- `DEFAULT CURRENT_TIMESTAMP`
- Index
- Co the them `CHECK` don gian neu version MariaDB muc tieu ho tro on dinh:
  - `SoLuong >= 0`
  - `GiaNhap >= 0`
  - `GiaBan >= 0`
  - `HSD >= NSX` neu ca hai cung not null

Nen dua len service:

- Kiem tra ton kho theo lo va FEFO
- Kiem tra quyen nhan vien
- Kiem tra khuyen mai con han
- Kiem tra hoa don hop le
- Kiem tra nghiep vu doi/tra
- Kiem tra quy trinh dat hang / giu hang
- Kiem tra audit log can biet user

### 5. Thong ke / bao cao

Thanh phan:

- `sp_ThongKeBanHang_*`
- `sp_Top5SanPham_*`
- `sp_ThongKeThuocHetHan`
- `sp_ThongKeXNT`
- `sp_HangHetHan`
- `sp_HangSapHetHan`
- `sp_GetHoaDonTheoThoiGian`
- `sp_GetHoaDonTheoTuyChon`

Quyet dinh:

- Khong bat buoc chuyen thanh stored procedure MariaDB ngay.
- Chuyen thanh `ThongKeService` / `ReportService` / `HoaDonQueryService`.
- Query don gian co the viet bang JPQL group-by sau nay.
- Query phuc tap, nhieu tong hop, hoac phu thuoc quy doi don vi nen cho phep native SQL / DB view ro rang.

De xuat phan tach:

- `ThongKeService`: doanh thu, top san pham, ton kho, han su dung
- `HoaDonQueryService`: tim hoa don theo khoang thoi gian
- `InventoryReportService`: bao cao XNT / hang het han / hang sap het han

## Logic nao nen giu o database

Nen giu:

- PK / FK / UNIQUE / NOT NULL
- Default timestamps
- Index
- Co the giu generated column don gian neu khong mang nghiep vu kho debug
  - Ngoai le: `HoatDong.MaHDong` khong nen dung generated column vi MariaDB khong hop voi cong thuc dua tren `AUTO_INCREMENT`
- Co the giu `CHECK` don gian khi phu hop

Khong nen giu trong DB duoi dang trigger/procedure nghiep vu:

- Audit/log can biet user/request
- Sinh ma nghiep vu phuc tap
- FEFO / giu hang / cap nhat ton kho giua nhieu bang
- Phan quyen
- Dong bo nghiep vu ma service khong nhin thay
- Logic bao cao phuc tap neu can su linh hoat va test tu service

## Chien luoc sinh ma nghiep vu

### Nguyen tac

- Khong dung `MAX(code) + 1` khong khoa trong moi truong nhieu client.
- Khoa ky thuat va ma nghiep vu nen tach biet neu sau nay muon nang cap schema.
- Trong giai doan chuyen doi, co the van giu ma chuoi lam PK, nhung service phai sinh ma an toan.

### Chien luoc de xuat

#### Chien luoc uu tien cho giai doan chuyen doi

- Dung bang `code_sequence`
- Mỗi `code_type` co 1 dong sequence
- Service thuc hien:
  - `SELECT ... FOR UPDATE`
  - tang `current_value`
  - format `prefix + LPAD(number, padding, '0')`
  - dung ma vua sinh trong cung transaction nghiep vu

#### Chien luoc lau dai neu duoc phep doi schema sau

- Can nhac them khoa ky thuat `BIGINT AUTO_INCREMENT`
- Ma nghiep vu tro thanh `UNIQUE` display code
- Hop voi JPA hon va giam phuc tap join/reference

### Cac diem can review tay lien quan den ma

- `HoaDon.MaHD` dang co du lieu mau `HD001`, `HD111`, `HD620`, trong khi user muc tieu muon `HD0001`
- `LoaiHang` va `LoThuoc` cung dang dung prefix `LH`, de gay nham
- `HoatDong.MaHDong` dang dung prefix `HD`, trung y nghia voi `HoaDon`

## Chien luoc audit / log

### De xuat

- Tiep tuc su dung bang `HoatDong` hoac bang audit moi.
- Bo toan bo phu thuoc `CONTEXT_INFO`.
- De service layer viet log ro rang, vi du:
  - ai thao tac
  - thao tac gi
  - tren bang/doi tuong nao
  - ma doi tuong nao
  - mo ta thay doi
  - thoi diem nao

### Muc tieu thiet ke

- `AuditService.logAction(userContext, action, entityName, entityId, description)`
- Ghi log trong cung transaction neu bat buoc phai nhat quan cung nghiep vu.
- Hoac su dung after-commit event neu uu tien giam coupling va log khong can rollback cung.

## Chien luoc cap nhat ton kho

### Nguyen tac

- Ton kho phai thay doi trong cung transaction voi nghiep vu phat sinh no.
- Service phai kiem soat ro:
  - doc ton kho
  - quy doi don vi
  - chon lo theo FEFO neu can
  - cap nhat `SoLuongTon`, `SoLuongDat`, `SoLuongGiu`
  - ghi audit
  - rollback neu co loi

### De xuat service boundary

- `PhieuNhapService.taoPhieuNhap(...)`
- `HoaDonService.lapHoaDon(...)`
- `DoiTraService.xuLyDoiTra(...)`
- `DatHangService.taoPhieuDatHang(...)`
- `DatHangService.duyetPhieuDatHang(...)`
- `InventoryService.reserveLotsFEFO(...)`
- `InventoryService.recalculateAvailabilityByThuoc(...)`

### Constraint co the giu o DB

- `SoLuongTon >= 0`
- `SoLuongDat >= 0`
- `SoLuongGiu >= 0`
- `GiaNhap >= 0`
- `GiaBan >= 0`
- `HSD >= NSX` neu du lieu duoc xac nhan

Nhung luu y:

- Constraint DB chi la lop bao ve cuoi cung.
- Nghiep vu chinh van phai kiem tra o service de bao loi de hieu cho client.

## Chien luoc thong ke / bao cao

### Nhom co the dua ve JPQL sau nay

- Bao cao danh sach hoa don theo khoang ngay
- Tong doanh thu co nhom theo ngay/thang
- Top san pham neu query khong qua phuc tap

### Nhom nen cho phep native SQL / view

- Bao cao XNT co quy doi don vi tu nhieu nguon giao dich
- Bao cao doanh thu co phep tru tra hang + cong VAT + join tong hop phuc tap
- Bao cao co yeu cau toi uu cao tren du lieu lon

### Nguyen tac goi

- Client chi goi `ThongKeService` / `ReportService`
- Service goi repository/query
- Controller khong goi stored procedure truc tiep

## Proposal service cho tung loai logic

| STT | Logic cu | Vi tri cu | Service de xuat | Method de xuat | Transaction? | Ghi chu |
|---|---|---|---|---|---|---|
| 1 | Ghi log them/sua/xoa danh muc thuoc | `trg_ThuocSanPham_Audit` | `AuditService` + `ThuocCatalogService` | `logAction(...)`, `createThuoc(...)`, `updateThuoc(...)`, `deleteThuoc(...)` | Cung transaction hoac after commit | Can `UserContext` |
| 2 | Ghi log thay doi hoat chat | `trg_ChiTietHoatChat_Audit` | `AuditService` + `ThuocCatalogService` | `updateHoatChat(...)` | Co | Ghi log sau khi cap nhat xong |
| 3 | Ghi log thay doi ton theo lo | `trg_ThuocSPTheoLo_Audit` | `AuditService` + `InventoryService` | `logAction(...)`, `adjustLotStock(...)` | Co | Log do service tao, khong do trigger |
| 4 | Ghi log thay doi gia/qui doi DVT | `trg_ChiTietDonViTinh_Audit` | `AuditService` + `PricingService`/`ThuocCatalogService` | `updateDonViTinh(...)` | Co | Co the gom vao service thuoc |
| 5 | Sinh ma nhan vien | `sp_InsertNhanVien` | `CodeGenerationService` + `NhanVienService` | `nextCode("NHAN_VIEN")`, `createNhanVien(...)` | Co | Sequence table |
| 6 | Sinh ma thuoc + tao DVT co ban | `sp_InsertThuoc_SanPham` | `CodeGenerationService` + `ThuocCatalogService` | `nextCode("THUOC")`, `createThuocWithBaseUnit(...)` | Co | Tao thuoc va DVT co ban trong cung transaction |
| 7 | Luu phieu nhap + cong ton + cap nhat gia | `sp_LuuPhieuNhap` | `PhieuNhapService` | `taoPhieuNhap(...)` | Co | Nghiep vu mau uu tien cao |
| 8 | Cap nhat trang thai dat hang theo ton kho | `sp_CapNhatTrangThaiDatHang`, `trg_CapNhatTrangThaiDatHang` | `DatHangService` + `InventoryService` | `recalculateAvailabilityByThuoc(...)` | Co | Internal method, khong expose thap |
| 9 | Duyet phieu dat hang theo FEFO | `sp_DuyetPhieuDatHang` | `DatHangService` + `InventoryService` | `duyetPhieuDatHang(...)`, `reserveLotsFEFO(...)` | Co | Rui ro cao, can test ky |
| 10 | Cap nhat trang thai phieu dat khi them chi tiet | `trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT` | `DatHangService` | `taoPhieuDatHang(...)` | Co | Service tu chu dong tinh trang thai |
| 11 | Bao cao doanh thu | `sp_ThongKeBanHang_*` | `ThongKeService` | `getDoanhThuTheoKhoang(...)`, `getDoanhThuHomNay(...)` | Khong nhat thiet | Native SQL/JPQL tuy muc do |
| 12 | Top san pham ban chay | `sp_Top5SanPham_*` | `ThongKeService` | `getTopSanPham(...)` | Khong nhat thiet | `LIMIT 5` sau nay |
| 13 | Bao cao ton kho XNT | `sp_ThongKeXNT` | `InventoryReportService` | `getXuatNhapTon(...)` | Khong nhat thiet | Nhieu kha nang native SQL/view |
| 14 | Bao cao hang het han / sap het han | `sp_HangHetHan`, `sp_HangSapHetHan`, `sp_ThongKeThuocHetHan` | `ExpiryReportService` | `getHangHetHan(...)`, `getHangSapHetHan(...)` | Khong nhat thiet | Co the tai su dung `ThongSoUngDung` |
| 15 | Tim hoa don theo thoi gian | `sp_GetHoaDonTheoThoiGian`, `sp_GetHoaDonTheoTuyChon` | `HoaDonQueryService` | `findHoaDonByPeriod(...)`, `findHoaDonByRange(...)` | Khong nhat thiet | Query phuc vu man tim kiem |

## Logic nao de xuat giu o DB

De xuat giu:

- PK, FK, UNIQUE, NOT NULL
- Default timestamps
- Index
- Co the giu generated column don gian neu da review ky y nghia
  - Ngoai le: `HoatDong.MaHDong` nen sinh o service layer
- Check constraint don gian khi can

Khong de xuat giu duoi dang trigger/procedure:

- Ca 4 trigger audit hien tai
- 2 trigger dong bo/trang thai dat hang
- 3 procedure nghiep vu ton kho/dat hang chinh
- 2 procedure sinh ma hien tai

## Viec can lam o cac buoc sau

1. Chot `CodeGenerationService` va quy uoc prefix/padding cho tung ma nghiep vu.
2. Chot co dung `HoatDong` hien tai hay doi sang bang audit moi.
3. Refactor `PhieuNhap` thanh use case transaction service dau tien trong server.
4. Thiet ke `DatHangService` va `InventoryService` cho logic FEFO / reserve stock.
5. Chot strategy cho `HoaDon.TrangThai` va `NhanVien.GioiTinh` truoc khi map JPA.
6. Chon query nao se la JPQL, query nao se la native SQL, query nao nen dua vao DB view.
7. Kiem thu thu cong nghiep vu ton kho/hang dat/doi-tra sau moi lan chuyen logic.

## Checklist hoan thanh buoc 8

- [x] Da quet toan bo file SQL Server trong thu muc `SQL`
- [x] Da liet ke toan bo trigger
- [x] Da liet ke toan bo stored procedure
- [x] Da liet ke toan bo function
- [x] Da phan loai audit/log
- [x] Da phan loai sinh ma nghiep vu
- [x] Da phan loai cap nhat ton kho
- [x] Da phan loai rang buoc nghiep vu
- [x] Da phan loai thong ke/bao cao
- [x] Da danh dau logic phu thuoc `CONTEXT_INFO`
- [x] Da quyet dinh logic nao chuyen len service
- [x] Da quyet dinh logic nao giu o DB
- [x] Da de xuat chien luoc sinh ma an toan
- [x] Da de xuat transaction service cho ton kho / hoa don / phieu nhap / dat hang
- [x] Da ghi cac diem can review thu cong
