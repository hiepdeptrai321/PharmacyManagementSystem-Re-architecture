package com.example.pharmacy.client.service;

import com.example.pharmacy.common.response.LoginResponse;

public interface AuthClientService {
    LoginResponse login(String username, String password);
}
