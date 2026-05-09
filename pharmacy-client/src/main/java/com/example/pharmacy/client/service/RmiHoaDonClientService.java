package com.example.pharmacy.client.service;

import com.example.pharmacy.client.rmi.RmiClientProvider;
import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.remote.HoaDonRemote;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacy.common.model.ChiTietHoaDon;
import com.example.pharmacy.common.model.HoaDon;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;

public class RmiHoaDonClientService implements HoaDonClientService {
    private final RmiClientProvider clientProvider;

    public RmiHoaDonClientService(RmiClientProvider clientProvider) {
        this.clientProvider = Objects.requireNonNull(clientProvider, "clientProvider must not be null");
    }

    @Override
    public String generateNewMaHoaDon() {
        try {
            return remote().generateNewMaHoaDon();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the sinh ma hoa don moi tu server: " + exception.getMessage());
            return "";
        }
    }

    @Override
    public String createInvoice(CreateHoaDonRequest request, UserContext actor) {
        try {
            return remote().createInvoice(request, actor);
        } catch (RemoteException | NotBoundException exception) {
            throw new IllegalStateException("Khong the tao hoa don tren server: " + exception.getMessage(), exception);
        }
    }

    @Override
    public List<HoaDon> findAll() {
        try {
            return remote().findAll();
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai danh sach hoa don tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public HoaDon findById(String maHoaDon) {
        try {
            return remote().findById(maHoaDon);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai hoa don tu server: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<ChiTietHoaDon> findDetailsByMaHD(String maHoaDon) {
        try {
            return remote().findDetailsByMaHD(maHoaDon);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tai chi tiet hoa don tu server: " + exception.getMessage());
            return List.of();
        }
    }

    @Override
    public List<HoaDon> search(HoaDonSearchRequest request) {
        try {
            return remote().search(request);
        } catch (RemoteException | NotBoundException exception) {
            System.err.println("Khong the tim kiem hoa don tren server: " + exception.getMessage());
            return List.of();
        }
    }

    private HoaDonRemote remote() throws RemoteException, NotBoundException {
        return clientProvider.getHoaDonRemote();
    }
}
