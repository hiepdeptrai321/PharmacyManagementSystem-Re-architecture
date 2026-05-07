# STEP 13 - Group 4 Migration

## Muc tieu

Migrate nhom nghiep vu:

- LapPhieuNhapHang
- LapPhieuDatHang
- TKPhieuNhapHang
- TKPhieuDatHang
- CapNhatSoLuong
- phan cong ton, duyet dat hang, cap nhat so luong

theo huong:

JavaFX client -> client service/RMI -> server service -> repository MariaDB

## Ket qua dat duoc

### Client

Da migrate cac controller Group 4 sang `pharmacy-client` va bo goi DAO truc tiep:

- `CapNhatSoLuongThuoc_Ctrl`
- `SuaSoLuongThuoc_Ctrl`
- `TimKiemPhieuNhap_Ctrl`
- `ChiTietPhieuNhap_Ctrl`
- `TKPhieuDatHang_Ctrl`
- `ChiTietPhieuDatHang_Ctrl`
- `LapPhieuNhapHang_Ctrl`
- `LapPhieuDatHang_Ctrl`
- `ThemThuoc_LapPhieuNhapHang_Ctrl`

### Common

Da them remote interface dung cho Group 4:

- `PhieuNhapRemote`
- `PhieuDatHangRemote`
- `TonKhoRemote`

Va cac model lien quan da `Serializable`:

- `PhieuNhap`
- `ChiTietPhieuNhap`
- `PhieuDatHang`
- `ChiTietPhieuDatHang`

### Server

Da them server-side implementation cho Group 4:

- `JdbcPurchaseOrderRepository`
- `JdbcPhieuDatHangRepository`
- `JdbcTonKhoRepository`
- `PhieuNhapServiceImpl`
- `PhieuDatHangServiceImpl`
- `TonKhoServiceImpl`
- RMI adapters cho `PhieuNhap`, `PhieuDatHang`, `TonKho`

## Build

Da chay:

```powershell
mvn -q -DskipTests compile
mvn clean install
```

Tai `pharmacy-parent/`

Ket qua:

- `BUILD SUCCESS`

## Kiem tra dependency tang

Da scan lai sau migration:

- Group 4 trong `pharmacy-client` khong con `dao`, `ConnectDB`, `DriverManager`, `PreparedStatement`, `ResultSet`, `EntityManager`
- `pharmacy-common` khong co `javafx`, `jakarta.persistence`, `repository`, `controller`
- `pharmacy-server` khong import `pharmacy-client` hay JavaFX UI trong phan Group 4 moi

## Luu y / TODO

1. `LapPhieuNhapHang` da migrate luu phieu nhap qua `PhieuNhapService`, nhung danh muc thuoc nguon hien van di qua `ThuocService` cua batch truoc.
   Dieu nay giu duoc luong UI moi, nhung chua phai MariaDB-pure cho master data thuoc.

2. `LapPhieuDatHang` da migrate tao phieu dat va duyet dat hang qua service moi.
   Nut `Dat hang va in phieu` hien tai van luu phieu va hien thong bao, chuc nang in se noi tiep sau.

3. `ChiTietPhieuDatHang_Ctrl` da bo goi `CuaSoChinh_QuanLy_Ctrl`.
   Luong lap hoa don tu phieu dat se duoc noi tiep o Group 5.

4. `NhapHangBangExcel` chua migrate trong batch nay.
   Nut tren man nhap hang hien dang thong bao TODO ro rang, khong con phu thuoc class cu chua migrate.

5. `ThemThuoc_LapPhieuNhapHang_Ctrl` duoc giu o muc toi thieu de khong vo UI.
   Popup nay chua noi lai day du luong thiet lap chi tiet don vi tinh.

## Huong test tay de xac nhan

1. Dang nhap vao client moi.
2. Mo man `CapNhatSoLuongThuoc`:
   - xem danh sach lo thuoc
   - sua so luong mot lo
3. Mo man `TKPhieuNhapHang`:
   - xem danh sach phieu nhap
   - mo chi tiet phieu nhap
4. Mo man `TKPhieuDatHang`:
   - xem danh sach phieu dat
   - mo chi tiet phieu dat
   - thu duyet phieu dat
5. Mo man `LapPhieuDatHang`:
   - tim/chon khach hang
   - them thuoc bang o tim kiem
   - dat hang
6. Mo man `LapPhieuNhapHang`:
   - chon nha cung cap
   - them thuoc bang o tim kiem
   - nhap NSX/HSD/so luong/gia nhap
   - luu phieu nhap

## Buoc tiep theo de nghi

Sau Group 4, huong hop ly nhat la:

- Group 5: `HoaDon + BanHang`
- sau do moi toi:
  - `DoiTra`
  - `ThongKe + HoatDong`

