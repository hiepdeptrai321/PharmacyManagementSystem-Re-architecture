package com.example.pharmacy.server.bootstrap.rmi;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.HoaDonRemote;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.server.service.HoaDonService;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

public class HoaDonRemoteAdapter extends UnicastRemoteObject implements HoaDonRemote {
    private final HoaDonService hoaDonService;

    public HoaDonRemoteAdapter(HoaDonService hoaDonService) throws RemoteException {
        super();
        this.hoaDonService = Objects.requireNonNull(hoaDonService, "hoaDonService must not be null");
    }

    @Override
    public String generateNewMaHoaDon() throws RemoteException {
        return hoaDonService.generateNewMaHoaDon();
    }

    @Override
    public String createInvoice(CreateHoaDonRequest request, UserContext actor) throws RemoteException {
        return hoaDonService.createInvoice(request, actor);
    }

    @Override
    public List<HoaDon> findAll() throws RemoteException {
        return hoaDonService.findAll();
    }

    @Override
    public HoaDon findById(String maHoaDon) throws RemoteException {
        return hoaDonService.findById(maHoaDon);
    }

    @Override
    public List<ChiTietHoaDon> findDetailsByMaHD(String maHoaDon) throws RemoteException {
        return hoaDonService.findDetailsByMaHD(maHoaDon);
    }

    @Override
    public List<HoaDon> search(HoaDonSearchRequest request) throws RemoteException {
        return hoaDonService.search(request);
    }
}
