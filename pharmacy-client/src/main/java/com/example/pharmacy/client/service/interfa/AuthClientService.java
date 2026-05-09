package com.example.pharmacy.client.service.interfa;

import com.example.pharmacy.common.response.LoginResponse;

public interface AuthClientService {
    LoginResponse login(String username, String password);
}
