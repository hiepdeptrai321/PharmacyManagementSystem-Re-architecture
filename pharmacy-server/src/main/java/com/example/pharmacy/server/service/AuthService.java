package com.example.pharmacy.server.service;

import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
