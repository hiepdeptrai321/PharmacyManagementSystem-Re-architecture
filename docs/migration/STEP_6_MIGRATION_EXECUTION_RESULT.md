# STEP 6 MIGRATION EXECUTION RESULT

## Mục tiêu migrate

Thực hiện migrate code thật từ monolith cũ trong `src/` sang cấu trúc 3 module Maven dưới `pharmacy-parent/`, đồng thời giữ đúng hướng dependency:

- `pharmacy-client -> pharmacy-common`
- `pharmacy-server -> pharmacy-common`
- không có `pharmacy-client -> pharmacy-server`
- `pharmacy-common` không phụ thuộc client/server

## Tài liệu đã đọc trước khi migrate

Tất cả tài liệu yêu cầu đều tồn tại và đã được đối chiếu trong lượt migrate này:

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/DAO_MIGRATION_MAPPING.md`
- `docs/migration/QUERY_INVENTORY.md`
- `docs/migration/MIGRATION_PRIORITY.md`
- `docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`

## Kết quả tổng quan sau migrate

- `pharmacy-parent` build thành công bằng `mvn clean install`
- `pharmacy-common` chứa shared DTO/common classes và shared legacy model/session đã làm sạch tầng
- `pharmacy-server` chứa server code, DAO JDBC legacy, service nghiệp vụ, transaction, config
- `pharmacy-client` chỉ còn client JavaFX sạch tầng, không còn import server/DAO/JDBC/JPA
- root `src/` vẫn được giữ lại làm reference an toàn

## Cấu trúc module sau migrate

### `pharmacy-common`

Hiện có `58` Java files tổng cộng, trong đó có các nhóm:

- `com.example.pharmacy.common.*`
  - DTO
  - request/response
  - enum
  - exception
  - remote interface RMI
- `com.example.pharmacymanagementsystem_qlht.model`
  - shared legacy domain/model đã được chuyển sang common
- `com.example.pharmacymanagementsystem_qlht.session`
  - `UserContext`
  - `LoginResult`

### `pharmacy-server`

Hiện có `88` Java files tổng cộng, gồm:

- `com.example.pharmacy.server.*`
  - bootstrap
  - config
  - repository
  - service
  - transaction
  - entity
- `com.example.pharmacymanagementsystem_qlht.connectDB`
- `com.example.pharmacymanagementsystem_qlht.dao`
- `com.example.pharmacymanagementsystem_qlht.service`
  - các service nghiệp vụ/legacy cần DB

### `pharmacy-client`

Hiện có `20` Java files và `97` resource files, gồm:

- `com.example.pharmacy.client.*`
  - RMI client
  - session client mới
  - auth client probe
- `com.example.pharmacymanagementsystem_qlht.*`
  - client login shell sạch tầng
  - session/UI utility
  - resource UI

## Các dependency sai tầng đã phát hiện và đã xử lý

### 1. `pharmacy-client -> pharmacy-server`

Trong lượt migrate trước đã có một bridge tạm `client -> server` để kéo toàn bộ controller legacy vào module client.

Trạng thái hiện tại:

- **Đã gỡ hoàn toàn**
- `pharmacy-client/pom.xml` không còn dependency sang `pharmacy-server`

### 2. `pharmacy-common` chứa model tự gọi DAO

Phát hiện:

- `PhieuNhap.java` từng import `ChiTietPhieuNhap_Dao`
- model trong common không được gọi data layer

Trạng thái hiện tại:

- **Đã xử lý**
- `PhieuNhap#getTongTien()` chỉ còn đọc state cục bộ
- thêm setter cho `TongTien`

### 3. `pharmacy-common` có JavaFX dependency

Nguyên nhân:

- `ChiTietHoaDon.java` dùng `IntegerProperty`

Trạng thái hiện tại:

- **Đã xử lý**
- refactor `ChiTietHoaDon` sang field `int soLuong`
- xóa `javafx-base` khỏi `pharmacy-common/pom.xml`

## Bảng mapping file migrate

| STT | File/Class | Vị trí cũ | Vị trí mới | Module | Loại code | Trạng thái | Ghi chú |
|---|---|---|---|---|---|---|---|
| 1 | `model/**` | `src/main/java/com/example/pharmacymanagementsystem_qlht/model` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model` | pharmacy-common | Cần review | Chuyển một phần | Đã chuyển nguyên cụm model legacy sang common để dùng chung tạm thời |
| 2 | `session/UserContext.java` | `src/main/java/.../session` | `pharmacy-parent/pharmacy-common/src/main/java/.../session` | pharmacy-common | Session | Đã chuyển | Dùng chung client/server |
| 3 | `session/LoginResult.java` | `src/main/java/.../session` | `pharmacy-parent/pharmacy-common/src/main/java/.../session` | pharmacy-common | Response | Đã chuyển | Dùng cho login flow legacy/client bridge |
| 4 | `connectDB/**` | `src/main/java/.../connectDB` | `pharmacy-parent/pharmacy-server/src/main/java/.../connectDB` | pharmacy-server | DB Config | Đã chuyển | Chỉ còn ở server module |
| 5 | `dao/**` | `src/main/java/.../dao` | `pharmacy-parent/pharmacy-server/src/main/java/.../dao` | pharmacy-server | Legacy DAO | Đã chuyển | JDBC legacy đặt ở server |
| 6 | `service/AuthService.java` legacy DB-backed | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Bản server-side legacy |
| 7 | `service/KhachHangService.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Gọi DAO cũ |
| 8 | `service/KeHangService.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Gọi DAO cũ |
| 9 | `service/NhaCungCapService.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Gọi DAO cũ |
| 10 | `service/CaiDatService.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Gọi DAO cũ |
| 11 | `service/DichVuKhuyenMai.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-server/src/main/java/.../service` | pharmacy-server | Server Service | Đã chuyển | Có truy cập DAO khuyến mãi |
| 12 | `service/DoiHangItem.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-client/src/main/java/.../service` | pharmacy-client | Utility | Đã chuyển | UI item, không có DB |
| 13 | `service/TraHangItem.java` | `src/main/java/.../service` | `pharmacy-parent/pharmacy-client/src/main/java/.../service` | pharmacy-client | Utility | Đã chuyển | UI item, không có DB |
| 14 | `session/SessionContext.java` | `src/main/java/.../session` | `pharmacy-parent/pharmacy-client/src/main/java/.../session` | pharmacy-client | Session | Đã chuyển | Chỉ dùng phía client |
| 15 | `session/UserContextMapper.java` | `src/main/java/.../session` | `pharmacy-parent/pharmacy-client/src/main/java/.../session` | pharmacy-client | Utility | Đã chuyển | Hỗ trợ UI/session mapping |
| 16 | `TienIch/**` | `src/main/java/.../TienIch` | `pharmacy-parent/pharmacy-client/src/main/java/.../TienIch` | pharmacy-client | Utility | Đã chuyển | Utility UI |
| 17 | `DangNhap_GUI.java` | `src/main/java/.../view/DangNhap_GUI.java` | `pharmacy-parent/pharmacy-client/src/main/java/.../view/DangNhap_GUI.java` | pharmacy-client | JavaFX Resource | Chuyển một phần | Đã re-implement thành login view sạch tầng |
| 18 | `DangNhap_Ctrl.java` | `src/main/java/.../controller/DangNhap_Ctrl.java` | `pharmacy-parent/pharmacy-client/src/main/java/.../controller/DangNhap_Ctrl.java` | pharmacy-client | JavaFX Controller | Chuyển một phần | Đã refactor dùng RMI auth qua client service |
| 19 | `ClientHome_GUI.java` | Không có trực tiếp ở monolith | `pharmacy-parent/pharmacy-client/src/main/java/.../view/ClientHome_GUI.java` | pharmacy-client | JavaFX Resource | Đã chuyển | Client home sạch tầng thay cho shell cũ đang còn dính DAO |
| 20 | `ClientHome_Ctrl.java` | Không có trực tiếp ở monolith | `pharmacy-parent/pharmacy-client/src/main/java/.../controller/ClientHome_Ctrl.java` | pharmacy-client | JavaFX Controller | Đã chuyển | Hiển thị session và logout |
| 21 | `service/AuthService.java` client wrapper | Monolith cũ là DB-backed service | `pharmacy-parent/pharmacy-client/src/main/java/.../service/AuthService.java` | pharmacy-client | Client Service | Đã chuyển | Wrapper gọi `AuthRemote` qua RMI |
| 22 | `resources/com/**` | `src/main/resources/com` | `pharmacy-parent/pharmacy-client/src/main/resources/com` | pharmacy-client | JavaFX Resource | Đã chuyển | Giữ css, image, excel template phục vụ client |
| 23 | `controller/**` legacy còn dính DAO | `src/main/java/.../controller` | `src cũ` | src cũ | JavaFX Controller | Tạm giữ ở src cũ | Chưa đưa sang client vì vi phạm tầng |
| 24 | `view/**` legacy đi kèm controller dính DAO | `src/main/java/.../view` | `src cũ` | src cũ | JavaFX Resource | Tạm giữ ở src cũ | Chờ migrate theo từng màn |

## Danh sách file/class còn ở `src` cũ

Root monolith vẫn còn nguyên `225` Java files trong:

- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/view`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/dao`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/service`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/model`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/session`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/TienIch`

Lý do giữ lại:

- làm reference/an toàn
- nhiều màn hình legacy vẫn gọi DAO trực tiếp
- chưa đủ điều kiện migrate sạch tầng vào `pharmacy-client`

## Scan kiểm tra sau migrate

### `pharmacy-client`

Đã scan các pattern:

- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `EntityManager`
- `org.hibernate`
- `jakarta.persistence`
- import `dao`
- import `server`

Kết quả:

- **Không phát hiện vi phạm**

### `pharmacy-common`

Đã scan các pattern:

- `javafx`
- `jakarta.persistence`
- `javax.persistence`
- `ConnectDB`
- `dao`
- `repository`
- `controller`
- `service implementation`

Kết quả:

- **Không phát hiện vi phạm**

### `pharmacy-server`

Đã scan các pattern:

- `javafx.scene`
- `javafx.control`
- `javafx.event`
- import `pharmacy-client`
- import `controller`
- import `view`

Kết quả:

- **Không phát hiện vi phạm**

## Build result

Lệnh đã chạy:

```powershell
mvn -f pharmacy-parent/pom.xml clean install
```

Kết quả:

- `BUILD SUCCESS`

## Các pom.xml đã sửa

- `pharmacy-parent/pharmacy-common/pom.xml`
- `pharmacy-parent/pharmacy-client/pom.xml`
- `pharmacy-parent/pharmacy-server/pom.xml`

## Những điểm còn cần review

1. `pharmacy-server` hiện vẫn có dependency `javafx-base`
   
   Lý do:
   - `ThongKe_Dao` và `ThongKeXNT_Dao` legacy còn trả về `ObservableList`
   - đây không phải UI import trực tiếp, nhưng vẫn là technical debt cần dọn tiếp

2. `model/**` hiện đang nằm ở `pharmacy-common`

   Lý do:
   - đây là cách ít rủi ro nhất để ổn định build ở giai đoạn hiện tại
   - về dài hạn cần tách rõ:
     - DTO/common model thực sự dùng chung
     - entity/projection server-only

3. Nhiều màn hình JavaFX legacy chưa được migrate sang client module

   Lý do:
   - còn phụ thuộc trực tiếp DAO/service DB-backed
   - nếu đưa sang client lúc này sẽ phá dependency direction

## Hướng xử lý tiếp theo

1. Tách tiếp từng màn hình legacy khỏi DAO trong root `src` theo thứ tự ưu tiên trong `MIGRATION_PRIORITY.md`
2. Với mỗi nhóm màn hình sạch được:
   - chuyển controller/view tương ứng sang `pharmacy-client`
   - tạo client service/remote request nếu cần
3. Tách `model` trong common thành:
   - DTO/common request-response thực sự dùng chung
   - entity/projection/server-only
4. Dọn `javafx-base` khỏi `pharmacy-server` bằng cách refactor các DAO thống kê trả `List` thay vì `ObservableList`
5. Khi coverage module mới đủ lớn và ổn định, mới tính chuyện archive hoặc loại bỏ dần root `src`
