package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;
import com.example.pharmacy.server.service.AuthService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class AuthRemoteAdapter extends UnicastRemoteObject implements AuthRemote {
    private final AuthService authService;

    public AuthRemoteAdapter(AuthService authService) throws RemoteException {
        super();
        this.authService = Objects.requireNonNull(authService, "authService must not be null");
    }

    @Override
    public LoginResponse login(LoginRequest request) throws RemoteException {
        return authService.login(request);
    }
}
