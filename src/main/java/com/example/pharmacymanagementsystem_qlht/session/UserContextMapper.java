package com.example.pharmacymanagementsystem_qlht.session;

import com.example.pharmacymanagementsystem_qlht.model.NhanVien;

public final class UserContextMapper {
    private UserContextMapper() {
    }

    public static NhanVien toNhanVienReference(UserContext userContext) {
        if (userContext == null) {
            return null;
        }

        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV(userContext.getEmployeeId());
        nhanVien.setTenNV(userContext.getFullName());
        nhanVien.setTaiKhoan(userContext.getUsername());
        nhanVien.setVaiTro(userContext.getRole());
        return nhanVien;
    }
}
