package com.example.pharmacy.client.session;

import com.example.pharmacy.common.dto.UserDTO;
import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.model.NhanVien;
import com.example.pharmacy.common.session.UserContext;

public final class UserContextMapper {
    private UserContextMapper() {
    }

    public static UserContext toUserContext(UserDTO user) {
        if (user == null) {
            return null;
        }
        return new UserContext(
                user.getEmployeeId(),
                user.getUsername(),
                user.getEmployeeId(),
                user.getDisplayName(),
                mapRole(user.getRole())
        );
    }

    public static com.example.pharmacy.common.dto.UserContext toRemoteUserContext(UserContext userContext) {
        if (userContext == null) {
            return null;
        }
        return new com.example.pharmacy.common.dto.UserContext(
                userContext.getUserId(),
                userContext.getUsername(),
                userContext.getEmployeeId(),
                userContext.getFullName(),
                mapRemoteRole(userContext.getRole())
        );
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

    private static String mapRole(UserRole role) {
        if (role == null) {
            return "Khong xac dinh";
        }
        return switch (role) {
            case MANAGER -> "Quan ly";
            case STAFF -> "Nhan vien";
            case UNKNOWN -> "Khong xac dinh";
        };
    }

    private static UserRole mapRemoteRole(String role) {
        if (role == null) {
            return UserRole.UNKNOWN;
        }
        String normalized = role.trim().toLowerCase();
        if (normalized.contains("quan")) {
            return UserRole.MANAGER;
        }
        if (normalized.contains("nhan")) {
            return UserRole.STAFF;
        }
        return UserRole.UNKNOWN;
    }
}
