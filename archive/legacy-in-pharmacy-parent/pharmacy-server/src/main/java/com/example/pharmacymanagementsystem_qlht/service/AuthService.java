package com.example.pharmacymanagementsystem_qlht.service;

import com.example.pharmacymanagementsystem_qlht.dao.NhanVien_Dao;
import com.example.pharmacymanagementsystem_qlht.model.NhanVien;
import com.example.pharmacymanagementsystem_qlht.session.LoginResult;
import com.example.pharmacymanagementsystem_qlht.session.UserContext;

public class AuthService {
    private final NhanVien_Dao nhanVienDao = new NhanVien_Dao();

    public LoginResult login(String username, String password) {
        String taiKhoan = username == null ? "" : username.trim();
        String matKhau = password == null ? "" : password;

        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            return LoginResult.failure("Vui lòng nhập đầy đủ tài khoản và mật khẩu.");
        }

        NhanVien nhanVien = nhanVienDao.selectByTKVaMK(taiKhoan, matKhau);
        if (nhanVien == null) {
            return LoginResult.failure("Tên đăng nhập hoặc mật khẩu không chính xác.");
        }

        UserContext userContext = new UserContext(
                nhanVien.getMaNV(),
                nhanVien.getTaiKhoan(),
                nhanVien.getMaNV(),
                nhanVien.getTenNV(),
                nhanVien.getVaiTro()
        );
        return LoginResult.success(userContext);
    }
}
