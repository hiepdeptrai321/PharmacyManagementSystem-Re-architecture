# STEP 16 - GROUP 6 DoiTra Migration

## 1. Muc tieu

Migrate cum `Doi/Tra hang` tu flow legacy `controller -> DAO -> ConnectDB` sang flow:

```text
LapPhieuDoiHang_Ctrl / LapPhieuTraHang_Ctrl
    -> DoiTraService (client wrapper)
    -> RmiDoiTraClientService
    -> DoiTraRemote
    -> DoiTraRemoteAdapter
    -> DoiTraServiceImpl
    -> DoiTraRepository / TransactionManager
    -> MariaDB
```

Pham vi batch nay:
- `LapPhieuDoiHang`
- `LapPhieuTraHang`
- `TKPhieuDoiHang`
- `ChiTietPhieuDoiHang`
- `TKPhieuTraHang`
- `ChiTietPhieuTraHang`
- phan attach khach hang vao hoa don de phuc vu doi/tra
- phan cong/tru ton va tao phieu doi/tra trong transaction server-side

Ngoai pham vi:
- thong ke doi/tra
- in phieu thuc te / xuat PDF hoan chinh ngoai ban in hien co
- refactor sau hon cho JPA pura thay vi JDBC transition repository
- thay doi quy tac nghiep vu legacy neu chua du can cu

## 2. Tai lieu da doc truoc khi migrate

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
- `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md`
- `docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_GUIDE.md`

Luu y:
- Guide Group 6 nhac den file `STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md`.
- Repo hien tai khong co file do; file thuc te duoc dung lam can cu la `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md`.

## 3. File da tao/sua

### pharmacy-common

- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuDoiRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuDoiItemRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuTraRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/CreatePhieuTraItemRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/DoiTraRemote.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/PhieuDoiHang.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietPhieuDoiHang.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/PhieuTraHang.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietPhieuTraHang.java`

### pharmacy-server

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/DoiTraRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcDoiTraRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/DoiTraService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/DoiTraServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/DoiTraRemoteAdapter.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`

### pharmacy-client

- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/DoiTraClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiDoiTraClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/DoiTraService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDoi/LapPhieuDoiHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuTra/LapPhieuTraHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/TKPhieuDoiHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/ChiTietPhieuDoiHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/TKPhieuTraHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/ChiTietPhieuTraHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_XuLy/LapPhieuDoi/LapPhieuDoi_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_XuLy/LapPhieuTra/LapPhieuTra_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKPhieuDoi/TKPhieuDoiHang_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKPhieuDoi/ChiTietPhieuDoiHang_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKPhieuTra/TKPhieuTraHang_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKPhieuTra/ChiTietPhieuTraHang_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java`

### root src legacy

- Giu nguyen lam reference.
- Khong xoa `src/main/java` va `src/main/resources`.

## 4. Flow moi

### 4.1. Lap phieu tra

```text
LapPhieuTraHang_Ctrl
    -> nhap ma hoa don
    -> DoiTraService.findHoaDonGocForDoiTra(...)
    -> DoiTraService.findHoaDonDetailsForDoiTra(...)
    -> validate UI co ban
    -> DoiTraService.createPhieuTra(request, sessionUser)
    -> RMI
    -> DoiTraServiceImpl.createPhieuTra(...)
    -> TransactionManager
        -> validate hoa don goc va han 7 ngay
        -> validate khach hang cua hoa don
        -> validate so luong con duoc tra
        -> insert PhieuTraHang
        -> insert ChiTietPhieuTraHang
        -> cong ton kho lai vao lo goc
        -> ghi audit
    -> commit / rollback
```

### 4.2. Lap phieu doi

```text
LapPhieuDoiHang_Ctrl
    -> nhap ma hoa don
    -> DoiTraService.findHoaDonGocForDoiTra(...)
    -> DoiTraService.findHoaDonDetailsForDoiTra(...)
    -> validate UI co ban
    -> DoiTraService.createPhieuDoi(request, sessionUser)
    -> RMI
    -> DoiTraServiceImpl.createPhieuDoi(...)
    -> TransactionManager
        -> validate hoa don goc va han 7 ngay
        -> validate khach hang cua hoa don
        -> validate so luong con duoc doi
        -> allocate lo thay the theo FEFO
        -> insert PhieuDoiHang
        -> insert ChiTietPhieuDoiHang
        -> tru ton kho cac lo thay the
        -> ghi audit
    -> commit / rollback
```

### 4.3. Tim kiem / chi tiet

```text
TKPhieuDoiHang_Ctrl / TKPhieuTraHang_Ctrl
    -> DoiTraService.findAllPhieuDoi() / findAllPhieuTra()
    -> loc tren client
    -> mo man hinh chi tiet
    -> DoiTraService.findChiTietPhieuDoiByMaPD(...) / findChiTietPhieuTraByMaPT(...)
```

## 5. Mapping legacy -> implementation moi

| STT | Logic legacy | Vi tri cu | Implementation moi | Trang thai | Ghi chu |
|---|---|---|---|---|---|
| 1 | Tim hoa don goc de doi/tra | `LapPhieuDoiHang_Ctrl`, `LapPhieuTraHang_Ctrl`, DAO legacy | `DoiTraService.findHoaDonGocForDoiTra(...)` | Da implement | Client khong con goi DAO |
| 2 | Lay chi tiet hoa don goc | controller + DAO legacy | `DoiTraService.findHoaDonDetailsForDoiTra(...)` | Da implement | Dung cho lap phieu va tinh tong |
| 3 | Kiem tra so luong da doi | DAO legacy | `DoiTraService.getSoLuongDaDoi(...)` | Da implement | Validate tai server |
| 4 | Kiem tra so luong da tra | DAO legacy | `DoiTraService.getSoLuongDaTra(...)` | Da implement | Validate tai server |
| 5 | Tao phieu doi | controller + DAO legacy | `DoiTraServiceImpl.createPhieuDoi(...)` | Da implement | Transaction server-side |
| 6 | Tao phieu tra | controller + DAO legacy | `DoiTraServiceImpl.createPhieuTra(...)` | Da implement | Transaction server-side |
| 7 | Gan khach hang vao hoa don de phuc vu doi/tra | controller + DAO legacy | `DoiTraService.attachKhachHangToHoaDon(...)` | Da implement | Dung sau khi them khach hang moi |
| 8 | Tim kiem phieu doi | DAO legacy | `findAllPhieuDoi()` + loc client-side | Da implement | Chua toi uu query search rieng |
| 9 | Tim kiem phieu tra | DAO legacy | `findAllPhieuTra()` + loc client-side | Da implement | Chua toi uu query search rieng |
| 10 | Xem chi tiet phieu doi/tra | DAO legacy | `findChiTietPhieuDoiByMaPD(...)`, `findChiTietPhieuTraByMaPT(...)` | Da implement | Van giu xuat PDF client-side |

## 6. Nhung gi da bo duoc o client

Trong pham vi Group 6, `pharmacy-client` khong con:
- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `CallableStatement`
- `new *_Dao()`
- import package `dao`
- import package `pharmacy-server`

## 7. Ket qua scan sau migrate

### 7.1. Client Group 6

Da scan cac controller:
- `LapPhieuDoiHang_Ctrl.java`
- `LapPhieuTraHang_Ctrl.java`
- `TKPhieuDoiHang_Ctrl.java`
- `ChiTietPhieuDoiHang_Ctrl.java`
- `TKPhieuTraHang_Ctrl.java`
- `ChiTietPhieuTraHang_Ctrl.java`

Pattern scan:

```text
import .*dao|new .*_Dao|ConnectDB|java.sql|Connection|PreparedStatement|ResultSet|CallableStatement
```

Ket qua:
- Khong con match nao trong Group 6 client controller.

### 7.2. Server Group 6

Da scan cac file:
- `DoiTraService.java`
- `DoiTraServiceImpl.java`
- `DoiTraRepository.java`
- `JdbcDoiTraRepository.java`
- `DoiTraRemoteAdapter.java`

Pattern scan:

```text
javafx.scene|javafx.fxml|javafx.stage|javafx.application|TableView|Button|Label|FXMLLoader|Stage|Scene
```

Ket qua:
- Khong con JavaFX import/UI leakage trong Group 6 server files.

### 7.3. Common Group 6

Da scan:
- `DoiTraRemote.java`
- `CreatePhieuDoiRequest.java`
- `CreatePhieuDoiItemRequest.java`
- `CreatePhieuTraRequest.java`
- `CreatePhieuTraItemRequest.java`

Pattern scan:

```text
javafx|jakarta.persistence|javax.persistence|org.hibernate|ConnectDB|DriverManager|EntityManager|RepositoryImpl|ServiceImpl
```

Ket qua:
- Khong co implementation leakage vao `pharmacy-common`.

### 7.4. Legacy debt con lai

Các DAO legacy lien quan doi/tra trong:
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao/...`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/dao/...`

van con:
- `SELECT TOP 1`
- `ISNULL`
- `ConnectDB`
- `Connection`

Day la technical debt cu, chua xoa trong batch nay. Luong Group 6 moi o client da khong con goi cac DAO do.

## 8. Build result

Da chay:

```powershell
mvn -q -DskipTests compile
mvn clean install
```

Tai:

```text
pharmacy-parent
```

Ket qua:
- `BUILD SUCCESS`

## 9. Luu y nghiep vu va rui ro con lai

### 9.1. Quy tac hoa don giam gia khi tra hang

`DoiTraServiceImpl.createPhieuTra(...)` hien dang block tra hang neu bat ky dong chi tiet hoa don nao co `GiamGia > 0`.

Luu y:
- Group 5 `HoaDon` chua khoi phuc day du mot truong "giam gia toan hoa don" tach rieng de server co the validate lai 1:1 quy tac cu.
- Hien tai quy tac dang duoc bieu dien theo muc "dong chi tiet hoa don".
- Can test tay ky voi cac hoa don co khuyen mai / giam gia.

### 9.2. Hanh vi doi hang va ton kho lo goc

`createPhieuDoi(...)` hien co chu y bat theo hanh vi legacy:
- allocate cac lo thay the theo FEFO
- tru ton kho cua lo thay the
- khong cong lai ton kho lo goc da ban

Day la business parity hien tai voi code cu, khong phai bug duoc sua ngam trong batch nay.

### 9.3. Tim kiem chua toi uu query

`TKPhieuDoiHang_Ctrl` va `TKPhieuTraHang_Ctrl` hien:
- fetch all tu server
- filter tren client

Tam chap nhan cho batch migrate. Co the toi uu thanh server-side search sau.

### 9.4. PDF export van o client

Man hinh chi tiet van giu xuat PDF o client-side de tranh tac dong rong. Chua doi sang service xuat tai server.

## 10. Manual test checklist

- [ ] Mo client moi, vao `Lap phieu doi`
- [ ] Tim hoa don ton tai thi hien du thong tin hoa don goc
- [ ] Tim hoa don khong ton tai thi bao loi ro rang
- [ ] Hoa don qua 7 ngay bi chan doi/tra
- [ ] Hoa don chua co khach hang thi flow them khach hang moi van chay
- [ ] Tao `PhieuTra` thanh cong thi man hinh chi tiet phieu tra mo duoc
- [ ] Tao `PhieuDoi` thanh cong thi man hinh chi tiet phieu doi mo duoc
- [ ] Tra hang so luong lon hon so da ban - da tra bi chan
- [ ] Doi hang so luong lon hon so da ban - da doi bi chan
- [ ] Doi hang khi ton thay the khong du bi chan tu server
- [ ] Search `TKPhieuTraHang` thay du lieu moi tao
- [ ] Search `TKPhieuDoiHang` thay du lieu moi tao
- [ ] Tat server roi thao tac doi/tra thi client bao loi ket noi de hieu
- [ ] Kiem tra Group 5 `HoaDon` van chay khong bi anh huong

## 11. Phan chua lam trong batch nay

- Khong JPA-hoa sau cum `DoiTra`; repository hien la JDBC transition repository tren server
- Khong toi uu search doi/tra thanh query rieng
- Khong xoa DAO legacy cu
- Khong thay doi nghiep vu legacy ve xu ly ton kho lo goc khi doi hang
- Khong dua thong ke doi/tra vao batch nay

## 12. Huong di tiep theo

Sau Group 6, batch hop ly tiep theo la:
- Group 7: `ThongKe + HoatDong + BaoCao`

Muc tieu luc do:
- don technical debt server con `ObservableList`
- dua report/query thong ke ve dung tang server
- hoan thien audit/activity display theo kien truc moi
