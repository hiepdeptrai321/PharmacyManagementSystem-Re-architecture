# STEP 9 - JPA/Hibernate + RMI Login POC

## Muc tieu buoc 9

Dung POC dang nhap theo dung luong dich:

```text
DangNhap_Ctrl
    -> AuthClientService
    -> RmiClientProvider
    -> AuthRemoteService (AuthRemote)
    -> AuthServiceImpl
    -> JpaNhanVienRepository
    -> JPA/Hibernate
    -> MariaDB
```

Pham vi buoc nay chi gom dang nhap. Khong chuyen cac module CRUD/giao dich khac sang JPA/RMI.

## Tai lieu da doc truoc khi implement

- `docs/migration/STEP_6_MAVEN_MODULE_SPLIT.md`
- `docs/migration/STEP_6_MIGRATION_EXECUTION_RESULT.md`
- `docs/migration/STEP_5_SESSION_CONTEXT_REFACTOR.md`
- `docs/migration/STEP_7_MARIADB_SCHEMA_DESIGN.md`
- `docs/migration/STEP_8_TRIGGER_PROCEDURE_STRATEGY.md`
- `docs/migration/STEP_8_IMPLEMENTATION_RESULT.md`
- `docs/migration/DAO_MIGRATION_MAPPING.md`
- `docs/migration/QUERY_INVENTORY.md`
- `docs/migration/MIGRATION_PRIORITY.md`

## Quyet dinh thiet ke cho POC dang nhap

### 1. Bang dang nhap duoc dung

POC dang nhap su dung truc tiep bang `NhanVien` trong `schema_mariadb.sql`.

Ly do:

- Schema MariaDB hien tai da dat cac cot dang nhap tren cung bang `NhanVien`:
  - `TaiKhoan`
  - `MatKhau`
  - `VaiTro`
  - `TrangThai`
  - `TrangThaiXoa`
- Chua co bang `TaiKhoan` rieng trong schema_mariadb.sql.
- Day la phuong an it rui ro nhat de dung POC JPA nhanh ma khong phai thiet ke lai auth model som hon can thiet.

### 2. Remote interface

POC giu remote interface hien co:

- `com.example.pharmacy.common.remote.AuthRemote`

Binding name duoc chuan hoa thanh:

- `AuthRemoteService`

### 3. Persistence unit

- Ten persistence unit: `pharmacyPU`

## File/class da tao hoac cap nhat

### pharmacy-common

- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/AuthRemote.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/LoginRequest.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/response/LoginResponse.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/UserDTO.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/UserContext.java`
- `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/enums/UserRole.java`

### pharmacy-server

- `pharmacy-parent/pharmacy-server/src/main/resources/META-INF/persistence.xml`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JpaUtil.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/entity/NhanVienEntity.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/NhanVienRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/JpaNhanVienRepository.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuthService.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuthServiceImpl.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/ServerComponentFactory.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/AuthRemoteAdapter.java`

### pharmacy-client

- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/AuthClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiAuthClientService.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/session/SessionContext.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/AuthClientProbe.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/controller/DangNhap_Ctrl.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/session/SessionContext.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContextMapper.java`
- `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacymanagementsystem_qlht/service/AuthService.java`

### SQL

- `SQL/seed_login_poc_mariadb.sql`

## Luong goi dang nhap chi tiet

```text
DangNhap_Ctrl
    -> RmiAuthClientService.login(username, password)
    -> RmiClientProvider.getAuthRemote()
    -> AuthRemote.login(LoginRequest)
    -> AuthRemoteAdapter.login(...)
    -> AuthServiceImpl.login(...)
    -> JpaNhanVienRepository.findByUsername(...)
    -> JpaUtil.createEntityManager()
    -> Hibernate / MariaDB
    -> LoginResponse
    -> SessionContext (client)
```

## Entity va repository cho login

### Entity

`NhanVienEntity`

Mapping toi cac cot can thiet cho login:

- `MaNV`
- `TaiKhoan`
- `MatKhau`
- `TenNV`
- `VaiTro`
- `TrangThai`
- `TrangThaiXoa`

Khong map them quan he chua can dung cho POC.

### Repository

`JpaNhanVienRepository`

Method:

- `Optional<NhanVienEntity> findByUsername(String username)`

Hien dung JPQL:

```text
SELECT nv
FROM NhanVienEntity nv
WHERE nv.taiKhoan = :username
```

## Server bootstrap

### RMI service

- Bind name: `AuthRemoteService`
- Port: `1099`

### JPA/Hibernate

- Persistence unit: `pharmacyPU`
- Dialect: `org.hibernate.dialect.MariaDBDialect`
- Driver: `org.mariadb.jdbc.Driver`
- DB default dev:
  - URL: `jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc`
  - user: `pharmacy_app`
  - password: `123456`

Co the override bang:

- `-Dpharmacy.db.url=...`
- `-Dpharmacy.db.user=...`
- `-Dpharmacy.db.password=...`
- `-Dpharmacy.jpa.ddl-auto=validate|update`
- `-Dpharmacy.jpa.show-sql=true|false`

## Client-side auth

### RMI lookup

`RmiClientProvider`

- host mac dinh: `localhost`
- port mac dinh: `1099`
- binding name: `AuthRemoteService`

### AuthClientService

`RmiAuthClientService`

- tao `LoginRequest`
- goi remote `AuthRemote.login(...)`
- bat `RemoteException`
- tra `LoginResponse.failure(...)` voi message de hieu
- tu dong `establish/clear` session phia client service moi

### DangNhap_Ctrl

Sau cap nhat:

- khong goi DAO
- khong goi `ConnectDB`
- khong tao `Connection`, `PreparedStatement`, `ResultSet`
- chi:
  - lay input
  - validate rong
  - goi `AuthClientService`
  - map `UserDTO -> UserContext`
  - luu vao `SessionContext`
  - mo man hinh chinh

## SessionContext phia client

Hai lop session dang cung ton tai:

1. `com.example.pharmacy.client.session.SessionContext`
   - session holder cho auth client service moi
2. `com.example.pharmacymanagementsystem_qlht.session.SessionContext`
   - session holder legacy de UI hien tai doc duoc ma khong phai rewrite nhieu man hinh

Trong POC dang nhap:

- `RmiAuthClientService` cap nhat session holder moi
- `DangNhap_Ctrl` cap nhat session holder legacy cho UI hien tai

Day la bridge chu dong phia client, khong phai server-side static session.

## Tai khoan test

Tu `SQL/seed_mariadb.sql`:

- `admin / 123`
- `staff / 123`

Bo sung cho POC neu can test account khoa:

- apply them `SQL/seed_login_poc_mariadb.sql`
- tai khoan khoa:
  - `locked / 123`

## Cach chay

### 1. Chay database

Apply schema:

```bash
mariadb --protocol=TCP -h 127.0.0.1 -P 3306 -u root -p < SQL/schema_mariadb.sql
```

Apply seed co ban:

```bash
mariadb --protocol=TCP -h 127.0.0.1 -P 3306 -u root -p quan_ly_nha_thuoc < SQL/seed_mariadb.sql
```

Neu can them account khoa cho POC:

```bash
mariadb --protocol=TCP -h 127.0.0.1 -P 3306 -u root -p quan_ly_nha_thuoc < SQL/seed_login_poc_mariadb.sql
```

Tao user rieng cho app truoc khi chay server:

```bash
mariadb --protocol=TCP -h 127.0.0.1 -P 3306 -u root -p quan_ly_nha_thuoc < SQL/create_pharmacy_app_user.sql
```

Script nay co ca `ALTER USER` de reset lai password auth neu `pharmacy_app` da ton tai tu truoc voi plugin khong phu hop.

### 2. Chay RMI server

Run main class:

- `com.example.pharmacy.server.bootstrap.RmiServerBootstrap`

Server se bind:

- port `1099`
- service name `AuthRemoteService`

### 3. Chay JavaFX client

Run main class:

- `com.example.pharmacymanagementsystem_qlht.controller.DangNhap_Ctrl`

Hoac dung Maven:

```bash
mvn -q -f pharmacy-parent/pharmacy-client/pom.xml javafx:run
```

### 4. Probe nhanh khong can UI

Run main:

- `com.example.pharmacy.client.rmi.AuthClientProbe`

Mac dinh probe dang dung:

- username: `admin`
- password: `123`

## Ket qua build va test trong dot nay

### Build

Da pass:

- `mvn -q -f pharmacy-parent/pom.xml -DskipTests compile`
- `mvn -q -f pharmacy-parent/pom.xml -DskipTests clean package`

Khong pass duoc:

- `mvn -f pharmacy-parent/pom.xml clean install`

Nguyen nhan:

- loi moi truong local Maven repository
- `AccessDeniedException` khi ghi vao:
  - `C:\\Users\\hiepdeptrai\\.m2\\repository\\com\\example\\pharmacy\\pharmacy-parent\\1.0-SNAPSHOT\\...tmp`

Day khong phai loi compile Java cua POC dang nhap.

### Runtime

Da kiem tra:

- cong `localhost:3306` dang mo

Chua chay duoc end-to-end probe tu command line trong dot nay vi:

- can lap classpath runtime day du cho server/client
- cac lenh dung plugin Maven bo sung de tao runtime classpath dang gap loi truy cap `.m2`

Vi vay, runtime POC hien tai duoc danh gia:

- compile/package da san sang
- co huong dan chay thu ro rang trong IDE hoac local machine
- chua co bang chung login probe da duoc execute thanh cong ngay trong terminal nay

## Hanh vi dang nhap hien tai

### Dang nhap dung

- Tra `LoginResponse.success(...)`
- chua `sessionId`
- chua `UserDTO`
- client luu vao session

### Sai mat khau / sai tai khoan

- Tra `LoginResponse.failure("Tai khoan hoac mat khau khong dung.")`

### Tai khoan bi khoa / ngung hoat dong

- Tra `LoginResponse.failure("Tai khoan da bi khoa hoac ngung hoat dong.")`

### Server RMI chua chay / khong ket noi duoc

- client tra `LoginResponse.failure(...)` voi message:
  - `Server RMI chua bind AuthRemoteService.`
  - hoac `Khong the ket noi den server. Vui long kiem tra RMI server va MariaDB.`

## Checklist test

- [x] `pharmacy-server` co cau hinh JPA/Hibernate cho MariaDB
- [x] co `JpaUtil` tao `EntityManagerFactory`
- [x] co entity toi thieu cho login (`NhanVienEntity`)
- [x] co JPA repository truy van login (`JpaNhanVienRepository`)
- [x] co remote interface trong `pharmacy-common`
- [x] co `AuthServiceImpl` phia server
- [x] co RMI bootstrap bind service auth
- [x] co RMI client lookup
- [x] `DangNhap_Ctrl` khong con goi DAO/ConnectDB
- [x] `pharmacy-client` khong can dependency DB/JPA cho login POC
- [x] `pharmacy-common` khong chua JPA/JavaFX cho login POC
- [x] `pharmacy-client` compile duoc sau khi sua auth flow
- [x] `pharmacy-parent` package duoc
- [ ] `Server RMI` da duoc start va login probe thanh cong ngay trong terminal nay
- [ ] `EntityManagerFactory` da duoc xac nhan tao thanh cong voi schema MariaDB that trong terminal nay
- [ ] test thanh cong account khoa `locked / 123` tren runtime that

## Rui ro va technical debt con ton tai

1. `pharmacy-server` van con mot so DAO thong ke legacy import JavaFX (`ThongKe_Dao`, `ThongKeXNT_Dao`)
   - day la ton dong cu
   - khong nam trong POC dang nhap
   - la ly do `pharmacy-server/pom.xml` van giu `javafx-base` tam thoi

2. Mat khau hien POC dang so sanh plain text
   - phu hop voi seed du lieu hien tai
   - can doi sang hashing/verification o buoc bao mat sau

3. Auth dang dua tren bang `NhanVien`
   - phu hop schema MariaDB buoc 7
   - neu sau nay tach bang `TaiKhoan` rieng, can refactor auth repository/service

4. Audit login la best effort
   - neu bang audit hoac script buoc 8 chua apply, login khong bi fail
   - server chi log loi audit ra stderr

## Nhung phan chua lam

- Chua chuyen cac module khac (`KhachHang`, `NhaCungCap`, `Thuoc`, `HoaDon`, `PhieuNhap`) sang JPA/RMI
- Chua end-to-end runtime proof ngay trong terminal nay
- Chua doi mat khau sang hash
- Chua bo technical debt JavaFX khoi server legacy DAO thong ke

## Buoc tiep theo de xuat

Theo `MIGRATION_PRIORITY.md`, sau POC dang nhap nen chuyen tiep:

1. `KhachHang`
2. `NhaCungCap`
3. `DonViTinh`
4. `NhomDuocLy`
5. `Thuoc` sau khi cac danh muc lien quan on dinh

POC dang nhap nay la mau chuan cho luong:

```text
Client Controller
    -> Client Service
    -> Remote interface (common)
    -> Server Service
    -> JPA Repository
    -> MariaDB
```
