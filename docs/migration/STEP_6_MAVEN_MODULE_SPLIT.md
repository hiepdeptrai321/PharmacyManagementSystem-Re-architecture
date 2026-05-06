# STEP 6 - Chia Project Thanh 3 Module Maven

## Muc tieu buoc 6

Buoc nay tap trung dung khung multi-module Maven cho kien truc dich:

```text
JavaFX Controller
    -> Client Service / RMI Stub
    -> Remote API trong pharmacy-common
    -> Server Service Implementation
    -> Repository / JPA
    -> MariaDB
```

Pham vi buoc nay:

- To chuc `pharmacy-parent` thanh parent Maven co 3 module `pharmacy-common`, `pharmacy-server`, `pharmacy-client`.
- Chuyen cac class khung da ro rang vao dung module.
- Dam bao huong dependency dung.
- Build duoc tu `pharmacy-parent`.

Khong lam trong buoc nay:

- Khong xoa `src/` cu.
- Khong chuyen toan bo DAO sang JPA.
- Khong doi SQL sang JPQL.
- Khong doi SQL Server sang MariaDB ngay.
- Khong refactor nghiep vu lon hoac UI hang loat.

## Cau truc truoc khi to chuc

Project goc van ton tai song song voi khung module moi:

```text
QuanLyHieuThuocV2/
├── docs/
├── pharmacy-parent/
│   ├── pharmacy-common/
│   ├── pharmacy-server/
│   ├── pharmacy-client/
│   └── pom.xml
├── SQL/
├── src/
├── tools/
├── pom.xml
└── README.md
```

`pom.xml` o root va thu muc `src/` cu duoc giu nguyen de tham khao va chuyen doi dan.

## Cau truc sau khi to chuc

```text
pharmacy-parent/
├── pom.xml
├── pharmacy-common/
│   ├── pom.xml
│   └── src/main/java/com/example/pharmacy/common/
│       ├── dto/
│       ├── enums/
│       ├── exception/
│       ├── remote/
│       ├── request/
│       └── response/
├── pharmacy-server/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/pharmacy/server/
│       │   ├── bootstrap/
│       │   ├── config/
│       │   ├── entity/
│       │   ├── legacydao/
│       │   ├── repository/
│       │   ├── service/
│       │   └── transaction/
│       └── resources/META-INF/
└── pharmacy-client/
    ├── pom.xml
    └── src/main/
        ├── java/com/example/pharmacy/client/
        │   ├── controller/
        │   ├── rmi/
        │   ├── service/
        │   ├── session/
        │   └── view/
        └── resources/
            ├── css/
            ├── fonts/
            ├── fxml/
            └── images/
```

## Vai tro tung module

### pharmacy-common

Chua phan dung chung giua client va server:

- DTO
- Request/response object
- Remote interface
- Exception dung chung
- Enum dung chung

Quy tac:

- Khong phu thuoc `pharmacy-client`
- Khong phu thuoc `pharmacy-server`
- Khong chua JavaFX
- Khong chua JPA/Hibernate
- Khong chua DAO/ConnectDB/Repository

### pharmacy-server

Chua phan chay o server:

- Service implementation
- Repository/entity skeleton
- Transaction abstraction
- RMI bootstrap
- Cau hinh JPA/Hibernate
- Driver JDBC SQL Server/MariaDB cho giai doan chuyen tiep

Quy tac:

- Phu thuoc `pharmacy-common`
- Khong phu thuoc `pharmacy-client`
- Khong chua JavaFX/FXML/UI resource

### pharmacy-client

Chua phan giao dien va giao tiep client:

- JavaFX controller skeleton
- Client service
- RMI provider/probe
- SessionContext phia client
- Resource UI future: FXML/CSS/images/fonts

Quy tac:

- Phu thuoc `pharmacy-common`
- Khong phu thuoc `pharmacy-server`
- Khong chua ConnectDB/DAO/Repository/JPA

## Dependency giua cac module

Huong dependency dung hien tai:

```text
pharmacy-client  ---> pharmacy-common
pharmacy-server  ---> pharmacy-common
```

Khong co dependency vong lap:

- `pharmacy-common -> pharmacy-client`
- `pharmacy-common -> pharmacy-server`
- `pharmacy-client -> pharmacy-server`
- `pharmacy-server -> pharmacy-client`

## Pom va cau hinh build da chinh

Da chinh cac file:

- [pharmacy-parent/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pom.xml)
- [pharmacy-common/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/pom.xml)
- [pharmacy-server/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/pom.xml)
- [pharmacy-client/pom.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/pom.xml)

Noi dung chinh:

- `pharmacy-parent` la parent project voi `packaging` = `pom`
- Khai bao module theo thu tu:
  - `pharmacy-common`
  - `pharmacy-server`
  - `pharmacy-client`
- Thong nhat Java version = `22` de phu hop voi project hien tai
- `pharmacy-common` giu nhe, chua them JavaFX/JPA
- `pharmacy-server` phu thuoc `pharmacy-common`, Hibernate/JPA, MariaDB driver, SQL Server JDBC driver transition
- `pharmacy-client` phu thuoc `pharmacy-common`, JavaFX controls/fxml

## Bang phan loai code can di chuyen

| STT | Class/File | Vi tri hien tai | Vi tri dich | Loai code | Trang thai | Ghi chu |
|---|---|---|---|---|---|---|
| 1 | `UserDTO` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/` | `pharmacy-common` | Common DTO | Da chuyen | DTO dung chung cho login/session skeleton |
| 2 | `UserRole` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/enums/` | `pharmacy-common` | Common Enum | Da chuyen | Khong phu thuoc UI/DB |
| 3 | `AuthenticationException` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/exception/` | `pharmacy-common` | Common Exception | Da chuyen | Exception dung chung |
| 4 | `LoginRequest` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/` | `pharmacy-common` | Common Request/Response | Da chuyen | Serializable cho RMI |
| 5 | `LoginResponse` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/response/` | `pharmacy-common` | Common Request/Response | Da chuyen | Serializable cho RMI |
| 6 | `AuthRemote` | `pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/` | `pharmacy-common` | Common Remote Interface | Da chuyen | `Remote` + `RemoteException` |
| 7 | `RmiClientProvider` | `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/` | `pharmacy-client` | Client Service | Da chuyen | Lookup stub RMI phia client |
| 8 | `AuthClientService`, `RmiAuthClientService` | `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/` | `pharmacy-client` | Client Service | Da chuyen | Skeleton POC dang nhap |
| 9 | `SessionContext` | `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/session/` | `pharmacy-client` | Client Session | Da chuyen | Context tam cho client module moi |
| 10 | `AuthClientProbe` | `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/` | `pharmacy-client` | Client Probe | Da chuyen | Dung de thu POC login RMI |
| 11 | `controller/package-info.java`, `view/package-info.java` | `pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/` | `pharmacy-client` | Client Controller/View Skeleton | Da chuyen | Moi la package skeleton |
| 12 | `src/main/resources/fxml`, `css`, `images`, `fonts` | `pharmacy-parent/pharmacy-client/src/main/resources/` | `pharmacy-client` | Client Resource | Da chuyen | Tao san thu muc resource UI |
| 13 | `AuthService`, `AuthServiceImpl` | `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/` | `pharmacy-server` | Server Service | Da chuyen | Service POC login phia server |
| 14 | `NhanVienRepository`, `JpaNhanVienRepository`, `InMemoryNhanVienRepository` | `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/` | `pharmacy-server` | Server Repository | Da chuyen | Skeleton repository va in-memory POC |
| 15 | `NhanVienEntity` | `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/entity/` | `pharmacy-server` | Server Entity | Da chuyen | Skeleton entity phia server |
| 16 | `RmiServerBootstrap`, `AuthRemoteAdapter` | `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/` | `pharmacy-server` | Server Bootstrap | Da chuyen | Khoi dong RMI server skeleton |
| 17 | `TransactionManager`, `NoOpTransactionManager` | `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/` | `pharmacy-server` | Server Transaction | Da chuyen | Abstraction cho server service |
| 18 | `config/package-info.java`, `legacydao/package-info.java`, `META-INF/persistence.xml` | `pharmacy-parent/pharmacy-server/src/main/` | `pharmacy-server` | Database Config / Legacy DAO | Da chuyen | Khung cho cau hinh va DAO JDBC transition |
| 19 | `src/main/java/com/example/pharmacymanagementsystem_qlht/controller/**` | `src/` goc | `pharmacy-client` | Client Controller | Tam giu o `src` cu | Chua di chuyen de tranh vo duong dan FXML/UI hang loat |
| 20 | `src/main/resources/**` cua monolith | `src/` goc | `pharmacy-client/src/main/resources` | Client Resource | Tam giu o `src` cu | Se chuyen dan khi mapping resource on dinh |
| 21 | `src/main/java/com/example/pharmacymanagementsystem_qlht/dao/**` | `src/` goc | `pharmacy-server` | Legacy DAO | Tam giu o `src` cu | Chua chuyen de tranh vo nghiep vu JDBC hien tai |
| 22 | `ConnectDB` | `src/main/java/com/example/pharmacymanagementsystem_qlht/connectDB/` | `pharmacy-server` | Database Config | Tam giu o `src` cu | Se dua vao server transition khi tach client/server that |
| 23 | `src/main/java/com/example/pharmacymanagementsystem_qlht/service/**` | `src/` goc | Phan lon vao `pharmacy-server`, mot phan vao `pharmacy-client` | Service | Can kiem tra them | Can tach ro service nghiep vu va UI support class |
| 24 | `src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContext.java` | `src/` goc | Co the tach thanh common hoac request context sau | Common DTO / Client Session | Tam giu o `src` cu | Hien van phuc vu monolith buoc 5 |
| 25 | `src/main/java/com/example/pharmacymanagementsystem_qlht/session/SessionContext.java` | `src/` goc | `pharmacy-client` | Client Session | Tam giu o `src` cu | Session monolith hien tai, chua doi toan bo UI sang module moi |
| 26 | `src/main/java/com/example/pharmacymanagementsystem_qlht/session/LoginResult.java` | `src/` goc | `pharmacy-common` hoac `pharmacy-client` | Common Response / Client Session | Can kiem tra them | Phu thuoc cach chot login flow sau nay |
| 27 | `src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContextMapper.java` | `src/` goc | `pharmacy-client` hoac `pharmacy-server` | Mapper | Can kiem tra them | Can xem mapper nay phuc vu monolith hay RMI |

## Danh sach file/class da co trong tung module

### pharmacy-common

- [UserDTO.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/dto/UserDTO.java)
- [UserRole.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/enums/UserRole.java)
- [AuthenticationException.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/exception/AuthenticationException.java)
- [AuthRemote.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/remote/AuthRemote.java)
- [LoginRequest.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/request/LoginRequest.java)
- [LoginResponse.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-common/src/main/java/com/example/pharmacy/common/response/LoginResponse.java)

### pharmacy-server

- [RmiServerBootstrap.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/RmiServerBootstrap.java)
- [AuthRemoteAdapter.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/bootstrap/rmi/AuthRemoteAdapter.java)
- [NhanVienEntity.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/entity/NhanVienEntity.java)
- [NhanVienRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/NhanVienRepository.java)
- [JpaNhanVienRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/JpaNhanVienRepository.java)
- [InMemoryNhanVienRepository.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/repository/InMemoryNhanVienRepository.java)
- [AuthService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuthService.java)
- [AuthServiceImpl.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/service/AuthServiceImpl.java)
- [TransactionManager.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/TransactionManager.java)
- [NoOpTransactionManager.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/transaction/NoOpTransactionManager.java)
- [package-info.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/package-info.java) trong `config`
- [package-info.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/legacydao/package-info.java) trong `legacydao`
- [persistence.xml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-server/src/main/resources/META-INF/persistence.xml)

### pharmacy-client

- [RmiClientProvider.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/RmiClientProvider.java)
- [AuthClientProbe.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/rmi/AuthClientProbe.java)
- [AuthClientService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/AuthClientService.java)
- [RmiAuthClientService.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/service/RmiAuthClientService.java)
- [SessionContext.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/session/SessionContext.java)
- [package-info.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/controller/package-info.java) trong `controller`
- [package-info.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/java/com/example/pharmacy/client/view/package-info.java) trong `view`
- Thu muc resource:
  - [fxml](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/resources/fxml)
  - [css](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/resources/css)
  - [images](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/resources/images)
  - [fonts](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent/pharmacy-client/src/main/resources/fonts)

## Danh sach file/class chua di chuyen

Nhung phan con lai van nam o `src/` goc theo chu truong an toan:

- Toan bo controller JavaFX monolith hien tai
- Toan bo DAO JDBC hien tai
- `ConnectDB`
- FXML/CSS/image cua monolith hien tai
- Cac service nghiep vu monolith da tao o buoc 4
- Session monolith cua buoc 5:
  - [UserContext.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContext.java)
  - [SessionContext.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/java/com/example/pharmacymanagementsystem_qlht/session/SessionContext.java)
  - [LoginResult.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/java/com/example/pharmacymanagementsystem_qlht/session/LoginResult.java)
  - [UserContextMapper.java](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/src/main/java/com/example/pharmacymanagementsystem_qlht/session/UserContextMapper.java)

Ghi chu quan trong:

- `SessionContext` trong `pharmacy-client` la session skeleton cho module moi.
- `SessionContext` trong `src/` goc van dang phuc vu luong monolith hien tai.
- Hai nhom nay chua hop nhat o buoc 6 de tranh vo luong dang nhap/hien thi hien co.

## Kiem tra dependency sai tang

Da quet lai source trong `pharmacy-parent` theo cac pattern chinh:

### pharmacy-common

Khong phat hien:

- `javafx.*`
- `jakarta.persistence.*`
- `org.hibernate.*`
- `ConnectDB`
- `DAO`
- `repository`
- `controller`

### pharmacy-client

Khong phat hien:

- `ConnectDB`
- `DriverManager`
- `Connection`
- `PreparedStatement`
- `ResultSet`
- `EntityManager`
- `org.hibernate.*`
- `repository`
- `DAO`

### pharmacy-server

Khong phat hien:

- `javafx.*`
- import `pharmacy.client`
- `FXML`
- `Scene`
- `Stage`

Ket luan:

- `pharmacy-common` hien doc lap va sach tang.
- `pharmacy-client` hien chua co dependency DB/JPA.
- `pharmacy-server` hien chua co UI dependency.

## Huong dan build

Build duoc tu thu muc [pharmacy-parent](/C:/Users/hiepdeptrai/Desktop/hk2_2025_2026/QuanLyHieuThuocV2/pharmacy-parent):

```powershell
cd pharmacy-parent
mvn clean install
```

Trang thai hien tai:

- `mvn clean install` da chay thanh cong tai `pharmacy-parent`
- Maven build ca 3 module thanh cong
- Root project cu van duoc giu nguyen va khong bi xoa

Luu y:

- Trong moi truong local nay, Maven can ghi vao `.m2` de tai dependency. Viec nay da duoc kiem chung qua build thanh cong.
- Chua them Maven Wrapper o buoc nay.

## Ghi chu cho giai doan chuyen doi tiep theo

- Root `pom.xml` va `src/` cu van la monolith goc, chua bi loai bo khoi quy trinh lam viec.
- `pharmacy-server` da duoc phep giu driver SQL Server tam thoi de ho tro giai doan dung lai legacy JDBC tren server, nhung chua chuyen code DAO vao module nay.
- `pharmacy-client` moi o muc skeleton, chua di chuyen controller/FXML that su de tranh vo resource path.
- `UserContext` monolith cua buoc 5 chua dua sang `pharmacy-common` ngay, vi can chot ro no la DTO dung chung hay context chi phia client trong giao doan RMI sau nay.

## Checklist hoan thanh buoc 6

- [x] Da co parent Maven project trong `pharmacy-parent`
- [x] Da co 3 module `pharmacy-common`, `pharmacy-server`, `pharmacy-client`
- [x] Moi module da co `pom.xml` rieng
- [x] Parent `pom.xml` da khai bao dung modules
- [x] Dependency da theo huong `client -> common`, `server -> common`
- [x] `pharmacy-common` doc lap, khong phu thuoc `client/server`
- [x] `pharmacy-client` khong phu thuoc `pharmacy-server`
- [x] `pharmacy-server` khong phu thuoc `pharmacy-client`
- [x] Da tao package/resource skeleton can thiet cho 3 module
- [x] Da giu nguyen `src/` cu de chuyen doi dan
- [x] Da ghi ro file/class da chuyen va chua chuyen
- [x] Da quet dependency sai tang trong module moi
- [x] Da build thanh cong bang `mvn clean install` tu `pharmacy-parent`

