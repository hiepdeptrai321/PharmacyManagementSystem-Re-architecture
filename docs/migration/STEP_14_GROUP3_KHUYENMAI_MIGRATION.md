# STEP 14 - GROUP 3 KHUYEN MAI MIGRATION

## Muc tieu

Migrate nhom `KhuyenMai + CapNhatKhuyenMai` tu monolith cu sang cau truc `pharmacy-common / pharmacy-server / pharmacy-client` theo huong:

`JavaFX Controller -> Client Service / RMI -> Server Service -> Server DAO bridge`

Muc tieu cua batch nay la dua luong quan ly khuyen mai ra khoi client database access, trong khi van giu hanh vi UI gan voi he thong cu de de test hoi quy.

## Tai lieu da doi chieu

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`
- `docs/migration/STEP_11_GROUP1_THUOC_KEHANG_DVT_MIGRATION.md`
- `docs/migration/STEP_12_GROUP2_NHANVIEN_TAIKHOAN_CAIDAT_MIGRATION.md`
- `docs/migration/STEP_13_GROUP4_PHIEUNHAP_PHIEUDATHANG_TONKHO_MIGRATION.md`

## Pham vi da migrate

- `DMKhuyenMai`
- `CN_CapNhat/CapNhatKhuyenMai`
- phan lookup khuyen mai dang hoat dong o muc server/service de tai su dung cho batch `HoaDon + BanHang`

## Kien truc ap dung cho batch nay

```text
DanhMucKhuyenMai_Ctrl / ThemKhuyenMai_Ctrl / CapNhatKhuyenMai_Ctrl / SuaKhuyenMai_Ctrl
    -> com.example.pharmacymanagementsystem_qlht.service.KhuyenMaiService
    -> com.example.pharmacy.client.service.RmiKhuyenMaiClientService
    -> com.example.pharmacy.common.remote.KhuyenMaiRemote
    -> com.example.pharmacy.server.bootstrap.rmi.KhuyenMaiRemoteAdapter
    -> com.example.pharmacy.server.service.KhuyenMaiServiceImpl
    -> KhuyenMai_Dao / LoaiKhuyenMai_Dao / ChiTietKhuyenMai_Dao / Thuoc_SP_TangKem_Dao
```

Luu y: batch nay chua JPA-hoa sau cum `KhuyenMai`. De giam rui ro, server hien tai dung `legacy DAO bridge` tu server-side service, giong chien luoc da dung cho `Thuoc`.

## File/class da tao hoac cap nhat

### pharmacy-common

- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/KhuyenMaiRemote.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/KhuyenMai.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/LoaiKhuyenMai.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/ChiTietKhuyenMai.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacymanagementsystem_qlht/model/Thuoc_SP_TangKem.java`

Thay doi chinh:
- bo sung `Serializable` cho cac model truyen qua RMI
- dinh nghia remote contract danh cho quan ly khuyen mai

### pharmacy-server

- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/KhuyenMaiServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/KhuyenMaiRemoteAdapter.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`

Thay doi chinh:
- expose `KhuyenMaiRemoteService` tren RMI server
- gom logic CRUD khuyen mai ve server service
- sinh `MaKM` qua `CodeGenerationService` va `BusinessCodeType.KHUYEN_MAI`

### pharmacy-client

- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/KhuyenMaiClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiKhuyenMaiClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/KhuyenMaiService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhuyenMai/DanhMucKhuyenMai_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_DanhMuc/DMKhuyenMai/ThemKhuyenMai_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatKhuyenMai/CapNhatKhuyenMai_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/CN_CapNhat/CapNhatKhuyenMai/SuaKhuyenMai_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_DanhMuc/DMKhuyenMai/DanhMucKhuyenMai_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_DanhMuc/DMKhuyenMai/ThemKhuyenMai_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_CapNhat/CapNhatKhuyenMai/CapNhatKhuyenMai_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/CN_CapNhat/CapNhatKhuyenMai/SuaKhuyenMai_GUI.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/ClientHome_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/view/ClientHome_GUI.java`

Thay doi chinh:
- controller client khong con goi `KhuyenMai_Dao`, `LoaiKhuyenMai_Dao`, `ChiTietKhuyenMai_Dao`, `Thuoc_SP_TangKem_Dao`
- UI `Danh muc khuyen mai` va `Cap nhat khuyen mai` da nam trong client module moi
- `ClientHome` co nut mo nhanh 2 man hinh nay de test tren project moi

## Cac luong da duoc dua qua service/server

1. Xem danh sach khuyen mai
2. Tim kiem khuyen mai theo tu khoa
3. Tao khuyen mai moi
4. Sua khuyen mai
5. Xoa khuyen mai
6. Tai danh sach `LoaiKhuyenMai`
7. Tai `ChiTietKhuyenMai` va danh sach qua tang kem
8. Lookup khuyen mai dang hoat dong theo ngay de tai dung cho batch ban hang

## Kiem tra phu thuoc tang

Da kiem tra trong `pharmacy-client` va khong con dau hieu:

- `KhuyenMai_Dao`
- `LoaiKhuyenMai_Dao`
- `ChiTietKhuyenMai_Dao`
- `Thuoc_SP_TangKem_Dao`
- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`

Batch nay giu dung huong phu thuoc:

```text
pharmacy-client  -> pharmacy-common
pharmacy-server  -> pharmacy-common
```

Khong them `client -> server` dependency truc tiep.

## Ket qua build

Da chay:

- `mvn -q -DskipTests compile`
- `mvn clean install`

Tai `pharmacy-parent`, va ket qua la `BUILD SUCCESS`.

## Rui ro / TODO con lai

1. `KhuyenMaiServiceImpl` hien van dung `server-side legacy DAO bridge`, chua chuyen sang JPA chi tiet.
2. Tao/sua khuyen mai dang cap nhat nhieu bang con (`KhuyenMai`, `ChiTietKhuyenMai`, `Thuoc_SP_TangKem`) nhung chua duoc boc lai trong mot transaction end-to-end rieng cua batch nay.
3. `DichVuKhuyenMai`, `ApDungKhuyenMai`, `PhienKhuyenMai` van la helper legacy ben server, chua noi vao luong ban hang moi.
4. Phan ap dung khuyen mai trong man `HoaDon` se duoc noi tiep o batch `HoaDon + BanHang`.
5. Chua test runtime end-to-end trong batch nay; da xac nhan duong di bang compile va wiring RMI.

## Danh gia ket qua

Batch Group 3 dat muc tieu:

- UI `KhuyenMai` da duoc dua vao `pharmacy-client`
- logic khuyen mai chay qua `service/server` thay vi `controller/DAO` cu
- client khong con truy cap database truc tiep cho cum khuyen mai
- da mo san lookup khuyen mai dang hoat dong cho batch ban hang tiep theo

## Buoc tiep theo de xuat

Uu tien tiep theo:

1. `HoaDon + BanHang`
2. noi lookup/ap dung khuyen mai vao luong lap hoa don moi
3. neu can, JPA-hoa sau hon cho cum `KhuyenMai` sau khi luong ban hang da on dinh
