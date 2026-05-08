# STEP_16_GROUP6_DOITRA_MIGRATION_GUIDE

## Mục tiêu tài liệu

Tài liệu này dùng để giao task cho Codex migrate **Nhóm 6: DoiTra** trong project JavaFX quản lý nhà thuốc đang chuyển sang kiến trúc:

```text
JavaFX Client
    -> Client Service
    -> RMI Stub
    -> Remote Interface trong pharmacy-common
    -> Server Service
    -> Repository / JPA hoặc JDBC transition có transaction
    -> MariaDB
```

Phạm vi nhóm 6 rất nhạy cảm vì đổi/trả phụ thuộc trực tiếp vào:

- Hóa đơn gốc và chi tiết hóa đơn đã bán.
- Tồn kho theo lô.
- Số lượng đã đổi / đã trả trước đó.
- Khách hàng gắn với hóa đơn.
- UserContext / nhân viên đang thao tác.
- Audit log.
- Transaction rollback khi bất kỳ bước nào lỗi.

Mục tiêu sau nhóm 6:

- `LapPhieuDoi`, `LapPhieuTra`, `TKPhieuDoiHang`, `TKPhieuTraHang` không còn gọi DAO / ConnectDB / JDBC trực tiếp ở client.
- Đổi/trả được điều phối bởi server service trong một transaction rõ ràng.
- Audit được ghi ở server service.
- Client chỉ còn UI logic, validate input cơ bản, gọi client service / RMI.
- Không phá flow `HoaDon + BanHang` đã migrate ở nhóm 5.

---

## 0. Điều kiện trước khi bắt đầu

Nhóm 6 **chỉ nên làm sau khi nhóm 5: HoaDon + BanHang đã chạy ổn**.

Trước khi sửa code, Codex phải kiểm tra các file nhóm 5 đã tồn tại:

```text
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_GUIDE.md
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
```

Nếu `STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md` chưa tồn tại thì:

- Không migrate sâu nhóm 6 ngay.
- Chỉ scan hiện trạng nhóm 6.
- Ghi rõ “Group 5 chưa có result, chưa đủ điều kiện migrate Group 6”.
- Dừng trước khi sửa transaction đổi/trả.

Lý do: đổi/trả cần dựa vào `HoaDonService` / `HoaDonRemote` / repository hóa đơn sau nhóm 5. Nếu nhóm 5 chưa xong mà migrate nhóm 6, Codex dễ tạo trùng service, trùng query, hoặc quay lại gọi DAO cũ.

---

## 1. Tài liệu bắt buộc phải đọc trước khi sửa code

### 1.1. Tài liệu nền tảng

```text
docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md
docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md
docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md
docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md
docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md
docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md
docs/migration/STEP_8_IMPLEMENTATION_RESULT.md
docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md
docs/migration/DAO_MIGRATION_MAPPING.md
docs/migration/QUERY_INVENTORY.md
docs/migration/MIGRATION_PRIORITY.md
```

### 1.2. Tài liệu các nhóm đã chạy

Nếu có, đọc kỹ các file sau trong `docs/` hoặc `docs/migration/`:

```text
docs/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
docs/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
docs/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
docs/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
```

Nếu file nào không tồn tại thì ghi rõ trong result markdown, không tự bịa.

### 1.3. Tài liệu result cần tạo sau khi làm

Sau khi migrate nhóm 6, Codex phải tạo:

```text
docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
```

File result phải ghi rõ:

- Đã đọc file nào.
- Đã sửa file nào.
- Flow mới.
- Mapping legacy -> new.
- Scan sai tầng.
- Build result.
- Test thủ công cần làm.
- Rủi ro còn lại.

---

## 2. Quy tắc kiến trúc bắt buộc

Không được phá các rule đã chốt trong docs migration:

```text
pharmacy-common:
    Chỉ chứa DTO / request / response / enum / exception / remote interface / model shared nếu repo hiện tại đang dùng legacy model chung.
    Không chứa JavaFX UI.
    Không chứa JPA entity.
    Không chứa Repository implementation.
    Không chứa JDBC / ConnectDB / EntityManager.

pharmacy-client:
    Chứa JavaFX UI, controller, client service, RMI provider.
    Không được kết nối database trực tiếp.
    Không được import ConnectDB.
    Không được import DAO.
    Không được dùng Connection / PreparedStatement / ResultSet cho nghiệp vụ.
    Không phụ thuộc pharmacy-server.

pharmacy-server:
    Chứa server service, transaction service, repository, RMI adapter, bootstrap.
    Được dùng JPA/Hibernate hoặc JDBC transition với MariaDB.
    Không chứa JavaFX controller / FXML / Stage / Scene / control UI.
    Không phụ thuộc pharmacy-client.

root src/:
    Legacy/reference.
    Không xóa vội.
    Chỉ dùng để đọc và migrate từng phần.
```

Rule riêng cho nhóm 6:

- Không tạo phiếu đổi/trả bằng nhiều remote call rời rạc từ client.
- Tạo phiếu đổi/trả phải là **một method server-side transaction**.
- Header phiếu, detail phiếu, tồn kho, trạng thái liên quan và audit phải cùng commit hoặc cùng rollback.
- Không dùng `MAX(code) + 1`, không dùng `SELECT TOP 1`, không sinh mã ở client.
- Không sửa đại trà hóa đơn / bán hàng nếu nhóm 5 đã xong; chỉ dùng lại service/repository đã có.
- Không migrate sang nhóm 7 thống kê / báo cáo trong task này.

---

## 3. Lệnh scan nhanh trước khi sửa

Chạy từ root repo.

### 3.1. Scan file docs nhóm gần nhất

```powershell
Get-ChildItem docs -Recurse -File -Include *.md |
  Select-Object FullName |
  Sort-Object FullName

Get-ChildItem docs -Recurse -File -Include *GROUP*.md,*MIGRATION*.md |
  Select-Object FullName |
  Sort-Object FullName
```

### 3.2. Scan remote/service/repository hiện có liên quan hóa đơn và đổi/trả

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "HoaDonRemote|HoaDonService|HoaDonRepository|DoiTraRemote|DoiTraService|PhieuDoiRemote|PhieuTraRemote|PhieuDoiHangService|PhieuTraHangService" |
  Select-Object Path, LineNumber, Line
```

### 3.3. Scan client nhóm 6 còn gọi DAO / DB trực tiếp

```powershell
$group6ClientPatterns = @(
  "LapPhieuDoiHang_Ctrl.java",
  "LapPhieuTraHang_Ctrl.java",
  "TKPhieuDoiHang_Ctrl.java",
  "ChiTietPhieuDoiHang_Ctrl.java",
  "TKPhieuTraHang_Ctrl.java",
  "ChiTietPhieuTraHang_Ctrl.java"
)

foreach ($name in $group6ClientPatterns) {
  Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Filter $name |
    Select-String -Pattern "import .*dao|new .*_Dao|ConnectDB|java.sql|Connection|PreparedStatement|ResultSet|CallableStatement" |
    Select-Object Path, LineNumber, Line
}
```

Nếu group 6 controller chưa được copy vào `pharmacy-client`, scan thêm legacy:

```powershell
foreach ($name in $group6ClientPatterns) {
  Get-ChildItem src/main/java -Recurse -File -Filter $name |
    Select-String -Pattern "import .*dao|new .*_Dao|ConnectDB|java.sql|Connection|PreparedStatement|ResultSet|CallableStatement" |
    Select-Object Path, LineNumber, Line
}
```

### 3.4. Scan server không được dính JavaFX UI

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "javafx.scene|javafx.fxml|javafx.stage|javafx.application|TableView|Button|Label|FXMLLoader|Stage|Scene" |
  Select-Object Path, LineNumber, Line
```

Nếu chỉ còn technical debt cũ đã ghi trong docs thì ghi rõ. Không để class mới của group 6 import JavaFX.

### 3.5. Scan common không được dính implementation

```powershell
Get-ChildItem pharmacy-parent/pharmacy-common/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "javafx|jakarta.persistence|javax.persistence|org.hibernate|ConnectDB|DriverManager|EntityManager|RepositoryImpl|ServiceImpl" |
  Select-Object Path, LineNumber, Line
```

### 3.6. Scan SQL Server-specific còn trong DAO đổi/trả

```powershell
Get-ChildItem . -Recurse -File -Include *PhieuDoi*Dao.java,*PhieuTra*Dao.java,*ChiTietPhieuDoi*Dao.java,*ChiTietPhieuTra*Dao.java |
  Select-String -Pattern "TOP 1|GETDATE|ISNULL|CONVERT|DATEPART|CallableStatement|OUTPUT INSERTED|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

---

## 4. File legacy group 6 cần rà

### 4.1. Controller lập phiếu đổi/trả

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDoi/LapPhieuDoiHang_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuTra/LapPhieuTraHang_Ctrl.java
```

Hiện trạng cần chú ý:

- `LapPhieuDoiHang_Ctrl` đang import `dao.*`, dùng `HoaDon_Dao`, `ChiTietHoaDon_Dao`, `KhachHang_Dao`, `ChiTietPhieuDoiHang_Dao`, `Thuoc_SP_TheoLo_Dao`, `PhieuDoiHang_Dao`.
- `LapPhieuTraHang_Ctrl` đang import `dao.*`, dùng `HoaDon_Dao`, `ChiTietHoaDon_Dao`, `KhachHang_Dao`, `ChiTietPhieuTraHang_Dao`, `PhieuTraHang_Dao`.
- Hai controller này có nhiều UI logic hợp lệ, nhưng nghiệp vụ transaction không nên nằm ở client.

### 4.2. Controller tìm kiếm và chi tiết phiếu đổi/trả

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/TKPhieuDoiHang_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/ChiTietPhieuDoiHang_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/TKPhieuTraHang_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/ChiTietPhieuTraHang_Ctrl.java
```

Hiện trạng cần chú ý:

- `TKPhieuDoiHang_Ctrl` đang dùng `PhieuDoiHang_Dao.selectAll()` rồi filter phía client.
- `TKPhieuTraHang_Ctrl` đang dùng `PhieuTraHang_Dao.selectAll()` rồi filter phía client.
- Detail controller đang dùng DAO detail để load chi tiết.
- Phần export PDF có thể giữ ở client vì đó là UI/export concern, nhưng dữ liệu phải lấy qua client service/RMI.

### 4.3. DAO legacy cần thay thế

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/PhieuDoiHang_Dao.java
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ChiTietPhieuDoiHang_Dao.java
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/PhieuTraHang_Dao.java
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ChiTietPhieuTraHang_Dao.java
```

Các vấn đề đã thấy:

- `PhieuDoiHang_Dao.generateNewMaPD()` dùng `SELECT TOP 1`.
- `PhieuTraHang_Dao.generateNewMaPT()` dùng `SELECT TOP 1`.
- `ChiTietPhieuDoiHang_Dao.tongSoLuongDaDoi()` dùng `ISNULL`.
- `ChiTietPhieuTraHang_Dao` dùng `ConnectDB.getInstance()`, `Connection`, `PreparedStatement`.
- Các DAO detail gọi ngược nhiều DAO khác trong mapping, dễ gây N+1 query và khó transaction.
- Tất cả phải được thay bằng server repository/service, không gọi từ client.

### 4.4. Model / view model liên quan

```text
pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/PhieuDoiHang.java
pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietPhieuDoiHang.java
pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/PhieuTraHang.java
pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietPhieuTraHang.java
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/DoiHangItem.java
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/TraHangItem.java
```

Lưu ý:

- `DoiHangItem` và `TraHangItem` là view model JavaFX property phía client, không nên truyền qua remote nếu có chứa JavaFX property.
- Remote request nên dùng DTO/request object nhẹ trong `pharmacy-common`.

---

## 5. Thiết kế đề xuất cho group 6

### 5.1. Remote boundary

Ưu tiên tạo một remote boundary chung:

```text
pharmacy-common/src/main/java/com/example/pharmacy/common/remote/DoiTraRemote.java
```

Tên binding:

```java
String BINDING_NAME = "DoiTraRemoteService";
```

Gợi ý method:

```java
HoaDon findHoaDonGocForDoiTra(String maHoaDon) throws RemoteException;

List<ChiTietHoaDon> findHoaDonDetailsForDoiTra(String maHoaDon) throws RemoteException;

int getSoLuongDaDoi(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

int getSoLuongDaTra(String maHoaDon, String maLoHang, String maDonViTinh) throws RemoteException;

String createPhieuDoi(CreatePhieuDoiRequest request, UserContext actor) throws RemoteException;

String createPhieuTra(CreatePhieuTraRequest request, UserContext actor) throws RemoteException;

List<PhieuDoiHang> findAllPhieuDoi() throws RemoteException;

List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPhieuDoi) throws RemoteException;

List<PhieuTraHang> findAllPhieuTra() throws RemoteException;

List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPhieuTra) throws RemoteException;
```

Nếu nhóm 5 đã có `HoaDonRemote.findById/findDetailsByMaHD`, có thể reuse cho phần tìm hóa đơn gốc, nhưng group 6 vẫn nên có method kiểm tra eligibility đổi/trả ở `DoiTraService` để gom luật nghiệp vụ.

### 5.2. Request DTO trong common

Tạo request object nhẹ, không dùng JavaFX property:

```text
pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuDoiRequest.java
pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuDoiItemRequest.java
pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuTraRequest.java
pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuTraItemRequest.java
```

Gợi ý field:

```java
CreatePhieuDoiRequest
- String maHoaDonGoc
- String maKhachHang
- LocalDate ngayLap
- String ghiChu
- List<CreatePhieuDoiItemRequest> items

CreatePhieuDoiItemRequest
- String maLoHang
- String maThuoc
- String maDonViTinh
- int soLuong
- String lyDoDoi

CreatePhieuTraRequest
- String maHoaDonGoc
- String maKhachHang
- LocalDate ngayLap
- String ghiChu
- List<CreatePhieuTraItemRequest> items

CreatePhieuTraItemRequest
- String maLoHang
- String maThuoc
- String maDonViTinh
- int soLuong
- double donGia
- double giamGia
- String lyDoTra
```

Nếu project đang ưu tiên dùng legacy model trực tiếp qua RMI để tiết kiệm thời gian, vẫn có thể dùng `PhieuDoiHang`, `ChiTietPhieuDoiHang`, `PhieuTraHang`, `ChiTietPhieuTraHang`, nhưng **không truyền `DoiHangItem` / `TraHangItem` qua remote**.

### 5.3. Server service

Tạo:

```text
pharmacy-server/src/main/java/com/example/pharmacy/server/service/DoiTraService.java
pharmacy-server/src/main/java/com/example/pharmacy/server/service/DoiTraServiceImpl.java
```

Nhiệm vụ service:

- Validate hóa đơn gốc tồn tại.
- Validate hóa đơn còn trong thời hạn đổi/trả theo logic cũ, hiện đang là 7 ngày.
- Validate hóa đơn có khách hàng nếu flow cũ yêu cầu.
- Validate số lượng đổi/trả không vượt quá số lượng đã bán trừ số đã đổi/trả trước đó.
- Validate lý do đổi/trả không trống.
- Điều phối transaction.
- Sinh mã bằng `CodeGenerationService`:
  - `BusinessCodeType.PHIEU_DOI_HANG`
  - `BusinessCodeType.PHIEU_TRA_HANG`
- Ghi audit bằng `AuditService`.
- Không để client tự insert header/detail hay tự cập nhật tồn kho.

### 5.4. Repository server

Tạo hoặc bổ sung:

```text
pharmacy-server/src/main/java/com/example/pharmacy/server/repository/DoiTraRepository.java
pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcDoiTraRepository.java
```

Có thể dùng JDBC transition trước cho nhanh và an toàn, vì nghiệp vụ đổi/trả liên quan nhiều bảng và transaction. Không bắt buộc phải JPA 100% trong đợt này.

Gợi ý method repository:

```java
HoaDon findHoaDonForDoiTra(String maHD);

List<ChiTietHoaDon> findChiTietHoaDon(String maHD);

int getSoLuongDaDoi(String maHD, String maLH, String maDVT);

int getSoLuongDaTra(String maHD, String maLH, String maDVT);

void insertPhieuDoiHeader(String maPD, CreatePhieuDoiRequest request, UserContext actor);

void insertPhieuDoiDetail(String maPD, CreatePhieuDoiItemRequest item);

void insertPhieuTraHeader(String maPT, CreatePhieuTraRequest request, UserContext actor);

void insertPhieuTraDetail(String maPT, CreatePhieuTraItemRequest item);

void congTonTraHang(String maLH, int soLuongBaseOrSoldUnit);

void truTonDoiHangIfCurrentLegacyRequiresIt(...);

List<PhieuDoiHang> findAllPhieuDoi();

List<ChiTietPhieuDoiHang> findChiTietPhieuDoiByMaPD(String maPD);

List<PhieuTraHang> findAllPhieuTra();

List<ChiTietPhieuTraHang> findChiTietPhieuTraByMaPT(String maPT);
```

Quan trọng:

- Dùng MariaDB syntax:
  - `LIMIT` thay `TOP`
  - `COALESCE` thay `ISNULL`
  - `CURRENT_TIMESTAMP` thay `GETDATE`
- Với dòng tồn kho / hóa đơn cần bảo vệ race condition, dùng `SELECT ... FOR UPDATE` trong transaction.
- Không close connection nếu đang transaction-bound. Reuse `JdbcConnectionProvider` và `TransactionConnectionContext` như các service trước.

### 5.5. RMI adapter / bootstrap

Tạo:

```text
pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/DoiTraRemoteAdapter.java
```

Cập nhật:

```text
pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java
pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java
pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java
```

Yêu cầu:

- `ServerComponentFactory.createDoiTraService()`
- `RmiServerBootstrap` bind `DoiTraRemote.BINDING_NAME`
- `RmiClientProvider.getDoiTraRemote()`

### 5.6. Client service

Tạo:

```text
pharmacy-client/src/main/java/com/example/pharmacy/client/service/DoiTraClientService.java
pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiDoiTraClientService.java
```

Nhiệm vụ client service:

- Gọi `DoiTraRemote`.
- Bắt `RemoteException` / `NotBoundException`.
- Trả message dễ hiểu cho controller.
- Không chứa JDBC / DAO / ConnectDB.
- Không chứa transaction logic.

### 5.7. Controller group 6

Refactor các controller:

```text
LapPhieuDoiHang_Ctrl
LapPhieuTraHang_Ctrl
TKPhieuDoiHang_Ctrl
ChiTietPhieuDoiHang_Ctrl
TKPhieuTraHang_Ctrl
ChiTietPhieuTraHang_Ctrl
```

Controller chỉ nên còn:

- UI event.
- Validate input cơ bản.
- Map `DoiHangItem` / `TraHangItem` sang request DTO.
- Lấy `SessionContext.getCurrentUser()`.
- Gọi `DoiTraClientService`.
- Hiển thị alert.
- Refresh table.
- Export PDF từ dữ liệu đã load qua service.

Không còn:

```java
import com.example.pharmacymanagementsystem_qlht.dao.*;
new PhieuDoiHang_Dao()
new PhieuTraHang_Dao()
new ChiTietPhieuDoiHang_Dao()
new ChiTietPhieuTraHang_Dao()
new HoaDon_Dao()
new ChiTietHoaDon_Dao()
new Thuoc_SP_TheoLo_Dao()
ConnectDB
Connection
PreparedStatement
ResultSet
```

---

## 6. Luồng nghiệp vụ mong muốn

### 6.1. Tìm hóa đơn gốc để đổi/trả

```text
LapPhieuDoi/LapPhieuTra Controller
    -> DoiTraClientService.findHoaDonGocForDoiTra(maHD)
    -> RMI DoiTraRemote
    -> DoiTraService
    -> Repository
    -> MariaDB
```

Server trả:

- Hóa đơn gốc.
- Chi tiết hóa đơn.
- Số lượng đã đổi / đã trả nếu cần.
- Thông tin khách hàng / nhân viên / thuốc / đơn vị tính.

Controller render table và disable các dòng không còn đổi/trả được.

### 6.2. Tạo phiếu đổi

```text
LapPhieuDoiHang_Ctrl
    -> build CreatePhieuDoiRequest
    -> DoiTraClientService.createPhieuDoi(request, actor)
    -> DoiTraRemote.createPhieuDoi(...)
    -> DoiTraService.createPhieuDoi(...)
        begin transaction
        validate invoice
        validate customer
        validate 7-day rule
        validate item quantity <= sold - alreadyChanged
        validate stock rule theo logic cũ
        generate MaPD
        insert PhieuDoiHang
        insert ChiTietPhieuDoiHang
        update stock theo logic cũ đã xác nhận
        audit CREATE
        commit / rollback
```

### 6.3. Tạo phiếu trả

```text
LapPhieuTraHang_Ctrl
    -> build CreatePhieuTraRequest
    -> DoiTraClientService.createPhieuTra(request, actor)
    -> DoiTraRemote.createPhieuTra(...)
    -> DoiTraService.createPhieuTra(...)
        begin transaction
        validate invoice
        validate customer
        validate 7-day rule
        validate invoice discount rule nếu logic cũ đang chặn
        validate item quantity <= sold - alreadyReturned
        generate MaPT
        insert PhieuTraHang
        insert ChiTietPhieuTraHang
        cộng tồn lại theo lô gốc nếu đúng logic cũ
        audit CREATE
        commit / rollback
```

### 6.4. Tìm kiếm phiếu đổi/trả

```text
TKPhieuDoiHang_Ctrl
    -> DoiTraClientService.findAllPhieuDoi()
    -> filter ở client hoặc service search tùy phạm vi nhỏ

TKPhieuTraHang_Ctrl
    -> DoiTraClientService.findAllPhieuTra()
    -> filter ở client hoặc service search tùy phạm vi nhỏ
```

Có thể giữ filter client-side tạm thời nếu dataset nhỏ, miễn là dữ liệu được lấy qua RMI thay vì DAO.

---

## 7. Các điểm nghiệp vụ tuyệt đối không được tự ý đổi

Codex phải giữ sát logic cũ, không tự “cải tiến” nghiệp vụ nếu chưa có yêu cầu.

### 7.1. Hạn đổi/trả 7 ngày

Controller cũ đang chặn hóa đơn quá 7 ngày. Giữ rule này trong server service.

### 7.2. Hóa đơn khách lẻ

Controller cũ yêu cầu bổ sung khách hàng trước khi đổi/trả. Có thể giữ UI mở form thêm khách hàng, nhưng update khách hàng vào hóa đơn phải đi qua service/remote của nhóm 5 nếu đã có, không gọi `HoaDon_Dao`.

### 7.3. Hóa đơn có giảm giá tổng / giảm giá dòng khi trả hàng

`LapPhieuTraHang_Ctrl` cũ có logic chặn trả hàng nếu hóa đơn có giảm giá tổng / line discount. Không bỏ rule này nếu chưa có business review.

### 7.4. Số lượng đã đổi / đã trả

Không chỉ dựa vào số lượng đang chọn trong UI. Server phải kiểm tra lại với DB trong transaction:

```text
số còn được đổi = số bán gốc - tổng số đã đổi trước đó
số còn được trả = số bán gốc - tổng số đã trả trước đó
```

### 7.5. Tồn kho theo lô

Không để client tự quyết định tồn kho cuối cùng. Client có thể cảnh báo sớm, nhưng server phải kiểm tra lại và lock dữ liệu trước khi commit.

---

## 8. Cách chia task nhỏ cho Codex

Không yêu cầu Codex làm tất cả trong một lần nếu repo lớn. Nên chia theo pass.

### Pass 0 - Read docs + scan only

Mục tiêu:

- Đọc docs migration.
- Đọc result nhóm 5.
- Scan group 6.
- Không sửa code.

Output:

- Danh sách file cần sửa.
- Flow cũ.
- Các DAO còn bị gọi.
- Các query SQL Server-specific.
- Rủi ro nghiệp vụ.

### Pass 1 - Common contract

Mục tiêu:

- Tạo `DoiTraRemote`.
- Tạo request DTO cho phiếu đổi/trả.
- Không sửa controller.

Acceptance:

- `pharmacy-common` compile.
- Common không có JavaFX/JPA/JDBC.

### Pass 2 - Server service/repository transaction

Mục tiêu:

- Tạo `DoiTraService`, `DoiTraServiceImpl`.
- Tạo `DoiTraRepository`, `JdbcDoiTraRepository`.
- Tạo `DoiTraRemoteAdapter`.
- Bind RMI.

Acceptance:

- `createPhieuDoi` và `createPhieuTra` chạy trong `transactionManager.execute`.
- Sinh mã bằng `CodeGenerationService`.
- Ghi audit.
- Không dùng `TOP`, `ISNULL`, `GETDATE`.

### Pass 3 - Client service

Mục tiêu:

- Tạo `DoiTraClientService`, `RmiDoiTraClientService`.
- Thêm `RmiClientProvider.getDoiTraRemote()`.

Acceptance:

- Client service không có DAO/JDBC.
- Remote exception được map thành message dễ hiểu.

### Pass 4 - Read-only screens

Mục tiêu:

- Refactor `TKPhieuDoiHang_Ctrl`.
- Refactor `ChiTietPhieuDoiHang_Ctrl`.
- Refactor `TKPhieuTraHang_Ctrl`.
- Refactor `ChiTietPhieuTraHang_Ctrl`.

Acceptance:

- Không còn `PhieuDoiHang_Dao`, `PhieuTraHang_Dao`, detail DAO trong các controller tìm kiếm/chi tiết.
- Export PDF vẫn hoạt động với data lấy từ RMI.

### Pass 5 - LapPhieuTra

Mục tiêu:

- Refactor `LapPhieuTraHang_Ctrl`.
- Map `TraHangItem` sang `CreatePhieuTraRequest`.
- Tạo phiếu trả qua service.

Acceptance:

- Không còn DAO/JDBC trong controller.
- Server validate lại số lượng, ngày, khách hàng, giảm giá.
- Insert header/detail + cộng tồn/audit trong transaction.

### Pass 6 - LapPhieuDoi

Mục tiêu:

- Refactor `LapPhieuDoiHang_Ctrl`.
- Map `DoiHangItem` sang `CreatePhieuDoiRequest`.
- Tạo phiếu đổi qua service.

Acceptance:

- Không còn DAO/JDBC trong controller.
- Server validate lại số lượng, ngày, khách hàng, tồn kho.
- Insert header/detail + cập nhật tồn/audit trong transaction.

### Pass 7 - Scan + docs result

Mục tiêu:

- Chạy scan sai tầng.
- Build.
- Tạo result markdown.

Acceptance:

- Có `docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md`.
- Có scan output.
- Có checklist test thủ công.

---

## 9. Checklist acceptance group 6

### 9.1. Architecture

- [ ] `pharmacy-common` có `DoiTraRemote`.
- [ ] `pharmacy-common` có request DTO đổi/trả hoặc dùng model shared hợp lý.
- [ ] `pharmacy-client` có `DoiTraClientService` / `RmiDoiTraClientService`.
- [ ] `pharmacy-client` có `RmiClientProvider.getDoiTraRemote()`.
- [ ] `pharmacy-server` có `DoiTraService`.
- [ ] `pharmacy-server` có repository đổi/trả.
- [ ] `pharmacy-server` có `DoiTraRemoteAdapter`.
- [ ] `RmiServerBootstrap` bind `DoiTraRemoteService`.

### 9.2. Client clean

- [ ] `LapPhieuDoiHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `LapPhieuTraHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `TKPhieuDoiHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `ChiTietPhieuDoiHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `TKPhieuTraHang_Ctrl` không còn DAO / ConnectDB / java.sql.
- [ ] `ChiTietPhieuTraHang_Ctrl` không còn DAO / ConnectDB / java.sql.

### 9.3. Server transaction

- [ ] Tạo phiếu đổi là một transaction.
- [ ] Tạo phiếu trả là một transaction.
- [ ] Header/detail/tồn kho/audit cùng commit hoặc rollback.
- [ ] Server validate lại số lượng, không tin client.
- [ ] Có lock hoặc logic transaction-safe cho dòng tồn kho / hóa đơn nhạy cảm.
- [ ] Sinh mã bằng `CodeGenerationService`, không dùng `MAX + 1`.

### 9.4. SQL compatibility

- [ ] Không còn `TOP 1` trong implementation mới.
- [ ] Không còn `ISNULL` trong implementation mới.
- [ ] Không còn `GETDATE` trong implementation mới.
- [ ] Không còn `OUTPUT INSERTED` trong implementation mới.
- [ ] Query dùng MariaDB syntax.

### 9.5. Build

- [ ] `mvn -q -f pharmacy-parent/pom.xml -DskipTests compile`
- [ ] Nếu build fail, ghi rõ fail ở module nào và do file nào.

---

## 10. Test thủ công cần làm sau migrate

### 10.1. Phiếu đổi

- [ ] Tìm hóa đơn tồn tại.
- [ ] Tìm hóa đơn không tồn tại.
- [ ] Hóa đơn quá 7 ngày bị chặn.
- [ ] Hóa đơn khách lẻ yêu cầu bổ sung khách hàng.
- [ ] Thêm 1 sản phẩm vào danh sách đổi.
- [ ] Nhập lý do trống bị chặn.
- [ ] Nhập số lượng đổi vượt số lượng bán bị chặn.
- [ ] Nhập số lượng đổi vượt tồn kho bị chặn.
- [ ] Tạo phiếu đổi thành công.
- [ ] Sau tạo, mở `TKPhieuDoiHang` thấy phiếu mới.
- [ ] Mở chi tiết phiếu đổi thấy đúng sản phẩm / số lượng / lý do.
- [ ] Test lỗi giữa chừng để chắc rollback không tạo header mồ côi.

### 10.2. Phiếu trả

- [ ] Tìm hóa đơn tồn tại.
- [ ] Tìm hóa đơn không tồn tại.
- [ ] Hóa đơn quá 7 ngày bị chặn.
- [ ] Hóa đơn khách lẻ yêu cầu bổ sung khách hàng.
- [ ] Hóa đơn có giảm giá theo logic cũ bị chặn nếu rule đang tồn tại.
- [ ] Thêm 1 sản phẩm vào danh sách trả.
- [ ] Nhập lý do trống bị chặn.
- [ ] Nhập số lượng trả vượt số lượng bán bị chặn.
- [ ] Tạo phiếu trả thành công.
- [ ] Sau tạo, mở `TKPhieuTraHang` thấy phiếu mới.
- [ ] Mở chi tiết phiếu trả thấy đúng sản phẩm / số lượng / lý do / tiền trả.
- [ ] Tồn kho được cộng lại đúng theo logic cũ.
- [ ] Test lỗi giữa chừng để chắc rollback không tạo header mồ côi.

### 10.3. Regression

- [ ] Đăng nhập vẫn chạy.
- [ ] Lập hóa đơn nhóm 5 vẫn chạy.
- [ ] Tìm kiếm hóa đơn vẫn chạy.
- [ ] Tồn kho sau bán / trả / đổi không bị âm.
- [ ] RMI server start được.
- [ ] Không ảnh hưởng nhóm 1-4.

---

## 11. Prompt dùng trực tiếp cho Codex

```text
Bạn đang làm trong repo JavaFX quản lý nhà thuốc đã migrate dần sang kiến trúc pharmacy-parent gồm:
- pharmacy-common
- pharmacy-client
- pharmacy-server

Task lần này: migrate Nhóm 6: DoiTra.

Phạm vi:
- LapPhieuDoi
- LapPhieuTra
- TKPhieuDoiHang
- ChiTietPhieuDoiHang
- TKPhieuTraHang
- ChiTietPhieuTraHang

Mục tiêu:
- Đổi/trả không còn chạy theo DAO rời rạc ở client.
- Client không được gọi DAO / ConnectDB / JDBC.
- Tạo phiếu đổi/trả phải đi qua Client Service -> RMI -> DoiTraRemote -> DoiTraServiceImpl -> Repository -> MariaDB.
- Header phiếu, detail phiếu, tồn kho và audit phải nằm trong transaction server-side.
- Giữ sát logic cũ, không tự đổi nghiệp vụ.

Trước khi code:
1. Đọc docs migration:
   docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md
   docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md
   docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md
   docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md
   docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md
   docs/migration/STEP_8_IMPLEMENTATION_RESULT.md
   docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md
   docs/migration/DAO_MIGRATION_MAPPING.md
   docs/migration/QUERY_INVENTORY.md
   docs/migration/MIGRATION_PRIORITY.md
2. Đọc các result nhóm đã chạy nếu có:
   docs/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
   docs/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
   docs/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
   docs/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
   docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md
3. Nếu group 5 result chưa có hoặc HoaDonService/HoaDonRemote chưa ổn, chỉ scan nhóm 6 và dừng, không migrate sâu.

Luật kiến trúc bắt buộc:
- Không cho client phụ thuộc server.
- Common không chứa JavaFX/JPA/JDBC/repository implementation.
- Server không chứa JavaFX UI/controller/FXML.
- Client không import DAO, ConnectDB, java.sql cho nghiệp vụ.
- Không xóa root src legacy.
- Không migrate sang nhóm 7 thống kê/báo cáo.
- Không sửa đại trà nhóm 5 nếu không cần.

Việc cần làm theo từng pass:

PASS 0 - Scan:
- Scan các controller nhóm 6 còn DAO/ConnectDB/JDBC.
- Scan các DAO legacy: PhieuDoiHang_Dao, ChiTietPhieuDoiHang_Dao, PhieuTraHang_Dao, ChiTietPhieuTraHang_Dao.
- Ghi rõ TOP/ISNULL/ConnectDB/java.sql còn ở đâu.

PASS 1 - Common contract:
- Tạo DoiTraRemote với binding "DoiTraRemoteService".
- Tạo request DTO nhẹ cho CreatePhieuDoi/CreatePhieuTra nếu chưa có.
- Không dùng DoiHangItem/TraHangItem làm remote DTO vì đó là client view model.

PASS 2 - Server:
- Tạo DoiTraService/DoiTraServiceImpl.
- Tạo DoiTraRepository/JdbcDoiTraRepository hoặc reuse repository hiện có nếu đã có.
- Tạo DoiTraRemoteAdapter.
- Thêm createDoiTraService() vào ServerComponentFactory.
- Bind DoiTraRemoteService trong RmiServerBootstrap.
- createPhieuDoi/createPhieuTra phải chạy trong transactionManager.execute.
- Sinh MaPD/MaPT bằng CodeGenerationService, không dùng SELECT TOP 1.
- Ghi AuditService.logAction.
- Dùng MariaDB syntax: COALESCE, LIMIT, CURRENT_TIMESTAMP nếu cần.

PASS 3 - Client service:
- Tạo DoiTraClientService/RmiDoiTraClientService.
- Thêm RmiClientProvider.getDoiTraRemote().
- Map RemoteException/NotBoundException thành message dễ hiểu.

PASS 4 - Read-only screens:
- Refactor TKPhieuDoiHang_Ctrl, ChiTietPhieuDoiHang_Ctrl, TKPhieuTraHang_Ctrl, ChiTietPhieuTraHang_Ctrl sang client service/RMI.
- PDF export giữ ở client nhưng dữ liệu phải lấy qua RMI.

PASS 5 - LapPhieuTra:
- Refactor LapPhieuTraHang_Ctrl.
- Controller chỉ validate UI, map TraHangItem -> CreatePhieuTraRequest, lấy UserContext, gọi service.
- Server validate lại: hóa đơn tồn tại, khách hàng, hạn 7 ngày, rule giảm giá nếu logic cũ đang chặn, số lượng trả còn hợp lệ.
- Server insert header/detail + cập nhật tồn kho + audit trong transaction.

PASS 6 - LapPhieuDoi:
- Refactor LapPhieuDoiHang_Ctrl.
- Controller chỉ validate UI, map DoiHangItem -> CreatePhieuDoiRequest, lấy UserContext, gọi service.
- Server validate lại: hóa đơn tồn tại, khách hàng, hạn 7 ngày, số lượng đổi còn hợp lệ, tồn kho hợp lệ.
- Server insert header/detail + cập nhật tồn kho theo logic cũ + audit trong transaction.

PASS 7 - Build/docs:
- Chạy mvn -q -f pharmacy-parent/pom.xml -DskipTests compile.
- Scan lại sai tầng.
- Tạo docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md.

Acceptance:
- Không còn DAO/ConnectDB/JDBC trong controller group 6 ở pharmacy-client.
- DoiTraRemote được bind ở RMI server.
- Client gọi DoiTraRemote qua RmiClientProvider.
- Tạo phiếu đổi/trả không tạo header mồ côi nếu lỗi ở detail/tồn kho/audit.
- Build compile pass hoặc ghi rõ lỗi còn lại.
```

---

## 12. Template result markdown cho Codex tạo sau khi migrate

Tạo file:

```text
docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
```

Nội dung template:

```markdown
# STEP 16 - GROUP 6 DOI TRA MIGRATION RESULT

## 1. Mục tiêu

Migrate nhóm 6: LapPhieuDoi, LapPhieuTra, TKPhieuDoiHang, TKPhieuTraHang sang kiến trúc Client -> RMI -> Server -> Repository -> MariaDB.

## 2. Tài liệu đã đọc

- ...
- File nào không tìm thấy: ...

## 3. Phạm vi đã làm

- [ ] LapPhieuDoi
- [ ] LapPhieuTra
- [ ] TKPhieuDoiHang
- [ ] ChiTietPhieuDoiHang
- [ ] TKPhieuTraHang
- [ ] ChiTietPhieuTraHang

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

### Tạo phiếu đổi

```text
...
```

### Tạo phiếu trả

```text
...
```

### Tìm kiếm phiếu đổi/trả

```text
...
```

## 7. Mapping legacy -> new

| Legacy | New | Trạng thái | Ghi chú |
|---|---|---|---|
| PhieuDoiHang_Dao | DoiTraRepository/DoiTraService | ... | ... |
| ChiTietPhieuDoiHang_Dao | DoiTraRepository/DoiTraService | ... | ... |
| PhieuTraHang_Dao | DoiTraRepository/DoiTraService | ... | ... |
| ChiTietPhieuTraHang_Dao | DoiTraRepository/DoiTraService | ... | ... |

## 8. Transaction và audit

- createPhieuDoi transaction: ...
- createPhieuTra transaction: ...
- audit action: ...

## 9. Scan sai tầng

### Client group 6 DAO/ConnectDB/JDBC

Kết quả:

```text
...
```

### Server JavaFX UI

Kết quả:

```text
...
```

### Common implementation leakage

Kết quả:

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

## 13. Ghi chú chiến lược

Nhóm 6 nên ưu tiên “đúng transaction, đúng tầng” hơn là làm đẹp toàn bộ query.

Nếu quá lớn, làm theo thứ tự an toàn:

1. Read-only screens trước.
2. `LapPhieuTra` trước vì thường chỉ cộng tồn lại theo dòng gốc, ít phụ thuộc chọn lot thay thế hơn.
3. `LapPhieuDoi` sau vì logic đổi hàng dễ nhạy cảm hơn: vừa liên quan hóa đơn gốc, vừa liên quan tồn kho sản phẩm đổi.

Không cố dọn nhóm 7 trong task này. Các màn thống kê / báo cáo / audit display để sau.
