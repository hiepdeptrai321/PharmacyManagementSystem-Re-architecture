package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.client.service.interfa.TonKhoClientService;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.TonKhoRemote;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLoDto;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiTonKhoClientService implements TonKhoClientService {
    private final RmiClientProvider clientProvider;

    public RmiTonKhoClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public List<Thuoc_SP_TheoLoDto> findAllLots() {
        try {
            return remote().findAllLots();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach ton kho theo lo tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean updateLotQuantity(Thuoc_SP_TheoLoDto thuocTheoLo, UserContext actor) {
        try {
            return remote().updateLotQuantity(thuocTheoLo, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the cap nhat ton kho tren server: " + exception.getMessage(), exception);
        }
    }

    private TonKhoRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getTonKhoRemote();
    }
}
