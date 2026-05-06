# STEP 8 - Implementation Result

## Muc tieu trien khai

Buoc nay trien khai that mot phan chien luoc thay trigger / stored procedure / function da duoc chot o buoc 8, theo huong:

- Nghiep vu chinh dua len service layer va transaction service.
- Audit khong dung `CONTEXT_INFO`.
- Sinh ma nghiep vu khong dung `MAX(code) + 1` khong khoa.
- Query thong ke dung cu phap MariaDB thay vi stored procedure SQL Server.
- Khong sua UI/FXML hang loat.
- Khong chuyen JPA dai tra.

## Tai lieu va script da doc

- [STEP_7_MARIADB_SCHEMA_DESIGN.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md)
- [STEP_8_TRIGGER_PROCEDURE_STRATEGY.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md)
- [schema_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/schema_mariadb.sql)
- [seed_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/seed_mariadb.sql)
- [mariadb_code_sequence_design.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/mariadb_code_sequence_design.sql)
- [DAO_MIGRATION_MAPPING.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/DAO_MIGRATION_MAPPING.md)
- [STEP_5_SESSION_CONTEXT_REFACTOR.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md)
- [STEP_6_MAVEN_MODULE_SPLIT.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md)

## Pham vi da implement that

### 1. Audit / log khong dung CONTEXT_INFO

Da tao:

- [AuditService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuditService.java)
- [AuditServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuditServiceImpl.java)
- [JdbcAuditLogRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcAuditLogRepository.java)
- [alter_add_audit_log_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/alter_add_audit_log_mariadb.sql)

Huong trien khai:

- Audit log nhan `UserContext`.
- Khong doc user tu controller, `ConnectDB`, hay bien static.
- Ghi vao bang moi `audit_log`.
- Tranh tiep tuc phu thuoc vao trigger audit SQL Server cu.

Thong tin log hien tai:

- `audit_code`
- `user_id`
- `username`
- `employee_id`
- `full_name`
- `action`
- `entity_name`
- `entity_id`
- `description`
- `created_at`

Da gan audit vao:

- `AuthServiceImpl` cho action `LOGIN` neu duoc wiring voi `AuditService`
- `EmployeeManagementServiceImpl`
- `ThuocCatalogServiceImpl`
- `PhieuNhapServiceImpl`

### 2. Sinh ma nghiep vu an toan

Da tao:

- [CodeGenerationService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/CodeGenerationService.java)
- [CodeGenerationServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/CodeGenerationServiceImpl.java)
- [CodeSequenceRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/CodeSequenceRepository.java)
- [JdbcCodeSequenceRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcCodeSequenceRepository.java)
- [alter_add_code_sequence_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/alter_add_code_sequence_mariadb.sql)

Co che:

- `SELECT ... FOR UPDATE` tren tung dong `code_sequence`
- update `current_value` trong transaction
- format ma theo `prefix + zero padding`

Dang duoc dung cho:

- `NHAN_VIEN`
- `THUOC`
- `PHIEU_NHAP`
- `LO_THUOC`
- `HOAT_DONG` cho `audit_code`

### 3. Transaction service cho nghiep vu tao du lieu

Da tao transaction infrastructure:

- [TransactionManager.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/TransactionManager.java)
- [TransactionCallback.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/TransactionCallback.java)
- [JdbcTransactionManager.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/JdbcTransactionManager.java)
- [TransactionConnectionContext.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/TransactionConnectionContext.java)

Da implement nghiep vu transaction-safe:

- [EmployeeManagementServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/EmployeeManagementServiceImpl.java)
  - thay `sp_InsertNhanVien`
- [ThuocCatalogServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocCatalogServiceImpl.java)
  - thay `sp_InsertThuoc_SanPham`
- [PhieuNhapServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/PhieuNhapServiceImpl.java)
  - thay logic chinh cua `sp_LuuPhieuNhap`

### 4. Query bao cao theo MariaDB

Da tao:

- [ReportService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportService.java)
- [ReportServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportServiceImpl.java)
- [ReportRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/ReportRepository.java)
- [JdbcReportRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcReportRepository.java)

Dang bao phu:

- doanh thu theo khoang ngay
- top ban chay theo khoang ngay
- lo da het han
- lo sap het han theo so ngay canh bao

## Service / class da tao

### pharmacy-common

- [UserContext.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/UserContext.java)
- [AuditLogDTO.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/AuditLogDTO.java)
- [RevenuePointDTO.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/RevenuePointDTO.java)
- [TopSellingProductDTO.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/TopSellingProductDTO.java)
- [ExpiringLotDTO.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/ExpiringLotDTO.java)
- [CreateEmployeeRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreateEmployeeRequest.java)
- [CreateThuocRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreateThuocRequest.java)
- [PhieuNhapRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/PhieuNhapRequest.java)
- [PhieuNhapItemRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/PhieuNhapItemRequest.java)
- [DateRangeRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/DateRangeRequest.java)
- [AuditAction.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/enums/AuditAction.java)
- [BusinessCodeType.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/enums/BusinessCodeType.java)
- [BusinessException.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/exception/BusinessException.java)

### pharmacy-server

- [ServerComponentFactory.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java)
- [JdbcNhanVienRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/JdbcNhanVienRepository.java)
- [JdbcCodeSequenceRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcCodeSequenceRepository.java)
- [JdbcAuditLogRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcAuditLogRepository.java)
- [JdbcEmployeeWriteRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcEmployeeWriteRepository.java)
- [JdbcMedicineCatalogRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcMedicineCatalogRepository.java)
- [JdbcPurchaseOrderRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcPurchaseOrderRepository.java)
- [JdbcReportRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcReportRepository.java)
- [AuthServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuthServiceImpl.java)
- [AuditServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuditServiceImpl.java)
- [CodeGenerationServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/CodeGenerationServiceImpl.java)
- [EmployeeManagementServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/EmployeeManagementServiceImpl.java)
- [ThuocCatalogServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocCatalogServiceImpl.java)
- [PhieuNhapServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/PhieuNhapServiceImpl.java)
- [ReportServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportServiceImpl.java)

## Mapping trigger / procedure cu sang implementation moi

| STT | Logic cu | Trigger/Procedure/Function cu | Quyet dinh o buoc 8 | Implementation moi | File/Class moi | Trang thai | Ghi chu |
|---|---|---|---|---|---|---|---|
| 1 | Audit thuoc | `trg_ThuocSanPham_Audit` | Chuyen len service | `AuditService.logAction(...)` | `AuditServiceImpl` | Implement mot phan | Co service va repo; chua gan vao full thuoc CRUD cu |
| 2 | Audit chi tiet hoat chat | `trg_ChiTietHoatChat_Audit` | Chuyen len service | `AuditService.logAction(...)` | `AuditServiceImpl` | Implement mot phan | Cho service thuoc/hoat chat sau |
| 3 | Audit ton kho theo lo | `trg_ThuocSPTheoLo_Audit` | Chuyen len service | `AuditService.logAction(...)` | `AuditServiceImpl` | Implement mot phan | Da gan vao `PhieuNhapService`; hoa don/doi-tra chua |
| 4 | Audit chi tiet don vi tinh | `trg_ChiTietDonViTinh_Audit` | Chuyen len service | `AuditService.logAction(...)` | `AuditServiceImpl` | Implement mot phan | Da gan vao tao thuoc; update gia hang loat chua |
| 5 | Cap nhat trang thai dat hang khi ton lo doi | `trg_CapNhatTrangThaiDatHang` | Transaction service | `DatHangService.recalculateAvailabilityByThuoc(...)` | Chua tao | Chua implement | Can review thu cong do logic dat/giu hang FEFO |
| 6 | Cap nhat trang thai dat hang khi them chi tiet | `trg_CapNhatTrangThaiPhieuDatHang_WhenInsertCT` | Transaction service | `DatHangService.taoPhieuDat(...)` + recalc | Chua tao | Chua implement | Take note: de lam sau voi nghiep vu dat hang |
| 7 | Sinh ma nhan vien | `sp_InsertNhanVien` | Chuyen len service | `CodeGenerationService` + insert JDBC | `EmployeeManagementServiceImpl` | Da implement | Transaction-safe qua `code_sequence` |
| 8 | Doanh thu hom nay | `sp_ThongKeBanHang_HomNay` | Repository query | `ReportService.getRevenueByDateRange(...)` | `ReportServiceImpl` | Implement mot phan | Caller se truyen range hom nay |
| 9 | Doanh thu tuan nay | `sp_ThongKeBanHang_TuanNay` | Repository query | `ReportService.getRevenueByDateRange(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper week boundary rieng |
| 10 | Doanh thu thang nay | `sp_ThongKeBanHang_ThangNay` | Repository query | `ReportService.getRevenueByDateRange(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper month boundary rieng |
| 11 | Doanh thu nam nay | `sp_ThongKeBanHang_NamNay` | Repository query | `ReportService.getRevenueByDateRange(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper year boundary rieng |
| 12 | Doanh thu tuy chon | `sp_ThongKeBanHang_TuyChon` | Repository query | `ReportService.getRevenueByDateRange(...)` | `ReportServiceImpl` | Da implement | Dung query MariaDB range |
| 13 | Top 5 hom nay | `sp_Top5SanPham_HomNay` | Repository query | `ReportService.getTopSellingProducts(...)` | `ReportServiceImpl` | Implement mot phan | Caller se truyen range hom nay |
| 14 | Top 5 tuan nay | `sp_Top5SanPham_TuanNay` | Repository query | `ReportService.getTopSellingProducts(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper week boundary |
| 15 | Top 5 thang nay | `sp_Top5SanPham_ThangNay` | Repository query | `ReportService.getTopSellingProducts(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper month boundary |
| 16 | Top 5 nam nay | `sp_Top5SanPham_NamNay` | Repository query | `ReportService.getTopSellingProducts(...)` | `ReportServiceImpl` | Implement mot phan | Chua co helper year boundary |
| 17 | Top 5 tuy chon | `sp_Top5SanPham_TuyChon` | Repository query | `ReportService.getTopSellingProducts(...)` | `ReportServiceImpl` | Da implement | Dung `LIMIT` thay `TOP` |
| 18 | Thuoc het han / sap het han | `sp_ThongKeThuocHetHan` | Repository query | `ReportService.getExpiredLots()`, `getExpiringLots(...)` | `ReportServiceImpl` | Implement mot phan | Da co 2 query rieng cho expired / expiring |
| 19 | Bao cao XNT phuc tap | `sp_ThongKeXNT` | Repository query | `InventoryReportService` | Chua tao | Chua implement | Take note: rat phuc tap, can native SQL/view rieng |
| 20 | Luu phieu nhap + cong ton + cap nhat gia | `sp_LuuPhieuNhap` | Transaction service | `createPurchaseOrder(...)` | `PhieuNhapServiceImpl` | Da implement | Ban create flow; chua day du upsert parity cua proc cu |
| 21 | Lo het han | `sp_HangHetHan` | Repository query | `ReportService.getExpiredLots()` | `ReportServiceImpl` | Da implement | MariaDB syntax |
| 22 | Lo sap het han | `sp_HangSapHetHan` | Repository query | `ReportService.getExpiringLots(...)` | `ReportServiceImpl` | Da implement | Nguong so ngay truyen tu service |
| 23 | Sinh ma thuoc + tao DVT co ban | `sp_InsertThuoc_SanPham` | Chuyen len service | `createThuocWithBaseUnit(...)` | `ThuocCatalogServiceImpl` | Da implement | Sinh ma + insert `ChiTietDonViTinh` co ban |
| 24 | Recalculate availability theo thuoc | `sp_CapNhatTrangThaiDatHang` | Transaction service | `DatHangService.recalculateAvailabilityByThuoc(...)` | Chua tao | Chua implement | Can review quy doi DVT + trang thai dat hang |
| 25 | Duyet phieu dat hang FEFO | `sp_DuyetPhieuDatHang` | Transaction service | `DatHangService.duyetPhieuDatHang(...)` | Chua tao | Chua implement | Take note quan trong: FEFO + reserve stock |
| 26 | Tim hoa don theo khoang co san | `sp_GetHoaDonTheoThoiGian` | Repository query | `HoaDonQueryService` | Chua tao | Chua implement | Chua uu tien trong dot nay |
| 27 | Tim hoa don tuy chon | `sp_GetHoaDonTheoTuyChon` | Repository query | `HoaDonQueryService` | Chua tao | Chua implement | Chua uu tien trong dot nay |

## Logic giu o database

Hien tai van giu o DB:

- PK
- FK
- UNIQUE
- NOT NULL
- DEFAULT CURRENT_TIMESTAMP
- index da co trong `schema_mariadb.sql`

Khong giu o DB trong dot nay:

- audit theo user/request
- sinh ma nghiep vu
- transaction nhap hang
- trigger phu thuoc `CONTEXT_INFO`

## Script SQL da tao

- [alter_add_code_sequence_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/alter_add_code_sequence_mariadb.sql)
- [alter_add_audit_log_mariadb.sql](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/SQL/alter_add_audit_log_mariadb.sql)

Khong tao `alter_step_8_constraints_mariadb.sql` trong dot nay vi:

- `schema_mariadb.sql` buoc 7 da co phan lon PK/FK/index/default.
- CHECK constraint bo sung can review theo version MariaDB muc tieu va du lieu migrate that.

## Transaction da ap dung

- `CodeGenerationServiceImpl.nextCode(...)`
- `EmployeeManagementServiceImpl.createEmployee(...)`
- `ThuocCatalogServiceImpl.createThuocWithBaseUnit(...)`
- `PhieuNhapServiceImpl.createPurchaseOrder(...)`

## Diem can test ky

### Audit

- Tao `audit_log` thanh cong.
- Audit code tang dung theo `code_sequence`.
- Dang nhap qua `AuthServiceImpl` co ghi log neu service duoc wiring voi `AuditService`.
- Khong luu password vao audit log.

### Sinh ma

- `NV`, `TS`, `PN`, `LH`, `LOG` tang dung.
- `code_sequence.current_value` cap nhat cung transaction.
- Goi lien tiep khong bi trung ma.

### Phieu nhap

- Tao phieu nhap moi se:
  - tao `PhieuNhap`
  - tao `ChiTietPhieuNhap`
  - tao `Thuoc_SP_TheoLo`
  - cap nhat `ChiTietDonViTinh`
  - ghi audit
- Neu loi o chi tiet/lo/gia thi rollback toan bo.

### Bao cao

- Query doanh thu MariaDB chay duoc voi khoang ngay hop le.
- Query top san pham dung `LIMIT`.
- Query het han / sap het han khong con `GETDATE`, `TOP`, `DATEPART`.

## Take note quan trong

1. `sp_DuyetPhieuDatHang` va `sp_CapNhatTrangThaiDatHang`

- Day la cum nghiep vu rui ro cao nhat con lai.
- Co FEFO, giu hang, tru ton, quy doi don vi tinh, cap nhat trang thai phieu dat.
- Chua implement trong dot nay de tranh lam sai ton kho.

2. `sp_ThongKeXNT`

- Qua phuc tap de thay an toan trong cung dot nay.
- Nen lam rieng thanh native SQL / view / query service sau khi chot mapping don vi tinh va doi/tra/nhap/xuat.

3. `PhieuNhapServiceImpl` hien tai la create flow

- Da thay phan chinh cua `sp_LuuPhieuNhap`.
- Chua co full upsert parity cua procedure SQL Server cu.
- Neu can sua phieu nhap da ton tai, nen lam use case rieng.

4. Prefix ma nghiep vu can review

- Script thuc thi dang dong bo theo MariaDB seed hien tai:
  - `PD001`
  - `LH0001`
- SQL Server / DAO cu co dau hieu dang dung:
  - `PDH001`
  - `LH00001`
- Can chot 1 chuan prefix truoc khi migrate du lieu that.

5. Audit table moi khac `HoatDong`

- Dot nay uu tien bang `audit_log` de luu `entity_id` ro rang va tranh tiep tuc phu thuoc generated code `MaHDong`.
- Man hinh cu doc `HoatDong` chua duoc doi sang `audit_log`.

6. Doanh thu bao cao chua dat parity 100% voi proc SQL Server

- Query range hien tai tinh doanh thu tu hoa don va chi tiet hoa don.
- Chua mo phong day du phan tru hang tra + VAT theo dung tat ca proc cu.
- Can business review truoc khi thay man thong ke hien tai.

## Build / compile

Da compile thanh cong:

```powershell
mvn -q -DskipTests compile
```

Thu muc build:

- [pharmacy-parent](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent)

## Checklist

- [x] Da tao `AuditService` va repo luu audit khong dung `CONTEXT_INFO`
- [x] Da tao `CodeGenerationService` dung `code_sequence`
- [x] Da tao script MariaDB cho `code_sequence`
- [x] Da tao script MariaDB cho `audit_log`
- [x] Da tao transaction infrastructure JDBC o server module
- [x] Da thay `sp_InsertNhanVien`
- [x] Da thay `sp_InsertThuoc_SanPham`
- [x] Da thay phan chinh cua `sp_LuuPhieuNhap`
- [x] Da tao query MariaDB cho doanh thu / top ban chay / het han
- [x] Da ghi ro logic chua implement va ly do
- [x] Da take note cac proc/trigger rui ro cao can lam sau
