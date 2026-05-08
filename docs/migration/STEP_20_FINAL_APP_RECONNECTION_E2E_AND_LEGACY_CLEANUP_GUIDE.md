# STEP 20 - Final App Reconnection + E2E Test + Legacy Cleanup Guide

## 1. Câu trả lời ngắn

Có thể giao cho Codex bằng **một prompt lớn kèm markdown hướng dẫn**, nhưng phải làm theo dạng **gate kiểm soát**. Không được để Codex vừa xóa code cũ vừa nối UI trong một lần không có điều kiện dừng.

Thứ tự đúng:

```text
1. Làm project mới chạy được
2. Tạo/nối entry point JavaFX
3. Nối login -> main shell -> các màn hình
4. Test smoke từng nhóm chức năng
5. Scan sạch tầng
6. Dọn legacy trong pharmacy-parent
7. Gỡ SQL Server dependency
8. Cuối cùng mới archive/xóa root src legacy
```

Nếu fail ở bước nào thì dừng ở bước đó, ghi result, không xóa tiếp.

---

## 2. Bối cảnh hiện tại

Hiện `pharmacy-client/pom.xml` đang trỏ main class tới:

```xml
<mainClass>com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl</mainClass>
```

Nhưng class `DangNhap_Ctrl` / `DangNhapController` hiện không tìm thấy trong `pharmacy-client`.

Hiện có `AuthClientProbe`, nhưng đó chỉ là console probe để test RMI login, không phải JavaFX app chính.

Vì vậy việc đầu tiên cần làm là tạo lại app entry point:

```text
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/PharmacyClientApplication.java
```

---

## 3. Luật bắt buộc

### 3.1. Chưa xóa root src ngay

Không xóa:

```text
src/
pom.xml root cũ
SQL Server script cũ
```

cho tới khi project mới:

```text
- build pass
- RMI server start được
- JavaFX client mở được
- login được
- màn hình chính mở được
- các màn chính được nối hoặc có placeholder an toàn
- smoke test chức năng chính pass
```

### 3.2. Client không được gọi DB

Trong `pharmacy-client` không được có:

```text
ConnectDB
DAO
java.sql
PreparedStatement
ResultSet
CallableStatement
EntityManager
Hibernate
import pharmacy-server
```

### 3.3. Server không chứa UI

Trong `pharmacy-server` không được có:

```text
JavaFX controller
FXML
Stage
Scene
TableView
Button
Label
FileChooser
UI package
```

### 3.4. Common chỉ chứa hợp đồng dùng chung

Trong `pharmacy-common` chỉ nên có:

```text
remote interface
DTO
request/response
enum
exception
model shared tạm thời nếu còn cần
```

Không có JavaFX/JPA/JDBC/service implementation.

### 3.5. Không copy nguyên DAO cũ

Nếu còn cần logic cũ:

```text
đọc DAO cũ
-> lấy query/logic cần thiết
-> viết repository/service mới đúng tầng
-> dùng MariaDB/JdbcConnectionProvider/JPA
```

Không để runtime mới đi qua `ConnectDB`.

---

## 4. Gate bắt buộc

Codex phải làm theo gate:

```text
Gate 1: Build pass
Gate 2: RMI server start được hoặc có lý do môi trường rõ ràng
Gate 3: JavaFX app mở được login
Gate 4: Login qua RMI được
Gate 5: Main shell mở được
Gate 6: Menu mở được các màn chính
Gate 7: Smoke test group 1 -> 7
Gate 8: Scan sạch tầng
Gate 9: Cleanup pharmacy-parent legacy
Gate 10: Gỡ mssql-jdbc nếu đủ điều kiện
Gate 11: Archive/xóa root src legacy nếu đủ điều kiện
```

Nếu fail ở gate nào, dừng ở gate đó và ghi rõ trong result.

---

## 5. Phase 0 - Scan trước khi sửa

Chạy từ root repo.

### 5.1. Tìm main class / JavaFX app

```powershell
Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "extends Application|Application.launch|launch\(|public static void main|DangNhap_Ctrl|DangNhapController|Login" |
  Select-Object Path, LineNumber, Line
```

### 5.2. Kiểm tra mainClass trong client pom

```powershell
Select-String -Path pharmacy-parent/pharmacy-client/pom.xml -Pattern "mainClass|javafx-maven-plugin"
```

### 5.3. Liệt kê GUI/controller hiện có

```powershell
Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Include *_GUI.java,*_Ctrl.java |
  Select-Object FullName |
  Sort-Object FullName
```

### 5.4. Scan client sai tầng

```powershell
Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|new .*_Dao|import .*\.dao\.|java\.sql|DriverManager|PreparedStatement|ResultSet|CallableStatement|EntityManager|org\.hibernate|jakarta\.persistence|com\.example\.pharmacy\.server" |
  Select-Object Path, LineNumber, Line
```

---

## 6. Phase 1 - Tạo entry point JavaFX

Tạo file:

```text
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/PharmacyClientApplication.java
```

Yêu cầu:

```java
package com.example.pharmacy.client;

import javafx.application.Application;
import javafx.stage.Stage;

public final class PharmacyClientApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // mở login window
    }
}
```

Cập nhật:

```text
pharmacy-parent/pharmacy-client/pom.xml
```

Đổi:

```xml
<mainClass>com.example.pharmacy.client.PharmacyClientApplication</mainClass>
```

Build:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

---

## 7. Phase 2 - Tạo hoặc nối lại login

Nếu `DangNhap_Ctrl` đã mất, tạo login tối giản mới.

Đề xuất file:

```text
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/ui/LoginView.java
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/ui/LoginController.java
```

Login phải dùng:

```text
AuthClientService
RmiAuthClientService
RmiClientProvider
SessionContext
```

Flow:

```text
Login UI
    -> AuthClientService.login(username, password)
    -> RMI AuthRemote
    -> server
    -> LoginResponse
    -> SessionContext
    -> MainShell
```

Yêu cầu:

- Login sai báo lỗi rõ.
- Login đúng mở main shell.
- Server chưa chạy thì báo lỗi kết nối rõ.
- Không DAO/ConnectDB/JDBC.

---

## 8. Phase 3 - Tạo MainShell / màn hình chính

Tạo:

```text
pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/ui/MainShell.java
```

MainShell cần có:

```text
- Header: user, role, logout
- Sidebar/menu
- Content area
- showScreen(Node node)
- showPlaceholder(String screenName) cho màn chưa nối được
```

Sau login:

```text
if role == QUANLY:
    mở menu quản lý
else:
    mở menu nhân viên
```

Nếu chưa xác định role enum chính xác, dùng thông tin có trong `UserDTO/UserContext` và ghi TODO nếu thiếu.

---

## 9. Phase 4 - Nối menu với màn hình

Menu tối thiểu:

```text
Danh mục:
- Thuốc
- Đơn vị tính
- Kệ hàng
- Khách hàng
- Nhà cung cấp
- Nhân viên
- Khuyến mãi

Xử lý:
- Lập hóa đơn
- Lập phiếu nhập
- Lập phiếu đặt hàng
- Lập phiếu đổi
- Lập phiếu trả

Tìm kiếm:
- Tìm thuốc
- Tìm hóa đơn
- Tìm phiếu nhập
- Tìm phiếu đặt
- Tìm phiếu đổi
- Tìm phiếu trả
- Hoạt động/Audit

Thống kê:
- Thống kê bán hàng
- Top sản phẩm
- XNT
- Thuốc hết hạn/sắp hết hạn
```

Nếu GUI class đang `extends Application`, không launch Application lồng nhau. Làm một trong 3 hướng:

```text
Hướng A: nếu đã có buildUI()/showWithController(), gọi method đó.
Hướng B: refactor nhẹ để tách buildUI() trả về Parent/Pane.
Hướng C: nếu refactor quá lớn, tạm mở Stage riêng và ghi TODO.
```

Ưu tiên Hướng A/B.

Codex phải tạo bảng mapping trong result:

| Menu | View/GUI | Controller | Client service | Trạng thái |
|---|---|---|---|---|

---

## 10. Phase 5 - Kiểm tra RMI/client service mapping

Mỗi màn chính phải đi qua client service/RMI tương ứng:

```text
Thuoc -> ThuocClientService
KhuyenMai -> KhuyenMaiClientService
NhanVien -> NhanVienClientService
HoaDon -> HoaDonClientService
DoiTra -> DoiTraClientService
PhieuNhap -> PhieuNhapClientService
PhieuDatHang -> PhieuDatHangClientService
TonKho -> TonKhoClientService
Report -> ReportClientService
AuditLog -> AuditLogClientService
```

Nếu thiếu client service cho màn nào thì tạo wrapper mỏng, không để controller gọi DB.

---

## 11. Phase 6 - Smoke test chức năng

### 11.1. Build

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

### 11.2. Start server

```powershell
mvn -f pharmacy-parent/pharmacy-server/pom.xml exec:java "-Dexec.mainClass=com.example.pharmacy.server.bootstrap.RmiServerBootstrap"
```

Nếu exec plugin không chạy được, chạy class trong IDE:

```text
com.example.pharmacy.server.bootstrap.RmiServerBootstrap
```

### 11.3. Start client

```powershell
mvn -f pharmacy-parent/pharmacy-client/pom.xml javafx:run
```

Hoặc chạy class:

```text
com.example.pharmacy.client.PharmacyClientApplication
```

### 11.4. Checklist

Auth:

```text
- app mở login
- login sai báo lỗi
- login đúng vào main shell
- logout quay về login
```

Navigation:

```text
- bấm từng menu không crash
- màn nào chưa nối có placeholder/TODO
```

Group 1:

```text
- Thuốc load được
- Đơn vị tính load được
- Kệ hàng load được
- Tồn kho thuốc load được
```

Group 2:

```text
- Nhân viên load được
- Tài khoản/role/cài đặt load được nếu có UI
```

Group 3:

```text
- Khuyến mãi load được
- Thêm/sửa/xóa khuyến mãi nếu UI hỗ trợ
```

Group 4:

```text
- Phiếu nhập load/tạo được
- Phiếu đặt hàng load/tạo được
- Tồn kho sau nhập cập nhật đúng
```

Group 5:

```text
- Lập hóa đơn 1 thuốc
- Lập hóa đơn nhiều dòng
- Không bán vượt tồn
- Tìm kiếm/xem chi tiết hóa đơn
```

Group 6:

```text
- Lập phiếu trả
- Lập phiếu đổi
- Tìm kiếm/xem chi tiết phiếu đổi/trả
```

Group 7:

```text
- Thống kê bán hàng load được
- Top sản phẩm load được
- XNT load được
- Hoạt động/Audit load được
```

Nếu môi trường không chạy được runtime, ghi rõ lý do và tạo checklist thủ công.

---

## 12. Phase 7 - Scan sạch tầng trước cleanup

### Client

```powershell
Get-ChildItem pharmacy-parent/pharmacy-client/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "ConnectDB|new .*_Dao|import .*\.dao\.|java\.sql|DriverManager|PreparedStatement|ResultSet|CallableStatement|EntityManager|org\.hibernate|jakarta\.persistence|com\.example\.pharmacy\.server" |
  Select-Object Path, LineNumber, Line
```

### Common

```powershell
Get-ChildItem pharmacy-parent/pharmacy-common/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "javafx|jakarta\.persistence|javax\.persistence|org\.hibernate|ConnectDB|DriverManager|EntityManager|RepositoryImpl|ServiceImpl|com\.example\.pharmacy\.server|com\.example\.pharmacy\.client" |
  Select-Object Path, LineNumber, Line
```

### Server UI leakage

```powershell
Get-ChildItem pharmacy-parent/pharmacy-server/src/main/java -Recurse -File -Include *.java |
  Select-String -Pattern "javafx\.fxml|javafx\.scene|javafx\.stage|javafx\.application|TableView|Button|Label|FXMLLoader|Stage|Scene|com\.example\.pharmacy\.client" |
  Select-Object Path, LineNumber, Line
```

### DAO/ConnectDB runtime mới

```powershell
Get-ChildItem pharmacy-parent -Recurse -File -Include *.java |
  Select-String -Pattern "com\.example\.pharmacymanagementsystem_qlht\.dao|new .*_Dao|ConnectDB" |
  Select-Object Path, LineNumber, Line
```

Nếu còn match trong runtime code mới thì chưa được cleanup.

---

## 13. Phase 8 - Dọn legacy khỏi pharmacy-parent

Chỉ làm nếu:

```text
- build pass
- login UI chạy
- main shell mở được
- menu chính mở không crash
- scan không còn code mới gọi DAO/ConnectDB
```

Dọn hoặc archive:

```text
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/dao
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB
pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacymanagementsystem_qlht/service legacy nếu không còn dùng
```

Có thể chuyển vào:

```text
archive/legacy-in-pharmacy-parent/
```

Sau đó build lại:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

---

## 14. Phase 9 - Gỡ SQL Server dependency khỏi project mới

Nếu không còn DAO/ConnectDB trong `pharmacy-parent`, gỡ:

```text
mssql-jdbc
SQL Server config runtime trong pharmacy-server nếu còn
```

Kiểm tra:

```powershell
Select-String -Path pharmacy-parent/pharmacy-server/pom.xml -Pattern "mssql|sqlserver"
```

Build lại:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

---

## 15. Phase 10 - Archive hoặc xóa root src legacy

Chỉ làm sau khi:

```text
- app mới chạy được
- login/main/navigation pass
- smoke test chức năng chính pass
- pharmacy-parent không còn DAO/ConnectDB/mssql-jdbc runtime
```

Tạo tag trước:

```powershell
git tag legacy-before-final-cleanup
git push origin legacy-before-final-cleanup
```

Sau đó chọn một hướng:

### Hướng an toàn

```text
archive/legacy-monolith/src
archive/legacy-monolith/pom.xml
archive/legacy-monolith/SQL-server-old
```

### Hướng sạch

```text
xóa root src/
xóa hoặc archive pom.xml root cũ nếu không còn dùng
xóa hoặc archive SQL Server script cũ nếu không cần reference
```

Build lại:

```powershell
mvn -q -f pharmacy-parent/pom.xml -DskipTests compile
```

---

## 16. File result bắt buộc tạo

Tạo:

```text
docs/migration/STEP_20_FINAL_APP_RECONNECTION_E2E_AND_LEGACY_CLEANUP_RESULT.md
```

Result phải có:

```text
- main class cũ và mới
- file entry point đã tạo
- login flow
- main shell/navigation mapping
- mapping màn hình -> client service -> remote
- smoke test result group 1 -> 7
- scan sai tầng
- cleanup đã làm
- dependency cleanup
- root src đã archive/xóa hay chưa
- build final
- rủi ro còn lại
- kết luận PASS/FAIL
```

---

## 17. Prompt dùng trực tiếp cho Codex

```text
Bạn hãy thực hiện giai đoạn cuối: Final App Reconnection + E2E Test + Legacy Cleanup cho repo PharmacyManagementSystem-Re-architecture.

Mục tiêu:
- Tạo/nối lại entry point JavaFX chính cho pharmacy-client.
- Login được bằng RMI Auth.
- Sau login mở được màn hình chính giống project cũ.
- Nối menu tới các màn hình đã migrate.
- Test smoke các chức năng group 1 -> 7.
- Sau khi project mới chạy được, dọn legacy khỏi pharmacy-parent.
- Chỉ archive/xóa root src legacy khi project mới đã build và smoke test pass.

Bối cảnh quan trọng:
- pharmacy-client/pom.xml đang trỏ mainClass tới com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl nhưng class này hiện không tồn tại.
- Hiện chỉ có AuthClientProbe để test RMI login console, chưa phải JavaFX app chính.
- Cần tạo main app mới: com.example.pharmacy.client.PharmacyClientApplication.

Luật bắt buộc:
- Không xóa root src legacy trước khi app mới chạy được.
- Không để client gọi DAO/ConnectDB/JDBC.
- Không để client phụ thuộc pharmacy-server.
- Không để common chứa JavaFX/JPA/JDBC/implementation.
- Không để server chứa JavaFX UI/controller/FXML.
- Không copy nguyên DAO cũ vào project mới.
- Nếu còn logic cũ cần dùng, chuyển sang repository/service mới đúng tầng.
- Không refactor lan nghiệp vụ nếu không cần.
- Làm theo gate: build -> server -> login -> main shell -> navigation -> smoke test -> scan -> cleanup.

PHASE 0 - Scan:
- Đọc docs migration/result mới nhất.
- Scan main class hiện tại trong pharmacy-client.
- Scan các class extends Application/public static void main.
- Scan các view/controller hiện có để lập mapping màn hình.

PHASE 1 - Tạo entry point:
- Tạo pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/PharmacyClientApplication.java.
- Class extends Application, main() gọi launch(args).
- Cập nhật pharmacy-client/pom.xml mainClass thành com.example.pharmacy.client.PharmacyClientApplication.
- Build compile.

PHASE 2 - Login:
- Nếu DangNhap_Ctrl không tồn tại, tạo login UI tối giản.
- Login UI dùng AuthClientService/RmiAuthClientService/RmiClientProvider/SessionContext.
- Không gọi DAO/ConnectDB.
- Login thành công mở MainShell.
- Login lỗi hiển thị message rõ ràng.

PHASE 3 - MainShell:
- Tạo/nối màn hình chính.
- Có header user/role/logout.
- Có menu/sidebar theo nhóm: Danh mục, Xử lý, Tìm kiếm, Thống kê.
- Phân quyền quản lý/nhân viên nếu UserContext có role.

PHASE 4 - Nối màn hình:
- Lập bảng mapping menu -> GUI/View -> Controller -> ClientService/Remote.
- Với GUI class đang extends Application, refactor nhẹ để có buildUI()/showWithController() trả về Parent/Pane nếu cần.
- Không launch Application lồng nhau.
- Nếu màn nào chưa nối được thì tạo placeholder an toàn và ghi TODO, không crash app.

PHASE 5 - Test RMI/client service:
- Mỗi màn chính phải gọi client service/RMI tương ứng.
- Không controller nào gọi DAO/ConnectDB.
- Test mở từng menu không crash.

PHASE 6 - Smoke test:
- Test Auth.
- Test group 1 Thuoc/DonViTinh/KeHang.
- Test group 2 NhanVien/TaiKhoan/CaiDat.
- Test group 3 KhuyenMai.
- Test group 4 PhieuNhap/PhieuDatHang/TonKho.
- Test group 5 HoaDon/BanHang.
- Test group 6 DoiTra.
- Test group 7 ThongKe/HoatDong/BaoCao.
- Nếu không chạy được runtime vì môi trường, ghi rõ lý do và để checklist thủ công.

PHASE 7 - Scan sai tầng:
- pharmacy-client không có DAO/ConnectDB/java.sql/JPA/Hibernate/import server.
- pharmacy-common không có JavaFX/JPA/JDBC/implementation.
- pharmacy-server không có JavaFX UI/controller/view.
- pharmacy-parent không còn runtime code mới gọi DAO/ConnectDB.

PHASE 8 - Cleanup pharmacy-parent:
- Nếu scan pass, archive/xóa DAO/ConnectDB legacy trong pharmacy-parent.
- Không xóa root src ở phase này nếu smoke test chưa pass.
- Build lại.

PHASE 9 - Remove SQL Server dependency:
- Nếu không còn ConnectDB/SQL Server code trong pharmacy-parent, gỡ mssql-jdbc khỏi pharmacy-server/pom.xml.
- Build lại.

PHASE 10 - Archive/delete root legacy:
- Chỉ sau khi app mới chạy được và smoke test pass.
- Tạo git tag legacy-before-final-cleanup.
- Archive hoặc xóa root src legacy.
- Build lại pharmacy-parent.

Output bắt buộc:
Tạo file docs/migration/STEP_20_FINAL_APP_RECONNECTION_E2E_AND_LEGACY_CLEANUP_RESULT.md

File result phải ghi:
- entry point JavaFX đã tạo
- login flow
- main shell/navigation mapping
- RMI/client service mapping
- smoke test result
- scan sai tầng
- cleanup đã làm
- dependency cleanup
- root src đã archive/xóa hay chưa
- build final
- rủi ro còn lại
- kết luận PASS/FAIL.
```
