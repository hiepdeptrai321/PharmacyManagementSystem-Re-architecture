CREATE USER IF NOT EXISTS 'pharmacy_app'@'localhost' IDENTIFIED BY '123456';
CREATE USER IF NOT EXISTS 'pharmacy_app'@'127.0.0.1' IDENTIFIED BY '123456';

ALTER USER 'pharmacy_app'@'localhost' IDENTIFIED BY '123456';
ALTER USER 'pharmacy_app'@'127.0.0.1' IDENTIFIED BY '123456';

GRANT ALL PRIVILEGES ON `quan_ly_nha_thuoc`.* TO 'pharmacy_app'@'localhost';
GRANT ALL PRIVILEGES ON `quan_ly_nha_thuoc`.* TO 'pharmacy_app'@'127.0.0.1';

FLUSH PRIVILEGES;
