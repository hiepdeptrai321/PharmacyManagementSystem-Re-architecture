package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.TonKhoRemote;
import com.example.pharmacy.server.service.TonKhoService;
import com.example.pharmacymanagementsystem_qlht.model.Thuoc_SP_TheoLo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class TonKhoRemoteAdapter extends UnicastRemoteObject implements TonKhoRemote {
    private final TonKhoService tonKhoService;

    public TonKhoRemoteAdapter(TonKhoService tonKhoService) throws RemoteException {
        super();
        this.tonKhoService = Objects.requireNonNull(tonKhoService, "tonKhoService must not be null");
    }

    @Override
    public List<Thuoc_SP_TheoLo> findAllLots() throws RemoteException {
        return tonKhoService.findAllLots();
    }

    @Override
    public boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo, UserContext actor) throws RemoteException {
        return tonKhoService.updateLotQuantity(thuocTheoLo, actor);
    }
}
