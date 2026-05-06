# Architecture Step 1

## 1. Pham vi cua buoc nay

Buoc 1 chi dung nen kien truc dich de chuan bi cho qua trinh chuyen doi sau nay.

Buoc nay co chu y:

- Khong migrate toan bo DAO sang repository.
- Khong doi hang loat SQL/JDBC sang JPQL.
- Khong xoa `ConnectDB`.
- Khong doi toan bo SQL Server sang MariaDB ngay lap tuc.
- Khong sua hang loat JavaFX controller.

Buoc nay da lam:

- Chot kien truc client-server dich.
- Chon RMI la co che giao tiep.
- Tao khung Maven 3 module trong `pharmacy-parent/`.
- Tao POC skeleton cho dang nhap qua RMI.
- Dat quy tac kien truc de client khong ket noi DB truc tiep.

Project JavaFX monolith hien tai van duoc giu nguyen. Khung moi duoc tao song song de cac buoc sau chuyen doi dan.

## 2. Kien truc dich da chot

```text
JavaFX Controller
    -> Client Service
    -> RMI Remote Interface
    -> Server Service
    -> Repository / JPA
    -> MariaDB
```

Nguyen tac cot loi:

- JavaFX chi la client hien thi giao dien.
- Server Java la noi xu ly nghiep vu.
- Server la noi duy nhat duoc phep truy cap database.
- Tang du lieu dich su dung JPA/Hibernate.
- Database dich la MariaDB.

## 3. Vi sao chon RMI

RMI duoc chon cho giai doan nay vi:

- Client va server deu la Java.
- Khong can tu thiet ke protocol request/response nhu socket tho.
- De tao remote interface, DTO, request, response dung chung.
- Phu hop voi qua trinh cat dan JavaFX monolith sang client-server.
- Cho phep tap trung vao ranh gioi kien truc truoc khi migration nghiep vu lon.

Socket tho chua duoc dung trong buoc 1.

## 4. Quy tac kien truc bat buoc

### 4.1 Client khong ket noi DB truc tiep

Module client khong duoc:

- Goi `ConnectDB`.
- Tao `Connection`, `PreparedStatement`, `ResultSet`.
- Chua JDBC URL, user, password.
- Goi DAO hay repository truc tiep.
- Doc ghi database bang SQL.

Controller JavaFX chi duoc:

- Lay input tu UI.
- Goi `Client Service`.
- Nhan DTO/response.
- Hien thi ket qua ra UI.

### 4.2 Nghiep vu phai di qua service layer o server

Module server phan tach theo trach nhiem:

- `Remote Interface`: hop dong giao tiep client-server.
- `Server Service`: xu ly nghiep vu.
- `Repository`: truy cap du lieu.
- `Transaction`: quan ly transaction cho cac use case co ghi du lieu.

Tat ca nghiep vu ve sau nhu dang nhap, lap hoa don, nhap hang, doi/tra, thong ke, tim kiem... deu phai di theo luong:

```text
Controller -> Client Service -> RMI -> Server Service -> Repository -> Database
```

## 5. Cau truc module Maven da dung

Khung moi duoc tao trong thu muc:

```text
pharmacy-parent/
+-- pom.xml
+-- pharmacy-common/
+-- pharmacy-client/
`-- pharmacy-server/
```

Chi tiet:

```text
pharmacy-parent/
+-- pharmacy-common/
|   `-- src/main/java/com/example/pharmacy/common/
|       +-- dto/
|       +-- enums/
|       +-- exception/
|       +-- remote/
|       +-- request/
|       `-- response/
|
+-- pharmacy-client/
|   `-- src/main/java/com/example/pharmacy/client/
|       +-- controller/
|       +-- rmi/
|       +-- service/
|       +-- session/
|       `-- view/
|
`-- pharmacy-server/
    `-- src/main/java/com/example/pharmacy/server/
        +-- bootstrap/
        +-- entity/
        +-- repository/
        +-- service/
        `-- transaction/
```

Vai tro cua tung module:

- `pharmacy-common`: DTO, request, response, exception, enum, remote interface dung chung.
- `pharmacy-client`: JavaFX UI, controller, client service, RMI lookup/stub, session context.
- `pharmacy-server`: service implementation, JPA entity, repository, transaction, RMI bootstrap.

## 6. Luong goi chuan da chot

Luong goi tong quat:

```text
JavaFX Controller
    -> Client Service
    -> AuthRemote / Remote Interface
    -> Server Service Implementation
    -> JPA Repository
    -> MariaDB
```

Luong goi mau cho dang nhap:

```text
DangNhap_Ctrl
    -> AuthClientService
    -> AuthRemote
    -> AuthServiceImpl
    -> NhanVienRepository
    -> MariaDB
```

Trang thai hien tai cua POC dang nhap:

- Da co hop dong RMI.
- Da co request/response/DTO.
- Da co `SessionContext` de thay cho viec phu thuoc vao `DangNhap_Ctrl.user`.
- Da co server bootstrap de bind RMI service.
- Da co repository interface va 1 implementation in-memory de smoke test duong truyen RMI truoc khi gan JPA that.
- Da co `JpaNhanVienRepository` skeleton lam diem noi cho buoc 2.

## 7. POC dang nhap trong buoc 1

Nhung class da duoc tao cho POC:

### 7.1 pharmacy-common

- `AuthRemote`
- `LoginRequest`
- `LoginResponse`
- `UserDTO`
- `UserRole`
- `AuthenticationException`

### 7.2 pharmacy-client

- `AuthClientService`
- `RmiAuthClientService`
- `RmiClientProvider`
- `SessionContext`
- `AuthClientProbe`

### 7.3 pharmacy-server

- `AuthService`
- `AuthServiceImpl`
- `NhanVienEntity`
- `NhanVienRepository`
- `InMemoryNhanVienRepository`
- `JpaNhanVienRepository`
- `AuthRemoteAdapter`
- `RmiServerBootstrap`
- `TransactionManager`
- `NoOpTransactionManager`
- `META-INF/persistence.xml`

Ghi chu quan trong:

- POC dang nhap hien tai chua dung DB that.
- `InMemoryNhanVienRepository` chi la cau noi tam de kiem thu duong truyen RMI.
- Buoc sau se thay no bang repository JPA + MariaDB ma khong can doi luong goi cua client.

## 8. Nhung viec chua lam trong buoc nay

- Chua doi `ConnectDB` trong monolith.
- Chua cat bo DAO JDBC hien tai.
- Chua doi SQL Server schema sang MariaDB schema that su.
- Chua doi SQL sang JPQL.
- Chua wire JavaFX controller hien tai sang `AuthClientService`.
- Chua tao `EntityManagerFactory` dung that cho server runtime.
- Chua them password hashing, session timeout, transport security.
- Chua tach cac nghiep vu khac nhu hoa don, nhap hang, thong ke.

## 9. Cach dung khung nay o buoc tiep theo

Thu tu de tiep tuc:

1. Noi `DangNhap_Ctrl` sang `AuthClientService`.
2. Thay `InMemoryNhanVienRepository` bang `JpaNhanVienRepository`.
3. Tao `EntityManagerFactory` tu `persistence.xml`.
4. Map `NhanVienEntity` voi database MariaDB dich.
5. Khi dang nhap da chay qua RMI, tiep tuc moi use case sang service layer.

## 10. Checklist hoan thanh buoc 1

- [x] Chot kien truc dich client-server.
- [x] Chot RMI la co che giao tiep.
- [x] Dat quy tac client khong ket noi DB.
- [x] Dat quy tac nghiep vu di qua server service.
- [x] Tao khung Maven 3 module.
- [x] Tao luong goi chuan bang tai lieu kien truc.
- [x] Tao skeleton POC dang nhap qua RMI.
- [x] Tao session context phia client de thay the phu thuoc vao controller.
- [x] Giu nguyen monolith hien tai, khong sua lon ngoai pham vi buoc 1.
