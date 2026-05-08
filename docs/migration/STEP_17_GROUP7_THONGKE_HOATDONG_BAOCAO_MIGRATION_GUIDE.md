# STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_GUIDE

## 1. Mục tiêu

Tài liệu này dùng để giao task cho Codex migrate **Nhóm 7: ThongKe + HoatDong + BaoCao** trong project JavaFX quản lý nhà thuốc đang chuyển dần sang:

```text
JavaFX Client
    -> Client Service
    -> RMI Stub
    -> Remote Interface trong pharmacy-common
    -> Server Service
    -> Repository/native query/JDBC transition hoặc JPA read-only
    -> MariaDB
```

Phạm vi nhóm 7:

```text
CN_ThongKe
TKHoatDong
các DAO/proc thống kê còn lại
audit/activity display
BaoCao / export Excel/PDF nếu gắn với thống kê
```

Mục tiêu sau nhóm 7:

- Các màn thống kê không còn gọi DAO / ConnectDB / JDBC trực tiếp từ client.
- Server không còn class thống kê dùng `javafx.collections.ObservableList`.
- Các stored procedure thống kê SQL Server được thay bằng repository/native query MariaDB hoặc service read-only.
- Audit/activity display đọc dữ liệu từ server qua RMI.
- Export Excel/PDF vẫn ở client, nhưng dữ liệu phải lấy qua RMI.
- Không đụng lại nghiệp vụ transaction của bán hàng / đổi trả nếu nhóm 5/6 đã xong.

---

## 2. Điều kiện trước khi bắt đầu

Nhóm 7 nên chạy sau khi các nhóm nghiệp vụ chính đã ổn:

```text
Group 1: Thuoc + KeHang + DonViTinh
Group 2: NhanVien + TaiKhoan + CaiDat
Group 3: KhuyenMai
Group 4: PhieuNhap + PhieuDatHang + TonKho
Group 5: HoaDon + BanHang
Group 6: DoiTra
```

Trước khi migrate sâu, Codex phải đọc result nhóm 5 và nhóm 6 nếu có:

```text
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
```

Nếu nhóm 5/6 chưa có result hoặc chưa chạy ổn:

- Không sửa lại transaction bán hàng / đổi trả.
- Chỉ migrate read-only report nào không phụ thuộc trực tiếp vào transaction đang lỗi.
- Ghi rõ trong result markdown.

Lý do: thống kê đọc dữ liệu sinh ra bởi bán hàng, nhập hàng, đổi/trả. Nếu các luồng đó chưa ổn, report dễ sai hoặc Codex sẽ cố “vá” ngược lại nghiệp vụ đã tách nhóm.

---

## 3. Tài liệu bắt buộc phải đọc

```text
docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md
docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md
docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md
docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md
docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md
docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md
docs/migration/STEP_8_IMPLEMENTATION_RESULT.md
docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md
docs/migration/STEP_10_MASTERDATA_RMI_MIGRATION.md
docs/migration/DAO_MIGRATION_MAPPING.md
docs/migration/QUERY_INVENTORY.md
docs/migration/MIGRATION_PRIORITY.md
```

Nếu có, đọc tiếp:

```text
docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
```

Sau khi làm xong, tạo:

```text
docs/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md
```

---

## 4. Quy tắc kiến trúc bắt buộc

```text
pharmacy-common:
    Chỉ chứa DTO / request / response / enum / exception / remote interface / model shared nếu repo đang dùng legacy model chung.
    Không chứa JavaFX UI.
    Không chứa JPA entity.
    Không chứa Repository implementation.
    Không chứa JDBC / ConnectDB / EntityManager.

pharmacy-client:
    Chứa JavaFX UI, controller, client service, RMI provider, export Excel/PDF.
    Không được kết nối database trực tiếp.
    Không được import DAO.
    Không được import ConnectDB.
    Không được dùng Connection / PreparedStatement / CallableStatement / ResultSet cho nghiệp vụ.
    Không phụ thuộc pharmacy-server.

pharmacy-server:
    Chứa server service, repository, native query/JDBC/JPA read-only, RMI adapter, bootstrap.
    Không chứa JavaFX controller / FXML / Stage / Scene / TableView.
    Không trả ObservableList từ repository/service.
    Không phụ thuộc pharmacy-client.

root src/:
    Legacy/reference.
    Không xóa vội.
    Chỉ dùng để đọc và migrate từng phần.
```

Rule riêng nhóm 7:

- Không làm transaction nghiệp vụ mới, trừ audit/query.
- Không sửa logic tạo hóa đơn / phiếu nhập / đổi trả trong nhóm này.
- Thống kê là read-only query.
- Export Excel/PDF là client concern; không đưa FileChooser, POI/iText UI code vào server.
- Ưu tiên bỏ `javafx-base` khỏi server sau khi các DAO thống kê không còn trả `ObservableList`.

---

## 5. Hiện trạng quan trọng đã scan được

### 5.1. Report service đã có một phần ở server

Hiện đã có các file:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportService.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportServiceImpl.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/ReportRepository.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcReportRepository.java
```

Các method hiện có:

```java
List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request);
List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit);
List<ExpiringLotDTO> getExpiredLots();
List<ExpiringLotDTO> getExpiringLots(int thresholdDays);
```

`JdbcReportRepository` đã dùng MariaDB syntax như `LIMIT`, `CURRENT_DATE()`, `DATE_ADD`, `DATEDIFF`.

Cần kiểm tra tiếp:

- Đã có `ReportRemote` chưa.
- Đã bind RMI chưa.
- Client đã dùng report service chưa.
- Các màn legacy `CN_ThongKe` đã migrate vào `pharmacy-client` chưa.

### 5.2. DAO thống kê legacy/server còn dính JavaFX và stored procedure cũ

Cần xử lý:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ThongKe_Dao.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ThongKeXNT_Dao.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ThongKeTopSP_Dao.java
```

Vấn đề:

- `ThongKe_Dao` dùng `ConnectDB`, `CallableStatement`, stored procedure `sp_ThongKeBanHang_*`, `sp_GetHoaDonTheoThoiGian`, `sp_GetHoaDonTheoTuyChon`.
- `ThongKe_Dao` trả `ObservableList<HoaDonDisplay>` và import `javafx.collections.FXCollections`.
- `ThongKeXNT_Dao` dùng `ObservableList`, `FXCollections`, stored procedure `sp_ThongKeXNT`, `sp_ThongKeThuocHetHan`.
- `ThongKeTopSP_Dao` còn SQL Server syntax `SELECT TOP (?)`, `ISNULL`, `CAST(... AS DATE)`.
- Đây chính là lý do nhóm 7 có mục tiêu “server sạch JavaFX hơn”.

### 5.3. Controller thống kê legacy còn gọi DAO trực tiếp

Cần rà:

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeBanHang_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeTopSanPham_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeXNT_Ctrl.java
```

Vấn đề:

- `ThongKeBanHang_Ctrl` gọi `new ThongKe_Dao()`.
- `ThongKeTopSanPham_Ctrl` gọi `new ThongKeTopSP_Dao()`.
- `ThongKeXNT_Ctrl` gọi `new ThongKeXNT_Dao()`.
- Export Excel/PDF có thể giữ ở client.
- Nhưng dữ liệu phải lấy qua client service/RMI.

### 5.4. TKHoatDong legacy còn gọi DAO trực tiếp

Cần rà:

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoatDong/TKHoatDong_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoatDong/ChiTietHoatDong_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/HoatDong_Dao.java
```

Vấn đề:

- `TKHoatDong_Ctrl` gọi `HoatDong_Dao.selectAll()` rồi filter phía client.
- `HoatDong_Dao` dùng `ConnectDB`.
- `AuditService` hiện đã có `logAction(...)`, nhưng `AuditLogRepository` hiện mới có `insert(...)`, chưa có read/query để hiển thị activity.

---

## 6. Lệnh scan nhanh trước khi sửa

Chạy từ root repo.

### 6.1. Scan docs group

```powershell
Get-ChildItem docs -Recurse -File -Include *.md |
  Select-Object FullName |
  Sort-Object FullName
```

### 6.2. Scan remote/service/report hiện có

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "ReportRemote|ReportService|ReportRepository|AuditLogRemote|HoatDongRemote|AuditService|AuditLogRepository|ThongKeRemote" |
  Select-Object Path, LineNumber, Line
```

### 6.3. Scan controller nhóm 7 còn DAO/JDBC

```powershell
$group7Controllers = @(
  "ThongKeBanHang_Ctrl.java",
  "ThongKeTopSanPham_Ctrl.java",
  "ThongKeXNT_Ctrl.java",
  "TKHoatDong_Ctrl.java",
  "ChiTietHoatDong_Ctrl.java"
)

foreach ($name in $group7Controllers) {
  Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Filter $name |
    Select-String -Pattern "import .*dao|new .*_Dao|ConnectDB|java.sql|Connection|PreparedStatement|CallableStatement|ResultSet" |
    Select-Object Path, LineNumber, Line
}

foreach ($name in $group7Controllers) {
  Get-ChildItem src/main/java -Recurse -File -Filter $name |
    Select-String -Pattern "import .*dao|new .*_Dao|ConnectDB|java.sql|Connection|PreparedStatement|CallableStatement|ResultSet" |
    Select-Object Path, LineNumber, Line
}
```

### 6.4. Scan server còn JavaFX

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "javafx|ObservableList|FXCollections|TableView|Button|Label|Stage|Scene|FXMLLoader" |
  Select-Object Path, LineNumber, Line
```

### 6.5. Scan SQL Server-specific thống kê/proc

```powershell
Get-ChildItem . -Recurse -File -Include *ThongKe*.java,*HoatDong*.java,*Report*.java |
  Select-String -Pattern "SELECT TOP|TOP \(|ISNULL|GETDATE|DATEPART|CONVERT|CallableStatement|prepareCall|sp_ThongKe|sp_GetHoaDon|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

---

## 7. Thiết kế đề xuất cho group 7

### 7.1. Remote boundary

Tạo hoặc hoàn thiện 2 remote read-only:

```text
pharmacy-common/src/main/java/com/example/pharmacy/common/remote/ReportRemote.java
pharmacy-common/src/main/java/com/example/pharmacy/common/remote/AuditLogRemote.java
```

Binding name:

```java
ReportRemote.BINDING_NAME = "ReportRemoteService";
AuditLogRemote.BINDING_NAME = "AuditLogRemoteService";
```

Nếu muốn bám tên UI cũ, có thể dùng `HoatDongRemote`, nhưng nên ghi rõ mapping:

```text
TKHoatDong UI cũ -> AuditLogRemote đọc bảng audit_log mới
```

Không nên tiếp tục dùng `HoatDong_Dao` cũ để đọc bảng `HoatDong` nếu Step 8 đã chuyển audit sang `audit_log`.

### 7.2. ReportRemote method gợi ý

```java
List<RevenuePointDTO> getRevenueByDateRange(DateRangeRequest request) throws RemoteException;

List<InvoiceReportDTO> getInvoicesByDateRange(DateRangeRequest request) throws RemoteException;

List<SalesSummaryDTO> getSalesSummary(DateRangeRequest request, ReportGroupBy groupBy) throws RemoteException;

List<TopSellingProductDTO> getTopSellingProducts(DateRangeRequest request, int limit) throws RemoteException;

List<TopSellingProductDTO> getTopRevenueProducts(DateRangeRequest request, int limit) throws RemoteException;

List<InventoryMovementDTO> getInventoryMovement(DateRangeRequest request) throws RemoteException;

List<ExpiringLotDTO> getExpiredLots() throws RemoteException;

List<ExpiringLotDTO> getExpiringLots(int thresholdDays) throws RemoteException;
```

Reuse DTO hiện có nếu có:

```text
RevenuePointDTO
TopSellingProductDTO
ExpiringLotDTO
DateRangeRequest
```

Bổ sung DTO nếu UI cần:

```text
SalesSummaryDTO
InvoiceReportDTO
InventoryMovementDTO
AuditLogSearchRequest
```

### 7.3. AuditLogRemote / Activity display

```java
List<AuditLogDTO> findAuditLogs(AuditLogSearchRequest request) throws RemoteException;

AuditLogDTO findAuditLogByCode(String auditCode) throws RemoteException;
```

`AuditLogSearchRequest` nên có:

```java
String keyword;
LocalDate fromDate;
LocalDate toDate;
String action;
String entityName;
String usernameOrEmployeeId;
int limit;
int offset;
```

Nếu chưa làm phân trang, vẫn nên có `limit` default để tránh load toàn bộ log.

### 7.4. Server service/repository

Bổ sung/hoàn thiện:

```text
ReportService
ReportServiceImpl
ReportRepository
JdbcReportRepository
```

Tạo thêm nếu cần:

```text
AuditLogQueryService
AuditLogQueryServiceImpl
AuditLogQueryRepository
JdbcAuditLogQueryRepository
```

Hoặc mở rộng `AuditLogRepository`:

```java
void insert(AuditLogDTO auditLog);
List<AuditLogDTO> findByCriteria(AuditLogSearchRequest request);
AuditLogDTO findByCode(String auditCode);
```

### 7.5. RMI adapter / bootstrap

Tạo:

```text
pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/ReportRemoteAdapter.java
pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/AuditLogRemoteAdapter.java
```

Cập nhật:

```text
ServerComponentFactory
RmiServerBootstrap
RmiClientProvider
```

Yêu cầu:

- `ServerComponentFactory.createReportService()` hiện đã có, reuse nếu đúng.
- Thêm `createAuditLogQueryService()` nếu cần.
- `RmiServerBootstrap` bind `ReportRemoteService` và `AuditLogRemoteService`.
- `RmiClientProvider.getReportRemote()`.
- `RmiClientProvider.getAuditLogRemote()`.

### 7.6. Client service

Tạo:

```text
pharmacy-client/src/main/java/com/example/pharmacy/client/service/ReportClientService.java
pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiReportClientService.java
pharmacy-client/src/main/java/com/example/pharmacy/client/service/AuditLogClientService.java
pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiAuditLogClientService.java
```

Controller chỉ giữ:

- UI event.
- Date range / combo filter.
- Render TableView / Chart.
- Export Excel/PDF.
- Gọi client service.

Không còn:

```java
import com.example.pharmacymanagementsystem_qlht.dao.*;
new ThongKe_Dao()
new ThongKeTopSP_Dao()
new ThongKeXNT_Dao()
new HoatDong_Dao()
ConnectDB
Connection
PreparedStatement
CallableStatement
ResultSet
```

---

## 8. Query migration notes

### 8.1. Không gọi stored procedure SQL Server

Các stored procedure cũ cần thay bằng native query/repository:

```text
sp_ThongKeBanHang_HomNay
sp_ThongKeBanHang_TuanNay
sp_ThongKeBanHang_ThangNay
sp_ThongKeBanHang_NamNay
sp_ThongKeBanHang_TuyChon
sp_GetHoaDonTheoThoiGian
sp_GetHoaDonTheoTuyChon
sp_ThongKeXNT
sp_ThongKeThuocHetHan
sp_Top5SanPham_*
sp_HangHetHan
sp_HangSapHetHan
```

### 8.2. MariaDB syntax

Thay:

```text
SELECT TOP (?)     -> ORDER BY ... LIMIT ?
ISNULL(x, y)       -> COALESCE(x, y)
GETDATE()          -> CURRENT_TIMESTAMP hoặc CURRENT_DATE()
CAST(col AS DATE)  -> DATE(col)
DATEPART(...)      -> YEAR(), MONTH(), WEEK(), DATE()
```

### 8.3. Report bán hàng

Nên tính từ:

```text
HoaDon
ChiTietHoaDon
PhieuTraHang
ChiTietPhieuTraHang
```

Cần xác nhận nhóm 5/6 đã chốt cách tính:

```text
TongGiaTri = tổng tiền bán trước trả?
GiamGia = tổng giảm dòng/hóa đơn?
GiaTriDonTra = tổng tiền trả?
DoanhThu = TongGiaTri - GiaTriDonTra?
```

Nếu chưa chắc, giữ công thức cũ và ghi TODO trong result, không tự đổi nghiệp vụ.

### 8.4. XNT

XNT là phần dễ sai nhất.

Nếu chưa đủ lịch sử nhập/xuất/trả/đổi để tính chính xác, có thể chia phase:

- Phase A: hiển thị tồn hiện tại theo `Thuoc_SP_TheoLo` / `ChiTietDonViTinh`.
- Phase B: tính XNT theo kỳ sau khi xác nhận đầy đủ nguồn nhập/xuất/trả/đổi.

Không được bịa công thức XNT nếu chưa xác nhận từ SQL cũ/proc cũ.

### 8.5. Activity display

Ưu tiên dùng bảng mới:

```text
audit_log
```

Map sang UI cũ nếu cần:

```text
audit_code     -> MaHDong / MaHD
action         -> LoaiHD
entity_name    -> BangDL
created_at     -> ThoiGian
full_name      -> NhanVien.TenNV
description    -> NoiDung
```

---

## 9. Cách chia task nhỏ cho Codex

### Pass 0 - Read docs + scan only

- Đọc docs migration.
- Đọc result nhóm 5/6 nếu có.
- Scan group 7.
- Không sửa code.

### Pass 1 - Common contract

- Tạo `ReportRemote`.
- Tạo `AuditLogRemote` hoặc `HoatDongRemote`.
- Tạo/bổ sung request DTO cần thiết.

### Pass 2 - Server report remote

- Tạo `ReportRemoteAdapter`.
- Bổ sung `ReportService/Repository` nếu thiếu method.
- Bind RMI.
- Repository trả `List`, không trả `ObservableList`.
- Query dùng MariaDB syntax.

### Pass 3 - Server audit/activity query

- Bổ sung read/query cho audit log.
- Tạo `AuditLogRemoteAdapter`.
- Bind RMI.
- `TKHoatDong` load log qua RMI.

### Pass 4 - Client service

- Tạo `ReportClientService/RmiReportClientService`.
- Tạo `AuditLogClientService/RmiAuditLogClientService`.
- Cập nhật `RmiClientProvider`.

### Pass 5 - Refactor CN_ThongKe

- Refactor `ThongKeBanHang_Ctrl`.
- Refactor `ThongKeTopSanPham_Ctrl`.
- Refactor `ThongKeXNT_Ctrl`.

### Pass 6 - Refactor TKHoatDong

- Refactor `TKHoatDong_Ctrl`.
- Refactor `ChiTietHoatDong_Ctrl`.

### Pass 7 - Dọn server JavaFX technical debt

- Loại bỏ hoặc không còn use các DAO thống kê server cũ có `ObservableList`.
- Nếu xóa không an toàn, đánh dấu deprecated và đảm bảo không client mới nào gọi.
- Cố gắng bỏ `javafx-base` khỏi `pharmacy-server/pom.xml` nếu không còn class server cần JavaFX.

### Pass 8 - Build + docs result

- Chạy build.
- Chạy scan.
- Tạo result markdown.

---

## 10. Checklist acceptance group 7

### Architecture

- [ ] Có `ReportRemote`.
- [ ] Có `ReportRemoteAdapter`.
- [ ] `RmiServerBootstrap` bind `ReportRemoteService`.
- [ ] `RmiClientProvider` có `getReportRemote()`.
- [ ] Có `ReportClientService`.
- [ ] Có remote/service cho audit/activity display.
- [ ] TKHoatDong không còn gọi `HoatDong_Dao`.

### Client clean

- [ ] `ThongKeBanHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `ThongKeTopSanPham_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `ThongKeXNT_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `TKHoatDong_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `ChiTietHoatDong_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] Export Excel/PDF vẫn ở client.

### Server clean

- [ ] Repository/service report trả `List`, không trả `ObservableList`.
- [ ] Implementation mới không import `javafx`.
- [ ] Không dùng `CallableStatement` cho stored proc SQL Server trong implementation mới.
- [ ] Không dùng `SELECT TOP`.
- [ ] Không dùng `ISNULL`.
- [ ] Không dùng `GETDATE`.
- [ ] Không dùng `ConnectDB` ở code mới.

### Build

- [ ] `mvn -q -f pharmacy-parent/pom.xml -DskipTests compile`
- [ ] Nếu fail, ghi rõ module/file/lỗi.

---

## 11. Test thủ công cần làm

### Thống kê bán hàng

- [ ] Mở màn thống kê bán hàng.
- [ ] Chọn “Hôm nay”.
- [ ] Chọn “Tuần này”.
- [ ] Chọn “Tháng này”.
- [ ] Chọn “Năm nay”.
- [ ] Chọn “Tùy chọn” với from/to hợp lệ.
- [ ] Chọn from > to phải báo lỗi hoặc clear data.
- [ ] Bảng doanh thu có dữ liệu đúng format.
- [ ] Chart render không crash.
- [ ] Xuất Excel.
- [ ] Xuất PDF.

### Top sản phẩm

- [ ] Top bán chạy theo số lượng.
- [ ] Top doanh thu.
- [ ] Top 5/10/20.
- [ ] Tùy chọn khoảng ngày.
- [ ] Chart pie/bar render.
- [ ] Xuất Excel/PDF.

### XNT / hết hạn

- [ ] XNT hôm nay.
- [ ] XNT tuần/tháng/năm.
- [ ] Tùy chọn khoảng ngày.
- [ ] Search nhanh theo mã/tên thuốc.
- [ ] Thuốc hết hạn load được.
- [ ] Xuất Excel/PDF.

### Hoạt động / audit

- [ ] Mở TKHoatDong.
- [ ] Load danh sách audit/activity.
- [ ] Filter hôm nay/tuần/tháng.
- [ ] Search theo mã log / user / entity / action.
- [ ] Mở chi tiết log.
- [ ] Không crash nếu log thiếu user hoặc description null.

### Regression

- [ ] Đăng nhập vẫn chạy.
- [ ] Bán hàng vẫn chạy.
- [ ] Đổi/trả vẫn chạy.
- [ ] RMI server start được.
- [ ] Server không còn phụ thuộc JavaFX nếu đã dọn xong.

---

## 12. Prompt dùng trực tiếp cho Codex

```text
Bạn đang làm trong repo JavaFX quản lý nhà thuốc đã migrate dần sang kiến trúc pharmacy-parent gồm:
- pharmacy-common
- pharmacy-client
- pharmacy-server

Task lần này: migrate Nhóm 7: ThongKe + HoatDong + BaoCao.

Phạm vi:
- CN_ThongKe:
  - ThongKeBanHang_Ctrl
  - ThongKeTopSanPham_Ctrl
  - ThongKeXNT_Ctrl
- TKHoatDong:
  - TKHoatDong_Ctrl
  - ChiTietHoatDong_Ctrl
- Các DAO/proc thống kê còn lại
- Audit/activity display
- Export Excel/PDF gắn với report

Mục tiêu:
- Client không còn gọi DAO / ConnectDB / JDBC cho thống kê/hoạt động.
- Report data đi qua Client Service -> RMI -> ReportRemote/AuditLogRemote -> Server Service -> Repository -> MariaDB.
- Server report trả List/DTO, không trả ObservableList.
- Dọn các DAO thống kê server còn dính javafx nếu an toàn.
- Export Excel/PDF giữ ở client, không đưa FileChooser/POI/iText UI code vào server.

Trước khi code:
1. Đọc docs migration:
   docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md
   docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md
   docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md
   docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md
   docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md
   docs/migration/STEP_8_IMPLEMENTATION_RESULT.md
   docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md
   docs/migration/STEP_10_MASTERDATA_RMI_MIGRATION.md
   docs/migration/DAO_MIGRATION_MAPPING.md
   docs/migration/QUERY_INVENTORY.md
   docs/migration/MIGRATION_PRIORITY.md

2. Đọc các result nhóm đã chạy nếu có:
   docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
   docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
   docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
   docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
   docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
   docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md

3. Nếu group 5/6 chưa ổn, không sửa transaction bán hàng/đổi trả. Chỉ làm read-only report an toàn và ghi rõ.

Luật kiến trúc:
- Không cho client phụ thuộc server.
- Common không chứa JavaFX/JPA/JDBC/repository implementation.
- Server không chứa JavaFX UI/controller/FXML.
- Client không import DAO, ConnectDB, java.sql cho nghiệp vụ.
- Không xóa root src legacy.
- Không sửa lại nghiệp vụ transaction của nhóm 5/6.
- Không tự bịa công thức XNT nếu chưa xác nhận từ SQL cũ/proc cũ.

PASS 0 - Scan:
- Scan các controller nhóm 7 còn DAO/ConnectDB/JDBC.
- Scan ThongKe_Dao, ThongKeXNT_Dao, ThongKeTopSP_Dao, HoatDong_Dao.
- Scan server còn javafx/ObservableList.
- Scan các proc SQL Server còn dùng.

PASS 1 - Common contract:
- Tạo ReportRemote nếu chưa có, binding "ReportRemoteService".
- Tạo AuditLogRemote hoặc HoatDongRemote cho activity display, binding rõ ràng.
- Tạo/bổ sung DTO/request cần thiết:
  DateRangeRequest nếu đã có thì reuse.
  RevenuePointDTO/TopSellingProductDTO/ExpiringLotDTO nếu đã có thì reuse.
  Bổ sung SalesSummaryDTO, InvoiceReportDTO, InventoryMovementDTO, AuditLogSearchRequest nếu UI cần.

PASS 2 - Server report:
- Reuse ReportService/ReportServiceImpl/ReportRepository/JdbcReportRepository hiện có nếu phù hợp.
- Bổ sung method còn thiếu cho:
  doanh thu bán hàng,
  danh sách hóa đơn theo khoảng ngày,
  top sản phẩm bán chạy,
  top sản phẩm doanh thu,
  XNT/tồn kho,
  thuốc hết hạn/sắp hết hạn.
- Không dùng CallableStatement/stored procedure SQL Server trong implementation mới.
- Dùng MariaDB syntax: LIMIT, COALESCE, CURRENT_DATE, DATE(), DATE_ADD.

PASS 3 - Server audit/activity:
- AuditService hiện có logAction để ghi audit.
- Bổ sung read/query cho audit log:
  findAuditLogs(AuditLogSearchRequest request)
  findAuditLogByCode(String code)
- Ưu tiên đọc bảng audit_log mới.
- Nếu UI cũ cần model HoatDong, map AuditLogDTO sang dữ liệu hiển thị tương đương.

PASS 4 - RMI:
- Tạo ReportRemoteAdapter.
- Tạo AuditLogRemoteAdapter/HoatDongRemoteAdapter.
- Cập nhật ServerComponentFactory.
- Cập nhật RmiServerBootstrap để bind remote.
- Cập nhật RmiClientProvider.

PASS 5 - Client service:
- Tạo ReportClientService/RmiReportClientService.
- Tạo AuditLogClientService/RmiAuditLogClientService.
- Không có DAO/JDBC/ConnectDB trong client service.

PASS 6 - Refactor CN_ThongKe:
- Refactor ThongKeBanHang_Ctrl, ThongKeTopSanPham_Ctrl, ThongKeXNT_Ctrl.
- Controller chỉ xử lý UI/date range/chart/table/export.
- Data lấy qua ReportClientService.
- Export Excel/PDF giữ nguyên ở client nếu có thể.

PASS 7 - Refactor TKHoatDong:
- Refactor TKHoatDong_Ctrl và ChiTietHoatDong_Ctrl.
- Data lấy qua AuditLogClientService.
- Không gọi HoatDong_Dao từ client.

PASS 8 - Dọn technical debt:
- Đảm bảo implementation mới không dùng ObservableList ở server.
- Nếu không còn class server nào cần JavaFX, bỏ javafx-base khỏi pharmacy-server/pom.xml.
- Nếu còn class legacy chưa xóa được, ghi rõ trong result.

PASS 9 - Build/docs:
- Chạy:
  mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
- Chạy scan sai tầng.
- Tạo docs/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md

Acceptance:
- Không còn DAO/ConnectDB/JDBC trong controller group 7 ở pharmacy-client.
- ReportRemote được bind ở RMI server.
- Activity/Audit remote được bind ở RMI server.
- Server report không trả ObservableList.
- Implementation mới không dùng stored procedure SQL Server.
- Build compile pass hoặc ghi rõ lỗi còn lại.
```

---

## 13. Template result markdown

Tạo file:

```text
docs/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md
```

Template:

```markdown
# STEP 17 - GROUP 7 THONGKE HOATDONG BAOCAO MIGRATION RESULT

## 1. Mục tiêu

Migrate nhóm 7: CN_ThongKe, TKHoatDong, report/query/audit display sang Client -> RMI -> Server -> Repository -> MariaDB.

## 2. Tài liệu đã đọc

- ...
- File không tìm thấy: ...

## 3. Phạm vi đã làm

- [ ] ThongKeBanHang
- [ ] ThongKeTopSanPham
- [ ] ThongKeXNT
- [ ] TKHoatDong
- [ ] ChiTietHoatDong
- [ ] Export Excel/PDF
- [ ] Audit/activity display

## 4. File đã tạo

### pharmacy-common
- ...

### pharmacy-client
- ...

### pharmacy-server
- ...

## 5. File đã sửa

- ...

## 6. Flow mới

### Report

```text
...
```

### Audit/activity display

```text
...
```

## 7. Mapping legacy -> new

| Legacy | New | Trạng thái | Ghi chú |
|---|---|---|---|
| ThongKe_Dao | ReportRepository/ReportService | ... | ... |
| ThongKeTopSP_Dao | ReportRepository/ReportService | ... | ... |
| ThongKeXNT_Dao | ReportRepository/ReportService | ... | ... |
| HoatDong_Dao | AuditLogRepository/AuditLogRemote | ... | ... |
| ThongKeBanHang_Ctrl | ReportClientService | ... | ... |
| TKHoatDong_Ctrl | AuditLogClientService | ... | ... |

## 8. Stored procedure / SQL migration

| Proc / query cũ | Query mới | Trạng thái |
|---|---|---|
| sp_ThongKeBanHang_* | ... | ... |
| sp_GetHoaDonTheoThoiGian | ... | ... |
| sp_ThongKeXNT | ... | ... |
| sp_ThongKeThuocHetHan | ... | ... |

## 9. Scan sai tầng

### Client group 7 DAO/ConnectDB/JDBC

```text
...
```

### Server JavaFX / ObservableList

```text
...
```

### Common implementation leakage

```text
...
```

### SQL Server syntax

```text
...
```

## 10. Build result

Command:

```bash
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Result:

```text
...
```

## 11. Test thủ công cần làm

- [ ] ...
- [ ] ...

## 12. Chưa làm / rủi ro còn lại

- ...
```

---

## 14. Ghi chú chiến lược

Nhóm 7 là nhóm “dọn report/query”, không phải nhóm sửa nghiệp vụ chính. Đừng để Codex lan sang sửa bán hàng, nhập hàng, đổi trả.

Thứ tự an toàn nhất:

1. Hoàn thiện `ReportRemote` từ `ReportService` đã có.
2. Refactor report read-only trước.
3. Refactor `TKHoatDong` sau, dùng `audit_log` mới thay vì `HoatDong` cũ.
4. Dọn `ObservableList` khỏi server.
5. Cuối cùng mới cân nhắc bỏ `javafx-base` khỏi `pharmacy-server/pom.xml`.

Nếu XNT chưa đủ dữ liệu để tính đúng, ghi rõ phase còn thiếu; không bịa công thức.
