package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.session.SessionContext;
import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.common.request.LoginRequest;
import com.example.pharmacy.common.response.LoginResponse;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Objects;

public class RmiAuthClientService implements AuthClientService {
    private final RmiClientProvider clientProvider;
    private final SessionContext sessionContext;

    public RmiAuthClientService(RmiClientProvider clientProvider, SessionContext sessionContext) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
        this.sessionContext = Objects.requireNonNull(sessionContext, "sessionContext must not be null");
    }

    @Override
    public LoginResponse login(String username, String password) throws RemoteException {
        try {
            AuthRemote authRemote = clientProvider.getAuthRemote();
            LoginResponse response = authRemote.login(new LoginRequest(username, password));
            if (response != null && response.isSuccess()) {
                sessionContext.establish(response);
            } else {
                sessionContext.clear();
            }
            return response;
        } catch (NotBoundException exception) {
            throw new RemoteException("AuthRemote is not bound in the RMI registry.", exception);
        }
    }
}
