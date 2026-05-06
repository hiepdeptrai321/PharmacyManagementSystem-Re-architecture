package com.example.pharmacy.client.service;

import com.example.pharmacy.common.response.LoginResponse;

import java.rmi.RemoteException;

public interface AuthClientService {
    LoginResponse login(String username, String password) throws RemoteException;
}
