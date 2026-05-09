package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.AuthClientService;
import com.example.pharmacy.client.service.RmiAuthClientService;
import com.example.pharmacy.common.dto.UserDTO;
import com.example.pharmacy.common.enums.UserRole;
import com.example.pharmacy.common.response.LoginResponse;
import com.example.pharmacy.common.session.LoginResult;
import com.example.pharmacy.common.session.UserContext;

public class AuthService {
    private final AuthClientService authClientService =
            new RmiAuthClientService(new RmiClientProvider(), new com.example.pharmacy.client.session.RmiSessionContext());

    public LoginResult login(String username, String password) {
        LoginResponse response = authClientService.login(username, password);
        if (response == null || !response.isSuccess() || response.getUser() == null) {
            String message = response == null ? "Khong nhan duoc phan hoi tu may chu." : response.getMessage();
            return LoginResult.failure(message == null || message.isBlank()
                    ? "Dang nhap that bai."
                    : message);
        }
        return LoginResult.success(toUserContext(response.getUser()));
    }

    private UserContext toUserContext(UserDTO user) {
        String role = mapRole(user.getRole());
        return new UserContext(
                user.getEmployeeId(),
                user.getUsername(),
                user.getEmployeeId(),
                user.getDisplayName(),
                role
        );
    }

    private String mapRole(UserRole role) {
        if (role == null) {
            return "Khong xac dinh";
        }
        return switch (role) {
            case MANAGER -> "Quan ly";
            case STAFF -> "Nhan vien";
            case UNKNOWN -> "Khong xac dinh";
        };
    }
}
