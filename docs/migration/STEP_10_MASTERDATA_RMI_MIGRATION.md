# STEP 10 - Master Data RMI Migration

## Muc tieu

Tiep tuc migrate theo uu tien sau Step 9:

- `KhachHang`
- `NhaCungCap`
- `DonViTinh`
- `NhomDuocLy`

Huong migrate duoc giu dong nhat voi POC dang nhap:

`JavaFX controller -> client service -> RMI stub -> remote interface (common) -> server service -> JPA repository -> MariaDB`

## Tai lieu da doc truoc khi migrate

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/DAO_MIGRATION_MAPPING.md`
- `docs/migration/QUERY_INVENTORY.md`
- `docs/migration/MIGRATION_PRIORITY.md`
- `docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`

## Pham vi da migrate

### 1. pharmacy-common

Da bo sung remote contract cho 4 nhom chuc nang:

- `AuthRemote` da co tu Step 9
- `KhachHangRemote`
- `NhaCungCapRemote`
- `DonViTinhRemote`
- `NhomDuocLyRemote`

Da cap nhat shared model de co the truyen qua RMI:

- `KhachHang`
- `NhaCungCap`
- `DonViTinh`
- `NhomDuocLy`

Da mo rong `BusinessCodeType` cho:

- `DON_VI_TINH`
- `NHOM_DUOC_LY`

### 2. pharmacy-server

Da them JPA entity:

- `KhachHangEntity`
- `NhaCungCapEntity`
- `DonViTinhEntity`
- `NhomDuocLyEntity`

Da them JPA repository:

- `JpaKhachHangRepository`
- `JpaNhaCungCapRepository`
- `JpaDonViTinhRepository`
- `JpaNhomDuocLyRepository`

Da them service nghiep vu:

- `KhachHangServiceImpl`
- `NhaCungCapServiceImpl`
- `DonViTinhServiceImpl`
- `NhomDuocLyServiceImpl`

Da them RMI adapter va bind trong server bootstrap:

- `KhachHangRemoteAdapter`
- `NhaCungCapRemoteAdapter`
- `DonViTinhRemoteAdapter`
- `NhomDuocLyRemoteAdapter`

### 3. pharmacy-client

Da them RMI client services:

- `RmiKhachHangClientService`
- `RmiNhaCungCapClientService`
- `RmiDonViTinhClientService`
- `RmiNhomDuocLyClientService`

Da them legacy-compatible wrapper service de controller JavaFX cu co the goi tiep ma khong dung DAO:

- `com.example.pharmacymanagementsystem_qlht.service.KhachHangService`
- `...NhaCungCapService`
- `...DonViTinhService`
- `...NhomDuocLyService`

Da migrate controller/view thuc te cho:

- `KhachHang`
  - `DanhMucKhachHang_Ctrl`
  - `ThemKhachHang_Ctrl`
  - `ChiTietKhachHang_Ctrl`
  - `TimKiemKhachHang_Ctrl`
  - `TimKiemKhachHangTrongHD_Ctrl`
- `NhaCungCap`
  - `DanhMucNhaCungCap_Ctrl`
  - `ThemNhaCungCap_Ctrl`
  - `SuaXoaNhaCungCap_Ctrl`
  - `TimKiemNCC_Ctrl`
  - `ChiTietNhaCungCap_Ctrl`
- `NhomDuocLy`
  - `DanhMucNhomDuocLy_Ctrl`
  - `ThemNhomDuocLy_Ctrl`
  - `SuaXoaNhomDuocLy`

## Bang mapping chinh

| STT | Nhom chuc nang | Common remote | Server service/repository | Client service | Client UI | Trang thai | Ghi chu |
|---|---|---|---|---|---|---|---|
| 1 | KhachHang | `KhachHangRemote` | `KhachHangServiceImpl` + `JpaKhachHangRepository` | `RmiKhachHangClientService` + wrapper `KhachHangService` | Da migrate danh muc + tim kiem | Da migrate | Delete hien tai la soft delete de giu hanh vi cu |
| 2 | NhaCungCap | `NhaCungCapRemote` | `NhaCungCapServiceImpl` + `JpaNhaCungCapRepository` | `RmiNhaCungCapClientService` + wrapper `NhaCungCapService` | Da migrate danh muc + tim kiem | Da migrate | `ThemNhaCungCap_Ctrl` da bo phu thuoc truc tiep vao `LapPhieuNhapHang_Ctrl`, thay bang callback `Runnable` |
| 3 | DonViTinh | `DonViTinhRemote` | `DonViTinhServiceImpl` + `JpaDonViTinhRepository` | `RmiDonViTinhClientService` + wrapper `DonViTinhService` | Chua migrate UI | Migrate mot phan | UI cu dang dinh chat vao `Thuoc` va `ChiTietDonViTinh`, can lam cung mot dot rieng |
| 4 | NhomDuocLy | `NhomDuocLyRemote` | `NhomDuocLyServiceImpl` + `JpaNhomDuocLyRepository` | `RmiNhomDuocLyClientService` + wrapper `NhomDuocLyService` | Da migrate danh muc | Da migrate | Controller client da bo DAO cu, delete co kiem tra thuoc dang gan voi nhom |

## Thay doi ky thuat dang chu y

- `KhachHang`, `NhaCungCap`, `DonViTinh`, `NhomDuocLy` trong `pharmacy-common` da `implements Serializable`.
- `NhomDuocLy` client UI khong con import DAO.
- `ThemNhaCungCap_Ctrl` khong con import `LapPhieuNhapHang_Ctrl`.
- `RmiServerBootstrap` da bind them 4 remote service master-data.
- `persistence.xml` da dang ky them 4 entity moi.
- `SQL/alter_add_code_sequence_mariadb.sql` da bo sung sequence type:
  - `DON_VI_TINH`
  - `NHOM_DUOC_LY`

## Kiem tra build

Da chay:

- `mvn -f pharmacy-parent/pom.xml -q -DskipTests compile`

Ket qua:

- `BUILD SUCCESS`

Da thu:

- `mvn -f pharmacy-parent/pom.xml clean install`

Ket qua:

- Khong loi compile
- Fail o buoc install vao local Maven repository `.m2`
- Day la loi moi truong ghi file local, khong phai loi code/module

## Kiem tra dependency sai tang

### pharmacy-client

Scan nhanh khong con phat hien:

- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `EntityManager`
- `jakarta.persistence`
- `org.hibernate`
- import package `com.example.pharmacy.server`

### pharmacy-common

Scan nhanh khong con phat hien:

- `javafx`
- `jakarta.persistence`
- `org.hibernate`
- `ConnectDB`
- `dao`
- `controller`

### pharmacy-server

Con ton tai 2 DAO thong ke legacy van import JavaFX `ObservableList`:

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ThongKe_Dao.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/ThongKeXNT_Dao.java`

Day la technical debt cu, chua thuoc nhom migrate master-data dot nay.

## Nhung phan chua lam trong dot nay

- Chua migrate UI `DonViTinh` vao `pharmacy-client`
- Chua migrate `Thuoc` va cac man phu thuoc `ChiTietDonViTinh`
- Chua thay the end-to-end cho `LapPhieuNhapHang`
- Chua doi toan bo master-data sang DTO rieng; hien tai van dung shared model de giam rui ro

## TODO dot tiep theo

1. Migrate `DonViTinh` cung `Thuoc` va `ChiTietDonViTinh` trong cung mot dot de tranh vo luong gia/chuyen doi don vi.
2. Tiep tuc `NhanVien` va `TaiKhoan` theo migration priority sau khi master-data co ban on dinh.
3. Goi tiep `KhachHang` tu cac man nghiep vu nhu `LapHoaDon` bang client service thay vi logic cu.
4. Refactor 2 DAO thong ke legacy trong `pharmacy-server` de loai JavaFX khoi server.
