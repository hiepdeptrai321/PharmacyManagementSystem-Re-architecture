package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthRemote extends Remote {
    String BINDING_NAME = "AuthRemote";

    LoginResponse login(LoginRequest request) throws RemoteException;
}
