# QuanLyHieuThuocV2

Project hien tai da duoc chot theo cau truc multi-module Maven o root repo:

- `pharmacy-common`
- `pharmacy-server`
- `pharmacy-client`

Thu muc `pharmacy-parent/` vat ly khong con la source chinh. Neu no xuat hien tro lai thi do build output hoac run config cu, khong phai cau truc dang duoc phat trien.

## Build dung

Chay tu root repo:

```powershell
mvn -q -DskipTests compile
mvn clean install
```

## Chay server

Main class:

- `com.example.pharmacy.server.bootstrap.RmiServerBootstrap`

## Chay client

Main class:

- `com.example.pharmacy.client.PharmacyClientApplication`

Hoac:

```powershell
mvn -q javafx:run
```

## Ghi chu cleanup

- Khong chay build bang duong dan cu `pharmacy-parent/...`
- Khong tao them source moi trong thu muc `pharmacy-parent/`
- Neu IDE con giu run config cu, hay doi ve module root hien tai
