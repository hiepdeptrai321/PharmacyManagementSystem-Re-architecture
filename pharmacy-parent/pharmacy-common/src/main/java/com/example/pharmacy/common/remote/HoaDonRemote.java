package com.example.pharmacy.common.remote;

import com.example.pharmacy.common.dto.UserContext;
import com.example.pharmacy.common.request.CreateHoaDonRequest;
import com.example.pharmacy.common.request.HoaDonSearchRequest;
import com.example.pharmacymanagementsystem_qlht.model.ChiTietHoaDon;
import com.example.pharmacymanagementsystem_qlht.model.HoaDon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HoaDonRemote extends Remote {
    String BINDING_NAME = "HoaDonRemoteService";

    String generateNewMaHoaDon() throws RemoteException;

    String createInvoice(CreateHoaDonRequest request, UserContext actor) throws RemoteException;

    List<HoaDon> findAll() throws RemoteException;

    HoaDon findById(String maHoaDon) throws RemoteException;

    List<ChiTietHoaDon> findDetailsByMaHD(String maHoaDon) throws RemoteException;

    List<HoaDon> search(HoaDonSearchRequest request) throws RemoteException;
}
