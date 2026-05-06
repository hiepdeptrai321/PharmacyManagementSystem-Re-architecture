# Step 4 - Controller/Service Refactor

## 1. Mục tiêu bước 4

- Tách controller JavaFX khỏi DAO trong monolith hiện tại.
- Giữ nguyên DAO JDBC cũ, `ConnectDB`, SQL Server và hành vi nghiệp vụ hiện có.
- Đưa luồng gọi tạm thời về dạng:

```text
JavaFX Controller
    -> Service
    -> DAO JDBC cũ
    -> ConnectDB
    -> SQL Server
```

## 2. Trước và sau refactor

### Trước refactor

```text
JavaFX Controller
    -> DAO
    -> ConnectDB
    -> SQL Server
```

### Sau refactor tạm thời

```text
JavaFX Controller
    -> Monolith Service
    -> DAO JDBC cũ
    -> ConnectDB
    -> SQL Server
```

## 3. Phạm vi đã làm trong bước này

- Đã quét toàn bộ `controller/**` theo các pattern:
  - `import ...dao...`
  - `new Xxx_Dao()`
  - `ConnectDB`
  - `PreparedStatement`
  - `ResultSet`
  - `Connection`
- Tổng số controller Java: `67`
- Số controller vẫn còn gọi DAO/DB trực tiếp sau đợt refactor này: `48`
- Số controller đã sạch DAO trực tiếp trong đợt này: `14`
- Số controller còn gọi `ConnectDB`/`PreparedStatement`/`ResultSet` trực tiếp: `1`
  - `CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java`

## 4. Service đã tạo

- `AuthService`
- `KhachHangService`
- `KeHangService`
- `NhaCungCapService`
- `CaiDatService`

Ghi chú:
- Các service mới tạo trong bước này không import `javafx.*`.
- Package `service` hiện có sẵn 2 class cũ vẫn import JavaFX property:
  - `service/DoiHangItem.java`
  - `service/TraHangItem.java`
- Đây là di sản cũ, chưa chỉnh ở bước này.

## 5. Mapping Controller -> Service -> DAO

| STT | Controller | Màn hình/Chức năng | DAO cũ đang gọi | Service mới | Trạng thái | Rủi ro | Ghi chú |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | `DangNhap_Ctrl` | Đăng nhập | `NhanVien_Dao` | `AuthService` | Đã tách | Trung bình | Controller đã gọi `AuthService.login()`, vẫn giữ `DangNhap_Ctrl.user` để không phá luồng cũ. |
| 2 | `DanhMucKhachHang_Ctrl` | Danh mục khách hàng | `KhachHang_Dao` | `KhachHangService` | Đã tách | Thấp | Load danh sách và tìm kiếm đã đi qua service. |
| 3 | `ThemKhachHang_Ctrl` | Thêm khách hàng | `KhachHang_Dao` | `KhachHangService` | Đã tách | Thấp | Validate UI giữ ở controller, insert chuyển xuống service. |
| 4 | `ChiTietKhachHang_Ctrl` | Chi tiết/Sửa/Xóa khách hàng | `KhachHang_Dao` | `KhachHangService` | Đã tách | Trung bình | Save/delete đi qua service, không đổi logic soft delete cũ. |
| 5 | `TimKiemKhachHang_Ctrl` | Tìm kiếm khách hàng | `KhachHang_Dao` | `KhachHangService` | Đã tách | Thấp | Tìm kiếm theo mã/tên/email/SDT đi qua service. |
| 6 | `TimKiemKhachHangTrongHD_Ctrl` | Chọn khách hàng trong hóa đơn | `KhachHang_Dao` | `KhachHangService` | Đã tách | Trung bình | Chuẩn bị tốt hơn cho luồng `HoaDonService` ở bước sau. |
| 7 | `DanhMucKeHang_Ctrl` | Danh mục kệ hàng | `KeHang_Dao` | `KeHangService` | Đã tách | Thấp | Load và tìm kiếm kệ đã đi qua service. |
| 8 | `ThemKe_Ctrl` | Thêm kệ hàng | `KeHang_Dao` | `KeHangService` | Đã tách | Thấp | Sinh mã và insert đi qua service. |
| 9 | `XoaSuaKeHang_Ctrl` | Sửa/Xóa kệ hàng | `KeHang_Dao`, `Thuoc_SanPham_Dao` | `KeHangService` | Đã tách | Trung bình | Service đang bọc cả kiểm tra thuốc còn nằm trong kệ trước khi xóa. |
| 10 | `DanhMucNhaCungCap_Ctrl` | Danh mục nhà cung cấp | `NhaCungCap_Dao` | `NhaCungCapService` | Đã tách | Thấp | Load và tìm kiếm đi qua service. |
| 11 | `ThemNhaCungCap_Ctrl` | Thêm nhà cung cấp | `NhaCungCap_Dao` | `NhaCungCapService` | Đã tách | Thấp | Validate UI giữ ở controller, insert chuyển xuống service. |
| 12 | `SuaXoaNhaCungCap_Ctrl` | Sửa/Xóa nhà cung cấp | `NhaCungCap_Dao` | `NhaCungCapService` | Đã tách | Trung bình | Update/delete đi qua service; refresh bảng giữ nguyên. |
| 13 | `TimKiemNCC_Ctrl` | Tìm kiếm nhà cung cấp | `NhaCungCap_Dao` | `NhaCungCapService` | Đã tách | Thấp | Cụm tìm kiếm NCC đã sạch DAO trực tiếp. |
| 14 | `caiDat_Ctrl` | Cài đặt hệ thống | `CaiDat_Dao` | `CaiDatService` | Đã tách | Thấp | Logic tính thuế/ngày giữ ở controller, cập nhật dữ liệu đi qua service. |
| 15 | `ChiTietNhaCungCap_Ctrl` | Xem chi tiết nhà cung cấp | Không gọi DAO trực tiếp | Không cần thêm service ở bước này | Đã sạch từ trước | Thấp | Controller chỉ hiển thị dữ liệu được truyền vào. |
| 16 | `LapHoaDon_Ctrl` | Lập hóa đơn | `Thuoc_SP_TheoLo_Dao`, `Thuoc_SanPham_Dao`, `ChiTietKhuyenMai_Dao`, `KhachHang_Dao`, `PhieuDatHang_Dao`, `ChiTietPhieuDatHang_Dao`, `DonViTinh_Dao`, `ConnectDB` | `HoaDonService` | Chưa tách | Cao | Còn `Connection`, `PreparedStatement`, `ResultSet`, `ConnectDB` trực tiếp; cần tách rất cẩn thận. |
| 17 | `LapPhieuNhapHang_Ctrl` | Lập phiếu nhập | `ChiTietPhieuNhap_Dao`, `PhieuNhap_Dao`, `ChiTietDonViTinh_Dao`, `NhaCungCap_Dao`, `Thuoc_SanPham_Dao` | `PhieuNhapService` | Chưa tách | Cao | Nghiệp vụ nhiều DAO, chưa nên refactor sâu khi chưa có test tay. |
| 18 | `LapPhieuDoiHang_Ctrl` | Lập phiếu đổi | `HoaDon_Dao`, `ChiTietHoaDon_Dao`, `KhachHang_Dao`, `ChiTietPhieuDoiHang_Dao`, `Thuoc_SP_TheoLo_Dao`, `PhieuDoiHang_Dao` | `DoiTraService` | Chưa tách | Cao | Dính transaction và tồn kho theo lô. |
| 19 | `LapPhieuTraHang_Ctrl` | Lập phiếu trả | `HoaDon_Dao`, `ChiTietHoaDon_Dao`, `KhachHang_Dao`, `ChiTietPhieuTraHang_Dao`, `PhieuTraHang_Dao`, `Thuoc_SP_TheoLo_Dao` | `DoiTraService` | Chưa tách | Cao | Dính transaction và đối chiếu hóa đơn gốc. |
| 20 | `ThongKeBanHang_Ctrl` | Thống kê bán hàng | `ThongKe_Dao` | `ThongKeService` | Chưa tách | Cao | Query báo cáo/stored procedure, nên làm sau cùng. |
| 21 | `DanhMucThuoc_Ctrl` | Danh mục thuốc | `Thuoc_SanPham_Dao` và DAO liên quan | `ThuocService` | Chưa tách | Trung bình | Có nhiều join và nhiều màn phụ thuộc. |
| 22 | `DanhMucNhanVien_Ctrl` | Danh mục nhân viên | `NhanVien_Dao` | `NhanVienService` | Chưa tách | Trung bình | Nên đi sau `AuthService`. |

## 6. Controller đã tách DAO thành công

- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/DangNhap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhachHang/DanhMucKhachHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhachHang/ThemKhachHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhachHang/ChiTietKhachHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKKhachHang/TimKiemKhachHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKKhachHang/TimKiemKhachHangTrongHD_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKeHang/DanhMucKeHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKeHang/ThemKe_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKeHang/XoaSuaKeHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhaCungCap/DanhMucNhaCungCap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhaCungCap/ThemNhaCungCap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhaCungCap/SuaXoaNhaCungCap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKNhaCungCap/TimKiemNCC_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/CaiDat/caiDat_Ctrl.java`

## 7. Controller còn gọi DAO trực tiếp

### Nhóm trung bình

- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatGia/CapNhatGiaThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatGia/SuaGiaThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatGia/ThemDVT_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatGia/ThietLapDonViTinh_SuaXoa_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatGia/ThietLapDonViTinh_Them_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatKhuyenMai/CapNhatKhuyenMai_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatKhuyenMai/SuaKhuyenMai_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatSoLuong/CapNhatSoLuongThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatSoLuong/SuaSoLuongThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhuyenMai/DanhMucKhuyenMai_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhuyenMai/ThemKhuyenMai_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhanVien/DanhMucNhanVien_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhanVien/SuaXoaNhanVien_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhanVien/ThemNhanVien_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhanVien/ThemTaiKhoan_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhomDuocLy/DanhMucNhomDuocLy_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhomDuocLy/SuaXoaNhomDuocLy.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMNhomDuocLy/ThemNhomDuocLy_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMThuoc/DanhMucThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMThuoc/SuaXoaThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMThuoc/ThemThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMThuoc/ThemThuocBangFileExcel.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKThuoc/ChiTietThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKThuoc/TimKiemThuoc_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKThuocTrongKho/TimKiemThuocTrongKho_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CuaSoChinh_NhanVien_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CuaSoChinh_QuanLy_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/TrangChu_Ctrl.java`

### Nhóm cao

- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeBanHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeTopSanPham_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_ThongKe/ThongKeXNT_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/ChiTietHoaDon_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoaDon/TimKiemHoaDon_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKHoatDong/TKHoatDong_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDatHang/ChiTietPhieuDatHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDatHang/TKPhieuDatHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/ChiTietPhieuDoiHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuDoiHang/TKPhieuDoiHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuNhapHang/ChiTietPhieuNhap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuNhapHang/TimKiemPhieuNhap_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/ChiTietPhieuTraHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_TimKiem/TKPhieuTraHang/TKPhieuTraHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDatHang/LapPhieuDatHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuDoi/LapPhieuDoiHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuNhapHang/LapPhieuNhapHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuNhapHang/ThemThuoc_LapPhieuNhapHang_Ctrl.java`
- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapPhieuTra/LapPhieuTraHang_Ctrl.java`

## 8. Controller còn gọi DB trực tiếp

- `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_XuLy/LapHoaDon/LapHoaDon_Ctrl.java`

Ghi chú:
- Đây là controller duy nhất còn gọi `ConnectDB`, `Connection`, `PreparedStatement`, `ResultSet` trực tiếp trong lần quét sau refactor.
- Màn này phải được tách thành `HoaDonService` ở một bước riêng, có test tay kỹ.

## 9. Những chỗ rủi ro cao chưa nên refactor sâu ở bước này

- `LapHoaDon_Ctrl`: đang tự điều phối nhiều DAO, tự thao tác JDBC và tự xử lý transaction thủ công.
- `LapPhieuNhapHang_Ctrl`: nghiệp vụ nhập hàng nhiều DAO và có tạo mã lô.
- `LapPhieuDoiHang_Ctrl` và `LapPhieuTraHang_Ctrl`: liên quan hóa đơn gốc, tồn kho theo lô, cập nhật trạng thái.
- `ThongKe*`: query phức tạp, stored procedure, projection tổng hợp.
- `CuaSoChinh_*` và `TrangChu_Ctrl`: đang kéo dữ liệu dashboard từ DAO thống kê/tồn kho.

## 10. Checklist test thủ công sau refactor

### Đăng nhập

- [ ] Đăng nhập đúng tài khoản.
- [ ] Đăng nhập sai mật khẩu.
- [ ] Đăng nhập với ô trống.
- [ ] Kiểm tra nhớ tài khoản/mật khẩu.
- [ ] Kiểm tra `DangNhap_Ctrl.user` vẫn được gán đúng sau đăng nhập.

### Khách hàng

- [ ] Mở màn hình danh mục khách hàng.
- [ ] Load danh sách khách hàng.
- [ ] Tìm kiếm theo mã/tên.
- [ ] Mở chi tiết khách hàng.
- [ ] Thêm khách hàng mới.
- [ ] Sửa khách hàng.
- [ ] Xóa khách hàng.
- [ ] Mở tìm kiếm khách hàng độc lập.
- [ ] Mở tìm kiếm khách hàng trong hóa đơn và chọn một dòng.

### Kệ hàng

- [ ] Mở danh mục kệ hàng.
- [ ] Load danh sách kệ.
- [ ] Tìm kiếm kệ.
- [ ] Thêm kệ mới.
- [ ] Sửa kệ.
- [ ] Xóa kệ rỗng.
- [ ] Thử xóa kệ còn chứa thuốc để kiểm tra cảnh báo.

### Nhà cung cấp

- [ ] Mở danh mục nhà cung cấp.
- [ ] Load danh sách nhà cung cấp.
- [ ] Tìm kiếm nhà cung cấp.
- [ ] Thêm nhà cung cấp mới.
- [ ] Sửa nhà cung cấp.
- [ ] Xóa nhà cung cấp.
- [ ] Mở màn hình tìm kiếm nhà cung cấp.
- [ ] Mở chi tiết nhà cung cấp.

### Cài đặt

- [ ] Mở màn hình cài đặt.
- [ ] Load thông số thuế và ngày hết hạn.
- [ ] Thử lưu khi dữ liệu hợp lệ.
- [ ] Thử lưu khi dữ liệu không hợp lệ.
- [ ] Thử hủy thao tác.
- [ ] Kiểm tra luồng hỏi restart ứng dụng sau khi lưu.

## 11. Kết luận tạm thời của bước 4

- Đã dựng service layer monolith đầu tiên cho nhóm rủi ro thấp và đăng nhập.
- Các controller đã refactor hiện không còn import DAO trực tiếp.
- Build Maven vẫn qua với `mvn -q -DskipTests compile`.
- Chưa thay đổi database, DAO JDBC, `ConnectDB`, query SQL hay nghiệp vụ lớn.
- Bước tiếp theo hợp lý là tiếp tục tách nhóm trung bình:
  - `NhomDuocLy`
  - `DonViTinh`
  - `NhanVien`
  - `Thuoc`
  - sau đó mới đến `HoaDon` và `PhieuNhap`.
