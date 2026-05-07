# STEP 12 - Group 2 Migration: NhanVien + TaiKhoan + CaiDat

## Muc tieu

Hoan tat Nhóm 2 theo huong:

JavaFX Client  
-> client wrapper service  
-> RMI remote interface trong `pharmacy-common`  
-> server service trong `pharmacy-server`  
-> JPA repository/entity  
-> MariaDB

Pham vi batch nay:

- `DMNhanVien`
- cac man tai khoan nhan vien
- khoa/mo tai khoan thong qua `TrangThai`
- hien thi vai tro qua `SessionContext` + `ClientHome`
- `CaiDat`
- `LuongNhanVien`

## Tai lieu da bam theo

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md`

## Ket qua chinh

- Da them `NhanVienRemote` va `CaiDatRemote` vao RMI server bootstrap.
- Da them JPA entity cho `LuongNhanVien` va `ThongSoUngDung`.
- Da them repository JPA cho `NhanVien`, `LuongNhanVien`, `CaiDat`.
- Da them server service `NhanVienService` va `CaiDatService`.
- Da migrate UI `DMNhanVien` va `CaiDat` sang `pharmacy-client`.
- `ClientHome` moi co nut mo truc tiep `Danh muc nhan vien` va `Cai dat`.
- `SessionContext` client tiep tuc la noi giu user hien tai; `ClientHome` hien thi `fullName`, `username`, `role`.

## File/class da tao hoac sua

### pharmacy-common

- `com.example.pharmacy.common.remote.NhanVienRemote`
- `com.example.pharmacy.common.remote.CaiDatRemote`
- `com.example.pharmacymanagementsystem_qlht.model.NhanVien`
  - bo sung copy constructor giu lai `vaiTro`

### pharmacy-server

- `com.example.pharmacy.server.entity.LuongNhanVienEntity`
- `com.example.pharmacy.server.entity.ThongSoUngDungEntity`
- `com.example.pharmacy.server.repository.NhanVienManagementRepository`
- `com.example.pharmacy.server.repository.JpaNhanVienManagementRepository`
- `com.example.pharmacy.server.repository.LuongNhanVienRepository`
- `com.example.pharmacy.server.repository.JpaLuongNhanVienRepository`
- `com.example.pharmacy.server.repository.CaiDatRepository`
- `com.example.pharmacy.server.repository.JpaCaiDatRepository`
- `com.example.pharmacy.server.service.NhanVienService`
- `com.example.pharmacy.server.service.NhanVienServiceImpl`
- `com.example.pharmacy.server.service.CaiDatService`
- `com.example.pharmacy.server.service.CaiDatServiceImpl`
- `com.example.pharmacy.server.bootstrap.rmi.NhanVienRemoteAdapter`
- `com.example.pharmacy.server.bootstrap.rmi.CaiDatRemoteAdapter`
- `META-INF/persistence.xml`
- `ServerComponentFactory`
- `RmiServerBootstrap`

### pharmacy-client

- `com.example.pharmacy.client.service.NhanVienClientService`
- `com.example.pharmacy.client.service.RmiNhanVienClientService`
- `com.example.pharmacy.client.service.CaiDatClientService`
- `com.example.pharmacy.client.service.RmiCaiDatClientService`
- `com.example.pharmacymanagementsystem_qlht.service.NhanVienService`
- `com.example.pharmacymanagementsystem_qlht.service.CaiDatService`
- `com.example.pharmacy.client.rmi.RmiClientProvider`
- `com.example.pharmacymanagementsystem_qlht.controller.CN_DanhMuc.DMNhanVien.*`
- `com.example.pharmacymanagementsystem_qlht.view.CN_DanhMuc.DMNhanVien.*`
- `com.example.pharmacymanagementsystem_qlht.controller.CN_XuLy.CaiDat.caiDat_Ctrl`
- `com.example.pharmacymanagementsystem_qlht.view.CN_XuLy.CaiDat.caiDat_GUI`
- `com.example.pharmacymanagementsystem_qlht.controller.ClientHome_Ctrl`
- `com.example.pharmacymanagementsystem_qlht.view.ClientHome_GUI`

## Mapping chuyen doi

| STT | Logic/Man hinh cu | Vi tri cu | Vi tri moi | Module | Trang thai | Ghi chu |
|---|---|---|---|---|---|---|
| 1 | Danh muc nhan vien | `src/.../DMNhanVien/DanhMucNhanVien_Ctrl` | `pharmacy-client/.../DMNhanVien/DanhMucNhanVien_Ctrl` | pharmacy-client | Da chuyen | Bo DAO, goi `NhanVienService` |
| 2 | Them nhan vien | `src/.../DMNhanVien/ThemNhanVien_Ctrl` | `pharmacy-client/.../DMNhanVien/ThemNhanVien_Ctrl` | pharmacy-client | Da chuyen | Tai khoan phu thuoc dialog rieng |
| 3 | Sua/xoa nhan vien | `src/.../DMNhanVien/SuaXoaNhanVien_Ctrl` | `pharmacy-client/.../DMNhanVien/SuaXoaNhanVien_Ctrl` | pharmacy-client | Da chuyen | Trang thai khoa/mo map vao `TrangThai` |
| 4 | Them tai khoan | `src/.../DMNhanVien/ThemTaiKhoan_Ctrl` | `pharmacy-client/.../DMNhanVien/ThemTaiKhoan_Ctrl` | pharmacy-client | Da chuyen | Kiem tra trung tai khoan qua RMI |
| 5 | Sua tai khoan | `src/.../DMNhanVien/SuaTaiKhoan_Ctrl` | `pharmacy-client/.../DMNhanVien/SuaTaiKhoan_Ctrl` | pharmacy-client | Da chuyen | Kiem tra duplicate co exclude `MaNV` |
| 6 | Bang luong nhan vien | `src/.../DMNhanVien/ThietLapLuongNV_Ctrl` | `pharmacy-client/.../DMNhanVien/ThietLapLuongNV_Ctrl` | pharmacy-client | Da chuyen | Luu qua `NhanVienService.saveLuongNhanVien()` |
| 7 | Cai dat ung dung | `src/.../CN_XuLy/CaiDat/caiDat_Ctrl` | `pharmacy-client/.../CN_XuLy/CaiDat/caiDat_Ctrl` | pharmacy-client | Da chuyen | Khong con `CuaSoChinh_QuanLy_Ctrl` |
| 8 | NhanVien repository | DAO JDBC cu | `JpaNhanVienManagementRepository` | pharmacy-server | Da chuyen | Dung JPA |
| 9 | LuongNhanVien repository | DAO JDBC cu | `JpaLuongNhanVienRepository` | pharmacy-server | Da chuyen | Dung JPA |
| 10 | CaiDat repository | DAO JDBC cu | `JpaCaiDatRepository` | pharmacy-server | Da chuyen | Dung JPA |

## Kiem tra dependency tang

Da scan lai sau migration:

- `pharmacy-client` khong con `ConnectDB`, `DriverManager`, `PreparedStatement`, `ResultSet`, `EntityManager`, `NhanVien_Dao`, `LuongNhanVien_Dao`, `CaiDat_Dao`.
- `pharmacy-common` khong co `javafx`, `jakarta.persistence`, `repository`, `controller`.
- `pharmacy-server` khong import `pharmacy-client` hay JavaFX UI trong batch nay.

## Build da chay

```powershell
mvn -q -DskipTests compile
mvn clean install
```

Ket qua:

- `pharmacy-parent`: BUILD SUCCESS
- `pharmacy-common`: BUILD SUCCESS
- `pharmacy-server`: BUILD SUCCESS
- `pharmacy-client`: BUILD SUCCESS

## Cach mo UI batch nay tren project moi

Sau khi login vao `ClientHome`:

- Nut `Danh muc nhan vien` mo man `DMNhanVien`
- Nut `Cai dat` mo man `CaiDat`

## Luu y va technical debt

- `VaiTro/PhanQuyen` hien dang duoc giu va hien thi qua `SessionContext` + `ClientHome`, chua co man hinh permission matrix rieng.
- `ThemNhanVien` hien default role ve `Nhan vien` neu form tai khoan khong dat role.
- `EmployeeManagementService` cua batch trigger/procedure truoc do van con ton tai trong server, nhung UI `DMNhanVien` batch nay da dung `NhanVienService` moi de phu hop model legacy.
- Chua co test runtime end-to-end bang tay trong note nay; moi xac nhan compile/install xanh va co duong mo UI tren `ClientHome`.

## Goi y test tay

- Login bang tai khoan manager.
- Mo `Danh muc nhan vien` tu `ClientHome`.
- Them nhan vien moi va them tai khoan.
- Sua tai khoan nhan vien.
- Chuyen `TrangThai` nhan vien sang nghi viec va luu.
- Thay doi luong nhan vien.
- Mo `Cai dat`, sua thue va ngay het han, luu, chon restart.

## Huong tiep theo

Sau Nhóm 2, uu tien tiep theo nen la:

1. `KhuyenMai + CapNhatKhuyenMai`
2. hoac di thang vao nghiep vu kho `PhieuNhap + PhieuDatHang + CapNhatSoLuong`

Neu can tiep tuc cung truc master-data, co the lam `KhuyenMai` truoc de giam rui ro transaction.
