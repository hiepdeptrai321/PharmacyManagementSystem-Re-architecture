# STEP 15 - GROUP 5 HoaDon + BanHang Migration

## 1. Muc tieu

Migrate cum `HoaDon + BanHang` tu controller/DAO legacy sang flow:

```text
LapHoaDon_Ctrl
    -> HoaDonService (client wrapper)
    -> RmiHoaDonClientService
    -> HoaDonRemote
    -> HoaDonRemoteAdapter
    -> HoaDonServiceImpl
    -> HoaDonRepository / TransactionManager
    -> MariaDB
```

Pham vi batch nay:
- `LapHoaDon`
- `TimKiemHoaDon`
- `ChiTietHoaDon`
- giao dich tao hoa don, tru ton theo lo, cap nhat phieu dat lien quan

Ngoai pham vi:
- doi/tra hang
- thong ke
- in hoa don/PDF hoan chinh
- Excel import cho lap hoa don

## 2. Tai lieu da doc

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
- `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_GUIDE.md`

Khong co file bat buoc nao bi thieu trong luc migrate batch nay.

## 3. Pham vi da lam

- Tao contract RMI cho hoa don trong `pharmacy-common`
- Tao request object cho tao hoa don va tim kiem hoa don
- Tao repository/server service/remote adapter cho hoa don ben `pharmacy-server`
- Implement transaction server-side cho `createInvoice(...)`
- Migrate `LapHoaDon_Ctrl` sang `pharmacy-client`, bo DAO/ConnectDB/JDBC
- Migrate `TimKiemHoaDon_Ctrl` va `ChiTietHoaDon_Ctrl` sang `pharmacy-client`
- Noi diem vao UI moi tu `ClientHome`

## 4. File da tao/sua

### pharmacy-common

- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/HoaDonRemote.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreateHoaDonRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreateHoaDonLineRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/HoaDonSearchRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/HoaDon.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietHoaDon.java`

### pharmacy-server

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/HoaDonRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcHoaDonRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/HoaDonService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/HoaDonServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/HoaDonRemoteAdapter.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`

### pharmacy-client

- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/HoaDonClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiHoaDonClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/HoaDonService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/ApDungKhuyenMai.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/DichVuKhuyenMai.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/TimKiemHoaDon_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/ChiTietHoaDon_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_XuLy/LapHoaDon/LapHoaDon_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoaDon/TKHoaDon_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoaDon/ChiTietHoaDon_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java`

### root src legacy

- Giữ nguyên làm reference
- Khong xoa `src/main/java` hoac `src/main/resources`

## 5. Flow moi

### Tao hoa don

```text
LapHoaDon_Ctrl
    -> build CreateHoaDonRequest
    -> HoaDonService.createInvoice(...)
    -> RmiHoaDonClientService.createInvoice(...)
    -> HoaDonRemote.createInvoice(...)
    -> HoaDonRemoteAdapter
    -> HoaDonServiceImpl.createInvoice(...)
    -> JdbcTransactionManager
    -> JdbcHoaDonRepository
        -> insert HoaDon
        -> insert ChiTietHoaDon
        -> tru ton theo lo
        -> cap nhat PhieuDatHang neu co
        -> ghi audit
```

### Tim kiem hoa don

```text
TimKiemHoaDon_Ctrl
    -> HoaDonService.search(...)
    -> RmiHoaDonClientService.search(...)
    -> HoaDonRemote.search(...)
    -> HoaDonServiceImpl.search(...)
```

### Xem chi tiet hoa don

```text
ChiTietHoaDon_Ctrl
    -> HoaDonService.findById(...)
    -> HoaDonService.findDetailsByMaHD(...)
    -> remote/server repository
```

## 6. Mapping legacy -> new

| Legacy | New | Trang thai | Ghi chu |
|---|---|---|---|
| `HoaDon_Dao.generateNewMaHD` | `HoaDonServiceImpl.generateNewMaHoaDon` + `CodeGenerationService` | Da chuyen | Khong dung `MAX(MaHD)+1` |
| `HoaDon_Dao.insert` | `HoaDonServiceImpl.createInvoice` | Da chuyen | Tao header nam trong transaction |
| `ChiTietHoaDon_Dao.insert` | `JdbcHoaDonRepository.insertDetail` | Da chuyen | Insert detail per allocated lot |
| Tru ton kho khi ban | `HoaDonServiceImpl.createInvoice` + `JdbcHoaDonRepository.updateLotAfterSale` | Da chuyen | Lock lot bang `FOR UPDATE` |
| Cap nhat phieu dat khi tao hoa don tu dat hang | `JdbcHoaDonRepository.updatePreorderStatus` | Da chuyen mot phan | Group 4 logic duoc tai su dung |
| `LapHoaDon_Ctrl` goi DAO/JDBC | `LapHoaDon_Ctrl` goi `HoaDonService` client wrapper | Da chuyen | Client khong goi DB |
| `TimKiemHoaDon_Ctrl` goi `HoaDon_Dao` | `TimKiemHoaDon_Ctrl` goi `HoaDonService.search/findAll` | Da chuyen | Search dang filter o server service |
| `ChiTietHoaDon_Ctrl` goi `ChiTietHoaDon_Dao` | `ChiTietHoaDon_Ctrl` goi `HoaDonService.findDetailsByMaHD` | Da chuyen | In/PDF con placeholder |

## 7. Scan sai tang

### Client

Da scan `pharmacy-client/src/main/java` voi cac pattern:
- `ConnectDB`
- `dao`
- `new .*_Dao`
- `DriverManager`
- `java.sql.Connection`
- `PreparedStatement`
- `ResultSet`
- `CallableStatement`
- `jakarta.persistence`
- `javax.persistence`
- `org.hibernate`
- `com.example.pharmacy.server`

Ket qua:
- Khong co match trong source client sau batch nay

### Common

Da scan `pharmacy-common/src/main/java` voi cac pattern:
- `javafx`
- `jakarta.persistence`
- `javax.persistence`
- `org.hibernate`
- `ConnectDB`
- `dao`
- `com.example.pharmacy.server`
- `com.example.pharmacy.client`

Ket qua:
- Khong co match trong source common sau batch nay

### Server

Da scan `pharmacy-server/src/main/java` voi cac pattern:
- `javafx.fxml`
- `javafx.scene`
- `javafx.stage`
- `com.example.pharmacy.client`
- `com.example.pharmacymanagementsystem_qlht.controller`
- `com.example.pharmacymanagementsystem_qlht.view`

Ket qua:
- Khong co match trong source server thuoc pham vi batch nay

Ghi chu:
- `pharmacy-server/pom.xml` van con `javafx-base` do technical debt cu cua `ThongKe_Dao` va `ThongKeXNT_Dao`. Khong phat sinh tu Group 5.

## 8. Build result

Lenh da chay:

```bash
mvn -q -DskipTests compile
mvn clean install
```

Thu muc chay:

```text
pharmacy-parent/
```

Ket qua:
- `mvn -q -DskipTests compile`: SUCCESS
- `mvn clean install`: SUCCESS

## 9. Cac thay doi UI sau migrate

- `ClientHome` da them nut:
  - `Lap hoa don`
  - `Tim kiem hoa don`
- `LapHoaDon_GUI`, `TKHoaDon_GUI`, `ChiTietHoaDon_GUI` duoc dua sang `pharmacy-client`
- `ChiTietHoaDon_Ctrl` co placeholder cho:
  - `In hoa don`
  - `Xuat PDF`

## 10. Test thu cong can lam

- [ ] Tao hoa don OTC voi 1 thuoc
- [ ] Tao hoa don OTC voi nhieu dong
- [ ] Tao hoa don ETC va nhap ma don thuoc
- [ ] Kiem tra khong cho ban vuot ton
- [ ] Kiem tra tru ton dung theo lo
- [ ] Kiem tra khuyen mai theo san pham
- [ ] Kiem tra giam gia theo hoa don
- [ ] Kiem tra thanh toan tien mat tinh tien thua dung
- [ ] Kiem tra thanh toan chuyen khoan
- [ ] Kiem tra tao hoa don tu `PhieuDatHang`
- [ ] Kiem tra `TimKiemHoaDon`
- [ ] Kiem tra `ChiTietHoaDon`

## 11. Chua lam / rui ro con lai

- `HoaDonServiceImpl` hien la JDBC transition repository, chua JPA hoa sau cho cum `HoaDon/Lo/ChiTietDonViTinh`
- `search(...)` hien dang filter tren danh sach `findAll()` o server service, chua day xuong query MariaDB toi uu
- `btnInHoaDon` va `xuLyXuatPDF()` moi la placeholder
- Chua xu ly day du qua tang kem thanh dong hoa don rieng
- `colSLP` trong `TimKiemHoaDon_Ctrl` dang de `0`, se hoan thien khi migrate Group 6 `DoiTra`
- `ThemThuocBangFileExcel` khong nam trong pham vi batch nay

## 12. Ket luan

Group 5 da duoc migrate sang kien truc multi-module moi theo dung huong:
- client khong con goi DAO/JDBC
- common chi giu contract/request/model chung
- server gom transaction tao hoa don, tru ton va cap nhat phieu dat

Batch nay da build xanh va co diem vao tren `ClientHome`, san sang cho runtime test va cho Group 6 tiep theo.
