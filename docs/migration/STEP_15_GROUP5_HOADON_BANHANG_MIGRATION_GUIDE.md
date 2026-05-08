# STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_GUIDE

## Mục tiêu tài liệu

Tài liệu này dùng để giao task cho Codex migrate **Nhóm 5: HoaDon + BanHang** trong project JavaFX quản lý nhà thuốc đang chuyển sang kiến trúc:

```text
JavaFX Client
    -> Client Service
    -> RMI Stub
    -> Remote Interface trong pharmacy-common
    -> Server Service
    -> Repository / JPA hoặc JDBC transition có transaction
    -> MariaDB
```

Phạm vi nhóm 5 rất nhạy cảm vì đây là trục bán hàng chính, có liên quan đến:
- `LapHoaDon`
- `TKHoaDon`
- popup chọn khách hàng / chọn thuốc trong hóa đơn
- chi tiết hóa đơn
- trừ tồn kho theo lô
- áp khuyến mãi
- tính tiền / VAT / giảm giá
- thanh toán tiền mặt / chuyển khoản
- hóa đơn từ phiếu đặt hàng nếu có

Không được migrate tràn lan sang đổi/trả, thống kê, báo cáo hoặc toàn bộ tồn kho ngoài nhu cầu lập hóa đơn.

---

## 1. Tài liệu bắt buộc phải đọc trước khi sửa code

Trước khi code, Codex phải đọc lại toàn bộ tài liệu migration có liên quan.

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

### 1.2. Tài liệu các nhóm đã migrate

Nếu tồn tại trong `docs/`, đọc kỹ theo thứ tự:

```text
docs/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
docs/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
docs/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
docs/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
```

Nếu file nào không tồn tại hoặc đang nằm ở thư mục khác thì ghi rõ trong result markdown.

Lý do phải đọc nhóm 1-4:
- Nhóm 1 đã tạo trục Thuoc / DonViTinh / KeHang / TonKho.
- Nhóm 2 đã hoàn thiện trục user/account/session/cài đặt.
- Nhóm 3 đã tạo khuyến mãi.
- Nhóm 4 đã có phiếu nhập / phiếu đặt / tồn kho.
- Nhóm 5 phải tận dụng các remote/service đã có, không tạo trùng DAO/service mới.

---

## 2. Quy tắc kiến trúc bắt buộc

### pharmacy-common

Được chứa:
- Remote interface
- DTO/request/response dùng chung
- Enum/exception dùng chung
- Legacy model dùng chung nếu các nhóm trước đang dùng pattern đó

Không được chứa:
- JavaFX
- JPA/Hibernate
- JDBC
- DAO/ConnectDB
- server implementation
- client controller

### pharmacy-client

Được chứa:
- JavaFX UI/controller/view/resource
- Client service wrapper
- RMI provider lookup
- Client-side session

Không được chứa:
- `ConnectDB`
- DAO
- `DriverManager`
- `java.sql.Connection`
- `PreparedStatement`
- `ResultSet`
- JPA/Hibernate
- dependency sang `pharmacy-server`

### pharmacy-server

Được chứa:
- Remote adapter
- Server service
- Repository
- JPA entity hoặc JDBC transition repository
- Transaction service
- Audit service
- CodeGenerationService

Không được chứa:
- JavaFX controller/view/FXML UI
- dependency sang `pharmacy-client`

### root `src/`

Root `src/` là legacy/reference an toàn. Không xóa root `src/` trong task này.

---

## 3. Lệnh scan nhanh trước khi sửa

Chạy từ root repo bằng PowerShell.

### 3.1. Scan tài liệu nhóm đã có

```powershell
Get-ChildItem docs -Recurse -Filter *.md |
  Sort-Object FullName |
  Select-Object FullName
```

```powershell
Get-ChildItem docs -Recurse -Filter "*GROUP*.md" |
  Sort-Object FullName |
  ForEach-Object {
    "`n===== $($_.FullName) ====="
    Get-Content $_.FullName -TotalCount 80
  }
```

### 3.2. Scan file group 5 trong legacy root

```powershell
$group5Patterns = @(
  "LapHoaDon",
  "TimKiemHoaDon",
  "ChiTietHoaDon",
  "HoaDon_Dao",
  "ChiTietHoaDon_Dao"
)

foreach ($p in $group5Patterns) {
  "`n===== SEARCH: $p ====="
  Get-ChildItem src/main/java -Recurse -Include *.java |
    Select-String -Pattern $p |
    Select-Object Path, LineNumber, Line
}
```

### 3.3. Scan lỗi sai tầng trong client module

```powershell
$clientPath = "pharmacy-parent/pharmacy-client/src/main/java"
$clientBadPatterns = @(
  "ConnectDB",
  "com.example.pharmacymanagementsystem_qlht.dao",
  "new .*_Dao",
  "DriverManager",
  "java.sql.Connection",
  "PreparedStatement",
  "ResultSet",
  "CallableStatement",
  "jakarta.persistence",
  "javax.persistence",
  "org.hibernate",
  "com.example.pharmacy.server"
)

Get-ChildItem $clientPath -Recurse -Include *.java |
  Select-String -Pattern $clientBadPatterns |
  Select-Object Path, LineNumber, Line
```

Kết quả mong muốn sau migrate group 5:
- Không còn match ở các controller/service client thuộc cụm hóa đơn/bán hàng.
- Nếu còn match ở file không thuộc group 5 thì ghi rõ, không sửa lan.

### 3.4. Scan lỗi sai tầng trong common module

```powershell
$commonPath = "pharmacy-parent/pharmacy-common/src/main/java"
$commonBadPatterns = @(
  "javafx",
  "jakarta.persistence",
  "javax.persistence",
  "org.hibernate",
  "ConnectDB",
  "com.example.pharmacymanagementsystem_qlht.dao",
  "com.example.pharmacy.server",
  "com.example.pharmacy.client"
)

Get-ChildItem $commonPath -Recurse -Include *.java |
  Select-String -Pattern $commonBadPatterns |
  Select-Object Path, LineNumber, Line
```

### 3.5. Scan lỗi sai tầng trong server module

```powershell
$serverPath = "pharmacy-parent/pharmacy-server/src/main/java"
$serverBadPatterns = @(
  "javafx.fxml",
  "javafx.scene",
  "javafx.stage",
  "com.example.pharmacy.client",
  "com.example.pharmacymanagementsystem_qlht.controller",
  "com.example.pharmacymanagementsystem_qlht.view"
)

Get-ChildItem $serverPath -Recurse -Include *.java |
  Select-String -Pattern $serverBadPatterns |
  Select-Object Path, LineNumber, Line
```

### 3.6. Scan dependency Maven

```powershell
Get-Content pharmacy-parent/pom.xml
Get-Content pharmacy-parent/pharmacy-common/pom.xml
Get-Content pharmacy-parent/pharmacy-client/pom.xml
Get-Content pharmacy-parent/pharmacy-server/pom.xml
```

Kiểm tra:
- `pharmacy-client -> pharmacy-common`
- `pharmacy-server -> pharmacy-common`
- Không có `pharmacy-client -> pharmacy-server`
- Không có `pharmacy-common -> pharmacy-client/server`
- Không có `pharmacy-server -> pharmacy-client`

---

## 4. File legacy group 5 cần rà

### Controller/view chính

Ưu tiên rà các file sau trong `src/` cũ:

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_XuLy/LapHoaDon/LapHoaDon_GUI.java

src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/TimKiemHoaDon_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoaDon/TimKiemHoaDon_GUI.java

src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/ChiTietHoaDon_Ctrl.java
src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoaDon/ChiTietHoaDon_GUI.java
```

### DAO legacy liên quan trực tiếp

```text
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/HoaDon_Dao.java
src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ChiTietHoaDon_Dao.java
```

### DAO legacy phụ thuộc khi lập hóa đơn

Chỉ rà để hiểu logic, không migrate toàn bộ nếu remote/service đã có ở nhóm trước:

```text
Thuoc_SanPham_Dao
Thuoc_SP_TheoLo_Dao
ChiTietDonViTinh_Dao
DonViTinh_Dao
ChiTietKhuyenMai_Dao
KhuyenMai_Dao
Thuoc_SP_TangKem_Dao
KhachHang_Dao
PhieuDatHang_Dao
ChiTietPhieuDatHang_Dao
```

---

## 5. Hiện trạng kỹ thuật cần chú ý

### 5.1. LapHoaDon_Ctrl legacy hiện đang vi phạm tầng

`LapHoaDon_Ctrl` cũ có dấu hiệu:
- import `ConnectDB`
- import `dao.*`
- dùng nhiều DAO trực tiếp: `Thuoc_SP_TheoLo_Dao`, `Thuoc_SanPham_Dao`, `ChiTietKhuyenMai_Dao`
- dùng `java.sql.*`
- tự xử lý nhiều phần nghiệp vụ bán hàng trong controller

Mục tiêu group 5:
- Controller chỉ giữ UI logic.
- Controller không gọi DAO/ConnectDB/JDBC.
- Controller gọi `HoaDonClientService`.
- `HoaDonClientService` gọi RMI.

### 5.2. HoaDon_Dao legacy còn SQL Server-specific

Các điểm cần thay:
- `OUTPUT INSERTED.MaHD`
- `GETDATE()`
- `SELECT TOP 1`
- `MAX/code generation` kiểu cũ
- `ConnectDB.update/query`

Mục tiêu group 5:
- Sinh `MaHD` bằng `CodeGenerationService.nextCode(BusinessCodeType.HOA_DON)`.
- Query MariaDB dùng `CURRENT_TIMESTAMP`, `LIMIT` nếu cần.
- Việc tạo hóa đơn phải nằm trong server transaction.

### 5.3. ChiTietHoaDon_Dao legacy có N+1 DAO load

`ChiTietHoaDon_Dao` cũ load chi tiết rồi gọi thêm:
- `HoaDon_Dao`
- `Thuoc_SP_TheoLo_Dao`
- `DonViTinh_Dao`

Mục tiêu group 5:
- Repository server nên dùng query join/projection rõ ràng.
- Không để client tự ráp chi tiết bằng nhiều DAO.
- Chi tiết hóa đơn nên được load qua `HoaDonRemote.findDetailsByMaHD(...)`.

### 5.4. Tồn kho khi bán hàng là transaction bắt buộc

Tạo hóa đơn phải cùng transaction với:
- insert `HoaDon`
- insert `ChiTietHoaDon`
- trừ/cập nhật tồn theo lô
- nếu hóa đơn từ phiếu đặt: xử lý trạng thái/giữ hàng tương ứng
- audit log

Không để controller gọi nhiều remote nhỏ để tự điều phối.

---

## 6. Thiết kế đề xuất cho group 5

### 6.1. pharmacy-common

Tạo hoặc kiểm tra các class sau.

#### Remote

```text
pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/HoaDonRemote.java
```

Gợi ý method:

```java
public interface HoaDonRemote extends Remote {
    String BINDING_NAME = "HoaDonRemoteService";

    String generateNewMaHoaDon() throws RemoteException;

    String createInvoice(CreateHoaDonRequest request, UserContext actor) throws RemoteException;

    List<HoaDonSummaryDTO> findAll() throws RemoteException;

    HoaDonDTO findById(String maHD) throws RemoteException;

    List<ChiTietHoaDonDTO> findDetailsByMaHD(String maHD) throws RemoteException;

    List<HoaDonSummaryDTO> search(HoaDonSearchRequest request) throws RemoteException;
}
```

Nếu các nhóm trước đang dùng legacy model trong remote thay vì DTO, có thể giữ tạm pattern đó để giảm rủi ro. Tuy nhiên, với `createInvoice`, nên ưu tiên request object vì đây là transaction service.

#### Request/DTO nên có

```text
common/request/CreateHoaDonRequest.java
common/request/CreateHoaDonLineRequest.java
common/request/HoaDonSearchRequest.java

common/dto/HoaDonDTO.java
common/dto/HoaDonSummaryDTO.java
common/dto/ChiTietHoaDonDTO.java
```

Các field tối thiểu:

`CreateHoaDonRequest`
- `maKhachHang`
- `ngayLap`
- `loaiHoaDon`
- `maDonThuoc`
- `phuongThucThanhToan` nếu schema/app đang dùng
- `tienKhachDua` nếu app đang dùng
- `maPhieuDat` nếu hóa đơn được tạo từ phiếu đặt
- `vatRate`
- `discountInvoiceAmount`
- `List<CreateHoaDonLineRequest> lines`

`CreateHoaDonLineRequest`
- `maLoHang`
- `maThuoc`
- `maDVT`
- `soLuong`
- `donGia`
- `giamGia`
- `heSoQuyDoi` nếu cần trừ tồn theo đơn vị cơ bản
- `tenThuoc` chỉ để audit/message, không dùng làm khóa chính

`HoaDonSummaryDTO`
- `maHD`
- `ngayLap`
- `maKH`
- `tenKH`
- `sdtKH`
- `maNV`
- `tenNV`
- `trangThai`
- `loaiHoaDon`
- `maDonThuoc`
- `tongTien`

`ChiTietHoaDonDTO`
- `maHD`
- `maLH`
- `maThuoc`
- `tenThuoc`
- `maDVT`
- `tenDVT`
- `soLuong`
- `donGia`
- `giamGia`
- `thanhTien`

### 6.2. pharmacy-server

Tạo hoặc kiểm tra:

```text
server/entity/HoaDonEntity.java
server/entity/ChiTietHoaDonEntity.java
server/entity/ChiTietHoaDonId.java
```

Nếu JPA composite key làm mất thời gian, có thể dùng JDBC repository transition cho group 5 để đảm bảo transaction trước, nhưng vẫn phải nằm trong server module.

Repository/service đề xuất:

```text
server/repository/HoaDonRepository.java
server/repository/jdbc/JdbcHoaDonRepository.java
server/service/HoaDonService.java
server/service/HoaDonServiceImpl.java
server/bootstrap/rmi/HoaDonRemoteAdapter.java
```

`HoaDonServiceImpl.createInvoice(...)` phải:
1. Validate `actor`.
2. Validate request không null, có line.
3. Validate `maLoHang`, `maDVT`, `soLuong > 0`, `donGia >= 0`, `giamGia >= 0`.
4. Sinh `MaHD` qua `CodeGenerationService`.
5. Mở transaction.
6. Lock dòng lô thuốc cần bán bằng `SELECT ... FOR UPDATE`.
7. Tính tồn khả dụng theo quy tắc hiện có.
8. Trừ tồn đúng theo đơn vị quy đổi.
9. Insert `HoaDon`.
10. Insert `ChiTietHoaDon`.
11. Nếu có `maPhieuDat`, cập nhật trạng thái phiếu đặt hoặc release/consume phần giữ hàng theo thiết kế group 4.
12. Ghi audit.
13. Commit/rollback rõ ràng.

Không để controller gọi:
- `generateMaHD`
- `insert HoaDon`
- `insert ChiTietHoaDon`
- `update tồn`
theo nhiều remote riêng lẻ.

### 6.3. pharmacy-client

Tạo:

```text
client/service/HoaDonClientService.java
client/service/RmiHoaDonClientService.java
```

Cập nhật:

```text
client/rmi/RmiClientProvider.java
```

Thêm:

```java
public HoaDonRemote getHoaDonRemote() ...
```

Migrate controller/view group 5 sang `pharmacy-client` nếu chưa có:
- `LapHoaDon_Ctrl`
- `LapHoaDon_GUI`
- `TimKiemHoaDon_Ctrl`
- `TimKiemHoaDon_GUI`
- `ChiTietHoaDon_Ctrl`
- `ChiTietHoaDon_GUI`

Trong controller:
- giữ nguyên giao diện và event UX nhiều nhất có thể
- đổi DAO calls sang client service/remote đã có
- gợi ý thuốc nên ưu tiên dùng `ThuocRemote`/`ThuocClientService` có sẵn từ group 1
- khách hàng nên dùng `KhachHangRemote` có sẵn
- khuyến mãi nên dùng `KhuyenMaiRemote` có sẵn
- phiếu đặt hàng nên dùng `PhieuDatHangRemote` có sẵn
- khi thanh toán chỉ gọi một method transaction: `hoaDonClientService.createInvoice(...)`

---

## 7. Cách chia task nhỏ cho Codex

Không nên làm nhóm 5 trong một diff quá lớn nếu project đang dễ vỡ. Chia thành các pass nhỏ.

### Pass 5A - Contract + scan + skeleton

- Đọc docs.
- Tạo `HoaDonRemote`.
- Tạo request/DTO tối thiểu.
- Tạo `HoaDonClientService` / `RmiHoaDonClientService`.
- Tạo `HoaDonService` / `HoaDonServiceImpl` skeleton.
- Tạo `HoaDonRemoteAdapter`.
- Bind vào `RmiServerBootstrap`.
- Thêm lookup trong `RmiClientProvider`.
- Chưa migrate UI sâu.
- Build compile.

### Pass 5B - Server transaction createInvoice

- Implement `HoaDonRepository` và `JdbcHoaDonRepository` hoặc JPA repository nếu entity đã ổn.
- Implement `createInvoice` transaction-safe.
- Sinh mã bằng `CodeGenerationService`.
- Insert header/detail.
- Trừ tồn theo lô.
- Audit log.
- Test bằng probe nhỏ không cần UI nếu có thể.

### Pass 5C - Migrate LapHoaDon_Ctrl

- Chuyển `LapHoaDon_Ctrl` vào `pharmacy-client` nếu chưa có.
- Loại bỏ DAO/ConnectDB/JDBC khỏi controller.
- Gợi ý thuốc/khách/khuyến mãi dùng client service đã có.
- Thanh toán gọi `HoaDonClientService.createInvoice`.
- UI giữ nguyên nhất có thể.

### Pass 5D - Migrate TKHoaDon + ChiTietHoaDon

- Search hóa đơn qua `HoaDonRemote.search/findAll/findById`.
- Chi tiết hóa đơn qua `findDetailsByMaHD`.
- Không gọi `HoaDon_Dao`/`ChiTietHoaDon_Dao`.

### Pass 5E - Scan + docs result

- Chạy scan sai tầng.
- Build `mvn -f pharmacy-parent/pom.xml -DskipTests compile`.
- Tạo/update result markdown cho group 5.
- Ghi rõ phần chưa làm.

---

## 8. Checklist acceptance group 5

### Kiến trúc

- [ ] Có `HoaDonRemote` trong common.
- [ ] Có request/DTO hoặc model contract serializable.
- [ ] Có `HoaDonServiceImpl` ở server.
- [ ] Có repository server cho hóa đơn.
- [ ] Có `HoaDonRemoteAdapter`.
- [ ] `RmiServerBootstrap` bind `HoaDonRemoteService`.
- [ ] `RmiClientProvider` lookup được `HoaDonRemoteService`.
- [ ] Có `HoaDonClientService` ở client.

### Client sạch tầng

- [ ] `LapHoaDon_Ctrl` trong `pharmacy-client` không import DAO.
- [ ] `LapHoaDon_Ctrl` không import `ConnectDB`.
- [ ] `LapHoaDon_Ctrl` không dùng `java.sql.*`.
- [ ] `TimKiemHoaDon_Ctrl` không gọi `HoaDon_Dao`.
- [ ] `ChiTietHoaDon_Ctrl` không gọi `ChiTietHoaDon_Dao`.
- [ ] `pharmacy-client/pom.xml` không phụ thuộc `pharmacy-server`.

### Server đúng tầng

- [ ] Server không import JavaFX UI/controller/view.
- [ ] Tạo hóa đơn nằm trong transaction service.
- [ ] Không dùng SQL Server syntax: `OUTPUT INSERTED`, `GETDATE`, `TOP`.
- [ ] Không dùng `MAX(MaHD)+1`.
- [ ] Có rollback nếu insert detail/trừ tồn lỗi.

### Nghiệp vụ

- [ ] Bán một thuốc thành công.
- [ ] Bán nhiều dòng cùng hóa đơn thành công.
- [ ] Bán theo đơn vị quy đổi không trừ sai tồn.
- [ ] Không cho bán vượt tồn.
- [ ] Áp khuyến mãi không làm âm tiền.
- [ ] Hóa đơn OTC không yêu cầu mã đơn thuốc.
- [ ] Hóa đơn ETC có xử lý mã đơn thuốc theo UI cũ.
- [ ] Thanh toán tiền mặt tính tiền thừa đúng.
- [ ] Thanh toán chuyển khoản tạo hóa đơn sau xác nhận.
- [ ] Nếu tạo từ phiếu đặt hàng, xử lý trạng thái/giữ hàng đúng theo group 4.
- [ ] Search hóa đơn hiển thị lại được hóa đơn vừa tạo.
- [ ] Xem chi tiết hóa đơn hiển thị đủ dòng.

---

## 9. Prompt dùng trực tiếp cho Codex

```text
Bạn hãy migrate Nhóm 5: HoaDon + BanHang theo đúng kiến trúc migration hiện tại.

Trước khi code, bắt buộc đọc:
- docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md
- docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md
- docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md
- docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md
- docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md
- docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md
- docs/migration/STEP_8_IMPLEMENTATION_RESULT.md
- docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md
- docs/migration/DAO_MIGRATION_MAPPING.md
- docs/migration/QUERY_INVENTORY.md
- docs/migration/MIGRATION_PRIORITY.md
- docs/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md nếu có
- docs/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md nếu có
- docs/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md nếu có
- docs/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md nếu có

Mục tiêu nhóm 5:
- Migrate LapHoaDon + TKHoaDon + ChiTietHoaDon theo flow:
  JavaFX Client -> HoaDonClientService -> RMI -> HoaDonRemote -> HoaDonServiceImpl -> Repository/Transaction -> MariaDB
- Không migrate lan sang DoiTra hoặc ThongKe.
- Không xóa root src legacy.
- Giữ UI/UX hiện tại nhiều nhất có thể.

Quy tắc bắt buộc:
- Client không được gọi DAO/ConnectDB/JDBC/JPA/Hibernate.
- Client không được phụ thuộc server.
- Common không được có JavaFX/JPA/DAO/implementation.
- Server không được có JavaFX UI/controller/view/FXML.
- Tạo hóa đơn phải là một transaction server-side duy nhất.
- Không dùng SQL Server syntax: OUTPUT INSERTED, GETDATE, TOP, CallableStatement proc cũ.
- Không dùng MAX(MaHD)+1; dùng CodeGenerationService với BusinessCodeType.HOA_DON.
- Nếu có thể thì dùng các remote/service đã migrate ở nhóm 1-4: ThuocRemote, TonKhoRemote, KhachHangRemote, KhuyenMaiRemote, PhieuDatHangRemote. Không tạo trùng service nếu đã có.

Phạm vi file cần rà:
- src/main/java/.../controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java
- src/main/java/.../controller/CN_TimKiem/TKHoaDon/TimKiemHoaDon_Ctrl.java
- src/main/java/.../controller/CN_TimKiem/TKHoaDon/ChiTietHoaDon_Ctrl.java
- src/main/java/.../dao/HoaDon_Dao.java
- src/main/java/.../dao/ChiTietHoaDon_Dao.java
- các DAO phụ thuộc chỉ để hiểu logic: Thuoc_SP_TheoLo_Dao, Thuoc_SanPham_Dao, ChiTietDonViTinh_Dao, KhachHang_Dao, ChiTietKhuyenMai_Dao, PhieuDatHang_Dao.

Thực hiện theo pass nhỏ:
1. Tạo contract/skeleton:
   - HoaDonRemote trong common
   - request/DTO cần thiết
   - HoaDonClientService/RmiHoaDonClientService
   - HoaDonService/HoaDonServiceImpl
   - HoaDonRepository/JdbcHoaDonRepository hoặc JPA repository
   - HoaDonRemoteAdapter
   - bind RMI server và lookup RMI client
2. Implement createInvoice transaction-safe:
   - validate request và actor
   - generate MaHD bằng CodeGenerationService
   - lock lô bằng SELECT ... FOR UPDATE nếu dùng JDBC
   - insert HoaDon
   - insert ChiTietHoaDon
   - trừ tồn theo lô/đơn vị quy đổi
   - xử lý maPhieuDat nếu có theo logic nhóm 4
   - audit log
   - rollback nếu lỗi
3. Migrate LapHoaDon_Ctrl:
   - bỏ DAO/ConnectDB/java.sql
   - gọi client service/remote
   - giữ giao diện cũ
4. Migrate TimKiemHoaDon/ChiTietHoaDon:
   - dùng HoaDonRemote search/findById/findDetailsByMaHD
   - bỏ HoaDon_Dao/ChiTietHoaDon_Dao khỏi client
5. Chạy scan sai tầng:
   - client không còn ConnectDB/dao/java.sql/JPA/Hibernate ở cụm hóa đơn
   - common không JavaFX/JPA/DAO
   - server không JavaFX UI/controller/view
6. Build:
   - mvn -f pharmacy-parent/pom.xml -DskipTests compile

Sau khi xong, tạo hoặc cập nhật file docs:
docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md

File result phải ghi:
- Tài liệu đã đọc
- File đã tạo/sửa
- Flow mới
- Mapping file cũ -> file mới
- Scan sai tầng trước/sau
- Build result
- Test thủ công cần làm
- Những phần chưa làm và lý do
```

---

## 10. Template result markdown cho Codex tạo sau khi migrate

```md
# STEP 15 - GROUP 5 HoaDon + BanHang Migration Result

## 1. Mục tiêu

Migrate nhóm HoaDon + BanHang từ controller/DAO legacy sang:
JavaFX Client -> Client Service -> RMI -> Server Service -> Repository/Transaction -> MariaDB.

## 2. Tài liệu đã đọc

- ...
- File không tìm thấy: ...

## 3. Phạm vi đã làm

- LapHoaDon
- TimKiemHoaDon
- ChiTietHoaDon
- HoaDonRemote
- HoaDonService
- HoaDonRepository
- ...

## 4. File đã tạo/sửa

### pharmacy-common
- ...

### pharmacy-client
- ...

### pharmacy-server
- ...

### root src legacy
- Giữ nguyên / chỉ tham khảo / hoặc cập nhật tối thiểu nếu có lý do.

## 5. Flow mới

```text
LapHoaDon_Ctrl
    -> HoaDonClientService
    -> RmiClientProvider.getHoaDonRemote()
    -> HoaDonRemote.createInvoice(...)
    -> HoaDonRemoteAdapter
    -> HoaDonServiceImpl.createInvoice(...)
    -> HoaDonRepository
    -> TransactionManager
    -> MariaDB
```

## 6. Mapping legacy -> new

| Legacy | New | Trạng thái | Ghi chú |
|---|---|---|---|
| HoaDon_Dao.insert/generateNewMaHD | HoaDonServiceImpl + CodeGenerationService | ... | ... |
| ChiTietHoaDon_Dao.insert/selectByMaHD | HoaDonRepository | ... | ... |
| LapHoaDon_Ctrl gọi DAO | LapHoaDon_Ctrl gọi HoaDonClientService | ... | ... |

## 7. Scan sai tầng

### Client
Kết quả scan:
- ...

### Common
Kết quả scan:
- ...

### Server
Kết quả scan:
- ...

## 8. Build result

Lệnh:
```bash
mvn -f pharmacy-parent/pom.xml -DskipTests compile
```

Kết quả:
- ...

## 9. Test thủ công cần làm

- [ ] Bán một thuốc
- [ ] Bán nhiều thuốc
- [ ] Không cho bán vượt tồn
- [ ] Thanh toán tiền mặt
- [ ] Thanh toán chuyển khoản
- [ ] Tạo hóa đơn từ phiếu đặt nếu có
- [ ] Search hóa đơn
- [ ] Xem chi tiết hóa đơn

## 10. Chưa làm / rủi ro còn lại

- ...
```
