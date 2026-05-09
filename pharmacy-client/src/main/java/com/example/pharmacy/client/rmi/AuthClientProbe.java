package com.example.pharmacy.client.rmi;

import com.example.pharmacy.client.service.AuthClientService;
import com.example.pharmacy.client.service.RmiAuthClientService;
import com.example.pharmacy.client.session.RmiSessionContext;
import com.example.pharmacy.common.response.LoginResponse;

public final class AuthClientProbe {
    private AuthClientProbe() {
    }

    public static void main(String[] args) throws Exception {
        String username = args.length > 0 ? args[0] : "admin";
        String password = args.length > 1 ? args[1] : "123";

        RmiSessionContext sessionContext = new RmiSessionContext();
        AuthClientService authClientService = new RmiAuthClientService(new RmiClientProvider(), sessionContext);

        LoginResponse response = authClientService.login(username, password);
        System.out.println("Success: " + response.isSuccess());
        System.out.println("Message: " + response.getMessage());
        System.out.println("Session: " + response.getSessionId());
        System.out.println("User: " + (response.getUser() != null ? response.getUser().getDisplayName() : "<none>"));
    }
}
