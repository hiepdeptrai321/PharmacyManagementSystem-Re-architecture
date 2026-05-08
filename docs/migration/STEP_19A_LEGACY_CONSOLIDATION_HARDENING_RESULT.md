# STEP 19A - Legacy Consolidation Hardening Result

## 1. Mục tiêu

Bỏ phụ thuộc của service mới vào legacy DAO cho cụm `Thuoc` và `KhuyenMai`, để runtime path mới không còn đi qua `ConnectDB` SQL Server.

## 2. Tài liệu đã đọc

- `docs/migration/STEP_19A_LEGACY_CONSOLIDATION_HARDENING_GUIDE.md`
- `docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md`
- `docs/migration/STEP_18_FINAL_E2E_VERIFICATION_AND_LEGACY_CLEANUP_PLAN_UPDATED.md`
- `docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md`
- `docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md`

File không thiếu trong phạm vi guide Step 19A.

## 3. Scan trước khi sửa

### Service mới còn gọi DAO cũ

Trước Step 19A:

```text
ThuocServiceImpl
  - Thuoc_SanPham_Dao
  - Thuoc_SP_TheoLo_Dao
  - DonViTinh_Dao
  - ChiTietDonViTinh_Dao
  - HoatChat_Dao
  - ChiTietHoatChat_Dao
  - LoaiHang_Dao

KhuyenMaiServiceImpl
  - KhuyenMai_Dao
  - LoaiKhuyenMai_Dao
  - ChiTietKhuyenMai_Dao
  - Thuoc_SP_TangKem_Dao
```

### DAO/method legacy cần thay

| Service | DAO cũ | Method cũ | Repository mới | Trạng thái |
|---|---|---|---|---|
| `ThuocServiceImpl` | `Thuoc_SanPham_Dao` | `selectAll`, `insert`, `update`, `xoaThuoc_SanPham`, `getAllLoaiHang`, `getTenDVTByMaThuoc` | `MedicineCatalogRepository` | Đã thay |
| `ThuocServiceImpl` | `Thuoc_SP_TheoLo_Dao` | `selectSoLuongTonByMaThuoc`, `getThuocTonKho`, `selectAll` | `MedicineCatalogRepository` | Đã thay |
| `ThuocServiceImpl` | `LoaiHang_Dao` | `selectAll` | `MedicineCatalogRepository` | Đã thay |
| `ThuocServiceImpl` | `HoatChat_Dao` | `selectAll` | `MedicineCatalogRepository` | Đã thay |
| `ThuocServiceImpl` | `ChiTietHoatChat_Dao` | `selectByMaThuoc`, `insert`, `update`, `deleteById` | `MedicineCatalogRepository` | Đã thay |
| `ThuocServiceImpl` | `DonViTinh_Dao` + `ChiTietDonViTinh_Dao` | lookup / insert đơn vị cơ bản | `MedicineCatalogRepository` | Đã thay |
| `KhuyenMaiServiceImpl` | `KhuyenMai_Dao` | `selectAll`, `selectById`, `selectByTuKhoa`, `insert`, `update`, `deleteByMaKM`, `selectActiveOn`, `selectActiveInvoiceOn` | `PromotionRepository` | Đã thay |
| `KhuyenMaiServiceImpl` | `LoaiKhuyenMai_Dao` | `selectAll`, `selectById`, `selectByTen` | `PromotionRepository` | Đã thay |
| `KhuyenMaiServiceImpl` | `ChiTietKhuyenMai_Dao` | `selectByMaKM`, `insert`, `deleteByMaKM` | `PromotionRepository` | Đã thay |
| `KhuyenMaiServiceImpl` | `Thuoc_SP_TangKem_Dao` | `selectByMaKM`, `insert`, `deleteByMaKM` | `PromotionRepository` | Đã thay |

## 4. File đã tạo

### Repository

- [PromotionRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/PromotionRepository.java)

### Repository JDBC

- [JdbcMedicineCatalogRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcMedicineCatalogRepository.java)
- [JdbcPromotionRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcPromotionRepository.java)

## 5. File đã sửa

- [MedicineCatalogRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/MedicineCatalogRepository.java)
- [ThuocServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocServiceImpl.java)
- [KhuyenMaiServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiServiceImpl.java)
- [ServerComponentFactory.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java)

## 6. Mapping mới

### Thuoc

| Method service | Repository method mới | Ghi chú |
|---|---|---|
| `findAll` | `findAllMedicines` | Query MariaDB mới, map trực tiếp `Thuoc_SanPham` + `LoaiHang` + `NhomDuocLy` + `KeHang` |
| `findAllLoaiHang` | `findAllLoaiHang` | Không còn `LoaiHang_Dao` |
| `findAllLoaiHangNames` | `findAllLoaiHangNames` | Không còn `Thuoc_SanPham_Dao.getAllLoaiHang()` |
| `findAllHoatChat` | `findAllHoatChat` | Không còn `HoatChat_Dao` |
| `findChiTietHoatChatByMaThuoc` | `findChiTietHoatChatByMaThuoc` | Không còn `ChiTietHoatChat_Dao` |
| `create` | `insertMedicine`, `insertChiTietHoatChat`, `insertBaseUnit` | Chạy trong transaction |
| `update` | `updateMedicine`, `findChiTietHoatChatByMaThuoc`, `insert/update/deleteChiTietHoatChat` | Chạy trong transaction |
| `softDelete` | `softDeleteMedicine` | Chuyển sang `UPDATE TrangThaiXoa = 1` ở MariaDB |
| `getTongSoLuongTonByMaThuoc` | `getTongSoLuongTonByMaThuoc` | Không còn `Thuoc_SP_TheoLo_Dao` |
| `getTenDonViTinhCoBan` | `getTenDonViTinhCoBan` | Query trực tiếp `ChiTietDonViTinh + DonViTinh` |
| `getThuocTonKho` | `getThuocTonKho` | Query summary tồn kho mới |
| `getAllTheoLo` | `findAllLots` | Query lot mới, không attach `ChiTietPhieuNhap` để tránh kéo legacy flow |

### KhuyenMai

| Method service | Repository method mới | Ghi chú |
|---|---|---|
| `findAll` | `findAll` | Query MariaDB mới, join `LoaiKhuyenMai` |
| `findById` | `findById` | Không còn `KhuyenMai_Dao` |
| `searchByKeyword` | `searchByKeyword` | Dùng `LOWER(... LIKE ...)` |
| `findAllLoaiKhuyenMai` | `findAllLoaiKhuyenMai` | Không còn `LoaiKhuyenMai_Dao` |
| `findLoaiKhuyenMaiById` | `findLoaiKhuyenMaiById` | Không còn `LoaiKhuyenMai_Dao` |
| `findLoaiKhuyenMaiByTen` | `findLoaiKhuyenMaiByTen` | Không còn `LoaiKhuyenMai_Dao` |
| `findChiTietByMaKM` | `findChiTietByMaKM` | Query trực tiếp `ChiTietKhuyenMai` + `Thuoc_SanPham` |
| `findQuaTangByMaKM` | `findQuaTangByMaKM` | Query trực tiếp `Thuoc_SP_TangKem` + `Thuoc_SanPham` |
| `create` | `insertPromotion`, `insertPromotionDetail`, `insertPromotionGift` | Chạy trong transaction |
| `update` | `updatePromotion`, `deletePromotionDetailsByMaKM`, `deletePromotionGiftsByMaKM`, `insert...` | Chạy trong transaction |
| `deleteByMaKM` | `deletePromotionGiftsByMaKM`, `deletePromotionDetailsByMaKM`, `deletePromotionById` | Chạy trong transaction |
| `findActiveOn` | `findActiveOn` | Query ngày hiệu lực MariaDB |
| `findActiveInvoiceOn` | `findActiveInvoiceOn` | Giữ filter `LKM004`, `LKM005` |

## 7. Transaction

- `Thuoc create/update/softDelete` đã chạy qua `TransactionManager`
- `KhuyenMai create/update/delete` đã chạy qua `TransactionManager`
- Step 19A không thêm audit mới; chỉ harden runtime path của 2 service

## 8. Scan sau khi sửa

### ThuocServiceImpl / KhuyenMaiServiceImpl còn DAO không

```text
Khong con match:
- com.example.pharmacymanagementsystem_qlht.dao
- new *_Dao()
- ConnectDB
```

### ConnectDB còn bị gọi trong service mới không

```text
Khong.
2 service muc tieu Step 19A hien chi goi:
- TransactionManager
- MedicineCatalogRepository
- PromotionRepository
- CodeGenerationService
```

### SQL Server syntax trong repository mới

```text
Khong phat hien token SQL Server-specific trong:
- JdbcMedicineCatalogRepository
- JdbcPromotionRepository

Da scan lai voi cac token:
- SELECT TOP
- ISNULL(
- GETDATE(
- OFFSET 0 ROWS
- FETCH NEXT
- COLLATE SQL_
- {call sp_
- jdbc:sqlserver
- SQLServer
```

## 9. Build result

Command:

```bash
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
mvn -f pharmacy-parent/pom.xml clean install
```

Result:

```text
- compile: PASS
- clean install: PASS (khi chay ngoai sandbox de bo qua chan ghi .m2)
```

## 10. Runtime smoke test

- [x] Xác nhận build toàn bộ `pharmacy-parent`
- [x] Xác nhận 2 service mục tiêu không còn runtime dependency vào DAO legacy
- [ ] Chạy smoke runtime thật cho `Thuoc` / `KhuyenMai` qua RMI + MariaDB

Chưa chạy runtime thật trong Step 19A vì task này tập trung harden dependency/runtime path. Runtime E2E vẫn nên test tay ở Step 19B sau khi chốt cleanup candidate.

## 11. Chưa làm / rủi ro còn lại

- [ConnectDB.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/ConnectDB.java) vẫn còn hardcode SQL Server (`jdbc:sqlserver`, `sapassword`) và vẫn được khoảng `28` file DAO legacy trong `pharmacy-server` dùng.
- `pharmacy-common` vẫn còn `37` model legacy ở package `com.example.pharmacymanagementsystem_qlht.model` đang đóng vai trò DTO tạm.
- `Thuoc` và `KhuyenMai` đã bỏ DAO legacy ở service mới, nhưng chưa JPA-hoa sâu; repository mới hiện là JDBC MariaDB transition repository.
- Runtime test tay cho các màn `DMThuoc`, `TKThuoc`, `DMKhuyenMai`, `CapNhatKhuyenMai` vẫn cần đối chiếu hành vi với monolith cũ.

## 12. Đề xuất Step 19B

- Scan lại toàn bộ `pharmacy-parent` để tìm code mới nào còn vô tình dựa vào package `com.example.pharmacymanagementsystem_qlht.dao`
- Tách tiếp các cụm còn đang compile phụ thuộc `ConnectDB` ra repository mới nếu chúng còn nằm trên runtime path cần giữ
- Chỉ archive/xóa DAO legacy trong `pharmacy-parent` khi không còn import/use thực tế
- Chỉ gỡ `ConnectDB` và `mssql-jdbc` khi không còn class compile phụ thuộc
- Bắt đầu kế hoạch split `37` legacy model ở `pharmacy-common` thành DTO/shared model rõ ràng hơn
