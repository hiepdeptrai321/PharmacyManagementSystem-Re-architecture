# MIGRATION STEP 6 EXECUTION

## Mục tiêu

Thực hiện migration thật từ monolith cũ trong `src/main/java` và `src/main/resources` sang cấu trúc multi-module trong `pharmacy-parent`, để `pharmacy-parent` trở thành điểm build chính thay vì chỉ là scaffold song song.

## Trách nhiệm module sau migration

- `pharmacy-common`
  - Chứa shared domain model legacy trong package `com.example.pharmacymanagementsystem_qlht.model`
  - Chứa shared session DTO trong package `com.example.pharmacymanagementsystem_qlht.session`
  - Chứa các DTO/request/response/remote interface mới trong package `com.example.pharmacy.common.*`

- `pharmacy-server`
  - Chứa toàn bộ JDBC legacy: `connectDB`, `dao`, các `service` có truy cập DB
  - Chứa các service/repository/transaction/bootstrap mới trong package `com.example.pharmacy.server.*`
  - Là nơi chứa persistence, transaction và logic server-side

- `pharmacy-client`
  - Chứa JavaFX controller/view/resource của app legacy
  - Chứa session client-side, UI utility, các item class phục vụ TableView/UI
  - Chứa RMI client/service mới trong package `com.example.pharmacy.client.*`

## Các package/class đã chuyển thật

| STT | Class/File | Vị trí hiện tại | Vị trí đích | Loại code | Trạng thái | Ghi chú |
|---|---|---|---|---|---|---|
| 1 | `model/**` | `src/main/java/com/example/pharmacymanagementsystem_qlht/model` | `pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model` | Shared domain model | Đã chuyển | Giữ nguyên package cũ để giảm sửa import |
| 2 | `session/UserContext.java` | `src/main/java/.../session` | `pharmacy-common/src/main/java/.../session` | Shared session DTO | Đã chuyển | Dùng chung client/server |
| 3 | `session/LoginResult.java` | `src/main/java/.../session` | `pharmacy-common/src/main/java/.../session` | Shared response model | Đã chuyển | Dùng cho flow đăng nhập legacy |
| 4 | `connectDB/**` | `src/main/java/.../connectDB` | `pharmacy-server/src/main/java/.../connectDB` | Database config/connection | Đã chuyển | DB access nằm phía server |
| 5 | `dao/**` | `src/main/java/.../dao` | `pharmacy-server/src/main/java/.../dao` | Legacy DAO/JDBC | Đã chuyển | Giữ nguyên package cũ |
| 6 | `service/AuthService.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển | Truy cập DB qua DAO |
| 7 | `service/CaiDatService.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển |  |
| 8 | `service/KeHangService.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển |  |
| 9 | `service/KhachHangService.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển |  |
| 10 | `service/NhaCungCapService.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển |  |
| 11 | `service/DichVuKhuyenMai.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Legacy business service | Đã chuyển | Có truy cập DAO |
| 12 | `service/ApDungKhuyenMai.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Promotion helper | Đã chuyển | Tạm giữ cùng service package cũ để không gãy import |
| 13 | `service/PhienKhuyenMai.java` | `src/main/java/.../service` | `pharmacy-server/src/main/java/.../service` | Promotion helper | Đã chuyển | Chưa dùng rộng, tạm giữ server-side |
| 14 | `controller/**` | `src/main/java/.../controller` | `pharmacy-client/src/main/java/.../controller` | JavaFX controller | Đã chuyển | Giữ nguyên package cũ |
| 15 | `view/**` | `src/main/java/.../view` | `pharmacy-client/src/main/java/.../view` | JavaFX view | Đã chuyển | App legacy build UI bằng code, không phải FXML chủ đạo |
| 16 | `TienIch/**` | `src/main/java/.../TienIch` | `pharmacy-client/src/main/java/.../TienIch` | UI utility | Đã chuyển | Chứa JavaFX helper nên nằm phía client |
| 17 | `session/SessionContext.java` | `src/main/java/.../session` | `pharmacy-client/src/main/java/.../session` | Client session holder | Đã chuyển | Static session chỉ dùng client-side tạm thời |
| 18 | `session/UserContextMapper.java` | `src/main/java/.../session` | `pharmacy-client/src/main/java/.../session` | UI/session helper | Đã chuyển | Dùng trong controller legacy |
| 19 | `service/DoiHangItem.java` | `src/main/java/.../service` | `pharmacy-client/src/main/java/.../service` | UI item model | Đã chuyển | Có JavaFX property |
| 20 | `service/TraHangItem.java` | `src/main/java/.../service` | `pharmacy-client/src/main/java/.../service` | UI item model | Đã chuyển | Có JavaFX property |
| 21 | `resources/com/**` | `src/main/resources/com` | `pharmacy-client/src/main/resources/com` | UI resource | Đã chuyển | Bao gồm css, image, excel template |

## Các chỉnh sửa code quan trọng trong lúc migrate

### 1. Cắt phụ thuộc DAO khỏi `pharmacy-common`

Model `PhieuNhap` trong code cũ tự gọi `ChiTietPhieuNhap_Dao` để tính tổng tiền. Điều này làm `common` không thể độc lập.

Đã sửa:

- `PhieuNhap#getTongTien()` chỉ trả về field `TongTien`
- thêm `PhieuNhap#setTongTien(Double)`
- xóa import DAO khỏi:
  - `PhieuNhap.java`
  - `ChiTietPhieuNhap.java`

Đây là chỉnh sửa ít rủi ro nhất để `common` compile đúng vai trò shared layer.

### 2. Giữ nguyên package legacy để giảm gãy import

Các package `com.example.pharmacymanagementsystem_qlht.*` được giữ nguyên trong module mới. Nhờ vậy phần lớn controller/view/dao/service legacy compile lại mà không cần đổi import hàng loạt.

## Cập nhật Maven

### `pharmacy-common/pom.xml`

- thêm `javafx-base`
- lý do: `ChiTietHoaDon` vẫn dùng `IntegerProperty`/`SimpleIntegerProperty`

### `pharmacy-server/pom.xml`

- giữ dependency sang `pharmacy-common`
- giữ JPA/Hibernate/MariaDB/SQL Server driver
- thêm `javafx-base`
- lý do: một số legacy DAO thống kê còn trả về `ObservableList`

### `pharmacy-client/pom.xml`

- giữ dependency sang `pharmacy-common`
- thêm JavaFX dependencies cần cho app legacy:
  - `javafx-base`
  - `javafx-controls`
  - `javafx-fxml`
  - `javafx-web`
  - `javafx-swing`
- thêm các thư viện UI/report đang dùng trong code cũ:
  - `openpdf`
  - `poi-ooxml`
  - `itextpdf` (`kernel`, `layout`, `io`)
  - `controlsfx`
  - `formsfx-core`
  - `validatorfx`
  - `ikonli-javafx`
  - `bootstrapfx-core`
  - `tilesfx`
  - `slf4j-api`
  - `slf4j-nop`
  - `log4j-api`
  - `log4j-core`
- thêm `javafx-maven-plugin` với main class:
  - `com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl`

## Cầu nối chuyển tiếp đang tồn tại

### Temporary bridge: `pharmacy-client -> pharmacy-server`

Để toàn bộ app legacy compile được ngay trên cấu trúc mới, `pharmacy-client` hiện **tạm thời** có dependency compile sang `pharmacy-server`.

Lý do:

- nhiều controller legacy vẫn đang gọi trực tiếp DAO/service cũ
- việc refactor hết các controller đó sang client adapter/RMI trong cùng một lượt migration sẽ rủi ro rất cao

Biện pháp giảm rủi ro đã áp dụng:

- dependency này được ghi nhận công khai, không để âm thầm
- đã exclude các dependency DB/JPA nặng khỏi dependency server trong client:
  - `jakarta.persistence-api`
  - `jakarta.transaction-api`
  - `hibernate-core`
  - `mariadb-java-client`
  - `mssql-jdbc`
  - `slf4j-simple`

Kết luận:

- đây là **bridge chuyển tiếp để compile và chạy app legacy trên cấu trúc multi-module mới**
- không phải đích kiến trúc cuối cùng
- bước tiếp theo phải là thay dần controller direct-call sang `client service -> remote/server service`

## Dependency direction hiện tại

### Đúng hoàn toàn

- `pharmacy-common` không phụ thuộc `pharmacy-client`
- `pharmacy-common` không phụ thuộc `pharmacy-server`
- `pharmacy-server -> pharmacy-common`

### Tạm thời có chủ đích

- `pharmacy-client -> pharmacy-common`
- `pharmacy-client -> pharmacy-server` (bridge chuyển tiếp)

## Lỗi đã gặp và cách xử lý

### Lỗi compile ban đầu

`pharmacy-common` fail vì:

- `PhieuNhap.java` import `ChiTietPhieuNhap_Dao`
- `ChiTietPhieuNhap.java` import DAO không cần thiết

### Cách xử lý

- xóa dependency DAO khỏi model trong `common`
- để model chỉ còn giữ state, không tự truy cập data layer

## Lệnh Maven đã chạy

```powershell
mvn -q -DskipTests compile
mvn clean install
```

Kết quả:

- `pharmacy-parent`: `BUILD SUCCESS`
- `pharmacy-common`: `BUILD SUCCESS`
- `pharmacy-server`: `BUILD SUCCESS`
- `pharmacy-client`: `BUILD SUCCESS`

## Các package/class chưa migrate dứt điểm

- root `src/main/java/**` và `src/main/resources/**` **chưa xóa**
- chúng vẫn được giữ lại làm bản tham chiếu an toàn
- direct DAO call trong nhiều controller legacy **chưa được loại bỏ hoàn toàn**
- một số model/DAO legacy vẫn còn phụ thuộc `javafx-base`

## TODO tiếp theo

1. Gỡ dần dependency chuyển tiếp `pharmacy-client -> pharmacy-server`
2. Tách các controller còn `new Xxx_Dao()` sang client adapter/service
3. Thay các legacy service mà controller đang gọi bằng remote/client service
4. Chuẩn hóa các model còn dùng JavaFX property nếu muốn làm `common` thuần Java hơn
5. Khi app mới ổn định trên `pharmacy-parent`, archive hoặc loại bỏ dần source tree cũ ở root

## Kết luận

Migration bước 6 đã được **thực thi thật**:

- code đã được chuyển vật lý sang `pharmacy-parent`
- `pharmacy-parent` đã build thành công bằng Maven
- `pharmacy-parent` hiện là điểm build chính khả dụng
- root source tree cũ vẫn được giữ lại như vùng tham chiếu an toàn

Điểm kiến trúc còn lệch lớn nhất hiện tại là dependency bridge `client -> server`. Đây là chỗ phải xử lý ở các bước tách controller/service/remote tiếp theo.
