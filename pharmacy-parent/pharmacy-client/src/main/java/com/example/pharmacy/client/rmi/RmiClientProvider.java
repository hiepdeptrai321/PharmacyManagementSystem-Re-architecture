package com.example.pharmacy.client.rmi;

import com.example.pharmacy.common.remote.AuthRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClientProvider {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 1099;

    private final String host;
    private final int port;

    public RmiClientProvider() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public RmiClientProvider(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public AuthRemote getAuthRemote() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(host, port);
        return (AuthRemote) registry.lookup(AuthRemote.BINDING_NAME);
    }
}
