# STEP 19A - Legacy Consolidation Hardening: Thuoc + KhuyenMai

## 1. Mục tiêu

Step 19A xử lý 2 cụm transition còn lại sau Step 18:

```text
1. ThuocServiceImpl.java
2. KhuyenMaiServiceImpl.java
```

Mục tiêu chính:

- Bỏ phụ thuộc của service mới vào legacy DAO.
- Không còn `com.example.pharmacymanagementsystem_qlht.dao.*` trong service mới.
- Không còn `new *_Dao()` trong service mới.
- Không còn đường runtime mới đi qua `ConnectDB` SQL Server.
- Tách query/logic còn cần từ DAO cũ sang repository mới trong `pharmacy-server`.
- Giữ root `src/` legacy làm reference, chưa xóa.
- Chưa xóa `ConnectDB`, DAO legacy, `mssql-jdbc` trong task này nếu còn class khác compile phụ thuộc.
- Build được sau từng pass nhỏ.

Kiến trúc đích:

```text
Client JavaFX
    -> RMI Client Service
    -> Remote Interface trong pharmacy-common
    -> RemoteAdapter trong pharmacy-server
    -> ThuocServiceImpl / KhuyenMaiServiceImpl
    -> Repository mới trong pharmacy-server
    -> JdbcConnectionProvider / TransactionManager
    -> MariaDB
```

Không còn flow:

```text
Service mới
    -> DAO legacy
    -> ConnectDB
    -> SQL Server
```

---

## 2. Kết luận từ Step 18 làm đầu vào cho Step 19A

Không được xóa ngay:

```text
- root src legacy
- ConnectDB legacy
- DAO legacy
```

Runtime E2E toàn hệ thống mới chưa thể nghiệm thu thật trên môi trường hiện tại vì:

```text
- không có mariadb CLI
- exec:java / clean install bị chặn bởi quyền ghi .m2
- JavaFX desktop chưa có bằng chứng chạy end-to-end trong terminal
```

2 cụm transition cần xử lý:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocServiceImpl.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiServiceImpl.java
```

---

## 3. Nguyên tắc bắt buộc

### 3.1. Không copy nguyên DAO cũ

Không được copy/giữ nguyên các class DAO cũ làm “repository mới”.

Sai:

```text
Thuoc_SanPham_Dao -> copy nguyên thành ThuocRepository
KhuyenMai_Dao -> copy nguyên thành KhuyenMaiRepository
```

Đúng:

```text
Đọc method DAO cũ còn cần
    -> trích query/logic cần thiết
    -> đổi SQL Server syntax sang MariaDB syntax
    -> viết repository interface mới
    -> viết JDBC/JPA implementation mới
    -> service mới gọi repository mới
```

### 3.2. Không dùng ConnectDB trong code mới

Code mới không được gọi:

```text
com.example.pharmacymanagementsystem_qlht.connectDB.ConnectDB
DriverManager trực tiếp
SQL Server URL
CallableStatement cho stored procedure SQL Server
```

Dùng:

```text
JdbcConnectionProvider
TransactionManager
JpaUtil / JPA repository nếu đã có entity phù hợp
```

### 3.3. Không xóa legacy ngay

Trong Step 19A:

- Chỉ bỏ dependency runtime từ service mới sang DAO cũ.
- Chưa xóa `src/`.
- Chưa xóa package DAO cũ nếu còn dùng hoặc chưa scan đủ.
- Chưa xóa `mssql-jdbc` nếu build còn cần.

Xóa/archive là Step 19B/19C sau khi scan xác nhận không còn import/use.

### 3.4. Không refactor lan sang nghiệp vụ khác

Không sửa luồng bán hàng, nhập hàng, đổi/trả, report nếu không liên quan trực tiếp.

Nếu phát hiện lỗi ở module khác, ghi TODO, không xử lý trong Step 19A.

### 3.5. Ưu tiên compile trước, runtime sau

Mỗi pass phải giữ được compile:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Nếu compile fail, fix trong phạm vi pass hiện tại, không mở rộng task.

---

## 4. Hiện trạng cần xử lý

### 4.1. ThuocServiceImpl hiện còn dùng DAO cũ

File:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocServiceImpl.java
```

Hiện service mới đang import và khởi tạo các DAO cũ:

```text
ChiTietDonViTinh_Dao
ChiTietHoatChat_Dao
DonViTinh_Dao
HoatChat_Dao
LoaiHang_Dao
Thuoc_SP_TheoLo_Dao
Thuoc_SanPham_Dao
```

Các method service hiện đang dựa vào DAO cũ:

```text
findAll()
findAllLoaiHang()
findAllLoaiHangNames()
findAllHoatChat()
findChiTietHoatChatByMaThuoc()
create(...)
update(...)
softDelete(...)
getTongSoLuongTonByMaThuoc(...)
getTenDonViTinhCoBan(...)
getThuocTonKho()
getAllTheoLo()
```

### 4.2. KhuyenMaiServiceImpl hiện còn dùng DAO cũ

File:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiServiceImpl.java
```

Hiện service mới đang import và khởi tạo các DAO cũ:

```text
KhuyenMai_Dao
LoaiKhuyenMai_Dao
ChiTietKhuyenMai_Dao
Thuoc_SP_TangKem_Dao
```

Các method service hiện đang dựa vào DAO cũ:

```text
findAll()
findById(...)
searchByKeyword(...)
findAllLoaiKhuyenMai()
findLoaiKhuyenMaiById(...)
findLoaiKhuyenMaiByTen(...)
findChiTietByMaKM(...)
findQuaTangByMaKM(...)
create(...)
update(...)
deleteByMaKM(...)
findActiveOn(...)
findActiveInvoiceOn(...)
```

### 4.3. MedicineCatalogRepository hiện mới có một phần

Đã có:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/MedicineCatalogRepository.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcMedicineCatalogRepository.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocCatalogServiceImpl.java
```

Nhưng hiện repository này mới hỗ trợ tạo thuốc với đơn vị cơ bản:

```text
insertMedicine(...)
insertBaseUnit(...)
```

Vì vậy Step 19A cần mở rộng hoặc tách repository mới để thay toàn bộ DAO đang dùng trong `ThuocServiceImpl`.

### 4.4. KhuyenMai chưa thấy repository mới riêng cho promotion

Cần tạo repository mới cho cụm khuyến mãi, ví dụ:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/PromotionRepository.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcPromotionRepository.java
```

Tên có thể dùng `KhuyenMaiRepository`, nhưng tránh trùng nếu đã có class/entity khác. Nếu repo hiện đã có `KhuyenMaiRepository`, reuse và mở rộng thay vì tạo mới.

---

## 5. Lệnh scan trước khi sửa

Chạy từ root repo.

### 5.1. Scan service mới còn gọi DAO cũ

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

Kỳ vọng sau Step 19A:

```text
Không còn match trong:
- ThuocServiceImpl.java
- KhuyenMaiServiceImpl.java
```

Nếu còn service khác match, ghi TODO Step 19A.2 hoặc Step 19B, không xử lý lan nếu ngoài phạm vi.

### 5.2. Scan DAO cũ cụm Thuoc/KhuyenMai còn method nào được dùng

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "Thuoc_SanPham_Dao|Thuoc_SP_TheoLo_Dao|DonViTinh_Dao|ChiTietDonViTinh_Dao|HoatChat_Dao|ChiTietHoatChat_Dao|LoaiHang_Dao|KhuyenMai_Dao|LoaiKhuyenMai_Dao|ChiTietKhuyenMai_Dao|Thuoc_SP_TangKem_Dao" |
  Select-Object Path, LineNumber, Line
```

### 5.3. Scan SQL Server syntax trong cụm cần xử lý

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao -Recurse -File -Include *Thuoc*.java,*KhuyenMai*.java,*LoaiKhuyenMai*.java,*ChiTietKhuyenMai*.java,*DonViTinh*.java,*HoatChat*.java,*LoaiHang*.java |
  Select-String -Pattern "SELECT TOP|TOP \(|ISNULL\(|GETDATE\(|OFFSET 0 ROWS|FETCH NEXT|COLLATE SQL_|prepareCall|CallableStatement|ConnectDB|sp_" |
  Select-Object Path, LineNumber, Line
```

### 5.4. Scan repository mới dùng ConnectDB không

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|com\.example\.pharmacymanagementsystem_qlht\.dao|DriverManager|getConnection\(" |
  Select-Object Path, LineNumber, Line
```

Lưu ý: `getConnection()` trong `AbstractJdbcRepository` hoặc repository JDBC kế thừa provider có thể hợp lệ. `DriverManager` trực tiếp hoặc `ConnectDB` là không hợp lệ.

---

## 6. Thiết kế đích cho cụm Thuoc

### 6.1. Repository interface

Mở rộng `MedicineCatalogRepository` hoặc tạo repository riêng nếu muốn tách đọc/ghi.

Đề xuất mở rộng:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/MedicineCatalogRepository.java
```

Thêm các method cần thay DAO:

```java
List<Thuoc_SanPham> findAllMedicines();

List<LoaiHang> findAllLoaiHang();

List<String> findAllLoaiHangNames();

List<HoatChat> findAllHoatChat();

List<ChiTietHoatChat> findChiTietHoatChatByMaThuoc(String maThuoc);

void insertMedicine(String maThuoc, Thuoc_SanPham thuoc);

void insertChiTietHoatChat(String maThuoc, ChiTietHoatChat chiTiet);

void insertBaseUnit(String maThuoc, String maDonViTinhCoBan);

void updateMedicine(Thuoc_SanPham thuoc);

void deleteChiTietHoatChat(String maThuoc, String maHoatChat);

void updateChiTietHoatChat(String maThuoc, ChiTietHoatChat chiTiet);

void softDeleteMedicine(String maThuoc);

int getTongSoLuongTonByMaThuoc(String maThuoc);

String getTenDonViTinhCoBan(String maThuoc);

List<ThuocTonKho> getThuocTonKho();

List<Thuoc_SP_TheoLo> findAllLots();
```

Có thể tinh chỉnh tên method cho đúng convention hiện có, nhưng service không được gọi DAO cũ.

### 6.2. Repository implementation

Mở rộng:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcMedicineCatalogRepository.java
```

Yêu cầu:

- Dùng `JdbcConnectionProvider`.
- Dùng transaction-bound connection nếu đang trong transaction.
- Không gọi `ConnectDB`.
- Không gọi DAO cũ để map nested object.
- Query MariaDB-compatible.

Ví dụ đổi SQL Server syntax:

```text
SELECT TOP ? ...       -> ORDER BY ... LIMIT ?
OFFSET 0 ROWS FETCH    -> LIMIT ?
ISNULL(x, y)           -> COALESCE(x, y)
GETDATE()              -> CURRENT_TIMESTAMP
COLLATE SQL_...        -> bỏ hoặc xử lý bằng LOWER()/LIKE tùy nhu cầu
```

### 6.3. Mapping object

Không gọi DAO cũ trong mapper. Nếu cần nested object:

```text
Thuoc_SanPham
    -> NhomDuocLy
    -> LoaiHang
    -> KeHang
```

Thì query nên `LEFT JOIN` đủ bảng cần thiết và map trực tiếp.

Ví dụ:

```sql
SELECT
    t.MaThuoc,
    t.TenThuoc,
    t.HamLuong,
    t.DonViHL,
    t.DuongDung,
    t.QuyCachDongGoi,
    t.SDK_GPNK,
    t.HangSX,
    t.NuocSX,
    t.HinhAnh,
    t.MaLoaiHang,
    lh.TenLH,
    t.MaNDL,
    ndl.TenNDL,
    t.ViTri,
    kh.TenKe,
    t.ETC
FROM Thuoc_SanPham t
LEFT JOIN LoaiHang lh ON t.MaLoaiHang = lh.MaLoaiHang
LEFT JOIN NhomDuocLy ndl ON t.MaNDL = ndl.MaNDL
LEFT JOIN KeHang kh ON t.ViTri = kh.MaKe
WHERE t.TrangThaiXoa = 0
```

Nếu tên bảng/cột thực tế khác, đọc `SQL/schema_mariadb.sql` trước khi code.

### 6.4. Service refactor

Refactor:

```text
ThuocServiceImpl
```

Constructor mới nên nhận dependency:

```java
public ThuocServiceImpl(
        MedicineCatalogRepository medicineCatalogRepository,
        TransactionManager transactionManager,
        CodeGenerationService codeGenerationService,
        AuditService auditService
)
```

Nếu muốn ít thay đổi hơn, tối thiểu:

```java
public ThuocServiceImpl(
        MedicineCatalogRepository medicineCatalogRepository,
        CodeGenerationService codeGenerationService
)
```

Tuy nhiên `create/update` có nhiều insert/update liên quan, nên nên đưa vào `TransactionManager`.

`ServerComponentFactory.createThuocService()` phải đổi từ:

```java
return new ThuocServiceImpl(createCodeGenerationService());
```

sang:

```java
return new ThuocServiceImpl(
    createMedicineCatalogRepository(),
    transactionManager,
    createCodeGenerationService(),
    createAuditService()
);
```

### 6.5. Transaction cho create/update thuốc

Các method cần transaction:

```text
create(...)
update(...)
softDelete(...) nếu có audit hoặc update nhiều bảng
```

`create(...)` cần cùng commit/rollback:

```text
- insert Thuoc_SanPham
- insert ChiTietHoatChat nếu có
- insert ChiTietDonViTinh đơn vị cơ bản nếu có
- audit nếu thêm vào
```

`update(...)` cần cùng commit/rollback:

```text
- update Thuoc_SanPham
- thêm/sửa/xóa ChiTietHoatChat theo danh sách mới
- audit nếu thêm vào
```

### 6.6. Không phá `ThuocCatalogServiceImpl`

Hiện đã có `ThuocCatalogServiceImpl` dùng `MedicineCatalogRepository` để tạo thuốc với base unit. Không được làm hỏng flow này.

Codex cần quyết định một trong hai hướng:

```text
Hướng A:
    Mở rộng MedicineCatalogRepository để cả ThuocServiceImpl và ThuocCatalogServiceImpl dùng chung.

Hướng B:
    Tạo repository mới như ThuocRepository/MedicineQueryRepository cho ThuocServiceImpl,
    giữ MedicineCatalogRepository hiện có cho ThuocCatalogServiceImpl.
```

Ưu tiên Hướng A nếu ít conflict và dễ build.

---

## 7. Thiết kế đích cho cụm KhuyenMai

### 7.1. Repository interface

Tạo mới nếu chưa có:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/PromotionRepository.java
```

Hoặc:

```text
KhuyenMaiRepository.java
```

Gợi ý method:

```java
List<KhuyenMai> findAll();

KhuyenMai findById(String maKhuyenMai);

List<KhuyenMai> searchByKeyword(String keyword);

List<LoaiKhuyenMai> findAllLoaiKhuyenMai();

LoaiKhuyenMai findLoaiKhuyenMaiById(String maLoaiKhuyenMai);

LoaiKhuyenMai findLoaiKhuyenMaiByTen(String tenLoaiKhuyenMai);

List<ChiTietKhuyenMai> findChiTietByMaKM(String maKhuyenMai);

List<Thuoc_SP_TangKem> findQuaTangByMaKM(String maKhuyenMai);

void insertPromotion(KhuyenMai khuyenMai);

void updatePromotion(KhuyenMai khuyenMai);

void deletePromotion(String maKhuyenMai);

void deleteChiTietByMaKM(String maKhuyenMai);

void deleteQuaTangByMaKM(String maKhuyenMai);

void insertChiTiet(KhuyenMai khuyenMai, ChiTietKhuyenMai chiTiet);

void insertQuaTang(KhuyenMai khuyenMai, Thuoc_SP_TangKem quaTang);

List<KhuyenMai> findActiveOn(Date ngay);

List<KhuyenMai> findActiveInvoiceOn(Date ngay);
```

### 7.2. Repository implementation

Tạo:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcPromotionRepository.java
```

Yêu cầu:

- Dùng `JdbcConnectionProvider`.
- Không gọi `ConnectDB`.
- Không gọi `KhuyenMai_Dao`, `LoaiKhuyenMai_Dao`, `ChiTietKhuyenMai_Dao`, `Thuoc_SP_TangKem_Dao`.
- Không dùng `SELECT TOP`.
- Không tự sinh mã bằng query `TOP 1`; service dùng `CodeGenerationService`.

### 7.3. Mapping KhuyenMai

Cần map ít nhất:

```text
KhuyenMai:
- MaKM
- LoaiKM
- TenKM
- GiaTriKM
- NgayBatDau
- NgayKetThuc
- MoTa
- NgayTao
- GiaTriApDung
```

`LoaiKM` có thể map bằng join:

```sql
SELECT
    km.MaKM,
    km.MaLoai,
    lkm.TenLoai,
    km.TenKM,
    km.GiaTriKM,
    km.NgayBatDau,
    km.NgayKetThuc,
    km.MoTa,
    km.NgayTao,
    km.GiaTriApDung
FROM KhuyenMai km
LEFT JOIN LoaiKhuyenMai lkm ON km.MaLoai = lkm.MaLoai
```

Nếu tên cột của `LoaiKhuyenMai` khác, đọc schema/model cũ trước.

### 7.4. Service refactor

Refactor:

```text
KhuyenMaiServiceImpl
```

Constructor mới nên nhận:

```java
public KhuyenMaiServiceImpl(
        PromotionRepository promotionRepository,
        TransactionManager transactionManager,
        CodeGenerationService codeGenerationService,
        AuditService auditService
)
```

Hoặc nếu muốn ít thay đổi hơn, tối thiểu:

```java
public KhuyenMaiServiceImpl(
        PromotionRepository promotionRepository,
        CodeGenerationService codeGenerationService
)
```

Nhưng `create/update/delete` động tới nhiều bảng, nên nên dùng `TransactionManager`.

`ServerComponentFactory.createKhuyenMaiService()` đổi từ:

```java
return new KhuyenMaiServiceImpl(createCodeGenerationService());
```

sang:

```java
return new KhuyenMaiServiceImpl(
    createPromotionRepository(),
    transactionManager,
    createCodeGenerationService(),
    createAuditService()
);
```

Thêm factory method:

```java
private PromotionRepository createPromotionRepository() {
    return new JdbcPromotionRepository(connectionProvider);
}
```

### 7.5. Transaction cho create/update/delete khuyến mãi

`create(...)` cần cùng commit/rollback:

```text
- insert KhuyenMai
- insert ChiTietKhuyenMai
- insert Thuoc_SP_TangKem
- audit nếu thêm vào
```

`update(...)` cần cùng commit/rollback:

```text
- update KhuyenMai
- delete detail cũ
- delete quà tặng cũ
- insert detail mới
- insert quà tặng mới
- audit nếu thêm vào
```

`deleteByMaKM(...)` cần cùng commit/rollback:

```text
- delete quà tặng
- delete chi tiết
- delete khuyến mãi
- audit nếu thêm vào
```

---

## 8. Cách chia pass cho Codex

### Pass 0 - Read docs + scan only

Mục tiêu:

- Đọc docs Step 18/result Step 18.
- Scan `ThuocServiceImpl` và `KhuyenMaiServiceImpl`.
- Scan repository hiện có.
- Không sửa code.

Output:

- Danh sách DAO cũ đang được service mới gọi.
- Danh sách method cần thay.
- Đề xuất tên repository sẽ tạo/mở rộng.

### Pass 1 - Medicine repository hardening

Mục tiêu:

- Mở rộng `MedicineCatalogRepository` hoặc tạo repository mới.
- Bổ sung implementation JDBC MariaDB cho các method đọc/ghi còn thiếu.
- Không sửa `ThuocServiceImpl` quá nhiều nếu repository chưa compile.

Acceptance:

- Repository compile.
- Không import DAO/ConnectDB.
- Không dùng SQL Server syntax.

### Pass 2 - Refactor ThuocServiceImpl

Mục tiêu:

- Bỏ tất cả DAO field khỏi `ThuocServiceImpl`.
- Inject repository mới.
- `create/update/softDelete` dùng transaction nếu cần.
- Cập nhật `ServerComponentFactory.createThuocService()`.

Acceptance:

- `ThuocServiceImpl` không còn import DAO cũ.
- `ThuocServiceImpl` không còn `new *_Dao`.
- Build compile pass.

### Pass 3 - Promotion repository hardening

Mục tiêu:

- Tạo `PromotionRepository` hoặc `KhuyenMaiRepository`.
- Tạo `JdbcPromotionRepository`.
- Implement đầy đủ method đang được `KhuyenMaiServiceImpl` cần.

Acceptance:

- Repository compile.
- Không import DAO/ConnectDB.
- Không dùng SQL Server syntax.
- Không tự sinh mã bằng `TOP 1`.

### Pass 4 - Refactor KhuyenMaiServiceImpl

Mục tiêu:

- Bỏ tất cả DAO field khỏi `KhuyenMaiServiceImpl`.
- Inject repository mới.
- `create/update/delete` dùng transaction.
- Cập nhật `ServerComponentFactory.createKhuyenMaiService()`.

Acceptance:

- `KhuyenMaiServiceImpl` không còn import DAO cũ.
- `KhuyenMaiServiceImpl` không còn `new *_Dao`.
- Build compile pass.

### Pass 5 - Scan lại + docs result

Chạy scan:

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

Chạy build:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Tạo result:

```text
docs/migration/STEP_19A_LEGACY_CONSOLIDATION_HARDENING_RESULT.md
```

---

## 9. Checklist acceptance

### 9.1. Thuoc

- [ ] `ThuocServiceImpl` không còn import `com.example.pharmacymanagementsystem_qlht.dao.*`.
- [ ] `ThuocServiceImpl` không còn `new *_Dao()`.
- [ ] `ThuocServiceImpl` không gọi `ConnectDB`.
- [ ] `findAll()` chạy qua repository mới.
- [ ] `findAllLoaiHang()` chạy qua repository mới.
- [ ] `findAllLoaiHangNames()` chạy qua repository mới.
- [ ] `findAllHoatChat()` chạy qua repository mới.
- [ ] `findChiTietHoatChatByMaThuoc()` chạy qua repository mới.
- [ ] `create()` chạy qua repository mới và transaction nếu nhiều bảng.
- [ ] `update()` chạy qua repository mới và transaction nếu nhiều bảng.
- [ ] `softDelete()` chạy qua repository mới.
- [ ] `getTongSoLuongTonByMaThuoc()` chạy qua repository mới.
- [ ] `getTenDonViTinhCoBan()` chạy qua repository mới.
- [ ] `getThuocTonKho()` chạy qua repository mới.
- [ ] `getAllTheoLo()` chạy qua repository mới và tránh serialization cycle như logic cũ.

### 9.2. KhuyenMai

- [ ] `KhuyenMaiServiceImpl` không còn import `com.example.pharmacymanagementsystem_qlht.dao.*`.
- [ ] `KhuyenMaiServiceImpl` không còn `new *_Dao()`.
- [ ] `KhuyenMaiServiceImpl` không gọi `ConnectDB`.
- [ ] `findAll()` chạy qua repository mới.
- [ ] `findById()` chạy qua repository mới.
- [ ] `searchByKeyword()` chạy qua repository mới.
- [ ] `findAllLoaiKhuyenMai()` chạy qua repository mới.
- [ ] `findLoaiKhuyenMaiById()` chạy qua repository mới.
- [ ] `findLoaiKhuyenMaiByTen()` chạy qua repository mới.
- [ ] `findChiTietByMaKM()` chạy qua repository mới.
- [ ] `findQuaTangByMaKM()` chạy qua repository mới.
- [ ] `create()` chạy transaction.
- [ ] `update()` chạy transaction.
- [ ] `deleteByMaKM()` chạy transaction.
- [ ] `findActiveOn()` chạy qua repository mới.
- [ ] `findActiveInvoiceOn()` chạy qua repository mới.

### 9.3. Architecture

- [ ] Không sửa root `src/`.
- [ ] Không xóa DAO legacy trong task này.
- [ ] Không xóa `ConnectDB` trong task này.
- [ ] Không xóa `mssql-jdbc` trong task này.
- [ ] Không cho client phụ thuộc DB.
- [ ] Không cho common chứa implementation.
- [ ] Build pass.

---

## 10. Runtime smoke test sau Step 19A

Nếu môi trường hỗ trợ JavaFX/RMI/MariaDB:

### Thuoc

- [ ] Mở màn danh mục thuốc.
- [ ] Load danh sách thuốc.
- [ ] Tìm kiếm thuốc.
- [ ] Thêm thuốc mới với đơn vị cơ bản.
- [ ] Sửa thuốc.
- [ ] Xóa mềm thuốc.
- [ ] Xem tồn kho theo lô.
- [ ] Mở màn bán hàng, lookup thuốc vẫn hoạt động.

### KhuyenMai

- [ ] Mở danh mục khuyến mãi.
- [ ] Load danh sách khuyến mãi.
- [ ] Tìm kiếm khuyến mãi.
- [ ] Thêm khuyến mãi.
- [ ] Sửa khuyến mãi.
- [ ] Xóa khuyến mãi.
- [ ] Lookup khuyến mãi trong bán hàng vẫn hoạt động.
- [ ] Khuyến mãi active theo ngày trả đúng.

Nếu không chạy được runtime trên terminal, ghi rõ lý do vào result và cung cấp checklist test thủ công.

---

## 11. File result bắt buộc tạo

Tạo:

```text
docs/migration/STEP_19A_LEGACY_CONSOLIDATION_HARDENING_RESULT.md
```

Template:

```markdown
# STEP 19A - Legacy Consolidation Hardening Result

## 1. Mục tiêu

Bỏ phụ thuộc của service mới vào legacy DAO cho cụm Thuoc và KhuyenMai.

## 2. Tài liệu đã đọc

- ...
- File không tìm thấy: ...

## 3. Scan trước khi sửa

### Service mới còn gọi DAO cũ

```text
...
```

### DAO/method legacy cần thay

| Service | DAO cũ | Method cũ | Repository mới | Trạng thái |
|---|---|---|---|---|
| ThuocServiceImpl | ... | ... | ... | ... |
| KhuyenMaiServiceImpl | ... | ... | ... | ... |

## 4. File đã tạo

### Repository

- ...

### Repository JDBC

- ...

## 5. File đã sửa

- ...

## 6. Mapping mới

### Thuoc

| Method service | Repository method mới | Ghi chú |
|---|---|---|
| findAll | ... | ... |
| create | ... | transaction |

### KhuyenMai

| Method service | Repository method mới | Ghi chú |
|---|---|---|
| findAll | ... | ... |
| create | ... | transaction |

## 7. Transaction

- Thuoc create/update/softDelete:
- KhuyenMai create/update/delete:
- Audit nếu có:

## 8. Scan sau khi sửa

### ThuocServiceImpl / KhuyenMaiServiceImpl còn DAO không

```text
...
```

### ConnectDB còn bị gọi trong service mới không

```text
...
```

### SQL Server syntax trong repository mới

```text
...
```

## 9. Build result

Command:

```bash
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Result:

```text
...
```

## 10. Runtime smoke test

- [ ] ...
- Nếu chưa chạy được runtime: lý do ...

## 11. Chưa làm / rủi ro còn lại

- ...

## 12. Đề xuất Step 19B

- Archive/xóa DAO legacy trong pharmacy-parent nếu không còn được import.
- Xóa ConnectDB khỏi pharmacy-parent nếu không còn code compile cần.
- Xóa mssql-jdbc khỏi pharmacy-server nếu không còn dùng.
```

---

## 12. Prompt dùng trực tiếp cho Codex

```text
Bạn hãy thực hiện Step 19A: Legacy Consolidation Hardening cho repo PharmacyManagementSystem-Re-architecture.

Bối cảnh:
Step 18 kết luận chưa được xóa root src legacy, chưa được xóa ConnectDB hay DAO legacy, runtime E2E chưa nghiệm thu thật trên môi trường hiện tại. Tuy nhiên còn 2 cụm transition cần xử lý:
- ThuocServiceImpl.java
- KhuyenMaiServiceImpl.java

Mục tiêu:
- Tách query/logic còn cần của cụm Thuoc sang repository mới trong pharmacy-server.
- Tách query/logic còn cần của cụm KhuyenMai sang repository mới trong pharmacy-server.
- Bỏ phụ thuộc của service mới vào legacy DAO.
- Không cho service mới gọi com.example.pharmacymanagementsystem_qlht.dao.*.
- Không cho service mới new *_Dao().
- Không cho service mới gọi ConnectDB.
- Sau đó mới tính Step 19B gỡ ConnectDB, mssql-jdbc, và dọn src cũ an toàn.

Luật:
- Không xóa root src legacy.
- Không xóa ConnectDB trong task này.
- Không xóa DAO legacy trong task này.
- Không xóa mssql-jdbc trong task này.
- Không copy nguyên DAO cũ thành repository mới.
- Chỉ trích query/logic cần thiết từ DAO cũ rồi viết lại repository mới dùng MariaDB/JdbcConnectionProvider/JPA.
- Không refactor lan sang bán hàng, nhập hàng, đổi/trả, report.
- Không JPA hóa hàng loạt.
- Ưu tiên build pass sau từng pass nhỏ.

Việc cần làm:

1. Đọc các docs:
   - docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md nếu có
   - docs/migration/STEP_18_FINAL_E2E_VERIFICATION_AND_LEGACY_CLEANUP_PLAN_UPDATED.md nếu có
   - docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
   - docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md

2. Scan trước khi sửa:
   - service mới còn import DAO cũ/new *_Dao/ConnectDB.
   - các DAO cũ mà ThuocServiceImpl đang dùng.
   - các DAO cũ mà KhuyenMaiServiceImpl đang dùng.
   - SQL Server syntax còn trong DAO cũ cần chuyển.

3. Cụm Thuoc:
   - Mở rộng MedicineCatalogRepository hoặc tạo repository mới phù hợp.
   - Implement bằng JdbcMedicineCatalogRepository hoặc class JDBC mới.
   - Chuyển các method đang cần từ DAO cũ sang repository mới:
     findAll, findAllLoaiHang, findAllLoaiHangNames, findAllHoatChat,
     findChiTietHoatChatByMaThuoc, create, update, softDelete,
     getTongSoLuongTonByMaThuoc, getTenDonViTinhCoBan, getThuocTonKho, getAllTheoLo.
   - Refactor ThuocServiceImpl để không còn import DAO cũ.
   - Cập nhật ServerComponentFactory.createThuocService().
   - Nếu create/update động nhiều bảng, dùng TransactionManager.

4. Cụm KhuyenMai:
   - Tạo PromotionRepository/KhuyenMaiRepository và JdbcPromotionRepository nếu chưa có.
   - Chuyển các method đang cần từ DAO cũ sang repository mới:
     findAll, findById, searchByKeyword, findAllLoaiKhuyenMai,
     findLoaiKhuyenMaiById, findLoaiKhuyenMaiByTen,
     findChiTietByMaKM, findQuaTangByMaKM,
     create, update, deleteByMaKM, findActiveOn, findActiveInvoiceOn.
   - Refactor KhuyenMaiServiceImpl để không còn import DAO cũ.
   - Cập nhật ServerComponentFactory.createKhuyenMaiService().
   - create/update/delete phải dùng TransactionManager.

5. SQL:
   - Không dùng SELECT TOP, ISNULL, GETDATE, OFFSET 0 ROWS FETCH NEXT, COLLATE SQL_.
   - Dùng MariaDB syntax: LIMIT, COALESCE, CURRENT_DATE/CURRENT_TIMESTAMP.
   - Không tự sinh mã bằng TOP 1; dùng CodeGenerationService.

6. Build:
   - Chạy mvn -q -f pharmacy-parent/pom.xml -DskipTests compile.
   - Nếu fail, sửa trong phạm vi Step 19A.

7. Scan sau khi sửa:
   - ThuocServiceImpl và KhuyenMaiServiceImpl không còn DAO/ConnectDB.
   - Repository mới không import DAO/ConnectDB.
   - Ghi rõ legacy DAO còn tồn tại nhưng chưa xóa.

8. Tạo file:
   docs/migration/STEP_19A_LEGACY_CONSOLIDATION_HARDENING_RESULT.md

File result phải có:
- scan trước
- file tạo/sửa
- mapping DAO cũ -> repository mới
- transaction đã dùng
- scan sau
- build result
- runtime smoke test nếu chạy được
- rủi ro còn lại
- đề xuất Step 19B.
```

---

## 13. Đề xuất sau Step 19A

Nếu Step 19A pass:

```text
Step 19B - Archive/remove legacy DAO package in pharmacy-parent
```

Điều kiện:

```text
- Không còn service mới nào import DAO cũ.
- Không còn code mới nào gọi ConnectDB.
- Build pass.
```

Sau Step 19B mới xử lý:

```text
Step 19C - Remove mssql-jdbc from pharmacy-server
```
