# Demo package

## Cau truc

- `server/`: app server RMI + JPA/Hibernate + MariaDB
- `client/`: app JavaFX cho may client

## Chay tren cung mot may

1. Bat MariaDB va dam bao database `quan_ly_nha_thuoc` san sang.
2. Chay `server/run-server.bat`
3. Chay `client/run-client.bat`

## Chay tren hai may

1. May server:
   - sua `server/run-server.bat`:
     - `-Dpharmacy.rmi.host=` thanh IP cua may server, vi du `192.168.1.10`
   - giu DB local neu MariaDB nam cung may:
     - `jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc`
2. May client:
   - sua `client/run-client.bat`:
     - `-Dpharmacy.rmi.host=` thanh cung IP may server
3. Mo port `1099` tren firewall neu can.

## Yeu cau

- Java 22 tren ca may server va client
- MariaDB chi can tren may server
- Tai khoan DB mac dinh:
  - user: `pharmacy_app`
  - password: `123456`
