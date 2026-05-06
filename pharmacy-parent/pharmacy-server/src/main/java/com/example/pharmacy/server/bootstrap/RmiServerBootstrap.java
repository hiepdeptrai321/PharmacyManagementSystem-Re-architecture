package com.example.pharmacy.server.bootstrap;

import com.example.pharmacy.common.remote.AuthRemote;
import com.example.pharmacy.server.bootstrap.rmi.AuthRemoteAdapter;
import com.example.pharmacy.server.repository.InMemoryNhanVienRepository;
import com.example.pharmacy.server.repository.NhanVienRepository;
import com.example.pharmacy.server.service.AuthService;
import com.example.pharmacy.server.service.AuthServiceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class RmiServerBootstrap {
    public static final int DEFAULT_PORT = 1099;

    private RmiServerBootstrap() {
    }

    public static void main(String[] args) throws Exception {
        Registry registry = createOrGetRegistry(DEFAULT_PORT);

        // Step 1 uses an in-memory repository so the RMI transport can be tested
        // without changing JDBC, DAO, or the legacy monolith yet.
        NhanVienRepository repository = new InMemoryNhanVienRepository();
        AuthService authService = new AuthServiceImpl(repository);
        AuthRemote authRemote = new AuthRemoteAdapter(authService);

        registry.rebind(AuthRemote.BINDING_NAME, authRemote);
        System.out.println("RMI server started on port " + DEFAULT_PORT + " with binding " + AuthRemote.BINDING_NAME);
    }

    private static Registry createOrGetRegistry(int port) throws RemoteException {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.list();
            return registry;
        } catch (RemoteException exception) {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.list();
            return registry;
        }
    }
}
