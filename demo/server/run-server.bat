@echo off
cd /d %~dp0
java ^
 -Dpharmacy.rmi.host=127.0.0.1 ^
 -Dpharmacy.db.url=jdbc:mariadb://127.0.0.1:3306/quan_ly_nha_thuoc ^
 -Dpharmacy.db.user=pharmacy_app ^
 -Dpharmacy.db.password=123456 ^
 -cp "pharmacy-server-1.0-SNAPSHOT.jar;lib/*" ^
 com.example.pharmacy.server.bootstrap.RmiServerBootstrap
pause
