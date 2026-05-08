# STEP 18 - Final E2E Verification + Legacy Consolidation + Legacy Cleanup Plan

## 1. Mục tiêu

Đây là giai đoạn sau khi các group migration chức năng 1 -> 7 đã hoàn thành.

Mục tiêu của Step 18 **không phải migrate thêm màn hình mới**, cũng **không phải xóa code cũ ngay lập tức**. Mục tiêu là:

1. Nghiệm thu runtime end-to-end toàn bộ hệ thống mới.
2. Scan lại kiến trúc sau migration.
3. Kiểm tra code cũ và code mới còn đang dùng lẫn nhau ở đâu.
4. **Gom các phần code cũ thật sự còn cần dùng vào project mới theo đúng tầng kiến trúc.**
5. Lập danh sách legacy/JDBC/SQL Server-specific còn sót.
6. Lập kế hoạch cleanup an toàn cho Step 19.
7. Tạo tài liệu kết quả để biết module nào đã sẵn sàng xóa/archive legacy, module nào cần giữ lại để đối chiếu.

Kiến trúc đích cần xác nhận:

```text
JavaFX Client
    -> Client Service
    -> RMI Stub
    -> Remote Interface trong pharmacy-common
    -> Server Remote Adapter
    -> Server Service
    -> Repository / TransactionManager
    -> MariaDB
```

---

## 2. Nguyên tắc quan trọng

### 2.1. Chưa xóa root legacy trong task này

Step 18 chỉ audit, verify, gom phần cần dùng vào project mới và lập plan. Không xóa root legacy `src/` ngay, không xóa hàng loạt DAO cũ nếu chưa có bằng chứng runtime pass.

Root `src/` cũ tạm thời giữ vai trò:

```text
- reference để đối chiếu nghiệp vụ
- backup logic cũ
- nguồn để kiểm tra query/flow cũ khi cần
```

### 2.2. Không copy nguyên xi code cũ vào project mới

Khi có phần code cũ còn cần dùng, **không được bê nguyên class DAO/ConnectDB/service cũ vào module mới** theo kiểu giữ nguyên kiến trúc cũ.

Sai:

```text
src/.../dao/Thuoc_SanPham_Dao.java
    -> copy nguyên sang pharmacy-server/.../dao/Thuoc_SanPham_Dao.java
    -> service mới gọi lại DAO cũ
```

Đúng:

```text
logic/query cần dùng trong DAO cũ
    -> phân tích phần cần giữ
    -> viết lại thành Repository interface/implementation mới
    -> dùng MariaDB syntax
    -> service mới gọi repository mới
```

### 2.3. Không refactor nghiệp vụ lớn trong Step 18 nếu chưa cần

Không sửa logic bán hàng, nhập hàng, đổi/trả, thống kê nếu không có lỗi compile hoặc lỗi runtime rõ ràng.

Step 18 chỉ được sửa nhỏ nếu cần:

```text
- fix typo docs
- fix script test/checklist
- fix lỗi build đơn giản
- gom phần code cần dùng vào đúng module nếu đang bị đặt sai tầng
- thay import DAO cũ bằng repository/service mới ở phạm vi nhỏ, có kiểm soát
```

Nếu phát hiện bug nghiệp vụ lớn, ghi vào TODO/risk, không tự ý sửa lan.

### 2.4. Không chuyển JDBC sang JPA/JPQL hàng loạt

Một số repository hiện đang là JDBC transition repository. Step 18 không yêu cầu JPA hóa tiếp. Sau khi runtime E2E pass mới tính Step 20: JPA/JPQL hardening.

### 2.5. Client không được kết nối database

Sau migration, client mới phải sạch:

```text
pharmacy-client không được có:
- ConnectDB
- DAO
- java.sql.Connection
- PreparedStatement
- ResultSet
- CallableStatement
- EntityManager
- Hibernate
- import pharmacy-server
```

### 2.6. Common chỉ là hợp đồng giao tiếp

```text
pharmacy-common không được có:
- JavaFX UI
- JPA entity
- Repository implementation
- ConnectDB/JDBC
- Hibernate
- ServiceImpl
```

`pharmacy-common` chỉ nên chứa:

```text
- Remote interface
- DTO
- Request
- Response
- Enum dùng chung
- Exception dùng chung
- Model shared tạm thời nếu project hiện vẫn còn cần để RMI compile
```

### 2.7. Server không chứa UI

```text
pharmacy-server không được có:
- JavaFX controller
- FXML
- Stage/Scene/TableView
- view package của UI
- dependency JavaFX nếu không cần
```

### 2.8. Server mới không nên gọi DAO cũ

Mục tiêu sau Step 18/19:

```text
com.example.pharmacy.server.service.*
    không import com.example.pharmacymanagementsystem_qlht.dao.*
    không new *_Dao()
    không gọi ConnectDB
```

Nếu còn service mới gọi DAO cũ thì đó là điểm cần gom/refactor theo đúng tầng.

---

## 3. Tài liệu bắt buộc đọc trước khi làm

Codex phải đọc các file sau trước khi scan/sửa:

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

Đọc tiếp các result group:

```text
docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md
docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
docs/migration/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md
```

Nếu file nào không tồn tại thì ghi rõ vào result Step 18, không tự bịa.

---

## 4. Kết quả đầu ra bắt buộc

Sau khi thực hiện, tạo file:

```text
docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md
```

File result phải có:

- Tài liệu đã đọc.
- Build result.
- Scan architecture result.
- RMI binding checklist.
- Database/schema/seed checklist.
- Runtime E2E checklist.
- **Bảng code cũ còn được code mới sử dụng.**
- **Bảng phần code cũ cần gom sang project mới.**
- **Mapping phần cần gom vào `pharmacy-common`, `pharmacy-client`, `pharmacy-server`.**
- Danh sách legacy code còn lại.
- Danh sách dependency cần xem xét xóa.
- Danh sách SQL Server-specific còn sót.
- Các rủi ro runtime.
- Đề xuất Step 19 cleanup.

---

## 5. Pha 1 - Nghiệm thu runtime end-to-end toàn hệ thống mới

Pha 1 là phần quan trọng nhất. **Không cleanup nếu Pha 1 chưa pass.**

### 5.1. Chuẩn bị database MariaDB

Kiểm tra các script SQL hiện có:

```text
SQL/schema_mariadb.sql
SQL/seed_mariadb.sql
SQL/create_pharmacy_app_user.sql
SQL/seed_login_poc_mariadb.sql
SQL/alter_add_code_sequence_mariadb.sql
SQL/alter_add_audit_log_mariadb.sql
```

Nếu script nào không tồn tại thì ghi rõ.

Chạy database theo thứ tự đề xuất:

```sql
-- 1. Tạo schema
SOURCE SQL/schema_mariadb.sql;

-- 2. Tạo app user nếu cần
SOURCE SQL/create_pharmacy_app_user.sql;

-- 3. Seed dữ liệu mẫu
SOURCE SQL/seed_mariadb.sql;

-- 4. Seed login POC nếu cần
SOURCE SQL/seed_login_poc_mariadb.sql;

-- 5. Thêm sequence/audit nếu schema chưa gồm sẵn
SOURCE SQL/alter_add_code_sequence_mariadb.sql;
SOURCE SQL/alter_add_audit_log_mariadb.sql;
```

Nếu dùng HeidiSQL thì copy nội dung từng file và chạy theo thứ tự tương ứng.

### 5.2. Kiểm tra config kết nối

Kiểm tra:

```text
pharmacy-parent/pharmacy-server/src/main/resources/META-INF/persistence.xml
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JdbcConnectionProvider.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JpaUtil.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/DatabaseProperties.java
```

Cần xác nhận:

- URL trỏ tới MariaDB, không phải SQL Server.
- Username/password đúng với local/dev DB.
- Charset tiếng Việt đúng.
- `hibernate.hbm2ddl.auto` nên là `validate` hoặc cấu hình đã được chú thích rõ.

### 5.3. Build project mới

Chạy từ root repo:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Nếu pass, chạy tiếp:

```powershell
mvn -f pharmacy-parent/pom.xml clean install
```

Nếu `clean install` fail do file lock/permission `.m2`, ghi rõ là môi trường hay code. Không sửa code khi chưa xác định.

### 5.4. Start RMI server

Chạy:

```powershell
mvn -f pharmacy-parent/pharmacy-server/pom.xml exec:java "-Dexec.mainClass=com.example.pharmacy.server.bootstrap.RmiServerBootstrap"
```

Nếu project chưa có `exec-maven-plugin`, có thể chạy bằng IDE class:

```text
com.example.pharmacy.server.bootstrap.RmiServerBootstrap
```

Server phải bind các remote sau:

```text
AuthRemoteService
KhachHangRemoteService
KhuyenMaiRemoteService
NhaCungCapRemoteService
DonViTinhRemoteService
KeHangRemoteService
NhomDuocLyRemoteService
ThuocRemoteService
NhanVienRemoteService
CaiDatRemoteService
PhieuNhapRemoteService
PhieuDatHangRemoteService
TonKhoRemoteService
HoaDonRemoteService
DoiTraRemoteService
ReportRemoteService
AuditLogRemoteService
```

Nếu server fail khi bind một service, ghi rõ service nào fail, stacktrace nào, không tiếp tục cleanup.

### 5.5. Start JavaFX client mới

Chạy client từ module:

```powershell
mvn -f pharmacy-parent/pharmacy-client/pom.xml javafx:run
```

Hoặc chạy main class trong IDE:

```text
com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl
```

Cần đảm bảo client kết nối tới:

```text
localhost:1099
```

thông qua:

```text
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java
```

---

## 6. Runtime checklist theo group

### Group 0 - RMI/Auth baseline

- [ ] Server start không lỗi.
- [ ] Client start không lỗi.
- [ ] Đăng nhập đúng thành công.
- [ ] Đăng nhập sai mật khẩu báo lỗi rõ ràng.
- [ ] Tài khoản khóa bị chặn.
- [ ] Tắt server rồi client login phải báo lỗi kết nối dễ hiểu.

### Group 1 - DonViTinh + Thuoc + KeHang

- [ ] Mở danh mục thuốc.
- [ ] Load danh sách thuốc.
- [ ] Tìm kiếm thuốc.
- [ ] Thêm/sửa/xóa hoặc khóa/mở thuốc nếu UI hỗ trợ.
- [ ] Đơn vị tính load đúng.
- [ ] Kệ hàng load đúng.
- [ ] Tồn kho theo lô hiện đúng.
- [ ] Không có lỗi serialization khi load list lớn.
- [ ] Kiểm tra cụm thuốc có còn gọi DAO cũ/ConnectDB không.

### Group 2 - NhanVien + TaiKhoan + PhanQuyen + CaiDat

- [ ] Mở danh mục nhân viên.
- [ ] Load danh sách nhân viên.
- [ ] Thêm/sửa/xóa/khóa mở nếu UI hỗ trợ.
- [ ] Quản lý tài khoản/role hiển thị đúng.
- [ ] Cài đặt load/sửa được.
- [ ] Logout/login lại không bị lẫn session.
- [ ] Audit ghi đúng actor nếu có thao tác.

### Group 3 - KhuyenMai + CapNhatKhuyenMai

- [ ] Mở danh mục khuyến mãi.
- [ ] Load danh sách.
- [ ] Thêm/sửa/xóa/cập nhật khuyến mãi nếu UI hỗ trợ.
- [ ] Lookup khuyến mãi khi bán hàng đúng.
- [ ] Khuyến mãi hết hạn/không hợp lệ không bị áp sai.
- [ ] Kiểm tra cụm khuyến mãi có còn gọi DAO cũ/ConnectDB không.

### Group 4 - PhieuNhap + PhieuDatHang + CapNhatSoLuong

- [ ] Lập phiếu nhập.
- [ ] Lập phiếu đặt hàng.
- [ ] Tìm kiếm phiếu nhập.
- [ ] Tìm kiếm phiếu đặt hàng.
- [ ] Cập nhật số lượng/tình trạng phiếu đặt.
- [ ] Tồn kho tăng đúng sau phiếu nhập.
- [ ] Transaction rollback đúng nếu lỗi giữa chừng.

### Group 5 - HoaDon + BanHang

- [ ] Lập hóa đơn OTC với 1 thuốc.
- [ ] Lập hóa đơn OTC với nhiều dòng.
- [ ] Lập hóa đơn ETC nếu có mã đơn thuốc.
- [ ] Không cho bán vượt tồn.
- [ ] Trừ tồn đúng theo lô.
- [ ] Áp khuyến mãi sản phẩm/hóa đơn đúng.
- [ ] Thanh toán tiền mặt tính tiền thừa đúng.
- [ ] Thanh toán chuyển khoản không lỗi.
- [ ] Tạo hóa đơn từ phiếu đặt hàng nếu có.
- [ ] Tìm kiếm hóa đơn.
- [ ] Xem chi tiết hóa đơn.
- [ ] Audit lập hóa đơn có ghi nhận.

### Group 6 - DoiTra

- [ ] Lập phiếu trả từ hóa đơn hợp lệ.
- [ ] Lập phiếu đổi từ hóa đơn hợp lệ.
- [ ] Hóa đơn không tồn tại báo lỗi.
- [ ] Hóa đơn quá 7 ngày bị chặn.
- [ ] Hóa đơn chưa có khách hàng xử lý đúng theo flow hiện tại.
- [ ] Trả hàng số lượng lớn hơn số đã bán - đã trả bị chặn.
- [ ] Đổi hàng số lượng lớn hơn số đã bán - đã đổi bị chặn.
- [ ] Đổi hàng khi tồn thay thế không đủ bị chặn từ server.
- [ ] Search phiếu trả thấy dữ liệu mới.
- [ ] Search phiếu đổi thấy dữ liệu mới.
- [ ] Xem chi tiết phiếu trả/phiếu đổi.
- [ ] Tồn kho sau trả/đổi đúng với logic đã ghi trong docs.

### Group 7 - ThongKe + HoatDong + BaoCao

- [ ] Thống kê bán hàng preset Hôm nay/Tuần này/Tháng này/Năm nay.
- [ ] Thống kê bán hàng tùy chọn date range.
- [ ] Top sản phẩm bán chạy.
- [ ] Top sản phẩm doanh thu.
- [ ] Thống kê XNT.
- [ ] Thuốc hết hạn/sắp hết hạn.
- [ ] Xuất Excel/PDF nếu UI có.
- [ ] TKHoatDong load audit log từ `audit_log`.
- [ ] Filter TKHoatDong theo ngày/keyword.
- [ ] ChiTietHoatDong hiện đúng nội dung audit.

### Tiêu chí kết thúc Pha 1

Pha 1 được coi là pass khi:

- Build compile pass.
- RMI server start pass.
- Client start pass.
- Auth pass.
- Mỗi group 1 -> 7 có ít nhất flow chính pass.
- Các lỗi còn lại nếu có đã được ghi rõ và không phải lỗi kiến trúc tầng.

Nếu Pha 1 fail, Step 18 result phải ghi rõ fail ở đâu và đề xuất Step 18.x fix runtime trước khi cleanup.

---

## 7. Pha 2 - Audit code cũ và code mới còn dùng lẫn nhau

Pha này dùng để trả lời câu hỏi:

```text
Code mới còn phụ thuộc code cũ ở đâu?
Code cũ nào thật sự còn cần giữ?
Code cũ nào đã chết, có thể archive/xóa ở Step 19?
```

### 7.1. Scan root legacy có gọi ngược vào project mới không

Mục tiêu: root `src/` nên là legacy/reference, không gọi vào module mới.

```powershell
Get-ChildItem src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacy\.client|com\.example\.pharmacy\.common|com\.example\.pharmacy\.server" |
  Select-Object Path, LineNumber, Line
```

Kết quả mong đợi:

```text
Không có match.
```

Nếu có match, ghi rõ root legacy đang phụ thuộc code mới ở đâu.

### 7.2. Scan client mới có dùng code DB cũ không

```powershell
Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|new .*_Dao|import .*\.dao\.|java\.sql|DriverManager|PreparedStatement|ResultSet|CallableStatement|EntityManager|org\.hibernate|jakarta\.persistence|com\.example\.pharmacy\.server" |
  Select-Object Path, LineNumber, Line
```

Kết quả mong đợi:

```text
Không có match.
```

### 7.3. Scan server service mới còn gọi DAO cũ

Đây là scan quan trọng nhất.

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

Nếu có match, ghi vào bảng:

| Service mới | DAO cũ đang dùng | Mức độ nguy hiểm | Cách gom đúng |
|---|---|---|---|

Ví dụ:

```text
ThuocServiceImpl
    -> Thuoc_SanPham_Dao
    -> Thuoc_SP_TheoLo_Dao
    -> DonViTinh_Dao
    -> ChiTietDonViTinh_Dao
    -> HoatChat_Dao
    -> LoaiHang_Dao

KhuyenMaiServiceImpl
    -> KhuyenMai_Dao
    -> LoaiKhuyenMai_Dao
    -> ChiTietKhuyenMai_Dao
    -> Thuoc_SP_TangKem_Dao
```

### 7.4. Scan toàn bộ pharmacy-parent còn ConnectDB/DAO cũ

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao|prepareCall|CallableStatement" |
  Select-Object Path, LineNumber, Line
```

Phân loại kết quả:

```text
A. Code mới đang dùng trực tiếp -> cần gom/refactor.
B. Legacy DAO còn nằm trong pharmacy-parent nhưng không ai gọi -> ứng viên archive/xóa Step 19.
C. Docs/comment/test -> không tính lỗi.
```

### 7.5. Scan common dùng model legacy

```powershell
Get-ChildItem pharmacy-parent/pharmacy-common/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.model" |
  Select-Object Path, LineNumber, Line
```

Nếu có match, ghi rõ:

```text
Hiện common còn dùng model legacy làm DTO tạm thời.
Đây chưa phải lỗi nghiêm trọng như DAO/ConnectDB, nhưng nên được đưa vào Step 20/21 để DTO hóa dần.
```

---

## 8. Pha 3 - Gom các phần code cũ còn cần dùng vào project mới

Pha này là phần bổ sung quan trọng: **gom phần cần dùng vào project mới**, nhưng theo đúng tầng.

### 8.1. Nguyên tắc gom code

Không gom theo kiểu copy class cũ. Gom theo kiểu **trích logic và đặt lại đúng module**.

| Loại code cũ | Nếu còn cần thì chuyển thành | Module đích |
|---|---|---|
| Controller JavaFX cũ | Controller/View mới, chỉ xử lý UI | `pharmacy-client` |
| View/FXML/CSS/icon | Resource/UI mới | `pharmacy-client` |
| Request từ UI | Request DTO | `pharmacy-common` |
| Response hiển thị | Response DTO/View DTO | `pharmacy-common` |
| Model truyền qua RMI | DTO chuẩn hoặc model shared tạm | `pharmacy-common` |
| DAO cũ | Repository interface + implementation | `pharmacy-server` |
| SQL query cũ | MariaDB query trong repository mới | `pharmacy-server/repository/jdbc` |
| Stored procedure cũ | Server service/transaction service | `pharmacy-server/service` |
| Sinh mã cũ | `CodeGenerationService` | `pharmacy-server/service` |
| Audit cũ | `AuditService` | `pharmacy-server/service` |
| ConnectDB cũ | Không chuyển; thay bằng `JdbcConnectionProvider`/JPA | `pharmacy-server/config` |

### 8.2. Các phần tuyệt đối không đưa vào project mới

Không đưa vào `pharmacy-parent` các phần sau nếu còn giữ nguyên logic cũ:

```text
- connectDB/ConnectDB.java trỏ SQL Server
- DAO cũ gọi ConnectDB
- Service cũ gọi DAO cũ
- Stored procedure SQL Server call qua CallableStatement
- Query dùng SELECT TOP, ISNULL, GETDATE, OUTPUT INSERTED, SCOPE_IDENTITY
- UI controller đặt trong server
- FileChooser/export code đặt trong server
```

### 8.3. Cách xử lý khi code mới đang cần DAO cũ

Nếu service mới đang dùng DAO cũ, không xóa DAO ngay. Làm theo các bước:

```text
1. Đọc method DAO cũ đang được service mới gọi.
2. Ghi lại chính xác method nào còn cần.
3. Chỉ trích query/logic cần thiết.
4. Tạo hoặc bổ sung Repository interface mới trong com.example.pharmacy.server.repository.
5. Implement bằng JDBC MariaDB hoặc JPA trong com.example.pharmacy.server.repository.jdbc / repository.
6. Inject repository vào service qua ServerComponentFactory.
7. Xóa import DAO cũ khỏi service.
8. Chạy build.
9. Chạy lại scan.
10. Khi không còn ai dùng DAO cũ, mới đưa DAO đó vào cleanup Step 19.
```

### 8.4. Ví dụ gom cụm Thuoc

Nếu `ThuocServiceImpl` còn dùng:

```text
Thuoc_SanPham_Dao
Thuoc_SP_TheoLo_Dao
DonViTinh_Dao
ChiTietDonViTinh_Dao
HoatChat_Dao
LoaiHang_Dao
```

Không copy thêm DAO. Hướng đúng:

```text
MedicineCatalogRepository
    findAllMedicines()
    findAllLoaiHang()
    findAllHoatChat()
    findChiTietHoatChatByMaThuoc()
    createMedicine(...)
    updateMedicine(...)
    softDeleteMedicine(...)
    getTongSoLuongTonByMaThuoc()
    getTenDonViTinhCoBan()
    getThuocTonKho()
    getAllTheoLo()
```

Implementation:

```text
JdbcMedicineCatalogRepository
```

Service mới:

```text
ThuocServiceImpl hoặc ThuocCatalogServiceImpl
    -> gọi MedicineCatalogRepository
    -> không gọi DAO cũ
```

### 8.5. Ví dụ gom cụm KhuyenMai

Nếu `KhuyenMaiServiceImpl` còn dùng:

```text
KhuyenMai_Dao
LoaiKhuyenMai_Dao
ChiTietKhuyenMai_Dao
Thuoc_SP_TangKem_Dao
```

Hướng đúng:

```text
KhuyenMaiRepository hoặc PromotionRepository
    findAll()
    findById()
    searchByKeyword()
    findAllLoaiKhuyenMai()
    findChiTietByMaKM()
    findQuaTangByMaKM()
    createPromotionWithDetails(...)
    updatePromotionWithDetails(...)
    deletePromotion(...)
    findActiveOn(...)
    findActiveInvoiceOn(...)
```

Nếu create/update/delete có nhiều bảng liên quan, phải đi qua:

```text
TransactionManager.execute(...)
```

### 8.6. Output bắt buộc của pha gom code

Trong result Step 18, Codex phải tạo bảng:

| Code cũ còn cần | Đang được dùng bởi | Module đích | Cách gom | Trạng thái |
|---|---|---|---|---|
| `Thuoc_SanPham_Dao.selectAll()` | `ThuocServiceImpl` | `pharmacy-server/repository` | chuyển thành `MedicineCatalogRepository.findAllMedicines()` | TODO/DONE |
| `KhuyenMai_Dao.selectAll()` | `KhuyenMaiServiceImpl` | `pharmacy-server/repository` | chuyển thành `PromotionRepository.findAll()` | TODO/DONE |

Nếu chưa thực hiện gom trong Step 18, phải ghi rõ là `TODO Step 19A`.

### 8.7. Khi nào được xem là gom xong?

Một phần được xem là gom xong khi:

```text
- Code mới không còn import DAO cũ.
- Code mới không gọi ConnectDB.
- Query đã dùng MariaDB/JPA phù hợp.
- Service mới gọi repository/service mới.
- Build pass.
- Runtime smoke test pass.
```

---

## 9. Pha 4 - Legacy Cleanup Audit Plan

Chỉ lập plan, chưa xóa.

### 9.1. Scan legacy JDBC trong pharmacy-parent

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|DriverManager|java\.sql|PreparedStatement|ResultSet|CallableStatement|prepareCall" |
  Select-Object Path, LineNumber, Line
```

Không bắt buộc zero vì server có thể còn JDBC transition repository. Nhưng cần phân loại:

```text
A. JDBC transition repository mới, được phép tạm thời:
   pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/*

B. Legacy DAO cũ trong package com.example.pharmacymanagementsystem_qlht.dao:
   ứng viên cleanup Step 19 nếu không còn được import/use.

C. ConnectDB legacy:
   ứng viên cleanup Step 19 nếu không còn code mới dùng.
```

### 9.2. Scan SQL Server-specific

```powershell
Get-ChildItem . -Recurse -File -Include *.java,*.sql,*.xml,*.md |
  Select-String -Pattern "SELECT TOP|TOP \(|ISNULL\(|GETDATE\(|CONTEXT_INFO|SCOPE_IDENTITY|OUTPUT INSERTED|IDENTITY\(|NVARCHAR|DATETIME2|prepareCall|CallableStatement|sp_" |
  Select-Object Path, LineNumber, Line
```

Phân loại kết quả:

```text
A. Root legacy/reference: chấp nhận tạm thời.
B. Docs nói về migration: chấp nhận.
C. SQL Server schema cũ: chấp nhận nếu archive/reference.
D. pharmacy-parent code mới: cần xử lý hoặc ghi risk.
```

### 9.3. Scan dependency cleanup

Kiểm tra:

```text
pom.xml
pharmacy-parent/pom.xml
pharmacy-parent/pharmacy-common/pom.xml
pharmacy-parent/pharmacy-client/pom.xml
pharmacy-parent/pharmacy-server/pom.xml
```

Cần xác định:

- `mssql-jdbc` còn ở đâu.
- `javafx-base` còn ở server không.
- Dependency nào chỉ phục vụ root legacy.
- Dependency nào phục vụ client export/UI.
- Dependency nào phục vụ server JPA/MariaDB.

Không xóa dependency trong Step 18, chỉ đề xuất.

### 9.4. Kiểm tra import/use của DAO legacy trong pharmacy-parent

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao\(" |
  Select-Object Path, LineNumber, Line
```

Nếu không có code mới import DAO legacy, Step 19 có thể archive/xóa package DAO legacy trong `pharmacy-parent/pharmacy-server`.

Root `src/main/java/.../dao` không xóa trong Step 18.

---

## 10. Thứ tự cleanup đề xuất cho Step 19

Chỉ được tiến hành Step 19 nếu Step 18 E2E pass hoặc nếu lỗi còn lại chỉ là cleanup rõ ràng, không ảnh hưởng runtime chính.

### 10.1. Cleanup docs/scripts trước

- Tạo `docs/migration/STEP_19_LEGACY_CLEANUP_RESULT.md`.
- Ghi rõ danh sách xóa/archive.
- Tạo branch riêng.

### 10.2. Step 19A - Remove legacy DAO usage from new server services

Ưu tiên xử lý service mới còn gọi DAO cũ:

```text
ThuocServiceImpl
KhuyenMaiServiceImpl
các service khác nếu scan phát hiện
```

Mục tiêu:

```text
com.example.pharmacy.server.service.*
    không còn import com.example.pharmacymanagementsystem_qlht.dao.*
    không còn new *_Dao()
    không còn gọi ConnectDB
```

### 10.3. Step 19B - Archive/xóa legacy DAO trong pharmacy-parent

Ứng viên xóa/archive:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/ConnectDB.java
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/*
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/service/* nếu không còn dùng
```

Chỉ xóa nếu scan import/use = 0.

### 10.4. Step 19C - Xóa mssql-jdbc khỏi pharmacy-server

Điều kiện:

- Không còn code mới nào kết nối SQL Server.
- Không còn legacy DAO trong pharmacy-server cần compile.
- Build pass sau khi xóa.

### 10.5. Step 19D - Giữ root `src/` làm archive/reference trong một thời gian

Không xóa root legacy ngay nếu chưa có bản tag/release.

Có thể đề xuất:

```text
archive/legacy-monolith/
```

hoặc tạo git tag:

```text
legacy-before-final-cleanup
```

### 10.6. Sau cleanup mới tính JPA/JPQL hardening

Step 20 có thể là:

```text
STEP_20_JPA_JPQL_HARDENING_PLAN.md
```

Mục tiêu:

- Chọn repository JDBC transition để JPA hóa dần.
- Không làm hàng loạt.
- Ưu tiên CRUD đơn giản trước, transaction/report phức tạp sau.

---

## 11. Template result cho Step 18

Codex tạo file:

```text
docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md
```

Nội dung:

```markdown
# STEP 18 - Final E2E Verification Result

## 1. Mục tiêu

Nghiệm thu runtime end-to-end toàn hệ thống mới, kiểm tra code cũ/mới còn dùng lẫn nhau, gom phần code cũ còn cần vào đúng module mới và lập cleanup plan.

## 2. Tài liệu đã đọc

- ...
- File không tìm thấy: ...

## 3. Build result

Command:

```bash
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
mvn -f pharmacy-parent/pom.xml clean install
```

Result:

```text
...
```

## 4. RMI server runtime result

- Command/IDE main class:
- Port:
- Binding list:
- Result:
- Error nếu có:

## 5. Client runtime result

- Command/IDE main class:
- Login result:
- Error nếu có:

## 6. E2E checklist theo group

### Auth
- [ ] ...

### Group 1 - DonViTinh + Thuoc + KeHang
- [ ] ...

### Group 2 - NhanVien + TaiKhoan + CaiDat
- [ ] ...

### Group 3 - KhuyenMai
- [ ] ...

### Group 4 - PhieuNhap + PhieuDatHang + TonKho
- [ ] ...

### Group 5 - HoaDon + BanHang
- [ ] ...

### Group 6 - DoiTra
- [ ] ...

### Group 7 - ThongKe + HoatDong + BaoCao
- [ ] ...

## 7. Code cũ và code mới còn dùng lẫn nhau

### Root legacy gọi code mới

```text
...
```

### Client mới gọi code cũ/DB

```text
...
```

### Server service mới gọi DAO cũ

```text
...
```

### Common dùng model legacy

```text
...
```

## 8. Bảng phần code cũ còn cần gom vào project mới

| Code cũ còn cần | Đang được dùng bởi | Module đích | Cách gom đúng | Trạng thái |
|---|---|---|---|---|
| ... | ... | ... | ... | TODO/DONE |

## 9. Mapping gom code theo tầng

| Loại logic | Nguồn cũ | Đích mới | Ghi chú |
|---|---|---|---|
| UI | src/.../controller/view | pharmacy-client | ... |
| DTO/request/response | model/params cũ | pharmacy-common | ... |
| Query DAO | dao cũ | pharmacy-server/repository | ... |
| Transaction/proc | stored procedure/service cũ | pharmacy-server/service | ... |

## 10. Architecture scan result

### Client wrong-layer scan

```text
...
```

### Common wrong-layer scan

```text
...
```

### Server UI leakage scan

```text
...
```

### JDBC/legacy scan

```text
...
```

### SQL Server-specific scan

```text
...
```

## 11. Legacy code còn lại

| Path | Loại | Còn được dùng không | Đề xuất |
|---|---|---|---|
| ... | ... | ... | ... |

## 12. Dependency cleanup candidates

| File | Dependency | Lý do còn tồn tại | Đề xuất |
|---|---|---|---|
| pharmacy-parent/pharmacy-server/pom.xml | mssql-jdbc | ... | ... |

## 13. Rủi ro còn lại

- ...

## 14. Kết luận

- Step 18 PASS/FAIL:
- Nếu PASS: sẵn sàng Step 19 legacy cleanup.
- Nếu FAIL: cần Step 18.x fix runtime trước.

## 15. Đề xuất Step 19

- ...
```

---

## 12. Prompt dùng trực tiếp cho Codex

```text
Bạn đang làm trong repo PharmacyManagementSystem-Re-architecture. Các group migration 1 -> 7 đã được thực hiện. Task lần này là Step 18: Final E2E Verification + Legacy Consolidation + Legacy Cleanup Plan.

Mục tiêu:
- Không migrate thêm chức năng mới.
- Không xóa code cũ ngay.
- Nghiệm thu runtime end-to-end toàn bộ hệ thống mới.
- Scan kiến trúc sau migration.
- Kiểm tra code cũ và code mới còn dùng lẫn nhau ở đâu.
- Gom các phần code cũ thật sự còn cần dùng vào project mới theo đúng tầng.
- Lập danh sách legacy/JDBC/SQL Server-specific còn sót.
- Tạo cleanup plan an toàn cho Step 19.

Bắt buộc đọc trước:
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
- docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md
- docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md
- docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md
- docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md
- docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md
- docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md
- docs/migration/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md

Việc cần làm:

1. Build verification:
   - Chạy mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
   - Nếu pass, chạy mvn -f pharmacy-parent/pom.xml clean install
   - Ghi rõ pass/fail, nếu fail do file lock/.m2 thì phân biệt môi trường hay code.

2. Runtime E2E checklist:
   - Kiểm tra SQL schema/seed MariaDB cần dùng.
   - Start RmiServerBootstrap.
   - Kiểm tra remote binding list.
   - Start JavaFX client mới.
   - Test login và các group 1 -> 7 theo checklist.
   - Nếu không thể test runtime trên môi trường hiện tại, ghi rõ "chưa chạy được runtime" và tạo checklist thao tác thủ công đầy đủ.

3. Architecture scan:
   - pharmacy-client không có ConnectDB/DAO/java.sql/EntityManager/Hibernate/import server.
   - pharmacy-common không có JavaFX/JPA/Hibernate/ConnectDB/implementation.
   - pharmacy-server không có JavaFX UI/controller/view.
   - scan JDBC legacy trong pharmacy-parent.
   - scan SQL Server-specific: TOP, ISNULL, GETDATE, CONTEXT_INFO, OUTPUT INSERTED, SCOPE_IDENTITY, prepareCall, CallableStatement, sp_.

4. Scan code cũ và code mới còn dùng lẫn nhau:
   - root src có gọi com.example.pharmacy.* không.
   - pharmacy-client có gọi DAO/ConnectDB không.
   - pharmacy-server service mới có import com.example.pharmacymanagementsystem_qlht.dao hoặc new *_Dao không.
   - pharmacy-common có dùng model legacy làm DTO tạm không.
   - ghi rõ từng file/path.

5. Gom phần code cũ còn cần vào project mới:
   - Không copy nguyên DAO/ConnectDB cũ.
   - Nếu service mới còn gọi DAO cũ, đọc method DAO đang dùng rồi chuyển logic sang repository/service mới đúng tầng.
   - UI -> pharmacy-client.
   - Remote/DTO/request/response -> pharmacy-common.
   - Business/service/repository/query/transaction -> pharmacy-server.
   - ConnectDB cũ không được gom; thay bằng JdbcConnectionProvider/JPA.
   - SQL Server syntax phải đổi sang MariaDB syntax.
   - Nếu chưa gom được trong Step 18, ghi rõ TODO Step 19A.

6. Dependency audit:
   - Kiểm tra pom.xml root và các pom trong pharmacy-parent.
   - Ghi rõ mssql-jdbc còn ở đâu.
   - Ghi rõ javafx dependency có còn trong server không.
   - Không xóa dependency trong Step 18, chỉ đề xuất.

7. Legacy cleanup plan:
   - Liệt kê legacy DAO/ConnectDB còn trong pharmacy-parent.
   - Kiểm tra có còn import/use không.
   - Đề xuất thứ tự cleanup Step 19.
   - Giữ root src legacy làm reference, không xóa trong task này.

Output bắt buộc:
- Tạo file docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md
- File phải có build result, runtime result, scan result, bảng code cũ/mới còn dùng lẫn nhau, bảng phần cần gom vào module mới, legacy list, dependency cleanup candidates, risk, đề xuất Step 19.

Luật:
- Không cho client phụ thuộc server.
- Không cho client kết nối DB.
- Không xóa root src legacy.
- Không xóa DAO/ConnectDB khi còn service mới sử dụng.
- Không copy nguyên DAO cũ vào module mới.
- Không JPA hóa/JPQL hóa hàng loạt.
- Không sửa nghiệp vụ bán hàng/nhập hàng/đổi trả/thống kê nếu không có lỗi compile/runtime rõ ràng.
```

---

## 13. Kết luận Step 18

Nếu Step 18 PASS, bước tiếp theo hợp lý là:

```text
STEP_19_LEGACY_CLEANUP_RESULT.md
```

Mục tiêu Step 19:

- Step 19A: Remove legacy DAO usage from new server services.
- Step 19B: Xóa/archive legacy DAO/ConnectDB trong `pharmacy-parent` nếu không còn được use.
- Step 19C: Xóa `mssql-jdbc` khỏi `pharmacy-server` nếu không còn code mới cần.
- Step 19D: Giữ root legacy hoặc archive có tag.
- Build lại và test smoke.

Nếu Step 18 FAIL, không cleanup. Tạo Step 18.x fix runtime theo lỗi cụ thể trước.
