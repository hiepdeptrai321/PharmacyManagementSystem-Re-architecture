package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.model.Thuoc_SP_TheoLo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TonKhoRemote extends Remote {
    String BINDING_NAME = "TonKhoRemoteService";

    List<Thuoc_SP_TheoLo> findAllLots() throws RemoteException;

    boolean updateLotQuantity(Thuoc_SP_TheoLo thuocTheoLo, UserContext actor) throws RemoteException;
}
