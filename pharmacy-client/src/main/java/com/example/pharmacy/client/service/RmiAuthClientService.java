package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.AuthClientService;
import com.example.pharmacy.client.session.RmiSessionContext;
import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

public class RmiAuthClientService implements AuthClientService {
    private final RmiClientProvider clientProvider;
    private final RmiSessionContext sessionContext;

    public RmiAuthClientService(RmiClientProvider clientProvider, RmiSessionContext sessionContext) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
        this.sessionContext = Objects.requireNonNull(sessionContext, "sessionContext must not be null");
    }

    @Override
    public LoginResponse login(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        String normalizedPassword = password == null ? "" : password;

        if (normalizedUsername.isEmpty() || normalizedPassword.isEmpty()) {
            sessionContext.clear();
            return LoginResponse.failure("Vui long nhap day du tai khoan va mat khau.");
        }

        try {
            AuthRemote authRemote = clientProvider.getAuthRemote();
            LoginResponse response = authRemote.login(new LoginRequest(normalizedUsername, normalizedPassword));
            if (response != null && response.isSuccess()) {
                sessionContext.establish(response);
            } else {
                sessionContext.clear();
            }
            return response == null ? LoginResponse.failure("Khong nhan duoc phan hoi hop le tu server.") : response;
        } catch (NotBoundException exception) {
            sessionContext.clear();
            return LoginResponse.failure("Server RMI chua bind AuthRemoteService.");
        } catch (RemoteException exception) {
            sessionContext.clear();
            return LoginResponse.failure("Khong the ket noi den server. Vui long kiem tra RMI server va MariaDB.");
        }
    }
}
