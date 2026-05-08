# STEP 17 - Group 7 ThongKe + HoatDong + BaoCao Migration Result

## Muc tieu

Migrate nhom 7 theo huong:

`JavaFX client -> RMI stub -> common remote interface -> server service/repository -> MariaDB`

Pham vi da xu ly:

- `ThongKeBanHang`
- `ThongKeTopSanPham`
- `ThongKeXNT`
- `TKHoatDong`
- `ChiTietHoatDong`
- cac query/thong ke/bao cao tuong ung o server

## Tai lieu da doc

- `docs/migration/STEP_17_GROUP7_THONGKE_HOATDONG_BAOCAO_MIGRATION_GUIDE.md`
- `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md`
- `docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`

## Ghi chu ve guide

Guide Step 17 tham chieu:

- `docs/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION_RESULT.md`
- `docs/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md`

Trong repo hien tai, duong dan thuc te dang la:

- `docs/migration/STEP_15_GROUP5_HOADON_BANHANG_MIGRATION.md`
- `docs/migration/STEP_16_GROUP6_DOITRA_MIGRATION_RESULT.md`

Migration da bam theo noi dung huong dan, chi khac duong dan file note.

## Kien truc sau migrate

```text
ThongKeBanHang_Ctrl / ThongKeTopSanPham_Ctrl / ThongKeXNT_Ctrl / TKHoatDong_Ctrl
    -> ThongKeService / HoatDongService
    -> RmiReportClientService / RmiAuditLogClientService
    -> ReportRemote / AuditLogRemote
    -> ReportServiceImpl / AuditLogQueryServiceImpl
    -> JdbcReportRepository / JdbcAuditLogRepository
    -> MariaDB
```

## File da tao hoac cap nhat

### pharmacy-common

- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/AuditLogSearchRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/ReportRemote.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/AuditLogRemote.java`
- cap nhat `ThongKeBanHang`, `ThongKeTopSanPham`, `ThongKeTonKho`, `ThuocHetHan`, `HoaDonDisplay`, `HoatDong` de Serializable

### pharmacy-server

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/ReportRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/AuditLogRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcReportRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/jdbc/JdbcAuditLogRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/ReportServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuditLogQueryService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuditLogQueryServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/ReportRemoteAdapter.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/AuditLogRemoteAdapter.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`
- `pharmacy-parent/pharmacy-server/pom.xml`

### pharmacy-client

- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/ReportClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiReportClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/AuditLogClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiAuditLogClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/ThongKeService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/HoatDongService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeBanHang_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeTopSanPham_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeXNT_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoatDong/TKHoatDong_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoatDong/ChiTietHoatDong_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_ThongKe/ThongKeBanHang_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_ThongKe/ThongKeTopSanPham_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_ThongKe/ThongKeXNT_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoatDong/TKHoatDong_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_TimKiem/TKHoatDong/ChiTietHoatDong_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java`

## Mapping logic cu -> implementation moi

| Logic cu | Vi tri cu | Implementation moi |
|---|---|---|
| Thong ke ban hang theo preset/khoang ngay | `ThongKe_Dao` + `sp_ThongKeBanHang_*` | `ReportRemote -> ReportServiceImpl -> JdbcReportRepository.findThongKeBanHangByDateRange(...)` |
| Lay hoa don cho thong ke ban hang | `ThongKe_Dao` + `sp_GetHoaDonTheo*` | `JdbcReportRepository.findHoaDonByDateRange(...)` |
| Top ban chay / top doanh thu | `ThongKeTopSP_Dao` | `JdbcReportRepository.findTopBanChayByDateRange(...)` va `findTopDoanhThuByDateRange(...)` |
| Thong ke XNT | `ThongKeXNT_Dao` + `sp_ThongKeXNT` | `JdbcReportRepository.findThongKeXnt(...)` |
| Thuoc het han / sap het han theo thong so | `sp_ThongKeThuocHetHan` | `JdbcReportRepository.findThuocHetHan()` |
| Tim kiem hoat dong | `HoatDong_Dao.selectAll()` + client-side filter | `AuditLogRemote -> AuditLogQueryServiceImpl -> JdbcAuditLogRepository.findAuditLogs(...)` |

## Cleanup da thuc hien

Da xoa khoi `pharmacy-server` cac DAO legacy khong con duoc goi:

- `ThongKe_Dao.java`
- `ThongKeTopSP_Dao.java`
- `ThongKeXNT_Dao.java`
- `HoatDong_Dao.java`

Root `src/main/java/...` van duoc giu nguyen de tham chieu.

Da bo dependency `javafx-base` khoi `pharmacy-server/pom.xml`.

## Kiem tra dependency tang

Da scan sau migrate:

- `pharmacy-client` khong con `ThongKe_Dao`, `ThongKeTopSP_Dao`, `ThongKeXNT_Dao`, `HoatDong_Dao`
- `pharmacy-client` khong co `ConnectDB`, `DriverManager`, `PreparedStatement`, `ResultSet`, `EntityManager`, `Hibernate` trong pham vi Group 7
- `pharmacy-server` khong con `javafx`, `ObservableList`, `FXCollections` sau khi xoa 4 DAO legacy tren
- `pharmacy-common` khong co `javafx`, `jakarta.persistence`, `hibernate`, `ConnectDB`

## Ket qua build

- `mvn -q -DskipTests compile` tai `pharmacy-parent`: **PASS**
- `mvn clean install` tai `pharmacy-parent`: **FAIL do moi truong clean/install**, khong phai loi code
  - mot lan fail o pha `clean` do file lock trong `pharmacy-client/target`
  - mot lan fail o pha `install` do khong ghi duoc file tam vao `.m2`

Tai thoi diem ket thuc Group 7, compile module moi da xanh.

## Luu y parity nghiep vu

- `ThongKeBanHang` moi da giu cong thuc doanh thu theo huong cu:
  - doanh thu ban hang sau giam gia
  - tru gia tri don tra
  - nhan VAT `1.05`
- `ThongKeXNT` moi da duoc viet lai tu proc cu bang SQL MariaDB/CTE, co quy doi don vi qua `ChiTietDonViTinh`
- `TKHoatDong` moi hien doc du lieu tu `audit_log`, khong con doc bang/trigger cu dung `HoatDong` + `CONTEXT_INFO`

## Cac diem can test ky

- preset `Hom nay / Tuan nay / Thang nay / Nam nay / Tuy chon` tren `ThongKeBanHang`
- doi chieu tong doanh thu moi voi proc SQL Server cu
- `Top ban chay` va `Top doanh thu` co dung thu tu va so luong top
- `ThongKeXNT` voi thuoc co nhieu don vi tinh
- `ThuocHetHan` co doc dung nguong `NgayHetHan` tu `ThongSoUngDung`
- `TKHoatDong` co tim dung theo ma, nhan vien, khoang ngay
- `ChiTietHoatDong` co hien dung noi dung audit moi
- cac nut moi tren `ClientHome` mo dung man:
  - `Thong ke ban hang`
  - `Thong ke top san pham`
  - `Thong ke XNT`
  - `Tim kiem hoat dong`

## Trang thai ket luan

Group 7 da duoc migrate xong theo pham vi huong dan:

- client khong con goi DAO cu cho thong ke/hoat dong
- server la noi duy nhat chay query bao cao va audit lookup
- server da duoc don sach JavaFX dependency cho cum report/activity
- root monolith cu van duoc giu de tham chieu

## TODO sau Group 7

- test runtime end-to-end tren MariaDB that
- toi uu `search/filter` cho mot so report neu dataset lon
- quyet dinh co can them export PDF/Excel server-side hay giu client-side
- doi chieu ky ket qua `ThongKeXNT` voi du lieu san xuat truoc khi archive root legacy
