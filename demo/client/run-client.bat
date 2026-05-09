@echo off
cd /d %~dp0
java ^
 -Dpharmacy.rmi.host=127.0.0.1 ^
 -cp "pharmacy-client-1.0-SNAPSHOT.jar;lib/*" ^
 com.example.pharmacy.client.Launch
pause
