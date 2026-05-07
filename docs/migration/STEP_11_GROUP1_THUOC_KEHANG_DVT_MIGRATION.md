# STEP 11 - Group 1 Migration (DonViTinh + Thuoc + KeHang)

## Muc tieu

Thuc hien batch dau tien cua nhom migrate:

- `DonViTinh`
- `Thuoc`
- `KeHang`
- `TKThuoc`
- `TKThuocTrongKho`
- phan lien quan `ChiTietDonViTinh`

Huong thuc hien van giu dung mau da chot o Step 9:

`JavaFX controller -> client service -> RMI stub -> remote interface (common) -> server service -> persistence`

Tuy nhien, de giam rui ro cho cum `Thuoc`, dot nay dung mo hinh chuyen tiep:

`JavaFX controller -> client service -> RMI -> server ThuocService -> legacy DAO (server-only)`

Nghia la client da sach khoi DAO/JDBC, con server tam thoi van duoc phep goi DAO cu ben trong service cho den khi JPA `Thuoc` du san sang.

## Tai lieu da doi chieu truoc khi lam

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`
- `docs/migration/STEP_4_CONTROLLER_SERVICE_REFACTOR.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`
- `docs/migration/STEP_9_JPA_RMI_LOGIN_POC.md`
- `docs/migration/STEP_10_MASTERDATA_RMI_MIGRATION.md`
- `docs/migration/MIGRATION_PRIORITY.md`

## Pham vi da migrate

### 1. KeHang

`KeHang` da duoc migrate full-stack theo huong `common -> server -> client`:

- `pharmacy-common`
  - `KeHangRemote`
  - `BusinessCodeType.KE_HANG`
  - `KeHang` da `Serializable`
- `pharmacy-server`
  - `KeHangEntity`
  - `KeHangRepository`
  - `JpaKeHangRepository`
  - `KeHangService`
  - `KeHangServiceImpl`
  - `KeHangRemoteAdapter`
  - bind trong `RmiServerBootstrap`
- `pharmacy-client`
  - `KeHangClientService`
  - `RmiKeHangClientService`
  - wrapper `com.example.pharmacymanagementsystem_qlht.service.KeHangService`
  - UI da dua vao module moi:
    - `DanhMucKeHang_Ctrl`
    - `ThemKe_Ctrl`
    - `XoaSuaKeHang_Ctrl`
    - cac `..._GUI` tuong ung

### 2. DonViTinh

Phan service/RMI cua `DonViTinh` da co tu dot truoc. Dot nay tiep tuc migrate UI helper lien quan don vi tinh sang `pharmacy-client`:

- `ThemDVT_Ctrl`
- `ThietLapDonViTinh_Them_Ctrl`
- `ThietLapDonViTinh_SuaXoa_Ctrl`
- cac GUI Java thuong ung trong `view/CN_CapNhat/CapNhatGia`

Nhung thay doi chinh:

- bo `DonViTinh_Dao` khoi client controller
- bo `Thuoc_SanPham_Dao` khoi 2 man thiet lap don vi tinh
- controller chi con goi `DonViTinhService`
- khi can tham chieu thuoc, controller chi gan `maThuoc` vao object nhe thay vi tu tu load DAO tai client

### 3. Thuoc

`Thuoc` la cum phuc tap nhat cua Nhóm 1, nen dot nay migrate theo huong trung gian an toan:

#### pharmacy-common

Da them:

- `ThuocRemote`

Da cap nhat `Serializable` cho model duoc truyen qua RMI:

- `Thuoc_SanPham`
- `Thuoc_SP_TheoLo`
- `ThuocTonKho`
- `LoaiHang`
- `HoatChat`
- `ChiTietHoatChat`
- `ChiTietDonViTinh`

#### pharmacy-server

Da them:

- `ThuocService`
- `ThuocServiceImpl`
- `ThuocRemoteAdapter`

Luu y quan trong:

- `ThuocServiceImpl` hien **chua** dung JPA cho `Thuoc`
- service nay dang goi cac legacy DAO ben trong server:
  - `Thuoc_SanPham_Dao`
  - `Thuoc_SP_TheoLo_Dao`
  - `ChiTietHoatChat_Dao`
  - `LoaiHang_Dao`
  - `HoatChat_Dao`
  - `DonViTinh_Dao`
  - `ChiTietDonViTinh_Dao`
- day la bridge chuyen tiep co chu dich de:
  - khong cho client truy cap DB
  - khong pha vo nghiep vu cu
  - van cho phep UI moi goi qua RMI/service

#### pharmacy-client

Da them:

- `ThuocClientService`
- `RmiThuocClientService`
- wrapper `com.example.pharmacymanagementsystem_qlht.service.ThuocService`

Da migrate UI/controller thuc te:

- `CN_DanhMuc/DMThuoc`
  - `DanhMucThuoc_Ctrl`
  - `ThemThuoc_Ctrl`
  - `SuaXoaThuoc_Ctrl`
  - cac GUI Java tuong ung
- `CN_TimKiem/TKThuoc`
  - `TimKiemThuoc_Ctrl`
  - `ChiTietThuoc_Ctrl`
  - cac GUI Java tuong ung
- `CN_TimKiem/TKThuocTrongKho`
  - `TimKiemThuocTrongKho_Ctrl`
  - `TKThuocTrongKho_GUI`

Nhung thay doi chinh trong controller:

- bo DAO truc tiep khoi client
- danh muc thuoc load qua `ThuocService.findAll()`
- them/sua/xoa thuoc goi:
  - `ThuocService.create(...)`
  - `ThuocService.update(...)`
  - `ThuocService.softDelete(...)`
- tim kiem thuoc trong kho goi:
  - `ThuocService.getAllTheoLo()`
  - `ThuocService.getThuocTonKho()`
  - `ThuocService.getTenDVTByMaThuoc()`
- chi tiet thuoc load hoat chat qua:
  - `ThuocService.findChiTietHoatChatByMaThuoc(...)`

### 4. ChiTietDonViTinh lien quan

Dot nay chua tach `ChiTietDonViTinh` thanh mot module rieng day du, nhung da chuyen duoc cac diem lien quan den `Thuoc`:

- khi tao thuoc moi, client gui `maDonViTinhCoBan`
- server `ThuocServiceImpl` se tao dong `ChiTietDonViTinh` co ban voi:
  - he so quy doi `1.0`
  - gia nhap `0`
  - gia ban `0`
  - `donViCoBan = true`

## Bang mapping chinh

| STT | Cum chuc nang | Huong di moi | Trang thai | Ghi chu |
|---|---|---|---|---|
| 1 | KeHang | JavaFX -> RMI -> JPA service -> MariaDB | Da migrate | Full-stack theo mau Step 9 |
| 2 | DonViTinh service | JavaFX -> RMI -> JPA service -> MariaDB | Da co tu dot truoc | Dot nay bo sung UI helper |
| 3 | DonViTinh UI helper | JavaFX -> client service -> RMI -> server service | Da migrate | Khong con `DonViTinh_Dao` o client |
| 4 | DMThuoc | JavaFX -> client `ThuocService` -> RMI -> server `ThuocServiceImpl` -> legacy DAO | Da migrate | Client da sach DB, server van la bridge |
| 5 | TKThuoc | JavaFX -> client `ThuocService` -> RMI -> server `ThuocServiceImpl` -> legacy DAO | Da migrate | Tim kiem va xem chi tiet qua remote |
| 6 | TKThuocTrongKho | JavaFX -> client `ThuocService` -> RMI -> server `ThuocServiceImpl` -> legacy DAO | Da migrate | Ho tro theo lo / theo san pham |
| 7 | Import thuoc bang Excel | Chua migrate | TODO | Tam thoi hien thong bao placeholder trong `DanhMucThuoc_Ctrl` |

## Kiem tra dependency sau migrate

Da quet lai `pharmacy-client/src/main/java` bang PowerShell sau khi patch:

Khong con phat hien:

- `com.example.pharmacymanagementsystem_qlht.dao`
- `new Xxx_Dao(...)`
- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `EntityManager`
- `jakarta.persistence`
- `org.hibernate`
- import `com.example.pharmacy.server`

Da quet `pharmacy-common`:

Khong con phat hien:

- `javafx`
- `jakarta.persistence`
- `javax.persistence`
- `org.hibernate`
- `ConnectDB`
- `dao`
- `controller`

Da quet `pharmacy-server`:

- khong co import `controller/view/FXML/Stage/Scene` trong cac phan moi cua Nhóm 1
- van con technical debt cu:
  - `ThongKe_Dao.java`
  - `ThongKeXNT_Dao.java`
  - 2 file nay van import `javafx.collections.ObservableList`

## SQL / code sequence

Da cap nhat:

- `SQL/alter_add_code_sequence_mariadb.sql`

De bo sung `code_sequence` cho:

- `KE_HANG`

Dong bo `current_value` dua tren `KeHang.MaKe` theo format `KE...`

## Kiem tra build

Da chay thanh cong:

- `mvn -q -DskipTests compile` tai `pharmacy-parent`
- `mvn clean install` tai `pharmacy-parent`

Ket qua:

- `BUILD SUCCESS`

## Rui ro / ghi chu can luu y

1. `Thuoc` hien chua len JPA that su.

Dieu nay la co chu dich. Client da dung kien truc moi, nhung server van bridge qua DAO cu de:

- giam rui ro voi nghiep vu thuoc/phoi hop hoat chat
- tranh sua qua rong khi `ChiTietDonViTinh`, `Thuoc_SP_TheoLo`, `LoHang` chua duoc mapping JPA day du

2. `ThemThuocBangFileExcel` chua migrate.

Hien tai `DanhMucThuoc_Ctrl.btnThemThuocByExcel()` chi hien thong bao TODO de giu build xanh va khong keo them dependency import Excel vao dot nay.

3. `TKThuocTrongKho` da chay qua remote, nhung van phu thuoc vao server-side legacy ton kho.

Day la buoc chuyen tiep hop ly truoc khi migrate tiep:

- `PhieuNhap`
- `CapNhatSoLuong`
- `LoHang`

## Nhung phan con lai cua Nhóm 1

Sau dot nay, Nhóm 1 da dat duoc muc:

- `KeHang`: full-stack
- `DonViTinh`: service full + UI helper da dua sang client moi
- `Thuoc`: client clean + remote/service bridge o server
- `TKThuoc`: da len client moi
- `TKThuocTrongKho`: da len client moi

Nhung van con viec nen lam tiep de ket thuc Nhóm 1 tron ven hon:

1. Tach rieng import Excel thuoc sang mot batch nho.
2. JPA hoa `Thuoc`, `Thuoc_SP_TheoLo`, `ChiTietHoatChat`, `ChiTietDonViTinh` phia server.
3. Kiem thu runtime end-to-end:
   - `server RMI`
   - `client login`
   - mo `DMThuoc`
   - them thuoc
   - sua/xoa thuoc
   - `TKThuoc`
   - `TKThuocTrongKho`

## Buoc tiep theo de xuat

Sau khi chot Nhóm 1, thu tu hop ly nhat la:

1. Hoan thien runtime test cho `Thuoc` / `KeHang` / `DonViTinh`
2. Chuyen sang `NhanVien + TaiKhoan + CaiDat`
3. Hoac neu uu tien nghiep vu kho truoc thi di tiep:
   - `PhieuNhap + PhieuDatHang + CapNhatSoLuong`

Khuyen nghi thuc te:

- neu muon giam no kỹ thuât truoc: hoan thien `Thuoc` JPA
- neu muon mo duong cho nghiep vu kho/ban hang: sang ngay `PhieuNhap + DatHang`
