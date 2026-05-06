# STEP 5 - Tách Session Người Dùng Khỏi UI Và ConnectDB

## Mục tiêu
- Loại bỏ phụ thuộc sai tầng giữa `ConnectDB` và `DangNhap_Ctrl.user`.
- Không còn dùng biến static trong controller đăng nhập làm "nguồn user toàn hệ thống".
- Tạo `UserContext` và `SessionContext` rõ ràng cho JavaFX monolith hiện tại.
- Chuẩn bị cho giai đoạn client-server, nơi mỗi request phải mang theo thông tin người gọi thay vì dùng static user ở server.

## Vấn đề trước khi sửa
- `ConnectDB` import trực tiếp `DangNhap_Ctrl` và tự `SET CONTEXT_INFO` bằng `DangNhap_Ctrl.user.getMaNV()`.
- Sau đăng nhập, app lưu người dùng bằng `DangNhap_Ctrl.user`.
- Nhiều controller nghiệp vụ lấy nhân viên hiện tại từ `DangNhap_Ctrl.user` để lập hóa đơn, phiếu nhập, phiếu đặt, phiếu đổi, phiếu trả.
- Hai màn hình chính cũng đọc `DangNhap_Ctrl.user` để hiển thị tên người dùng, vai trò và logout.

## Thiết kế mới
- `UserContext`
  - Object dữ liệu nhẹ, `Serializable`.
  - Chỉ giữ `userId`, `username`, `employeeId`, `fullName`, `role`.
  - Không chứa password.
  - Không chứa DAO, `Connection`, JavaFX control hay entity nặng.
- `SessionContext`
  - Context phiên tạm thời cho JavaFX monolith hiện tại.
  - Chỉ dùng ở phía client/monolith.
  - Có `setCurrentUser()`, `getCurrentUser()`, `requireCurrentUser()`, `isLoggedIn()`, `clear()`.
- `LoginResult`
  - Kết quả đăng nhập gồm `success`, `message`, `userContext`.
- `UserContextMapper`
  - Cầu nối tạm thời để đổi `UserContext` sang `NhanVien` tối thiểu cho các luồng nghiệp vụ cũ chưa đi qua service riêng.

## Luồng mới sau khi sửa
```text
DangNhap_Ctrl
    -> AuthService.login(...)
    -> LoginResult
    -> SessionContext.setCurrentUser(userContext)
    -> Mở màn hình chính

Controller nghiệp vụ
    -> SessionContext.getCurrentUser()
    -> UserContext / UserContextMapper
    -> DAO JDBC cũ hoặc service hiện có

ConnectDB
    -> chỉ mở connection / tạo statement
    -> không đọc user từ UI
```

## Quy tắc áp dụng
- `ConnectDB` không được import `controller.*`.
- `ConnectDB` không được đọc `DangNhap_Ctrl.user`.
- `ConnectDB` không được giữ "current user" của ứng dụng.
- Controller được phép đọc `SessionContext` trong monolith hiện tại.
- Khi bước sau tách nghiệp vụ khỏi controller nhiều hơn, service nên nhận `UserContext` qua tham số hoặc request object.
- Không dùng `SessionContext.currentUser` ở server tương lai. Với client-server, user phải đi theo request/token/session id.

## Bảng mapping các chỗ dùng user hiện tại

| STT | File/Class | Đang dùng user để làm gì | Cách cũ | Cách mới | Trạng thái | Ghi chú |
|---|---|---|---|---|---|---|
| 1 | `ConnectDB` | Gắn `CONTEXT_INFO` cho DB/audit trigger | `DangNhap_Ctrl.user` | Không lấy user trong `ConnectDB` nữa | Đã sửa | Audit trigger SQL Server cần thiết kế lại ở bước sau |
| 2 | `DangNhap_Ctrl` | Lưu người dùng sau login | `public static NhanVien user` | `SessionContext.setCurrentUser(LoginResult.getUserContext())` | Đã sửa | Không lưu password vào session |
| 3 | `CuaSoChinh_QuanLy_Ctrl` | Hiển thị tên người dùng, vai trò, logout | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` và `SessionContext.clear()` | Đã sửa | Giữ nguyên flow mở lại màn login |
| 4 | `CuaSoChinh_NhanVien_Ctrl` | Hiển thị tên người dùng, vai trò, logout | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` và `SessionContext.clear()` | Đã sửa | Giữ nguyên flow mở lại màn login |
| 5 | `LapHoaDon_Ctrl` | Lấy mã nhân viên lập hóa đơn | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` + `UserContextMapper.toNhanVienReference()` | Đã sửa | Bước sau nên đổi thành `HoaDonService.lapHoaDon(request, userContext)` |
| 6 | `LapPhieuDatHang_Ctrl` | Gắn người lập phiếu đặt | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` + mapper | Đã sửa | Hiện vẫn là cầu nối tạm trong controller |
| 7 | `LapPhieuNhapHang_Ctrl` | Gắn người tạo phiếu nhập | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` + mapper | Đã sửa | Nên chuyển xuống `PhieuNhapService` ở bước sau |
| 8 | `LapPhieuDoiHang_Ctrl` | Gắn người xử lý phiếu đổi | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` + mapper | Đã sửa | Nghiệp vụ rủi ro cao, chưa refactor sâu |
| 9 | `LapPhieuTraHang_Ctrl` | Gắn người xử lý phiếu trả | `DangNhap_Ctrl.user` | `SessionContext.getCurrentUser()` + mapper | Đã sửa | Nghiệp vụ rủi ro cao, chưa refactor sâu |
| 10 | `ChiTietHoaDon_Ctrl` | Hiển thị/fallback tên nhân viên khi in | `DangNhap_Ctrl.user.getTenNV()` | `SessionContext.getCurrentUser().getFullName()` | Đã sửa | Chỉ phục vụ hiển thị |
| 11 | `DangNhap_GUI`, `CuaSoChinh_*`, `caiDat_Ctrl` | Điều hướng/mở lại màn đăng nhập | Tham chiếu lớp `DangNhap_Ctrl` | Giữ nguyên | Chưa cần đổi | Đây không còn là phụ thuộc session, chỉ là điều hướng UI |

## File đã tạo
- `src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContext.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/session/SessionContext.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/session/LoginResult.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContextMapper.java`

## File đã sửa
- `src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/ConnectDB.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/service/AuthService.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/DangNhap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CuaSoChinh_QuanLy_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CuaSoChinh_NhanVien_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/ChiTietHoaDon_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDatHang/LapPhieuDatHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuNhapHang/LapPhieuNhapHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDoi/LapPhieuDoiHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuTra/LapPhieuTraHang_Ctrl.java`

## Kết quả quét lại sau refactor
- Không còn `DangNhap_Ctrl.user`.
- `ConnectDB` không còn import `DangNhap_Ctrl`.
- `ConnectDB` không còn `CONTEXT_INFO` trong mã Java.
- Không có DAO nào import controller đăng nhập.
- Không có service mới nào phụ thuộc JavaFX cho phần session.
- Vẫn còn các tham chiếu tới lớp `DangNhap_Ctrl` để mở màn login hoặc wiring view:
  - `DangNhap_GUI`
  - `CuaSoChinh_QuanLy_Ctrl`
  - `CuaSoChinh_NhanVien_Ctrl`
  - `caiDat_Ctrl`
  - Đây là phụ thuộc điều hướng UI, không còn là nguồn session toàn cục.

## Nghiệp vụ cần truyền `UserContext` ở bước sau
- `HoaDonService.lapHoaDon(request, userContext)`
- `PhieuNhapService.taoPhieuNhap(request, userContext)`
- `PhieuDatHangService.taoPhieuDat(request, userContext)`
- `DoiTraService.lapPhieuDoi(...) / lapPhieuTra(...)`
- Các luồng cập nhật dữ liệu đang phụ thuộc trigger/audit:
  - cập nhật giá
  - cập nhật tồn kho
  - cập nhật thuốc
  - cập nhật hoạt chất
  - các màn danh mục cần ghi log người thao tác

## Ghi chú quan trọng về `CONTEXT_INFO`
- `SQL/QuanLyNhaThuoc.sql` vẫn có trigger audit dựa vào `CONTEXT_INFO()`.
- Sau bước này, `ConnectDB` không còn tự bơm `MaNV` vào connection.
- Điều này làm kiến trúc đúng tầng hơn, nhưng các trigger audit hiện tại cần được thiết kế lại ở bước sau.
- Hướng xử lý phù hợp ở giai đoạn client-server:
  - đưa user vào request/service layer
  - nếu còn cần audit DB-level, dùng cơ chế context riêng ở server-side transaction boundary
  - hoặc chuyển hẳn audit lên service/domain layer thay vì để DB đọc state từ UI

## Checklist test thủ công sau khi sửa session
- Đăng nhập đúng tài khoản.
- Đăng nhập sai mật khẩu.
- Đăng nhập tài khoản không tồn tại.
- Sau đăng nhập thành công, `SessionContext.getCurrentUser()` khác `null`.
- `UserContext` không chứa password.
- Màn hình chính hiển thị đúng tên nhân viên.
- Popup thông tin người dùng hiển thị đúng vai trò.
- Lập hóa đơn vẫn lấy đúng mã nhân viên.
- Lập phiếu nhập vẫn lấy đúng người tạo.
- Lập phiếu đặt vẫn lấy đúng người tạo.
- Lập phiếu đổi/trả vẫn lấy đúng người xử lý.
- Logout ở cửa sổ chính xóa `SessionContext`.
- Sau logout không còn `currentUser`.
- `ConnectDB` vẫn kết nối DB bình thường.
- Không còn lỗi `null user` phát sinh từ `ConnectDB`.

## Những gì chưa làm trong bước này
- Chưa đổi JDBC sang JPA/Hibernate.
- Chưa đổi SQL Server sang MariaDB.
- Chưa đổi SQL sang JPQL.
- Chưa chuyển toàn bộ nghiệp vụ lớn xuống service.
- Chưa thiết kế request object đầy đủ cho `HoaDon`, `PhieuNhap`, `PhieuDat`, `PhieuDoi`, `PhieuTra`.
- Chưa thay thế chiến lược audit `CONTEXT_INFO` trong SQL Server.

## Kết luận
- Bước 5 đã tách session khỏi UI đăng nhập và khỏi `ConnectDB`.
- `DangNhap_Ctrl` không còn giữ static user toàn cục.
- `ConnectDB` đã trở lại đúng vai trò tầng kết nối.
- JavaFX monolith hiện tại dùng `SessionContext` như một context client-side tạm thời.
- Bước tiếp theo nên là đẩy các nghiệp vụ lớn đang lấy `UserContext` trong controller xuống service/request object, đặc biệt `LapHoaDon`, `LapPhieuNhap`, `LapPhieuDat`, `LapPhieuDoi`, `LapPhieuTra`.
