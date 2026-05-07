# MariaDB Authentication Fix

## Muc tieu

Fix loi khoi tao JPA/Hibernate tren Windows:

```text
The token supplied to the function is invalid
```

khi `EntityManagerFactory` ket noi MariaDB.

## Nguyen nhan thuong gap

Loi nay thuong xay ra khi MariaDB local dang uu tien co che xac thuc SSPI/GSSAPI/Windows authentication hoac dang su dung user khong phu hop cho JDBC app.

Du project khong viet DAO JDBC thu cong cho login nua, JPA/Hibernate van phai di qua MariaDB JDBC driver o tang duoi. Neu JDBC driver khong dang nhap duoc thi `JpaUtil` se fail ngay luc tao `EntityManagerFactory`.

## Quy uoc cau hinh moi

- Khong dung `root` lam tai khoan mac dinh cho app
- Khong dung `localhost` lam host mac dinh
- Mac dinh moi:
  - URL: `jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc`
  - User: `pharmacy_app`
  - Password: `123456`

Ly do:

- `127.0.0.1` ep TCP ro rang hon `localhost`
- user rieng giup tranh plugin xac thuc dac biet cua `root`
- giam rui ro dung tai khoan qua quyen cho ung dung

## File da cap nhat

- `pharmacy-parent/pharmacy-server/src/main/resources/META-INF/persistence.xml`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/DatabaseProperties.java`
- `pharmacy-parent/pharmacy-server/src/main/java/com/example/pharmacy/server/config/JpaUtil.java`
- `tools/ValidateMariaDbSchema.java`
- `SQL/create_pharmacy_app_user.sql`

## Thu tu uu tien cau hinh DB

`JpaUtil` va cac helper JDBC server-side uu tien doc cau hinh theo thu tu:

1. System property:
   - `pharmacy.db.url`
   - `pharmacy.db.user`
   - `pharmacy.db.password`
2. Environment variable:
   - `PHARMACY_DB_URL`
   - `PHARMACY_DB_USER`
   - `PHARMACY_DB_PASSWORD`
3. Neu khong co override thi dung default da dong bo voi `persistence.xml`

## Cach tao user rieng cho app

Chay file:

- `SQL/create_pharmacy_app_user.sql`

Co the chay trong HeidiSQL hoac terminal:

```sql
CREATE USER IF NOT EXISTS 'pharmacy_app'@'localhost' IDENTIFIED BY '123456';
CREATE USER IF NOT EXISTS 'pharmacy_app'@'127.0.0.1' IDENTIFIED BY '123456';

ALTER USER 'pharmacy_app'@'localhost' IDENTIFIED BY '123456';
ALTER USER 'pharmacy_app'@'127.0.0.1' IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES ON quan_ly_nha_thuoc.* TO 'pharmacy_app'@'localhost';
GRANT ALL PRIVILEGES ON quan_ly_nha_thuoc.* TO 'pharmacy_app'@'127.0.0.1';

FLUSH PRIVILEGES;
```

Hai lenh `ALTER USER` o day co muc dich reset lai password auth cho truong hop user da ton tai tu truoc voi config/plugin khong phu hop.

## Cach test bang terminal

```bash
mariadb --protocol=TCP -h 127.0.0.1 -P 3306 -u pharmacy_app -p quan_ly_nha_thuoc
```

Neu login duoc, JDBC/JPA thuong cung se ket noi duoc voi cung thong tin nay.

Neu van thay loi SSPI/GSSAPI, hay dang nhap bang session quan tri trong HeidiSQL va kiem tra user `pharmacy_app` da duoc tao/alter thanh cong chua. Day la dau hieu server MariaDB local van dang uu tien co che auth khac o phia database.

## Chay server voi override neu can

Co the chay server bang system property:

```bash
-Dpharmacy.db.url=jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc
-Dpharmacy.db.user=pharmacy_app
-Dpharmacy.db.password=123456
```

Hoac set environment variable:

```text
PHARMACY_DB_URL
PHARMACY_DB_USER
PHARMACY_DB_PASSWORD
```

## Ghi chu

- `JpaUtil` chi log URL va username, khong in password
- neu JPA khoi tao that bai, message moi se goi y kiem tra:
  - MariaDB service
  - database name
  - user/password
  - authentication plugin
- dependency `mssql-jdbc` trong `pharmacy-server` duoc giu tam thoi cho code legacy chua migrate het; no khong tham gia vao luong JPA login POC hien tai
