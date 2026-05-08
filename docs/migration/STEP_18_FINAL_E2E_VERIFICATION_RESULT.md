# STEP 18 - Final E2E Verification Result

## 1. Muc tieu Step 18

Step 18 khong migrate them man hinh/chuc nang moi. Muc tieu la:

- build va audit kien truc sau cac step/group migration 0 -> 7
- thu runtime end-to-end o muc an toan co the
- xac dinh code cu va code moi con dung lan nhau o dau
- xac dinh phan legacy nao thuc su con can giu
- lap cleanup plan an toan cho Step 19

## 2. Tai lieu da doc

Tat ca cac file duoc yeu cau trong Step 18 deu ton tai va da duoc doc/doi chieu:

- `docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`
- `docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`
- `docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md`
- `docs/migration/DAO_MIGRATION_MAPPING.md`
- `docs/migration/QUERY_INVENTORY.md`
- `docs/migration/MIGRATION_PRIORITY.md`
- `docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md`
- `docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md`
- `docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md`
- `docs/migration/STEP_14_GROUP3_KHUYENMAI_MIGRATION.md`
- `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md`
- `docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md`
- `docs/migration/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_RESULT.md`
- `docs/migration/STEP_18_FINAL_E2E_VERIFICATION_AND_LEGACY_CLEANUP_PLAN_UPDATED.md`

## 3. Build result

### 3.1. Compile

Lenh:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Ket qua:

- **PASS**

### 3.2. Clean install

Lenh:

```powershell
mvn -f pharmacy-parent/pom.xml clean install
```

Ket qua:

- **FAIL do moi truong**, khong phai loi compile code

Chi tiet:

- Maven ghi duoc compile module moi
- nhung `install` fail khi ghi file tam vao `.m2`
- loi quan sat duoc:
  - `Failed to install artifact ... .tmp -> Access is denied`
- truoc do da tung gap them mot case `clean` fail do file lock trong `pharmacy-client/target`

Ket luan build:

- codebase moi hien tai **compile duoc**
- `clean install` chua the xem la bang chung runtime pass vi dang bi chan boi quyen ghi local Maven repository

## 4. Database / script checklist

Da kiem tra su ton tai cua cac script duoc Step 18 yeu cau:

- `SQL/schema_mariadb.sql` -> co
- `SQL/seed_mariadb.sql` -> co
- `SQL/create_pharmacy_app_user.sql` -> co
- `SQL/seed_login_poc_mariadb.sql` -> co
- `SQL/alter_add_code_sequence_mariadb.sql` -> co
- `SQL/alter_add_audit_log_mariadb.sql` -> co

## 5. Config ket noi hien tai

Da xac nhan:

- `pharmacy-parent/pharmacy-server/src/main/resources/META-INF/persistence.xml`
  - URL: `jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc`
  - user: `pharmacy_app`
  - password default: `123456`
  - dialect: `org.hibernate.dialect.MariaDBDialect`
  - `hibernate.hbm2ddl.auto=validate`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/DatabaseProperties.java`
  - ho tro override qua system property / environment variable
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JpaUtil.java`
  - ho tro override runtime va log cau hinh an toan
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JdbcConnectionProvider.java`
  - dung `MariaDbConnectionFactory`

## 6. Runtime E2E result

### 6.1. Muc da thu duoc

Da thu cac huong runtime sau:

- thu DB CLI bang `mariadb --protocol=TCP ...`
- thu start RMI server bang `mvn ... exec:java`

### 6.2. Ket qua

Runtime E2E **chua verify duoc tren moi truong hien tai**.

Ly do cu the:

1. May hien tai **khong co lenh `mariadb`**
   - khong test duoc DB bang terminal theo checklist Step 18
2. Thu start `RmiServerBootstrap` qua Maven `exec:java`
   - bi chan boi loi quyen ghi `.m2`
   - Maven khong resolve/plugin bootstrap duoc do:
     - `resolver-status.properties (Access is denied)`
3. JavaFX client la desktop app
   - khong co luong GUI automation de nghiem thu end-to-end trong terminal hien tai
4. Quan trong hon:
   - hai transition service `ThuocServiceImpl` va `KhuyenMaiServiceImpl` van goi legacy DAO
   - cac legacy DAO nay van di qua `ConnectDB`
   - `ConnectDB` trong module server moi van hardcode **SQL Server**

### 6.3. Ket luan runtime

Pha 1 cua Step 18 **chua pass**.

Nguyen nhan gom ca:

- blocker moi truong local (`mariadb` command khong co, `.m2` access denied)
- blocker ky thuat con ton tai trong codebase moi:
  - cum `Thuoc`
  - cum `KhuyenMai`
  van chua MariaDB-pure

## 7. RMI binding checklist

Da kiem tra bang static scan tai:

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`

Server hien tai da co rebind cho:

- `AuthRemoteService`
- `KhachHangRemoteService`
- `KhuyenMaiRemoteService`
- `NhaCungCapRemoteService`
- `DonViTinhRemoteService`
- `KeHangRemoteService`
- `NhomDuocLyRemoteService`
- `ThuocRemoteService`
- `NhanVienRemoteService`
- `CaiDatRemoteService`
- `PhieuNhapRemoteService`
- `PhieuDatHangRemoteService`
- `TonKhoRemoteService`
- `HoaDonRemoteService`
- `DoiTraRemoteService`
- `ReportRemoteService`
- `AuditLogRemoteService`

Ghi chu:

- Trong Step 18, da chuan hoa them 2 binding name de dong bo voi checklist:
  - `NhanVienRemote` -> `NhanVienRemoteService`
  - `CaiDatRemote` -> `CaiDatRemoteService`

## 8. Scan architecture result

### 8.1. pharmacy-client

Da scan:

- `ConnectDB`
- `DriverManager`
- `PreparedStatement`
- `ResultSet`
- `CallableStatement`
- `EntityManager`
- `Hibernate`
- `jakarta.persistence`
- `javax.persistence`
- import `pharmacy-server`
- import legacy DAO

Ket qua:

- **Khong phat hien vi pham** trong `pharmacy-client`

### 8.2. pharmacy-common

Da scan:

- `javafx`
- `jakarta.persistence`
- `javax.persistence`
- `EntityManager`
- `Hibernate`
- `ConnectDB`
- JDBC implementation
- service/controller implementation

Ket qua:

- **Khong phat hien vi pham tang** trong `pharmacy-common`

Luu y:

- `pharmacy-common` van dang chua **37 class model legacy** trong package
  - `com.example.pharmacymanagementsystem_qlht.model`
- day la shared model tam thoi de RMI/client-server compile duoc
- khong phai vi pham layer truc tiep, nhung la **technical debt can tach DTO** o Step 19A/19B

### 8.3. pharmacy-server

Da scan:

- JavaFX UI import
- `Stage`, `Scene`, `TableView`, `FXML`
- import `pharmacy-client`
- import `view` / `controller`

Ket qua:

- **Khong phat hien JavaFX UI/controller/view** trong `pharmacy-server`

Ghi chu:

- `javafx-base` da duoc bo khoi `pharmacy-server/pom.xml`
- 4 DAO report/activity legacy da bi xoa khoi module server moi o Step 17

## 9. Scan code cu va code moi con dung lan nhau

### 9.1. Root `src` co goi nguoc sang project moi khong

Da scan `src/main/java` theo pattern `com.example.pharmacy.`

Ket qua:

- **Khong phat hien root legacy goi nguoc sang module moi**

Y nghia:

- root `src` hien tai dang dung dung vai tro `legacy/reference`
- project moi khong bi root legacy “keo nguoc” vao runtime path

### 9.2. Service moi trong `pharmacy-server` con import legacy DAO khong

Ket qua:

- **Co**, va hien tai chi con 2 transition service ro rang:

| File service moi | Legacy DAO dang dung | Trang thai |
|---|---|---|
| `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ThuocServiceImpl.java` | `Thuoc_SanPham_Dao`, `Thuoc_SP_TheoLo_Dao`, `ChiTietDonViTinh_Dao`, `ChiTietHoatChat_Dao`, `DonViTinh_Dao`, `HoatChat_Dao`, `LoaiHang_Dao` | Can gom lai o Step 19A |
| `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiServiceImpl.java` | `KhuyenMai_Dao`, `LoaiKhuyenMai_Dao`, `ChiTietKhuyenMai_Dao`, `Thuoc_SP_TangKem_Dao` | Can gom lai o Step 19A |

Nhan xet:

- day la **transition dependency** giua service moi va tang DAO cu
- compile khong loi, nhung runtime cua 2 cum nay chua duoc xem la migration “sach”

### 9.3. `ConnectDB` con duoc dung o dau

Ket qua scan:

- `ConnectDB` van ton tai trong:
  - `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/ConnectDB.java`
- `ConnectDB` van duoc goi boi **29 DAO legacy** con lai trong:
  - `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao`

Phat hien quan trong:

- `ConnectDB` hien tai van hardcode:
  - `jdbc:sqlserver://localhost:1433;databaseName=QuanLyNhaThuoc...`
  - user `sa`
  - password `sapassword`

Y nghia:

- bat ky service moi nao con di qua legacy DAO package nay se van chay theo **SQL Server path**
- do do:
  - `ThuocServiceImpl`
  - `KhuyenMaiServiceImpl`
  chua the xem la MariaDB end-to-end

### 9.4. `pharmacy-common` con dung model legacy nao lam DTO tam

Ket qua:

- `pharmacy-common` hien dang giu **37 class model legacy** trong:
  - `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model`

Day la lop shared model tam cho:

- client controller/view wrapper
- remote adapter / repository / service transition
- report / transaction flows chua DTO-hoa xong

Vi du nhom con nhieu anh huong:

- master data: `KhachHang`, `NhaCungCap`, `DonViTinh`, `KeHang`, `NhomDuocLy`, `Thuoc_SanPham`
- giao dich: `HoaDon`, `PhieuNhap`, `PhieuDatHang`, `PhieuDoiHang`, `PhieuTraHang`
- report: `ThongKeBanHang`, `ThongKeTopSanPham`, `ThongKeTonKho`, `HoaDonDisplay`, `ThuocHetHan`, `HoatDong`

## 10. Bang code cu / code moi con dung lan nhau

| STT | Code moi | Dang dung code cu nao | Kieu phu thuoc | Muc do | Ghi chu |
|---|---|---|---|---|---|
| 1 | `ThuocServiceImpl` | cum `*_Dao` legacy cua `Thuoc` | service moi -> legacy DAO | Cao | blocker migration sach cho Group 1 |
| 2 | `KhuyenMaiServiceImpl` | cum `*_Dao` legacy cua `KhuyenMai` | service moi -> legacy DAO | Cao | blocker migration sach cho Group 3 |
| 3 | toan bo legacy DAO package trong `pharmacy-server` | `ConnectDB` | legacy DAO -> SQL Server connector | Cao | chua duoc xoa |
| 4 | remote adapter / repository / service transition | `com.example.pharmacymanagementsystem_qlht.model.*` | shared model tam | Trung binh | can DTO-hoa sau |
| 5 | root `src` legacy | project moi | khong co | Thap | khong goi nguoc vao module moi |

## 11. Bang phan code cu can gom vao project moi

Khong gom theo kieu copy nguyen class. Chi trich logic/query can thiet vao dung tang moi.

| STT | Phan code cu con can | Vi tri hien tai | Noi gom dung o Step 19A | Cach xu ly dung |
|---|---|---|---|---|
| 1 | query/logic `Thuoc` danh muc, tim kiem, chi tiet, ton theo lo, quy doi DVT | `pharmacy-server/.../dao/Thuoc_SanPham_Dao`, `Thuoc_SP_TheoLo_Dao`, `ChiTietDonViTinh_Dao`, `ChiTietHoatChat_Dao`, `HoatChat_Dao`, `LoaiHang_Dao`, `DonViTinh_Dao` | `pharmacy-server/repository/jdbc` + `pharmacy-server/service` | tach thanh repository moi dung `MariaDbConnectionFactory/JdbcConnectionProvider`, khong copy nguyen DAO |
| 2 | query/logic `KhuyenMai` | `pharmacy-server/.../dao/KhuyenMai_Dao`, `LoaiKhuyenMai_Dao`, `ChiTietKhuyenMai_Dao`, `Thuoc_SP_TangKem_Dao` | `pharmacy-server/repository/jdbc` + `pharmacy-server/service` | trich query/mapper can dung, viet lai repository moi cho MariaDB |
| 3 | shared contract dang dung model legacy | `pharmacy-common/.../model/*` | `pharmacy-common/dto`, `request`, `response` | tao DTO moi theo cum master-data/giao-dich/report, giam dan su phu thuoc vao model legacy |
| 4 | query report co parity cao | da o `JdbcReportRepository` | giu o `pharmacy-server/repository/jdbc` | khong can gom them; chi can test runtime doi chieu |

## 12. Legacy code con lai

### Trong project moi

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao`
  - con **29 file DAO legacy**
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/ConnectDB.java`
  - van hardcode SQL Server
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model`
  - con **37 shared model legacy**

### Ngoai project moi

- root monolith:
  - `src/main/java`
  - `src/main/resources`
  van duoc giu lam reference/backup nghiep vu

## 13. Dependency cleanup candidates

Chua xoa ngay trong Step 18. Day la candidate cho Step 19 sau khi Step 19A xong:

| Candidate | Dieu kien de xoa |
|---|---|
| `com.microsoft.sqlserver:mssql-jdbc` trong `pharmacy-server/pom.xml` | sau khi khong con service/repository nao di qua `ConnectDB`/legacy SQL Server path |
| package `pharmacy-parent/pharmacy-server/.../connectDB` | sau khi xoa xong 29 DAO legacy hoac thay het query can dung |
| package `pharmacy-parent/pharmacy-server/.../dao` legacy | sau khi `Thuoc` va `KhuyenMai` khong con goi DAO cu, va khong con group nao dung transition JDBC cu |
| shared legacy model package trong `pharmacy-common` | sau khi co DTO/request/response moi thay the theo cum |
| root `src/` | chi sau khi runtime E2E pass va khong con doi chieu nghiep vu bat buoc |

## 14. SQL Server-specific con sot

Phan con sot chu yeu nam trong legacy JDBC path:

- `ConnectDB` hardcode `jdbc:sqlserver://...`
- cum DAO legacy van con cac pattern SQL Server theo inventory:
  - `TOP`
  - `GETDATE()`
  - `CallableStatement` / procedure
  - `ISNULL`
  - `CONVERT`
  - `COLLATE`
  - logic sinh ma cu

Quan trong:

- phan report/activity da duoc viet lai sang MariaDB path
- phan `Thuoc` va `KhuyenMai` van la cum transition can lam tiep

## 15. Runtime checklist tong hop

### Trang thai Group 0 -> 7

| Group | Trang thai Step 18 | Ghi chu |
|---|---|---|
| Group 0 - Auth/RMI | Chua verify runtime | compile co, server/client chua chay duoc do blocker moi truong |
| Group 1 - DonViTinh + Thuoc + KeHang | **Chua the nghiem thu sach** | `ThuocServiceImpl` van qua legacy DAO + SQL Server `ConnectDB` |
| Group 2 - NhanVien + TaiKhoan + CaiDat | Chua verify runtime | build ok, can test UI/RMI that |
| Group 3 - KhuyenMai + CapNhatKhuyenMai | **Chua the nghiem thu sach** | `KhuyenMaiServiceImpl` van qua legacy DAO + SQL Server `ConnectDB` |
| Group 4 - PhieuNhap + PhieuDatHang + CapNhatSoLuong | Chua verify runtime | compile ok, can test DB that |
| Group 5 - HoaDon + BanHang | Chua verify runtime | compile ok, can test DB + UI |
| Group 6 - DoiTra | Chua verify runtime | compile ok, can test DB + UI |
| Group 7 - ThongKe + HoatDong + BaoCao | Chua verify runtime | compile ok, report/activity path da sach hon |

## 16. Rui ro con lai

1. `Thuoc` va `KhuyenMai` chua sach khoi legacy SQL Server path.
2. `clean install` chua pass vi `.m2` permission/file lock, nen chua co bang chung dong goi artifact on dinh.
3. Khong co `mariadb` CLI tren may hien tai de test DB nhanh.
4. Khong co evidence runtime cho `RmiServerBootstrap` do Maven `exec:java` bi chan boi `.m2`.
5. Chua co runtime GUI proof cho JavaFX client trong moi truong hien tai.
6. `pharmacy-common` van dang chua shared model legacy rat rong, de tang coupling client/server.

## 17. De xuat Step 19

Step 19 nen tach thanh 2 nhanh ro rang:

### Step 19A - Legacy consolidation hardening

Muc tieu:

- xu ly dung 2 cum con “kep chan”:
  - `Thuoc`
  - `KhuyenMai`
- khong cho service moi import legacy DAO nua

Cong viec:

1. Tao repository JDBC moi cho cum `Thuoc` tren `pharmacy-server/repository/jdbc`
   - dung `MariaDbConnectionFactory/JdbcConnectionProvider`
   - trich query can dung tu DAO cu
   - khong copy nguyen DAO cu
2. Tao repository JDBC moi cho cum `KhuyenMai`
3. Cap nhat `ThuocServiceImpl` va `KhuyenMaiServiceImpl`
   - bo import `com.example.pharmacymanagementsystem_qlht.dao.*`
   - bo `new *_Dao()`
4. Khi 2 cum tren xong:
   - xem xet xoa `ConnectDB`
   - xem xet xoa `mssql-jdbc`

### Step 19B - Safe legacy cleanup

Chi bat dau khi:

- Step 19A xong
- runtime server start pass
- runtime client login + mot vai flow chinh pass

Cong viec:

1. tach bot shared legacy model trong `pharmacy-common` thanh DTO/request/response
2. danh dau nhung package root `src` chi con de archive
3. chot package/thu muc co the xoa khoi `pharmacy-parent`
4. lap danh sach archive/xoa root `src` theo dot, khong xoa 1 lan

## 18. Ket luan

Step 18 xac nhan duoc:

- kien truc module moi dang giu duoc huong dependency chinh
- `pharmacy-client` sach DAO/JDBC/JPA
- `pharmacy-server` khong con UI import
- `pharmacy-common` khong co JavaFX/JPA/JDBC implementation
- root `src` khong goi nguoc module moi

Nhung Step 18 **chua du dieu kien cleanup legacy** vi:

- runtime E2E chua duoc nghiem thu that
- `Thuoc` va `KhuyenMai` van con transition dependency toi legacy DAO/ConnectDB/SQL Server path

Do do:

- **khong xoa root `src` trong Step 18**
- **khong xoa DAO/ConnectDB trong Step 18**
- can lam Step 19A truoc, roi moi cleanup an toan o Step 19B
