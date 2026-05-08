# STEP 20 - Final App Reconnection + E2E Verification + Legacy Cleanup Result

## 1. Muc tieu Step 20

- Noi lai entry point JavaFX cua project moi.
- Noi login -> main shell -> cac man hinh da migrate theo nhom 0 -> 7.
- Chay build va runtime smoke-test cho duong di moi.
- Scan kien truc de dam bao project moi khong quay nguoc ve DAO/ConnectDB cu.
- Cleanup phan legacy nam trong `pharmacy-parent` neu da du dieu kien.
- Khong xoa `root src` legacy trong Step 20.

## 2. Tai lieu da doc

- [STEP_20_FINAL_APP_RECONNECTION_E2E_AND_LEGACY_CLEANUP_GUIDE.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_20_FINAL_APP_RECONNECTION_E2E_AND_LEGACY_CLEANUP_GUIDE.md)
- [STEP_19A_LEGACY_CONSOLIDATION_HARDENING_RESULT.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_19A_LEGACY_CONSOLIDATION_HARDENING_RESULT.md)
- [STEP_18_FINAL_E2E_VERIFICATION_RESULT.md](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/docs/migration/STEP_18_FINAL_E2E_VERIFICATION_RESULT.md)
- cac docs migration nhom 1 -> 7 da tao truoc do

## 3. File da tao/sua trong Step 20

### 3.1. Entry point va shell

- Tao [PharmacyClientApplication.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/PharmacyClientApplication.java)
- Sua [pharmacy-client/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/pom.xml) de `javafx:run` tro den `com.example.pharmacy.client.PharmacyClientApplication`
- Sua [ClientHome_Ctrl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java)
- Sua [ClientHome_GUI.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java)

### 3.2. RMI runtime hardening

- Sua [RmiClientProvider.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java)
- Sua [RmiServerBootstrap.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java)

### 3.3. Cleanup server legacy

- Sua [pharmacy-server/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/pom.xml) de go `mssql-jdbc`
- Archive 3 thu muc legacy khoi source build:
  - [archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao)
  - [archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB)
  - [archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/service](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/archive/legacy-in-pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/service)

## 4. Build result

### 4.1. Compile

Lenh:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

Ket qua: `PASS`

### 4.2. Clean install

Lenh:

```powershell
mvn -f pharmacy-parent/pom.xml clean install
```

Ket qua: `PASS`

Ghi chu:

- Sau cleanup, `pharmacy-server` build thanh cong ma khong con `mssql-jdbc`.
- `pharmacy-common`, `pharmacy-server`, `pharmacy-client` deu install thanh cong vao `.m2`.

## 5. Runtime result

## 5.1. Gate 2 - RMI server start

Da test bang:

```powershell
cmd /c mvn -f pharmacy-parent/pharmacy-server/pom.xml -Dexec.mainClass=com.example.pharmacy.server.bootstrap.RmiServerBootstrap exec:java
```

Ket qua: `PASS`

Log xac nhan:

- `RMI server started on port 1099 with binding AuthRemoteService`
- `RMI hostname: 127.0.0.1`
- `Persistence unit: pharmacyPU`
- `RMI server is ready. Press Ctrl+C to stop.`

## 5.2. Gate 3 - JavaFX client launch

Da smoke-launch bang:

```powershell
mvn -q javafx:run
```

Ket qua: `PASS o muc smoke-run`

Bang chung:

- process JavaFX ton tai hon 10 giay
- khong co stack trace crash trong `tools/step20-javafx-client.err.log`
- login UI khong crash ngay khi boot

Ghi chu:

- Khong co co che UI automation desktop trong phien nay, nen gate nay duoc xac nhan o muc "launch thanh cong, khong crash som".

## 5.3. Gate 4 - Login qua RMI

Da test bang:

```powershell
mvn -f pharmacy-parent/pharmacy-client/pom.xml exec:java "-Dexec.mainClass=com.example.pharmacy.client.rmi.AuthClientProbe"
```

### Case 1 - Dang nhap dung

Ket qua: `PASS`

Output:

```text
Success: true
Message: Dang nhap thanh cong.
User: Pham Quan Ly
```

### Case 2 - Sai mat khau

Ket qua: `PASS`

Output:

```text
Success: false
Message: Tai khoan hoac mat khau khong dung.
```

### Case 3 - Tai khoan khoa

Ket qua: `PASS`

Output:

```text
Success: false
Message: Tai khoan da bi khoa hoac ngung hoat dong.
```

### Case 4 - Tat server roi dang nhap

Ket qua: `PASS`

Output:

```text
Success: false
Message: Khong the ket noi den server. Vui long kiem tra RMI server va MariaDB.
```

## 5.4. Gate 5 - Main shell

Ket qua: `PASS o muc wiring`

Da noi shell moi qua:

- [DangNhap_Ctrl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/DangNhap_Ctrl.java)
- [ClientHome_Ctrl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java)
- [ClientHome_GUI.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java)

Shell moi hien:

- user
- role
- status
- menu trai theo nhom chuc nang
- logout quay ve login

## 5.5. Gate 6 - Menu mapping

### Bang mapping menu

| Menu | View/GUI | Controller | Client service | Trang thai |
|---|---|---|---|---|
| Thuoc | `DanhMucThuoc_GUI` | `DanhMucThuoc_Ctrl` | `ThuocService` | Da noi |
| Don vi tinh | hub shell + `ThemDVT_GUI` | `ClientHome_Ctrl`, `ThemDVT_Ctrl` | `DonViTinhService` | Da noi theo huong hub an toan |
| Ke hang | `DanhMucKeHang_GUI` | `DanhMucKeHang_Ctrl` | `KeHangService` | Da noi |
| Khach hang | `DanhMucKhachHang_GUI` | `DanhMucKhachHang_Ctrl` | `KhachHangService` | Da noi |
| Nha cung cap | `DanhMucNhaCungCap_GUI` | `DanhMucNhaCungCap_Ctrl` | `NhaCungCapService` | Da noi |
| Nhan vien | `DanhMucNhanVien_GUI` | `DanhMucNhanVien_Ctrl` | `NhanVienService` | Da noi |
| Nhom duoc ly | `DanhMucNhomDuocLy_GUI` | `DanhMucNhomDuocLy_Ctrl` | `NhomDuocLyService` | Da noi |
| Danh muc khuyen mai | `DanhMucKhuyenMai_GUI` | `DanhMucKhuyenMai_Ctrl` | `KhuyenMaiService` | Da noi |
| Cap nhat khuyen mai | `CapNhatKhuyenMai_GUI` | `CapNhatKhuyenMai_Ctrl` | `KhuyenMaiService` | Da noi |
| Cap nhat so luong | `CapNhatSoLuongThuoc_GUI` | `CapNhatSoLuongThuoc_Ctrl` | `TonKhoService`, `ThuocService` | Da noi |
| Lap hoa don | `LapHoaDon_GUI` | `LapHoaDon_Ctrl` | `HoaDonService` | Da noi |
| Lap phieu nhap | `LapPhieuNhapHang_GUI` | `LapPhieuNhapHang_Ctrl` | `PhieuNhapService` | Da noi |
| Lap phieu dat | `LapPhieuDat_GUI` | `LapPhieuDatHang_Ctrl` | `PhieuDatHangService` | Da noi |
| Lap phieu doi | `LapPhieuDoi_GUI` | `LapPhieuDoiHang_Ctrl` | `DoiTraService` | Da noi |
| Lap phieu tra | `LapPhieuTra_GUI` | `LapPhieuTraHang_Ctrl` | `DoiTraService` | Da noi |
| Tim thuoc | `TimKiemThuoc_GUI` | `TimKiemThuoc_Ctrl` | `ThuocService` | Da noi |
| Tim thuoc trong kho | `TimKiemThuocTrongKho_GUI` | `TimKiemThuocTrongKho_Ctrl` | `ThuocService` | Da noi |
| Tim khach hang | `TimKiemKhachHang_GUI` | `TimKiemKhachHang_Ctrl` | `KhachHangService` | Da noi |
| Tim nha cung cap | `TimKiemNCC_GUI` | `TimKiemNCC_Ctrl` | `NhaCungCapService` | Da noi |
| Tim hoa don | `TimKiemHoaDon_GUI` | `TimKiemHoaDon_Ctrl` | `HoaDonService` | Da noi |
| Tim phieu nhap | `TKPhieuNhapHang_GUI` | `TimKiemPhieuNhap_Ctrl` | `PhieuNhapService` | Da noi |
| Tim phieu dat | `TKPhieuDatHang_GUI` | `TKPhieuDatHang_Ctrl` | `PhieuDatHangService` | Da noi |
| Tim phieu doi | `TKPhieuDoiHang_GUI` | `TKPhieuDoiHang_Ctrl` | `DoiTraService` | Da noi |
| Tim phieu tra | `TKPhieuTraHang_GUI` | `TKPhieuTraHang_Ctrl` | `DoiTraService` | Da noi |
| Thong ke ban hang | `ThongKeBanHang_GUI` | `ThongKeBanHang_Ctrl` | `ThongKeService` | Da noi |
| Thong ke top san pham | `ThongKeTopSanPham_GUI` | `ThongKeTopSanPham_Ctrl` | `ThongKeService` | Da noi |
| Thong ke XNT | `ThongKeXNT_GUI` | `ThongKeXNT_Ctrl` | `ThongKeService` | Da noi |
| Thuoc het han | placeholder | `ClientHome_Ctrl` | `ThongKeService` | Placeholder an toan |
| Thuoc sap het han | placeholder | `ClientHome_Ctrl` | `ThongKeService` | Placeholder an toan |
| Tim kiem hoat dong | `TKHoatDong_GUI` | `TKHoatDong_Ctrl` | `HoatDongService` | Da noi |
| Cai dat | `caiDat_GUI` | `caiDat_Ctrl` | `CaiDatService` | Da noi |

## 5.6. Gate 7 - Smoke test group 0 -> 7

### Group 0 - Auth

- Login app boot: pass
- Login dung: pass
- Login sai: pass
- Login tai khoan khoa: pass
- Tat server -> thong bao loi ket noi: pass

### Group 1 - Thuoc / DonViTinh / KeHang

- Da noi menu den cac man: pass
- Da compile va boot duong di moi: pass
- Chua co UI automation de bam tung thao tac trong phien nay

### Group 2 - NhanVien / TaiKhoan / CaiDat

- Da noi menu den `DanhMucNhanVien` va `CaiDat`: pass
- Chua co UI automation de test CRUD chi tiet trong phien nay

### Group 3 - KhuyenMai

- Da noi `Danh muc khuyen mai` va `Cap nhat khuyen mai`: pass
- Chua co runtime thao tac tay tao/sua/xoa trong phien nay

### Group 4 - PhieuNhap / PhieuDatHang / CapNhatSoLuong

- Da noi menu va compile pass
- Chua co runtime thao tac tay tao phieu trong phien nay

### Group 5 - HoaDon / BanHang

- Da noi menu lap hoa don + tim hoa don
- Chua co runtime thao tac tay lap hoa don trong phien nay

### Group 6 - DoiTra

- Da noi menu lap/tim phieu doi tra
- Chua co runtime thao tac tay trong phien nay

### Group 7 - ThongKe / HoatDong / BaoCao

- Da noi menu thong ke va audit
- Chua co runtime thao tac tay trong phien nay

### Ket luan smoke test

- `Gate 1 -> 6`: dat
- `Gate 7`: dat o muc wiring + build + launch + auth path
- Can test tay desktop day du de nghiem thu CRUD/nghiep vu chi tiet

## 6. Scan kien truc sau cleanup

## 6.1. pharmacy-client

Scan theo cac pattern:

- `ConnectDB`
- `new *_Dao`
- `import *.dao`
- `DriverManager`
- `PreparedStatement`
- `ResultSet`
- `CallableStatement`
- `EntityManager`
- `Hibernate`
- `jakarta.persistence`
- `import server`

Ket qua: `Khong con match`

Ghi chu:

- Client van con `java.sql.Date` / `java.sql.Timestamp` o mot so wrapper/controller do shared model DTO tam thoi van dung kieu SQL legacy.
- Day la van de cleanup kieu du lieu, khong con la duong DB access truc tiep.

## 6.2. pharmacy-common

Scan theo:

- `javafx`
- `jakarta.persistence`
- `javax.persistence`
- `org.hibernate`
- `ConnectDB`
- `DriverManager`
- `EntityManager`
- `RepositoryImpl`
- `ServiceImpl`
- `import client/server`

Ket qua: `Khong con match`

## 6.3. pharmacy-server

Scan theo:

- `import javafx.fxml`
- `import javafx.scene`
- `import javafx.stage`
- `import javafx.application`
- `import com.example.pharmacy.client`

Ket qua: `Khong con match`

## 6.4. Runtime code moi con quay ve DAO/ConnectDB cu hay khong

Scan tren `pharmacy-parent` theo:

- `com.example.pharmacymanagementsystem_qlht.dao`
- `new *_Dao`
- `ConnectDB`

Ket qua sau cleanup source build: `Khong con match trong source build cua project moi`

## 7. Legacy cleanup result

## 7.1. Da cleanup/archived

Da dua ra khoi source build moi:

- `pharmacy-parent/pharmacy-server/.../com/example/pharmacymanagementsystem_qlht/dao`
- `pharmacy-parent/pharmacy-server/.../com/example/pharmacymanagementsystem_qlht/connectDB`
- `pharmacy-parent/pharmacy-server/.../com/example/pharmacymanagementsystem_qlht/service`

Vi tri archive:

- [archive/legacy-in-pharmacy-parent/](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/archive/legacy-in-pharmacy-parent)

## 7.2. Da cleanup dependency

- Da go `mssql-jdbc` khoi [pharmacy-server/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/pom.xml)

## 7.3. Chua cleanup

- `root src/` legacy: chua xoa, chua archive
- `root pom.xml` legacy: chua dong vao
- SQL Server scripts cu: chua xoa

## 8. Root legacy con lai

### Van giu lai

- [src/main/java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/java)
- [src/main/resources](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/resources)

### Ly do chua xoa

- Van can lam vung tham chieu doi chieu nghiep vu va UI cu
- Chua co bang chung desktop E2E thao tac tay cho tat ca group 1 -> 7
- Van con shared model DTO tam thoi trong `pharmacy-common` mang dau vet legacy naming/type

## 9. Rui ro con lai

- `Thuoc het han` va `Thuoc sap het han` hien dang la placeholder an toan trong shell, chua co man hinh UI rieng nhu project cu
- Chua co desktop automation de bam tung menu va thao tac CRUD nghiep vu trong phien nay
- Client/common van con dung mot so model legacy tam thoi va kieu `java.sql.Date/Timestamp`
- Audit login van co the phat sinh warning neu schema `code_sequence` / `audit_log` thieu du lieu cau hinh, du khong chan login thanh cong

## 10. De xuat Step 21

1. Chay test tay desktop day du theo menu shell moi:
   - login
   - logout
   - mo tung man group 1 -> 7
   - thao tac 1 use-case chinh moi group
2. Tach dan shared model legacy trong `pharmacy-common` thanh DTO/time type sach hon:
   - uu tien bo `java.sql.Date/Timestamp` khoi `client/common`
3. Neu test tay pass:
   - archive `root src` legacy
   - cap nhat `README`/huong dan chay chinh thuc chi con `pharmacy-parent`
4. Sau khi archive `root src`, moi xem xet Step 22 hoac cleanup cuoi cung

## 11. Ket luan Step 20

Trang thai chung: `PASS co dieu kien`

Da dat:

- project moi build duoc
- login RMI + JPA + MariaDB chay duoc
- main shell moi da duoc noi lai
- menu da duoc noi toi cac man hinh da migrate
- project moi khong con quay ve DAO/ConnectDB cu trong source build
- legacy server code trong `pharmacy-parent` da duoc archive
- `mssql-jdbc` da duoc go khoi project moi

Chua du dieu kien de lam ngay:

- xoa `root src` legacy
- ket luan "desktop E2E full app 100%" khi chua co vong test tay tren tung man hinh

Khuyen nghi:

- chuyen sang Step 21 voi muc tieu `manual desktop acceptance + root legacy archive plan`
